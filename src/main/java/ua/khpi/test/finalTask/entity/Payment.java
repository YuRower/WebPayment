package ua.khpi.test.finalTask.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class Payment extends AbstractEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Date date;
	private BigDecimal moneyAmount;
	private int accountIdFrom;
	private int accountIdTo;
	private int paymentTypeId;

	
	
	
}
