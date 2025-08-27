package com.core.api.dto.fund.notification;

import com.core.api.Enums.TypeNotificationEnum;
import com.core.api.Validators.anotate.Phone;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class NotificationDTO {
    @NotNull
    @NotBlank
    private TypeNotificationEnum type;

    @NotNull
    @NotBlank
    private String message;

    @Email(message = "{email.message}")
    private String email;

    @Phone
    private String phone;

}
