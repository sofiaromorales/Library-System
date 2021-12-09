/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import javax.swing.JOptionPane;

import shared.LibraryRMIInterface;
import shared.Book;

/**
 *
 * @author sofiarodriguezmorales
 */
public class LibraryClient {
    public static void main(String[] args) {
        try {
            Registry reg = LocateRegistry.getRegistry("localhost", 1099);
            LibraryRMIInterface server = (LibraryRMIInterface) reg.lookup("rmi://localhost/service");
            
            boolean findMore;
            do {
                String[] options = {"Find a book by title","Find a book by Author", "Exit"};
                int choice = JOptionPane.showOptionDialog(null, "Choose an action", "Option dialog",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null, options, options[0]
                );
                switch (choice) {
                    case 0: {
                        String name = JOptionPane.showInputDialog("Type the name of the book to find");
                        try {
                           Book response = server.findBook(new Book(name));
                           JOptionPane.showMessageDialog(null, "Title : " + response.getTitle() + "\n" + "Author: " + response.getAuthor()); 
                        } catch(NoSuchElementException ex) {
                            JOptionPane.showMessageDialog(null, "Not found");
                        }
                        
                        break;
                    }
                    case 1:{
                        String author = JOptionPane.showInputDialog("Type the name of the author");
                        String aux = "";
                        try{
                            ArrayList<Book> response = server.findBookByAuthor(new Book("",author));
                            for (int i=0;i < response.size();i++){
                                
                                aux = aux + "Title : "+ response.get(i).getTitle()+ "\n"+ "Author: "+ response.get(i).getAuthor()+ "\n\n";
                               
                            }
                            JOptionPane.showMessageDialog(null,aux);
                        }catch(NoSuchElementException ex){
                            JOptionPane.showMessageDialog(null, "Not found");
                        }
                    }
                    default: 
                       System.exit(0);
                       break;
                }
                findMore = (JOptionPane.showConfirmDialog(null, "Do you want to exit?", "Exit",
                        JOptionPane.YES_NO_OPTION
                        ) == JOptionPane.NO_OPTION);
            } while (findMore);
                    
        } catch (RemoteException | NotBoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
