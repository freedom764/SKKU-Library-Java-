package com.nariman.library;

import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLaf;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.awt.CardLayout;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Font;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class AdminPage extends JFrame {

   private JPanel contentPane;
   private JPanel dashboard;
   private JPanel cardPanel;
   private JPanel tableBooksPanel;
   private JPanel tableStudentsPanel;

   private JPanel topBooksPanel;
   private JPanel topStudentsPanel;

   private JPanel loadingPanel;
   private JPanel addBookPanel;
   private JPanel addStudentPanel;
   private JTextField searchBooksField;
   private JTextField searchStudentsField;
   private JLabel searchBooksLabel;
   private JLabel searchStudentsLabel;
   private JScrollPane booksScrollPane;
   private JScrollPane studentsScrollPane;
   private JTable booksTable;
   private JTable studentsTable;
   private JLabel loadingLabel;
   private JButton profileButton;
   private JButton booksButton;
   private JButton addBookButton;
   private JButton studentsButton;
   private JButton addStudentButton;
   private JLabel emptyLabel;
   private JTextField titleField;
   private JTextField authorField;
   private JTextField isbnField;
   private JTextField publisherField;
   private JTextField publishDateField;
   private JComboBox genreBox;
   private JTextField numberField;
   private JTextField urlField;
   private JButton bookButton;
   private JButton studentButton;
   private JScrollPane bookScrollPane;
   private JScrollPane studentScrollPane;
   private JTextField studentNameField;
   private JTextField studentEmailField;
   private JTextField studentIDField;
   private String title;
   private String author;
   private String isbn;
   private String publisher;
   private String genre;
   private String yearString;
   private String numberString;
   private String urlString;
   private String borrowedBooks;

   private final FirebaseDatabase database = FirebaseDatabase.getInstance("https://library-c6bfc-default-rtdb.asia-southeast1.firebasedatabase.app/");
   private TableRowSorter < TableModel > rowSorter;
   private DefaultTableModel dtmBooks;
   private DefaultTableModel dtmStudents;
   private int counter = 0;
   private String studentEmail;
   private String studentName;
   private String studentID;
   private String name;
   private String studentId;
   private String email;
   private String[] columnBookNames = {
      "Title",
      "Author",
      "ISBN",
      "Publisher",
      "Publication Year",
      "Number of Copies"
   };
   private String[] columnStudentNames = {
      "Name",
      "Student ID",
      "Email"

   };
   private final String[] genres = {
      "Choose a genre",
      "Action and adventure",
      "Alternate history",
      "Anthology",
      "Art/architecture",
      "Autobiography",
      "Biography",
      "Business/economics",
      "Chick lit",
      "Children's",
      "Classic",
      "Comic book",
      "Coming-of-age",
      "Cookbook",
      "Crafts/hobbies",
      "Crime fiction",
      "Diary",
      "Dictionary",
      "Drama",
      "Encyclopedia",
      "Fairytale",
      "Fantasy",
      "Fiction",
      "Graphic novel",
      "Guide",
      "Health/fitness",
      "Historical fiction",
      "History",
      "Home and garden",
      "Horror",
      "Humor",
      "Journal",
      "Math",
      "Memoir",
      "Mystery",
      "Paranormal romance",
      "Philosophy",
      "Picture book",
      "Poetry",
      "Political thriller",
      "Prayer",
      "Religion, spirituality, and new age",
      "Review",
      "Romance",
      "Satire",
      "Science",
      "Science fiction",
      "Self help",
      "Short story",
      "Sports and leisure",
      "Suspense",
      "Textbook",
      "Thriller",
      "Tragedy",
      "Travel",
      "True crime",
      "Western",
      "Young adult"
   };

   /**
    * Launch the application.
    */

   /**
    * Create the frame.
    */
   public AdminPage(Admin admin) {
      System.out.println("wefwefwefwe");
      UIManager.put("Component.focusWidth", 1);
      UIManager.put("Button.borderColor", new Color(255, 255, 255));
      FlatLaf.setGlobalExtraDefaults(Collections.singletonMap("@accentColor", "#3cb371"));
      FlatIntelliJLaf.setup();
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setBounds(100, 100, 952, 714);
      contentPane = new JPanel();

      setContentPane(contentPane);
      contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));

      dashboard = new JPanel();

      dashboard.setMaximumSize(new Dimension(200, 32767));
      dashboard.setMinimumSize(new Dimension(30, 40));
      dashboard.setBackground(new Color(60, 179, 113));
      dashboard.setPreferredSize(new Dimension(200, 800));
      contentPane.add(dashboard);
      dashboard.setLayout(new GridBagLayout());
      GridBagConstraints gbc1 = new GridBagConstraints();
      gbc1.gridx = 0;
      gbc1.gridy = 0;
      gbc1.weightx = 1.0;

      gbc1.fill = GridBagConstraints.BOTH;
      profileButton = new JButton();
      profileButton.setBackground(new Color(60, 179, 113));
      profileButton.setBorder(null);
      profileButton.setPreferredSize(new Dimension(200, 150));
      profileButton.setIcon(new ImageIcon(this.getClass().getResource("/icones/icons8-male-user-90.png")));
      dashboard.add(profileButton, gbc1);

      booksButton = new JButton("BOOKS");
      booksButton.setIcon(new ImageIcon(this.getClass().getResource("/icones/icons8-books-30.png")));
      booksButton.setForeground(new Color(255, 255, 255));
      booksButton.setBackground(new Color(60, 179, 113));
      booksButton.setBorder(null);
      booksButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
      booksButton.setPreferredSize(new Dimension(200, 50));
      GridBagConstraints gbc2 = new GridBagConstraints();
      gbc2.gridx = 0;
      gbc2.gridy = 1;
      gbc2.weightx = 1.0;

      gbc2.fill = GridBagConstraints.HORIZONTAL;

      dashboard.add(booksButton, gbc2);

      addBookButton = new JButton("ADD BOOK");
      addBookButton.setIcon(new ImageIcon(this.getClass().getResource("/icones/icons8-book-30.png")));
      addBookButton.setForeground(new Color(255, 255, 255));
      addBookButton.setBackground(new Color(60, 179, 113));
      addBookButton.setBorder(null);
      addBookButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
      addBookButton.setPreferredSize(new Dimension(200, 50));
      GridBagConstraints gbc3 = new GridBagConstraints();
      gbc3.gridx = 0;
      gbc3.gridy = 2;
      gbc3.weightx = 1.0;

      gbc3.fill = GridBagConstraints.HORIZONTAL;

      dashboard.add(addBookButton, gbc3);

      studentsButton = new JButton("STUDENTS");
      studentsButton.setIcon(new ImageIcon(this.getClass().getResource("/icones/icons8-people-30.png")));
      studentsButton.setForeground(new Color(255, 255, 255));
      studentsButton.setBackground(new Color(60, 179, 113));
      studentsButton.setBorder(null);
      studentsButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
      studentsButton.setPreferredSize(new Dimension(200, 50));
      GridBagConstraints gbc4 = new GridBagConstraints();
      gbc4.gridx = 0;
      gbc4.gridy = 3;
      gbc4.weightx = 1.0;

      gbc4.fill = GridBagConstraints.HORIZONTAL;

      dashboard.add(studentsButton, gbc4);

      addStudentButton = new JButton("ADD STUDENT");
      addStudentButton.setIcon(new ImageIcon(this.getClass().getResource("/icones/icons8-add-user-male-30.png")));
      addStudentButton.setForeground(new Color(255, 255, 255));
      addStudentButton.setBackground(new Color(60, 179, 113));
      addStudentButton.setBorder(null);
      addStudentButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
      addStudentButton.setPreferredSize(new Dimension(200, 50));
      GridBagConstraints gbc5 = new GridBagConstraints();
      gbc5.gridx = 0;
      gbc5.gridy = 4;
      gbc5.weightx = 1.0;

      gbc5.fill = GridBagConstraints.HORIZONTAL;

      dashboard.add(addStudentButton, gbc5);

      emptyLabel = new JLabel();
      GridBagConstraints gbc6 = new GridBagConstraints();
      gbc6.gridx = 0;
      gbc6.gridy = 5;
      gbc6.weightx = 1.0;
      gbc6.weighty = 1.0;

      gbc6.fill = GridBagConstraints.BOTH;
      dashboard.add(emptyLabel, gbc6);

      cardPanel = new JPanel();
      contentPane.add(cardPanel);
      CardLayout cl_cardPanel = new CardLayout();
      cardPanel.setLayout(cl_cardPanel);

      tableBooksPanel = new JPanel();
      cardPanel.add(tableBooksPanel, "books");
      tableBooksPanel.setLayout(new BorderLayout(0, 0));

      topBooksPanel = new JPanel();
      topBooksPanel.setLayout(new GridBagLayout());
      tableBooksPanel.add(topBooksPanel, BorderLayout.NORTH);

      searchBooksLabel = new JLabel("Search:");
      searchBooksLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));

      GridBagConstraints gridbc1 = new GridBagConstraints();
      gridbc1.gridx = 0;
      gridbc1.gridy = 0;
      gridbc1.insets = new Insets(5, 5, 5, 5);
      topBooksPanel.add(searchBooksLabel, gridbc1);

      searchBooksField = new JTextField();
      searchBooksField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
      searchBooksField.putClientProperty("JComponent.roundRect", true);

      GridBagConstraints gridbc2 = new GridBagConstraints();
      gridbc2.gridx = 1;
      gridbc2.gridy = 0;
      gridbc2.weightx = 1.0;
      gridbc2.insets = new Insets(5, 5, 5, 5);
      gridbc2.fill = GridBagConstraints.HORIZONTAL;

      topBooksPanel.add(searchBooksField, gridbc2);
      booksTable = new JTable();
      booksTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
      booksTable.setShowHorizontalLines(true);
      booksTable.setRowHeight(50);

      booksScrollPane = new JScrollPane(booksTable);
      booksScrollPane.setBorder(null);
      tableBooksPanel.add(booksScrollPane, BorderLayout.CENTER);

      tableStudentsPanel = new JPanel();
      cardPanel.add(tableStudentsPanel, "students");
      tableStudentsPanel.setLayout(new BorderLayout(0, 0));

      topStudentsPanel = new JPanel();
      topStudentsPanel.setLayout(new GridBagLayout());
      tableStudentsPanel.add(topStudentsPanel, BorderLayout.NORTH);

      searchStudentsLabel = new JLabel("Search:");
      searchStudentsLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));

      GridBagConstraints gridbstd1 = new GridBagConstraints();
      gridbstd1.gridx = 0;
      gridbstd1.gridy = 0;
      gridbstd1.insets = new Insets(5, 5, 5, 5);
      topStudentsPanel.add(searchStudentsLabel, gridbstd1);

      searchStudentsField = new JTextField();
      searchStudentsField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
      searchStudentsField.putClientProperty("JComponent.roundRect", true);

      GridBagConstraints gridstd2 = new GridBagConstraints();
      gridstd2.gridx = 1;
      gridstd2.gridy = 0;
      gridstd2.weightx = 1.0;
      gridstd2.insets = new Insets(5, 5, 5, 5);
      gridstd2.fill = GridBagConstraints.HORIZONTAL;

      topStudentsPanel.add(searchStudentsField, gridstd2);
      studentsTable = new JTable();
      studentsTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));

      studentsTable.setShowHorizontalLines(true);
      studentsTable.setRowHeight(50);

      studentsScrollPane = new JScrollPane(studentsTable);
      studentsScrollPane.setBorder(null);
      tableStudentsPanel.add(studentsScrollPane, BorderLayout.CENTER);

      loadingPanel = new JPanel(new GridBagLayout());
      cardPanel.add(loadingPanel, "loading");
      Icon imgIcon = new ImageIcon(this.getClass().getResource("/icones/Spinner-1.2s-144px (1).gif"));
      loadingLabel = new JLabel();
      loadingLabel.setIcon(imgIcon);
      loadingPanel.add(loadingLabel);
      loadingPanel.setBackground(new Color(255, 255, 255));

      addBookPanel = new JPanel(new GridBagLayout());
      bookScrollPane = new JScrollPane(addBookPanel);
      bookScrollPane.setBorder(null);
      cardPanel.add(bookScrollPane, "add_book");

      GridBagConstraints gbc_title = new GridBagConstraints();
      titleField = new JTextField();
      titleField.setText("Title");
      titleField.setForeground(Color.LIGHT_GRAY);
      titleField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
      gbc_title.insets = new Insets(5, 0, 5, 0);
      gbc_title.gridx = 0;
      gbc_title.gridy = 0;
      titleField.setPreferredSize(new Dimension(240, 40));

      addBookPanel.add(titleField, gbc_title);

      GridBagConstraints gbc_author = new GridBagConstraints();
      authorField = new JTextField();
      authorField.setText("Author");
      authorField.setForeground(Color.LIGHT_GRAY);
      authorField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
      authorField.setPreferredSize(new Dimension(240, 40));
      gbc_author.gridx = 0;
      gbc_author.gridy = 1;

      gbc_author.insets = new Insets(5, 0, 5, 0);

      addBookPanel.add(authorField, gbc_author);

      GridBagConstraints gbc_isbn = new GridBagConstraints();
      isbnField = new JTextField();
      isbnField.setText("ISBN");
      isbnField.setForeground(Color.LIGHT_GRAY);
      isbnField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
      isbnField.setPreferredSize(new Dimension(240, 40));
      gbc_isbn.gridx = 0;
      gbc_isbn.gridy = 2;

      gbc_isbn.insets = new Insets(5, 0, 5, 0);

      addBookPanel.add(isbnField, gbc_isbn);

      GridBagConstraints gbc_publisher = new GridBagConstraints();
      publisherField = new JTextField();
      publisherField.setText("Publisher");
      publisherField.setForeground(Color.LIGHT_GRAY);
      publisherField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
      publisherField.setPreferredSize(new Dimension(240, 40));
      gbc_publisher.gridx = 0;
      gbc_publisher.gridy = 3;

      gbc_publisher.insets = new Insets(5, 0, 5, 0);

      addBookPanel.add(publisherField, gbc_publisher);

      GridBagConstraints gbc_publish_date = new GridBagConstraints();
      publishDateField = new JTextField();
      publishDateField.setText("Publication Year");
      publishDateField.setForeground(Color.LIGHT_GRAY);
      publishDateField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
      publishDateField.setPreferredSize(new Dimension(240, 40));
      gbc_publish_date.gridx = 0;
      gbc_publish_date.gridy = 4;

      gbc_publish_date.insets = new Insets(5, 0, 5, 0);

      addBookPanel.add(publishDateField, gbc_publish_date);

      GridBagConstraints gbc_genre = new GridBagConstraints();
      genreBox = new JComboBox(genres);
      genreBox.setSelectedIndex(0);
      genreBox.setForeground(Color.BLACK);
      genreBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
      genreBox.setPreferredSize(new Dimension(240, 40));
      gbc_genre.gridx = 0;
      gbc_genre.gridy = 5;

      gbc_genre.insets = new Insets(5, 0, 5, 0);

      addBookPanel.add(genreBox, gbc_genre);

      GridBagConstraints gbc_number = new GridBagConstraints();
      numberField = new JTextField();
      numberField.setText("Number of Copies");
      numberField.setForeground(Color.LIGHT_GRAY);
      numberField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
      numberField.setPreferredSize(new Dimension(240, 40));
      gbc_number.gridx = 0;
      gbc_number.gridy = 6;

      gbc_number.insets = new Insets(5, 0, 5, 0);

      addBookPanel.add(numberField, gbc_number);

      GridBagConstraints gbc_url = new GridBagConstraints();
      urlField = new JTextField();
      urlField.setText("URL of Image");
      urlField.setForeground(Color.LIGHT_GRAY);
      urlField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
      urlField.setPreferredSize(new Dimension(240, 40));
      gbc_url.gridx = 0;
      gbc_url.gridy = 7;

      gbc_url.insets = new Insets(5, 0, 5, 0);

      addBookPanel.add(urlField, gbc_url);

      bookButton = new JButton("ADD BOOK");
      bookButton.setBackground(new Color(60, 179, 113));
      bookButton.setForeground(Color.WHITE);
      bookButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
      GridBagConstraints gbc_button = new GridBagConstraints();
      gbc_button.gridx = 0;
      gbc_button.gridy = 8;
      gbc_button.insets = new Insets(40, 0, 10, 0);
      bookButton.setPreferredSize(new Dimension(240, 40));
      addBookPanel.add(bookButton, gbc_button);

      bookButton.putClientProperty("JButton.buttonType", "roundRect");

      dtmBooks = new DefaultTableModel(columnBookNames, 0);
      dtmStudents = new DefaultTableModel(columnStudentNames, 0);

      cl_cardPanel.show(cardPanel, "loading");
      DatabaseReference refBooks = database.getReference();
      refBooks.child("books").addListenerForSingleValueEvent(new ValueEventListener() {
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
                  String numberOfCopiesString = issue.child("numberOfCopies").getValue().toString();
                  int numberOfCopies = Integer.parseInt(numberOfCopiesString);

                  rowData[0] = title;
                  rowData[1] = author;
                  rowData[2] = isbn;
                  rowData[3] = publisher;
                  rowData[4] = publishDate;
                  rowData[5] = numberOfCopies;

                  dtmBooks.addRow(rowData);
                  counter++;
               }

            }

            booksTable.setModel(dtmBooks);

            rowSorter = new TableRowSorter < > (booksTable.getModel());
            booksTable.setRowSorter(rowSorter);

            cl_cardPanel.show(cardPanel, "books");

            if (counter == 0) {
               JOptionPane.showMessageDialog(null, "There are no books", "Books", JOptionPane.INFORMATION_MESSAGE);
            }
         }

         @Override
         public void onCancelled(DatabaseError databaseError1) {

         }
      });

      studentsButton.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {
            clearAddBook();
            counter = 0;
            dtmStudents.setRowCount(0);
            cl_cardPanel.show(cardPanel, "loading");
            DatabaseReference refBooks = database.getReference();
            refBooks.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
               @Override
               public void onDataChange(DataSnapshot dataSnapshot1) {
                  if (dataSnapshot1.exists()) {

                     for (DataSnapshot issue: dataSnapshot1.getChildren()) {

                        Object[] rowData = new Object[4];
                        name = issue.child("name").getValue().toString();
                        studentId = issue.child("studentID").getValue().toString();
                        email = issue.child("email").getValue().toString();

                        rowData[0] = name;
                        rowData[1] = studentId;
                        rowData[2] = email;

                        dtmStudents.addRow(rowData);
                        counter++;
                     }

                  }

                  studentsTable.setModel(dtmStudents);

                  rowSorter = new TableRowSorter < > (studentsTable.getModel());
                  studentsTable.setRowSorter(rowSorter);

                  studentsTable.setDefaultEditor(Object.class, null);

                  cl_cardPanel.show(cardPanel, "students");

                  if (counter == 0) {
                     JOptionPane.showMessageDialog(null, "There are no students", "Students", JOptionPane.INFORMATION_MESSAGE);
                  }

               }

               @Override
               public void onCancelled(DatabaseError databaseError1) {

               }
            });

         }
      });

      booksButton.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {
            clearAddBook();
            counter = 0;
            dtmBooks.setRowCount(0);
            cl_cardPanel.show(cardPanel, "loading");
            DatabaseReference refBooks = database.getReference();
            refBooks.child("books").addListenerForSingleValueEvent(new ValueEventListener() {
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
                        String numberOfCopiesString = issue.child("numberOfCopies").getValue().toString();
                        int numberOfCopies = Integer.parseInt(numberOfCopiesString);

                        rowData[0] = title;
                        rowData[1] = author;
                        rowData[2] = isbn;
                        rowData[3] = publisher;
                        rowData[4] = publishDate;
                        rowData[5] = numberOfCopies;

                        dtmBooks.addRow(rowData);
                        counter++;
                     }

                  }
                  booksTable.setModel(dtmBooks);

                  rowSorter = new TableRowSorter < > (booksTable.getModel());
                  booksTable.setRowSorter(rowSorter);

                  booksTable.setDefaultEditor(Object.class, null);

                  cl_cardPanel.show(cardPanel, "books");

                  if (counter == 0) {
                     JOptionPane.showMessageDialog(null, "There are no books", "Books", JOptionPane.INFORMATION_MESSAGE);
                  }
               }

               @Override
               public void onCancelled(DatabaseError databaseError1) {

               }
            });
         }
      });

      searchBooksField.getDocument().addDocumentListener(new DocumentListener() {

         @Override
         public void insertUpdate(DocumentEvent e) {
            String text = searchBooksField.getText();

            if (text.trim().length() == 0) {
               rowSorter.setRowFilter(null);
            } else {
               rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
            }
         }

         @Override
         public void removeUpdate(DocumentEvent e) {
            String text = searchBooksField.getText();

            if (text.trim().length() == 0) {
               rowSorter.setRowFilter(null);
            } else {
               rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
            }
         }

         @Override
         public void changedUpdate(DocumentEvent e) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
         }

      });

      searchStudentsField.getDocument().addDocumentListener(new DocumentListener() {

         @Override
         public void insertUpdate(DocumentEvent e) {
            String text = searchStudentsField.getText();

            if (text.trim().length() == 0) {
               rowSorter.setRowFilter(null);
            } else {
               rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
            }
         }

         @Override
         public void removeUpdate(DocumentEvent e) {
            String text = searchStudentsField.getText();

            if (text.trim().length() == 0) {
               rowSorter.setRowFilter(null);
            } else {
               rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
            }
         }

         @Override
         public void changedUpdate(DocumentEvent e) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
         }

      });

      titleField.addFocusListener(new FocusListener() { //this is focus listener reacts when text area is in focus (it is pressed)

         @Override
         public void focusGained(FocusEvent e) { //when text area is in focus
            if (titleField.getText().trim().equals("Title")) { //if inside text area there is a hint text "At least 100 words..."
               titleField.setText(""); //this hint text gets deleted
               titleField.setForeground(Color.BLACK); //color of the text in text area becomes blank
            }
         }

         @Override
         public void focusLost(FocusEvent e) { //when text area is not in focus
            if (titleField.getText().trim().equals("")) { //if there is no text in the text area (user did not enter anything)
               titleField.setText("Title"); //hint text is shown in the text area again
               titleField.setForeground(Color.LIGHT_GRAY); //and the hint text has a gray color
            }
         }

      });

      authorField.addFocusListener(new FocusListener() {

         @Override
         public void focusGained(FocusEvent e) {
            if (authorField.getText().trim().equals("Author")) {
               authorField.setText("");
               authorField.setForeground(Color.BLACK);
            }
         }

         @Override
         public void focusLost(FocusEvent e) {
            if (authorField.getText().trim().equals("")) {
               authorField.setText("Author");
               authorField.setForeground(Color.LIGHT_GRAY);
            }
         }

      });

      isbnField.addFocusListener(new FocusListener() {

         @Override
         public void focusGained(FocusEvent e) {
            if (isbnField.getText().trim().equals("ISBN")) {
               isbnField.setText("");
               isbnField.setForeground(Color.BLACK);
            }
         }

         @Override
         public void focusLost(FocusEvent e) {
            if (isbnField.getText().trim().equals("")) {
               isbnField.setText("ISBN");
               isbnField.setForeground(Color.LIGHT_GRAY);
            }
         }

      });

      publisherField.addFocusListener(new FocusListener() {

         @Override
         public void focusGained(FocusEvent e) {
            if (publisherField.getText().trim().equals("Publisher")) {
               publisherField.setText("");
               publisherField.setForeground(Color.BLACK);
            }
         }

         @Override
         public void focusLost(FocusEvent e) {
            if (publisherField.getText().trim().equals("")) {
               publisherField.setText("Publisher");
               publisherField.setForeground(Color.LIGHT_GRAY);
            }
         }

      });

      publishDateField.addFocusListener(new FocusListener() {

         @Override
         public void focusGained(FocusEvent e) {
            if (publishDateField.getText().trim().equals("Publication Year")) {
               publishDateField.setText("");
               publishDateField.setForeground(Color.BLACK);
            }
         }

         @Override
         public void focusLost(FocusEvent e) {
            if (publishDateField.getText().trim().equals("")) {
               publishDateField.setText("Publication Year");
               publishDateField.setForeground(Color.LIGHT_GRAY);
            }
         }

      });
      numberField.addFocusListener(new FocusListener() {

         @Override
         public void focusGained(FocusEvent e) {
            if (numberField.getText().trim().equals("Number of Copies")) {
               numberField.setText("");
               numberField.setForeground(Color.BLACK);
            }
         }

         @Override
         public void focusLost(FocusEvent e) {
            if (numberField.getText().trim().equals("")) {
               numberField.setText("Number of Copies");
               numberField.setForeground(Color.LIGHT_GRAY);
            }
         }

      });
      urlField.addFocusListener(new FocusListener() {

         @Override
         public void focusGained(FocusEvent e) {
            if (urlField.getText().trim().equals("URL of Image")) {
               urlField.setText("");
               urlField.setForeground(Color.BLACK);
            }
         }

         @Override
         public void focusLost(FocusEvent e) {
            if (urlField.getText().trim().equals("")) {
               urlField.setText("URL of Image");
               urlField.setForeground(Color.LIGHT_GRAY);
            }
         }

      });

      addStudentPanel = new JPanel(new GridBagLayout());
      studentScrollPane = new JScrollPane(addStudentPanel);
      studentScrollPane.setBorder(null);
      cardPanel.add(studentScrollPane, "add_student");

      GridBagConstraints gbc_email = new GridBagConstraints();
      studentEmailField = new JTextField();
      studentEmailField.setText("Student's Email");
      studentEmailField.setForeground(Color.LIGHT_GRAY);
      studentEmailField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
      gbc_email.insets = new Insets(10, 0, 10, 0);
      gbc_email.gridx = 0;
      gbc_email.gridy = 0;
      studentEmailField.setPreferredSize(new Dimension(240, 40));

      addStudentPanel.add(studentEmailField, gbc_email);

      GridBagConstraints gbc_name = new GridBagConstraints();
      studentNameField = new JTextField();
      studentNameField.setText("Student's Name");
      studentNameField.setForeground(Color.LIGHT_GRAY);
      studentNameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
      studentNameField.setPreferredSize(new Dimension(240, 40));
      gbc_name.gridx = 0;
      gbc_name.gridy = 1;

      gbc_name.insets = new Insets(10, 0, 10, 0);

      addStudentPanel.add(studentNameField, gbc_name);

      GridBagConstraints gbc_id = new GridBagConstraints();
      studentIDField = new JTextField();
      studentIDField.setText("Student's ID number");
      studentIDField.setForeground(Color.LIGHT_GRAY);
      studentIDField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
      studentIDField.setPreferredSize(new Dimension(240, 40));
      gbc_id.gridx = 0;
      gbc_id.gridy = 2;

      gbc_id.insets = new Insets(10, 0, 10, 0);

      addStudentPanel.add(studentIDField, gbc_id);

      studentButton = new JButton("ADD STUDENT");
      studentButton.setBackground(new Color(60, 179, 113));
      studentButton.setForeground(Color.WHITE);
      studentButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
      GridBagConstraints gbc_student = new GridBagConstraints();
      gbc_student.gridx = 0;
      gbc_student.gridy = 3;
      gbc_student.insets = new Insets(40, 0, 10, 0);
      studentButton.setPreferredSize(new Dimension(240, 40));
      addStudentPanel.add(studentButton, gbc_student);

      studentButton.putClientProperty("JButton.buttonType", "roundRect");

      studentEmailField.addFocusListener(new FocusListener() { //this is focus listener reacts when text area is in focus (it is pressed)

         @Override
         public void focusGained(FocusEvent e) { //when text area is in focus
            if (studentEmailField.getText().trim().equals("Student's Email")) { //if inside text area there is a hint text "At least 100 words..."
               studentEmailField.setText(""); //this hint text gets deleted
               studentEmailField.setForeground(Color.BLACK); //color of the text in text area becomes blank
            }
         }

         @Override
         public void focusLost(FocusEvent e) { //when text area is not in focus
            if (studentEmailField.getText().trim().equals("")) { //if there is no text in the text area (user did not enter anything)
               studentEmailField.setText("Student's Email"); //hint text is shown in the text area again
               studentEmailField.setForeground(Color.LIGHT_GRAY); //and the hint text has a gray color
            }
         }

      });

      studentNameField.addFocusListener(new FocusListener() {

         @Override
         public void focusGained(FocusEvent e) {
            if (studentNameField.getText().trim().equals("Student's Name")) {
               studentNameField.setText("");
               studentNameField.setForeground(Color.BLACK);
            }
         }

         @Override
         public void focusLost(FocusEvent e) {
            if (studentNameField.getText().trim().equals("")) {
               studentNameField.setText("Student's Name");
               studentNameField.setForeground(Color.LIGHT_GRAY);
            }
         }

      });

      studentIDField.addFocusListener(new FocusListener() {

         @Override
         public void focusGained(FocusEvent e) {
            if (studentIDField.getText().trim().equals("Student's ID number")) {
               studentIDField.setText("");
               studentIDField.setForeground(Color.BLACK);
            }
         }

         @Override
         public void focusLost(FocusEvent e) {
            if (studentIDField.getText().trim().equals("")) {
               studentIDField.setText("Student's ID number");
               studentIDField.setForeground(Color.LIGHT_GRAY);
            }
         }

      });

      addStudentPanel.setBackground(new Color(255, 255, 255));
      addBookPanel.setBackground(new Color(255, 255, 255));
      tableBooksPanel.setBackground(new Color(255, 255, 255));

      addBookButton.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {
            cl_cardPanel.show(cardPanel, "add_book");
         }
      });

      addStudentButton.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {
            clearAddBook();
            cl_cardPanel.show(cardPanel, "add_student");
         }
      });

      studentButton.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {

            studentEmail = studentEmailField.getText().trim();
            studentName = studentNameField.getText().trim();
            studentID = studentIDField.getText().trim();

            admin.addStudent(studentEmail, studentName, studentID, addStudentPanel, studentEmailField, studentNameField, studentIDField);

         }
      });

      bookButton.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {

            title = titleField.getText().trim();
            author = authorField.getText().trim();
            isbn = isbnField.getText().trim();
            publisher = publisherField.getText().trim();
            yearString = publishDateField.getText().trim();
            genre = (String) genreBox.getSelectedItem();
            numberString = numberField.getText().trim();
            urlString = urlField.getText().trim();
            System.out.println(genre);
            admin.addBook(title, author, isbn, publisher, yearString, genre, numberString, urlString, addBookPanel, null);

         }
      });
      booksTable.addMouseListener(new MouseAdapter() {
         public void mousePressed(MouseEvent mouseEvent) {
            JTable table = (JTable) mouseEvent.getSource();
            Point point = mouseEvent.getPoint();
            int row = table.rowAtPoint(point);
            if (mouseEvent.getClickCount() == 2 && row != -1) {
               AdminBookPage bookPage = new AdminBookPage(table.getValueAt(row, 2).toString(), admin);

               if (getExtendedState() != MAXIMIZED_BOTH) {
                  bookPage.setSize(getSize());
                  bookPage.setLocation(getX(), getY());
               } else {
                  bookPage.setLocationRelativeTo(null);
               }
               bookPage.setExtendedState(getExtendedState());

               bookPage.setVisible(true);
               setVisible(false);
            }
         }
      });
      profileButton.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {
            AdminProfile adminProfile = new AdminProfile(admin);

            if (getExtendedState() != MAXIMIZED_BOTH) {
               adminProfile.setSize(getSize());
               adminProfile.setLocation(getX(), getY());
            } else {
               adminProfile.setLocationRelativeTo(null);
            }
            adminProfile.setExtendedState(getExtendedState());

            adminProfile.setVisible(true);
            setVisible(false);

         }
      });

   }

   public void clearAddBook() {
      titleField.setText("Title");
      titleField.setForeground(Color.LIGHT_GRAY);
      authorField.setText("Author");
      authorField.setForeground(Color.LIGHT_GRAY);
      isbnField.setText("ISBN");
      isbnField.setForeground(Color.LIGHT_GRAY);
      publisherField.setText("Publisher");
      publisherField.setForeground(Color.LIGHT_GRAY);
      publishDateField.setText("Publication Year");
      publishDateField.setForeground(Color.LIGHT_GRAY);
      numberField.setText("Number of Copies");
      numberField.setForeground(Color.LIGHT_GRAY);
      urlField.setText("URL of Image");
      urlField.setForeground(Color.LIGHT_GRAY);

   }

}