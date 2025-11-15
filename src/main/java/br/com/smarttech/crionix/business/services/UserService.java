package br.com.smarttech.crionix.business.services;

import br.com.smarttech.crionix.business.models.entities.GeradorMatriculaEntity;
import br.com.smarttech.crionix.business.models.entities.UserEntity;
import br.com.smarttech.crionix.business.models.repositories.IGeradorMatriculaJpaRepository;
import br.com.smarttech.crionix.business.models.repositories.IRoleJpaRepository;
import br.com.smarttech.crionix.business.models.repositories.IUserJpaRepository;
import br.com.smarttech.crionix.controllers.dtos.ChangePasswordRecordDTO;
import br.com.smarttech.crionix.controllers.dtos.UserRequestRecordDTO;
import br.com.smarttech.crionix.controllers.dtos.UserResponseRecordDTO;
import br.com.smarttech.crionix.controllers.dtos.UserUpdateRecordDTO;
import br.com.smarttech.crionix.controllers.mappers.UserMapper;
import br.com.smarttech.crionix.infrastructures.utils.MatriculaUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final IUserJpaRepository userRepository;
    private final IRoleJpaRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final IGeradorMatriculaJpaRepository geradorMatriculaRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public UserService(IUserJpaRepository userRepository, IRoleJpaRepository roleRepository, BCryptPasswordEncoder passwordEncoder, IGeradorMatriculaJpaRepository geradorMatriculaRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.geradorMatriculaRepository = geradorMatriculaRepository;
    }

    @Transactional
    public UserResponseRecordDTO createNewUser(UserRequestRecordDTO userDTO){

        userRepository.findByCpf(userDTO.cpf()).ifPresent(user -> {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "CPF já cadastrado");
        });

        String temporaryPassword = generateTemporaryPassword(userDTO);

        UserEntity user = switch (userDTO.cargo()){
            case COLABORADOR -> new ColaboradorEntity();
            case RH -> new RHEntity();
            case GESTOR_RH -> new GestorRHEntity();
            case TI -> new TIEntity();
            case ADMIN -> new AdminEntity();
        };

        user.setName(userDTO.name());
        user.setCpf(userDTO.cpf());
        user.setNascimento(userDTO.nascimento());
        user.setEmail(userDTO.email());
        user.setMatricula(generateMatricula());

        user.setPassword(passwordEncoder.encode(temporaryPassword));
        user.setMustChangePassword(true);

        user.defineRoles(roleRepository);

        UserEntity savedUser = userRepository.save(user);

        return UserMapper.toResponseDTO(savedUser);
    }

    @Transactional
    public UserResponseRecordDTO updateUser(UUID id, UserUpdateRecordDTO userUpdateDTO){
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        if (userUpdateDTO.name() != null && !userUpdateDTO.name().isBlank()){
            user.setName(userUpdateDTO.name());
        }

        if (userUpdateDTO.email() != null && !userUpdateDTO.email().isBlank()){
            user.setEmail(userUpdateDTO.email());
        }

        UserEntity savedUser = userRepository.save(user);

        return UserMapper.toResponseDTO(savedUser);
    }

    @Transactional
    public void deleteUser(UUID id){
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        userRepository.delete(user);
    }

    public List<UserResponseRecordDTO> findAllUsers(){

        Session session = entityManager.unwrap(Session.class);
        session.enableFilter("activeUserFilter").setParameter("isActive", true);

        List<UserEntity> users = userRepository.findAll();

        session.disableFilter("activeUserFilter");

        return users.stream().map(UserMapper::toResponseDTO).collect(Collectors.toList());
    }

    @Transactional
    public void changePassword(UUID userId, ChangePasswordRecordDTO changePasswordDTO){
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado."));

        if (!passwordEncoder.matches(changePasswordDTO.senhaAntiga(), user.getPassword())){
            throw new BadCredentialsException("A senha antiga está incorreta.");
        }

        user.setPassword(passwordEncoder.encode(changePasswordDTO.novaSenha()));

        if (user.isMustChangePassword()){
            user.setMustChangePassword(false);
        }

        userRepository.save(user);
    }

    public UserResponseRecordDTO findById(UUID userId){
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado."));

        return UserMapper.toResponseDTO(user);
    }

    private String generateMatricula(){
        int anoAtual = LocalDate.now().getYear();
        GeradorMatriculaEntity geradorMatricula = geradorMatriculaRepository.findByAno(anoAtual).orElseGet(() -> new GeradorMatriculaEntity(anoAtual));
        Long novoSequencial = geradorMatricula.incrementarEObter();
        geradorMatriculaRepository.save(geradorMatricula);

        String numeroBase = String.format("%d%05d", anoAtual, novoSequencial);

        String digitoVerificador = MatriculaUtils.calcularDigitoVerificador(numeroBase);

        return numeroBase + "-" + digitoVerificador;
    }

    private String generateTemporaryPassword(UserRequestRecordDTO userDTO){

        String firstName = userDTO.name().split(" ")[0].toLowerCase();

        String cleanedCpf = userDTO.cpf().replaceAll("\\D", "");

        String lastFourDigitsOfCpf = cleanedCpf.substring(cleanedCpf.length() - 4);

        return firstName + "@" + lastFourDigitsOfCpf + "!";
    }

}
