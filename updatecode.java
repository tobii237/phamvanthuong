import java.io.*;
import java.net.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Server {

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(1234)) {
            System.out.println("Server is listening on port 1234");
            
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");

                InputStream input = socket.getInputStream();
                FileOutputStream fos = new FileOutputStream("received_file.txt");
                
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = input.read(buffer)) != -1) {
                    fos.write(buffer, 0, bytesRead);
                }
                fos.close();
                
                // Ghi thông tin file vào cơ sở dữ liệu
                saveFileInfo("received_file.txt");
                
                socket.close();
            }
            
        } catch (IOException | SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    private static void saveFileInfo(String fileName) throws SQLException {
        String sql = "INSERT INTO files (file_name) VALUES (?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, fileName);
            pstmt.executeUpdate();
        }
    }
}
