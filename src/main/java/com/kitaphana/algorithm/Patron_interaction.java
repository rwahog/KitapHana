package com.kitaphana.algorithm;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Patron_interaction {
    static String command;
    static Library library;
    static Patron patron;
    public static void command(Connection conn, Scanner in, Patron patronCur) throws SQLException {
        patron  = patronCur;
        library = new Library(conn, in);
        System.out.println("Choose your command: search for/check out/return document/exit");
        command = in.nextLine();
        switch (command){
            case("search for"):
                searchFor(conn, in);
                break;
            case("check out") :
                checkOut(conn, in);
                break;

            case("return document")    :
                returnDoc(conn, in);
                break;
            case("exit"):
                GUI.exit();
                break;
            default: System.out.println("WRONG INPUT");
        }
        command(conn, in, patron);
    }

    private static void returnDoc(Connection conn, Scanner in) throws SQLException {
        Document doc = searchFor(conn, in);
        patron.returnDocument(doc);
        doc.save();
        patron.save();
    }

    private static void checkOut(Connection conn, Scanner in) throws SQLException {

        Document document = searchFor(conn, in);
        patron.checkOutDocument(document);
        document.save();
        patron.save();
    }

    public static Document searchFor(Connection conn, Scanner in) throws SQLException {
        System.out.println("Author/Keyword/PossibleTitle");
        command = in.nextLine();
        Document doc = new Document(conn, in);
        switch (command){
            case("Author"):
                System.out.println("write the Author");
                Author author = new Author(conn, in);
                author.setName(in.nextLine());
                author.setSurname(in.nextLine());
                author.setVariablesKnowingNameSurname();
                doc = patron.getDocumentByTitle(library.searchDocumentByAuthor(author));
                break;
            case("PossibleTitle"):
                System.out.println("write the Title");
                doc = patron.getDocumentByTitle(in.nextLine());
                break;
            case("Keyword"):
                System.out.println("write the keyword");
                Keyword keyword = new Keyword(conn, in);
                keyword.setKeyword(in.nextLine());
                keyword.setVariablesKnowingKeyword();
                doc = patron.getDocumentByTitle(library.searchDocumentByKeyword(keyword));
                break;
        }
        return doc;
    }

}
