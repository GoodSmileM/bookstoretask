package com.epam.bookstore.service.impl;

import com.epam.bookstore.dao.RoleDao;
import com.epam.bookstore.dao.UserDao;
import com.epam.bookstore.dto.LoginDTO;
import com.epam.bookstore.dto.RegisterDTO;
import com.epam.bookstore.entity.Role;
import com.epam.bookstore.entity.User;
import com.epam.bookstore.exception.AuthErrorException;
import com.epam.bookstore.exception.BookErrorException;
import com.epam.bookstore.security.JwtUtils;
import com.epam.bookstore.security.MyUserDetailsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class AuthServiceImplTest {

    @InjectMocks
    private AuthServiceImpl authService;

    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private MyUserDetailsService myUserDetailsService;
    @Mock
    private JwtUtils jwtTokenUtil;
    @Mock
    private UserDao userDao;
    @Mock
    private RoleDao roleDao;
    private static final String RIGHT_USERNAME = "user";
    private static final String RIGHT_PASSWORD = "123";
    private static final LoginDTO RIGHT_LOGINDTO = new LoginDTO("user", "123");
    private static final User USER = new User(1L, "user", "123");
    private static final String TOKEN = "eyJhbGciOiJIUzI1NiIsInppcCI6IkRFRiJ9.eJwljFEKwkAMBe-S7y64mzTN9jKybSKsoBbTgiDe3UD_Zh7D-8Lh9r52hTkXpAF8fW0GMzR99CeEH0uYuwff9x7MhiVPa04XmSyRLZIqCycdm1amdlOTiHvb45MZCyERDmCf7RywVCH8_QGOZiGA.lvCpyNS81UXM5Vow_AnGxl6PAVeSEjLDMleJbuhWCqk";
    private static final RegisterDTO REGISTER_DTO=new RegisterDTO("user","123",43243243L,1L);
    private static final Role ROLE=new Role(1L,"user");

    @BeforeEach
    void setUp() {
        authService = new AuthServiceImpl(authenticationManager, myUserDetailsService, jwtTokenUtil, userDao, roleDao);
    }

    @Test
    void login() {
        Mockito.when(authenticationManager.authenticate(any())).thenReturn(new UsernamePasswordAuthenticationToken(USER, null));
        Mockito.when(jwtTokenUtil.generateAccessToken(any())).thenReturn(TOKEN);
        Assertions.assertNotNull(authService.login(RIGHT_USERNAME, RIGHT_PASSWORD));
    }

    @Test
    void logout() {
        ReflectionTestUtils.setField(authService,"tokenHead","Bearer");
        Assertions.assertTrue(authService.logout(TOKEN));
    }

    @Test
    void registerFail() {
        Mockito.when(userDao.findByUsername(any())).thenReturn(USER);
         try {
            authService.register(REGISTER_DTO);
            Assertions.fail("no expected exception thrown");
        } catch (AuthErrorException e) {
            Assertions.assertEquals(-7, e.getCode());
        }
    }

    @Test
    void registerSuccess() {
        Mockito.when(userDao.findByUsername(any())).thenReturn(null);
        Mockito.when(roleDao.findById(any())).thenReturn(Optional.of(ROLE));
        Assertions.assertNotNull(authService.register(REGISTER_DTO));
    }
}