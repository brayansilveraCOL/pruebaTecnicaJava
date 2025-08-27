package com.core.api.Service.interfaces;

import com.core.api.dto.ResponseDTO;
import com.core.api.dto.user.UserDTO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserService {
    ResponseDTO create(@Validated @RequestBody UserDTO user);
}
