package model;

import java.util.Comparator;
import java.util.Date;

public class Rental extends Client {
	private Integer id;
	private Long bookISBN;
	private Date rentalDate;
	private Integer rentalDuration;
	private Date returnDate;
	private Double rentalPrice;
	private BookSale bookSale;

	public Rental() {

	}

	public Rental(String name, String email, String cpf, Long phoneNumber, String street, Integer houseNumber,
			Integer zipcode, String district, String city, String state, Integer id, BookSale bookSale, Date rentalDate,
			Integer rentalDuration, Long bookISBN, Date returnDate) {
		super(name, email, cpf, phoneNumber, street, houseNumber, zipcode, district, city, state);
		this.id = id;
		this.bookISBN = bookISBN;
		this.bookSale = bookSale;
		this.rentalDate = rentalDate;
		this.rentalDuration = rentalDuration;
		this.returnDate = returnDate;
		calculateRentalPrice();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getBookISBN() {
		return bookISBN;
	}

	public void setBookISBN(Long bookId) {
		this.bookISBN = bookId;
	}

	public Date getRentalDate() {
		return rentalDate;
	}

	public void setRentalDate(Date rentalDate) {
		this.rentalDate = rentalDate;
	}

	public Integer getRentalDuration() {
		return rentalDuration;
	}

	public void setRentalDuration(Integer rentalDuration) {
		this.rentalDuration = rentalDuration;
	}

	public Double getRentalPrice() {
		return rentalPrice;
	}

	public void setRentalPrice(Double rentalPrice) {
		this.rentalPrice = rentalPrice;
	}

	public BookSale getBookSale() {
		return bookSale;
	}

	public void setBookSale(BookSale bookSale) {
		this.bookSale = bookSale;
	}
	
	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}
	
	public void calculateRentalPrice() {
	    if (bookSale != null && rentalDuration != null && rentalDuration >= 0) {
	        double salePrice = bookSale.getSalePrice();
	        rentalPrice = salePrice * rentalDuration;
	    } else {
	        rentalPrice = 0.0;
	    }
	}
	
	public class RentalComparator implements Comparator<Rental> {
	    @Override
	    public int compare(Rental rental1, Rental rental2) {
	        // Compare the return dates for two rentals
	        return rental1.getReturnDate().compareTo(rental2.getReturnDate());
	    }
	}
}