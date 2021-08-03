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
import Model.Default_data;
import DAOmodel.Default_dataDAO;
import javax.servlet.annotation.WebServlet;

/**
 *
 * @author Michelle
 */
@WebServlet(name = "Default_dataServlet", urlPatterns = {"/Default_dataServlet"})

public class Default_dataServlet extends HttpServlet {

    Default_dataDAO default_dataDAO = new Default_dataDAO();
    Default_data default_data = new Default_data();

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
        System.out.println("POST Data Default");
        int type = Integer.parseInt(request.getParameter("Type"));
        switch (type) {
            case 1: {
                default_data.setUser_informationid(Integer.parseInt(request.getParameter("Datum")));
                jsonResult = "{\"status\":200, \"result\":" + default_dataDAO.jsonDefault_data(default_data) + "}";
                break;
            }
            case 2: {
                default_data.setJson(request.getParameter("Datum"));
                if (default_dataDAO.addDefault_data(default_data)) {
                    jsonResult = "{\"status\":200}";
                } else {
                    jsonResult = "{\"status\":300}";
                }
                break;
            }
            case 3: {

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
