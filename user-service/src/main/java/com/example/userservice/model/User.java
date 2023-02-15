package com.example.userservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "id")
@Builder
@Table("users")
public class User implements Persistable<UUID> {

    @Id
    private UUID id;

    @NotBlank(message = "cannot be null, empty or whitespace")
    @Size(min = 4, max = 16, message = "must be between 4 and 16 characters")
    private String username;

    @NotBlank(message = "cannot be null, empty or whitespace")
    private String password;

    @NotNull(message = "cannot be null")
    private UUID roleId;

    @Transient
    private String roleName;
    @Transient
    private boolean newUser;

    @Override
    public boolean isNew() {
        return newUser;
    }


}

