package servlet;

import lombok.SneakyThrows;
import manager.TaskManager;
import model.Task;
import model.User;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/userHome")
public class UserHomeServlet extends HttpServlet {
    TaskManager taskManager = new TaskManager();

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

            List<Task> tasks = taskManager.getAllTasksByUserId(user.getId());
            req.setAttribute("tasks", tasks);
            req.getRequestDispatcher("/WEB-INF/userHome.jsp").forward(req, resp);

    }
}
