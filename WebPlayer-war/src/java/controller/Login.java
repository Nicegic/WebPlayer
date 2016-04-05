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
        String action = request.getParameter("hidden");
        String username = request.getParameter("username");
        char[] password = request.getParameter("password").toCharArray();
        try {
            boolean logingranted;
            if (action.equals("login")) {
                byte[] pwhash = generatePwHash(username, password);
                if (pwhash == null) {
                    logingranted = false;
                } else {
                    logingranted = loginbean.checkAccess(username, pwhash);
                }
                if (logingranted) {
                    System.out.println("Login was successful!");
                    response.sendRedirect(request.getContextPath()+"/home?username="+username);
                } else {
                    System.out.println("Login was not successful!");
                    response.sendRedirect(request.getContextPath());
                    /*response.addHeader("username",username);
                    response.sendRedirect(request.getContextPath()+"/home?username="+username);
                    //request.getRequestDispatcher("index.html").forward(request, response);*/
                }
            } else if (action.equals("signup")) {
                String email = request.getParameter("email");
                byte[] salt = SecureRandom.getInstance("SHA1PRNG").generateSeed(32);
                byte[] pwhash = generateNewPwHash(password, salt);
                String salts = new String(salt, StandardCharsets.UTF_8);
                loginbean.register(username, pwhash, salt, email);
                response.sendRedirect(request.getContextPath()+"/home?username="+username);
                //request.getRequestDispatcher("home.jsp").forward(request, response);
            }
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

    private byte[] generatePwHash(String username, char[] pw) throws InvalidKeySpecException, NoSuchAlgorithmException {
        byte[] salt = loginbean.getSalt(username);
        if (salt == null) {
            System.out.println("User is not existing!");
            return null;
        }
        PBEKeySpec spec = new PBEKeySpec(pw, salt, 1, 128);
        return pwHash(spec);
    }

    private byte[] generateNewPwHash(char[] pw, byte[] salt) throws InvalidKeySpecException, NoSuchAlgorithmException {
        PBEKeySpec spec = new PBEKeySpec(pw, salt, 1, 128);
        return pwHash(spec);
    }

    private byte[] pwHash(PBEKeySpec spec) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
        SecretKey key = skf.generateSecret(spec);
        byte[] res = key.getEncoded();
        return res;
    }
}
