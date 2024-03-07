package com.nariman.library;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLaf;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;

public class UserBookPage extends JFrame {

   private JPanel contentPane;
   private CardLayout cardLayout;
   private JPanel cardPanel;
   private JPanel topPanel;
   private JPanel loadingPanel;
   private JLabel loadingLabel;
   private JPanel bookPanel;
   private JPanel imageAndBookPanel;
   private JLabel imageLabel;
   private JPanel imagePanel;
   private JLabel titleLabel;
   private JLabel authorLabel;
   private JLabel ratingLabel;
   private JLabel isbnLabel;
   private JLabel genreLabel;
   private JLabel publisherLabel;
   private JLabel numberLabel;
   private JButton backButton;
   private JLabel emptyTopLabel;
   private JLabel emptyBookLabel;
   private JScrollPane scroll;
   private JButton star1;
   private JButton star2;
   private JButton star3;
   private JButton star4;
   private JButton star5;
   private JButton borrowButton;
   private JButton extendButton;
   private final FirebaseDatabase database = FirebaseDatabase.getInstance("https://library-c6bfc-default-rtdb.asia-southeast1.firebasedatabase.app/");
   DatabaseReference bookRef = database.getReference("books");
   private String title;
   private String author;
   private long overallRating;
   private int reviews;
   private String genre;
   private String publisher;
   private int publishDate;
   private int numberOfCopies;
   private int borrowersNum;
   private String urlString;
   private double averageRating;
   private double averageRatingTrunk;
   private Icon icon;
   private boolean isBorrowed;
   private boolean isHistory;
   private boolean isReviewed;
   private boolean isExtended;
   private int extendedFlag;
   private int ownRating = 0;
   private String name;
   private String email;
   private String borrowDate;
   private SimpleDateFormat sdf;
   private String returnDate;
   private String id;
   private String stdId;

   /**
    * Create the frame.
    */

