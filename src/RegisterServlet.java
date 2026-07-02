package src;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/register")
public class RegisterServlet extends HttpServlet{
    private final String URL = "jdbc:mysql://localhost:3306/java";
    private final String USER = "root";
    private final String PASSWORD = "Satish@3002";



    @Override

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
     throws ServletException, IOException{


        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String sql = "Select * From studentInfo";

        out.println("<html>");
        out.println("<head><title>Registed students</title></head>");

        out.println("<body style='font-family: Arial, sans-serif; margin: 40px;'>");
        out.println("<h2>📋 Registered Student List</h2>");
        
        
        out.println("<table border='1' cellpadding='10' style='border-collapse: collapse; width: 80%;'>");
        out.println("<tr style='background-color: #f2f2f2;'><th>ID</th><th>Name</th><th>Age</th><th>City</th></tr>");

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");

            try(Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()){
                    while(rs.next()){
                        out.println("<tr>");
                        out.println("<td>" + rs.getInt(1)+"</td>");
                        out.println("<td>" + rs.getString(2)+"</td>");
                        out.println("<td>" + rs.getInt(3)+"</td>");
                        out.println("<td>" + rs.getString(4)+"</td>");
                        out.println("</tr>");
                    }
            }
        }catch(Exception e){
            out.println("<p style='color: red;'>Error fetching data: " + e.getMessage() + "</p>");
            e.printStackTrace();
        }

        out.println("</table>");
        out.println("<br><br><a href='index.html' style='text-decoration: none; background: #333; color: #fff; padding: 10px 15px; border-radius: 5px;'>Back to Form</a>");
        out.println("</body></html>");

     }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException{


        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        int id = Integer.parseInt(request.getParameter("studentId"));
        String name = request.getParameter("studentName");
        int age = Integer.parseInt(request.getParameter("studentAge"));
        String city = request.getParameter("studentCity");

        String sql = "INSERT INTO studentInfo (id, sname, sage, scity) VALUES (?, ?, ?, ?)";

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");


            try(Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement pstmt = conn.prepareStatement(sql)){

                pstmt.setInt(1, id);
                pstmt.setString(2, name );
                pstmt.setInt(3, age);
                pstmt.setString(4, city);


                int rows = pstmt.executeUpdate();

                out.println("<html>");
                out.println("<head><title>Registration Status</title></head>");
                out.println("<body style='font-family: Arial, sans-serif; margin: 40px;'>");
                if (rows > 0) {
                    out.println("<h2 style='color: green;'>✔ Success! Student data saved to MySQL.</h2>");
                } else {
                    out.println("<h2 style='color: red;'>❌ Error: Failed to save data.</h2>");
                }
                
                out.println("<br><br>");
                out.println("<a href='index.html' style='text-decoration: none; background: #333; color: #fff; padding: 10px 15px; border-radius: 5px;'>Back to Form</a>");
                out.println("</body>");
                out.println("</html>");
            }
        }catch(Exception e){
                out.println("<html><body>");
            out.println("<h2 style='color: red;'>Database Error occurred:</h2>");
            out.println("<p style='color: gray;'>" + e.getMessage() + "</p>");
            out.println("<br><a href='index.html'>Back to Form</a>");
            out.println("</body></html>");
            e.printStackTrace();
            }finally{
                out.close();
            }

    }
}
