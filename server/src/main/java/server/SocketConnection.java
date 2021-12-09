/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import shared.Book;

/**
 *
 * @author sofiarodriguezmorales
 */
public class SocketConnection {
    public void createSocket() throws RemoteException {
        LibraryServer libraryServer = new LibraryServer();
        Constants constants = new Constants();
        try {  
            ServerSocket ss = new ServerSocket(constants.SOCKET_REMOTE_LIBRARY_PORT);  
            Socket s = ss.accept();
            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dout = new DataOutputStream(s.getOutputStream());
            String[] response = dis.readUTF().split(" ");
            String bookName = response[0] ;
            System.out.println("Looking for = " + bookName); 
            Book book = new Book(bookName);
            libraryServer.registryLog("Get Book: "+ bookName, response[1]);
            Book returnedBook = libraryServer.findBook(book);
            dout.writeUTF(returnedBook.getTitle() + " " + returnedBook.getAuthor());
            dout.flush();
            ss.close();  
        } catch(Exception e) { System.out.println(e); }    
    }
    
    public static void main(String[] args)  {
        
    }
}
