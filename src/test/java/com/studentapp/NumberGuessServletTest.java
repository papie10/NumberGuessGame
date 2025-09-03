package com.studentapp;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NumberGuessServlet extends HttpServlet {
    private int targetNumber;

    @Override
    public void init() throws ServletException {
        // Initialize with a random number between 1–100
        targetNumber = (int) (Math.random() * 100 + 1);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        response.getWriter().println("<h1>Guess a number between 1 and 100!</h1>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int guess = Integer.parseInt(request.getParameter("guess"));

        response.setContentType("text/html");
        if (guess < targetNumber) {
            response.getWriter().println("<h1>Too low!</h1>");
        } else if (guess > targetNumber) {
            response.getWriter().println("<h1>Too high!</h1>");
        } else {
            response.getWriter().println("<h1>Correct! The number was " + targetNumber + ".</h1>");
        }
    }

    // ✅ Add this helper method for unit testing
    public void setTargetNumberForTest(int number) {
        this.targetNumber = number;
    }

    // Optional getter if needed in tests
    public int getTargetNumber() {
        return targetNumber;
    }
}
