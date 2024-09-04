package com.durgesh.scm.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User{
    @Id
    private String userId;
    @Column(name="user_name", nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String email;
    private String password;
    @Column(length = 1000)
    private String about;
    private String phoneNumber;
    @Column(length = 1000)
    private String profilePic;

    //information
    public boolean enabled = false;
    public boolean emailVerified = false;
    public boolean phoneVerified = false;

    @Enumerated(EnumType.STRING)
    //SELF,GOOGLE,GITHUB
    private Providers provider= Providers.SELF ;
    private String providerUserId;

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY, cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Contact> contacts = new ArrayList<>();


}
