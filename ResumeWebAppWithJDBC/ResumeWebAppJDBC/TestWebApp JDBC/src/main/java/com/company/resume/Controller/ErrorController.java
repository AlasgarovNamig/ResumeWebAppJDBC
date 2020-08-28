package com.company.resume.Controller;

import com.company.dao.inter.UserDaoInter;
import com.company.main.Contex;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ErrorController",urlPatterns = {"/error"})
public class ErrorController extends HttpServlet {

    private UserDaoInter userDao = Contex.instanceUserDao();
// error contollerde meqsedimiz filterlemeyimizdi  filter sonu jsp olan file block elediyi ucun error jpn i / error olaraq el catan edirik
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
       request.getRequestDispatcher("error.jsp").forward(request,response);
    }
}
