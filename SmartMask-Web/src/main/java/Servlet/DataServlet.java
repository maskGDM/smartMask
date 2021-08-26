/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import Config.Util;
import DAOmodel.DataDAO;
import Model.Data;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONObject;

/**
 *
 * @author geova
 */
@WebServlet(name = "DataServlet", urlPatterns = {"/DataServlet"})
public class DataServlet extends HttpServlet {

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
    Data data = new Data();
    DataDAO dataDAO = new DataDAO();
    Util u = new Util();

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
        u.zonaHoraria();
        System.out.println("POST Data");
        int type = Integer.parseInt(request.getParameter("Type"));
        switch (type) {
            case 1: {
                data.setUser_informationid((request.getParameter("Datum")));
                String getData = dataDAO.jsonData(data);

                if (!"[]".equals(getData.trim())) {
                    jsonResult = "{\"status\":200, \"result\":" + getData + "}";
                } else {
                    jsonResult = "{\"status\":200, \"result\":" + (dataDAO.jsonData()) + "}";
                }
                break;
            }
            case 2: {
                data.setJson(request.getParameter("Datum"));
                if (dataDAO.addData(data)) {
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
