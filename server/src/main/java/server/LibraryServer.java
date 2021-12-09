/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import shared.LibraryRMIInterface;
import shared.Book;

/**
 *
 * @author sofiarodriguezmorales
 */
public class LibraryServer extends UnicastRemoteObject implements LibraryRMIInterface {
    
    monitor XMLBookReader = new monitor();
    Constants constants = new Constants();
    
    @Override
    public Book findBook(Book b) throws RemoteException {
        List<Book> libraryBooks = XMLBookReader.getBookList();
        Book response = new Book("");
        for (int i = 0; i < libraryBooks.size(); i++) {
            if (libraryBooks.get(i).getTitle().equals(b.getTitle())) {
                response = libraryBooks.get(i);
            }
        }
        this.registryLog("Get Book: "+ b.getTitle(), "My own client");
        return response;
    }
    
    public LibraryServer() throws RemoteException {
        super();
    }
    
    public void registryLog(String message, String origin){
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(constants.LOG_FILE_PATH, true));
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            writer.append(dtf.format(LocalDateTime.now())+" "+message +" "+ "From: " + origin+"\n");
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public ArrayList<Book> findBookByAuthor(Book b) throws RemoteException {
        List<Book> libraryBooks = XMLBookReader.getBookList();
        ArrayList<Book> booksList = new ArrayList<Book>();
        System.out.println(libraryBooks.toString());
        for (int i = 0; i < libraryBooks.size(); i++) {
            if (libraryBooks.get(i).getAuthor().equals(b.getAuthor())) {
                booksList.add(libraryBooks.get(i));
            }
        }
        this.registryLog("Get Author: "+ b.getAuthor(), "My own client");
        return booksList;
        
        
    }
    public static void main(String[] args) throws IOException {
        Constants constants = new Constants();
        try {
             Registry reg = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
             LibraryServer library = new LibraryServer();
             reg.rebind("rmi://" + constants.CLIENT_IP + "/service", library);
             System.out.println("Server running...");
             SocketConnection socketConnection = new SocketConnection();
             socketConnection.createSocket();
        } catch (RemoteException ex) {
            System.out.println(ex.getMessage());
        }
       
    }
    
}
