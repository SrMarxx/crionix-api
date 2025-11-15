package br.com.smarttech.crionix.controllers;

import br.com.smarttech.crionix.business.services.UserService;
import br.com.smarttech.crionix.controllers.dtos.ChangePasswordRecordDTO;
import br.com.smarttech.crionix.controllers.dtos.UserRequestRecordDTO;
import br.com.smarttech.crionix.controllers.dtos.UserResponseRecordDTO;
import br.com.smarttech.crionix.controllers.dtos.UserUpdateRecordDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('SCOPE_CADASTRAR_USUARIOS')")
    public ResponseEntity<Void> newUser(@RequestBody @Valid UserRequestRecordDTO userRecordDTO) {
        UserResponseRecordDTO newUserDTO = userService.createNewUser(userRecordDTO);

        URI location = URI.create("/api/users/" + newUserDTO.userId());
        return ResponseEntity.created(location).build();
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('SCOPE_LISTAR_USUARIOS', 'SCOPE_CADASTRAR_USUARIOS')")
    public ResponseEntity<List<UserResponseRecordDTO>> getUser(){
        var users = userService.findAllUsers();
        return ResponseEntity.ok(users);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMINISTRAR', 'SCOPE_LISTAR_USUARIOS') or #id.toString() == authentication.principal.subject")
    public ResponseEntity<UserResponseRecordDTO> updateUser(@PathVariable UUID id, @RequestBody @Valid UserUpdateRecordDTO userUpdateDTO){
        var updatedUser = userService.updateUser(id, userUpdateDTO);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_CADASTRAR_USUARIOS')")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/change-password")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> changePassword(
            @AuthenticationPrincipal(expression = "subject") String userIdString,
            @RequestBody @Valid ChangePasswordRecordDTO changePasswordDTO
    ){
        if (userIdString == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        UUID userId = UUID.fromString(userIdString);
        try{
            userService.changePassword(userId, changePasswordDTO);
            return ResponseEntity.ok().build();
        } catch (BadCredentialsException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserResponseRecordDTO> getMe(@AuthenticationPrincipal(expression = "subject")String userIdString){
        if (userIdString == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        UUID userId = UUID.fromString(userIdString);
        UserResponseRecordDTO userResponseDTO = userService.findById(userId);
        return ResponseEntity.ok(userResponseDTO);
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasAnyAuthority('SCOPE_LISTAR_USUARIOS', 'SCOPE_CADASTRAR_USUARIOS')")
    public ResponseEntity<UserResponseRecordDTO> getUserById(@PathVariable UUID userId){
        UserResponseRecordDTO userResponseDTO = userService.findById(userId);
        return ResponseEntity.ok(userResponseDTO);
    }
}
