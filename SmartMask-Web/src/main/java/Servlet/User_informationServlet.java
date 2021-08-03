/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Model.User_information;
import DAOmodel.User_informationDAO;
import javax.servlet.annotation.WebServlet;
import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONObject;

/**
 *
 * @author Michelle
 */
@WebServlet(name = "User_informationServlet", urlPatterns = {"/User_informationServlet"})

public class User_informationServlet extends HttpServlet {

    User_informationDAO user_informationDAO = new User_informationDAO();
    User_information user_information = new User_information();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response, String jsonResult)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println(jsonResult);

        }
    }
    String jsonResult = "";

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @param jsonResult
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        processRequest(request, response, jsonResult);
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
            throws ServletException, IOException {
        System.out.println("POST User Information");
        // JSONObject objetJson = new JSONObject(request.getParameter("User")).getJSONObject("Type");// get json
        int JSONtype = Integer.parseInt(request.getParameter("Type"));// receive json from js
        switch (JSONtype) {
            case 1: {
                JSONObject objetJson = new JSONObject(request.getParameter("User")).getJSONObject("User");// get json
                int JSONrolU = Integer.parseInt(request.getParameter("Rol"));// receive json from js
                if (JSONrolU == 1) {
                    objetJson = objetJson.append("type", "U");
                } else if (JSONrolU == 2) {
                    objetJson = objetJson.append("type", "A");
                }
                objetJson = objetJson.append("socialnetworkid", 1);//local
                objetJson = objetJson.append("imguser", "https://res.cloudinary.com/bricex/image/upload/v1599484372/Mask/jl5t1apijlqelbelx0c8.png");
                objetJson = objetJson.put("password", DigestUtils.sha256Hex(objetJson.getString("password")));// change
                JSONObject objetJsonAll = new JSONObject(request.getParameter("User")).put("User", objetJson);// json
                user_information.setJson(objetJsonAll.toString());// send json which is internally converted to xml to get the xml
                if (JSONrolU == 1) {
                    if (user_informationDAO.addUser_information(user_information)) {
                        jsonResult = "{\"float\": 200,\"resultPage\":\"login.html\"}";
                    } else {
                        jsonResult = "{\"float\": 400 ,\"resultPage\":\"login.html\"}";
                    }
                } else if (JSONrolU == 2) {
                    if (user_informationDAO.addUser_information(user_information)) {
                        jsonResult = "{\"float\": 200}";
                    } else {
                        jsonResult = "{\"float\": 400}";
                    }
                }
                break;
            }

            case 5: {
                ///SELECT EDIT PROFILE///          
                JSONObject objetJsonEdit = new JSONObject(request.getParameter("User")).getJSONObject("User");//get json from json from js
                objetJsonEdit = objetJsonEdit.put("password", DigestUtils.sha256Hex(objetJsonEdit.getString("password")));// change
                JSONObject objetJsonAll = new JSONObject(request.getParameter("User")).put("User", objetJsonEdit);// json
                user_information.setJson(objetJsonAll.toString());
                if (user_informationDAO.editUser_information(user_information)) {
                    jsonResult = "{\"flag\": 200}";
                } else {
                    jsonResult = "{\"flag\": 400}";
                }
                break;
            }
            case 2: {
                user_information.setUser_informationid(Integer.parseInt(request.getParameter("Datum")));// receive json from js
                jsonResult = "{\"information\": " + user_informationDAO.selectUser_information(user_information) + "}";
                break;
            }
            case 3: {
                ///REGISTER WITH SOCIAL NETWORKS///
                System.out.println("Social Networks");
                JSONObject objetJsonSocial = new JSONObject(request.getParameter("Data"));//get json from json from js
                user_information.setJson(objetJsonSocial.toString());
                if (user_informationDAO.addUser_informationSocial(user_information)) {
                    jsonResult = "{\"float\": 200 ,\"resultPage\":\"login.html\"}";
                } else {
                    jsonResult = "{\"float\": 400 ,\"resultPage\":\"login.html\"}";
                }
                break;
            }
            case 4: {
                user_information.setUser_informationid(Integer.parseInt(request.getParameter("Datum")));// receive json from js
                jsonResult = "{\"information\": " + user_informationDAO.selectAllUser_information(user_information) + "}";
                break;
            }
            case 6: {
                jsonResult = "{\"result\": " + user_informationDAO.selectAdmin() + "}";
                break;
            }
            case 7: {
                jsonResult = "{\"result\": " + user_informationDAO.selectUser() + "}";
                break;
            }
            case 8: {
                
                break;
            }

            default:
                break;
        }
        System.out.println(jsonResult);

        processRequest(request, response, jsonResult);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
