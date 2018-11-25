package ua.khpi.test.finalTask.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.khpi.test.finalTask.exception.ApplicationException;
import ua.khpi.test.finalTask.web.RequestProcessorInfo;


public abstract class Command {

	public abstract RequestProcessorInfo execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			ApplicationException;

	@Override
	public final String toString() {
		return getClass().getSimpleName();
	}
}