package org.javaschool.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private int id;

    private String username;

    private String email;

    private PassengerDto passenger;

    private String password;

    private String passwordConfirm;

    private Set<RoleDto> roles;
}
