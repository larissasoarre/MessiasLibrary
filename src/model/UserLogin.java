package model;

public class UserLogin {
	
	private Integer id;
	private String username;
	private String password;
	private String isAdmin;
	private int temporaryPassword;
	
	public UserLogin() {
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
        this.password = password;
	}

	public String getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(String isAdmin) {
		this.isAdmin = isAdmin;
	}

	public int getTemporaryPassword() {
		return temporaryPassword;
	}

	public void setTemporaryPassword(int temporaryPassword) {
		this.temporaryPassword = temporaryPassword;
	}
	
	// Function checks if it is a temporary password
	public boolean isTemporaryPassword() {
	    return temporaryPassword == 1;
	}

}
