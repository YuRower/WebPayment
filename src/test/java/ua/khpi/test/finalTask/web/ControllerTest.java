package ua.khpi.test.finalTask.web;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.*;
@RunWith(Suite.class)
@SuiteClasses({})
public class ControllerTest {
    private final static String path = "/WEB-INF/login.jsp";
    
  @BeforeClass
  public static void start() {
	 org.jboss.logging.Logger.getLogger(ControllerTest.class).debug("info");
  }
    @Test
    public void whenCallDoGetThenServletReturnIndexPage() throws ServletException, IOException {

        final Controller servlet = new Controller();

        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        when(request.getRequestDispatcher(path)).thenReturn(dispatcher); //var... args => OngoingStubbing<T> thenReturn(T value, T... values);

        servlet.doGet(request, response);

        verify(request, times(1)).getRequestDispatcher(path);
        verify(request, never()).getSession();
        verify(dispatcher).forward(request, response);
    }


}
