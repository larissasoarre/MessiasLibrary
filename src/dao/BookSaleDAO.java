package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.BookSale;

public class BookSaleDAO {

	private Connection connection;

	// Function that deletes a book from the database
	public void delete(BookSale book) {
		connection = DBManager.getConnection();
		PreparedStatement sqlCommand = null;

		try {
			sqlCommand = connection.prepareStatement("DELETE FROM T_PBL_ORDER_BOOK WHERE ID_ORDER_BK = ?");
			sqlCommand.setInt(1, book.getId());

			sqlCommand.executeUpdate();

			connection.close();
			sqlCommand.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Function that updates information in a book
	public void update(BookSale bookSale) {

		connection = DBManager.getConnection();
		PreparedStatement sqlCommand = null;

		try {
			sqlCommand = connection.prepareStatement(
					"UPDATE T_PBL_ORDER_BOOK SET CD_ORDER_ISBN = ?, NM_ORDER_TITLE = ?, NM_AUTHOR = ?, DS_GENRE = ?, DS_PUBLISHER = ?, DS_EDITION = ?, VL_SALE = ?, QT_ORDER_AVAILABLE = ? WHERE ID_ORDER_BK = ?");

			sqlCommand.setLong(1, bookSale.getIsbn());
			sqlCommand.setString(2, bookSale.getTitle());
			sqlCommand.setString(3, bookSale.getAuthor());
			sqlCommand.setString(4, bookSale.getGenre());
			sqlCommand.setString(5, bookSale.getPublisher());
			if (bookSale.getEdition() != null) {
				sqlCommand.setInt(6, bookSale.getEdition());
			} else {
				sqlCommand.setNull(6, java.sql.Types.INTEGER);
			}
			sqlCommand.setDouble(7, bookSale.getSalePrice());
			sqlCommand.setInt(8, bookSale.getQuantity());
			sqlCommand.setLong(9, bookSale.getId());

			sqlCommand.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (sqlCommand != null) {
					sqlCommand.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// Function that adds books to inventory
	public void addBook(BookSale bookSale) {

		connection = DBManager.getConnection();
		PreparedStatement sqlCommand = null;

		try {
			sqlCommand = connection.prepareStatement(
					"INSERT INTO T_PBL_ORDER_BOOK (ID_ORDER_BK, CD_ORDER_ISBN, NM_ORDER_TITLE, NM_AUTHOR, DS_GENRE, DS_PUBLISHER, DS_EDITION, VL_SALE, QT_ORDER_AVAILABLE) VALUES (SQ_ORDER_BOOK.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?)");

			sqlCommand.setLong(1, bookSale.getIsbn());
			sqlCommand.setString(2, bookSale.getTitle());
			sqlCommand.setString(3, bookSale.getAuthor());
			sqlCommand.setString(4, bookSale.getGenre());
			sqlCommand.setString(5, bookSale.getPublisher());
			if (bookSale.getEdition() != null) {
				sqlCommand.setInt(6, bookSale.getEdition());
			} else {
				sqlCommand.setNull(6, java.sql.Types.INTEGER);
			}
			sqlCommand.setDouble(7, bookSale.getSalePrice());
			sqlCommand.setInt(8, bookSale.getQuantity());

			sqlCommand.executeUpdate();

			connection.close();
			sqlCommand.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Function that returns all books in the database
	public List<BookSale> selectAll() {
		List<BookSale> books = new ArrayList<>();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;

		try {
			connection = DBManager.getConnection();
			statement = connection.prepareStatement("SELECT * FROM T_PBL_ORDER_BOOK");
			rs = statement.executeQuery();

			while (rs.next()) {
				BookSale book = new BookSale();
				book.setId(rs.getInt("ID_ORDER_BK"));
				book.setIsbn(rs.getLong("CD_ORDER_ISBN"));
				book.setTitle(rs.getString("NM_ORDER_TITLE"));
				book.setAuthor(rs.getString("NM_AUTHOR"));
				book.setGenre(rs.getString("DS_GENRE"));
				book.setPublisher(rs.getString("DS_PUBLISHER"));
				book.setEdition(rs.getInt("DS_EDITION"));
				book.setSalePrice(rs.getDouble("VL_SALE"));
				book.setQuantity(rs.getInt("QT_ORDER_AVAILABLE"));

				books.add(book);
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

		return books;
	}

	// Function that returns information from a book
	public BookSale selectById(int bookId) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		BookSale book = null;

		try {
			connection = DBManager.getConnection();
			statement = connection.prepareStatement("SELECT * FROM T_PBL_ORDER_BOOK WHERE ID_ORDER_BK = ?");
			statement.setInt(1, bookId);
			rs = statement.executeQuery();

			if (rs.next()) {
				book = new BookSale();
				book.setId(rs.getInt("ID_ORDER_BK"));
				book.setIsbn(rs.getLong("CD_ORDER_ISBN"));
				book.setTitle(rs.getString("NM_ORDER_TITLE"));
				book.setAuthor(rs.getString("NM_AUTHOR"));
				book.setGenre(rs.getString("DS_GENRE"));
				book.setPublisher(rs.getString("DS_PUBLISHER"));
				book.setEdition(rs.getInt("DS_EDITION"));
				book.setSalePrice(rs.getDouble("VL_SALE"));
				book.setQuantity(rs.getInt("QT_ORDER_AVAILABLE"));
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

		return book;
	}
}