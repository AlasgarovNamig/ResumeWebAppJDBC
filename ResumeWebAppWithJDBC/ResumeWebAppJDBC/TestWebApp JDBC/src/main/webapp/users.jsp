<%-- 
    Document   : user
    Created on : Jul 20, 2020, 11:37:20 AM
    Author     : Hp
--%>

<%@page import="com.company.entity.User" %>
<%@page import="com.company.main.Contex" %>
<%@page import="com.company.dao.inter.UserDaoInter" %>
<%@ page import="java.util.List" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <link rel="stylesheet" href="css/users.css">

    <script src="https://kit.fontawesome.com/0967980b98.js" crossorigin="anonymous"></script>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <title>JSP Page</title>

    <script type="text/javascript" src="JS/users.js"></script>
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
            integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
            crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
            integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
            crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
            integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
            crossorigin="anonymous"></script>
</head>
<body>

<%
    User user =(User)session.getAttribute("loggedInUser");
%>



<%
    String name = request.getParameter("name");
    String surname = request.getParameter("surname");
    String nationalityIdStr = request.getParameter("nid");
    Integer nationalityId = null;
    if (nationalityIdStr != null && !nationalityIdStr.trim().isEmpty()) {
        nationalityId = (Integer.parseInt(nationalityIdStr));
    }
    UserDaoInter userDao = Contex.instanceUserDao();
    List<User> list = userDao.SerachUser(name, surname, nationalityId);
%>

<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">DELETE</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                Are you sure?
            </div>
            <div class="modal-footer">
                <form action="userDetail" method="POST">
                    <input type="hidden" name="id" value="" id="idForDelete">
                    <input type="hidden" name="action" value="delete">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                <input type="submit" class="btn btn-danger" value="DELETE"/>
                </form>
            </div>
        </div>
    </div>
</div>

<div class="container mycontainer ">




    <div>
        <form action="users.jsp" method="GET">
            <div class="form-group">
                <label for="name">name:</label>
                <input onkeydown="writeWhatIamTyping()" placeholder="Enter Name" class="form-control" type="text"
                       name="name" value=""
                       id="whatIamTyping"/>
                <span id="typing">

                </span>
            </div>
            <div class="form-group">
                <label for="surname">surname:</label>
                <input placeholder="Enter Surname" class="form-control" type="text" name="surname" value=""/>
            </div>
            <input class="btn btn-primary" type="submit" name="search" value="Search" id="btnSearch">
            <input class="btn btn-primary" type="button" name="search" value=" Show hide" onclick="showHide()">

        </form>
    </div>





    <div class=" container">
        <table class="table">
            <thead>
            <tr>
                <th>Name</th>
                <th>Surname</th>
                <th>Nationality</th>
                <th>operation</th>
            </tr>
            </thead>
            <tbody>

            <%
                for (User u : list) {
            %>
            <tr>
                <td><%=u.getName()%>
                </td>
                <td><%=u.getSurname()%>
                </td>
                <td><%=u.getNationality().getName() == null ? "" : u.getNationality().getName()%>
                </td>
                <td style="width:5px">
                    <form action="userDetail " method="GET">
                        <input type="hidden" name="id" value="<%=u.getId()%>"/>
                        <input type="hidden" name="action" value="update"/>
                        <button class="btn btn-secondary" type="submit" name="action">
                            <i class="fas fa-user-edit"></i>
                        </button>
                    </form>
                <td/>
                <td style="width:5px">

                        <input type="hidden" name="id" value="<%=u.getId()%>"/>
                        <input type="hidden" name="action" value="delete"/>
                        <button class=" btn btn-danger" type="submit" name="action"
                                data-toggle="modal" data-target="#exampleModal" onclick="setIdForDelete('<%=u.getId()%>')">
                            <i class="fas fa-user-times"></i>
                        </button>


                </td>

                <%}%>
            </tr>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
