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
        Book response = XMLBookReader.findBook(b);
        XMLBookReader.registryLog("Get Book: "+ b.getTitle(), "My own client");
        return response;
    }
    
    public LibraryServer() throws RemoteException {
        super();
    }
    
    
    @Override
    public ArrayList<Book> findBookByAuthor(Book b) throws RemoteException {
        ArrayList<Book> booksList= XMLBookReader.findBookByAuthor(b);
        XMLBookReader.registryLog("Get Author: "+ b.getAuthor(), "My own client");
        return booksList;
    }
    
    public static void main(String[] args) throws IOException {
        Constants constants = new Constants();
        try {
             Registry reg = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
             LibraryServer library = new LibraryServer();
             System.out.println("rmi://" + constants.SERVER_IP + "/service");
             reg.rebind("rmi://" + constants.SERVER_IP + "/service", library);
             System.out.println("Server running...");
             SocketConnection socketConnection = new SocketConnection();
             socketConnection.createSocket();
        } catch (RemoteException ex) {
            System.out.println(ex.getMessage());
        }
       
    }
    
}
