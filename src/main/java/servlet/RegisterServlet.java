package servlet;

import manager.UserManager;
import model.User;
import model.UserType;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;

@WebServlet(urlPatterns = "/register")

@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 5 * 5)

public class RegisterServlet extends HttpServlet {

    private UserManager userManager = new UserManager();
private final String UPLOAD_DIR = "C:\\Users\\Aka\\IdeaProjects\\Tasks-Management\\upload";
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String name = req.getParameter("name");
        String surName = req.getParameter("surName");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String userType = req.getParameter("userType");


       User user = (User.builder()
                .name(name)
                .surName(surName)
                .email(email)
                .password(password)
                .userType(UserType.valueOf(userType))
                .build());
        for (Part part : req.getParts()) {
            if (getFileName(part)!=null) {
                String fileName = System.currentTimeMillis() + getFileName(part);
                String fullFileName = UPLOAD_DIR + File.separator + fileName;
                part.write(fullFileName);
                user.setPictureUrl(fileName);
            }
        }
         userManager.addUser(user);
        resp.sendRedirect("/managerHome");
    }
    private String getFileName(Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename"))
                return content.substring(content.indexOf("=") + 2, content.length() - 1);
        }
        return null;
    }
}






