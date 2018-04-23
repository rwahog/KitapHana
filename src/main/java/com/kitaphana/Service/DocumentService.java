package com.kitaphana.Service;

import com.kitaphana.Database.Database;
import com.kitaphana.Entities.*;
import com.kitaphana.dao.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.PriorityQueue;

public class DocumentService {
  public Database db = Database.getInstance();
  documentDAOImpl documentDAO = new documentDAOImpl();
  bookDAOImpl bookDAO = new bookDAOImpl();
  patronDAOImpl patronDAO = new patronDAOImpl();
  authorDAOImpl authorDAO = new authorDAOImpl();
  journalArticleDAOImpl journalArticleDAO = new journalArticleDAOImpl();
  keywordDAOImpl keywordDAO = new keywordDAOImpl();
  avMaterialDAOImpl avMaterialDAO = new avMaterialDAOImpl();
  DBService DBService = new DBService();
  long day = 24 * 1000 * 60 * 60;

  public ArrayList<Document> findAll() {
    ArrayList<Document> docs = documentDAO.findAll();
    for (Document doc : docs) {
      DBService.setAuthors(doc);
      DBService.setKeywords(doc);
    }
    return docs;
  }

  public Document findById(long id) {
    Document document = documentDAO.findById(id);
    DBService.setKeywords(document);
    DBService.setAuthors(document);

    return document;
  }

  public ArrayList<Keyword> findAllKeywords() {
    ArrayList<Keyword> keys = keywordDAO.findAll();
    for (Keyword key : keys) {
      ArrayList<String> docsId = fromDBStringToArray(key.getDocumentsId());
      ArrayList<Document> documents = new ArrayList<>();
      for (String docId : docsId) {
          documents.add(documentDAO.findById(Long.parseLong(docId)));
      }
      key.setDocuments(documents);
    }
    return keys;
  }

