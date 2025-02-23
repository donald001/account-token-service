package liming.emsp.ats.domains;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "account")
public class AccountDto extends BaseDto{
    @NotBlank(message = "ServiceId can not be null!")
    private String serviceId;
    @NotBlank(message = "fleetSolution can not be null!")
    private String fleetSolution;
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
    private String contractId;
}
