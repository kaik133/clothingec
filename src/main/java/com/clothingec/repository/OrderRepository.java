package com.clothingec.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clothingec.model.Order;
import com.clothingec.model.User;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserOrderByOrderDateDesc(User user);
    List<Order> findByStatus(Order.Status status);
    List<Order> findAllByOrderByOrderDateDesc();
}