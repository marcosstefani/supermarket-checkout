package com.qikserve.supermarket.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "username", referencedColumnName = "username")
    private User user;
    private boolean closed;
    private LocalDateTime createdAt;
    private LocalDateTime checkoutAt;

    public Basket(User user, boolean closed) {
        this.user = user;
        this.closed = closed;
        this.createdAt = LocalDateTime.now();
    }
}
