package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

import model.BookSale;
import model.UserLogin;

public class UserLoginDAO {

	private Connection connection;

	// Function that pulls user login information from the database
	public UserLogin getUserbyUsername(String username) {
		connection = DBManager.getConnection();
		PreparedStatement sqlCommand = null;

		ResultSet resultSet = null;
		UserLogin user = null;

		try {
			sqlCommand = connection.prepareStatement("SELECT * FROM T_PBL_USERLOGIN WHERE NM_USERNAME = ?");

			// Assigning values ​​to the parameters indicated when preparing the declaration
			sqlCommand.setString(1, username);

			// Executing the SELECT statement
			resultSet = sqlCommand.executeQuery();

			// Getting username and password from resultSet
			if (resultSet.next()) {
				user = new UserLogin();
				user.setUsername(resultSet.getString("NM_USERNAME"));
				user.setPassword(resultSet.getString("DS_PASSWORD"));
				user.setIsAdmin(resultSet.getString("IS_ADMIN"));
				user.setTemporaryPassword(resultSet.getInt("TEMP_PASSWORD"));
			}

			// Closing the connection and command to release resources
			connection.close();
			sqlCommand.close();
			resultSet.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	// Function that creates a user
	public void createUser(UserLogin user) {

		connection = DBManager.getConnection();
		PreparedStatement sqlCommand = null;

		try {
			sqlCommand = connection.prepareStatement(
					"INSERT INTO T_PBL_USERLOGIN (ID_USERLOGIN, NM_USERNAME, DS_PASSWORD) VALUES (SQ_USERLOGIN.NEXTVAL, ?, ?)");

			// Assigning values ​​to the parameters indicated when preparing the declaration
			sqlCommand.setString(1, user.getUsername());
			String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
			sqlCommand.setString(2, hashedPassword);

			// Executing the INSERT statement
			sqlCommand.executeUpdate();

			// Closing the connection and command to release resources
			connection.close();
			sqlCommand.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Function that checks if the username is already registered
	public boolean usernameExists(String username) {

		connection = DBManager.getConnection();
		PreparedStatement sqlCommand = null;
		ResultSet resultSet = null;

		try {
			sqlCommand = connection.prepareStatement("SELECT COUNT(*) FROM T_PBL_USERLOGIN WHERE NM_USERNAME = ?");

			// Assigning values ​​to the parameters indicated when preparing the declaration
			sqlCommand.setString(1, username);

			// Runs query for already existing username and gets the result
			resultSet = sqlCommand.executeQuery();
			if (resultSet.next()) {
				int count = resultSet.getInt(1);
				return count > 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	// Function that creates a user with a random temporary password
	public void createUserWithTemporaryPassword(UserLogin user, String selectedOption) {

		connection = DBManager.getConnection();
		PreparedStatement sqlCommand = null;

		try {
			sqlCommand = connection.prepareStatement(
					"INSERT INTO T_PBL_USERLOGIN (ID_USERLOGIN, NM_USERNAME, DS_PASSWORD, IS_ADMIN, TEMP_PASSWORD) VALUES (SQ_USERLOGIN.NEXTVAL, ?, ?, ?, 1)");

			// Assigning values ​​to the parameters indicated when preparing the declaration
			sqlCommand.setString(1, user.getUsername());
			String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
			sqlCommand.setString(2, hashedPassword);
			sqlCommand.setString(3, selectedOption.equals("Administrador") ? "Y" : "N");

			// Executing the INSERT statement
			sqlCommand.executeUpdate();

			// Closing the connection and command to release resources
			connection.close();
			sqlCommand.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Function that generates a random temporary password
	public String generateRandomPassword() {
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		StringBuilder password = new StringBuilder();
		for (int i = 0; i < 8; i++) {
			int index = (int) (Math.random() * characters.length());
			password.append(characters.charAt(index));
		}
		return password.toString();
	}

	// Function that updates the user's password
	public void updateUserPassword(UserLogin user) {
		connection = DBManager.getConnection();
		PreparedStatement sqlCommand = null;

		try {
			sqlCommand = connection.prepareStatement(
					"UPDATE T_PBL_USERLOGIN SET DS_PASSWORD = ?, TEMP_PASSWORD = ? WHERE NM_USERNAME = ?");

			// Hash the new password and set it
			String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
			sqlCommand.setString(1, hashedPassword);

			/// Sets TEMP_PASSWORD to 0 to indicate that it is no longer a password
			/// temporary
			sqlCommand.setInt(2, 0);
			sqlCommand.setString(3, user.getUsername());

			// Executes the update statement
			sqlCommand.executeUpdate();

			// Closes the connection and statement to release resources
			connection.close();
			sqlCommand.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Function that returns all users in the database
	public List<UserLogin> selectAll() {
		List<UserLogin> users = new ArrayList<>();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;

		try {
			connection = DBManager.getConnection();
			statement = connection
					.prepareStatement("SELECT ID_USERLOGIN, NM_USERNAME, TEMP_PASSWORD, IS_ADMIN FROM T_PBL_USERLOGIN");
			rs = statement.executeQuery();

			while (rs.next()) {
				UserLogin user = new UserLogin();
				user.setId(rs.getInt("ID_USERLOGIN"));
				user.setUsername(rs.getString("NM_USERNAME"));
				user.setTemporaryPassword(rs.getInt("TEMP_PASSWORD"));
				user.setIsAdmin(rs.getString("IS_ADMIN"));

				users.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return users;
	}

	// Function that deletes a user from the database
	public void delete(UserLogin user) {
		connection = DBManager.getConnection();
		PreparedStatement sqlCommand = null;

		try {
			sqlCommand = connection.prepareStatement("DELETE FROM T_PBL_USERLOGIN WHERE ID_USERLOGIN = ?");
			sqlCommand.setInt(1, user.getId());

			sqlCommand.executeUpdate();

			connection.close();
			sqlCommand.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Function that returns a user's information
	public UserLogin selectById(int userId) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		UserLogin user = null;

		try {
			connection = DBManager.getConnection();
			statement = connection.prepareStatement("SELECT * FROM T_PBL_USERLOGIN WHERE ID_USERLOGIN = ?");
			statement.setInt(1, userId);
			rs = statement.executeQuery();

			if (rs.next()) {
				user = new UserLogin();
				user.setId(rs.getInt("ID_USERLOGIN"));
				user.setUsername(rs.getString("NM_USERNAME"));
				user.setTemporaryPassword(rs.getInt("TEMP_PASSWORD"));
				user.setIsAdmin(rs.getString("IS_ADMIN"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return user;
	}
}