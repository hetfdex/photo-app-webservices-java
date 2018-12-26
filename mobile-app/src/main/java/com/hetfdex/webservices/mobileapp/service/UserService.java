package com.hetfdex.webservices.mobileapp.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.hetfdex.webservices.mobileapp.shared.dto.UserDTO;

public interface UserService extends UserDetailsService {
	UserDTO createUser(UserDTO user);

	UserDTO updateUser(String id, UserDTO user);

	boolean deleteUser(String id);

	List<UserDTO> getUsers(int page, int limit);

	UserDTO getUserByEmail(String email);

	UserDTO getUserByUserID(String id);
}