<%@ page import="model.Task" %>
<%@ page import="java.util.List" %>
<%@ page import="model.User" %><%--
  Created by IntelliJ IDEA.
  User: Aka
  Date: 17.06.2020
  Time: 21:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Manager Home</title>
</head>
<body>
<a href="/logout">LOGOUT</a><br><br>
<%User user = (User) session.getAttribute("user");%>
<% List<User> users = (List<User>) request.getAttribute("users");%>
<% List<Task> tasks = (List<Task>) request.getAttribute("tasks");

%>
Welcome <%=user.getName() + " " + user.getSurName() + " " %>  <% if (user.getPictureUrl()!=null){%>

 <img src="/image?path=<%=user.getPictureUrl()%>" width="100px"><%}%>
<div style="width:600px">
    <div style="jnwidth: 50%;float: left">
        Add User:
        <form action="/register" method="post" enctype="multipart/form-data">
            <p>Name</p>
            <input type="text" name="name" placeholder="name"/>
            <p>Surname</p>
            <input type="text" name="surName" placeholder="surName"/>
            <p>Email</p>
            <input type="text" name="email" placeholder="email"/>
            <p>Password</p>
            <input type="password" name="password" placeholder="password"/>
            <p>User Type</p>
            <select name="userType">
                <option value="USER">USER</option>
                <option value="MANAGER">MANAGER</option>
            </select><br><br>
            <input type="file" name="image"/><br>
            <input type="submit" value="OK"><br><br>
        </form>
    </div>
    <div style="width: 50%;float: left">
        Add Tasks:

        <form action="/addTasks" method="post">
            <p>Name</p>
            <input type="text" name="name" placeholder="name">
            <p>Description</p>
            <textarea name="description" placeholder="description"></textarea>
            <p>Deadline</p>
            <input type="date" name="deadline">
            <p>Status</p>
            <select name="status">
                <option value="NEW">NEW</option>
                <option value="IN_PROGRESS">IN_PROGRESS</option>
                <option value="FINISHED">FINISHED</option>
            </select>
            <p>UserID</p>
            <select name="user_id">
                    <%
                for (User user1 : users) {%>
                <option value="<%=user1.getId()%>"><%=user1.getName()%> <%=user1.getSurName()%>
                </option>

                    <%
                }
%>
            </select>
                <br><br>
                <input type="submit" value="OK"><br><br>
        </form>
    </div>
</div>
<div>
    <div>
        All Users:

        <table border="1">
            <tr>
                <th>Name</th>
                <th>SurName</th>
                <th>Email</th>
                <th>UserType</th>
                <th>Pictures</th>

            </tr>
            <% for (User user1 : users) { %>

            <tr>

                <td><%=user1.getName()%>
                </td>
                <td><%=user1.getSurName()%>
                </td>
                <td><%=user1.getEmail()%>
                </td>
                <td><%=user1.getUserType().name()%>
                <td><%if (user1.getPictureUrl()!=null){%>
                <img src="/image?path=<%=user1.getPictureUrl()%>" width="30px"/>

           <% }%>
                </td>

            </tr>
            <% } %>
        </table>
    </div>
    <br><br>

    <div>
        All Tasks:

        <table border="1">
            <tr>
                <th>Name</th>
                <th>Description</th>
                <th>Deadline</th>
                <th>Status</th>
                <th>User</th>

            </tr>
            <%
                for (Task task : tasks) { %>

            <tr>
                <td><a href="/taskPage?id=<%=task.getId()%>"><%=task.getName()%></a>
                </td>
                <td><%=task.getDescription()%>
                </td>
                <td><%=task.getDeadline()%>
                </td>
                <td><%=task.getStatus().name()%>
                </td>
                <td><%=task.getUser().getName() + " " + task.getUser().getSurName()%>
                </td>

            </tr>
            <% } %>
        </table>
    </div>
</div>
</body>
</html>
