package com.nariman.library;

import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLaf;
import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.cloud.StorageClient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.internal.NonNull;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

//import com.nariman.library.util.WhiteTheme;
//import com.nariman.library.util.Theme;

import java.awt.Color;
import java.awt.Component;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.awt.Dimension;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.awt.GridLayout;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.commons.validator.routines.EmailValidator;

public class LoginPage extends JFrame {

   private JPanel contentPane;
   private JPanel panel;
   private JPanel panel_1;
   private JLabel lblNewLabel_1;
   private JButton createButton;
   private JButton adminButton;
   private JScrollPane scrollPane;
   public JLabel welcomeLabel1;
   public JLabel welcomeLabel2;
   public JLabel signInLabel;
   private JTextField emailField;
   private JPasswordField pwdPassword;
   private JButton loginButton;
   private String passwordAdmin;
   private String nameAdmin;
   private String passwordStudent;
   private String passwordDatabase;
   private String emailStudent;
   private FirebaseDatabase database;
   private int flag;
   private int flag_another;
   private String studentID;

   /**
    * Launch the application.
    */

   /**
    * Create the frame.
    */
   public LoginPage() {

      database = FirebaseDatabase.getInstance("https://library-c6bfc-default-rtdb.asia-southeast1.firebasedatabase.app/");
      UIManager.put("Component.focusWidth", 1);
      UIManager.put("Button.borderColor", new Color(255, 255, 255));
      FlatLaf.setGlobalExtraDefaults(Collections.singletonMap("@accentColor", "#3cb371"));

      FlatIntelliJLaf.setup();
      setLocationRelativeTo(null);

      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setBounds(100, 100, 1000, 750);
      contentPane = new JPanel();
      contentPane.setBackground(Color.WHITE);

      setContentPane(contentPane);
      contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));

      panel = new JPanel();
      panel.setBackground(Color.WHITE);
      panel.setBorder(new EmptyBorder(0, 0, 0, 0));

      panel.setLayout(new GridBagLayout());

      GridBagConstraints gbc_txtEmail = new GridBagConstraints();

      gbc_txtEmail.insets = new Insets(10, 0, 0, 0);

      signInLabel = new JLabel("Sign In");
      signInLabel.setForeground(new Color(60, 179, 113));
      signInLabel.setFont(new Font("Segoe UI", Font.BOLD, 30));

      GridBagConstraints gbcon = new GridBagConstraints();

      gbcon.gridx = 0;
      gbcon.gridy = 0;
      gbcon.insets = new Insets(0, 0, 40, 0);
      panel.add(signInLabel, gbcon);

      emailField = new JTextField();
      emailField.setText("Email");
      emailField.setForeground(Color.LIGHT_GRAY);
      emailField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

      gbc_txtEmail.gridx = 0;
      gbc_txtEmail.gridy = 1;
      emailField.setPreferredSize(new Dimension(240, 40));

      panel.add(emailField, gbc_txtEmail);

      GridBagConstraints gbc_pwdPassword = new GridBagConstraints();
      pwdPassword = new JPasswordField();
      pwdPassword.setText("Password");
      pwdPassword.setEchoChar((char) 0);
      pwdPassword.setForeground(Color.LIGHT_GRAY);
      pwdPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
      pwdPassword.setPreferredSize(new Dimension(240, 40));
      gbc_pwdPassword.gridx = 0;
      gbc_pwdPassword.gridy = 2;

      gbc_pwdPassword.insets = new Insets(10, 0, 10, 0);

      panel.add(pwdPassword, gbc_pwdPassword);

      loginButton = new JButton("SIGN IN");
      loginButton.setBackground(new Color(60, 179, 113));
      loginButton.setForeground(Color.WHITE);
      loginButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
      GridBagConstraints gbc4 = new GridBagConstraints();
      gbc4.gridx = 0;
      gbc4.gridy = 3;
      gbc4.insets = new Insets(40, 0, 10, 0);
      loginButton.setPreferredSize(new Dimension(240, 40));
      panel.add(loginButton, gbc4);

      loginButton.putClientProperty("JButton.buttonType", "roundRect");

      FlatLaf.setGlobalExtraDefaults(Collections.singletonMap("@accentColor", "#fff"));
      FlatIntelliJLaf.setup();

      scrollPane = new JScrollPane(panel);
      contentPane.add(scrollPane);
      panel_1 = new JPanel();
      panel_1.setPreferredSize(new Dimension(300, 800));
      panel_1.setMaximumSize(new Dimension(300, 32767));
      panel_1.setBackground(new Color(60, 179, 113));
      contentPane.add(panel_1);
      panel_1.setLayout(new GridBagLayout());

      welcomeLabel1 = new JLabel("Welcome to the");
      welcomeLabel1.setFont(new Font("Segoe UI", Font.PLAIN, 30));
      GridBagConstraints gridwelcome = new GridBagConstraints();
      gridwelcome.gridx = 0;
      gridwelcome.gridy = 0;
      gridwelcome.insets = new Insets(120, 0, 0, 0);

      panel_1.add(welcomeLabel1, gridwelcome);

      welcomeLabel1.setForeground(new Color(255, 255, 255));
      welcomeLabel2 = new JLabel("SKKU Library");
      welcomeLabel2.setFont(new Font("Segoe UI", Font.PLAIN, 40));
      GridBagConstraints gridwelcome1 = new GridBagConstraints();
      gridwelcome1.gridx = 0;
      gridwelcome1.gridy = 1;
      gridwelcome1.insets = new Insets(0, 0, 0, 0);

      panel_1.add(welcomeLabel2, gridwelcome1);

      welcomeLabel2.setForeground(new Color(255, 255, 255));

      lblNewLabel_1 = new JLabel("");
      lblNewLabel_1.setAlignmentX(0.5f);
      GridBagConstraints gridbc1 = new GridBagConstraints();
      gridbc1.gridx = 0;
      gridbc1.gridy = 2;
      gridbc1.weighty = 1.0;
      panel_1.add(lblNewLabel_1, gridbc1);
      createButton = new JButton("CREATE ACCOUNT");
      createButton.setForeground(new Color(255, 255, 255));
      createButton.setBackground(new Color(60, 179, 113));
      createButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
      createButton.setPreferredSize(new Dimension(180, 40));
      createButton.putClientProperty("JButton.buttonType", "roundRect");

      GridBagConstraints gridbc = new GridBagConstraints();
      gridbc.gridx = 0;
      gridbc.gridy = 4;
      gridbc.insets = new Insets(10, 0, 52, 0);

      panel_1.add(createButton, gridbc);

      adminButton = new JButton("ADMIN LOGIN");
      adminButton.setForeground(new Color(255, 255, 255));
      adminButton.setBackground(new Color(60, 179, 113));
      adminButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
      adminButton.setPreferredSize(new Dimension(180, 40));
      adminButton.putClientProperty("JButton.buttonType", "roundRect");

      GridBagConstraints gridadmin = new GridBagConstraints();
      gridadmin.gridx = 0;
      gridadmin.gridy = 3;
      gridadmin.insets = new Insets(10, 0, 10, 0);

      panel_1.add(adminButton, gridadmin);

      scrollPane.setBorder(null);

      FlatLaf.setGlobalExtraDefaults(Collections.singletonMap("@accentColor", "#3cb371"));
      FlatIntelliJLaf.setup();

      ImageIcon iconReset = new ImageIcon(LoginPage.class.getResource("/icones/icons8-password-reset-50.png"));
      ImageIcon iconPassword = new ImageIcon(LoginPage.class.getResource("/icones/icons8-password-window-50.png"));
      UIManager.put("Button.borderColor", new Color(60, 179, 113));

      emailField.addFocusListener(new FocusListener() { //this is focus listener reacts when text area is in focus (it is pressed)

         @Override
         public void focusGained(FocusEvent e) { //when text area is in focus
            if (emailField.getText().trim().equals("Email")) { //if inside text area there is a hint text "At least 100 words..."
               emailField.setText(""); //this hint text gets deleted
               emailField.setForeground(Color.BLACK); //color of the text in text area becomes blank
            }
         }

         @Override
         public void focusLost(FocusEvent e) { //when text area is not in focus
            if (emailField.getText().trim().equals("")) { //if there is no text in the text area (user did not enter anything)
               emailField.setText("Email"); //hint text is shown in the text area again
               emailField.setForeground(Color.GRAY); //and the hint text has a gray color
            }
         }

      });
      pwdPassword.addFocusListener(new FocusListener() { //this is focus listener reacts when text area is in focus (it is pressed)

         @Override
         public void focusGained(FocusEvent e) { //when text area is in focus
            if (new String(pwdPassword.getPassword()).trim().equals("Password")) { //if inside text area there is a hint text "At least 100 words..."
               pwdPassword.setText(""); //this hint text gets deleted
               pwdPassword.setForeground(Color.BLACK); //color of the text in text area becomes blank
               pwdPassword.setEchoChar((Character) UIManager.get("PasswordField.echoChar"));
            }
         }

         @Override
         public void focusLost(FocusEvent e) { //when text area is not in focus
            if (new String(pwdPassword.getPassword()).trim().equals("")) { //if there is no text in the text area (user did not enter anything)
               pwdPassword.setText("Password"); //hint text is shown in the text area again
               pwdPassword.setForeground(Color.GRAY); //and the hint text has a gray color
               pwdPassword.setEchoChar((char) 0);
            }
         }

      });

      createButton.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {

            RegistrationPage register = new RegistrationPage();

            if (getExtendedState() != MAXIMIZED_BOTH) {
               register.setSize(getSize());
               register.setLocation(getX(), getY());
            } else {
               register.setLocationRelativeTo(null);
            }
            register.setExtendedState(getExtendedState());

            register.setVisible(true);
            setVisible(false);

         }
      });

      adminButton.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {

            DatabaseReference ref = database.getReference();
            UIManager.put("OptionPane.okButtonText", null);
            String input = (String) JOptionPane.showInputDialog(null, "Enter password:",
               "Admin", JOptionPane.INFORMATION_MESSAGE, iconPassword, null, "");

            if (input != null) {
               for (Component cp: panel_1.getComponents())
                  cp.setEnabled(false);

               for (Component cp: panel.getComponents())
                  cp.setEnabled(false);

               ref.child("admin").addListenerForSingleValueEvent(new ValueEventListener() {
                  @Override
                  public void onDataChange(DataSnapshot dataSnapshot1) {
                     if (dataSnapshot1.exists()) {

                        for (DataSnapshot issue: dataSnapshot1.getChildren()) {

                           passwordAdmin = issue.child("password").getValue().toString();
                           nameAdmin = issue.child("name").getValue().toString();
                        }

                     }

                     if (passwordAdmin.equals(input)) {
                        Admin admin = new Admin(nameAdmin, passwordAdmin);
                        AdminPage adminPage = new AdminPage(admin);

                        if (getExtendedState() != MAXIMIZED_BOTH) {
                           adminPage.setSize(getSize());
                           adminPage.setLocation(getX(), getY());
                        } else {
                           adminPage.setLocationRelativeTo(null);
                        }
                        adminPage.setExtendedState(getExtendedState());

                        adminPage.setVisible(true);
                        setVisible(false);
                     } else {
                        JOptionPane.showMessageDialog(null, "Wrong Password!", "Access Denied",
                           JOptionPane.ERROR_MESSAGE);
                        for (Component cp: panel_1.getComponents()) {
                           cp.setEnabled(true);
                        }

                        for (Component cp: panel.getComponents())
                           cp.setEnabled(true);
                     }
                  }

                  @Override
                  public void onCancelled(DatabaseError databaseError1) {

                  }
               });

            }

         }
      });
      loginButton.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {
            emailStudent = emailField.getText().trim();
            passwordStudent = new String(pwdPassword.getPassword()).trim();

            if (emailStudent.length() > 0 && passwordStudent.length() > 0 && !emailStudent.equals("Email") && !passwordStudent.equals("Password")) {
               if (isEmailValid(emailStudent)) {
                  for (Component cp: panel_1.getComponents()) {
                     cp.setEnabled(false);
                  }

                  for (Component cp: panel.getComponents())
                     cp.setEnabled(false);
                  UserRecord userRecord = null;
                  flag = 0;
                  try {

                     userRecord = FirebaseAuth.getInstance().getUserByEmail(emailStudent);

                  } catch (FirebaseAuthException e1) {

                     flag++;

                  }

                  if (flag == 0) {
                     DatabaseReference refStudent = database.getReference("users");
                     Query query = refStudent.orderByChild("email").equalTo(emailStudent);
                     query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                           if (dataSnapshot.exists()) {

                              for (DataSnapshot issue: dataSnapshot.getChildren()) {

                                 passwordDatabase = issue.child("password").getValue().toString();
                                 studentID = issue.child("studentID").getValue().toString();

                              }

                           }

                           if (passwordDatabase.equals(passwordStudent)) {
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
                              JOptionPane.showMessageDialog(null, "Authentication Error! Check entered Email, Password, and Internet Connection", "Error", JOptionPane.ERROR_MESSAGE);
                              for (Component cp: panel_1.getComponents()) {
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
                           for (Component cp: panel_1.getComponents()) {
                              cp.setEnabled(true);
                           }

                           for (Component cp: panel.getComponents()) {
                              cp.setEnabled(true);
                           }
                        }
                     });
                  } else {
                     JOptionPane.showMessageDialog(null, "Authentication Error! Check entered Email, Password, and Internet Connection", "Error", JOptionPane.ERROR_MESSAGE);
                     for (Component cp: panel_1.getComponents()) {
                        cp.setEnabled(true);
                     }

                     for (Component cp: panel.getComponents()) {
                        cp.setEnabled(true);
                     }
                  }

               } else {
                  JOptionPane.showMessageDialog(null, "Email address that you entered is not valid", "Email",
                     JOptionPane.ERROR_MESSAGE);
               }
            } else {

               JOptionPane.showMessageDialog(null, "Enter Email and Password", "Warning",
                  JOptionPane.ERROR_MESSAGE);
            }
         }

      });

   }
   @Override
   public void setVisible(boolean value) {
      super.setVisible(value);
      signInLabel.requestFocus();
   }

   public static boolean isEmailValid(String email) {
      // create the EmailValidator instance
      EmailValidator validator = EmailValidator.getInstance();

      // check for valid email addresses using isValid method
      return validator.isValid(email);
   }

}