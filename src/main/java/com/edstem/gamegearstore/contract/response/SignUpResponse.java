package com.edstem.gamegearstore.contract.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignUpResponse {
    private Long id;
    private String name;
    private String email;
    private String hashedPassword;
}
