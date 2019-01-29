package ua.khpi.test.finalTask.entity.enums;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.khpi.test.finalTask.entity.Card;
import ua.khpi.test.finalTask.web.command.user.SortPaymentsCommand;

public enum Fee {
	GOLD(2), SILVER(1), DEFAULT(0);
	private int percentage;
	private static final Logger LOG = LogManager.getLogger(Fee.class);

	private Fee(int percentage) {
		this.percentage = percentage;
	}

	public static Fee getFee(Card card) {
		int feeId = card.getCardFeeid();
		LOG.info(feeId);
		return Fee.values()[feeId-1];
	}

	public String getFee() {
		return name().toLowerCase();
	}

	public int getPercentage() {
		return percentage;
	}

}
