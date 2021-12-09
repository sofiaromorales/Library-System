/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import shared.LibraryRMIInterface;
import shared.Book;

/**
 *
 * @author sofiarodriguezmorales
 */
public class LibraryServer extends UnicastRemoteObject implements LibraryRMIInterface {
    
    monitor XMLBookReader = new monitor();
    
    @Override
    public Book findBook(Book b) throws RemoteException {
        List<Book> libraryBooks = XMLBookReader.getBookList();
        Book response = new Book("");
        for (int i = 0; i < libraryBooks.size(); i++) {
            if (libraryBooks.get(i).getTitle().equals(b.getTitle())) {
                return libraryBooks.get(i);
            }
        }
        return response;
    }
    
    public LibraryServer() throws RemoteException {
        super();
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
