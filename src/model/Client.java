package model;

public class Client extends Address {
	private Integer id;
	private String name;
	private String email;
	private String cpf;
	private Long phoneNumber;
	
	public Client() {
		super();
	}

	public Client (String name, String email, String cpf, Long phoneNumber, String street, Integer houseNumber, Integer zipcode, String district, String city, String state) {
		super(street, houseNumber, zipcode, district, city, state);
		this.name = name;
		this.email = email;
		this.cpf = cpf;
		this.phoneNumber = phoneNumber;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getCpf() {
		return cpf;
	}
	
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	public Long getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(Long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	
}
