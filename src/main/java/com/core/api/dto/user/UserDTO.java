package com.core.api.dto.user;

import com.core.api.Validators.anotate.Cedula;
import com.core.api.Validators.anotate.Phone;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDTO {
    @Cedula
    private String identify;
    @Email
    private String email;
    private String name;
    private String lastName;
    @Phone
    private String phone;
}
