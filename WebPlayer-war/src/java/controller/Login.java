package controller;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import connect.LoginBeanLocal;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.ejb.EJB;

/**
 *
 * @author Nicolas
 */
@WebServlet(name = "loginServlet", urlPatterns = {"/login"})
public class Login extends HttpServlet {

    @EJB
    LoginBeanLocal loginbean;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet LoginServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoginServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("id");
        String username = request.getParameter("username");
        char[] password = request.getParameter("password").toCharArray();
        final int keyLength = 256;
        try {
            byte[] salt = SecureRandom.getInstance("SHA1PRNG").generateSeed(32);
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            PBEKeySpec spec = new PBEKeySpec(password, salt, 1, keyLength);
            SecretKey key = skf.generateSecret(spec);
            byte[] res = key.getEncoded();
            String pwhash = new String(res, StandardCharsets.UTF_8);
            String salts = new String(salt, StandardCharsets.UTF_8);
            boolean logingranted = loginbean.checkAccess(username, pwhash);
            if (logingranted) {
                System.out.println("Login was successful!");
                request.getRequestDispatcher("home.jsp").forward(request, response);
            } else {
                System.out.println("Login was not successful!");
                request.getRequestDispatcher("home.jsp").forward(request, response);
            }
            request.getRequestDispatcher("home.jsp").forward(request, response);
        } catch (InvalidKeySpecException ikse) {
            System.out.println("Falscher Key!");
        } catch (NoSuchAlgorithmException nsae) {
            System.out.println("SHA1-Algorithmus nicht gefunden!");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
