package com.nariman.library;

import javax.swing.JFrame;
import javax.swing.JPanel;

public abstract class User implements Usable {

   protected String name;
   protected String password;

   public User(String name, String password) {

      setName(name);
      setPassword(password);
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public abstract void addBook(String title, String author, String isbn, String publisher, String yearString, String genre, String numberString, String urlString, JPanel panel, JFrame frame);

}