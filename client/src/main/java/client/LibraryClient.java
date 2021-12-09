/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import static java.lang.System.in;
import static java.lang.System.out;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import shared.LibraryRMIInterface;
import shared.Book;

/**
 *
 * @author sofiarodriguezmorales
 */
public class LibraryClient {
    
    public static void main(String[] args) {
        Constants constants = new Constants();
        try {
            Registry reg = LocateRegistry.getRegistry(constants.SERVER_IP, constants.SERVER_PORT);
            LibraryRMIInterface server = (LibraryRMIInterface) reg.lookup("rmi://localhost/service");
            
            boolean findMore;
            do {
                String[] options = {"Find a book","Find a book by Author","Exit"};
                int choice = JOptionPane.showOptionDialog(null, "Choose an action", "Option dialog",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null, options, options[0]
                );
                JTextField xField = new JTextField(5);
                JTextField yField = new JTextField(5);
                JPanel myPanel = new JPanel();
                myPanel.add(new JLabel("Book name:"));
                myPanel.add(xField);
                myPanel.add(Box.createHorizontalStrut(15));
                myPanel.add(new JLabel("Library:"));
                myPanel.add(yField);
                switch (choice) {
                    case 0: {
                        int result = JOptionPane.showConfirmDialog(
                                null, 
                                myPanel, 
                                "", 
                                JOptionPane.OK_CANCEL_OPTION
                        );
                        //String name = JOptionPane.showInputDialog("Type the name of the book to find");
                        if (yField.getText().isEmpty()) {
                           try {
                                Book response = server.findBook(new Book(xField.getText()));
                                JOptionPane.showMessageDialog(null, "Title: " + response.getTitle() + "\n" + "Author: " + response.getAuthor()); 
                            } catch(NoSuchElementException ex) {
                                JOptionPane.showMessageDialog(null, "Not found");
                            } 
                        } else if (yField.getText().equals(constants.REMOTE_LIBRARY)) {
                            try{      
                                Socket s = new Socket(constants.SOCKET_REMOTE_LIBRARY_IP, constants.SOCKET_REMOTE_LIBRARY_PORT);  
                                DataInputStream din = new DataInputStream(s.getInputStream());  
                                DataOutputStream dout = new DataOutputStream(s.getOutputStream());
                                dout.writeUTF(xField.getText() + " " + constants.LIBRARY);  
                                dout.flush(); 
                                String[] bookInfo = din.readUTF().split(" ");
                                Book response = new Book(bookInfo[0], bookInfo[1]);
                                JOptionPane.showMessageDialog(null, "Title: " + response.getTitle() + "\n" + "Author: " + response.getAuthor()); 
                                dout.close();  
                                s.close();  
                            } catch(Exception e){ System.out.println(e); }  
                        } else {
                            JOptionPane.showMessageDialog(null, "Library doesn't exists"); 
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
                findMore = false;
//                findMore = (JOptionPane.showConfirmDialog(null, "Do you want to exit?", "Exit",
//                        JOptionPane.YES_NO_OPTION
//                        ) == JOptionPane.NO_OPTION);
            } while (findMore);
                    
        } catch (RemoteException | NotBoundException e) {
            System.out.println(e.getMessage());
        }
        
    }
    

}
