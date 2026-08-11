package com.pulseengine.gateway.security.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pulseengine.gateway.model.User;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserDetailsImpl implements UserDetails {
    private Long id;
    private String username;
    private String tenantId;

    @JsonIgnore
    private String password;

    public UserDetailsImpl(Long id, String username, String tenantId,
                           String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.tenantId = tenantId;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserDetailsImpl build(User user) {

        List<GrantedAuthority> grantedAuthorityList = List.of(new SimpleGrantedAuthority(user.getRole().name()));

        return new UserDetailsImpl(
                user.getId(),
                user.getUsername(),
                user.getTenantId(),
                user.getPassword(),
                grantedAuthorityList
        );

    }

    // Holds the user's role in Spring Security's authority format
    private Collection<? extends GrantedAuthority> authorities;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public @Nullable String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public String getTenantId() {
        return tenantId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
