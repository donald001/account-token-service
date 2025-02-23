package liming.emsp.ats.controller;

import jakarta.validation.Valid;
import liming.emsp.ats.domains.TokenDto;
import liming.emsp.ats.domains.TokenStatus;
import liming.emsp.ats.service.TokenService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/token")
public class TokenController {
    private final TokenService service;

    public TokenController(TokenService service) {
        this.service = service;
    }

    @PostMapping
    public TokenDto addToken(@Valid @RequestBody TokenDto token){
        return service.save(token);
    }
    @GetMapping("/all")
    public Page<TokenDto> getTokens(@PageableDefault(page = 0, size = 10, sort = "lastUpdated", direction = Sort.Direction.DESC) Pageable pager
            , @RequestParam(defaultValue = "1970-01-01T00:00:00") LocalDateTime start, @RequestParam(required = false) LocalDateTime end){
        if (null == end) {
            end = LocalDateTime.now();
        }
        return service.getTokensByPage(pager, start, end);
    }
    @PutMapping("/{id}/status")
    public Integer updateTokenStatus(@PathVariable Long id, @RequestParam TokenStatus status){
        return service.updateTokenStatus(id, status);
    }
    @PutMapping("/{id}/account")
    public Integer assignAccount(@PathVariable Long id, @RequestParam Long accountId){
        return service.assignAccount(id, accountId);
    }
}
