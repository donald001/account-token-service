package liming.emsp.ats.service;

import jakarta.validation.Valid;
import liming.emsp.ats.dao.AccountDao;
import liming.emsp.ats.domains.AccountDto;
import liming.emsp.ats.domains.AccountStatus;
import liming.emsp.ats.utils.EmaidGenerator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class AccountService {
    private AccountDao accountDao;

    public AccountService(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Transactional(rollbackFor = Exception.class)
    public AccountDto save(@Valid AccountDto account) {
        List<AccountDto> existingList = accountDao.findAccountDtoByServiceIdAndFleetSolution(account.getServiceId(), account.getFleetSolution());
        if (!CollectionUtils.isEmpty(existingList)) {
            throw new IllegalArgumentException("ServiceId and fleetSolution already exists!");
        }
        account.setStatus(AccountStatus.Created);
        account.setContractId(EmaidGenerator.generateEMAID(true));
        return accountDao.save(account);
    }

    public Page<AccountDto> getAccountsByPage(Pageable pager, LocalDateTime start, LocalDateTime end) {
        return accountDao.findAccountDtoByLastUpdatedBetween(start, end, pager);
    }

    public Integer updateAccountStatus(Long id, AccountStatus status) {
        return accountDao.updateAccountStatusById(id, status);
    }
}
