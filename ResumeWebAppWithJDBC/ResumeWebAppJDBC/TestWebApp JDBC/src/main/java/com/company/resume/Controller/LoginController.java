package com.company.resume.Controller;

import com.company.dao.inter.UserDaoInter;
import com.company.entity.User;
import com.company.main.Contex;
import com.company.resume.Util.ControllerUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "LoginController ", urlPatterns = {"/login"})
public class LoginController extends HttpServlet {
    private UserDaoInter userDao = Contex.instanceUserDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            User user = userDao.findbyEmailAndPasswor(email, password);

            if (user == null) {
                throw new IllegalArgumentException("email and password is incorrect");
            }
            request.getSession().setAttribute("loggedInUser", user);
            response.sendRedirect("users");
        } catch (Exception ex) {
            ControllerUtil.errorPage(response,ex);
        }
    }
}
