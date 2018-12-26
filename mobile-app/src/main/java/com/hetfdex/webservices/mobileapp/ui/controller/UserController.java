package com.hetfdex.webservices.mobileapp.ui.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
import com.hetfdex.webservices.mobileapp.ui.controller.ui.model.response.ErrorMessages;
import com.hetfdex.webservices.mobileapp.ui.controller.ui.model.response.OperationStatusModel;
import com.hetfdex.webservices.mobileapp.ui.controller.ui.model.response.UserRest;

@RestController
@RequestMapping(value = "/users")
public class UserController {
	@Autowired
	UserService userService;

	@GetMapping(path = "/{id}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public UserRest getUser(@PathVariable String id) throws Exception {
		if (id.isEmpty())
			throw new Exception(ErrorMessages.REQUIRED_FIELD_MISSING.getErrorMessage());

		UserRest result = new UserRest();

		UserDTO userDTO = userService.getUserByUserID(id);

		BeanUtils.copyProperties(userDTO, result);

		return result;
	}

	@PostMapping(consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public UserRest setUser(@RequestBody UserDetailsRequestModel userDetails) throws Exception {
		if (userDetails.getEmail().isEmpty() || userDetails.getFirstName().isEmpty()
				|| userDetails.getLastName().isEmpty() || userDetails.getPassword().isEmpty())
			throw new Exception(ErrorMessages.REQUIRED_FIELD_MISSING.getErrorMessage());

		UserRest result = new UserRest();

		UserDTO userDTO = new UserDTO();

		BeanUtils.copyProperties(userDetails, userDTO);

		UserDTO createdUser = userService.createUser(userDTO);

		BeanUtils.copyProperties(createdUser, result);

		return result;
	}

	@PutMapping(path = "/{id}", consumes = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_XML_VALUE,
					MediaType.APPLICATION_JSON_VALUE })
	public UserRest updateUser(@PathVariable String id, @RequestBody UserDetailsRequestModel userDetails) {
		UserRest result = new UserRest();

		UserDTO userDTO = new UserDTO();

		BeanUtils.copyProperties(userDetails, userDTO);

		UserDTO updatedUser = userService.updateUser(id, userDTO);

		BeanUtils.copyProperties(updatedUser, result);

		return result;
	}

	@DeleteMapping(path = "/{id}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public OperationStatusModel deleteUser(@PathVariable String id) {
		OperationStatusModel result = new OperationStatusModel();
		
		result.setOperationName("DELETE");
		result.setOperationUserID(id);
		
		boolean success = userService.deleteUser(id);
		
		result.setOperationSuccess(success);
		
		return result;
	}

}