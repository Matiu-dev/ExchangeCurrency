package org.acme.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class AppUser {

    @Id
    @GeneratedValue
    private long id;
    private String username;
    private String password;
}
