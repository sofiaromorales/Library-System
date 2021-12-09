/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package shared;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 *
 * @author sofiarodriguezmorales
 */
public interface LibraryRMIInterface extends Remote {
    Book findBook(Book b) throws RemoteException;
    ArrayList<Book> findBookByAuthor(Book b) throws RemoteException;

}
