package com.nariman.library;

import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLaf;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.commons.validator.routines.EmailValidator;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.UserRecord.CreateRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import javax.swing.JPasswordField;

import javax.swing.JScrollPane;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Component;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.util.Collections;

import javax.swing.ImageIcon;

import javax.swing.BoxLayout;
import javax.swing.SwingWorker;
import javax.swing.UIManager;

public class RegistrationPage extends JFrame {

   private JPanel contentPane;
   private JButton btnNewButton;
   private JPanel panel;
   private JPanel panel_B;
   private JScrollPane scrollPane;
   private JLabel lblNewLabel_1;
   private JLabel createLabel;

   private JTextField textField;
   private JTextField textField_1;
   private JTextField studentIDField;
   private JPasswordField passwordField;
   private JPasswordField passwordField_1;
   private GridBagConstraints gbc;
   private JLabel emailLabel;
   private JLabel nameLabel;
   private JLabel studentIDLabel;
   private JLabel passwordLabel;
   private JLabel repasswordLabel;
   private JButton registerButton;
   private JButton backButton;
   private int flag;
   private int counter;
   private final FirebaseDatabase database = FirebaseDatabase.getInstance("https://library-c6bfc-default-rtdb.asia-southeast1.firebasedatabase.app/");
   /**
    * Launch the application.
    */

