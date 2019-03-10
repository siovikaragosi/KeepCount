package com.keepcount.service.user;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.keepcount.dao.user.UserHibernation;
import com.keepcount.model.user.User;

@Service
public class UserService {

	public int createNewUser(User user, String businessId) {
		int result = 0;
		result = UserHibernation.createNewUser(user, businessId);
		return result;
	}

	public int updateUser(BigDecimal id, String businessId, User user) {
		int result = 0;
		UserHibernation.updateUser(id, businessId, user);
		return result;
	}

	public int deleteUser(BigDecimal id, String businessId) {
		int result = 0;
		result = UserHibernation.deleteUser(id, businessId);
		return result;
	}

	public List<User> findAllUsersPerBusinessByEmail(String email, String businessId) {
		return UserHibernation.findAllUsersPerBusinessByEmail(email, businessId);
	}

	public User findUserById(BigDecimal id, String businessId) {
		return UserHibernation.findUserById(id, businessId);
	}

	public List<User> findAllUserPerBusiness(String businessId) {
		return UserHibernation.findAllUserPerBusiness(businessId);
	}

	public List<User> findAllUsersPerBusinessUponRefreshing(String businessId) {
		return UserHibernation.findAllUsersPerBusinessUponRefreshing(businessId);
	}

}
