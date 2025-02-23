package liming.emsp.ats.dao;

import liming.emsp.ats.domains.TokenDto;
import liming.emsp.ats.domains.TokenStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Repository
public interface TokenDao extends PagingAndSortingRepository<TokenDto,Long>, CrudRepository<TokenDto,Long> {
    @Modifying
    @Transactional
    @Query(value = "update TokenDto set status = :status, lastUpdated=CURRENT_TIMESTAMP where id = :id")
    Integer updateTokenStatusById(@Param("id") Long id, @Param("status")TokenStatus status);
    @Modifying
    @Transactional
    @Query(value = "update TokenDto set status = :status, accountId=:accountId, lastUpdated=CURRENT_TIMESTAMP where id = :id")
    Integer assignAccount(@Param("id")Long id, @Param("accountId")Long accountId, @Param("status")TokenStatus tokenStatus);
    Page<TokenDto> findTokenDtoByLastUpdatedBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);
}
