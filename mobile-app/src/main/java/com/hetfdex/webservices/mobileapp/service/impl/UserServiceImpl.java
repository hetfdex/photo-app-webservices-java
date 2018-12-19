package com.hetfdex.webservices.mobileapp.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hetfdex.webservices.mobileapp.UserRepository;
import com.hetfdex.webservices.mobileapp.io.entity.UserEntity;
import com.hetfdex.webservices.mobileapp.service.UserService;
import com.hetfdex.webservices.mobileapp.shared.dto.UserDTO;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;
	
	@Override
	public UserDTO createUser(UserDTO user) {
		UserEntity userEntity = new UserEntity();
		
		BeanUtils.copyProperties(user, userEntity);
		
		userEntity.setUserID("test-userid");
		userEntity.setEncryptedPassword("test-password");
		
		UserEntity savedUser = userRepository.save(userEntity);
		
		UserDTO result = new UserDTO();
		
		BeanUtils.copyProperties(savedUser, result);
		
		return result;
	}
}
