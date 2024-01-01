package com.micro.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(
        name = "Customer",
        description = "Hold customer and account data"
)
public class CustomerDto {
    @Schema(
            example = "Jone",
            description = "Name of customer"
    )
    @NotEmpty(message = "Name can not be empty.")
    @Size(min = 3, message = "Name should be more then 3")
    private String name;

    @Schema(
            example = "test@gmail.com",
            description = "Email of customer"
    )
    @Email(message = "Required a valid email.")
    private String email;

    @Schema(
            example = "00000000",
            description = "Phone number of customer"
    )
    @Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits.")
    private String mobileNumber;

    private AccountsDto accountsDto;
}
