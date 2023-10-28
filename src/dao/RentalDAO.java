package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.BookSale;
import model.Rental;

public class RentalDAO {

	private Connection connection;

	// Function that deletes a loan from the database
	public void delete(Rental rental) {
		connection = DBManager.getConnection();
		PreparedStatement sqlCommand = null;

		try {
			sqlCommand = connection.prepareStatement("DELETE FROM T_PBL_RENTAL WHERE ID_RENTAL = ?");
			sqlCommand.setInt(1, rental.getId());

			sqlCommand.executeUpdate();

			connection.close();
			sqlCommand.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Function that adds loan to the database
	public void insertRental(Rental rental) {
		connection = DBManager.getConnection();
		PreparedStatement sqlCommand = null;

		try {
			sqlCommand = connection.prepareStatement(
					"INSERT INTO T_PBL_RENTAL (ID_RENTAL, ISBN_BOOK, DT_RENTAL, DS_RENTAL_DURATION, DS_RENTAL_PRICE, NM_CLIENT, DS_EMAIL, NR_PHONE, NM_STREET, NR_HOUSE, NR_CEP, NM_DISTRIC, NM_CITY, NM_STATE, DT_RETURN) VALUES (SQ_RENTAL.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

			sqlCommand.setLong(1, rental.getBookISBN());
			sqlCommand.setDate(2, new java.sql.Date(rental.getRentalDate().getTime()));
			sqlCommand.setInt(3, rental.getRentalDuration());
			rental.calculateRentalPrice();
			sqlCommand.setDouble(4, rental.getRentalPrice());
			sqlCommand.setString(5, rental.getName());
			sqlCommand.setString(6, rental.getEmail());
			sqlCommand.setLong(7, rental.getPhoneNumber());
			sqlCommand.setString(8, rental.getStreet());
			sqlCommand.setInt(9, rental.getHouseNumber());
			sqlCommand.setInt(10, rental.getZipcode());
			sqlCommand.setString(11, rental.getDistrict());
			sqlCommand.setString(12, rental.getCity());
			sqlCommand.setString(13, rental.getState());
			sqlCommand.setDate(14, new java.sql.Date(rental.getReturnDate().getTime()));

			sqlCommand.executeUpdate();

			connection.close();
			sqlCommand.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Function that checks whether the ISBN entered by the user is valid
	public boolean isISBNValid(String isbn) {
		connection = DBManager.getConnection();
		PreparedStatement sqlCommand = null;
		ResultSet resultSet = null;
		boolean isValid = false;

		try {
			sqlCommand = connection.prepareStatement("SELECT COUNT(*) FROM T_PBL_ORDER_BOOK WHERE CD_ORDER_ISBN = ?");
			sqlCommand.setString(1, isbn);
			resultSet = sqlCommand.executeQuery();

			if (resultSet.next()) {
				int count = resultSet.getInt(1);
				isValid = count > 0; // If count > 0, ISBN exists in the database
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (sqlCommand != null) {
					sqlCommand.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return isValid;
	}

	// Function that checks the price of the book based on the ISBN
	public double getBookSalePrice(String isbn) {
		connection = DBManager.getConnection();
		PreparedStatement sqlCommand = null;
		ResultSet resultSet = null;
		double salePrice = 0.0;

		try {
			sqlCommand = connection.prepareStatement("SELECT VL_SALE FROM T_PBL_ORDER_BOOK WHERE CD_ORDER_ISBN = ?");
			sqlCommand.setString(1, isbn);
			resultSet = sqlCommand.executeQuery();

			if (resultSet.next()) {
				salePrice = resultSet.getDouble("VL_SALE");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (sqlCommand != null) {
					sqlCommand.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return salePrice;
	}

	// Function that returns all loans from the database
	public List<Rental> selectAll() {
		List<Rental> rentals = new ArrayList<>();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;

		try {
			connection = DBManager.getConnection();
			statement = connection.prepareStatement("SELECT * FROM T_PBL_RENTAL");
			rs = statement.executeQuery();

			while (rs.next()) {
				Rental rental = new Rental();
				rental.setId(rs.getInt("ID_RENTAL"));
				rental.setBookISBN(rs.getLong("ISBN_BOOK"));
				rental.setName(rs.getString("NM_CLIENT"));
				rental.setRentalDuration(rs.getInt("DS_RENTAL_DURATION"));
				rental.setRentalPrice(rs.getDouble("DS_RENTAL_PRICE"));
				rental.setReturnDate(rs.getDate("DT_RETURN"));

				rentals.add(rental);
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
		return rentals;
	}

	// Function that returns information about a loan
	public Rental selectById(int rentalId) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		Rental rental = null;

		try {
			connection = DBManager.getConnection();
			statement = connection.prepareStatement("SELECT * FROM T_PBL_RENTAL WHERE ID_RENTAL = ?");
			statement.setInt(1, rentalId);
			rs = statement.executeQuery();

			if (rs.next()) {
				rental = new Rental();
				rental.setId(rs.getInt("ID_RENTAL"));
				rental.setBookISBN(rs.getLong("ISBN_BOOK"));
				rental.setRentalDate(rs.getDate("DT_RENTAL"));
				rental.setRentalDuration(rs.getInt("DS_RENTAL_DURATION"));
				rental.setRentalPrice(rs.getDouble("DS_RENTAL_PRICE"));
				rental.setName(rs.getString("NM_CLIENT"));
				rental.setEmail(rs.getString("DS_EMAIL"));
				rental.setPhoneNumber(rs.getLong("NR_PHONE"));
				rental.setStreet(rs.getString("NM_STREET"));
				rental.setHouseNumber(rs.getInt("NR_HOUSE"));
				rental.setZipcode(rs.getInt("NR_CEP"));
				rental.setDistrict(rs.getString("NM_DISTRIC"));
				rental.setCity(rs.getString("NM_CITY"));
				rental.setState(rs.getString("NM_STATE"));
				rental.setReturnDate(rs.getDate("DT_RETURN"));
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
		return rental;
	}
}