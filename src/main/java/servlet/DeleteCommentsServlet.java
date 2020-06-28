package servlet;

import lombok.SneakyThrows;
import manager.CommentManager;
import manager.TaskManager;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(urlPatterns = "/deleteComment")
public class DeleteCommentsServlet extends HttpServlet {
    CommentManager commentManager = new CommentManager();
    TaskManager taskManager = new TaskManager();


    @SneakyThrows
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int taskId= Integer.parseInt(req.getParameter("taskId"));
        int comId = Integer.parseInt(req.getParameter("id"));
        commentManager.deleteComment(comId);
        resp.sendRedirect("/taskPage?id=" + taskId);
    }
}
