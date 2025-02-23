package liming.emsp.ats.service;

import jakarta.validation.Valid;
import liming.emsp.ats.dao.TokenDao;
import liming.emsp.ats.domains.TokenDto;
import liming.emsp.ats.domains.TokenStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class TokenService {
    private TokenDao tokenDao;

    public TokenService(TokenDao tokenDao) {
        this.tokenDao = tokenDao;
    }

    public TokenDto save(@Valid TokenDto token) {
        token.setStatus(TokenStatus.Created);
        return tokenDao.save(token);
    }

    public Page<TokenDto> getTokensByPage(Pageable pager, LocalDateTime start, LocalDateTime end) {
        return tokenDao.findTokenDtoByLastUpdatedBetween(start, end, pager);
    }

    public Integer updateTokenStatus(Long id, TokenStatus status) {
        return tokenDao.updateTokenStatusById(id, status);
    }

    public Integer assignAccount(Long id, Long accountId) {
        return tokenDao.assignAccount(id, accountId, TokenStatus.Assigned);
    }
}
