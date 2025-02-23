package liming.emsp.ats.controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import liming.emsp.ats.domains.AccountDto;
import liming.emsp.ats.domains.AccountStatus;
import liming.emsp.ats.service.AccountService;

public class AccountControllerTest {

    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountController accountController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void addAccount_ValidAccount_ReturnsCreatedAccount() {
        AccountDto accountDto = new AccountDto("serviceId", "fleetSolution", AccountStatus.Created, "contractId");
        when(accountService.save(any(AccountDto.class))).thenReturn(accountDto);

        AccountDto response = accountController.addAccount(accountDto);

        assertNotNull(response);
        assertEquals("serviceId", response.getServiceId());
        assertEquals("fleetSolution", response.getFleetSolution());
        assertEquals(AccountStatus.Created, response.getStatus());
        assertEquals("contractId", response.getContractId());
    }

    @Test
    public void getAccounts_WithStartAndEndDate_ReturnsAccounts() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<AccountDto> accountPage = new PageImpl<>(Collections.emptyList(), pageable, 0);
        when(accountService.getAccountsByPage(any(Pageable.class), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(accountPage);

        Page<AccountDto> response = accountController.getAccounts(pageable, LocalDateTime.of(2023, 1, 1, 0, 0),
                LocalDateTime.of(2023, 12, 31, 23, 59));

        assertNotNull(response);
        assertTrue(response.getContent().isEmpty());
    }

    @Test
    public void getAccounts_WithoutEndDate_ReturnsAccounts() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<AccountDto> accountPage = new PageImpl<>(Collections.emptyList(), pageable, 0);
        when(accountService.getAccountsByPage(any(Pageable.class), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(accountPage);

        Page<AccountDto> response = accountController.getAccounts(pageable, LocalDateTime.of(2023, 1, 1, 0, 0), null);

        assertNotNull(response);
        assertTrue(response.getContent().isEmpty());
    }

    @Test
    public void updateAccountStatus_ValidIdAndStatus_ReturnsUpdatedRows() {
        when(accountService.updateAccountStatus(anyLong(), any(AccountStatus.class))).thenReturn(1);

        Integer response = accountController.updateAccountStatus(1L, AccountStatus.Activated);

        assertNotNull(response);
        assertEquals(Integer.valueOf(1), response);
    }
}
