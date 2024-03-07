package com.nariman.library;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import static com.nariman.library.RegistrationPage.isEmailValid;
import static com.nariman.library.RegistrationPage.onlyDigits;
import java.awt.Color;
import java.awt.Component;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.awt.Image;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import org.apache.commons.validator.routines.EmailValidator;

/**
 *
 * @author Nariman
 */

public class Admin extends User {

   protected int year;
   protected int number;
   private int flag;
   private int counter;
   final FirebaseDatabase database = FirebaseDatabase.getInstance("https://library-c6bfc-default-rtdb.asia-southeast1.firebasedatabase.app/");

   public Admin(String name, String password) {
      super(name, password);

      // TODO Auto-generated constructor stub
   }

   @Override
   public void addBook(String title, String author, String isbn, String publisher, String yearString, String genre, String numberString, String urlString, JPanel panel, JFrame frame) {
      if (title.length() > 0 && author.length() > 0 && isbn.length() > 0 && publisher.length() > 0 && yearString.length() > 0 && numberString.length() > 0 && urlString.length() > 0 &&
         !title.equals("Title") && !author.equals("Author") && !isbn.equals("ISBN") &&
         !publisher.equals("Publisher") && !yearString.equals("Publication Year") && !genre.equals("Choose a genre") && !numberString.equals("Number of Copies") && !urlString.equals("URL of Image")) {
         if (validateIsbn13(isbn)) {
            int flag = 0;
            try {
               year = Integer.parseInt(yearString);
               if (year > 2022 || year < 1) {
                  throw new Exception();
               }
            } catch (NumberFormatException ex) {
               JOptionPane.showMessageDialog(null, "Enter a number in Publication Year", "Warning", JOptionPane.ERROR_MESSAGE);
               flag++;
            } catch (Exception e1) {

               JOptionPane.showMessageDialog(null, "Wrong Year", "Warning", JOptionPane.ERROR_MESSAGE);
               flag++;
            }
            try {
               number = Integer.parseInt(numberString);
               if (number < 1) {
                  throw new Exception();
               }
            } catch (NumberFormatException ex) {
               JOptionPane.showMessageDialog(null, "Enter a number in Number of Copies", "Warning", JOptionPane.ERROR_MESSAGE);
               flag++;
            } catch (Exception e1) {
               JOptionPane.showMessageDialog(null, "Wrong Number of Copies", "Warning", JOptionPane.ERROR_MESSAGE);
               flag++;
            }
            if (flag == 0) {
               for (Component cp: panel.getComponents()) {
                  cp.setEnabled(false);
               }

               SwingWorker < Boolean,
                  Void > urlWorker = new SwingWorker < Boolean,
                  Void > () {
                     protected Boolean doInBackground() {
                        if (testImage(urlString)) return true;
                        else return false;

                     }

                     protected void done() {
                        try {
                           Boolean checkBoolean = get();
                           if (checkBoolean) {
                              Book book = new Book(title, author, isbn, publisher, year, genre, number, urlString, 0, 0, 0);
                              DatabaseReference refBook = database.getReference("books");

                              final Query query1 = refBook.orderByChild("isbn").equalTo(isbn);
                              query1.addListenerForSingleValueEvent(new ValueEventListener() {
                                 @Override
                                 public void onDataChange(DataSnapshot dataSnapshot1) {
                                    if (!dataSnapshot1.exists()) {

                                       refBook.child(isbn).setValueAsync(book);

                                       JOptionPane.showMessageDialog(null, "Book was successfully added", "Success", JOptionPane.INFORMATION_MESSAGE);

                                    } else {
                                       JOptionPane.showMessageDialog(null, "Book with such ISBN already exists", "Warning", JOptionPane.ERROR_MESSAGE);
                                    }
                                    for (Component cp: panel.getComponents()) {
                                       cp.setEnabled(true);
                                    }

                                 }

                                 @Override
                                 public void onCancelled(DatabaseError databaseError1) {
                                    JOptionPane.showMessageDialog(null, "Canceled", "Warning", JOptionPane.ERROR_MESSAGE);
                                    for (Component cp: panel.getComponents()) {
                                       cp.setEnabled(true);
                                    }
                                 }
                              });
                           } else {
                              JOptionPane.showMessageDialog(null, "Wrong Image URL", "Warning", JOptionPane.ERROR_MESSAGE);
                              for (Component cp: panel.getComponents()) {
                                 cp.setEnabled(true);
                              }
                           }

                        } catch (Exception e) {
                           JOptionPane.showMessageDialog(null, "Error", "Error", JOptionPane.ERROR_MESSAGE);
                           for (Component cp: panel.getComponents()) {
                              cp.setEnabled(true);
                           }
                        }
                     }
                  };
               urlWorker.execute();
            }

         } else {
            JOptionPane.showMessageDialog(null, "ISBN is incorrect", "Warning", JOptionPane.ERROR_MESSAGE);
         }
      } else {
         JOptionPane.showMessageDialog(null, "Fill all the fields", "Warning", JOptionPane.ERROR_MESSAGE);
      }

   }

