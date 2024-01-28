package com.nhnacademy.minidooray.account.backend;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.minidooray.account.backend.domain.requestbody.AccountRegisterRequest;
import com.nhnacademy.minidooray.account.backend.domain.requestbody.LoginInfoRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AccountIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    @DisplayName("계정 생성 성공")
    void testCreateAccountSuccess() throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        AccountRegisterRequest request = new AccountRegisterRequest("test", "1234", "test@test.com", "tester");

        mockMvc.perform(post("/account/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    @Order(2)
    @DisplayName("계정 생성 실패")
    void testCreateAccountFailed() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        AccountRegisterRequest request = new AccountRegisterRequest("test", "1234", "test@test.com", "tester");

        mockMvc.perform(post("/account/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict());
    }

    @Test
    @Order(3)
    @DisplayName("로그인 성공")
    void testDoLoginSuccess() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        LoginInfoRequest request = new LoginInfoRequest("test", "1234");

        mockMvc.perform(post("/account/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", equalTo("test")));
    }

    @Test
    @Order(4)
    @DisplayName("로그인 실패")
    void testDoLoginFailed() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        LoginInfoRequest request = new LoginInfoRequest("test", "123455");

        mockMvc.perform(post("/account/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Order(5)
    @DisplayName("계정 정보 가져오기")
    void testGetInfoSuccess() throws Exception {
        mockMvc.perform(get("/account/info/{id}", "test")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo("test")));
    }

    @Test
    @Order(6)
    @DisplayName("계정 정보 가져오기 실패")
    void testGetInfoFailed() throws Exception {
        mockMvc.perform(get("/account/info/{id}", "tests")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(7)
    @DisplayName("계정 삭제 성공")
    void testDeleteAccountSuccess() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        LoginInfoRequest request = new LoginInfoRequest("test", "1234");

        mockMvc.perform(delete("/account/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    @Order(8)
    @DisplayName("계정 삭제 실패")
    void testDeleteAccountFailed() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        LoginInfoRequest request = new LoginInfoRequest("test", "1234");

        mockMvc.perform(delete("/account/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict());
    }

}