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
            Book returnedBook = new Book();
            if(response[1]=="Get Title:"){
                String bookName = response[1] ;
                System.out.println("Looking for = " + bookName); 
                Book book = new Book(bookName);
                XMLBookReader.registryLog("Get Book: "+ bookName, response[2]);
                returnedBook = XMLBookReader.findBook(book);
            }else{
                String authorName = response[1] ;
                System.out.println("Looking for = " + authorName); 
                Book book = new Book("",authorName);
                XMLBookReader.registryLog("Get Book: "+ authorName, response[2]);
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
