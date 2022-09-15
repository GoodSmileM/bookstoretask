package com.epam.bookstore.security;

import com.alibaba.fastjson2.JSONObject;
import com.epam.bookstore.dto.LoginDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
@SpringBootTest
public class SecurityTest {
    private MockMvc mockMvc;


    private static final LoginDTO LOGIN_DTO = new LoginDTO("user", "123");

    @Autowired
    private WebApplicationContext wac;

    @BeforeEach // 在测试开始前初始化工作
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).apply(springSecurity()).build();
    }


    @Test
    @WithMockUser(username = "user", password = "123")
    public void testLoginSuccess() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("username", "user")
                        .param("password", "123"))
                .andExpect(authenticated());

    }

    @Test
    public void testLoginFail() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSONObject.toJSONString(LOGIN_DTO)))
                .andExpect(unauthenticated());

    }

    @Test
    @WithMockUser(username = "user", password = "123")
    public void testLogoutSuccess() throws Exception {
        // 测试成功退出登录
        mockMvc.perform(MockMvcRequestBuilders.get("/signout")).andExpect(authenticated());
    }

    @Test
    public void testLogoutFail() throws Exception {
        // 测试退出登录
        mockMvc.perform(MockMvcRequestBuilders.get("/signout")).andExpect(unauthenticated());
    }
}
