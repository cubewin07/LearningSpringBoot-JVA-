package org.example.authen;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse {
    private String token;
}
