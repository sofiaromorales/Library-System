/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package shared;

import java.io.Serializable;

/**
 *
 * @author sofiarodriguezmorales
 */
public class Book implements Serializable {
    private String title;
    private String author;
    
    public Book(String title) {
        this.title = title;
    }
  
    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public Book() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getAuthor() {
        return author;
    }
    
    @Override
    public String toString() {
        return "Book = Title: " + this.title + " Author: " + this.author;
    }
}
