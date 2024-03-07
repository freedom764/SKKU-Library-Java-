package com.nariman.library;

public class Main {

   public static void main(String[] args) {

      new FirebaseConfig().initFirebase();
      LoginPage loginPage = new LoginPage();
      loginPage.setLocationRelativeTo(null);
      loginPage.setVisible(true);

   }

}