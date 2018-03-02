
# KitapHana
---
Java-CSS-JavaScript implemetion of Library Management System webapp: 
Introduction to Programming project by students of BS1-6 group, team: Aygul Malikova, Almir Mullanurov,
Victoria Zubrinkina, Georgy Khorushevsky 
# Contents
<a href="#InstAndL">Usage</a>

# Main scheme
Patrons are able survey diffrent documents on the main page and check them out for a certain time.
Librarins are used to manage their moves and them as objects. 
<a name="InstAndL">
   
# Usage
</a>
<a name="inst"

## Installation accompanying soft:
</a>
<ul>
    <li>Install Java jdk according to your operation system
        <ul>
           <li> use <a href="ProvidedDoc/java.pdf"> This guide </a>
        </ul>
    <li>Install Intellij IDEA 
        <ul>
           <li> use <a href="https://www.jetbrains.com/help/idea/install-and-set-up-intellij-idea.html"> This guide</a>
        </ul>
    <li>Install mySql
        <ul>
           <li> use <a href="ProvidedDoc/mysql.pdf">This guide</a>
        </ul>
 </ul>

## Launching the project
<li>Download and launch the project
        <ul>
           <li> use <a href="ProvidedDoc/project.pdf">This guide</a>
        </ul>
   
## Issues?
   <ul><big> If something goes wrong:</big>
       <li> Deinstall everything and try again
       <li> Still have some problems? Please contact one of us in Telegram:
          <ul>
             <li> Almir Mullanurov - @mir_lan
             <li> Georgy Khorushevsky - @georrgy

## Entrying
To except the library one can use one of pre-signed up users:
<ul>
   <li> Patron:
      <ul>
         <li> login - 123
         <li> password - 123
      </ul>
   <li> Librarian:
      <ul>
         <li> login - 89547456654
         <li> password  - 123

<%--## Testing--%>

# Architecture of the website
 <img src="ProvidedDoc/scheme.jpg" alt="альтернативный текст"> 
                    
# Implementation
## Documents
We store all documents in documents db. 


         public class Document {
             protected String title;
             protected String keywords;
             protected String authors;
             protected String cover;
             protected String type;
             protected int price, amount, id;

which is typicly the abstract class for all documents.
Below we have particular types of document extending from it: 

         public class Book extends Document {
             protected String publisher;
             protected int year, edition_number;
             protected int best_seller;

 

         public class AVMaterial extends Document {


 

         public class JournalArticle extends Document{
             protected String journal_name, date;
             protected ArrayList<String> editors;




# Users 
<ul>
<li> Patron<li>
   Could give requests to  <big>search for, check out and return documents.</big>
<ul>   
   <li> Student</li>
   Have permission to сheck out documents for <big>3</big> weeks 
   <li> Faculty member </li>
   Have permission to сheck out documents for <big>4</big> weeks 
</ul>
<li> Librarian </li>
   Is allowed to <big>modify/delete/add</big> any document or patron.
</ul>

We assign the loged in user with new exemplar of appropriate class:

         public class User {
             protected String phone_number, name, surname, password, possible_type, type, email;
             protected Address address;
             protected long card_number, maxdays, day = 24*60*60*1000;
             protected int id;
             protected ArrayList<Document> documents;
             protected ArrayList<Long> deadlines;


## Librarian features
Librarian is a user with manage abilities. One's 3special features
defined in class Librarian. 

         public void modifyDocument(Document document) throws SQLException {...
         public void  removeDocument(Document document) throws SQLException {...
         public void addDocument() throws SQLException {...
         public void modifyUser(User user) throws SQLException {...
         public void removeUser(User user) throws SQLException {...



## Booking System (Document Copy)
         public void checkOutDocument(Document document) throws SQLException {
                 if(document.getAmount() > 0 && !this.hasDocument(document)){
                     addDocument(document);
                     document.decreaseAmount();
                     document.addUser(this);
                     document.save();
                     save();
                 }
                 else{
                     System.out.println("You can't book this document");
                 }
             }

Every time user check out document - mount of copies in the library decrease.

