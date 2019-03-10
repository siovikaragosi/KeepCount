package com.keepcount.dao.user;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.keepcount.model.user.User;

public class UserHibernation {

	private static List<User> usersHibernated;
	private static List<User> usersHibernatedByEmail;
	private static int createUsertable;

	// private static UserHibernation userHibernationInstance = null;

	// public static UserHibernation getUserHibernationInstance() {
	// if (userHibernationInstance == null) {
	// userHibernationInstance = new UserHibernation();
	// }
	// return userHibernationInstance;
	// }

	/**
	 * This method invokes from the UserDAO class, the checkUsertableExistence that
	 * checks if the users table for this particular business exists. If the table
	 * does not exist, it will be created, otherwise no action takes place.
	 *
	 */
	private static int createUserTable(String businessId) {
		int result = 0;
		try {
			result = UserDAO.checkUserTableExistence(businessId);
		} catch (Exception e) {
			e.printStackTrace();
		}

		setCreateUsertable(result);

		return result;
	}

	/**
	 * This method invokes the createNewUser method in the UserDAO class. It takes a
	 * User model as parameter which is to be created and saved into the database
	 *
	 */

	public static int createNewUser(User user, String businessId) {
		int result = 0;
		int tableExistence = getCreateUsertable();
		if (tableExistence == 0) {
			createUserTable(businessId);
			try {
				result = UserDAO.createNewUser(user, businessId);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				result = UserDAO.createNewUser(user, businessId);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return result;
	}

	/**
	 * This method invokes the updateUser method in the UserDAO class. It takes a
	 * User model as parameter which is to be updated and a BigDecimal id of the
	 * particular User model to be updated
	 *
	 */
	public static int updateUser(BigDecimal id, String businessId, User user) {
		int tableExistence = getCreateUsertable();
		if (tableExistence == 0) {
			createUserTable(businessId);
		}

		int result = 0;
		try {
			result = UserDAO.updateUser(id, businessId, user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * This method invokes the deleteUser method in the UserDAO class. It takes a
	 * User model as parameter which is to be deleted and a BigDecimal id of the
	 * particular User model to be deleted
	 *
	 */
	public static int deleteUser(BigDecimal id, String businessId) {
		int tableExistence = getCreateUsertable();
		if (tableExistence == 0) {
			createUserTable(businessId);
		}

		int result = 0;
		try {
			result = UserDAO.deleteUserByIdAndEmail(id, businessId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * This method invokes the findAUserPerBusinessByID method in the UserDAO class.
	 * It takes a String parameter to represent the required email. The method will
	 * retrieve this particular user whose email has been parameterized
	 *
	 */
	private static void findAllUsersPerBusinessByEmailWithin(String email, String businessId) {
		int tableExistence = getCreateUsertable();
		if (tableExistence == 0) {
			createUserTable(businessId);
		}

		List<User> users = new ArrayList<>();
		try {
			users = UserDAO.findAUserPerBusinessByID(email, businessId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		setUsersHibernatedByEmail(users);
	}

	public static List<User> findAllUsersPerBusinessByEmail(String email, String businessId) {
		int tableExistence = getCreateUsertable();
		if (tableExistence == 0) {
			createUserTable(businessId);
		}

		List<User> users = new ArrayList<>();
		users = getUsersHibernatedByEmail();
		if (users == null) {
			findAllUsersPerBusinessByEmailWithin(email, businessId);
			users = getUsersHibernatedByEmail();
			return users;
		} else {
			return users;
		}
	}

	private static void findAllUsersPerBusinessWithin(String businessId) {
		int tableExistence = getCreateUsertable();
		if (tableExistence == 0) {
			createUserTable(businessId);
		}

		List<User> users = new ArrayList<>();
		try {
			users = UserDAO.findAllUsersPerBusiness(businessId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		setUsersHibernated(users);
	}

	public static List<User> findAllUsersPerBusinessUponRefreshing(String businessId) {
		List<User> users = new ArrayList<>();
		try {
			users = UserDAO.findAllUsersPerBusiness(businessId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return users;
	}

	public static User findUserById(BigDecimal id, String businessId) {
		User user = new User();
		try {
			user = UserDAO.findUserById(id, businessId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	public static List<User> findAllUserPerBusiness(String businessId) {
		int tableExistence = getCreateUsertable();
		if (tableExistence == 0) {
			createUserTable(businessId);
		}

		List<User> users = getUsersHibernated();
		if (users == null) {
			findAllUsersPerBusinessWithin(businessId);
			users = getUsersHibernated();
			// System.out.println("hib: " + users);
			return users;
		} else {
			// System.out.println("hib: " + users);
			return users;
		}
	}

	public static int getCreateUsertable() {
		return createUsertable;
	}

	public static void setCreateUsertable(int createUsertable) {
		UserHibernation.createUsertable = createUsertable;
	}

	public static List<User> getUsersHibernated() {
		return usersHibernated;
	}

	public static void setUsersHibernated(List<User> usersHibernated) {
		UserHibernation.usersHibernated = usersHibernated;
	}

	public static List<User> getUsersHibernatedByEmail() {
		return usersHibernatedByEmail;
	}

	public static void setUsersHibernatedByEmail(List<User> usersHibernatedByEmail) {
		UserHibernation.usersHibernatedByEmail = usersHibernatedByEmail;
	}

}
