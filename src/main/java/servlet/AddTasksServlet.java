package servlet;

import lombok.SneakyThrows;
import manager.TaskManager;
import manager.UserManager;
import model.Task;
import model.TaskStatus;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

;

@WebServlet(urlPatterns = "/addTasks")
public class AddTasksServlet extends HttpServlet {
    TaskManager taskManager = new TaskManager();
    UserManager userManager = new UserManager();
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {


        String name = req.getParameter("name");
        String description = req.getParameter("description");
        String deadline = req.getParameter("deadline");
        String status = (req.getParameter("status"));
        int userId = Integer.parseInt(req.getParameter("user_id"));

        try {
            taskManager.create(Task.builder()
                    .name(name)
                    .description(description)
                    .deadline(sdf.parse(deadline))
                    .status(TaskStatus.valueOf(status))
                    .userId(userId)
                    .build());
            resp.sendRedirect("/managerHome");

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }

    }
}
