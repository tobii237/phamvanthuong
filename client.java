import java.io.*;
import java.net.*;
public class Client {
public static void main(String[] args) {
String hostname = "localhost";
int port = 1234;
File file = new File("file_to_send.txt");
try (Socket socket = new Socket(hostname, port)) {
OutputStream output = socket.getOutputStream();
FileInputStream fis = new FileInputStream(file);
byte[] buffer = new byte[4096];
int bytesRead;
while ((bytesRead = fis.read(buffer)) != -1) {
output.write(buffer,, bytesRead);
}
fis.close();
socket.close();
} catch (UnknownHostException ex) {
ex.printStackTrace();
} catch (IOException ex) {
ex.printStackTrace();
}
}
}
