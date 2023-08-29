package com.learnSphere.services;

import com.learnSphere.entity.Users;

public interface UserService {
	
	String addUser(Users user);
	
	boolean emailExist(String email);
	
	boolean validate(String email, String password);
	
	String getUserRole(String email);
	
	Users getUser(String email);

	String updateUser(Users user);
	
}
