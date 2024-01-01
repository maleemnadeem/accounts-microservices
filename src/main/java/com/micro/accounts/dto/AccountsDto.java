package com.micro.accounts.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AccountsDto {
    @NotEmpty(message = "Account number can not be empty")
    @Pattern(regexp = "\\d{10}", message = "account number has only 10 digits.")
    private Long accountNumber;

    @NotEmpty
    private String accountType;

    @NotEmpty
    private String branchAddress;
}
