package liming.emsp.ats.controller;

import jakarta.validation.Valid;
import liming.emsp.ats.domains.AccountDto;
import liming.emsp.ats.domains.AccountStatus;
import liming.emsp.ats.service.AccountService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/account")
public class AccountController {
    private AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    @PostMapping
    public AccountDto addAccount(@Valid @RequestBody AccountDto account) {
        return service.save(account);
    }

    @GetMapping("/all")
    public Page<AccountDto> getAccounts(@PageableDefault(page = 0, size = 10, sort = "lastUpdated", direction = Sort.Direction.DESC) Pageable pager,
                                        @RequestParam(defaultValue = "1970-01-01T00:00:00") LocalDateTime start, @RequestParam(required = false) LocalDateTime end) {
        if (null == end) {
            end = LocalDateTime.now();
        }
        return service.getAccountsByPage(pager, start, end);
    }

    @PutMapping("/{id}/status")
    public Integer updateAccountStatus(@PathVariable Long id, @RequestParam AccountStatus status) {
        return service.updateAccountStatus(id, status);
    }

}
