package com.epam.bookstore.security;

import com.alibaba.fastjson2.JSON;
import com.epam.bookstore.dto.common.ResultBody;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@Component
public class JwtAthenticaitonSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String previousToken = (String) authentication.getCredentials();
        final String token = JwtUtil.createToken(JwtUtil.getSubject(previousToken), (String) JwtUtil.getId(previousToken));
        HashMap<String, Object> map = new HashMap<>();
        map.put("token", token);
        ResultBody r = new ResultBody(map).code(0).message("登录成功");
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String json = JSON.toJSONString(r);
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(json);

    }

}
