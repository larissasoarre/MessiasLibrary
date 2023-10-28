package model;

public class BookSale extends Book {
	private Double salePrice;
	
	public BookSale() {
		super();
	}

	public BookSale(Integer id, Long isbn, String title, String author, String publisher, Integer edition, String genre, Integer quantity, Double salePrice) {
		super(id, isbn, title, author, publisher, edition, genre, quantity);
		this.salePrice = salePrice;
	}

	public Double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(Double salePrice) {
		this.salePrice = salePrice;
	}
}