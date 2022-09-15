package com.epam.bookstore.service.impl;

import com.epam.bookstore.dao.RoleDao;
import com.epam.bookstore.dao.UserDao;
import com.epam.bookstore.dto.RegisterDTO;
import com.epam.bookstore.dto.common.ResponseUserToken;
import com.epam.bookstore.entity.User;
import com.epam.bookstore.enums.ResultEnum;
import com.epam.bookstore.exception.AuthErrorException;
import com.epam.bookstore.exception.BookErrorException;
import com.epam.bookstore.security.JwtUtils;
import com.epam.bookstore.service.AuthService;
import com.epam.bookstore.security.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtils jwtTokenUtil;
    private final UserDao userDao;
    private final RoleDao roleDao;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager, MyUserDetailsService myUserDetailsService, JwtUtils jwtTokenUtil, UserDao userDao, RoleDao roleDao) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = myUserDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDao = userDao;
        this.roleDao = roleDao;
    }


    @Override
    public ResponseUserToken login(String username, String password) {
        //用户验证
        final Authentication authentication = authenticate(username, password);
        //存储认证信息
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //生成token
        final User userDetail = (User) authentication.getPrincipal();
        final String token = jwtTokenUtil.generateAccessToken(userDetail);
        //存储token
        jwtTokenUtil.putToken(username, token);
        return new ResponseUserToken(token, username);
    }

    @Override
    public boolean logout(String token) {
        token = token.substring(tokenHead.length());
        String userName = jwtTokenUtil.getUsernameFromToken(token);
        jwtTokenUtil.deleteToken(userName);
        return true;
    }

    @Override
    public User register(RegisterDTO registerDTO) {
        if (userDao.findByUsername(registerDTO.getUsername()) != null) {
            throw new AuthErrorException(ResultEnum.USER_EXSISTED);
        }
        User user = new User();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setUsername(registerDTO.getUsername());
        user.setPassword(encoder.encode(registerDTO.getPassword()));
        user.setAccountNonExpired(true);
        user.setEnabled(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setId(registerDTO.getId());
        user.setRole(roleDao.findById(registerDTO.getRoleId()).get());
        userDao.save(user);
        return user;
    }

    private Authentication authenticate(String username, String password) {
        try {
            //该方法会去调用userDetailsService.loadUserByUsername()去验证用户名和密码，如果正确，则存储该用户名密码到“security 的 context中”
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException | BadCredentialsException e) {
            throw new BookErrorException(ResultEnum.LOGIN_ERROR);
        }
    }
}
