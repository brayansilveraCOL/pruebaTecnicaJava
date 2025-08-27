package com.core.api.Controller;

import com.core.api.Service.interfaces.UserService;
import com.core.api.dto.ResponseDTO;
import com.core.api.dto.user.UserDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("OK");
    }
    @PostMapping(value = "create")
    public ResponseEntity<ResponseDTO> create(@Valid @RequestBody UserDTO user)  {
        return ResponseEntity.ok(userService.create(user));
    }
}
