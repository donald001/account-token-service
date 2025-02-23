
package liming.emsp.ats.service;


import liming.emsp.ats.dao.TokenDao;
import liming.emsp.ats.domains.TokenDto;
import liming.emsp.ats.domains.TokenStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TokenServiceTest {

    public static final String TEST_TOKEN = "testToken";
    @Mock
    private TokenDao tokenDao;

    @InjectMocks
    private TokenService tokenService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void save_ShouldSetStatusToCreatedAndSaveToken() {
        TokenDto token = new TokenDto();
        token.setToken(TEST_TOKEN);

        TokenDto savedToken = new TokenDto();
        savedToken.setId(1L);
        savedToken.setToken(TEST_TOKEN);
        savedToken.setStatus(TokenStatus.Created);

        when(tokenDao.save(any(TokenDto.class))).thenReturn(savedToken);

        TokenDto result = tokenService.save(token);

        assertEquals(TokenStatus.Created, result.getStatus());
        assertEquals(1L, result.getId());
        assertEquals(TEST_TOKEN, result.getToken());
    }

    @Test
    public void getTokensByPage_ShouldReturnTokensWithinDateRange() {
        Pageable pageable = PageRequest.of(0, 10);
        LocalDateTime start = LocalDateTime.of(2023, 1, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2023, 12, 31, 23, 59);

        TokenDto token = new TokenDto();
        token.setId(1L);
        token.setToken(TEST_TOKEN);
        token.setStatus(TokenStatus.Created);

        Page<TokenDto> page = new PageImpl<>(Collections.singletonList(token));

        when(tokenDao.findTokenDtoByLastUpdatedBetween(start, end, pageable)).thenReturn(page);

        Page<TokenDto> result = tokenService.getTokensByPage(pageable, start, end);

        assertEquals(1, result.getTotalElements());
        assertEquals(TEST_TOKEN, result.getContent().get(0).getToken());
    }

    @Test
    public void updateTokenStatus_ShouldUpdateStatusSuccessfully() {
        Long tokenId = 1L;
        TokenStatus newStatus = TokenStatus.Activated;

        when(tokenDao.updateTokenStatusById(tokenId, newStatus)).thenReturn(1);

        Integer result = tokenService.updateTokenStatus(tokenId, newStatus);

        assertEquals(1, result);
    }

    @Test
    public void assignAccount_ShouldAssignAccountAndSetStatusToAssigned() {
        Long tokenId = 1L;
        Long accountId = 100L;

        when(tokenDao.assignAccount(tokenId, accountId, TokenStatus.Assigned)).thenReturn(1);

        Integer result = tokenService.assignAccount(tokenId, accountId);

        assertEquals(1, result);
    }
}