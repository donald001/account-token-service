
package liming.emsp.ats.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import liming.emsp.ats.dao.AccountDao;
import liming.emsp.ats.domains.AccountDto;
import liming.emsp.ats.domains.AccountStatus;
import liming.emsp.ats.utils.EmaidGenerator;

public class AccountServiceTest {
    public static final String CONTRACT_ID = "contractId";
    @Mock
    private AccountDao accountDao;

    @InjectMocks
    private AccountService accountService;



    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void save_AccountCreated_StatusAndContractIdSet() {

        AccountDto account = new AccountDto("serviceId", "fleetSolution", null, null);
        AccountDto savedAccount = new AccountDto("serviceId", "fleetSolution", AccountStatus.Created, CONTRACT_ID);

        when(accountDao.save(any(AccountDto.class))).thenReturn(savedAccount);
        MockedStatic<EmaidGenerator> emaidGeneratorMockedStatic = mockStatic(EmaidGenerator.class);
        emaidGeneratorMockedStatic.when(() -> EmaidGenerator.generateEMAID(true)).thenReturn(CONTRACT_ID);
        AccountDto result = accountService.save(account);

        assertEquals(AccountStatus.Created, result.getStatus());
        assertEquals(CONTRACT_ID, result.getContractId());
        verify(accountDao, times(1)).save(any(AccountDto.class));
    }

    @Test
    public void getAccountsByPage_AccountsRetrievedCorrectly() {
        Pageable pageable = PageRequest.of(0, 10);
        LocalDateTime start = LocalDateTime.of(2023, 1, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2023, 12, 31, 23, 59);
        AccountDto account = new AccountDto("serviceId", "fleetSolution", AccountStatus.Activated, CONTRACT_ID);
        Page<AccountDto> accountPage = new PageImpl<>(java.util.Collections.singletonList(account));

        when(accountDao.findAccountDtoByLastUpdatedBetween(start, end, pageable)).thenReturn(accountPage);

        Page<AccountDto> result = accountService.getAccountsByPage(pageable, start, end);

        assertEquals(1, result.getTotalElements());
        assertEquals("serviceId", result.getContent().get(0).getServiceId());
        verify(accountDao, times(1)).findAccountDtoByLastUpdatedBetween(start, end, pageable);
    }

    @Test
    public void updateAccountStatus_StatusUpdatedSuccessfully() {
        Long accountId = 1L;
        AccountStatus newStatus = AccountStatus.Deactivated;

        when(accountDao.updateAccountStatusById(accountId, newStatus)).thenReturn(1);

        Integer result = accountService.updateAccountStatus(accountId, newStatus);

        assertEquals(1, result);
        verify(accountDao, times(1)).updateAccountStatusById(accountId, newStatus);
    }
}