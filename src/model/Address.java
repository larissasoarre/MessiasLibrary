package model;

public class Address {
	private Integer idAddress;
	private String street;
	private Integer houseNumber;
	private Integer zipcode;
	private String district;
	private String city;
	private String state;
	
	public Address() {
		
	}
	
	public Address(String street, Integer houseNumber, Integer zipcode, String district, String city, String state) {
		this.street = street;
		this.houseNumber = houseNumber;
		this.zipcode = zipcode;
		this.district = district;
		this.city = city;
		this.state = state;
	}
	
	public Integer getIdAddress() {
		return idAddress;
	}
	
	public void setIdAddress(Integer id) {
		this.idAddress = id;
	}
	
	public String getStreet() {
		return street;
	}
	
	public void setStreet(String street) {
		this.street = street;
	}
	
	public Integer getHouseNumber() {
		return houseNumber;
	}
	
	public void setHouseNumber(Integer number) {
		this.houseNumber = number;
	}
	
	public Integer getZipcode() {
		return zipcode;
	}
	
	public void setZipcode(Integer zipcode) {
		this.zipcode = zipcode;
	}
	
	public String getDistrict() {
		return district;
	}
	
	public void setDistrict(String district) {
		this.district = district;
	}
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
}
