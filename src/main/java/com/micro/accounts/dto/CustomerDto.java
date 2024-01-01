package com.micro.accounts.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerDto {
    @NotEmpty(message = "Name can not be empty.")
    @Size(min = 3, message = "Name should be more then 3")
    private String name;

    @Email(message = "Required a valid email.")
    private String email;

    @Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits.")
    private String mobileNumber;

    private AccountsDto accountsDto;
}
