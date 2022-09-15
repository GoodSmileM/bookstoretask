package com.epam.bookstore.controller;

import com.epam.bookstore.dto.LoginDTO;
import com.epam.bookstore.dto.RegisterDTO;
import com.epam.bookstore.dto.common.ResponseUserToken;
import com.epam.bookstore.entity.User;
import com.epam.bookstore.exception.AuthErrorException;
import com.epam.bookstore.service.impl.AuthServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private AuthServiceImpl authService;
    private static final String USERNAME = "user";
    private static final String PASSWORD = "123";
    private static final String TOKEN = "eyJhbGciOiJIUzI1NiIsInppcCI6IkRFRiJ9.eJwljFEKwkAMBe-S7y64mzTN9jKybSKsoBbTgiDe3UD_Zh7D-8Lh9r52hTkXpAF8fW0GMzR99CeEH0uYuwff9x7MhiVPa04XmSyRLZIqCycdm1amdlOTiHvb45MZCyERDmCf7RywVCH8_QGOZiGA.lvCpyNS81UXM5Vow_AnGxl6PAVeSEjLDMleJbuhWCqk";
    private static final ResponseUserToken RESPONSE_USER_TOKEN = new ResponseUserToken(TOKEN, USERNAME);
    private static final LoginDTO LOGIN_DTO = new LoginDTO("user", "123");
    private static final RegisterDTO REGISTER_DTO = new RegisterDTO("user", "123", 43243243L, 1L);
    private static final RegisterDTO WRONG_REGISTER_DTO = new RegisterDTO("", "", 43243243L, 1L);
    private static final User USER = new User(1L, "user", "123");

    @Autowired
    private WebApplicationContext wac;

    @BeforeEach
    void setUp() {
        authController = new AuthController(authService);
    }

    @Test
    void login() {
        Mockito.when(authService.login(any(), any())).thenReturn(RESPONSE_USER_TOKEN);
        Assertions.assertEquals(RESPONSE_USER_TOKEN, authController.login(LOGIN_DTO).getBody().getData());
    }

    @Test
    void registerFail() {
        Mockito.when(authService.register(any())).thenReturn(USER);
        try {
            authController.register(WRONG_REGISTER_DTO);
            Assertions.fail("no expected exception thrown");
        } catch (AuthErrorException e) {
            Assertions.assertEquals(-8, e.getCode());
        }
    }

    @Test
    void registerSuccess() {
        Mockito.when(authService.register(any())).thenReturn(USER);
        Assertions.assertNotNull(authController.register(REGISTER_DTO));
    }
}

