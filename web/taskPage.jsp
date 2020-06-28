<%@ page import="model.User" %>
<%@ page import="model.Task" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Comment" %>
<%@ page import="model.UserType" %><%--
  Created by IntelliJ IDEA.
  User: Aka
  Date: 26.06.2020
  Time: 14:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Task page</title>
</head>
<body>
<%User user = (User) session.getAttribute("user");%>
<%Task tasks = (Task) request.getAttribute("tasks");%>
<%List<Comment> comments = (List<Comment>) request.getAttribute("comments");%>

<div class="tasks">


    TaskName: <%=" " + tasks.getName()%><br>
    Description: <%=" " + tasks.getDescription()%><br>
    Deadline: <%=" " + tasks.getDeadline()%><br>
    Status: <%=" " + tasks.getStatus()%><br>
    UserId: <%=" " + tasks.getUserId()%><br>
    UserName: <%=" " + tasks.getUser().getName() + " " + tasks.getUser().getSurName()%><br>


</div>

<div>
    <form action="/addComments?id=<%=tasks.getId()%>" method="post">
        <input type="hidden" name="taskId" value="<%=tasks.getId()%>">
        <textarea name="comment" placeholder="add comments"></textarea><br>
        <input type="submit" value="OK">

    </form>
    <%
        for (Comment comment : comments) {%>
    <p><%=comment.getUser().getName()%> <%=comment.getUser().getSurName()%> <%=comment.getDate()%>
    </p>
    <p><%=comment.getComment()%>  <%if (comment.getUser().getEmail().equals(user.getEmail()) | user.getUserType() == UserType.MANAGER) {%>
        <a href="/deleteComment?id=<%=comment.getId()%>&taskId=<%=comment.getTaskId()%> " style="color: red">x</a></p>

    <%
            }
        }
    %>
</div>
</body>


</html>
