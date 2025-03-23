import java.io.*;
import java.net.*;
public class Server {
public static void main(String[] args) {
try (ServerSocket serverSocket = new ServerSocket(1234)) {
System.out.println("Server is listening on port 1234");
while (true) {
Socket socket = serverSocket.accept();
System.out.println("New client connected");
InputStream input = socket.getInputStream();
FileOutputStream fos = new FileOutputStream("received_file.1
byte[] buffer = new byte[4096];
int bytesRead;
while ((bytesRead = input.read(buffer)) != -1) {
fos.write(buffer,, bytesRead);
}
fos.close();
socket.close();
}
} catch (IOException ex) {
ex.printStackTrace();
}
}
