package com.example.bootmongo.serviceImpl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.bootmongo.model.Address;
import com.example.bootmongo.model.Role;
import com.example.bootmongo.model.User;
import com.example.bootmongo.repo.RoleRepo;
import com.example.bootmongo.repo.UserRepo;
import com.example.bootmongo.requestModel.UpdateUserRquest;
import com.example.bootmongo.requestModel.UserRequest;
import com.example.bootmongo.service.UserService;

@Component
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private RoleRepo roleRepo;

	@Autowired
	private MongoOperations mongoOperations;
	
	@Override
	public String addUser(UserRequest userModel) {

		User user = new User();
		user.setFirstname(userModel.getFirstname());
		user.setLastname(userModel.getLastname());
		user.setPassword(bCryptPasswordEncoder.encode(userModel.getPassword()));
		user.setEmail(userModel.getEmail());
		ArrayList<Address> addr = new ArrayList<>();
		userModel.getAddress().forEach(address -> {
			Address add = new Address();
			add.setRoute(address.getRoute());
			add.setLandmark(address.getLandmark());
			add.setCity(address.getCity());
			add.setState(address.getState());
			add.setZip(address.getZip());
			add.setCountry(address.getCountry());
			addr.add(add);
		});
		if (addr.isEmpty()) {
			return "address error";
		}
		user.setAddress(addr);

		ArrayList<Role> roles = new ArrayList<>();
		userModel.getRoles().forEach(role -> {
			Role dRole = roleRepo.findByRoleName(role.getRoleName());
			if (dRole != null) {
				Role r = new Role();
				r.setName(dRole.getName());
				r.set_id(dRole.get_id());
				roles.add(r);
			} else {
				roles.add(null);
			}

		});
		if (roles.contains(null)) {
			return "role error";
		}
		user.setRoles(roles);

		userRepo.save(user);
		return "OK";
	}

	@Override
	public String updateUser(UpdateUserRquest userModel, User user) {

		if (userModel.getFirstName() != "") {
			user.setFirstname(userModel.getFirstName());
		}
		if (userModel.getLastName() != "") {
			user.setLastname(userModel.getLastName());
		}
		ArrayList<Address> addr = new ArrayList<>();
		if (userModel.getAddress()!=null && userModel.getAddress().size()!=0) {
			userModel.getAddress().forEach(address -> {
				Address add = new Address();
				add.setRoute(address.getRoute());
				add.setLandmark(address.getLandmark());
				add.setCity(address.getCity());
				add.setState(address.getState());
				add.setZip(address.getZip());
				add.setCountry(address.getCountry());
				addr.add(add);
			});
		}

		if (!addr.isEmpty()) {
			user.setAddress(addr);
		}
		if (userModel.getRoles()!=null && userModel.getRoles().size() != 0) {
			ArrayList<Role> roles = new ArrayList<>();
			userModel.getRoles().forEach(role -> {
				Role dRole = roleRepo.findByRoleName(role.getRoleName());
				if (dRole != null) {
					Role r = new Role();
					r.setName(dRole.getName());
					r.set_id(dRole.get_id());
					roles.add(r);
				} else {
					roles.add(null);
				}

			});
			if (roles.contains(null)) {
				return "role error";
			}else	if(roles.size()!=0){
				user.setRoles(roles);
			}
		}
		userRepo.save(user);
		return "OK";
	}

	@Override
	public String deleteUser(User user) {
		mongoOperations.remove(user);
		return "OK";
	}

}
