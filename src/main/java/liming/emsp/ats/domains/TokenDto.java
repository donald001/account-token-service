package liming.emsp.ats.domains;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "token")
public class TokenDto extends BaseDto{
    @Enumerated(EnumType.STRING)
    private TokenStatus status;
    @NotNull(message = "token type can not be null!")
    @Enumerated(EnumType.STRING)
    private TokenType type;
    /**
     * foreign key references account(id)
     */
    private Long accountId;
    private String token;
}
