package ua.khpi.test.finalTask.entity;

import java.math.BigDecimal;
import java.util.Date;

public class Payment extends AbstractEntity{

	
	private Date date;
	private BigDecimal moneyAmount;
	private int accountIdFrom;
	private int accountIdTo;
	private int paymentTypeId;

	
	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public BigDecimal getMoneyAmount() {
		return this.moneyAmount;
	}

	public void setMoneyAmount(BigDecimal moneyAmount) {
		this.moneyAmount = moneyAmount;
	}

	public int getAccountIdFrom() {
		return this.accountIdFrom;
	}

	public void setAccountIdFrom(int accountIdFrom) {
		this.accountIdFrom = accountIdFrom;
	}

	public int getAccountIdTo() {
		return this.accountIdTo;
	}

	public void setAccountIdTo(int accountIdTo) {
		this.accountIdTo = accountIdTo;
	}

	public int getPaymentTypeId() {
		return this.paymentTypeId;
	}


	public void setPaymentTypeId(int paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
	}

	@Override
	public String toString() {
		return "Payment [id=" + getId() + ", date=" + date + ", moneyAmount=" + moneyAmount + ", accountIdFrom="
				+ accountIdFrom + ", accountIdTo=" + accountIdTo + ", paymentTypeId=" + paymentTypeId + "]";
	}
}
