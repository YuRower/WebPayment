package ua.khpi.test.finalTask.dao;

import java.math.BigDecimal;
import java.util.List;

import ua.khpi.test.finalTask.entity.Payment;
import ua.khpi.test.finalTask.exception.ConnectionException;
import ua.khpi.test.finalTask.exception.DBException;


public interface PaymentsDAO {
	Payment getPaymentById(int paymentId)throws DBException, ConnectionException;
 	List<Payment> getPaymentsByAccount (int accountId) throws ConnectionException,DBException;
 	boolean insertReplenish(BigDecimal amount, int accountId) throws DBException, ConnectionException;
 	boolean insertRemittance(BigDecimal amount, int accountFrom, int accountTo) throws DBException,ConnectionException;
}
