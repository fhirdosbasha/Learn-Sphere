package com.learnSphere.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.learnSphere.entity.Users;
import com.learnSphere.repository.UserRepository;

@Service
public class UserServiceImplementation implements UserService{
	
	@Autowired
	UserRepository repo;
	
	@Override
	public String addUser(Users user) {
		repo.save(user);
		return "User added successfully!";
	}

	@Override
	public boolean emailExist(String email) {
		return repo.existsByEmail(email);
	}

	@Override
	public boolean validate(String email, String password) {
		boolean emailExist = repo.existsByEmail(email);
		
		if(emailExist == true) {
			Users user = repo.findByEmail(email);
			String db_password = user.getPassword();
			if(db_password.equals(password)) {
				return true;
			}
			else {
				return false;
			}
		}
		return false;
	}

	@Override
	public String getUserRole(String email) {
		Users user = repo.findByEmail(email);
		return user.getRole();
	}

	@Override
	public Users getUser(String email) {
		return repo.findByEmail(email);
	}

	@Override
	public String updateUser(Users user) {
		repo.save(user);
		return "Student updated successfully!";
	}
	
	

	
}
