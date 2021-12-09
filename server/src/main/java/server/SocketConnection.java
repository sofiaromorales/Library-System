/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;

import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author sofiarodriguezmorales
 */
public class SocketConnection {
    public static void main(String[] args)  {
        try {
            ServerSocket ss = new ServerSocket(666);
            Socket s = ss.accept();
            DataInputStream dis = new DataInputStream(s.getInputStream());
            String message = (String) dis.readUTF();
            System.out.println("Client says = " + message);
            ss.close();
        } catch(Exception e) {
            System.out.println(e);
        }
    }
}
