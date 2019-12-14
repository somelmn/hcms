
import java.sql.*;
import javax.swing.*;

public class DBConnect {
   
    Connection con=null;
    
    public static Connection ConnectDB(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/healthcare?useUnicode=true&useLegacyDatetimeCode=false&serverTimezone=Turkey", "root","");
            return con;
            
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null,ex);
            return null;
        }
    }
    
    public String ReturnHospitalNames(){                                         
        con=DBConnect.ConnectDB();
        String hospitals = "Select * from hospital";
        return hospitals;
    }
}  
    
