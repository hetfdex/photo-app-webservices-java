package com.hetfdex.webservices.mobileapp;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hetfdex.webservices.mobileapp.io.entity.UserEntity;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
	UserEntity findUserByEmail(String email);
}