package com.example.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.Entity.Order;
import com.example.Entity.Orders;

@Repository
public interface PagingDAOInterfaceNew extends JpaRepository<Orders, Integer> {
	
	List<Orders> findAll();
	
}
