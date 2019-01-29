package ua.khpi.test.finalTask.entity.enums;

import ua.khpi.test.finalTask.entity.Payment;

public enum PaymentType {
	CARD_TO_CARD, REPLENISH;

	public static PaymentType getType(Payment payment) {
		int typeId = payment.getPaymentTypeId();
		return PaymentType.values()[typeId];
	}

	public String getName() {
		return name().toLowerCase();
	}
}
