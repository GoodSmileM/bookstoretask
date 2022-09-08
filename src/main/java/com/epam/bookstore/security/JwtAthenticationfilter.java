package com.epam.bookstore.security;

import com.epam.bookstore.dao.TokenDao;
import io.jsonwebtoken.JwtException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 通过JWT令牌登录的过滤器，放在 UsernamePasswordAuthenticationFilter之前
 */
public class JwtAthenticationfilter extends OncePerRequestFilter {

    private final TokenDao tokenDao;

    public JwtAthenticationfilter(TokenDao tokenDao) {
        this.tokenDao = tokenDao;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取令牌
        String token = request.getHeader(SecurityConstants.TOKEN_HEADER);
        //若令牌为空，直接返回，进行usernamepassword认证
        if (token == null || !token.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            SecurityContextHolder.clearContext();
            filterChain.doFilter(request, response);
            return;
        }
        String tokenValue = token.replace(SecurityConstants.TOKEN_PREFIX, "");
        UsernamePasswordAuthenticationToken authentication = null;
        try {
            String previousToken = tokenDao.findTokenById(Long.parseLong(JwtUtil.getId(tokenValue)));
            if (!token.equals(previousToken)) {
                SecurityContextHolder.clearContext();
                filterChain.doFilter(request, response);
                return;
            }
            authentication = JwtUtil.getAuthentication(tokenValue);
        } catch (JwtException e) {
            logger.error("Invalid jwt : " + e.getMessage());
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }
}
