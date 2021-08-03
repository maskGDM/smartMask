/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import Config.Util;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAOmodel.Mask_producedDAO;
import Model.Mask_produced;
import javax.servlet.annotation.WebServlet;
import org.json.JSONObject;

/**
 *
 * @author geova
 */
@WebServlet(name = "Mask_producedServlet", urlPatterns = {"/Mask_producedServlet"})

public class Mask_producedServlet extends HttpServlet {

    Mask_producedDAO maskDAO = new Mask_producedDAO();
    Mask_produced mask = new Mask_produced();
    Util u = new Util();

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
        System.out.println("POST MASK_produced");
        int type = Integer.parseInt(request.getParameter("Type"));
        switch (type) {
            case 1: {
                mask.setMask_code(Integer.parseInt(request.getParameter("Datum")));
                if (maskDAO.deactivateMaskMask_produced(mask)) {
                    jsonResult = "{\"status\":200}";
                } else {
                    jsonResult = "{\"status\":400}";
                }
                break;
            }
            case 2: {
                mask.setMask_code(Integer.parseInt(request.getParameter("Datum")));
                if (maskDAO.addMask_produced(mask)) {
                    jsonResult = "{\"status\":200}";
                } else {
                    jsonResult = "{\"status\":400}";
                }
                break;
            }
            case 3: {
                jsonResult = "{\"status\":200, \"result\":" + maskDAO.jsonMaskMask_produced() + "}";
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
