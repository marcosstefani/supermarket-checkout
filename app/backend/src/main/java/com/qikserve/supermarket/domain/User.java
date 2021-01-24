package com.qikserve.supermarket.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id
    private String username;
    private LocalDateTime createdAt;

    public User(String username) {
        this.username = username;
        this.createdAt = LocalDateTime.now();
    }
}
