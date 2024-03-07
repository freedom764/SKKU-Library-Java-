package com.nariman.library;

public class Book {

   private String title;
   private String author;
   private String isbn;
   private String publisher;
   private int publishDate;
   private int numberOfCopies;
   private String url;
   private String genre;
   private long rating;
   private int reviews;
   private int borrowersNum;

   public Book(String title, String author, String isbn, String publisher, int publishDate, String genre, int numberOfCopies, String url, long rating, int reviews, int borrowersNum) {

      setTitle(title);
      setAuthor(author);
      setIsbn(isbn);
      setPublisher(publisher);
      setPublishDate(publishDate);
      setGenre(genre);
      setNumberOfCopies(numberOfCopies);
      setUrl(url);
      setRating(rating);
      setReviews(reviews);
      setBorrowersNum(borrowersNum);
   }
   public String getTitle() {
      return title;
   }
   public void setTitle(String title) {
      this.title = title;
   }
   public String getAuthor() {
      return author;
   }
   public void setAuthor(String author) {
      this.author = author;
   }
   public String getIsbn() {
      return isbn;
   }
   public void setIsbn(String isbn) {
      this.isbn = isbn;
   }
   public String getPublisher() {
      return publisher;
   }
   public void setPublisher(String publisher) {
      this.publisher = publisher;
   }
   public int getPublishDate() {
      return publishDate;
   }
   public void setPublishDate(int publishDate) {
      this.publishDate = publishDate;
   }
   public void setGenre(String genre) {
      this.genre = genre;
   }
   public String getGenre() {
      return genre;
   }
   public int getNumberOfCopies() {
      return numberOfCopies;
   }
   public void setNumberOfCopies(int numberOfCopies) {
      this.numberOfCopies = numberOfCopies;
   }
   public String getUrl() {
      return url;
   }
   public void setUrl(String url) {
      this.url = url;
   }
   public long getRating() {
      return rating;
   }
   public void setRating(long rating) {
      this.rating = rating;
   }
   public int getReviews() {
      return reviews;
   }
   public void setReviews(int reviews) {
      this.reviews = reviews;
   }
   public int getBorrowersNum() {
      return borrowersNum;
   }
   public void setBorrowersNum(int borrowersNum) {
      this.borrowersNum = borrowersNum;
   }

}