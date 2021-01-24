package com.qikserve.supermarket.service;

import com.qikserve.supermarket.domain.User;
import com.qikserve.supermarket.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User find(String user) {
        return repository.findById(user).orElse(null);
    }

    public boolean exists(String user) {
        return repository.findById(user).isPresent();
    }

    public void create(String user) {
        repository.save(new User(user));
    }
}
