package ua.khpi.test.finalTask.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;


@Entity
public class Card extends AbstractEntity {
	
	
	private BigDecimal cardNumber;
	private Date expDate;
	private String cardName;
	private int cardFeeid;

	public Card(BigDecimal cardNumber, Date expDate, String cardName, int cardFeeid) {
		this.cardNumber = cardNumber;
		this.expDate = expDate;
		this.cardName = cardName;
		this.cardFeeid = cardFeeid;
	}

	public BigDecimal getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(BigDecimal cardNumber) {
		this.cardNumber = cardNumber;
	}

	public Date getExpDate() {
		return expDate;
	}

	public void setExpDate(Date expDate) {
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
