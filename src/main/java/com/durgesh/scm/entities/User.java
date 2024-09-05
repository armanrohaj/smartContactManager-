package com.durgesh.scm.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements UserDetails {

    @Id
    private String userId;
    @Column(name="user_name", nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String email;
    @Getter(AccessLevel.NONE)
    private String password;
    @Column(length = 1000)
    private String about;
    private String phoneNumber;
    @Column(length = 1000)
    private String profilePic;

    //information
    @Getter(value = AccessLevel.NONE)
    public boolean enabled = true;
    public boolean emailVerified = false;
    public boolean phoneVerified = false;

    @Enumerated(EnumType.STRING)
    //SELF,GOOGLE,GITHUB
    private Providers provider= Providers.SELF ;
    private String providerUserId;

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY, cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Contact> contacts = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roleList = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
    //List of roles[USER,ADMIN]
        //Collection of SimpleGrantedAuthority[roles{ADMIN,USER}]
        Collection<SimpleGrantedAuthority> roles = roleList.stream().map(role-> new SimpleGrantedAuthority(role)).collect(Collectors.toList());
        return roles;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
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
        return enabled;
    }
}
