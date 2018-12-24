package com.hetfdex.webservices.mobileapp.service.impl;

import java.util.ArrayList;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.hetfdex.webservices.mobileapp.io.entity.UserEntity;
import com.hetfdex.webservices.mobileapp.io.repositories.UserRepository;
import com.hetfdex.webservices.mobileapp.service.UserService;
import com.hetfdex.webservices.mobileapp.shared.Utils;
import com.hetfdex.webservices.mobileapp.shared.dto.UserDTO;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	Utils utils;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	UserRepository userRepository;

	@Override
	public UserDTO createUser(UserDTO user) {
		UserEntity userEntity = new UserEntity();

		BeanUtils.copyProperties(user, userEntity);

		userEntity.setUserID(utils.generateUserID(64));
		userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));

		UserEntity savedUser = userRepository.save(userEntity);

		UserDTO result = new UserDTO();

		BeanUtils.copyProperties(savedUser, result);

		return result;
	}

	@Override
	public UserDTO getUserByEmail(String email) throws UsernameNotFoundException {
		UserEntity userEntity = userRepository.findByEmail(email);

		if (userEntity == null)
			throw new UsernameNotFoundException(email);

		UserDTO result = new UserDTO();

		BeanUtils.copyProperties(userEntity, result);

		return result;
	}

	@Override
	public UserDTO getUserByUserID(String id) throws UsernameNotFoundException {
		UserEntity userEntity = userRepository.findByUserID(id);

		if (userEntity == null)
			throw new UsernameNotFoundException(id);

		UserDTO result = new UserDTO();

		BeanUtils.copyProperties(userEntity, result);

		return result;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserEntity userEntity = userRepository.findByEmail(email);

		if (userEntity == null)
			throw new UsernameNotFoundException(email);

		return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());
	}
}