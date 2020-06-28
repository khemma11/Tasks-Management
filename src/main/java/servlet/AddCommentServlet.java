package servlet;

import lombok.SneakyThrows;
import manager.CommentManager;
import manager.TaskManager;
import manager.UserManager;
import model.Comment;
import model.User;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;


@WebServlet(urlPatterns = "/addComments")
public class AddCommentServlet extends HttpServlet {
    CommentManager commentManager = new CommentManager();
UserManager userManager = new UserManager();
TaskManager taskManager = new TaskManager();
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp){
        HttpSession session = req.getSession();
        User user = (User)session.getAttribute("user");
        int taskId= Integer.parseInt(req.getParameter("id"));
        String comment = req.getParameter("comment");
        Comment comment1 = Comment.builder()
                .userId(user.getId())
                .taskId(taskId)
                .comment(comment)
                .build();
        commentManager.addComment(comment1);
        resp.sendRedirect("/taskPage?id="+taskId);
    }



  }
