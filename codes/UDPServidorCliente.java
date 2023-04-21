import java.net.*;
import java.io.*;
import java.util.Scanner;

public class UDPServidorCliente {
    private static final int BUFFER_SIZE = 1000;

    public static void main(String[] args) {
        DatagramSocket socket = null;

        Thread sendThread = new SendThread();
        sendThread.start();
        
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

class SendThread extends Thread {
    Scanner sc = new Scanner(System.in);

    public void run() {

        while (true) {
            DatagramSocket aSocket = null;
            try {
                System.out.print("Digite a mensagem a ser enviada: ");
                String message = sc.nextLine();
                System.out.print("Digite o endereço do servidor: ");
                String serverAddress = sc.nextLine();

                if (message.length() != 0) {
                    aSocket = new DatagramSocket();
                    byte[] m = message.getBytes();
                    InetAddress aHost = InetAddress.getByName(serverAddress);
                    int serverPort = 6789;
                    DatagramPacket request = new DatagramPacket(m, m.length, aHost, serverPort);
                    aSocket.send(request);
                }

            } catch (Exception e) {
                // TODO: handle exception
            } finally {
                if (aSocket != null) {
                    aSocket.close();
                }
            }
        }
        
    }
}