   public void addStudent(String studentEmail, String studentName, String studentID, JPanel panel, JTextField textField_1, JTextField textField_2, JTextField textField_3) {
      if (studentEmail.length() > 0 && studentName.length() > 0 && studentID.length() > 0 &&
         !studentEmail.equals("Student's Email") && !studentName.equals("Student's Name") && !studentID.equals("Student's ID number")) {

         if (isEmailValid(studentEmail)) {

            if (studentID.length() == 10 && onlyDigits(studentID)) {

               for (Component cp: panel.getComponents())
                  cp.setEnabled(false);
               counter = 0;
               DatabaseReference refStudent = database.getReference("users");
               Query query1 = refStudent.orderByChild("studentID").equalTo(studentID);
               query1.addListenerForSingleValueEvent(new ValueEventListener() {
                  @Override
                  public void onDataChange(DataSnapshot dataSnapshot1) {
                     if (dataSnapshot1.exists()) {

                        counter++;

                     }

                     if (counter == 0) {
                        SwingWorker < Boolean, Void > registrationWorker = new SwingWorker < Boolean, Void > () {
                           protected Boolean doInBackground() {
                              UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                                 .setEmail(studentEmail)
                                 .setEmailVerified(false)
                                 .setPassword("bestlibrary")
                                 .setDisplayName(studentName)
                                 .setDisabled(false);

                              UserRecord userRecord = null;
                              try {
                                 flag = 0;
                                 userRecord = FirebaseAuth.getInstance().createUser(request);
                                 if (flag == 0) {
                                    Student student = new Student(studentName, studentEmail, studentID, "bestlibrary");

                                    refStudent.child(studentID).setValueAsync(student);

                                    return true;
                                 }

                              } catch (FirebaseAuthException e1) {

                                 flag++;
                                 JOptionPane.showMessageDialog(null, e1.getMessage(), "Error",
                                    JOptionPane.ERROR_MESSAGE);
                                 return false;

                              }
                              return false;
                           }

                           protected void done() {
                              try {
                                 boolean loginSuccessful = get();
                                 if (loginSuccessful) {
                                    JOptionPane.showMessageDialog(null, "Account was successfully created. User's password is \"bestlibrary\" ", "Sucess",
                                       JOptionPane.INFORMATION_MESSAGE);
                                    textField_1.setText("Student's Email");
                                    textField_1.setForeground(Color.LIGHT_GRAY);
                                    textField_2.setText("Student's Name");
                                    textField_2.setForeground(Color.LIGHT_GRAY);
                                    textField_3.setText("Student's ID number");
                                    textField_3.setForeground(Color.LIGHT_GRAY);
                                    for (Component cp: panel.getComponents()) {
                                       cp.setEnabled(true);
                                    }

                                 } else {
                                    for (Component cp: panel.getComponents()) {
                                       cp.setEnabled(true);
                                    }

                                 }
                              } catch (Exception e) {
                                 JOptionPane.showMessageDialog(null, e.getMessage(), "Error",
                                    JOptionPane.ERROR_MESSAGE);
                                 for (Component cp: panel.getComponents()) {
                                    cp.setEnabled(true);
                                 }
                              }
                           }
                        };
                        registrationWorker.execute();
                     } else {
                        JOptionPane.showMessageDialog(null, "Student with such user ID is already registered", "Warning", JOptionPane.ERROR_MESSAGE);
                        for (Component cp: panel.getComponents()) {
                           cp.setEnabled(true);
                        }
                     }

                  }

                  @Override
                  public void onCancelled(DatabaseError databaseError1) {
                     JOptionPane.showMessageDialog(null, "Canceled", "Warning", JOptionPane.ERROR_MESSAGE);
                     for (Component cp: panel.getComponents()) {
                        cp.setEnabled(true);
                     }
                  }
               });

            } else {
               JOptionPane.showMessageDialog(null, "Enter correct Student ID number", "Student ID",
                  JOptionPane.ERROR_MESSAGE);
            }

         } else {
            JOptionPane.showMessageDialog(null, "Email address that you entered is not valid", "Email",
               JOptionPane.ERROR_MESSAGE);
         }

      } else {

         JOptionPane.showMessageDialog(null, "Fill all the fields", "Warning",
            JOptionPane.ERROR_MESSAGE);
      }
   }

   public boolean validateIsbn13(String isbn) {
      if (isbn == null) {
         return false;
      } //remove any hyphens
      isbn = isbn.replaceAll("-", ""); //must be a 13 digit ISBN
      if (isbn.length() != 13) {
         return false;
      }
      try {
         int tot = 0;
         for (int i = 0; i < 12; i++) {
            int digit = Integer.parseInt(isbn.substring(i, i + 1));
            tot += (i % 2 == 0) ? digit * 1 : digit * 3;
         } //checksum must be 0-9. If calculated as 10 then = 0
         int checksum = 10 - (tot % 10);
         if (checksum == 10) {
            checksum = 0;
         }
         return checksum == Integer.parseInt(isbn.substring(12));
      } catch (NumberFormatException nfe) {
         //to catch invalid ISBNs that have non-numeric characters in them
         return false;
      }
   }

   public Boolean testImage(String url) {
      try {
         Image image = ImageIO.read(new URL(url));

         if (image != null) {
            return true;
         } else {
            return false;
         }

      } catch (MalformedURLException e) {
         // TODO Auto-generated catch block  
         System.err.println("URL error with image");
         e.printStackTrace();
         return false;
      } catch (IOException e) {
         System.err.println("IO error with image");
         // TODO Auto-generated catch block  
         e.printStackTrace();
         return false;
      }

   }
   public static boolean isEmailValid(String email) {
      // create the EmailValidator instance
      EmailValidator validator = EmailValidator.getInstance();

      // check for valid email addresses using isValid method
      return validator.isValid(email);
   }
   public static boolean onlyDigits(String str) {
      // Traverse the string from
      // start to end
      for (int i = 0; i < str.length(); i++) {

         // Check if character is
         // digit from 0-9
         // then return true
         // else false
         if (str.charAt(i) >= '0' &&
            str.charAt(i) <= '9') {
            return true;
         } else {
            return false;
         }
      }
      return false;
   }

}