  public Document setDocInfo(String id) {
    Document document = new Document();
    try {
      String type = DBService.findColumn(id, "documents", "type");
      switch (type) {
        case "Book":
          type = "books";
          break;
        case "Journal Article":
          type = "ja";
          break;
        case "Audio/Video material":
          type = "av";
          break;
      }
      document = DBService.findDocumentAndTypeInfo(id, type);
      DBService.setAuthors(document);
      DBService.setKeywords(document);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return document;
  }

  public boolean queue(long id_user, int id_doc) {
    Patron patron = patronDAO.findById(id_user);
    String docsId = patron.getDocumentsId();
    if (docsId != null && docsId.length() != 0) {
      ArrayList<String> docs = fromDBStringToArray(docsId);
      boolean exist = docs.contains(String.valueOf(id_doc));
      if (exist) {
        return false;
      }
    }
    if (!patron.getPossibleType().equals(patron.getType())) {
      return false;
    }
    String available = DBService.findColumn(String.valueOf(id_doc), "documents", "available");
    if (available.equals("0")) {
      return false;
    }
    Document document = documentDAO.findById(id_doc);
    String awaiters = document.getAwaitersId();
    if (awaiters != null && awaiters.length() != 0) {
      PriorityComparator comparator = new PriorityComparator();
      PriorityQueue<Patron> priorityQueue = new PriorityQueue<Patron>(10, comparator);
      ArrayList<String> awaiters_arr = DBService.fromDBStringToArray(awaiters);
      for (String user_id : awaiters_arr) {
        Patron awaiter = patronDAO.findById(Long.parseLong(user_id));
        priorityQueue.add(awaiter);
      }
      patron.setPriority();
      priorityQueue.add(patron);
      awaiters = "";
      awaiters = awaiters.concat(String.valueOf(priorityQueue.poll().getId()));
      for (User usr : priorityQueue) {
        awaiters = awaiters.concat("," + usr.getId());
      }
    } else {
      awaiters = String.valueOf(id_user);
    }
    document.setAwaitersId(awaiters);
    documentDAO.update(document);
    String user_waitlist = patron.getWaitingListId();
    if (user_waitlist != null && user_waitlist.length() != 0) {
      user_waitlist = user_waitlist.concat("," + id_doc);
    } else {
      user_waitlist = String.valueOf(id_doc);
    }
    patron.setWaitingListId(user_waitlist);
    patronDAO.update(patron);
    return true;
  }

  public boolean checkOut(long id_user, int id) {
    try {
      Patron patron = patronDAO.findById(id_user);
      Document document = documentDAO.findById(id);
      int amount = document.getAmount();
      amount--;
      if (patron != null) {
        ArrayList docs = fromDBStringToArray(patron.getDocumentsId());
        ArrayList checks = fromDBStringToArray(patron.getCheckoutsId());
        if (document.getAmount() <= 0) {
          return false;
        }
        if (docs != null && docs.contains(id)) {
          return false;
        }
        if (!patron.getPossibleType().equals(patron.getType())) {
          return false;
        }
        if (checks != null && checks.contains(String.valueOf(id))) {
          return false;
        }
        String available = DBService.findColumn(String.valueOf(id), "documents", "available");
        if (available.equals("0")) {
          return false;
        }
        String waiting_list = DBService.findColumn(String.valueOf(id), "documents", "waiting_list");
        if (waiting_list != "") {
          queue(id_user, id);
        }
        String checkouts = patron.getCheckoutsId();
        if (checkouts.length() == 0) {
          DBService.updateColumn(String.valueOf(id_user), checkouts.concat(String.valueOf(id)), "users", "checkouts");
        } else {
          DBService.updateColumn(String.valueOf(id_user), checkouts.concat("," + String.valueOf(id)), "users", "checkouts");
        }
      }
      DBService.updateColumn(String.valueOf(id), String.valueOf(amount), "documents", "amount");
      DBService.sendMessageToLibrarians("You have a new checkout request");
    } catch (Exception e) {
      e.printStackTrace();
    }
    return true;
  }

  public boolean checkOutApproval(String id_user, String id_document) {
    Date date = new Date();
    long current_time = date.getTime();
    String users = "";
    boolean best = false;
    Document document = documentDAO.findById(Long.parseLong(id_document));
    if (document != null) {
      if (document.getType().equals("book")) {
        Book book = bookDAO.findByIdDocument(Long.parseLong(id_document));
        if (book != null) {
          if (book.isBestseller() == 1) {
            best = true;
          }
        }
      }

      users = document.getUsersId();
    }
    Patron patron = patronDAO.findById(Long.parseLong(id_user));
    if (patron != null) {
      if (best) {
        current_time += 14 * day;
      } else {
        current_time += patron.getMaxDays() * day;
      }
      String docs = patron.getDocumentsId();
      String deadlines = patron.getDeadlines();
      String checkouts = patron.getCheckoutsId();
      String fines = patron.getFines();
      String renews_num = DBService.findColumn(id_user, "users", "renews_num");
      if (renews_num.length() != 0) {
        renews_num = renews_num.concat("," + 0);
      } else {
        renews_num = String.valueOf(0);
      }
      if (deadlines.length() != 0) {
        deadlines = deadlines + "," + current_time;
      } else {
        deadlines = String.valueOf(current_time);
      }
      if (docs.length() != 0) {
        docs = docs + "," + id_document;
      } else {
        docs = String.valueOf(id_document);
      }
      if (fines.length() != 0) {
        fines = fines.concat("," + 0);
      } else {
        fines = String.valueOf(0);
      }
      ArrayList<String> arrayList = fromDBStringToArray(checkouts);
      arrayList.remove(arrayList.indexOf(id_document));
      checkouts = DBService.fromArrayToDBString(arrayList);
      if (users.length() != 0) {
        users = users + "," + id_user;
      } else {
        users = id_user;
      }
      DBService.updateColumn(id_user, renews_num, "users", "renews_num");
      DBService.updateColumn(id_user, deadlines, "users", "deadlines");
      DBService.updateColumn(id_user, docs, "users", "documents");
      DBService.updateColumn(id_document, users, "documents", "users");
      DBService.updateColumn(id_user, checkouts, "users", "checkouts");
      DBService.updateColumn(id_user, fines, "users", "fine");
    }
    return true;
  }

  public ArrayList<String> fromDBStringToArray(String sample) {
    ArrayList<String> arrayList = new ArrayList<>();
    if (sample != null && sample.length() != 0) {
      arrayList = new ArrayList<>(Arrays.asList(sample.split(",")));
    }
    return arrayList;
  }

  public void checkOutDisapproval(String user_id, String doc_id) {
    ArrayList<String> checkouts = fromDBStringToArray(DBService.findColumn(user_id, "users", "checkouts"));
    if (checkouts != null) {
      checkouts.remove(checkouts.indexOf(doc_id));
      DBService.updateColumn(user_id, DBService.fromArrayToDBString(checkouts), "users", "checkouts");
    }
    int amount = Integer.parseInt(DBService.findColumn(doc_id, "documents", "amount"));
    amount++;
    DBService.updateColumn(doc_id, String.valueOf(amount), "documents", "amount");
  }

  public boolean renewDocApproval(String id_user, String id_doc) {
    Date date = new Date();
    long currentTime = date.getTime();
    String docs_str = DBService.findColumn(id_user, "users", "documents");
    String deadlines_str = DBService.findColumn(id_user, "users", "deadlines");
    String renew_str = DBService.findColumn(id_user, "users", "renews");
    ArrayList<String> docs = fromDBStringToArray(docs_str);
    ArrayList<String> deadlines = fromDBStringToArray(deadlines_str);
    ArrayList<String> renews = fromDBStringToArray(renew_str);
    int index = docs.indexOf(id_doc);
    String type = DBService.findColumn(id_user, "users", "type");
    String docType = DBService.findColumn(id_doc, "documents", "type");
    int best = 0;
    if (docType.equals("book")) {
      Book doc = bookDAO.findById(Long.parseLong(id_doc));
      best = doc.isBestseller();
    } else {
      best = 0;
    }
    long add_time = 0;
    if (best == 1) {
      add_time += 14 * day;
    } else if (type.equals("Student")) {
      add_time += 21 * day;
    } else if (type.equals("Visiting Professor")) {
      add_time += 7 * day;
    } else {
      add_time += 28 * day;
    }
    currentTime += add_time;
    deadlines.remove(index);
    renews.remove(id_doc);
    deadlines.add(index, String.valueOf(currentTime));
    deadlines_str = "";
    renew_str = "";
    if (deadlines.size() == 1) {
      deadlines_str = deadlines.get(0);
    } else {
      for (int i = 0; i < deadlines.size() - 1; i++) {
        deadlines_str = deadlines_str.concat(deadlines.get(i) + ",");
      }
      deadlines_str = deadlines_str.concat(deadlines.get(deadlines.size() - 1));
    }
    if (renews.size() == 1) {
      renew_str = renews.get(0);
    } else if (renews.size() == 0) {
      renew_str = "";
    } else {
      for (int i = 0; i < renews.size() - 1; i++) {
        renew_str = renew_str.concat(renews.get(i) + ",");
      }
      renew_str = renew_str.concat(renews.get(renews.size() - 1));
    }
    String renews_num = DBService.findColumn(id_user, "users", "renews_num");
    ArrayList<String> renewsNum = fromDBStringToArray(renews_num);
    int q = Integer.parseInt(renewsNum.get(index));
    renewsNum.remove(index);
    q++;
    renewsNum.add(index, String.valueOf(q));
    DBService.updateColumn(id_user, deadlines_str, "users", "deadlines");
    DBService.updateColumn(id_user, renew_str, "users", "renews");
    return true;
  }

  public void renewDocDisApproval(String id_user, String id_doc) {
    String renew_str = DBService.findColumn(id_user, "users", "renews");
    ArrayList<String> renews = fromDBStringToArray(renew_str);
    renews.remove(id_doc);
    if (renews.size() == 1) {
      renew_str = renews.get(0);
    } else if (renews.size() == 0) {
      renew_str = "";
    } else {
      for (String id : renews) {
        renew_str = renew_str.concat("," + id);
      }
    }
    DBService.updateColumn(id_user, renew_str, "users", "renews");
  }

  public void returnDocApproval(String id_user, String id_doc) {
    String deadlines_str = DBService.findColumn(id_user, "users", "deadlines");
    String documents_str = DBService.findColumn(id_user, "users", "documents");
    String amount_str = DBService.findColumn(id_doc, "documents", "amount");
    String users_str = DBService.findColumn(id_doc, "documents", "users");
    String returns_str = DBService.findColumn(id_user, "users", "returns");
    String awaiters_str = DBService.findColumn(id_doc, "documents", "waiting_list");
    String title = DBService.findColumn(id_doc, "documents", "title");
    String fines = DBService.findColumn(id_user, "users", "fine");

    ArrayList<String> arrayList = fromDBStringToArray(documents_str);
    int index = arrayList.indexOf(id_doc);
    arrayList.remove(index);
    documents_str = DBService.fromArrayToDBString(arrayList);

    arrayList = DBService.fromDBStringToArray(deadlines_str);
    arrayList.remove(index);
    deadlines_str = DBService.fromArrayToDBString(arrayList);

    arrayList = DBService.fromDBStringToArray(fines);
    if (!arrayList.get(index).equals("0")) {
      return;
    } else {
      arrayList.remove(index);
    }
    fines = DBService.fromArrayToDBString(arrayList);

    arrayList = DBService.fromDBStringToArray(users_str);
    arrayList.remove(id_user);
    users_str = DBService.fromArrayToDBString(arrayList);

    arrayList = DBService.fromDBStringToArray(returns_str);
    try {
      arrayList.remove(id_doc);
    } catch (Exception e) {
    }
    returns_str = DBService.fromArrayToDBString(arrayList);
    if (amount_str.equals("")) amount_str = "0";
    int newAmount = Integer.parseInt(amount_str) + 1;
    ArrayList<String> awaitersId = fromDBStringToArray(awaiters_str);
    if (awaitersId.size() >= 1) {
      DBService.sendMessageToUser("The book " + title + " is available for checkout.", awaitersId.get(0));
    }
    DBService.updateColumn(id_user, deadlines_str, "users", "deadlines");
    DBService.updateColumn(id_user, documents_str, "users", "documents");
    DBService.updateColumn(id_user, fines, "users", "fine");
    DBService.updateColumn(id_doc, users_str, "documents", "users");
    DBService.updateColumn(id_doc, String.valueOf(newAmount), "documents", "amount");
    DBService.updateColumn(id_user, returns_str, "users", "returns");

  }

  public void outstandingRequest(String docId) {
    Date date = new Date();
    long currentTime = date.getTime();
    String title = DBService.findColumn(docId, "documents", "title");
    String awaiters = DBService.findColumn(docId, "documents", "waiting_list");
    ArrayList<String> awaitersId = fromDBStringToArray(awaiters);
    for (String id : awaitersId) {
      String waitingList = DBService.findColumn(id, "users", "waiting_list");
      ArrayList<String> waitingListId = fromDBStringToArray(waitingList);
      waitingListId.remove(waitingList.indexOf(docId));
      waitingList = DBService.fromArrayToDBString(waitingListId);
      DBService.updateColumn(id, waitingList, "users", "waiting_list");
      DBService.sendMessageToUser("The book " + title + " is no longer available.", id);
    }
    DBService.updateColumn(docId, "", "documents", "waiting_list");
    DBService.updateColumn(docId, String.valueOf(0), "documents", "available");
    String users = DBService.findColumn(docId, "documents", "users");
    ArrayList<String> usersId = fromDBStringToArray(users);
    for (String id : usersId) {
      String docs = DBService.findColumn(id, "users", "documents");
      String deadlines = DBService.findColumn(id, "users", "deadlines");
      ArrayList<String> docsId = fromDBStringToArray(docs);
      ArrayList<String> deadlinesId = fromDBStringToArray(deadlines);
      int index = docsId.indexOf(docId);
      deadlinesId.remove(index);
      deadlinesId.add(index, String.valueOf(currentTime));
      deadlines = DBService.fromArrayToDBString(deadlinesId);
      DBService.updateColumn(id, deadlines, "users", "deadlines");
      DBService.sendMessageToUser("Please return book " + title + " to the library", id);
    }
  }

  public void deleteKeywords(ArrayList<String> keywords, long id) {
    if (keywords == null || keywords.size() == 0) {
      return;
    }
    for (String keyword : keywords) {
      Keyword key = keywordDAO.findById(Long.parseLong(keyword));
      if (key != null) {
        ArrayList<String> docs = fromDBStringToArray(key.getDocumentsId());
        if (docs != null) {
          if (docs.size() == 1 && docs.get(0).equals(String.valueOf(id))) {
            keywordDAO.delete(key.getId());
          } else {
            ArrayList<String> keyword_docs = fromDBStringToArray(key.getDocumentsId());
            keyword_docs.remove(keyword_docs.indexOf(String.valueOf(id)));
            key.setDocumentsId(DBService.fromArrayToDBString(keyword_docs));
            keywordDAO.update(key);
          }
        }
      }
    }
  }


  public void deleteAuthors(ArrayList<String> authors, String id) {
    if (authors == null || authors.size() == 0) {
      return;
    }
    for (String string : authors) {
      Author author = authorDAO.findById(Long.parseLong(string));
      if (author != null) {
        ArrayList<String> docs = fromDBStringToArray(author.getDocumentsId());
        if (docs != null) {
          if (docs.size() == 1 && docs.get(0).equals(id)) {
            authorDAO.delete(author.getId());
          } else {
            ArrayList<String> author_docs = fromDBStringToArray(author.getDocumentsId());
            author_docs.remove(author_docs.indexOf(id));
            author.setDocumentsId(DBService.fromArrayToDBString(author_docs));
            authorDAO.update(author);
          }
        }
      }
    }
  }

  public void editBook(Book book) {
    ArrayList<String> authorsList = fromDBStringToArray(book.getAuthorsId());
    ArrayList<String> keywordsList = fromDBStringToArray(book.getKeywordsId());
    editAuthors(authorsList, book.getDocumentId());
    editKeywords(keywordsList, book.getDocumentId());
    documentDAO.updateInfo(book);
    bookDAO.updateByIdDocument(book);
  }

  public void editKeywords(ArrayList<String> keywords, long id) {
    ArrayList<String> previousKeywords = fromDBStringToArray(DBService.findColumn(String.valueOf(id), "documents", "keywords"));
    ArrayList<String> keywordsToDelete = new ArrayList<>();
    for (String s : previousKeywords) {
      if (!keywords.contains(s)) {
        keywordsToDelete.add(s);
      }
    }
    ArrayList<String> keywordsToAdd = new ArrayList<>();
    for (String s : keywords) {
      if (!previousKeywords.contains(s)) {
        keywordsToAdd.add(s);
      }
    }
    deleteKeywords(keywordsToDelete, id);
    saveKeywords(keywordsToAdd, id);
  }

  public void editAuthors(ArrayList<String> authors, long id) {
    ArrayList<String> previousAuthors = fromDBStringToArray(DBService.findColumn(String.valueOf(id), "documents", "authors"));
    ArrayList<String> authorsToDelete = new ArrayList<>();
    for (String s : previousAuthors) {
      if (!authors.contains(s)) {
        authorsToDelete.add(s);
      }
    }
    ArrayList<String> authorsToAdd = new ArrayList<>();
    for (String s : authors) {
      if (!previousAuthors.contains(s)) {
        authorsToAdd.add(s);
      }
    }
    deleteAuthors(authorsToDelete, String.valueOf(id));
    saveAuthors(authorsToAdd, id);
  }

  public ArrayList<String> saveAuthors(ArrayList<String> authors, long id) {
    ArrayList<String> authorsId = new ArrayList<>();
    for (String author : authors) {
      String name = "";
      String surname;
      if (author.contains(".")) {
        int i = 0;
        while (author.charAt(i) != '.') {
          name = name + author.charAt(i);
          i++;
        }
        name = name.concat(".");
        surname = author.substring(i + 2);
      } else {
        int i = 0;
        while (author.charAt(i) != ' ') {
          name = name + author.charAt(i);
          i++;
        }
        surname = author.substring(i + 1);
      }
      Author author1 = authorDAO.findByNameAndSurname(name, surname);
      if (author1 == null) {
        Author author_obj = new Author(name, surname);
        author_obj.setDocumentsId(String.valueOf(id));
        authorDAO.insert(author_obj);
        authorsId.add(String.valueOf(authorDAO.findLastId()));
      } else {
        author1.setDocumentsId(author1.getDocumentsId().concat("," + id));
        authorDAO.update(author1);
        authorsId.add(String.valueOf(author1.getId()));
      }
    }
    return authorsId;
  }

  public ArrayList<String> saveKeywords(ArrayList<String> keywords, long id) {
    ArrayList<String> keywordsId = new ArrayList<>();
    if (keywords == null || keywords.size() == 0) {
      return null;
    }
    for (String keyword : keywords) {
      Keyword key = keywordDAO.findByKeyword(keyword);
      if (key == null) {
        Keyword keyword1 = new Keyword(keyword);
        keyword1.setDocumentsId(String.valueOf(id));
        keywordDAO.insert(keyword1);
        keywordsId.add(String.valueOf(keywordDAO.findLastId()));
      } else {
        key.setDocumentsId(key.getDocumentsId().concat("," + id));
        keywordDAO.update(key);
        keywordsId.add(String.valueOf(key.getId()));
      }
    }
    return keywordsId;
  }

  public void saveDocument(Document document) {
    document.setAuthorsId("");
    document.setKeywordsId("");
    documentDAO.insert(document);
    long docId = documentDAO.findLastId();
    document.setId(docId);
    String type = document.getType();
    switch (type) {
      case "book":
        bookDAO.insert((Book) document);
        break;
      case "":
        journalArticleDAO.insert((JournalArticle) document);
        break;
      case "av":
        avMaterialDAO.insert((AVMaterial) document);
        break;
    }
    ArrayList<String> authorsList = fromDBStringToArray(document.getAuthorsAsString());
    ArrayList<String> keywordsList = fromDBStringToArray(document.getKeywordsAsString());
    String authorsId = DBService.fromArrayToDBString(saveAuthors(authorsList, docId));
    String keywordsId = DBService.fromArrayToDBString(saveKeywords(keywordsList, docId));
    DBService.updateColumn(String.valueOf(docId), authorsId, "documents", "authors");
    DBService.updateColumn(String.valueOf(docId), keywordsId, "documents", "keywords");
  }

  public void deleteBook(long id) {
    bookDAO.deleteByDocumentId(id);
  }

  public void deleteJournalArticle(long id) {
    journalArticleDAO.delete(id);
  }

  public void deleteAV(long id) {
    avMaterialDAO.delete(id);
  }

  public void deleteDocument(long id) {
    Document document = documentDAO.findById(id);
    if (document.getUsersId().length() != 0) {
      return;
    } else {
      if (document.getAwaitersId().length() != 0) {
        String[] awaiters = document.getAwaitersId().split(",");
        for (String user_id : awaiters) {
          Patron patron = patronDAO.findById(Long.parseLong(user_id));
          DBService.sendMessageToUser("The document is no longer available.", user_id);
          ArrayList<String> waiting_list = fromDBStringToArray(patron.getWaitingListId());
          waiting_list.remove(waiting_list.indexOf(String.valueOf(id)));
          String waiting = DBService.fromArrayToDBString(waiting_list);
          patron.setWaitingListId(waiting);
          patronDAO.update(patron);
        }
      }
      switch (document.getType()) {
        case "book":
          deleteBook(id);
          documentDAO.delete(id);
          break;
        case "journal article":
          deleteJournalArticle(id);
          documentDAO.delete(id);
          break;
        case "av":
          deleteAV(id);
          documentDAO.delete(id);
          break;
      }
      deleteKeywords(fromDBStringToArray(document.getKeywordsId()), id);
      deleteAuthors(fromDBStringToArray(document.getAuthorsId()), String.valueOf(id));
    }
  }
}