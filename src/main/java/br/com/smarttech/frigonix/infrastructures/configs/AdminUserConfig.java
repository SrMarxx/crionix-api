package br.com.smarttech.frigonix.infrastructures.configs;

import br.com.smarttech.frigonix.business.models.entities.AdminEntity;
import br.com.smarttech.frigonix.business.models.entities.UserEntity;
import br.com.smarttech.frigonix.business.models.repositories.IRoleJpaRepository;
import br.com.smarttech.frigonix.business.models.repositories.IUserJpaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class AdminUserConfig implements CommandLineRunner {

    private final IRoleJpaRepository roleRepository;
    private final IUserJpaRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Value("${admin.initial.matricula}")
    private String adminMatricula;
    @Value("${admin.initial.password}")
    private String adminPassword;
    @Value("${admin.initial.cpf}")
    private String adminCpf;
    @Value("${admin.initial.email}")
    private String adminEmail;

    @Autowired
    public AdminUserConfig(IRoleJpaRepository roleRepository, IUserJpaRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        var userAdmin = userRepository.findByMatricula(adminMatricula);

        userAdmin.ifPresentOrElse(
                user -> {
                    System.out.println("Admin ja existe.");
                },
                () -> {
                    UserEntity user = new AdminEntity();
                    user.setName("Admin");
                    user.setCpf(adminCpf);
                    user.setEmail(adminEmail);
                    user.setMatricula(adminMatricula);
                    user.setMustChangePassword(true);
                    user.setPassword(passwordEncoder.encode(adminPassword));
                    user.defineRoles(roleRepository);

                    userRepository.save(user);
                    System.out.println("Admin criado com sucesso.");
                }
        );
    }
}
