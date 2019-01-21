package ua.khpi.test.finalTask.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User extends AbstractEntity {
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "surname")
	private String surname;
	@Column(name = "email")

	private String email;
	@Column(name = "email_verification")

	private String emailVerification;
	@Column(name = "password")

	private String password;
	@Column(name = "user_role_id")
	private int userRoleId;
	
	@Column(name = "user_status_id")
	private int userStatusId;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Card> cardList = new ArrayList<>();
	

	public User() {
	}

	public User(String name, String surname, String email, String emailVerification, String password, int userRoleId,
			int userStatusId) {
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.emailVerification = emailVerification;
		this.password = password;
		this.userRoleId = userRoleId;
		this.userStatusId = userStatusId;
	}

	public User(String name, String surname, String email, String emailVerification, String password) {
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.emailVerification = emailVerification;
		this.password = password;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return this.surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String login) {
		this.email = login;
	}

	public String getEmailVerification() {
		return emailVerification;
	}

	public void setEmailVerification(String emailVerification) {
		this.emailVerification = emailVerification;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getUserRoleId() {
		return this.userRoleId;
	}

	public void setUserRoleId(int userRoleId) {
		this.userRoleId = userRoleId;
	}

	public int getUserStatusId() {
		return userStatusId;
	}

	public void setUserStatusId(int userStatusId) {
		this.userStatusId = userStatusId;
	}

	@Override
	public String toString() {
		return "User [id=" + getId() + ", name=" + name + ", surname=" + surname + ", email=" + email + ", userRoleId="
				+ userRoleId + ", userStatusId=" + userStatusId + "]";
	}

	/**
	 * @return the cardList
	 */
	public List<Card> getCardList() {
		return cardList;
	}

	/**
	 * @param cardList the cardList to set
	 */
	public void setCardList(List<Card> cardList) {
		this.cardList = cardList;
	}

}
