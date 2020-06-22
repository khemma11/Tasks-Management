<%@ page import="model.Task" %>
<%@ page import="java.util.List" %>
<%@ page import="model.User" %><%--
  Created by IntelliJ IDEA.
  User: Aka
  Date: 17.06.2020
  Time: 21:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Home</title>
</head>
<body>
<a href="logout">LOGOUT</a><br><br>
<% List<Task> tasks = (List<Task>) request.getAttribute("tasks");%>
<div>
    All Tasks:

    <table border="1">
        <tr>
            <th>Name</th>
            <th>Description</th>
            <th>Deadline</th>
            <th>Status</th>
            <th>User</th>
            <th>Update Status</th>

        </tr>
        <%
            for (Task task : tasks) { %>

        <tr>
            <td><%=task.getName()%>
            </td>
            <td><%=task.getDescription()%>
            </td>
            <td><%=task.getDeadline()%>
            </td>
            <td><%=task.getStatus().name()%>
            </td>
            <td><%=task.getUser().getName() + " " + task.getUser().getSurName()%>
            </td>
<td>
    <form action="/changeTaskStatus" method="post">
        <input type="hidden" name="taskId" value="<%=task.getId()%>">
        <select name="status">
            <option value="NEW">NEW</option>
            <option value="IN_PROGRESS">IN_PROGRESS</option>
            <option value="FINISHED">FINISHED</option>
        </select> <input type="submit" value="OK">
    </form>
</td>
        </tr>
        <% } %>
    </table>
</div>

</body>
</html>
