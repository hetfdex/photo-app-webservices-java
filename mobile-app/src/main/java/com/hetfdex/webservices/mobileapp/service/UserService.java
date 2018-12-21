package com.hetfdex.webservices.mobileapp.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.hetfdex.webservices.mobileapp.shared.dto.UserDTO;

public interface UserService extends UserDetailsService{
	UserDTO createUser(UserDTO user);
	UserDTO getUser(String email);
}
