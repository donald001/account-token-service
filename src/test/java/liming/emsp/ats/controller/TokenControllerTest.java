package liming.emsp.ats.controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Collections;

import liming.emsp.ats.domains.TokenType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import liming.emsp.ats.domains.TokenDto;
import liming.emsp.ats.domains.TokenStatus;
import liming.emsp.ats.service.TokenService;

public class TokenControllerTest {

    @Mock
    private TokenService tokenService;

    @InjectMocks
    private TokenController tokenController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void addToken_ValidToken_ReturnsCreatedToken() {
        TokenDto tokenDto = new TokenDto(TokenStatus.Activated, TokenType.EMAID,1L, "token"  );
        when(tokenService.save(any(TokenDto.class))).thenReturn(tokenDto);

        TokenDto response = tokenController.addToken(tokenDto);

        assertNotNull(response);
        assertEquals("token", response.getToken());
        assertEquals(1L, response.getAccountId());
        assertEquals(TokenStatus.Activated, response.getStatus());
        assertEquals(TokenType.EMAID, response.getType());
    }

    @Test
    public void getTokens_WithStartAndEndDate_ReturnsTokens() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<TokenDto> tokenPage = new PageImpl<>(Collections.emptyList(), pageable, 0);
        when(tokenService.getTokensByPage(any(Pageable.class), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(tokenPage);

        Page<TokenDto> response = tokenController.getTokens(pageable, LocalDateTime.of(2023, 1, 1, 0, 0),
                LocalDateTime.of(2023, 12, 31, 23, 59));

        assertNotNull(response);
        assertTrue(response.getContent().isEmpty());
    }

    @Test
    public void getTokens_WithoutEndDate_ReturnsTokens() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<TokenDto> tokenPage = new PageImpl<>(Collections.emptyList(), pageable, 0);
        when(tokenService.getTokensByPage(any(Pageable.class), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(tokenPage);

        Page<TokenDto> response = tokenController.getTokens(pageable, LocalDateTime.of(2023, 1, 1, 0, 0), null);

        assertNotNull(response);
        assertTrue(response.getContent().isEmpty());
    }

    @Test
    public void updateTokenStatus_ValidIdAndStatus_ReturnsUpdatedRows() {
        when(tokenService.updateTokenStatus(anyLong(), any(TokenStatus.class))).thenReturn(1);

        Integer response = tokenController.updateTokenStatus(1L, TokenStatus.Activated);

        assertNotNull(response);
        assertEquals(Integer.valueOf(1), response);
    }

    @Test
    public void assignAccount_ValidIdAndAccountId_ReturnsUpdatedRows() {
        when(tokenService.assignAccount(anyLong(), anyLong())).thenReturn(1);

        Integer response = tokenController.assignAccount(1L, 100L);

        assertNotNull(response);
        assertEquals(Integer.valueOf(1), response);
    }
}
