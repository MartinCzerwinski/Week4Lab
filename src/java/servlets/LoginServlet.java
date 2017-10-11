/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import utility.User;
import utility.UserService;

/**
 *
 * @author 727525
 */
public class LoginServlet extends HttpServlet
{
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        String loggedout = request.getParameter("loggedout");
        if(loggedout != null && loggedout.equals("1"))
        {
            request.getSession().removeAttribute("user");
            request.setAttribute("message", "Successfully Logged Out");
        }
        
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user != null) {
            response.sendRedirect("home");
            return;
        }
        
        Cookie userCookie = getCookie(request.getCookies());
        
        if (userCookie != null && !userCookie.getValue().equals("")) {
            request.setAttribute("username", userCookie.getValue());
            request.setAttribute("remember", "checked");
        }
        
        getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        HttpSession session = request.getSession();
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        UserService userService = new UserService();
        User user = new User();
        
        if (username.isEmpty() || password.isEmpty())
        {
            request.setAttribute("message", "Username and/or Password field cannot be empty");
            request.setAttribute("username", username);
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        }
        if ((user = userService.login(username, password)) != null)
        {
            session.setAttribute("user", user);
            
            if (request.getParameterValues("rememberMe") != null)
            {
                Cookie cookie = new Cookie("rememberMeCookie", user.getUsername());
                response.addCookie(cookie);
            }
            
            response.sendRedirect("Home");
            return;
        }
        
        request.setAttribute("username", username);
        request.setAttribute("password", password);
        request.setAttribute("message", "Invalid Username/Password");
        getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
    }

    private Cookie getCookie(Cookie[] cookies)
    {
        Cookie userCookie = null;
        for (Cookie c : cookies)
        {
            if (c.getName().equals("rememberMeCookie"))
            {
                userCookie = c;
            }
        }
        return userCookie;
    }
}
