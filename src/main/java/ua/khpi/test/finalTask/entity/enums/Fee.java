package ua.khpi.test.finalTask.entity.enums;

import ua.khpi.test.finalTask.entity.Card;

public enum Fee {
	GOLD(1), SILVER(0), DEFAULT(-1);
	private int percentage;

	private Fee(int percentage) {
		this.percentage = percentage;
	}

	public static Fee getFee(Card card) {
		int feeId = card.getCardFeeid();
		return Fee.values()[feeId];
	}

	public String getFee() {
		return name().toLowerCase();
	}

	public int getPercentage() {
		return percentage;
	}

}
