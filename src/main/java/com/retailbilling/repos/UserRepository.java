package com.retailbilling.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.retailbilling.models.User;

@Repository
public interface UserRepository extends CrudRepository<User, String>{

}
