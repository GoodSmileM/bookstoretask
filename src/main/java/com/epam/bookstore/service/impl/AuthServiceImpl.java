package com.epam.bookstore.service.impl;

import com.epam.bookstore.dao.UserDao;
import com.epam.bookstore.dto.common.ResponseUserToken;
import com.epam.bookstore.dto.common.ResultBody;
import com.epam.bookstore.entity.User;
import com.epam.bookstore.enums.ResultEnum;
import com.epam.bookstore.exception.BookErrorException;
import com.epam.bookstore.security.JwtUtils;
import com.epam.bookstore.service.AuthService;
import com.epam.bookstore.security.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtils jwtTokenUtil;
    private final UserDao userDao;


    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager, MyUserDetailsService myUserDetailsService, JwtUtils jwtTokenUtil, UserDao userDao) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = myUserDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDao = userDao;
    }
    @Override
    public User register(User userDetail) {
        return null;
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
        return new ResponseUserToken(token, userDetail);
    }

    @Override
    public void logout(String token) {

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