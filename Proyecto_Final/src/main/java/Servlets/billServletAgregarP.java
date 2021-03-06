/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Clases.Detalle;
import Clases.Factura;
import Clases.Producto;
import Clases.ProductoExistencia;
import Clases.User;
import DB.BDUser;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author reyg6
 */
@WebServlet(name = "billServletAgregarP", urlPatterns = {"/agregarProductoBill"})
public class billServletAgregarP extends HttpServlet {

    static Random ran = new Random();
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
          
        ProductoExistencia pro = new ProductoExistencia();
        ArrayList<ProductoExistencia> producto = new ArrayList<ProductoExistencia>();
        BDUser bd = new BDUser();
        Producto detalleProducto = new Producto();
        double precio = 0.00;
        int codigoP;
        int existencia;
        
        int noFact  = 0;
        String fechafact;
        int nit;
        String nombreC;
        String direccionC;
        double subtotal;        
                
        
        response.setContentType("text/html;charset=UTF-8");
        
        
        
        try (PrintWriter out = response.getWriter()) {
            
        
                  
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            
            out.println("<style>");
            out.print("table {");
            out.print("font-family: arial, sans-serif;");
            out.print("border-collapse: collapse;");
            out.print("width: 100%;");
            out.print("}");

            out.print("td, th {");
            out.print("border: 1px solid #dddddd;");
            out.print("text-align: left;");
            out.print("padding: 8px;");
            out.print("}");

            out.print("tr:nth-child(even) {");
            out.print("background-color: #dddddd;");
            out.print("}");
            out.print("</style>");
            out.println("</head>");
            out.println("<body>");
             out.println("<table border='1'>");
            
            out.println("<tr>");
            bd.setDataBase();
            bd.connectingBD();
            producto = bd.requestProducto();
            
            
            
            
            out.println("<form>");
            
            
            out.println("<th><label>Nombre del producto</label></th>");
            out.println("</tr>");
            out.println("<td><select name='idProducto'>");
            for(ProductoExistencia proE : producto){
            out.println("<option value='" + proE.getIdProducto() + "'>" + proE.getNombreProducto() + "</option>");
            }
            
            out.println("</select></td>");            
            out.println("</tr>");
            out.println("</table>");
            out.println("<input type='submit' value='Buscar'/>");
            out.println("<br/>");
            out.println("</form>");
            
            out.println("<br/>");
            out.println("<br/>");
            out.println("<br/>");
            int codigoB = Integer.parseInt(request.getParameter("idProducto"));
            int existente = 0;
        
            int encontrado = -1;
            
            for(int i = 0; i < producto.size(); i++){
                if(producto.get(i).getIdProducto() == codigoB){
                    encontrado = i;
                }else{
                    continue;
                }
            }
            out.println("<table>");
        out.println("<tr>");
        out.println("<th>Producto ID</th>");
        out.println("<th>Nombre</th>");
        out.println("<th>Precio Venta</th>");
        out.println("<th>Precio Costo</th>");
        out.println("<th>Descripcion</th>");
        out.println("<th>Existencia</th>");
        out.println("</tr>");
        
        out.println("<tr>");
        out.println("<td>" + producto.get(encontrado).getIdProducto() + "</td>");
        out.println("<td>" + producto.get(encontrado).getNombreProducto() + "</td>");
        out.println("<td>" + producto.get(encontrado).getPrecioVenta() + "</td>");
        out.println("<td>" + producto.get(encontrado).getPrecioCosto() + "</td>");
        out.println("<td>" + producto.get(encontrado).getDescripcion() + "</td>");
        out.println("<td>" + producto.get(encontrado).getExistencia() + "</td>");
        
                    precio = producto.get(encontrado).getPrecioVenta();
                    codigoP = producto.get(encontrado).getIdProducto();
                    existente = producto.get(encontrado).getExistencia();
                    
        
        out.println("</tr>");
        out.println("</table>");
            out.println("<table>");
            out.println("<tr>");
            out.println("<form action='repetir' method='post'>");
            out.println("<H2>Producto a Solicitar: </h2>");
            out.println("</br>");
            out.println("<th><label>Ingrese Codigo: </label></th>");
            out.println("<th><label>Cantidad de articulo no Mayor a existencia: </label></th>");
            
            out.println("</tr>");
            out.println("<tr>");
            out.println("<td><input type='number' name='IdProducto'/></td>");
            out.println("<td><input type='text' name='existencia'/></td>");
            out.println("</tr>");
            out.println("</br>");
            out.println("</table>");
            out.println("<input type='submit' value='Guardar'/>");
            
            
            existencia = Integer.parseInt(request.getParameter("existencia"));
            subtotal = existencia * precio;
           
            
        noFact = Integer.parseInt(request.getParameter("noFact"));
        fechafact = (String) request.getParameter("fecha");
        nit = Integer.parseInt(request.getParameter("nit"));
        nombreC = (String) request.getParameter("nombreConsumer");
        direccionC = (String) request.getParameter("direccion");
           
            out.println("</form>");
            
            
            Detalle det = new Detalle();
            det.setCantidad(existencia);
            det.setSubtotal(subtotal);
            det.setIdProducto(codigoB);
            det.setNoFactura(noFact);
            User user = new User();
        
        user.setNit(nit);
        user.setNombre(nombreC);
        user.setDireccion(direccionC);
        
        Factura fac = new Factura();
        
        bd.setDataBase();
        bd.connectingBD();
        
        bd.insertDetalle(det, existente);
            
            
        /*
        
        fac.setNoFactura(noFact);
        fac.setFecha(fechafact);
        fac.setNit(nit);
        fac.setTotal(0.00);
        
        
        
        
        bd.insertUsuario(user);
        bd.insertFact(fac);
        */
            
            out.println("</body>");
            out.println("</html>");
        }
    }

    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @return 
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        
        
        
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
        processRequest(request, response);
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
