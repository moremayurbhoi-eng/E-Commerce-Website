package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepositery;

	@Override
	public User saveUser(User user) {
		 return  userRepositery.save(user);
	}

	@Override
	public List<User> getAllUsers() {
		return userRepositery.findAll();
	}

	@Override
	public User getUserById(Long id) {
		return userRepositery.findById(id).orElse(null);
	}

	@Override
	public User updateUser(Long id, User user) {
		
	User existingUser = userRepositery.findById(id).orElse(null);
	
	if (existingUser != null) {
		
		existingUser.setName(user.getName());
		existingUser.setEmail(user.getEmail());
		existingUser.setPassword(user.getPassword());
		existingUser.setPhone(user.getPhone());
		existingUser.setRole(user.getRole());
		
		return userRepositery.save(existingUser);
		
	}
		
		return null;
	}

	@Override
	public void deleteUser(Long id) {
		userRepositery.deleteById(id);
		
	}

}
