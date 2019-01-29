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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name="cards")
@Getter
@Setter
@NoArgsConstructor
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
	
		
	
	@Column (name = "card_number")
	private BigDecimal cardNumber;
	
	@Column (name = "exp_date")
	private LocalDate expDate;
	
	@Column (name = "card_name")
	private String cardName;
	@Column (name = "card_fee_id")
	private int cardFeeid;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable=false)
	private User user ;
	
	
	@OneToMany(mappedBy = "cards", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Account> account= new ArrayList<>();;

	public Card(BigDecimal cardNumber, LocalDate expDate, String cardName, int cardFeeid,int userId) {
		this.cardNumber = cardNumber;
		this.expDate = expDate;
		this.cardName = cardName;
		this.cardFeeid = cardFeeid;
		user = new User();
		this.user.setId(userId);
	}


}
