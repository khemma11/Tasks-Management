package servlet;

import manager.TaskManager;
import manager.UserManager;
import model.Task;
import model.User;
import model.UserType;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;


@WebServlet(urlPatterns = "/managerHome")

public class ManagerHomeServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TaskManager taskManager = new TaskManager();
        UserManager userManager = new UserManager();

        List<Task> alltasks = taskManager.getAllTasks();
        List<User> allUsers = userManager.getAllUser();
        req.setAttribute("tasks", alltasks);
        req.setAttribute("users", allUsers);
        req.getRequestDispatcher("/WEB-INF/managerHome.jsp").forward(req, resp);


    }
}












