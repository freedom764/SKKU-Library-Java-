package com.nariman.library;

import javax.swing.JFrame;
import javax.swing.JPanel;

public interface Usable {

   void addBook(String title, String author, String isbn, String publisher, String yearString, String genre, String numberString, String urlString, JPanel panel, JFrame frame);

}