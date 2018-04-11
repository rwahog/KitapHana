package com.kitaphana.Service;

import com.kitaphana.Database.Database;
import com.kitaphana.Entities.*;
import com.kitaphana.dao.*;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class DocumentService {
    public Database db = Database.getInstance();
    documentDAOImpl documentDAO = new documentDAOImpl();
    bookDAOImpl bookDAO = new bookDAOImpl();
    userDAOImpl userDAO = new userDAOImpl();
    authorDAOImpl authorDAO = new authorDAOImpl();
    journalArticleDAOImpl journalArticleDAO = new journalArticleDAOImpl();
    keywordDAOImpl keywordDAO = new keywordDAOImpl();
    avMaterialDAOImpl avMaterialDAO = new avMaterialDAOImpl();
    DBService DBService = new DBService();
    long day = 24*1000*60*60;

    public ArrayList<Document> findAll() {
        ArrayList<Document> docs = documentDAO.findAll();
        for (Document doc : docs) {
            setAuthors(doc);
            setKeywords(doc);
        }
        return docs;
    }

    public Document findById(long id) {
        Document document = documentDAO.findById(id);
        setKeywords(document);
        setAuthors(document);

        return document;
    }

    public void setKeywords(Document document) {
        ArrayList<String> keywordsId = fromDBStringToArray(document.getKeywordsId());
        ArrayList<Keyword> keywords = new ArrayList<>();
        for (String keyId : keywordsId) {
            Keyword keyword = keywordDAO.findById(Long.parseLong(keyId));
            keywords.add(keyword);
        }
        document.setKeywords(keywords);
    }

    public void setAuthors(Document document) {
        ArrayList<String> authorsId = fromDBStringToArray(document.getAuthorsId());
        ArrayList<Author> authors = new ArrayList<>();
        for (String authorId : authorsId) {
            Author author = authorDAO.findById(Long.parseLong(authorId));
            authors.add(author);
        }
        document.setAuthors(authors);
    }

    public Document setDocInfo(String id) {
        Document document = new Document();
        try {
            String type = DBService.findColumn(id, "documents","type");
            switch (type) {
                case "book":
                    type = "books";
                    break;
                case "ja":
                    type = "ja";
                    break;
                case "av":
                    type = "av";
                    break;
            }
            document = DBService.findDocumentAndTypeInfo(id, type);
            setAuthors(document);
            setKeywords(document);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return document;
    }

    public boolean queue(long id_user, int id_doc) throws SQLException {
        User user = userDAO.findById(id_user);
        String docsId = user.getDocuments();
        if (docsId != null && docsId.length() != 0) {
            ArrayList docs = fromDBStringToArray(docsId);
            boolean exist = docs.contains(id_doc);
            if (exist) {
                return false;
            }
        }
        if (!user.getPossibleType().equals(user.getType())) {
            return false;
        }
        Document document = documentDAO.findById(id_doc);
        String awaiters = document.getAwaiters();
        if (awaiters != null && awaiters.length() != 0) {
            PriorityComparator comparator = new PriorityComparator();
            PriorityQueue<User> priorityQueue = new PriorityQueue<>(10, comparator);
            ArrayList<String> awaiters_arr = DBService.fromDBStringToArray(awaiters);
            for (String user_id : awaiters_arr) {
                User awaiter = userDAO.findById(Long.parseLong(user_id));
                awaiter.setPriority();
                priorityQueue.add(awaiter);
            }
            user.setPriority();
            priorityQueue.add(user);
            awaiters = "";
            awaiters = awaiters.concat(String.valueOf(priorityQueue.poll().getId()));
            for (User usr : priorityQueue) {
                awaiters = awaiters.concat("," + usr.getId());
            }
        } else {
            awaiters = String.valueOf(id_user);
        }
        document.setAwaiters(awaiters);
        documentDAO.update(document);
        String user_waitlist = user.getWaitingList();
        if (user_waitlist != null && user_waitlist.length() != 0) {
            user_waitlist = user_waitlist.concat("," + id_doc);
        } else {
            user_waitlist = String.valueOf(id_doc);
        }
        user.setWaitingList(user_waitlist);
        userDAO.update(user);
        return true;
    }

    public boolean checkOut(long id_user, int id) {
        try {
            User user = userDAO.findById(id_user);
            Document document = documentDAO.findById(id);
            int amount = document.getAmount();
            amount--;
            if (user != null) {
                ArrayList docs = fromDBStringToArray(user.getDocuments());
                ArrayList checks = fromDBStringToArray(user.getCheckouts());
                if (docs != null && docs.contains(id)) {
                    return false;
                }
                if (!user.getPossibleType().equals(user.getType())) {
                    return false;
                }
                if (checks != null && checks.contains(String.valueOf(id))) {
                    return false;
                }
                String available = DBService.findColumn(String.valueOf(id), "documents", "available");
                if (available.equals("0")) {
                    return false;
                }
                String checkouts = user.getCheckouts();
                if (checkouts.length() == 0) {
                    DBService.updateColumn(String.valueOf(id_user), checkouts.concat(String.valueOf(id)), "users", "checkouts");
                } else {
                    DBService.updateColumn(String.valueOf(id_user), checkouts.concat("," + String.valueOf(id)), "users","checkouts");
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
            users = document.getUsers();
        }
        User user = userDAO.findById(Long.parseLong(id_user));
        if (user != null) {
            user.setMaxDays();
            if (best) {
                current_time += 14 * day;
            } else {
                current_time += user.getMaxDays() * day;
            }
            String docs = user.getDocuments();
            String deadlines = user.getDeadlines();
            String checkouts = user.getCheckouts();
            String fines = user.getFine();
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
            DBService.updateColumn(id_user, deadlines, "users", "deadlines");
            DBService.updateColumn(id_user, docs, "users", "documents");
            DBService.updateColumn(id_document, users, "documents","users");
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
        String docs_str = DBService.findColumn(id_user, "users","documents");
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
        DBService.updateColumn(id_user, deadlines_str,"users","deadlines");
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

        ArrayList<String> arrayList = fromDBStringToArray(documents_str);
        int index = arrayList.indexOf(id_doc);
        arrayList.remove(index);
        documents_str = DBService.fromArrayToDBString(arrayList);

        arrayList = DBService.fromDBStringToArray(deadlines_str);
        arrayList.remove(index);
        deadlines_str = DBService.fromArrayToDBString(arrayList);

        arrayList = DBService.fromDBStringToArray(users_str);
        arrayList.remove(id_user);
        users_str = DBService.fromArrayToDBString(arrayList);

        arrayList = DBService.fromDBStringToArray(returns_str);
        try {
            arrayList.remove(id_doc);
        }catch (Exception e){}
        returns_str = DBService.fromArrayToDBString(arrayList);
        if (amount_str.equals("")) amount_str = "0";
        int newAmount = Integer.parseInt(amount_str) + 1;

        DBService.updateColumn(id_user, deadlines_str, "users", "deadlines");
        DBService.updateColumn(id_user, documents_str, "users", "documents");
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
        for (String keyword: keywords) {
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
        for (String string: authors) {
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
        ArrayList<String> authorsToDelete = new ArrayList<> ();
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

    public void saveAuthors(ArrayList<String> authors, long id) {
        for (String author: authors) {
            String name = "";
            String surname;
            if (author.contains(".")) {
                int i = 0;
                while (author.charAt(i) != '.') {
                    name = name + author.charAt(i);
                    i++;
                }
                name = name.concat(".");
                surname = author.substring(i + 1);
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
            } else {
                author1.setDocumentsId(author1.getDocumentsId().concat("," + id));
                authorDAO.update(author1);
            }
        }
    }

    public void saveKeywords(ArrayList<String> keywords, long id) {
        if (keywords == null || keywords.size() == 0) {
            return;
        }
        for (String keyword: keywords) {
            Keyword key = keywordDAO.findByKeyword(keyword);
            if (key == null) {
                Keyword keyword1 = new Keyword(keyword);
                keyword1.setDocumentsId(String.valueOf(id));
                keywordDAO.insert(keyword1);
            } else {
                key.setDocumentsId(key.getDocumentsId().concat("," + id));
                keywordDAO.update(key);
            }
        }
    }

    public void saveDocument(Document document) {
        documentDAO.insert(document);
        document.setId(documentDAO.findLastId());
        String type = document.getType();
        switch (type) {
            case "book":
                bookDAO.insert((Book) document);
                break;
            case "ja":
                journalArticleDAO.insert((JournalArticle) document);
                break;
        }
        ArrayList<String> authorsList = fromDBStringToArray(document.getAuthorsId());
        ArrayList<String> keywordsList = fromDBStringToArray(document.getKeywordsId());
        saveAuthors(authorsList, documentDAO.findLastId());
        saveKeywords(keywordsList, documentDAO.findLastId());
    }

    public void deleteBook(long id) {
        bookDAO.deleteByDocumentId(id);
    }

    public void deleteJournalArticle(long id) {
        journalArticleDAO.delete(id);
    }

    public void deleteAV(long id) {
        avMaterialDAO.delete(String.valueOf(id));
    }

    public void deleteDocument(long id) throws SQLException {
        Document document = documentDAO.findById(id);
        if (document.getUsers() != null && document.getUsers().length() != 0) {
            return;
        } else {
            if (document.getAwaiters() != null && document.getAwaiters().length() != 0) {
                 String[] awaiters = document.getAwaiters().split(",");
                for (String user_id: awaiters) {
                    User user = userDAO.findById(Long.parseLong(user_id));
                    ArrayList<String> waiting_list = fromDBStringToArray(user.getWaitingList());
                    waiting_list.remove(waiting_list.indexOf(String.valueOf(id)));
                    String waiting = DBService.fromArrayToDBString(waiting_list);
                    user.setWaitingList(waiting);
                    userDAO.update(user);
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
