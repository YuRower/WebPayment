package ua.khpi.test.finalTask.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@NoArgsConstructor
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
	
	@Override
	public String toString() {
		return "Account [name=" + name + ", balance=" + balance + ", accountStatusId=" + accountStatusId + ", cards="
				 + "]";
	}

	
}
