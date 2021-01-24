package com.qikserve.supermarket.repository;

import com.qikserve.supermarket.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, String> {}
