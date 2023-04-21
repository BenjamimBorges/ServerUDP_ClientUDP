import java.net.*;
import java.io.*;

public class UDPServer {
    private static final int BUFFER_SIZE = 1000;

    public static void main(String[] args) {
        DatagramSocket socket = null;
        
        try {
            socket = new DatagramSocket(6789);
            byte[] buffer = new byte[BUFFER_SIZE];
            while (true) {
                    
                DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                socket.receive(request);

                DatagramPacket reply = new DatagramPacket(request.getData(), request.getLength(),
                        request.getAddress(), request.getPort());
                socket.send(reply);

                String replyMessage = new String(reply.getData(), 0, reply.getLength());
                System.out.println("Reply: " + replyMessage);

            }
        } catch (SocketException e) {
            System.out.println("Socket error: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO error: " + e.getMessage());
        } finally {
            if (socket != null) {
                socket.close();
            }
        }
    }
}