   public UserBookPage(String isbnFromTable, String studentID) {
      DatabaseReference borrowingReference = database.getReference();
      id = borrowingReference.push().getKey();
      UIManager.put("Component.focusWidth", 1);
      UIManager.put("Button.borderColor", new Color(255, 255, 255));
      FlatLaf.setGlobalExtraDefaults(Collections.singletonMap("@accentColor", "#3cb371"));
      FlatIntelliJLaf.setup();
      DecimalFormat df = new DecimalFormat();
      df.setMaximumFractionDigits(2);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setBounds(100, 100, 952, 714);
      contentPane = new JPanel();

      setContentPane(contentPane);

      contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

      topPanel = new JPanel(new GridBagLayout());

      topPanel.setMaximumSize(new Dimension(32767, 50));
      //topPanel.setMinimumSize(new Dimension(30, 40));
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

      imageAndBookPanel = new JPanel();
      imageAndBookPanel.setLayout(new GridBagLayout());
      imageAndBookPanel.setBackground(new Color(255, 255, 255));
      scroll = new JScrollPane(imageAndBookPanel);

      imagePanel = new JPanel(new GridBagLayout());
      imagePanel.setBackground(new Color(255, 255, 255));

      bookPanel = new JPanel(new GridBagLayout());
      bookPanel.setBackground(new Color(255, 255, 255));
      cardPanel.add(scroll, "book");

      GridBagConstraints grid_imgPanel = new GridBagConstraints();
      grid_imgPanel.gridx = 0;
      grid_imgPanel.gridy = 0;
      imageAndBookPanel.add(imagePanel, grid_imgPanel);

      GridBagConstraints grid_bookPanel = new GridBagConstraints();
      grid_bookPanel.gridx = 1;
      grid_bookPanel.gridy = 0;
      grid_bookPanel.insets = new Insets(0, 50, 0, 0);
      imageAndBookPanel.add(bookPanel, grid_bookPanel);

      imageLabel = new JLabel();

      imagePanel.add(imageLabel);

      titleLabel = new JLabel("War and Peace");
      titleLabel.setForeground(new Color(60, 179, 113));
      titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
      GridBagConstraints gbc_title = new GridBagConstraints();
      gbc_title.gridx = 0;
      gbc_title.gridy = 0;
      gbc_title.anchor = GridBagConstraints.FIRST_LINE_START;
      bookPanel.add(titleLabel, gbc_title);

      authorLabel = new JLabel("by Leo Tolstoy");
      authorLabel.setForeground(new Color(60, 179, 113));
      authorLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
      GridBagConstraints gbc_author = new GridBagConstraints();
      gbc_author.gridx = 0;
      gbc_author.gridy = 1;
      gbc_author.anchor = GridBagConstraints.FIRST_LINE_START;
      bookPanel.add(authorLabel, gbc_author);

      JPanel stars = new JPanel();
      stars.setLayout(new BoxLayout(stars, BoxLayout.X_AXIS));

      Icon starGrayIcon = new ImageIcon(this.getClass().getResource("/icones/icons8-star-filled-36-gray.png"));
      Icon starGoldIcon = new ImageIcon(this.getClass().getResource("/icones/icons8-star-filled-36-gold.png"));
      Icon starHalfIcon = new ImageIcon(this.getClass().getResource("/icones/icons8-star-half-empty-36.png"));
      star1 = new JButton();
      star1.setBorder(null);
      star1.setIcon(starGrayIcon);
      stars.add(star1);

      star2 = new JButton();
      star2.setBorder(null);
      star2.setIcon(starGrayIcon);
      stars.add(star2);

      star3 = new JButton();
      star3.setBorder(null);
      star3.setIcon(starGrayIcon);
      stars.add(star3);

      star4 = new JButton();
      star4.setBorder(null);
      star4.setIcon(starGrayIcon);
      stars.add(star4);

      star5 = new JButton();
      star5.setBorder(null);
      star5.setIcon(starGrayIcon);
      stars.add(star5);

      GridBagConstraints gbc_stars = new GridBagConstraints();
      gbc_stars.gridx = 0;
      gbc_stars.gridy = 2;
      gbc_stars.anchor = GridBagConstraints.FIRST_LINE_START;
      gbc_stars.insets = new Insets(20, 0, 0, 0);

      bookPanel.add(stars, gbc_stars);

      ratingLabel = new JLabel("Average Rating: 4.57");
      ratingLabel.setForeground(new Color(60, 179, 113));
      ratingLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
      GridBagConstraints gbc_rating = new GridBagConstraints();
      gbc_rating.gridx = 0;
      gbc_rating.gridy = 3;
      gbc_rating.anchor = GridBagConstraints.FIRST_LINE_START;
      gbc_rating.insets = new Insets(20, 0, 0, 0);
      bookPanel.add(ratingLabel, gbc_rating);

      publisherLabel = new JLabel("Publisher: Vintage (2021)");
      publisherLabel.setForeground(new Color(60, 179, 113));
      publisherLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
      GridBagConstraints gbc_publisher = new GridBagConstraints();
      gbc_publisher.gridx = 0;
      gbc_publisher.gridy = 4;
      gbc_publisher.anchor = GridBagConstraints.FIRST_LINE_START;

      bookPanel.add(publisherLabel, gbc_publisher);

      isbnLabel = new JLabel("ISBN: 1241232352242");
      isbnLabel.setForeground(new Color(60, 179, 113));
      isbnLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
      GridBagConstraints gbc_isbn = new GridBagConstraints();
      gbc_isbn.gridx = 0;
      gbc_isbn.gridy = 5;
      gbc_isbn.anchor = GridBagConstraints.FIRST_LINE_START;
      gbc_isbn.insets = new Insets(0, 0, 0, 0);
      bookPanel.add(isbnLabel, gbc_isbn);

      numberLabel = new JLabel("Copies left: 3");
      numberLabel.setForeground(new Color(60, 179, 113));
      numberLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
      GridBagConstraints gbc_number = new GridBagConstraints();
      gbc_number.gridx = 0;
      gbc_number.gridy = 6;
      gbc_number.anchor = GridBagConstraints.FIRST_LINE_START;
      gbc_number.insets = new Insets(0, 0, 0, 0);
      bookPanel.add(numberLabel, gbc_number);

      borrowButton = new JButton("BORROW");
      borrowButton.setBackground(new Color(60, 179, 113));
      borrowButton.setForeground(Color.WHITE);
      borrowButton.setFont(new Font("Segoe UI", Font.BOLD, 12));

      borrowButton.setPreferredSize(new Dimension(240, 40));

      extendButton = new JButton("EXTEND");
      extendButton.setBackground(new Color(60, 179, 113));
      extendButton.setForeground(Color.WHITE);
      extendButton.setFont(new Font("Segoe UI", Font.BOLD, 12));

      extendButton.setPreferredSize(new Dimension(240, 40));

      borrowButton.putClientProperty("JButton.buttonType", "roundRect");
      GridBagConstraints gbc_borrow = new GridBagConstraints();
      gbc_borrow.gridx = 0;
      gbc_borrow.gridy = 7;
      //gbc_borrow.anchor = GridBagConstraints.FIRST_LINE_START;
      gbc_borrow.insets = new Insets(40, 0, 0, 0);
      bookPanel.add(borrowButton, gbc_borrow);

      emptyBookLabel = new JLabel();

      GridBagConstraints grid_emp = new GridBagConstraints();
      grid_emp.gridx = 1;

      grid_emp.weightx = 1.0;

      grid_emp.fill = GridBagConstraints.HORIZONTAL;
      bookPanel.add(emptyBookLabel, grid_emp);
      cardLayout.show(cardPanel, "loading");

      Query query = bookRef.orderByChild("isbn").equalTo(isbnFromTable);
      query.addListenerForSingleValueEvent(new ValueEventListener() {
         @Override
         public void onDataChange(DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()) {

               for (DataSnapshot issue: dataSnapshot.getChildren()) {

                  title = issue.child("title").getValue().toString();
                  author = issue.child("author").getValue().toString();

                  publisher = issue.child("publisher").getValue().toString();
                  String publishDateString = issue.child("publishDate").getValue().toString();
                  publishDate = Integer.parseInt(publishDateString);
                  genre = issue.child("genre").getValue().toString();
                  String borrowersNumString = issue.child("borrowersNum").getValue().toString();
                  borrowersNum = Integer.parseInt(borrowersNumString);
                  String numberOfCopiesString = issue.child("numberOfCopies").getValue().toString();
                  numberOfCopies = Integer.parseInt(numberOfCopiesString);
                  urlString = issue.child("url").getValue().toString();
                  String overallRatingString = issue.child("rating").getValue().toString();
                  overallRating = Long.parseLong(overallRatingString);
                  String reviewsString = issue.child("reviews").getValue().toString();
                  reviews = Integer.parseInt(reviewsString);

               }

            }
            DatabaseReference refBorrowed = database.getReference();

            Query query1 = refBorrowed.child("users/" + studentID + "/borrowed").orderByChild("isbn").equalTo(isbnFromTable);
            query1.addListenerForSingleValueEvent(new ValueEventListener() {
               @Override
               public void onDataChange(DataSnapshot dataSnapshot1) {
                  if (dataSnapshot1.exists()) {
                     isBorrowed = true;
                     for (DataSnapshot issue: dataSnapshot1.getChildren()) {

                        returnDate = issue.child("returnDate").getValue().toString();
                     }
                  } else
                     isBorrowed = false;

               }

               @Override
               public void onCancelled(DatabaseError databaseError1) {
                  JOptionPane.showMessageDialog(null, "Canceled", "Warning", JOptionPane.ERROR_MESSAGE);

               }
            });

            Query query2 = refBorrowed.child("history").orderByChild("isbn").equalTo(isbnFromTable);
            query2.addListenerForSingleValueEvent(new ValueEventListener() {
               @Override
               public void onDataChange(DataSnapshot dataSnapshot2) {
                  if (dataSnapshot2.exists()) {

                     for (DataSnapshot issue: dataSnapshot2.getChildren()) {
                        stdId = issue.child("studentID").getValue().toString();

                     }
                     if (stdId.equals(studentID))
                        isHistory = true;
                     else
                        isHistory = false;

                  } else {
                     isHistory = false;
                  }

               }

               @Override
               public void onCancelled(DatabaseError databaseError1) {
                  JOptionPane.showMessageDialog(null, "Canceled", "Warning", JOptionPane.ERROR_MESSAGE);

               }
            });

            Query query3 = refBorrowed.child("books/" + isbnFromTable + "/reviewed").orderByChild("studentID").equalTo(studentID);
            query3.addListenerForSingleValueEvent(new ValueEventListener() {
               @Override
               public void onDataChange(DataSnapshot dataSnapshot3) {
                  if (dataSnapshot3.exists()) {
                     isReviewed = true;
                  } else {
                     isReviewed = false;
                  }

               }

               @Override
               public void onCancelled(DatabaseError databaseError1) {
                  JOptionPane.showMessageDialog(null, "Canceled", "Warning", JOptionPane.ERROR_MESSAGE);

               }
            });

            Query query4 = refBorrowed.child("users/" + studentID + "/borrowed").orderByChild("isbn").equalTo(isbnFromTable);
            query4.addListenerForSingleValueEvent(new ValueEventListener() {
               @Override
               public void onDataChange(DataSnapshot dataSnapshot4) {
                  if (dataSnapshot4.exists()) {

                     for (DataSnapshot issue: dataSnapshot4.getChildren()) {
                        String extendedSring = issue.child("extended").getValue().toString();
                        extendedFlag = Integer.parseInt(extendedSring);

                     }
                  }

                  if (extendedFlag == 1)
                     isExtended = true;
                  else
                     isExtended = false;

               }

               @Override
               public void onCancelled(DatabaseError databaseError1) {
                  JOptionPane.showMessageDialog(null, "Canceled", "Warning", JOptionPane.ERROR_MESSAGE);

               }
            });

            SwingWorker < Boolean, Void > imageWorker = new SwingWorker < Boolean, Void > () {
               protected Boolean doInBackground() {
                  averageRating = overallRating / (double) reviews;

                  URL url = null;
                  try {
                     url = new URL(urlString);
                  } catch (MalformedURLException ex) {
                     JOptionPane.showMessageDialog(null, ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
                     return false;
                  }
                  BufferedImage c = null;
                  try {
                     c = ImageIO.read(url);
                  } catch (IOException ex) {
                     JOptionPane.showMessageDialog(null, ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
                     return false;

                  }
                  Dimension imgSize = new Dimension(c.getWidth(), c.getHeight());
                  Dimension boundary = new Dimension(500, 500);
                  Dimension dimension = getScaledDimension(imgSize, boundary);

                  Image dimg = c.getScaledInstance((int) dimension.getWidth(), (int) dimension.getHeight(), Image.SCALE_SMOOTH);
                  icon = new ImageIcon(dimg);

                  return true;
               }

               protected void done() {
                  try {
                     imageLabel.setIcon(icon);
                     boolean imageSuccessful = get();
                     if (imageSuccessful) {
                        titleLabel.setText(title);
                        authorLabel.setText("by " + author);

                        if (averageRating > 0 && averageRating <= 0.5) {
                           star1.setIcon(starHalfIcon);
                        } else if (averageRating > 0.5 && averageRating <= 1) {
                           star1.setIcon(starGoldIcon);
                        } else if (averageRating > 1 && averageRating <= 1.5) {
                           star1.setIcon(starGoldIcon);
                           star2.setIcon(starHalfIcon);
                        } else if (averageRating > 1.5 && averageRating <= 2) {
                           star1.setIcon(starGoldIcon);
                           star2.setIcon(starGoldIcon);
                        } else if (averageRating > 2 && averageRating <= 2.5) {
                           star1.setIcon(starGoldIcon);
                           star2.setIcon(starGoldIcon);
                           star3.setIcon(starHalfIcon);
                        } else if (averageRating > 2.5 && averageRating <= 3) {
                           star1.setIcon(starGoldIcon);
                           star2.setIcon(starGoldIcon);
                           star3.setIcon(starGoldIcon);
                        } else if (averageRating > 3 && averageRating <= 3.5) {
                           star1.setIcon(starGoldIcon);
                           star2.setIcon(starGoldIcon);
                           star3.setIcon(starGoldIcon);
                           star4.setIcon(starHalfIcon);
                        } else if (averageRating > 3.5 && averageRating <= 4) {
                           star1.setIcon(starGoldIcon);
                           star2.setIcon(starGoldIcon);
                           star3.setIcon(starGoldIcon);
                           star4.setIcon(starGoldIcon);
                        } else if (averageRating > 4 && averageRating <= 4.5) {
                           star1.setIcon(starGoldIcon);
                           star2.setIcon(starGoldIcon);
                           star3.setIcon(starGoldIcon);
                           star4.setIcon(starGoldIcon);
                           star5.setIcon(starHalfIcon);
                        } else if (averageRating > 4.5 && averageRating <= 5) {
                           star1.setIcon(starGoldIcon);
                           star2.setIcon(starGoldIcon);
                           star3.setIcon(starGoldIcon);
                           star4.setIcon(starGoldIcon);
                           star5.setIcon(starGoldIcon);
                        }

                        if (reviews > 0)
                           ratingLabel.setText("Average Rating: " + df.format(averageRating));
                        else
                           ratingLabel.setText("Average Rating: No Reviews");
                        publisherLabel.setText("Publisher: " + publisher + " (" + publishDate + ")");
                        isbnLabel.setText("ISBN-13: " + isbnFromTable);
                        numberLabel.setText("Copies Left: " + (numberOfCopies - borrowersNum));
                        if (isBorrowed) {

                           numberLabel.setText("Return Date: " + (returnDate));
                           borrowButton.setText("RETURN");

                           extendButton.putClientProperty("JButton.buttonType", "roundRect");
                           GridBagConstraints gbc_extend = new GridBagConstraints();
                           gbc_extend.gridx = 0;
                           gbc_extend.gridy = 8;
                           //gbc_extend.anchor = GridBagConstraints.FIRST_LINE_START;
                           gbc_extend.insets = new Insets(10, 0, 0, 0);
                           bookPanel.add(extendButton, gbc_extend);
                        } else {
                           borrowButton.setText("BORROW");

                        }
                        if (!isBorrowed && (numberOfCopies - borrowersNum) == 0) {
                           borrowButton.setEnabled(false);
                        }

                        cardLayout.show(cardPanel, "book");
                     }
                  } catch (Exception ex) {
                     System.out.println(ex.getMessage());
                     JOptionPane.showMessageDialog(null, ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
                  }
               }
            };

            imageWorker.execute();

         }

         @Override
         public void onCancelled(DatabaseError databaseError1) {
            JOptionPane.showMessageDialog(null, "Canceled", "Warning", JOptionPane.ERROR_MESSAGE);

         }
      });

      star1.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {
            if (isHistory) {
               if (!isReviewed) {
                  ownRating = 1;
                  star1.setIcon(starGoldIcon);
                  star2.setIcon(starGrayIcon);
                  star3.setIcon(starGrayIcon);
                  star4.setIcon(starGrayIcon);
                  star5.setIcon(starGrayIcon);
               } else {
                  JOptionPane.showMessageDialog(null, "You cannot rate books that you have already rated", "Rating", JOptionPane.INFORMATION_MESSAGE);
               }

            } else {
               JOptionPane.showMessageDialog(null, "You cannot rate books that you have never borrowed", "Rating", JOptionPane.INFORMATION_MESSAGE);
            }

         }

      });
      star2.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {
            if (isHistory) {
               if (!isReviewed) {
                  ownRating = 2;
                  star1.setIcon(starGoldIcon);
                  star2.setIcon(starGoldIcon);
                  star3.setIcon(starGrayIcon);
                  star4.setIcon(starGrayIcon);
                  star5.setIcon(starGrayIcon);
               } else {
                  JOptionPane.showMessageDialog(null, "You cannot rate books that you have already rated", "Rating", JOptionPane.INFORMATION_MESSAGE);
               }
            } else {
               JOptionPane.showMessageDialog(null, "You cannot rate books that you never borrowed", "Rating", JOptionPane.INFORMATION_MESSAGE);
            }

         }

      });
      star3.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {
            if (isHistory) {
               if (!isReviewed) {
                  ownRating = 3;
                  star1.setIcon(starGoldIcon);
                  star2.setIcon(starGoldIcon);
                  star3.setIcon(starGoldIcon);
                  star4.setIcon(starGrayIcon);
                  star5.setIcon(starGrayIcon);
               } else {
                  JOptionPane.showMessageDialog(null, "You cannot rate books that you have already rated", "Rating", JOptionPane.INFORMATION_MESSAGE);
               }
            } else {
               JOptionPane.showMessageDialog(null, "You cannot rate books that you never borrowed", "Rating", JOptionPane.INFORMATION_MESSAGE);
            }

         }

      });
      star4.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {
            if (isHistory) {
               if (!isReviewed) {
                  ownRating = 4;
                  star1.setIcon(starGoldIcon);
                  star2.setIcon(starGoldIcon);
                  star3.setIcon(starGoldIcon);
                  star4.setIcon(starGoldIcon);
                  star5.setIcon(starGrayIcon);
               } else {
                  JOptionPane.showMessageDialog(null, "You cannot rate books that you have already rated", "Rating", JOptionPane.INFORMATION_MESSAGE);
               }
            } else {
               JOptionPane.showMessageDialog(null, "You cannot rate books that you never borrowed", "Rating", JOptionPane.INFORMATION_MESSAGE);
            }

         }

      });
      star5.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {
            if (isHistory) {
               if (!isReviewed) {
                  ownRating = 5;
                  star1.setIcon(starGoldIcon);
                  star2.setIcon(starGoldIcon);
                  star3.setIcon(starGoldIcon);
                  star4.setIcon(starGoldIcon);
                  star5.setIcon(starGoldIcon);
               } else {
                  JOptionPane.showMessageDialog(null, "You cannot rate books that you have already rated", "Rating", JOptionPane.INFORMATION_MESSAGE);
               }
            } else {
               JOptionPane.showMessageDialog(null, "You cannot rate books that you never borrowed", "Rating", JOptionPane.INFORMATION_MESSAGE);
            }

         }

      });

      borrowButton.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {

            if (!isBorrowed) {

               Student student = new Student(name, email, studentID, null);
               student.addBook(title, author, isbnFromTable, publisher, Integer.toString(publishDate), genre, Integer.toString(borrowersNum), id, null, UserBookPage.this);

            } else {
               StudentPage studentPage = new StudentPage(studentID);
               DatabaseReference reference = database.getReference();
               if (ownRating > 0) {

                  reference.child("books/" + isbnFromTable + "/rating").setValueAsync(overallRating + ownRating);
                  reference.child("books/" + isbnFromTable + "/reviews").setValueAsync(reviews + 1);
                  reference.child("books/" + isbnFromTable + "/reviewed/" + studentID + "/studentID").setValueAsync(studentID);
                  JOptionPane.showMessageDialog(null, "Book was successfully rated", "Rating", JOptionPane.INFORMATION_MESSAGE);
               }
               reference.child("users/" + studentID + "/borrowed/" + isbnFromTable).removeValueAsync();
               reference.child("books/" + isbnFromTable + "/borrowed/" + studentID).removeValueAsync();
               reference.child("books/" + isbnFromTable + "/borrowersNum").setValueAsync(borrowersNum - 1);

               LocalDate currentDate = LocalDate.now();
               borrowDate = currentDate.toString(); // Start date

               Query query2 = reference.child("history").orderByChild("isbn").equalTo(isbnFromTable);
               query2.addListenerForSingleValueEvent(new ValueEventListener() {
                  @Override
                  public void onDataChange(DataSnapshot dataSnapshot2) {
                     if (dataSnapshot2.exists()) {

                        for (DataSnapshot issue: dataSnapshot2.getChildren()) {
                           stdId = issue.child("studentID").getValue().toString();
                           if (stdId.equals(studentID)) {
                              id = issue.child("id").getValue().toString();
                           }
                        }

                     }
                     reference.child("history/" + id + "/returnDate").setValueAsync(borrowDate);

                     JOptionPane.showMessageDialog(null, "You successfully returned this book", "Extention", JOptionPane.INFORMATION_MESSAGE);
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

                  @Override
                  public void onCancelled(DatabaseError databaseError1) {
                     JOptionPane.showMessageDialog(null, "Canceled", "Warning", JOptionPane.ERROR_MESSAGE);

                  }
               });

            }
         }

      });

      backButton.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {
            StudentPage studentPage = new StudentPage(studentID);

            if (ownRating > 0) {
               DatabaseReference ratingReference = database.getReference();
               ratingReference.child("books/" + isbnFromTable + "/rating").setValueAsync(overallRating + ownRating);
               ratingReference.child("books/" + isbnFromTable + "/reviews").setValueAsync(reviews + 1);
               ratingReference.child("books/" + isbnFromTable + "/reviewed/" + studentID + "/studentID").setValueAsync(studentID);
               JOptionPane.showMessageDialog(null, "Book was successfully rated", "Rating", JOptionPane.INFORMATION_MESSAGE);
            }

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

      extendButton.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {
            if (!isExtended) {
               StudentPage studentPage = new StudentPage(studentID);
               DatabaseReference reference = database.getReference();
               if (ownRating > 0) {

                  reference.child("books/" + isbnFromTable + "/rating").setValueAsync(overallRating + ownRating);
                  reference.child("books/" + isbnFromTable + "/reviews").setValueAsync(reviews + 1);
                  reference.child("books/" + isbnFromTable + "/reviewed/" + studentID + "/studentID").setValueAsync(studentID);
                  JOptionPane.showMessageDialog(null, "Book was successfully rated", "Rating", JOptionPane.INFORMATION_MESSAGE);
               }

               sdf = new SimpleDateFormat("yyyy-MM-dd");
               Calendar calendar = Calendar.getInstance();
               try {
                  calendar.setTime(sdf.parse(returnDate));
               } catch (ParseException ex) {
                  Logger.getLogger(UserBookPage.class.getName()).log(Level.SEVERE, null, ex);
               }
               calendar.add(Calendar.DATE, 14); // number of days to add
               returnDate = sdf.format(calendar.getTime());

               Query query = reference.child("history").orderByChild("isbn").equalTo(isbnFromTable);
               query.addListenerForSingleValueEvent(new ValueEventListener() {
                  @Override
                  public void onDataChange(DataSnapshot dataSnapshot) {
                     if (dataSnapshot.exists()) {

                        for (DataSnapshot issue: dataSnapshot.getChildren()) {

                           id = issue.child("id").getValue().toString();

                        }
                     }

                     reference.child("users/" + studentID + "/borrowed/" + isbnFromTable + "/returnDate").setValueAsync(returnDate);
                     reference.child("books/" + isbnFromTable + "/borrowed/" + studentID + "/returnDate").setValueAsync(returnDate);
                     reference.child("history/" + id + "/returnDate").setValueAsync(returnDate);
                     reference.child("users/" + studentID + "/borrowed/" + isbnFromTable + "/extended").setValueAsync(1);
                     JOptionPane.showMessageDialog(null, "You successfully extended this book", "Extention", JOptionPane.INFORMATION_MESSAGE);
                  }

                  @Override
                  public void onCancelled(DatabaseError databaseError1) {
                     JOptionPane.showMessageDialog(null, "Canceled", "Warning", JOptionPane.ERROR_MESSAGE);

                  }
               });

               if (getExtendedState() != MAXIMIZED_BOTH) {
                  studentPage.setSize(getSize());
                  studentPage.setLocation(getX(), getY());
               } else {
                  studentPage.setLocationRelativeTo(null);
               }
               studentPage.setExtendedState(getExtendedState());

               studentPage.setVisible(true);
               setVisible(false);
            } else
               JOptionPane.showMessageDialog(null, "You cannot extend this book again", "Extention", JOptionPane.INFORMATION_MESSAGE);

         }

      });

   }
   public static Dimension getScaledDimension(Dimension imgSize, Dimension boundary) {

      int original_width = imgSize.width;
      int original_height = imgSize.height;
      int bound_width = boundary.width;
      int bound_height = boundary.height;
      int new_width = original_width;
      int new_height = original_height;

      // first check if we need to scale width
      if (original_width > bound_width) {
         //scale width to fit
         new_width = bound_width;
         //scale height to maintain aspect ratio
         new_height = (new_width * original_height) / original_width;
      }

      // then check if we need to scale even with the new height
      if (new_height > bound_height) {
         //scale height to fit instead
         new_height = bound_height;
         //scale width to maintain aspect ratio
         new_width = (new_height * original_width) / original_height;
      }

      return new Dimension(new_width, new_height);
   }

}