package com.superland.repository;

import com.superland.entity.UserAccounts;
import com.superland.entity.UserCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
@EnableJpaRepositories
public interface AccountRepository extends JpaRepository<UserAccounts,String> {
    Optional<UserAccounts> findByCredential(UserCredentials userCredentials);
}
