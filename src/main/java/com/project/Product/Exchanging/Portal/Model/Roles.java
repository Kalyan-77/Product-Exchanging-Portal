package com.project.Product.Exchanging.Portal.Model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Roles implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private ERole name;

    // This is the required method for Spring Security
    @Override
    public String getAuthority() {
        return name.name(); // returns "ROLE_USER" or "ROLE_ADMIN"
    }

    public enum ERole {
        ROLE_USER,
        ROLE_ADMIN
    }
}
