package com.nariman.library;

import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLaf;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Scanner;
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
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Nariman
 */
public class AdminProfile extends JFrame {

   private JPanel contentPane;
   private CardLayout cardLayout;
   private JPanel cardPanel;
   private JPanel topPanel;
   private JPanel loadingPanel;
   private JLabel loadingLabel;
   private JScrollPane scrollPanel;
   private JButton backButton;
   private JPanel adminPanel;
   private JLabel emptyTopLabel;
   private JLabel nameLabel;
   private JLabel historyLabel;
   private JTextArea historyArea;
   private JScrollPane scroll;
   private JButton logoutButton;
   private JButton updateButton;
   private JButton passwordButton;

   final FirebaseDatabase database = FirebaseDatabase.getInstance("https://library-c6bfc-default-rtdb.asia-southeast1.firebasedatabase.app/");
   private String name;

   private JPanel passwordPanel;
   private JLabel passwordLabel;
   private JLabel repasswordLabel;
   private JPasswordField passwordField;
   private JPasswordField passwordField_1;
   private FileOutputStream fileOutput;
   private PrintWriter writer;
   private FileInputStream fileInput;
   private Scanner scanner;

   public AdminProfile(Admin admin) {
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

      adminPanel = new JPanel();
      adminPanel.setLayout(new GridBagLayout());
      adminPanel.setBackground(new Color(255, 255, 255));

      scrollPanel = new JScrollPane(adminPanel);
      cardPanel.add(scrollPanel, "profile");

      nameLabel = new JLabel("Welcome, Nariman Abubakirov");
      nameLabel.setForeground(new Color(60, 179, 113));
      nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));

      GridBagConstraints gbc_name = new GridBagConstraints();
      gbc_name.gridx = 0;
      gbc_name.gridy = 0;
      gbc_name.insets = new Insets(30, 0, 0, 0);
      adminPanel.add(nameLabel, gbc_name);

      logoutButton = new JButton("LOG OUT");
      logoutButton.setBackground(new Color(60, 179, 113));
      logoutButton.setForeground(Color.WHITE);
      logoutButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
      logoutButton.putClientProperty("JButton.buttonType", "roundRect");
      logoutButton.setPreferredSize(new Dimension(240, 40));

      GridBagConstraints gbc_logout = new GridBagConstraints();
      gbc_logout.gridx = 0;
      gbc_logout.gridy = 1;
      gbc_logout.insets = new Insets(40, 0, 0, 0);
      adminPanel.add(logoutButton, gbc_logout);

      passwordButton = new JButton("CHANGE PASSWORD");
      passwordButton.setBackground(new Color(60, 179, 113));
      passwordButton.setForeground(Color.WHITE);
      passwordButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
      passwordButton.putClientProperty("JButton.buttonType", "roundRect");
      passwordButton.setPreferredSize(new Dimension(240, 40));

      GridBagConstraints gbc_password = new GridBagConstraints();
      gbc_password.gridx = 0;
      gbc_password.gridy = 2;
      gbc_password.insets = new Insets(10, 0, 0, 0);
      adminPanel.add(passwordButton, gbc_password);

      historyLabel = new JLabel("Borrowing History:");
      historyLabel.setForeground(new Color(60, 179, 113));
      historyLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));

      GridBagConstraints gbc_hist_label = new GridBagConstraints();
      gbc_hist_label.gridx = 0;
      gbc_hist_label.gridy = 3;
      gbc_hist_label.insets = new Insets(40, 0, 0, 0);
      adminPanel.add(historyLabel, gbc_hist_label);

      historyArea = new JTextArea();
      historyArea.setFont(new Font("Segoe UI", Font.PLAIN, 12));
      historyArea.setEditable(false);
      historyArea.setWrapStyleWord(true); //words are wrapped if text goes beyond text area. Using this words will not be cut in the middle and a whole word will be wrapped
      historyArea.setLineWrap(true); //text is wrapped if it goes beyond text area

      scroll = new JScrollPane(historyArea);
      scroll.setBorder(null);
      scroll.setPreferredSize(new Dimension(600, 350));
      scroll.setBackground(Color.WHITE);
      historyArea.setOpaque(true);

      GridBagConstraints gbc_history = new GridBagConstraints();
      gbc_history.gridx = 0;
      gbc_history.gridy = 4;
      gbc_history.weightx = 1.0;

      gbc_history.fill = GridBagConstraints.HORIZONTAL;
      gbc_history.insets = new Insets(10, 0, 0, 0);
      adminPanel.add(scroll, gbc_history);

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

      reference.child("admin").addListenerForSingleValueEvent(new ValueEventListener() {
         @Override
         public void onDataChange(DataSnapshot dataSnapshot1) {
            if (dataSnapshot1.exists()) {

               for (DataSnapshot issue: dataSnapshot1.getChildren()) {

                  name = issue.child("name").getValue().toString();

               }

            }

            nameLabel.setText("Welcome, " + name);

         }

         @Override
         public void onCancelled(DatabaseError databaseError1) {

         }
      });

      reference.child("history").addListenerForSingleValueEvent(new ValueEventListener() {
         @Override
         public void onDataChange(DataSnapshot dataSnapshot1) {
            if (dataSnapshot1.exists()) {
               int counter = 0;
               try {
                  fileOutput = new FileOutputStream("borrowing_history.txt", false);
                  writer = new PrintWriter(fileOutput);

               } catch (Exception ex) {
                  JOptionPane.showMessageDialog(null, ex.getMessage(), "Error",
                     JOptionPane.ERROR_MESSAGE);
               }

               for (DataSnapshot issue: dataSnapshot1.getChildren()) {
                  counter++;
                  String title = issue.child("title").getValue().toString();
                  String isbn = issue.child("isbn").getValue().toString();
                  String name = issue.child("name").getValue().toString();
                  String studentId = issue.child("studentID").getValue().toString();
                  String borrowDate = issue.child("borrowDate").getValue().toString();
                  String returnDate = issue.child("returnDate").getValue().toString();

                  writer.println("------Borrowing #" + counter + "------");
                  writer.println("Book Title: " + title);
                  writer.println("Book ISBN: " + isbn);
                  writer.println("Borrower Name: " + name);
                  writer.println("Student ID: " + studentId);
                  writer.println("Borrow Date: " + borrowDate);
                  writer.println("Return Date: " + returnDate);

               }

            }
            writer.close();
            try {
               fileInput = new FileInputStream("borrowing_history.txt");
               scanner = new Scanner(fileInput);
               while (scanner.hasNext()) {
                  historyArea.setText(historyArea.getText() + "\n" + scanner.nextLine());
               }

            } catch (FileNotFoundException ex) {
               JOptionPane.showMessageDialog(null, ex.getMessage(), "Error",
                  JOptionPane.ERROR_MESSAGE);
            }

            scanner.close();
            cardLayout.show(cardPanel, "profile");
         }

         @Override
         public void onCancelled(DatabaseError databaseError1) {

         }
      });

      backButton.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {
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
                     reference.child("admin/" + "admin" + "/password").setValueAsync(password);
                     JOptionPane.showMessageDialog(null, "Your password was successfully updated", "Success", JOptionPane.INFORMATION_MESSAGE);
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