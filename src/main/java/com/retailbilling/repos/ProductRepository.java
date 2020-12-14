package com.retailbilling.repos;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.retailbilling.models.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, String>{

}

