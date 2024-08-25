package sa.lendo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import sa.lendo.entity.User;
import sa.lendo.repository.UserRepository;

@SpringBootApplication
public class LendoApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(LendoApplication.class, args);
    }

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public void addUsers() {
        var user = new User();
        user.setUsername("bassam");
        user.setEmail("bassam@lendo.sa");
        user.setPassword(passwordEncoder.encode("bassam"));

        userRepository.save(user);
    }

    @Override
    public void run(String... args) {
        addUsers();
    }
}
