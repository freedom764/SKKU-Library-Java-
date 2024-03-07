package com.nariman.library;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.awt.Frame;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Student extends User {

   protected String email;
   protected String studentID;
   private final FirebaseDatabase database = FirebaseDatabase.getInstance("https://library-c6bfc-default-rtdb.asia-southeast1.firebasedatabase.app/");
   private String borrowDate;
   private SimpleDateFormat sdf;
   private String returnDate;
   private int publishDate;
   private int borrowersNum;

   public Student(String name, String email, String studentID, String password) {
      super(name, password);
      setEmail(email);
      setStudentID(studentID);

      // TODO Auto-generated constructor stub
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public String getStudentID() {
      return studentID;
   }

   public void setStudentID(String studentID) {
      this.studentID = studentID;
   }

   @Override
   public void addBook(String title, String author, String isbn, String publisher, String yearString, String genre, String numberString, String urlString, JPanel panel, JFrame frame) {
      DatabaseReference refStudent = database.getReference("users");
      Query query = refStudent.orderByChild("studentID").equalTo(studentID);
      query.addListenerForSingleValueEvent(new ValueEventListener() {
         @Override
         public void onDataChange(DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()) {

               for (DataSnapshot issue: dataSnapshot.getChildren()) {

                  name = issue.child("name").getValue().toString();
                  email = issue.child("email").getValue().toString();

               }

            }
            LocalDate currentDate = LocalDate.now();
            borrowDate = currentDate.toString(); // Start date
            sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            try {
               calendar.setTime(sdf.parse(borrowDate));
            } catch (ParseException ex) {
               Logger.getLogger(UserBookPage.class.getName()).log(Level.SEVERE, null, ex);
            }
            DatabaseReference borrowingReference = database.getReference();

            borrowingReference.child("users/" + studentID + "/borrowed/" + isbn + "/isbn").setValueAsync(isbn);
            borrowingReference.child("users/" + studentID + "/borrowed/" + isbn + "/title").setValueAsync(title);
            borrowingReference.child("users/" + studentID + "/borrowed/" + isbn + "/author").setValueAsync(author);
            borrowingReference.child("users/" + studentID + "/borrowed/" + isbn + "/publisher").setValueAsync(publisher);
            borrowingReference.child("users/" + studentID + "/borrowed/" + isbn + "/extended").setValueAsync(0);
            publishDate = Integer.parseInt(yearString);
            borrowingReference.child("users/" + studentID + "/borrowed/" + isbn + "/publishDate").setValueAsync(publishDate);
            borrowingReference.child("users/" + studentID + "/borrowed/" + isbn + "/genre").setValueAsync(genre);
            borrowingReference.child("users/" + studentID + "/borrowed/" + isbn + "/borrowDate").setValueAsync(borrowDate);
            borrowingReference.child("books/" + isbn + "/borrowed/" + studentID + "/borrowDate").setValueAsync(borrowDate);
            borrowingReference.child("history/" + urlString + "/borrowDate").setValueAsync(borrowDate);
            calendar.add(Calendar.DATE, 14); // number of days to add
            borrowDate = sdf.format(calendar.getTime()); // borrow date is now the return date
            borrowingReference.child("users/" + studentID + "/borrowed/" + isbn + "/returnDate").setValueAsync(borrowDate);
            borrowingReference.child("books/" + isbn + "/borrowed/" + studentID + "/returnDate").setValueAsync(borrowDate);
            borrowingReference.child("history/" + urlString + "/returnDate").setValueAsync(borrowDate);
            borrowingReference.child("books/" + isbn + "/borrowed/" + studentID + "/studentID").setValueAsync(studentID);
            borrowingReference.child("books/" + isbn + "/borrowed/" + studentID + "/name").setValueAsync(name);
            borrowingReference.child("books/" + isbn + "/borrowed/" + studentID + "/email").setValueAsync(email);
            borrowersNum = Integer.parseInt(numberString);
            borrowingReference.child("books/" + isbn + "/borrowersNum").setValueAsync(borrowersNum + 1);

            borrowingReference.child("history/" + urlString + "/id").setValueAsync(urlString);
            borrowingReference.child("history/" + urlString + "/studentID").setValueAsync(studentID);
            borrowingReference.child("history/" + urlString + "/name").setValueAsync(name);
            borrowingReference.child("history/" + urlString + "/title").setValueAsync(title);
            borrowingReference.child("history/" + urlString + "/isbn").setValueAsync(isbn);
            borrowingReference.child("history/" + urlString + "/genre").setValueAsync(genre);
            borrowingReference.child("history/" + urlString + "/author").setValueAsync(author);

            JOptionPane.showMessageDialog(null, "Book has been successfully borrowed!", "Success", JOptionPane.INFORMATION_MESSAGE);

            StudentPage studentPage = new StudentPage(studentID);

            if (frame.getExtendedState() != MAXIMIZED_BOTH) {
               studentPage.setSize(frame.getSize());
               studentPage.setLocation(frame.getX(), frame.getY());
            } else {
               studentPage.setLocationRelativeTo(null);
            }
            studentPage.setExtendedState(frame.getExtendedState());

            studentPage.setVisible(true);
            frame.setVisible(false);

         }

         @Override
         public void onCancelled(DatabaseError databaseError1) {
            JOptionPane.showMessageDialog(null, "Canceled", "Warning", JOptionPane.ERROR_MESSAGE);

         }

      });

   }

}