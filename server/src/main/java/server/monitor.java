/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import shared.Book;

/**
 *
 * @author sofiarodriguezmorales
 */
public class monitor {
    
    List<Book> bookList = new ArrayList<Book>();
    
    public void readLibraryXML() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {    
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File("src/main/java/server/library.xml"));
            doc.getDocumentElement().normalize();
            Element root = doc.getDocumentElement();
            NodeList nodeList = doc.getElementsByTagName("book");
            System.out.println("==============SERVER BOOKS==============");
            for (int i = 0; i < nodeList.getLength(); i++) {
                System.out.println("");
                bookList.add(getBook(nodeList.item(i)));
            }
        } catch (ParserConfigurationException ex) {
            ex.printStackTrace();
            //Logger.getLogger(XMLReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(monitor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(monitor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String[] args) {
        monitor XMLBookReader = new monitor();
        XMLBookReader.readLibraryXML();
    }
    
    private static Book getBook(Node node) {
        if (node.getNodeType() == node.ELEMENT_NODE) {
           Element element = (Element) node;
           Book book = new Book(
                getTagValue("libro", element), 
                getTagValue("autor", element)
           );
           System.out.println(book.toString());
           return book;
        }
        return new Book();
    }
    
    private static String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodeList.item(0);
        return node.getNodeValue();
    }
    
    public synchronized List<Book> getBookList() {
        readLibraryXML();
        return bookList;
    }
    
    
}
