package user.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import user.entity.User;
import user.repository.UserRepository;
import user.service.UserService;


import java.util.List;
import java.util.UUID;

/**
 * @author fdse
 */
@Component
public class InitUser implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Override
    public void run(String... strings) throws Exception {
        User whetherExistUser = userRepository.findByUserName("fdse_microservice");
        if (whetherExistUser == null) {
            User user = User.builder()
                    .userId("4d2a46c7-71cb-4cf1-b5bb-b68406d9da6f")
                    .userName("fdse_microservice")
                    .password("111111")
                    .gender(1)
                    .documentType(1)
                    .documentNum("2135488099312X")
                    .email("fdse_microservice@163.com").build();
            User savedUser = userRepository.save(user);
            System.out.println("saved user: " + user);

        }
        User user = userRepository.findByUserId("4d2a46c7-71cb-4cf1-b5bb-b68406d9da6f");
        System.out.println(user);
    }
}
