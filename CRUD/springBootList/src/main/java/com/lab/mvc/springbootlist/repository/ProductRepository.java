package com.lab.mvc.springbootlist.repository;

import com.lab.mvc.springbootlist.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
