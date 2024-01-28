package com.nhnacademy.minidooray.account.backend.controller;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.minidooray.account.backend.domain.dto.AccountPageInfoDTO;
import com.nhnacademy.minidooray.account.backend.domain.dto.AccountStatusInfoDTO;
import com.nhnacademy.minidooray.account.backend.domain.requestbody.AccountRegisterRequest;
import com.nhnacademy.minidooray.account.backend.domain.requestbody.LoginInfoRequest;
import com.nhnacademy.minidooray.account.backend.service.AccountServiceImpl;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AccountRestController.class)
class AccountRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountServiceImpl accountService;

    @Test
    @DisplayName("계정 생성 성공")
    void testCreateAccountSuccess() throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        AccountRegisterRequest request = new AccountRegisterRequest("test", "1234", "test@test.com", "tester");

        when(accountService.createAccount(request))
                .thenReturn(true);

        mockMvc.perform(post("/account/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        verify(accountService).createAccount(request);
    }

    @Test
    @DisplayName("계정 생성 실패")
    void testCreateAccountFailed() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        AccountRegisterRequest request = new AccountRegisterRequest("test", "1234", "test@test.com", "tester");

        when(accountService.createAccount(request))
                .thenReturn(false);

        mockMvc.perform(post("/account/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict());

        verify(accountService).createAccount(request);
    }

    @Test
    @DisplayName("로그인 성공")
    void testDoLoginSuccess() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        LoginInfoRequest request = new LoginInfoRequest("test", "1234");

        AccountStatusInfoDTO accountStatusInfoDTO = new AccountStatusInfoDTO() {
            @Override
            public String getId() {
                return "test";
            }

            @Override
            public String getStatus() {
                return "1234";
            }
        };

        when(accountService.matches(request))
                .thenReturn(Optional.of(accountStatusInfoDTO));

        mockMvc.perform(post("/account/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", equalTo("test")));

        verify(accountService)
                .matches(request);
    }

    @Test
    @DisplayName("로그인 실패")
    void testDoLoginFailed() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        LoginInfoRequest request = new LoginInfoRequest("test", "1234");

        when(accountService.matches(request))
                .thenReturn(Optional.empty());

        mockMvc.perform(post("/account/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized());

        verify(accountService)
                .matches(request);
    }

    @Test
    @DisplayName("계정 정보 가져오기")
    void testGetInfoSuccess() throws Exception {
        AccountPageInfoDTO accountPageInfoDTO = new AccountPageInfoDTO() {
            @Override
            public String getId() {
                return "test";
            }

            @Override
            public String getEmail() {
                return "test@test.com";
            }

            @Override
            public String getName() {
                return "tester";
            }
        };

        when(accountService.getAccountPageInfo("test"))
                .thenReturn(Optional.of(accountPageInfoDTO));

        mockMvc.perform(get("/account/info/{id}", "test")
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo("test")));

        verify(accountService)
                .getAccountPageInfo("test");
    }

    @Test
    @DisplayName("계정 정보 가져오기 실패")
    void testGetInfoFailed() throws Exception {
        when(accountService.getAccountPageInfo("test"))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/account/info/{id}", "test")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(accountService)
                .getAccountPageInfo("test");
    }

    @Test
    @DisplayName("계정 삭제 성공")
    void testDeleteAccountSuccess() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        LoginInfoRequest request = new LoginInfoRequest("test", "1234");

        when(accountService.setDormantAccount(request))
                .thenReturn(true);

        mockMvc.perform(delete("/account/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        verify(accountService)
                .setDormantAccount(request);
    }

    @Test
    @DisplayName("계정 삭제 실패")
    void testDeleteAccountFailed() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        LoginInfoRequest request = new LoginInfoRequest("test", "1234");

        when(accountService.setDormantAccount(request))
                .thenReturn(false);

        mockMvc.perform(delete("/account/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict());

        verify(accountService)
                .setDormantAccount(request);
    }

}