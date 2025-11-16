package br.com.smarttech.frigonix.controllers;

import br.com.smarttech.frigonix.business.services.LoginService;
import br.com.smarttech.frigonix.controllers.dtos.LoginRequestRecordDTO;
import br.com.smarttech.frigonix.controllers.dtos.LoginResponseRecordDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
public class LoginController {

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping
    public ResponseEntity<LoginResponseRecordDTO> login(@RequestBody @Valid LoginRequestRecordDTO loginRequestRecordDTO){
        var loginResponse = loginService.authenticateAndGenerateToken(loginRequestRecordDTO);
        return ResponseEntity.ok(loginResponse);
    }
}
