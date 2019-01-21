package ua.khpi.test.finalTask.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "accounts")
public class Account extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	@Column(name = "name")
	private String name;
	@Column(name = "balance")
	private BigDecimal balance;
	@Column(name = "account_status_id")
	private int accountStatusId;
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="cards_id", nullable=false)
	private Card cards;

	public Account(String accName, BigDecimal balance ,int accountStatusId, int cardsId) {
		this.name = accName;
		this.balance=balance;
		cards = new Card();
		this.accountStatusId=accountStatusId;
		this.cards.setId(cardsId);
	}
	
public Account() {}
	
	
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

	


	/**
	 * @return the cards
	 */
	public Card getCards() {
		return cards;
	}



	


	/**
	 * @param cards the cards to set
	 */
	public void setCards(Card cards) {
		this.cards = cards;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Account [name=" + name + ", balance=" + balance + ", accountStatusId=" + accountStatusId + ", cards="
				 + "]";
	}

	
}
