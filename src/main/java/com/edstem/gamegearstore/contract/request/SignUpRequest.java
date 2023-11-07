package com.edstem.gamegearstore.contract.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
    public class SignUpRequest {
        private String username;
        private String password;
        private String email;
}
