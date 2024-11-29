package com.pizzaro.model;

import com.pizzaro.model.enums.Role;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Users implements UserDetails {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String username;
    private String password;
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Orderings> orderingsOwner = new ArrayList<>();
    @OneToMany(mappedBy = "manager", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Orderings> orderingsManager = new ArrayList<>();
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Details> details = new ArrayList<>();
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Stats stats;

    public Users(Role role, String username, String password) {
        this.role = role;
        this.username = username;
        this.password = passwordEncoder().encode(password);
    }

    public void setStats(Stats stats) {
        this.stats = stats;
        if (stats != null) {
            this.stats.setManager(this);
        }
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }//не истек ли срок

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }//не заблокан

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }//не истек ли срок данных

    @Override
    public boolean isEnabled() {
        return true;
    }//включена ли учетная запись

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(getRole());
    }

    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
