package com.example.userservice.model;

import com.example.userservice.entity.PgUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String login;
    private String companyName;
    private Long companyId;
    private String password;
    private boolean enabled;

    public PgUser toEntity(){
        PgUser user = new PgUser();
        user.setName(this.getName());
        user.setEnabled(this.isEnabled());
        user.setLogin(this.getLogin());
        user.setPassword(this.getPassword());
        user.setCompanyId(this.getCompanyId());
        user.setEmail(this.getEmail());
        return user;
    }
}
