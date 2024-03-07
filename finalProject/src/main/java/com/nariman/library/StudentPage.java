package com.nariman.library;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Collections;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLaf;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class StudentPage extends JFrame {

   private JPanel contentPane;
   private JPanel dashboard;
   private JPanel cardPanel;
   private JPanel tableBooksPanel;
   private JPanel tableBorrowedPanel;

   private JPanel topBooksPanel;
   private JPanel topBorrowedPanel;

   private JPanel loadingPanel;
   private JTextField searchBooksField;
   private JTextField searchBorrowedField;
   private JLabel searchBooksLabel;
   private JLabel searchBorrowedLabel;
   private JScrollPane booksScrollPane;
   private JScrollPane borrowedScrollPane;
   private JTable booksTable;
   private JTable borrowedTable;
   private JLabel loadingLabel;
   private JButton profileButton;
   private JButton booksButton;
   private JButton borrowedButton;
   private JLabel emptyLabel;
   private JCheckBox booksCheckBox;
   private JLabel checkBoxLabel;
   private DefaultTableModel dtmBooks;
   private DefaultTableModel dtmBorrowed;
   private ArrayList < Book > booksList;
   private int counter;
   private TableRowSorter < TableModel > rowSorter;
   final FirebaseDatabase database = FirebaseDatabase.getInstance("https://library-c6bfc-default-rtdb.asia-southeast1.firebasedatabase.app/");
   private final String[] columnBookNames = {
      "Title",
      "Author",
      "Genre",
      "Publisher",
      "ISBN",
      "Availability"
   };
   private final String[] columnBorrowedNames = {
      "Title",
      "Author",
      "Genre",
      "ISBN",
      "Borrow date",
      "Return date"
   };

   public StudentPage(String studentID) {
      UIManager.put("Component.focusWidth", 1);
      UIManager.put("Button.borderColor", new Color(255, 255, 255));
      FlatLaf.setGlobalExtraDefaults(Collections.singletonMap("@accentColor", "#3cb371"));
      FlatIntelliJLaf.setup();
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setBounds(100, 100, 1000, 750);
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
      booksButton.setIcon(new ImageIcon(StudentPage.class.getResource("/icones/icons8-books-30.png")));
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

      borrowedButton = new JButton("BORROWED BOOKS");
      borrowedButton.setIcon(new ImageIcon(this.getClass().getResource("/icones/icons8-borrow-book-30.png")));
      borrowedButton.setForeground(new Color(255, 255, 255));
      borrowedButton.setBackground(new Color(60, 179, 113));
      borrowedButton.setBorder(null);
      borrowedButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
      borrowedButton.setPreferredSize(new Dimension(200, 50));
      GridBagConstraints gbc3 = new GridBagConstraints();
      gbc3.gridx = 0;
      gbc3.gridy = 2;
      gbc3.weightx = 1.0;

      gbc3.fill = GridBagConstraints.HORIZONTAL;

      dashboard.add(borrowedButton, gbc3);

      emptyLabel = new JLabel();
      GridBagConstraints gbc6 = new GridBagConstraints();
      gbc6.gridx = 0;
      gbc6.gridy = 3;
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

      booksCheckBox = new JCheckBox();

      GridBagConstraints gridbc3 = new GridBagConstraints();
      gridbc3.gridx = 2;
      gridbc3.gridy = 0;

      gridbc3.insets = new Insets(5, 10, 5, 5);
      gridbc3.fill = GridBagConstraints.HORIZONTAL;

      topBooksPanel.add(booksCheckBox, gridbc3);

      checkBoxLabel = new JLabel("Show unavailable books");
      checkBoxLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));

      GridBagConstraints gridbc4 = new GridBagConstraints();
      gridbc4.gridx = 3;
      gridbc4.gridy = 0;
      gridbc4.insets = new Insets(5, 0, 5, 5);
      topBooksPanel.add(checkBoxLabel, gridbc4);

      booksTable = new JTable();
      booksTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
      booksTable.setRowHeight(50);
      booksTable.setDefaultEditor(Object.class, null);
      booksTable.setShowHorizontalLines(true);

      booksScrollPane = new JScrollPane(booksTable);
      booksScrollPane.setBorder(null);
      tableBooksPanel.add(booksScrollPane, BorderLayout.CENTER);

      tableBorrowedPanel = new JPanel();
      cardPanel.add(tableBorrowedPanel, "borrowed");
      tableBorrowedPanel.setLayout(new BorderLayout(0, 0));

      topBorrowedPanel = new JPanel();
      topBorrowedPanel.setLayout(new GridBagLayout());
      tableBorrowedPanel.add(topBorrowedPanel, BorderLayout.NORTH);

      searchBorrowedLabel = new JLabel("Search:");
      searchBorrowedLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));

      GridBagConstraints gridbstd1 = new GridBagConstraints();
      gridbstd1.gridx = 0;
      gridbstd1.gridy = 0;
      gridbstd1.insets = new Insets(5, 5, 5, 5);
      topBorrowedPanel.add(searchBorrowedLabel, gridbstd1);

      searchBorrowedField = new JTextField();
      searchBorrowedField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
      searchBorrowedField.putClientProperty("JComponent.roundRect", true);

      GridBagConstraints gridstd2 = new GridBagConstraints();
      gridstd2.gridx = 1;
      gridstd2.gridy = 0;
      gridstd2.weightx = 1.0;
      gridstd2.insets = new Insets(5, 5, 5, 5);
      gridstd2.fill = GridBagConstraints.HORIZONTAL;

      topBorrowedPanel.add(searchBorrowedField, gridstd2);
      borrowedTable = new JTable();
      borrowedTable.setShowHorizontalLines(true);
      borrowedTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
      borrowedTable.setRowHeight(50);
      borrowedTable.setDefaultEditor(Object.class, null);

      borrowedScrollPane = new JScrollPane(borrowedTable);
      borrowedScrollPane.setBorder(null);
      tableBorrowedPanel.add(borrowedScrollPane, BorderLayout.CENTER);

      loadingPanel = new JPanel(new GridBagLayout());
      cardPanel.add(loadingPanel, "loading");
      Icon imgIcon = new ImageIcon(this.getClass().getResource("/icones/Spinner-1.2s-144px (1).gif"));
      loadingLabel = new JLabel();
      loadingLabel.setIcon(imgIcon);
      loadingPanel.add(loadingLabel);
      loadingPanel.setBackground(new Color(255, 255, 255));

      cl_cardPanel.show(cardPanel, "loading");

      dtmBooks = new DefaultTableModel(columnBookNames, 0);
      dtmBorrowed = new DefaultTableModel(columnBorrowedNames, 0);
      booksList = new ArrayList < > ();
      counter = 0;

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
                  String genre = issue.child("genre").getValue().toString();
                  String borrowersNumString = issue.child("borrowersNum").getValue().toString();
                  int borrowersNum = Integer.parseInt(borrowersNumString);
                  String numberOfCopiesString = issue.child("numberOfCopies").getValue().toString();
                  int numberOfCopies = Integer.parseInt(numberOfCopiesString);

                  booksList.add(new Book(title, author, isbn, publisher, publishDate, genre, numberOfCopies, "no", 0, 0, borrowersNum));

                  rowData[0] = title;
                  rowData[1] = author;
                  rowData[2] = genre;
                  rowData[3] = publisher + " (" + publishDate + ")";
                  rowData[4] = isbn;

                  if (borrowersNum < numberOfCopies) {
                     counter++;
                     rowData[5] = "Available";
                     dtmBooks.addRow(rowData);
                  }

               }

            }

            booksTable.setModel(dtmBooks);

            rowSorter = new TableRowSorter < > (booksTable.getModel());
            booksTable.setRowSorter(rowSorter);

            cl_cardPanel.show(cardPanel, "books");

            if (counter == 0) {
               JOptionPane.showMessageDialog(null, "There are no available books", "Books", JOptionPane.INFORMATION_MESSAGE);
            }
         }

         @Override
         public void onCancelled(DatabaseError databaseError1) {

         }
      });

      booksCheckBox.addItemListener(new ItemListener() {
         @Override
         public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) { //checkbox has been selected

               dtmBooks.setRowCount(0);
               Object[] rowData = new Object[6];
               //cl_cardPanel.show(cardPanel, "loading");

               for (int i = 0; i < booksList.size(); i++) {
                  Book book = booksList.get(i);
                  rowData[0] = book.getTitle();
                  rowData[1] = book.getAuthor();
                  rowData[2] = book.getGenre();
                  rowData[3] = book.getPublisher() + " (" + book.getPublishDate() + ")";
                  rowData[4] = book.getIsbn();
                  if (book.getBorrowersNum() < book.getNumberOfCopies()) {
                     rowData[5] = "Available";
                     dtmBooks.addRow(rowData);
                  } else {

                     rowData[5] = "Unavailable";
                     dtmBooks.addRow(rowData);
                  }

               }

               booksTable.setModel(dtmBooks);

               rowSorter = new TableRowSorter < > (booksTable.getModel());
               booksTable.setRowSorter(rowSorter);

            } else { //checkbox has been deselected

               dtmBooks.setRowCount(0);
               Object[] rowData = new Object[6];
               //cl_cardPanel.show(cardPanel, "loading");

               for (int i = 0; i < booksList.size(); i++) {
                  Book book = booksList.get(i);
                  rowData[0] = book.getTitle();
                  rowData[1] = book.getAuthor();
                  rowData[2] = book.getGenre();
                  rowData[3] = book.getPublisher() + " (" + book.getPublishDate() + ")";
                  rowData[4] = book.getIsbn();

                  if (book.getBorrowersNum() < book.getNumberOfCopies()) {
                     rowData[5] = "Available";
                     dtmBooks.addRow(rowData);
                  }

               }
               booksTable.setModel(dtmBooks);

               rowSorter = new TableRowSorter < > (booksTable.getModel());
               booksTable.setRowSorter(rowSorter);

            }

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

      searchBorrowedField.getDocument().addDocumentListener(new DocumentListener() {

         @Override
         public void insertUpdate(DocumentEvent e) {
            String text = searchBorrowedField.getText();

            if (text.trim().length() == 0) {
               rowSorter.setRowFilter(null);
            } else {
               rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
            }
         }

         @Override
         public void removeUpdate(DocumentEvent e) {
            String text = searchBorrowedField.getText();

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

      booksTable.addMouseListener(new MouseAdapter() {
         public void mousePressed(MouseEvent mouseEvent) {
            JTable table = (JTable) mouseEvent.getSource();
            Point point = mouseEvent.getPoint();
            int row = table.rowAtPoint(point);
            if (mouseEvent.getClickCount() == 2 && row != -1) {
               UserBookPage bookPage = new UserBookPage(table.getValueAt(row, 4).toString(), studentID);

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
      borrowedTable.addMouseListener(new MouseAdapter() {
         public void mousePressed(MouseEvent mouseEvent) {
            JTable table = (JTable) mouseEvent.getSource();
            Point point = mouseEvent.getPoint();
            int row = table.rowAtPoint(point);
            if (mouseEvent.getClickCount() == 2 && row != -1) {
               UserBookPage bookPage = new UserBookPage(table.getValueAt(row, 3).toString(), studentID);

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

      borrowedButton.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {
            cl_cardPanel.show(cardPanel, "loading");
            dtmBorrowed.setRowCount(0);

            counter = 0;

            refBooks.child("users/" + studentID + "/borrowed").addListenerForSingleValueEvent(new ValueEventListener() {
               @Override
               public void onDataChange(DataSnapshot dataSnapshot1) {
                  if (dataSnapshot1.exists()) {

                     for (DataSnapshot issue: dataSnapshot1.getChildren()) {
                        Object[] rowData = new Object[6];
                        String title = issue.child("title").getValue().toString();
                        String author = issue.child("author").getValue().toString();
                        String isbn = issue.child("isbn").getValue().toString();
                        String genre = issue.child("genre").getValue().toString();
                        String borrowDate = issue.child("borrowDate").getValue().toString();
                        String returnDate = issue.child("returnDate").getValue().toString();

                        rowData[0] = title;
                        rowData[1] = author;
                        rowData[2] = genre;
                        rowData[3] = isbn;
                        rowData[4] = borrowDate;
                        rowData[5] = returnDate;
                        counter++;
                        dtmBorrowed.addRow(rowData);

                     }

                  }

                  borrowedTable.setModel(dtmBorrowed);

                  rowSorter = new TableRowSorter < > (borrowedTable.getModel());
                  borrowedTable.setRowSorter(rowSorter);

                  cl_cardPanel.show(cardPanel, "borrowed");

                  if (counter == 0) {
                     JOptionPane.showMessageDialog(null, "There are no borrowed books", "Books", JOptionPane.INFORMATION_MESSAGE);
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
            cl_cardPanel.show(cardPanel, "loading");
            dtmBooks.setRowCount(0);
            booksList.clear();

            counter = 0;

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
                        String genre = issue.child("genre").getValue().toString();
                        String borrowersNumString = issue.child("borrowersNum").getValue().toString();
                        int borrowersNum = Integer.parseInt(borrowersNumString);
                        String numberOfCopiesString = issue.child("numberOfCopies").getValue().toString();
                        int numberOfCopies = Integer.parseInt(numberOfCopiesString);

                        booksList.add(new Book(title, author, isbn, publisher, publishDate, genre, numberOfCopies, "no", 0, 0, borrowersNum));

                        rowData[0] = title;
                        rowData[1] = author;
                        rowData[2] = genre;
                        rowData[3] = publisher + " (" + publishDate + ")";
                        rowData[4] = isbn;

                        if (borrowersNum < numberOfCopies) {
                           counter++;
                           rowData[5] = "Available";
                           dtmBooks.addRow(rowData);
                        }

                     }

                  }

                  booksTable.setModel(dtmBooks);

                  rowSorter = new TableRowSorter < > (booksTable.getModel());
                  booksTable.setRowSorter(rowSorter);

                  cl_cardPanel.show(cardPanel, "books");

                  if (counter == 0) {
                     JOptionPane.showMessageDialog(null, "There are no available books", "Books", JOptionPane.INFORMATION_MESSAGE);
                  }
               }

               @Override
               public void onCancelled(DatabaseError databaseError1) {

               }
            });
         }

      });

      profileButton.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {

            StudentProfile studentProfile = new StudentProfile(studentID);

            if (getExtendedState() != MAXIMIZED_BOTH) {
               studentProfile.setSize(getSize());
               studentProfile.setLocation(getX(), getY());
            } else {
               studentProfile.setLocationRelativeTo(null);
            }
            studentProfile.setExtendedState(getExtendedState());

            studentProfile.setVisible(true);
            setVisible(false);

         }
      });

   }
}