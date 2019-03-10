package com.keepcount.dao.signup;

import org.springframework.security.crypto.bcrypt.BCrypt;

import com.keepcount.dao.login.LoginDAO;
import com.keepcount.model.login.Login;
import com.keepcount.model.signup.SignUp;

public class CryptTest {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		// String password = BCrypt.hashpw("password", BCrypt.gensalt(12));
		// System.out.println(password);
		// System.out.println(BCrypt.checkpw("password", password));

		// String t = hashPassword("123456");
		// checkPass("123456", t);
		//
		// Date sqlDate = Date.valueOf(LocalDate.now());
		// LocalDateTime dateTime = LocalDateTime.now();
		//
		// System.out.println("ldt: " + dateTime);
		//
		// System.out.println("sqld: " + sqlDate);
		//
		// Timestamp timestamp = Timestamp.valueOf(dateTime);
		//
		// System.out.println("tt: " + timestamp);
		// System.out.println("tonly: " + timestamp.getHours() + ":" +
		// timestamp.getMinutes());

		String hash = hashPassword("123456");
		// System.out.println(hash);

		SignUp signUp = new SignUp(null, "alimahmoudraage@gmail.com", "123456", hash);
//		SignUpDAO.signUpNewUser(signUp);

		// Login login = new Login("alimahmoudraage@gmail.com", "123456");
		//
		// LoginDAO.login(login);

		String email = null;
		email = LoginDAO.findLoginPasswordByEmail("alimahmoudraage@gmail.com");

		System.out.println(email);

		checkPass("123456", email);
		// checkPass("123456",
		// "$2a$10$7LTboKFz6HXebDM71qPNEux8n/8cQx1.Zi1ElKcZ4KVJqckuhz1ea");

	}

	private static String hashPassword(String plainTextPassword) {

		return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());

	}

	private static void checkPass(String plainPassword, String hashedPassword) {

		if (BCrypt.checkpw(plainPassword, hashedPassword))

			System.out.println("The password matches.");

		else

			System.out.println("The password does not match.");

	}

}
