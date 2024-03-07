/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nariman.library;

import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLaf;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.Collections;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class StudentProfile extends JFrame {

   private JPanel contentPane;
   private CardLayout cardLayout;
   private JPanel cardPanel;
   private JPanel topPanel;
   private JPanel loadingPanel;
   private JLabel loadingLabel;
   private JScrollPane scrollPanel;
   private JButton backButton;
   private JPanel studentPanel;
   private JLabel emptyTopLabel;
   private JLabel nameLabel;
   private JLabel studentIdLabel;
   private JLabel emailLabel;
   private JLabel recommendLabel;
   private JLabel historyLabel;
   private JTable historyTable;
   private JTable recommendTable;
   private JScrollPane historyScroll;
   private JScrollPane recommendScroll;
   private DefaultTableModel dtmRecommend;
   private DefaultTableModel dtmHistory;
   private JButton logoutButton;
   private JButton updateButton;
   private JButton passwordButton;
   private TableRowSorter < TableModel > rowSorter;
   final FirebaseDatabase database = FirebaseDatabase.getInstance("https://library-c6bfc-default-rtdb.asia-southeast1.firebasedatabase.app/");
   private String name;
   private String email;
   private String genreHistory;
   private String authorHistory;
   private String isbnHistory;
   private JPanel passwordPanel;
   private JLabel passwordLabel;
   private JLabel repasswordLabel;
   private JPasswordField passwordField;
   private JPasswordField passwordField_1;

   private final String[] columnBookNames = {
      "Title",
      "Author",
      "Genre",
      "Publisher",
      "ISBN",
      "Availability"
   };
   private final String[] columnHistoryNames = {
      "Title",
      "Author",
      "Genre",
      "ISBN",
      "Borrow date",
      "Return date"
   };

   public StudentProfile(String studentID) {
      UIManager.put("Component.focusWidth", 1);
      UIManager.put("Button.borderColor", new Color(255, 255, 255));
      FlatLaf.setGlobalExtraDefaults(Collections.singletonMap("@accentColor", "#3cb371"));
      FlatIntelliJLaf.setup();
      DecimalFormat df = new DecimalFormat();
      df.setMaximumFractionDigits(2);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setBounds(100, 100, 1000, 750);
      contentPane = new JPanel();

      setContentPane(contentPane);

      contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

      topPanel = new JPanel(new GridBagLayout());

      topPanel.setMaximumSize(new Dimension(32767, 50));
      topPanel.setMinimumSize(new Dimension(30, 50));
      topPanel.setBackground(new Color(60, 179, 113));
      topPanel.setPreferredSize(new Dimension(1000, 50));
      contentPane.add(topPanel);
      backButton = new JButton("BACK");
      backButton.setForeground(new Color(255, 255, 255));

      backButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
      backButton.setBackground(new Color(60, 179, 113));
      backButton.setBorder(null);
      backButton.setPreferredSize(new Dimension(100, 50));
      backButton.setIcon(new ImageIcon(this.getClass().getResource("/icones/icons8-back-25.png")));

      GridBagConstraints grid_back = new GridBagConstraints();
      grid_back.gridx = 0;
      grid_back.gridy = 0;

      topPanel.add(backButton, grid_back);

      emptyTopLabel = new JLabel();

      GridBagConstraints grid_emp_lbl = new GridBagConstraints();
      grid_emp_lbl.gridx = 1;
      grid_emp_lbl.gridy = 0;
      grid_emp_lbl.weightx = 1.0;

      grid_emp_lbl.fill = GridBagConstraints.HORIZONTAL;
      topPanel.add(emptyTopLabel, grid_emp_lbl);

      cardLayout = new CardLayout();

      cardPanel = new JPanel(cardLayout);
      cardPanel.setBackground(new Color(255, 255, 255));

      contentPane.add(cardPanel);
      loadingPanel = new JPanel(new GridBagLayout());
      cardPanel.add(loadingPanel, "loading");
      Icon imgIcon = new ImageIcon(this.getClass().getResource("/icones/Spinner-1.2s-144px (1).gif"));
      loadingLabel = new JLabel();
      loadingLabel.setIcon(imgIcon);
      loadingPanel.add(loadingLabel);
      loadingPanel.setBackground(new Color(255, 255, 255));

      studentPanel = new JPanel();
      studentPanel.setLayout(new GridBagLayout());
      studentPanel.setBackground(new Color(255, 255, 255));

      scrollPanel = new JScrollPane(studentPanel);
      cardPanel.add(scrollPanel, "profile");

      nameLabel = new JLabel("Welcome, Nariman Abubakirov");
      nameLabel.setForeground(new Color(60, 179, 113));
      nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));

      GridBagConstraints gbc_name = new GridBagConstraints();
      gbc_name.gridx = 0;
      gbc_name.gridy = 0;
      gbc_name.insets = new Insets(30, 0, 0, 0);
      studentPanel.add(nameLabel, gbc_name);

      studentIdLabel = new JLabel("Student ID: 2021315064");
      studentIdLabel.setForeground(new Color(60, 179, 113));
      studentIdLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
      GridBagConstraints gbc_student_id = new GridBagConstraints();
      gbc_student_id.gridx = 0;
      gbc_student_id.gridy = 1;

      gbc_student_id.insets = new Insets(40, 0, 0, 0);
      studentPanel.add(studentIdLabel, gbc_student_id);

      emailLabel = new JLabel("Email: one@piece.com");
      emailLabel.setForeground(new Color(60, 179, 113));
      emailLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
      GridBagConstraints gbc_email = new GridBagConstraints();
      gbc_email.gridx = 0;
      gbc_email.gridy = 2;

      studentPanel.add(emailLabel, gbc_email);

      logoutButton = new JButton("LOG OUT");
      logoutButton.setBackground(new Color(60, 179, 113));
      logoutButton.setForeground(Color.WHITE);
      logoutButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
      logoutButton.putClientProperty("JButton.buttonType", "roundRect");
      logoutButton.setPreferredSize(new Dimension(240, 40));

      GridBagConstraints gbc_logout = new GridBagConstraints();
      gbc_logout.gridx = 0;
      gbc_logout.gridy = 3;
      gbc_logout.insets = new Insets(40, 0, 0, 0);
      studentPanel.add(logoutButton, gbc_logout);

      passwordButton = new JButton("CHANGE PASSWORD");
      passwordButton.setBackground(new Color(60, 179, 113));
      passwordButton.setForeground(Color.WHITE);
      passwordButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
      passwordButton.putClientProperty("JButton.buttonType", "roundRect");
      passwordButton.setPreferredSize(new Dimension(240, 40));

      GridBagConstraints gbc_password = new GridBagConstraints();
      gbc_password.gridx = 0;
      gbc_password.gridy = 4;
      gbc_password.insets = new Insets(10, 0, 0, 0);
      studentPanel.add(passwordButton, gbc_password);

      recommendLabel = new JLabel("Recommended Books:");
      recommendLabel.setForeground(new Color(60, 179, 113));
      recommendLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));

      GridBagConstraints gbc_rec_label = new GridBagConstraints();
      gbc_rec_label.gridx = 0;
      gbc_rec_label.gridy = 5;
      gbc_rec_label.insets = new Insets(40, 0, 0, 0);
      studentPanel.add(recommendLabel, gbc_rec_label);

      recommendTable = new JTable();
      recommendTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
      recommendTable.setRowHeight(50);
      recommendTable.setDefaultEditor(Object.class, null);

      recommendScroll = new JScrollPane(recommendTable);
      recommendScroll.setBorder(null);
      recommendScroll.setPreferredSize(new Dimension(600, 200));

      GridBagConstraints gbc_recommend = new GridBagConstraints();
      gbc_recommend.gridx = 0;
      gbc_recommend.gridy = 6;
      gbc_recommend.weightx = 1.0;
      gbc_recommend.fill = GridBagConstraints.HORIZONTAL;
      gbc_recommend.insets = new Insets(10, 0, 0, 0);
      studentPanel.add(recommendScroll, gbc_recommend);

      historyLabel = new JLabel("Borrowing History:");
      historyLabel.setForeground(new Color(60, 179, 113));
      historyLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));

      GridBagConstraints gbc_hist_label = new GridBagConstraints();
      gbc_hist_label.gridx = 0;
      gbc_hist_label.gridy = 7;
      gbc_hist_label.insets = new Insets(40, 0, 0, 0);
      studentPanel.add(historyLabel, gbc_hist_label);

      historyTable = new JTable();
      historyTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
      historyTable.setRowHeight(50);
      historyTable.setDefaultEditor(Object.class, null);

      historyScroll = new JScrollPane(historyTable);
      historyScroll.setBorder(null);
      historyScroll.setPreferredSize(new Dimension(600, 200));

      GridBagConstraints gbc_history = new GridBagConstraints();
      gbc_history.gridx = 0;
      gbc_history.gridy = 8;
      gbc_history.weightx = 1.0;
      gbc_history.fill = GridBagConstraints.HORIZONTAL;
      gbc_history.insets = new Insets(10, 0, 30, 0);
      studentPanel.add(historyScroll, gbc_history);

      dtmRecommend = new DefaultTableModel(columnBookNames, 0);
      dtmHistory = new DefaultTableModel(columnHistoryNames, 0);

      passwordPanel = new JPanel();
      passwordPanel.setBackground(Color.WHITE);
      passwordPanel.setLayout(new GridBagLayout());
      cardPanel.add(passwordPanel, "password");

      passwordLabel = new JLabel("Enter new password:");
      passwordLabel.setForeground(new Color(60, 179, 113));
      passwordLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
      GridBagConstraints gbc1 = new GridBagConstraints();

      gbc1.gridx = 0;
      gbc1.gridy = 0;

      passwordPanel.add(passwordLabel, gbc1);

      GridBagConstraints gbc2 = new GridBagConstraints();
      passwordField = new JPasswordField();
      passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
      passwordField.setPreferredSize(new Dimension(240, 40));
      gbc2.gridx = 0;
      gbc2.gridy = 1;

      gbc2.insets = new Insets(10, 0, 10, 0);

      passwordPanel.add(passwordField, gbc2);

      repasswordLabel = new JLabel("Enter new password again:");
      repasswordLabel.setForeground(new Color(60, 179, 113));
      repasswordLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
      GridBagConstraints gbc3 = new GridBagConstraints();

      gbc3.gridx = 0;
      gbc3.gridy = 2;

      passwordPanel.add(repasswordLabel, gbc3);

      GridBagConstraints gbc4 = new GridBagConstraints();
      passwordField_1 = new JPasswordField();
      passwordField_1.setFont(new Font("Segoe UI", Font.PLAIN, 14));
      passwordField_1.setPreferredSize(new Dimension(240, 40));
      gbc4.gridx = 0;
      gbc4.gridy = 3;
      gbc4.insets = new Insets(10, 0, 10, 0);

      passwordPanel.add(passwordField_1, gbc4);

      updateButton = new JButton("UPDATE");
      updateButton.setBackground(new Color(60, 179, 113));
      updateButton.setForeground(Color.WHITE);
      updateButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
      updateButton.putClientProperty("JButton.buttonType", "roundRect");
      updateButton.setPreferredSize(new Dimension(240, 40));

      GridBagConstraints gbc5 = new GridBagConstraints();
      gbc5.gridx = 0;
      gbc5.gridy = 4;
      gbc5.insets = new Insets(40, 0, 0, 0);
      passwordPanel.add(updateButton, gbc5);

      cardLayout.show(cardPanel, "loading");
      DatabaseReference reference = database.getReference();
      System.out.println(studentID);
      Query query = reference.child("users").orderByChild("studentID").equalTo(studentID);
      query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot1) {
               if (dataSnapshot1.exists()) {

                  for (DataSnapshot issue: dataSnapshot1.getChildren()) {

                     name = issue.child("name").getValue().toString();
                     email = issue.child("email").getValue().toString();
                  }

               }

               nameLabel.setText("Welcome, " + name);
               studentIdLabel.setText("Student ID: " + studentID);
               emailLabel.setText("Email: " + email);

            }

            @Override
            public void onCancelled(DatabaseError databaseError1) {

            }
         }

      );

      Query query1 = reference.child("history").orderByChild("studentID").equalTo(studentID);
      query1.addListenerForSingleValueEvent(new ValueEventListener() {
         @Override
         public void onDataChange(DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()) {

               for (DataSnapshot issue: dataSnapshot.getChildren()) {
                  Object[] rowData = new Object[6];
                  genreHistory = issue.child("genre").getValue().toString();
                  authorHistory = issue.child("author").getValue().toString();
                  isbnHistory = issue.child("isbn").getValue().toString();

                  rowData[0] = issue.child("title").getValue().toString();
                  rowData[1] = authorHistory;
                  rowData[2] = genreHistory;
                  rowData[3] = isbnHistory;
                  rowData[4] = issue.child("borrowDate").getValue().toString();
                  rowData[5] = issue.child("returnDate").getValue().toString();
                  dtmHistory.addRow(rowData);
               }
            }

            historyTable.setModel(dtmHistory);

            rowSorter = new TableRowSorter < > (historyTable.getModel());

            historyTable.setRowSorter(rowSorter);

            reference.child("books").addListenerForSingleValueEvent(new ValueEventListener() {
               @Override
               public void onDataChange(DataSnapshot dataSnapshot1) {
                  if (dataSnapshot1.exists()) {

                     for (DataSnapshot issue: dataSnapshot1.getChildren()) {
                        Object[] rowData = new Object[6];
                        String title = issue.child("title").getValue().toString();
                        String author = issue.child("author").getValue().toString();
                        String isbn = issue.child("isbn").getValue().toString();
                        String publisher = issue.child("publisher").getValue().toString();
                        String publishDateString = issue.child("publishDate").getValue().toString();
                        int publishDate = Integer.parseInt(publishDateString);
                        String genre = issue.child("genre").getValue().toString();
                        String borrowersNumString = issue.child("borrowersNum").getValue().toString();
                        int borrowersNum = Integer.parseInt(borrowersNumString);
                        String numberOfCopiesString = issue.child("numberOfCopies").getValue().toString();
                        int numberOfCopies = Integer.parseInt(numberOfCopiesString);

                        if ((genre.equals(genreHistory) || author.equals(authorHistory)) && !isbnHistory.equals(isbn)) {
                           if (borrowersNum < numberOfCopies) {
                              rowData[0] = title;
                              rowData[1] = author;
                              rowData[2] = genre;
                              rowData[3] = publisher + " (" + publishDate + ")";
                              rowData[4] = isbn;
                              rowData[5] = "Available";
                              dtmRecommend.addRow(rowData);
                           } else {
                              rowData[0] = title;
                              rowData[1] = author;
                              rowData[2] = genre;
                              rowData[3] = publisher + " (" + publishDate + ")";
                              rowData[4] = isbn;
                              rowData[5] = "Unavailable";
                              dtmRecommend.addRow(rowData);
                           }
                        }

                     }

                  }

                  recommendTable.setModel(dtmRecommend);

                  rowSorter = new TableRowSorter < > (recommendTable.getModel());
                  recommendTable.setRowSorter(rowSorter);

                  cardLayout.show(cardPanel, "profile");

               }

               @Override
               public void onCancelled(DatabaseError databaseError1) {

               }
            });

         }

         @Override
         public void onCancelled(DatabaseError databaseError1) {
            JOptionPane.showMessageDialog(null, "Canceled", "Warning", JOptionPane.ERROR_MESSAGE);

         }
      });

      backButton.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {
            StudentPage studentPage = new StudentPage(studentID);

            if (getExtendedState() != MAXIMIZED_BOTH) {
               studentPage.setSize(getSize());
               studentPage.setLocation(getX(), getY());
            } else {
               studentPage.setLocationRelativeTo(null);
            }
            studentPage.setExtendedState(getExtendedState());

            studentPage.setVisible(true);
            setVisible(false);

         }

      });

      logoutButton.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {
            LoginPage loginPage = new LoginPage();

            if (getExtendedState() != MAXIMIZED_BOTH) {
               loginPage.setSize(getSize());
               loginPage.setLocation(getX(), getY());
            } else {
               loginPage.setLocationRelativeTo(null);
            }
            loginPage.setExtendedState(getExtendedState());

            loginPage.setVisible(true);
            setVisible(false);

         }

      });

      passwordButton.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {
            cardLayout.show(cardPanel, "password");
         }

      });
      updateButton.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {
            String password = new String(passwordField.getPassword()).trim();
            String password_again = new String(passwordField_1.getPassword()).trim();

            if (password.length() > 0 && password_again.length() > 0) {
               if (password.length() > 5) {
                  if (password.equals(password_again)) {
                     DatabaseReference reference = database.getReference();
                     reference.child("users/" + studentID + "/password").setValueAsync(password);
                     JOptionPane.showMessageDialog(null, "Your password was successfully updated", "Success", JOptionPane.INFORMATION_MESSAGE);
                     StudentPage studentPage = new StudentPage(studentID);

                     if (getExtendedState() != MAXIMIZED_BOTH) {
                        studentPage.setSize(getSize());
                        studentPage.setLocation(getX(), getY());
                     } else {
                        studentPage.setLocationRelativeTo(null);
                     }
                     studentPage.setExtendedState(getExtendedState());

                     studentPage.setVisible(true);
                     setVisible(false);

                  } else {
                     JOptionPane.showMessageDialog(null, "Passwords do not match", "Password",
                        JOptionPane.ERROR_MESSAGE);
                  }
               } else {
                  JOptionPane.showMessageDialog(null, "Password should have at least 6 characters", "Password",
                     JOptionPane.ERROR_MESSAGE);
               }

            } else {
               JOptionPane.showMessageDialog(null, "Fill all the fields", "Warning",
                  JOptionPane.ERROR_MESSAGE);
            }
         }

      });

   }

}