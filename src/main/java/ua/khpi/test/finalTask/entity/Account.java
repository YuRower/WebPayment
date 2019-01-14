package ua.khpi.test.finalTask.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="account")
public class Account extends AbstractEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int userId;
	private String name;
	private BigDecimal balance;
	private int accountStatusId;
	private int cardid;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "card_fee_id")
	private Card cards;

	
	public Account () {
		
	}
	/**
	 * @return the card
	 */
	public Card getCard() {
		return cards;
	}

	/**
	 * @param card the card to set
	 */
	public void setCard(Card card) {
		this.cards = card;
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getBalance() {
		return this.balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public int getAccountStatusId() {
		return this.accountStatusId;
	}

	public void setAccountStatusId(int accountStatusId) {
		this.accountStatusId = accountStatusId;
	}

	@Override
	public String toString() {
		return "Account [id=" + getId() + ", userId=" + userId + ", name=" + name + ", balance=" + balance
				+ ", accountStatusId=" + accountStatusId + "]";
	}
	/**
	 * @return the cardid
	 */
	public int getCardid() {
		return cardid;
	}
	/**
	 * @param cardid the cardid to set
	 */
	public void setCardid(int cardid) {
		this.cardid = cardid;
	}
}
