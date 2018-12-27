package com.hetfdex.webservices.mobileapp.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.hetfdex.webservices.mobileapp.io.entity.UserEntity;
import com.hetfdex.webservices.mobileapp.io.repositories.UserRepository;
import com.hetfdex.webservices.mobileapp.service.UserService;
import com.hetfdex.webservices.mobileapp.shared.dto.AddressDTO;
import com.hetfdex.webservices.mobileapp.shared.dto.UserDTO;
import com.hetfdex.webservices.mobileapp.ui.controller.ui.model.response.ErrorMessages;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	UserRepository userRepository;

	@Override
	public UserDTO createUser(UserDTO user) {
		ModelMapper modelMapper = new ModelMapper();
		
		for (int i = 0; i < user.getAddresses().size(); i++) {
			AddressDTO address = user.getAddresses().get(i);
			
			address.setUserDTO(user);
			address.setAddressID(UUID.randomUUID().toString());
			
			user.getAddresses().set(i, address);
		}
		
		UserEntity userEntity = modelMapper.map(user, UserEntity.class);

		userEntity.setUserID(UUID.randomUUID().toString());
		userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));

		UserEntity savedUser = userRepository.save(userEntity);

		UserDTO result = modelMapper.map(savedUser, UserDTO.class);

		return result;
	}

	@Override
	public UserDTO updateUser(String id, UserDTO user) {
		UserEntity userEntity = userRepository.findByUserID(id);

		if (userEntity == null)
			throw new UsernameNotFoundException(ErrorMessages.RECORD_NOT_FOUND.getErrorMessage());

		boolean save = false;

		if (!user.getFirstName().isEmpty() && user.getFirstName() != userEntity.getFirstName()) {
			userEntity.setFirstName(user.getFirstName());

			save = true;
		}

		if (!user.getLastName().isEmpty() && user.getLastName() != userEntity.getLastName()) {
			userEntity.setLastName(user.getLastName());

			save = true;
		}

		UserDTO result = new UserDTO();
		
		ModelMapper modelMapper = new ModelMapper();

		if (save) {
			UserEntity savedUser = userRepository.save(userEntity);
			
			result = modelMapper.map(savedUser, UserDTO.class);
		} else {
			result = modelMapper.map(userEntity, UserDTO.class);
		}
		return result;
	}

	@Override
	public boolean deleteUser(String id) {
		UserEntity userEntity = userRepository.findByUserID(id);

		if (userEntity == null)
			throw new UsernameNotFoundException(ErrorMessages.RECORD_NOT_FOUND.getErrorMessage());

		userRepository.delete(userEntity);

		return true;
	}

	@Override
	public List<UserDTO> getUsers(int page, int limit) {
		List<UserDTO> results = new ArrayList<>();

		if (page > 0) {
			page -= 1;
		}

		Pageable pageableRequest = PageRequest.of(page, limit);

		Page<UserEntity> usersPage = userRepository.findAll(pageableRequest);

		List<UserEntity> users = usersPage.getContent();

		for (UserEntity user : users) {
			UserDTO result = new UserDTO();

			BeanUtils.copyProperties(user, result);

			results.add(result);
		}
		return results;
	}

	@Override
	public UserDTO getUserByEmail(String email) throws UsernameNotFoundException {
		UserEntity userEntity = userRepository.findByEmail(email);

		if (userEntity == null)
			throw new UsernameNotFoundException(ErrorMessages.RECORD_NOT_FOUND.getErrorMessage());

		UserDTO result = new UserDTO();

		BeanUtils.copyProperties(userEntity, result);

		return result;
	}

	@Override
	public UserDTO getUserByUserID(String id) throws UsernameNotFoundException {
		UserEntity userEntity = userRepository.findByUserID(id);

		if (userEntity == null)
			throw new UsernameNotFoundException(ErrorMessages.RECORD_NOT_FOUND.getErrorMessage());

		UserDTO result = new UserDTO();

		BeanUtils.copyProperties(userEntity, result);

		return result;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserEntity userEntity = userRepository.findByEmail(email);

		if (userEntity == null)
			throw new UsernameNotFoundException(ErrorMessages.RECORD_NOT_FOUND.getErrorMessage());

		return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());
	}
}