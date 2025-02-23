package liming.emsp.ats;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.DriverManager;
import java.sql.SQLException;

@SpringBootApplication
@Slf4j
public class AccountTokenServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountTokenServiceApplication.class, args);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                DriverManager.getConnection("jdbc:derby:;shutdown=true");
            } catch (SQLException e) {
                log.warn("closing DB exception:{}",e.getMessage());
            }
        }));
    }

}
