import java.sql.*;
import javax.swing.*;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

public class DBConnect {
   
    Connection con = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    
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
    
    public void PatientRegister(String username,String name, String password,String sec_q,String answer, String pid ,String email, String age, String phone_num, String address, String gender){
        try{
            con=DBConnect.ConnectDB();
            String sql = "INSERT INTO `patient`(`pid`, `name`, `username`, `email`, `age`, `phonenum`, `address`, `gender`, `password`, `sec_q`, `answer`) "
                    + "VALUES ('"+pid+"','"+name+"','"+username+"','"+email+"', '"+age+"','"+phone_num+"','"+address+"','"+gender+"','"+password+"','"+sec_q+"','"+answer+"')";
            pst = con.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Successfully Registered");
            
        } catch (Exception e) {
           JOptionPane.showMessageDialog(null, e);
    }
    }
    
     public String patientAutoId(){
        
        try {
            String sql = "SELECT `pid` FROM `patient` ORDER BY pid DESC LIMIT 1";
            
            con=DBConnect.ConnectDB();
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            
            if(rs.next()){
                
                String rnno = rs.getString("pid");
                int co = rnno.length();
                String txt = rnno.substring(0 , 2);
                String num = rnno.substring(2, co);
                int n = Integer.parseInt(num);
                n++;
                String snum = Integer.toString(n);
                String ftxt = txt + snum;
                return ftxt;
                  
            }
            else{
                return "MI1000";
            }
        } catch (Exception e){
            JOptionPane.showMessageDialog(null , e);
            return "error";
        }
    }
    
    public void DoctorRegister(String d_id, String name, String username, String password, String specialization)
    {
        try {
        String sql = "INSERT INTO `doctor`(`d_id`,`name`, `username`, `password`, `specialization`, `hospital`,`available dates`) VALUES (?,?, ?, ?, ?, ?, ?)";
        con=DBConnect.ConnectDB();
        pst=con.prepareStatement(sql);
        
        String uname= username;
        
        Statement st = con.createStatement();
        ResultSet rs2=st.executeQuery("Select username from doctor where username= '"+uname+"'");
        if(rs2.next()){
        JOptionPane.showMessageDialog(null,"This username already exists");
        }
        
        else{
        pst.setString(1, d_id);
        pst.setString(2, name);
        pst.setString(3, username);
        pst.setString(4, password);
        pst.setString(5, specialization);
        pst.setNull(6, Types.VARCHAR);
        pst.setNull(7, Types.VARCHAR);
        
        
        pst.executeUpdate();
         JOptionPane.showMessageDialog(null," REGISTER SUCCESSFULL");
        }
                } catch(Exception e){ 
                    JOptionPane.showMessageDialog(null,e);
                }
    }
    
    public String doctorAutoId(){
        
        try {
            String sql = "SELECT `d_id` FROM `doctor` ORDER BY d_id DESC LIMIT 1";
            
            con=DBConnect.ConnectDB();
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            
            if(rs.next()){
                
                String rnno = rs.getString("d_id");
                int co = rnno.length();
                String txt = rnno.substring(0 , 2);
                String num = rnno.substring(2, co);
                int n = Integer.parseInt(num);
                n++;
                String snum = Integer.toString(n);
                String ftxt = txt + snum;
                return ftxt;
                  
                
            }
            else{
                return("DI1000");
            }
        } catch (Exception e){
            JOptionPane.showMessageDialog(null , e);
            return("error");
        }
    }
    
    public boolean adminLogin(String username, String password){
        con=DBConnect.ConnectDB();
        String sql = "Select * from admin where username=? and password=?";
        try {
        pst = con.prepareStatement(sql);
        pst.setString(1, username);
        pst.setString(2, password);
        rs = pst.executeQuery();
        if(rs.next()){
            //JOptionPane.showMessageDialog(null,"Welcome admin");
             return true;
             
        }
        else{
            JOptionPane.showMessageDialog(null,"Invalid username or password");
            return false;
        }
        
                } catch(Exception e){ 
                    JOptionPane.showMessageDialog(null,e);
                    return false;
                }

    }
    
    public boolean doctorLogin(String username, String password){
        con=DBConnect.ConnectDB();
        String sql = "Select * from doctor where username=? and password=?";
        try {
        pst = con.prepareStatement(sql);
        pst.setString(1, username);
        pst.setString(2, password);
        rs = pst.executeQuery();
        if(rs.next()){
            JOptionPane.showMessageDialog(null,"Welcome doctor");
            return true;
        }
        else{
            JOptionPane.showMessageDialog(null,"Invalid username or password");
            return false;
        }
        
                } catch(Exception e){ 
                    JOptionPane.showMessageDialog(null,e);
                    return false;
                }
    }
    
    public boolean patientLogin(String username, String password){
        con=DBConnect.ConnectDB();
        String sql = "Select * from patient where username=? and password=?";
        try {
        pst = con.prepareStatement(sql);
        pst.setString(1, username);
        pst.setString(2, password);
        rs = pst.executeQuery();
        if(rs.next()){
            //JOptionPane.showMessageDialog(null,"Welcome patient");
             return true;
        }
        else{
            JOptionPane.showMessageDialog(null,"Invalid username or password or id");
            return false;
        }
                } catch(Exception e){ 
                    JOptionPane.showMessageDialog(null,e);
                    return false;
                }
    }
    
    
    public void deleteAccount(String username, String pid){
        con=DBConnect.ConnectDB();
        String sql = "DELETE FROM patient WHERE username = ? and pid = ? ";
        try{
            pst = con.prepareStatement(sql);
            pst.setString(1, username);
            pst.setString(2, pid);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Account Deleted");
            
            
         } catch (Exception e){
             
               JOptionPane.showMessageDialog(null, " I WANT TO DIE ");
               
         }      
    }
    
    
}  
    
