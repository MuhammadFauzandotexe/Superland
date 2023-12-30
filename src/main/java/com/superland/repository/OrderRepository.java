package com.superland.repository;

import com.superland.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
@EnableJpaRepositories
public interface OrderRepository extends JpaRepository<Orders,String> {


    Optional<List<Orders>> findByStatus(String status );
}
