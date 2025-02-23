package liming.emsp.ats.dao;

import liming.emsp.ats.domains.AccountDto;
import liming.emsp.ats.domains.AccountStatus;
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
import java.util.List;

@Repository
public interface AccountDao extends PagingAndSortingRepository<AccountDto,Long>, CrudRepository<AccountDto,Long> {
    @Modifying
    @Transactional
    @Query(value = "update AccountDto set status = :status, lastUpdated=CURRENT_TIMESTAMP  where id = :id")
    Integer updateAccountStatusById(@Param("id") Long id, @Param("status")AccountStatus status);

    Page<AccountDto> findAccountDtoByLastUpdatedBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);
    List<AccountDto> findAccountDtoByServiceIdAndFleetSolution(String serviceId, String fleetSolution);

}
