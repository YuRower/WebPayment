package ua.khpi.test.finalTask.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="cards")
public class Card extends AbstractEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return "Card [cardNumber=" + cardNumber + ", expDate=" + expDate + ", cardName=" + cardName + ", cardFeeid="
				+ cardFeeid + ", account=" + account + "]";
	}
	
	public Card() {
		
	}
	@Column (name = "card_number")
	private BigDecimal cardNumber;
	
	@Column (name = "exp_date")
	private LocalDate expDate;
	
	@Column (name = "card_name")
	private String cardName;
	@Column (name = "card_fee_id")
	private int cardFeeid;

	/**
	 * @return the account
	 */
	public List<Account> getAccount() {
		return account;
	}

	/**
	 * @param account the account to set
	 */
	public void setAccount(ArrayList<Account> account) {
		this.account = account;
	}

	@OneToMany(mappedBy = "cards", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Account> account;

	public Card(BigDecimal cardNumber, LocalDate expDate, String cardName, int cardFeeid) {
		this.cardNumber = cardNumber;
		this.expDate = expDate;
		this.cardName = cardName;
		this.cardFeeid = cardFeeid;
		this.account = new ArrayList<>();
	}

	public BigDecimal getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(BigDecimal cardNumber) {
		this.cardNumber = cardNumber;
	}

	public LocalDate getExpDate() {
		return expDate;
	}

	public void setExpDate(LocalDate expDate) {
		this.expDate = expDate;
	}

	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public int getCardFeeid() {
		return cardFeeid;
	}

	public void setCardFeeid(int cardFeeid) {
		this.cardFeeid = cardFeeid;
	}

}
