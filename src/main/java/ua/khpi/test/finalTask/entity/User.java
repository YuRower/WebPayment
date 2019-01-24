package ua.khpi.test.finalTask.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@EqualsAndHashCode(callSuper=false)
@Data
@NoArgsConstructor
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


}
