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
import java.util.ArrayList;
import shared.Book;

/**
 *
 * @author sofiarodriguezmorales
 */
public class SocketConnection {
    
        monitor XMLBookReader = new monitor();

    public void createSocket() throws RemoteException {
        LibraryServer libraryServer = new LibraryServer();
        Constants constants = new Constants();
        try {  
            ServerSocket ss = new ServerSocket(constants.SOCKET_REMOTE_LIBRARY_PORT);  
            Socket s = ss.accept();
            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dout = new DataOutputStream(s.getOutputStream());
            String[] response = dis.readUTF().split(" ");
            System.out.println("response");
            System.out.println(response);
            System.out.println("response[1]");
            System.out.println(response[1]);
            System.out.println("response[2]");
            System.out.println(response[2]);
            Book returnedBook = new Book("");
            System.out.println("Title:".equals(response[1]));
            if("Title:".equals(response[1])){
                String bookName = response[2] ;
                System.out.println("bookName > " + bookName); 
                Book book = new Book(bookName);
                System.out.println("XMLBookReader.registryLog"); 
                XMLBookReader.registryLog("Get Book: " + bookName, response[3]);
                returnedBook = XMLBookReader.findBook(book);
            }else{
                String authorName = response[2] ;
                System.out.println("Looking for = " + authorName); 
                Book book = new Book("",authorName);
                XMLBookReader.registryLog("Get Author: "+ authorName, response[3]);
                ArrayList<Book> booksList = XMLBookReader.findBookByAuthor(book);
                returnedBook = booksList.get(0);
            }

            dout.writeUTF("Title "+ returnedBook.getTitle() + " Author " + returnedBook.getAuthor());
            dout.flush();
            ss.close();  
        } catch(Exception e) { System.out.println(e); }    
    }
    
    public static void main(String[] args)  {
        
    }
}
