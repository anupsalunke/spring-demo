package com.demo.repository;

import com.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    public List<User> findAll();

    public Optional<User> findByUserId(int userId);
}
