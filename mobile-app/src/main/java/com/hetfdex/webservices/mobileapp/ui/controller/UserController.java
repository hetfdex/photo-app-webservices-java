package com.hetfdex.webservices.mobileapp.ui.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hetfdex.webservices.mobileapp.service.UserService;
import com.hetfdex.webservices.mobileapp.shared.dto.UserDTO;
import com.hetfdex.webservices.mobileapp.ui.controller.ui.model.request.UserDetailsRequestModel;
import com.hetfdex.webservices.mobileapp.ui.controller.ui.model.response.UserRest;

@RestController
@RequestMapping("users")
public class UserController {
	@Autowired
	UserService userService;
	
	@GetMapping(path="/{id}")
	public UserRest getUser(@PathVariable String id) {
		UserRest result = new UserRest();
		
		UserDTO userDTO = userService.getUserByUserID(id);
		
		BeanUtils.copyProperties(userDTO, result);
		
		return result;
	}
	
	@PostMapping
	public UserRest postUser(@RequestBody UserDetailsRequestModel userDetails) {
		UserRest result = new UserRest();
		
		UserDTO userDTO = new UserDTO();
		
		BeanUtils.copyProperties(userDetails, userDTO);
		
		UserDTO createdUser = userService.createUser(userDTO);
		
		BeanUtils.copyProperties(createdUser, result);
		
		return result;
	}
	
	@PutMapping
	public String putUser() {
		return "putUser";
	}
	
	@DeleteMapping
	public String deleteUser() {
		return "deleteUser";
	}
}