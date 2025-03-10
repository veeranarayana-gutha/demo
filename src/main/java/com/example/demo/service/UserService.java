package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.model.User;

public class UserService {
	
	List<User> users = new ArrayList<>();
	
	
	public List<User> getUsers(){
		return users;		
	}
	
	public User saveUser(User user) {
		users.add(user);
		return user;
	}
	
	public void deleteUser(User user) {
		users.remove(user);
	}
	

}
