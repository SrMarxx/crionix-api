package br.com.smarttech.crionix.business.services;

import br.com.smarttech.crionix.business.models.entities.RoleEntity;
import br.com.smarttech.crionix.business.models.repositories.IUserJpaRepository;
import br.com.smarttech.crionix.controllers.dtos.LoginRequestRecordDTO;
import br.com.smarttech.crionix.controllers.dtos.LoginResponseRecordDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.stream.Collectors;

@Service
public class LoginService {

    @Value("${crionix.jwt.expiration-seconds}")
    private Long expiresIn;

    private final JwtEncoder jwtEncoder;
    private final IUserJpaRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public LoginService(JwtEncoder jwtEncoder, IUserJpaRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.jwtEncoder = jwtEncoder;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginResponseRecordDTO authenticateAndGenerateToken(LoginRequestRecordDTO loginRequest){
        var userOptional = userRepository.findByMatricula(loginRequest.matricula());

        var isPasswordValid = userOptional
                .map(user -> passwordEncoder.matches(loginRequest.password(), user.getPassword()))
                .orElse(false);

        if (userOptional.isEmpty() || !isPasswordValid){
            throw new BadCredentialsException("Matricula ou senha inválidos!");
        }

        var now = Instant.now();

        var scopes = userOptional.get().getRoles()
                .stream()
                .map(RoleEntity::getName)
                .collect(Collectors.joining(" "));

        var claims = JwtClaimsSet.builder()
                .issuer("crionix")
                .subject(userOptional.get().getUserId().toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresIn))
                .claim("scope", scopes)
                .build();

        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return new LoginResponseRecordDTO(jwtValue, expiresIn);
    }
}
