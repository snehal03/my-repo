package com.example.bootmongo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.bootmongo.model.User;
import com.example.bootmongo.repo.UserRepo;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepo.findUserByEmail(email);

		if (user == null)
			throw new UsernameNotFoundException(String.format("No user exists with email : %s", email));
		else
			return JwtUserFactory.create(user);
	}

	public User getUserByEmail(String email) {
		User user = userRepo.findUserByEmail(email);

		if (user == null) {
			throw new UsernameNotFoundException(String.format("No user found with email '%s'.", email));
		} else {
			return user;
		}
	}

	public Boolean changePassword(String userId, String password) {
		String pass = bCryptPasswordEncoder.encode(password);
		User user = userRepo.findOne(userId);
		user.setPassword(pass);

		User response = userRepo.save(user);
		if ((response instanceof User)) {
			return true;
		}
		return false;

	}

	/*
	  
	  public User getUserByEmail(String email) throws
	  UsernameNotFoundException, WazooException { User user =
	  userRepo.findUserByEmail(email);
	  
	 if (user == null) { throw new
	  UsernameNotFoundException(String.format("No user found with email '%s'.",
	  email)); } else { if
	  (user.getStatus().compareTo(UserStatus.ACTIVE.getStatusName()) != 0)
	  throw new WazooException(201, "Please activate your account = " +
	  waasAppUtils.generateActivationLink(user)); else return user; } }
	  
	  
	  public User getUserByUserId(String id) throws UsernameNotFoundException {
	  User user = userRepo.findOne(id);
	  
	  if (user == null) { throw new
	  UsernameNotFoundException(String.format("No user found with id '%s'.",
	  id)); } else { return user; } }
	  
	  public Boolean activateUser(String userId) { // Find user User user =
	  userRepo.findOne(userId);
	  user.setStatus(UserStatus.ACTIVE.getStatusName()); // Activate User User
	 response = userRepo.save(user); if ((response instanceof User) &&
	 response.getStatus().equals(UserStatus.ACTIVE.getStatusName())) { return
	  true; } return false; }
	  
	  public Boolean changePassword(String userId, String password, String
	  oldPassword) {
	  
	  
	 User user = userRepo.findOne(userId); if (oldPassword != null &&
	  !(StringUtils.isEmpty(oldPassword))) { String pass =
	  bCryptPasswordEncoder.encode(oldPassword);
	  if(bCryptPasswordEncoder.matches(oldPassword,user.getProfile().
	  getPassword()) ) { user.getProfile().setPassword(password);
	 
	 User response = userRepo.save(user); if ((response instanceof User)) {
	 return true; } return false; } else { return false; } } else {
	  user.getProfile().setPassword(password);
	
	  User response = userRepo.save(user); if ((response instanceof User)) {
	 return true; } return false; } }
	 */
}
