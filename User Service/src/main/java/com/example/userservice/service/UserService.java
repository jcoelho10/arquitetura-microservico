package com.example.userservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.example.userservice.model.User;
import com.example.userservice.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RedisTemplate<String, User> redisTemplate;

	public List<User> findAllUsers() {
		List<User> users = redisTemplate.opsForList().range("users", 0, -1);
		if (users == null || users.isEmpty()) {
			users = userRepository.findAll();
			redisTemplate.opsForList().rightPushAll("users", users);
		}
		return users;
	}

}
