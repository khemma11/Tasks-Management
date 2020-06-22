package servlet;

import manager.UserManager;
import model.User;
import model.UserType;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/register")
public class RegisterServlet extends HttpServlet {
    private UserManager userManager = new UserManager();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = req.getParameter("name");
        String surName = req.getParameter("surName");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String userType = req.getParameter("userType");

        userManager.addUser(User.builder()
                .name(name)
                .surName(surName)
                .email(email)
                .password(password)
                .userType(UserType.valueOf(userType))
                .build());
        resp.sendRedirect("/managerHome");
    }
}






