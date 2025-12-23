package org.example.repository.entities;

import java.util.Objects;

public class AccountsEntity {
	private Integer id;
	private String username;
	private String password;

	public AccountsEntity() {}

	public AccountsEntity(Integer id, String username, String password) {
		this.id = id;
		this.username = username;
		this.password = password;
	}

	public Integer getId() {return id;}

	public void setId(Integer id) {this.id = id;}

	public String getUsername() {return username;}

	public void setUsername(String username) {this.username = username;}

	public String getPassword() {return password;}

	public void setPassword(String password) {this.password = password;}

	@Override
	public String toString() {
		return "AccountsEntity{" +
				"id=" + id +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		AccountsEntity that = (AccountsEntity) o;
		return Objects.equals(id, that.id) && Objects.equals(username, that.username) && Objects.equals(password, that.password);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, username, password);
	}
}
