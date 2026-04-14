package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Attendee.User;
import com.example.demo.repo.UserRepository;

@Service
public class UserServicEImpl implements UserServicee{
//
//	@Override
//	public boolean checkEmail(String email) {
//
//		return userrepo.existsByEmail(email);
//	}

	@Autowired
	UserRepository userrepo; 
	
	

	

	@Override
	public User createUser(User user) {
		return userrepo.save(user);
	}

	@Override
	public void saveUser(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public User findByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

}
