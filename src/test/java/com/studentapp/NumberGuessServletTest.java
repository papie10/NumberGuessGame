package com.studentapp;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;

public class NumberGuessServletTest {

    private NumberGuessServlet servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private StringWriter responseWriter;

    @Before
    public void setUp() throws Exception {
        servlet = new NumberGuessServlet();
        servlet.init();

        request = Mockito.mock(HttpServletRequest.class);
        response = Mockito.mock(HttpServletResponse.class);

        responseWriter = new StringWriter();
        Mockito.when(response.getWriter()).thenReturn(new PrintWriter(responseWriter));
    }

    @Test
    public void testGuessTooLow() throws Exception {
        // We force the target number to 50 for testing
        servlet.setTargetNumberForTest(50);

        Mockito.when(request.getParameter("guess")).thenReturn("10");
        servlet.doPost(request, response);

        assertTrue(responseWriter.toString().contains("Your guess is too low"));
    }

    @Test
    public void testGuessTooHigh() throws Exception {
        servlet.setTargetNumberForTest(50);

        Mockito.when(request.getParameter("guess")).th