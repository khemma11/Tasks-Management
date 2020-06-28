package servlet;

import lombok.SneakyThrows;
import manager.CommentManager;
import manager.TaskManager;
import model.Comment;
import model.Task;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@WebServlet(urlPatterns = "/taskPage")

public class TaskPageServlet extends HttpServlet {
    TaskManager taskManager = new TaskManager();
    CommentManager commentManager = new CommentManager();


    @SneakyThrows
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {


        int id = Integer.parseInt(req.getParameter("id"));
        Task tasks = taskManager.getAllTasksById(id);
        List<Comment> comments = commentManager.getAllComments();
        req.setAttribute("tasks", tasks);
        req.setAttribute("comments", comments);

        req.getRequestDispatcher("/taskPage.jsp").forward(req, resp);
    }
}