   /**
    * Create the frame.
    */
   public RegistrationPage() {
      UIManager.put("Component.focusWidth", 1);
      UIManager.put("Button.borderColor", new Color(255, 255, 255));

      DatabaseReference ref = database.getReference("users");

      FlatLaf.setGlobalExtraDefaults(Collections.singletonMap("@accentColor", "#fff"));
      FlatIntelliJLaf.setup();
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setBounds(100, 100, 952, 714);
      contentPane = new JPanel();
      contentPane.setBackground(Color.WHITE);

      setContentPane(contentPane);
      contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));

      panel = new JPanel();
      panel.setMaximumSize(new Dimension(300, 32767));
      panel.setMinimumSize(new Dimension(30, 40));
      panel.setBackground(new Color(60, 179, 113));
      panel.setPreferredSize(new Dimension(300, 800));
      contentPane.add(panel);
      panel.setLayout(new GridBagLayout());

      lblNewLabel_1 = new JLabel("");
      lblNewLabel_1.setAlignmentX(0.5f);
      lblNewLabel_1.setIcon(new ImageIcon(RegistrationPage.class.getResource("/icones/icons8-signing-a-document-100.png")));
      GridBagConstraints gridbc1 = new GridBagConstraints();
      gridbc1.gridx = 0;
      gridbc1.gridy = 0;
      gridbc1.weighty = 1.0;
      panel.add(lblNewLabel_1, gridbc1);
      backButton = new JButton("");
      backButton.setIcon(new ImageIcon(RegistrationPage.class.getResource("/icones/icons8-left-arrow-40.png")));
      backButton.setForeground(new Color(255, 255, 255));
      backButton.setBackground(new Color(60, 179, 113));
      backButton.setFont(new Font("Segoe UI", Font.PLAIN, 12));
      backButton.setPreferredSize(new Dimension(180, 40));
      backButton.putClientProperty("JButton.buttonType", "roundRect");

      GridBagConstraints gridbc = new GridBagConstraints();
      gridbc.gridx = 0;
      gridbc.gridy = 1;
      gridbc.insets = new Insets(10, 0, 52, 0);

      panel.add(backButton, gridbc);

      FlatLaf.setGlobalExtraDefaults(Collections.singletonMap("@accentColor", "#3cb371"));
      FlatIntelliJLaf.setup();

      panel_B = new JPanel();
      panel_B.setBackground(Color.WHITE);

      scrollPane = new JScrollPane(panel_B);
      panel_B.setLayout(new GridBagLayout());
      gbc = new GridBagConstraints();

      gbc.insets = new Insets(10, 0, 10, 0);
      createLabel = new JLabel("Create Account");
      createLabel.setForeground(new Color(60, 179, 113));
      createLabel.setFont(new Font("Segoe UI", Font.BOLD, 30));
      GridBagConstraints gbcon = new GridBagConstraints();

      gbcon.gridx = 0;
      gbcon.gridy = 0;
      gbcon.insets = new Insets(0, 0, 40, 0);
      panel_B.add(createLabel, gbcon);

      emailLabel = new JLabel("Enter your email address:");
      emailLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
      emailLabel.setForeground(new Color(60, 179, 113));
      GridBagConstraints gbc0 = new GridBagConstraints();
      gbc0.anchor = GridBagConstraints.LINE_START;

      gbc0.gridx = 0;
      gbc0.gridy = 1;

      panel_B.add(emailLabel, gbc0);

      textField = new JTextField();
      textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

      gbc.gridx = 0;
      gbc.gridy = 2;
      textField.setPreferredSize(new Dimension(240, 40));

      panel_B.add(textField, gbc);

      nameLabel = new JLabel("Enter your full name:");
      nameLabel.setForeground(new Color(60, 179, 113));
      nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
      GridBagConstraints gbc01 = new GridBagConstraints();
      gbc01.anchor = GridBagConstraints.LINE_START;

      gbc01.gridx = 0;
      gbc01.gridy = 3;

      panel_B.add(nameLabel, gbc01);

      GridBagConstraints gbc1 = new GridBagConstraints();
      textField_1 = new JTextField();
      textField_1.setFont(new Font("Segoe UI", Font.PLAIN, 14));
      textField_1.setPreferredSize(new Dimension(240, 40));
      gbc1.gridx = 0;
      gbc1.gridy = 4;
      gbc1.insets = new Insets(10, 0, 10, 0);
      panel_B.add(textField_1, gbc1);

      studentIDLabel = new JLabel("Enter your Student ID number:");
      studentIDLabel.setForeground(new Color(60, 179, 113));
      studentIDLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
      GridBagConstraints gbc_student_label = new GridBagConstraints();
      gbc_student_label.anchor = GridBagConstraints.LINE_START;

      gbc_student_label.gridx = 0;
      gbc_student_label.gridy = 5;

      panel_B.add(studentIDLabel, gbc_student_label);

      GridBagConstraints gbc_student_field = new GridBagConstraints();
      studentIDField = new JTextField();
      studentIDField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
      studentIDField.setPreferredSize(new Dimension(240, 40));
      gbc_student_field.gridx = 0;
      gbc_student_field.gridy = 6;
      gbc_student_field.insets = new Insets(10, 0, 10, 0);
      panel_B.add(studentIDField, gbc_student_field);

      passwordLabel = new JLabel("Enter your password:");
      passwordLabel.setForeground(new Color(60, 179, 113));
      passwordLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
      GridBagConstraints gbc02 = new GridBagConstraints();
      gbc02.anchor = GridBagConstraints.LINE_START;

      gbc02.gridx = 0;
      gbc02.gridy = 7;

      panel_B.add(passwordLabel, gbc02);

      GridBagConstraints gbc2 = new GridBagConstraints();
      passwordField = new JPasswordField();
      passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
      passwordField.setPreferredSize(new Dimension(240, 40));
      gbc2.gridx = 0;
      gbc2.gridy = 8;

      gbc2.insets = new Insets(10, 0, 10, 0);

      panel_B.add(passwordField, gbc2);

      repasswordLabel = new JLabel("Enter your password again:");
      repasswordLabel.setForeground(new Color(60, 179, 113));
      repasswordLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
      GridBagConstraints gbc03 = new GridBagConstraints();
      gbc03.anchor = GridBagConstraints.LINE_START;

      gbc03.gridx = 0;
      gbc03.gridy = 9;

      panel_B.add(repasswordLabel, gbc03);

      GridBagConstraints gbc3 = new GridBagConstraints();
      passwordField_1 = new JPasswordField();
      passwordField_1.setFont(new Font("Segoe UI", Font.PLAIN, 14));
      passwordField_1.setPreferredSize(new Dimension(240, 40));
      gbc3.gridx = 0;
      gbc3.gridy = 10;
      gbc3.insets = new Insets(10, 0, 10, 0);

      panel_B.add(passwordField_1, gbc3);

      registerButton = new JButton("SIGN UP");
      registerButton.setBackground(new Color(60, 179, 113));
      registerButton.setForeground(Color.WHITE);
      registerButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
      GridBagConstraints gbc4 = new GridBagConstraints();
      gbc4.gridx = 0;
      gbc4.gridy = 11;
      gbc4.insets = new Insets(40, 0, 10, 0);
      registerButton.setPreferredSize(new Dimension(240, 40));
      panel_B.add(registerButton, gbc4);

      registerButton.putClientProperty("JButton.buttonType", "roundRect");

      scrollPane.setBackground(Color.WHITE);
      contentPane.add(scrollPane);
      scrollPane.setBorder(null);

      backButton.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {
            LoginPage login = new LoginPage();

            if (getExtendedState() != MAXIMIZED_BOTH) {
               login.setSize(getSize());
               login.setLocation(getX(), getY());
            } else {
               login.setLocationRelativeTo(null);
            }
            login.setExtendedState(getExtendedState());

            login.setVisible(true);
            setVisible(false);

         }
      });

      registerButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {

            String email = textField.getText().trim();
            String name = textField_1.getText().trim();
            String studentID = studentIDField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
            String password_again = new String(passwordField_1.getPassword()).trim();
            if (email.length() > 0 && name.length() > 0 && studentID.length() > 0 && password.length() > 0 && password_again.length() > 0) {

               if (isEmailValid(email)) {

                  if (studentID.length() == 10 && onlyDigits(studentID)) {
                     if (password.length() > 5) {

                        if (password.equals(password_again)) {
                           counter = 0;
                           for (Component cp: panel_B.getComponents())
                              cp.setEnabled(false);
                           for (Component cp: panel.getComponents())
                              cp.setEnabled(false);
                           final Query query1 = ref.orderByChild("studentID").equalTo(studentID);
                           query1.addListenerForSingleValueEvent(new ValueEventListener() {
                              @Override
                              public void onDataChange(DataSnapshot dataSnapshot1) {
                                 if (dataSnapshot1.exists()) {

                                    counter++;

                                 }

                                 if (counter == 0) {
                                    SwingWorker < Boolean, Void > registrationWorker = new SwingWorker < Boolean, Void > () {
                                       protected Boolean doInBackground() {
                                          CreateRequest request = new CreateRequest()
                                             .setEmail(email)
                                             .setEmailVerified(false)
                                             .setPassword(password)
                                             .setDisplayName(name)
                                             .setDisabled(false);

                                          UserRecord userRecord = null;
                                          try {
                                             flag = 0;
                                             userRecord = FirebaseAuth.getInstance().createUser(request);
                                             if (flag == 0) {
                                                Student student = new Student(name, email, studentID, password);

                                                ref.child(studentID).setValueAsync(student);

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
                                                JOptionPane.showMessageDialog(null, "Account was successfully created", "Sucess",
                                                   JOptionPane.INFORMATION_MESSAGE);
                                                LoginPage login = new LoginPage();

                                                if (getExtendedState() != MAXIMIZED_BOTH) {
                                                   login.setSize(getSize());
                                                   login.setLocation(getX(), getY());
                                                } else {
                                                   login.setLocationRelativeTo(null);
                                                }
                                                login.setExtendedState(getExtendedState());

                                                login.setVisible(true);
                                                setVisible(false);
                                             } else {
                                                for (Component cp: panel_B.getComponents()) {
                                                   cp.setEnabled(true);
                                                }
                                                for (Component cp: panel.getComponents()) {
                                                   cp.setEnabled(true);
                                                }
                                             }
                                          } catch (Exception e) {
                                             JOptionPane.showMessageDialog(null, e.getMessage(), "Error",
                                                JOptionPane.ERROR_MESSAGE);
                                             for (Component cp: panel_B.getComponents()) {
                                                cp.setEnabled(true);
                                             }
                                             for (Component cp: panel.getComponents()) {
                                                cp.setEnabled(true);
                                             }
                                          }
                                       }
                                    };
                                    registrationWorker.execute();
                                 } else {
                                    JOptionPane.showMessageDialog(null, "Student with such user ID is already registered", "Warning", JOptionPane.ERROR_MESSAGE);
                                    for (Component cp: panel_B.getComponents()) {
                                       cp.setEnabled(true);
                                    }
                                    for (Component cp: panel.getComponents()) {
                                       cp.setEnabled(true);
                                    }
                                 }

                              }

                              @Override
                              public void onCancelled(DatabaseError databaseError1) {
                                 JOptionPane.showMessageDialog(null, "Canceled", "Warning", JOptionPane.ERROR_MESSAGE);
                                 for (Component cp: panel_B.getComponents()) {
                                    cp.setEnabled(true);
                                 }
                                 for (Component cp: panel.getComponents()) {
                                    cp.setEnabled(true);
                                 }
                              }
                           });

                        } else {
                           JOptionPane.showMessageDialog(null, "Passwords do not match", "Password",
                              JOptionPane.ERROR_MESSAGE);
                        }

                     } else {
                        JOptionPane.showMessageDialog(null, "Password should have at least 6 characters", "Password",
                           JOptionPane.ERROR_MESSAGE);
                     }
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
      });
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