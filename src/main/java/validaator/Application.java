package validaator;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import validaator.model.StopRepository;
import validaator.model.TransactionRepository;
import validaator.model.User;
import validaator.model.UserRepository;

import java.sql.Date;
import java.util.Arrays;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    @Bean
    CommandLineRunner init(UserRepository userRepository) {
        return (args) ->
                Arrays.asList("username1,username2".split(","))
                        .forEach(a -> {
                            User user = userRepository.save(new User(
                                    a,
                                    "password",
                                    a.concat("@email.com"),
                                    a.concat("39505310000"),
                                    Date.valueOf("1995-05-31"),
                                    "firstname",
                                    "lastname"));
                        });
    }
}
