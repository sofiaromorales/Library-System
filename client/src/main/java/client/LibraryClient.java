/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package client;

import java.io.DataOutputStream;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
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
        try {
            Registry reg = LocateRegistry.getRegistry("10.0.1.54", 1099);
            LibraryRMIInterface server = (LibraryRMIInterface) reg.lookup("rmi://10.0.1.54/service");
            
            boolean findMore;
            do {
                String[] options = {"Find a book", "Exit"};
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
                        System.out.println(yField.getText());
                        System.out.println(yField.getText().isEmpty());
                        //String name = JOptionPane.showInputDialog("Type the name of the book to find");
                        if (yField.getText().isEmpty()) {
                           try {
                                Book response = server.findBook(new Book(xField.getText()));
                                JOptionPane.showMessageDialog(null, "Title : " + response.getTitle() + "\n" + "Author: " + response.getAuthor()); 
                            } catch(NoSuchElementException ex) {
                                JOptionPane.showMessageDialog(null, "Not found");
                            } 
                        } else {
                            try {
                               Socket socket = new Socket("10.0.1.54", 6666); 
                               DataOutputStream dataStream = new DataOutputStream(socket.getOutputStream());
                               dataStream.writeUTF(xField.getText());
                               dataStream.flush();
                               dataStream.close();
                               socket.close();
                            } catch (Exception e) {
                                System.out.println(e);
                            }

                        }
                        
                        
                        break;
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
