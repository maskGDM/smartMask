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
import Model.Mask;
import DAOmodel.MaskDAO;
import java.util.TimeZone;
import javax.servlet.annotation.WebServlet;
import org.json.JSONObject;

/**
 *
 * @author geova
 */
@WebServlet(name = "MaskServlet", urlPatterns = {"/MaskServlet"})

public class MaskServlet extends HttpServlet {

    MaskDAO maskDAO = new MaskDAO();
    Mask mask = new Mask();
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
        // System.out.println("POST MASK");
        u.zonaHoraria();
        int type = Integer.parseInt(request.getParameter("Type"));
        switch (type) {
            case 1: {
                mask.setUser_informationid((request.getParameter("Datum")));
                jsonResult = "{\"status\":200, \"result\":" + (maskDAO.jsonMask(mask)) + "}";
                break;
            }
            case 2: {//insert    
                mask.setMask_code(new JSONObject(request.getParameter("Datum")).getJSONObject("mask").getString("mask_code"));
                System.out.println(maskDAO.activeMask(mask));
                if (maskDAO.activeMask(mask) == false) {
                    mask.setJson(request.getParameter("Datum"));
                    if (maskDAO.addMask(mask)) {
                        jsonResult = "{\"status\":200}";
                    } else {
                        jsonResult = "{\"status\":400}";
                    }
                } else {
                    jsonResult = "{\"status\":300}";
                }
                break;
            }
            case 3: {//select data mask   
                mask.setUser_informationid((request.getParameter("Datum")));
                String getData = null;
                if (!"".equals(request.getParameter("Limit"))) {
                    getData = (maskDAO.jsonMaskDailyDataLimit(mask, Integer.parseInt(request.getParameter("Limit"))));
                } else {
                    getData = (maskDAO.jsonMaskDailyData(mask));
                }
                if (!"[]".equals(getData)) {// que no tenga datos agregados para no repetir
                    jsonResult = "{\"status\":200, \"result\":" + getData + "}";
                } else {
                    jsonResult = "{\"status\":300}";
                }

                break;
            }
            case 4: {//select data mask   
                mask.setUser_informationid((request.getParameter("Datum")));
                if (maskDAO.deleteorresetMask(mask)) {
                    jsonResult = "{\"status\":200}";
                } else {
                    jsonResult = "{\"status\":300}";
                }

                break;
            }
            case 5: {//select mask one month              
                mask.setUser_informationid((request.getParameter("Datum")));
                String getData = null;
                if (!"".equals(request.getParameter("Limit"))) {
                    getData = (maskDAO.jsonMask1MonthDataLimit(mask, Integer.parseInt(request.getParameter("Limit"))));
                } else {
                    getData = (maskDAO.jsonMask1MonthData(mask));
                }
                if (!"[]".equals(getData)) {// que no tenga datos agregados para no repetir
                    jsonResult = "{\"status\":200, \"result\":" + getData + "}";
                } else {
                    jsonResult = "{\"status\":300}";
                }
                break;
            }
            case 6: {//select mask 3 month               
                mask.setUser_informationid((request.getParameter("Datum")));
                String getData = null;
                getData = (maskDAO.jsonMask3MonthData(mask));
                if (!"[]".equals(getData)) {// que no tenga datos agregados para no repetir
                    jsonResult = "{\"status\":200, \"result\":" + getData + "}";
                } else {
                    jsonResult = "{\"status\":300}";
                }
                break;
            }
            case 7: {//select mask report    
                JSONObject jsonReport = new JSONObject(request.getParameter("Datum")).getJSONObject("Report");
                mask.setUser_informationid(jsonReport.getString("idUser"));
                String getData = null;
                System.out.println((jsonReport.getString("dateInit")) + "=====" + (jsonReport.getString("dateEnd")));
                getData = (maskDAO.jsonMaskReport(mask, (jsonReport.getString("dateInit")), (jsonReport.getString("dateEnd"))));
                if (!"[]".equals(getData)) {// que no tenga datos agregados para no repetir
                    jsonResult = "{\"status\":200, \"result\":" + getData + "}";
                } else {
                    jsonResult = "{\"status\":300}";
                }
                break;
            }
            case 8: {//select mask one month admin
                if (request.getParameter("Limit").equals("")) {
                    jsonResult = "{\"status\":200, \"type\":1,\"result\":" + (maskDAO.jsonMask1MonthDataAdmin()) + "}";
                } else {
                    jsonResult = "{\"status\":200, \"type\":1,\"result\":" + (maskDAO.jsonMask1MonthDataAdminLimit(Integer.parseInt(request.getParameter("Limit")))) + "}";
                }
                break;
            }
            case 9: {//select mask report    
                JSONObject jsonReport = new JSONObject(request.getParameter("Datum")).getJSONObject("Report");
                if (request.getParameter("Limit").equals("")) {
                    jsonResult = "{\"status\":200,\"type\":2,\"subtype\":2 ,\"result\":" + (maskDAO.jsonMaskReportAdmin((jsonReport.getString("dateInit")), (jsonReport.getString("dateEnd")))) + "}";
                } else {
                    jsonResult = "{\"status\":200,\"type\":2,\"subtype\":2 ,\"result\":" + (maskDAO.jsonMaskReportAdminLimit((jsonReport.getString("dateInit")), Integer.parseInt(request.getParameter("Limit")), (jsonReport.getString("dateEnd")))) + "}";
                }

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
