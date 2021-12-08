/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import shared.LibraryRMIInterface;
import shared.Book;

/**
 *
 * @author sofiarodriguezmorales
 */
public class LibraryServer extends UnicastRemoteObject implements LibraryRMIInterface {
    
    @Override
    public Book findBook(Book b) throws RemoteException {
        Book response = new Book("Hello");
        return response;
    }
    
    public LibraryServer() throws RemoteException {
        super();
    }
    
    public static void main(String[] args) {
        try {
             Registry reg = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
             LibraryServer library = new LibraryServer();
             reg.rebind("rmi://localhost/service", library);
             System.out.println("Serever running...");
        } catch (RemoteException ex) {
            System.out.println(ex.getMessage());
        }
       
    }
    
}
