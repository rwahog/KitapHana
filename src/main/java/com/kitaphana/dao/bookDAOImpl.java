package com.kitaphana.dao;

import com.kitaphana.Database.Database;
import com.kitaphana.Entities.Book;
import com.kitaphana.exceptions.DocumentNotFoundException;
import com.kitaphana.exceptions.OperationFailedException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class bookDAOImpl implements bookDAO {

  Database db = Database.getInstance();
  commonDAOImpl commonDAO = new commonDAOImpl();
  //Prepared statements which will be used in methods below.
  private static final String FIND_ALL = "SELECT id FROM books";
  private static final String FIND_BY_ID = "SELECT * FROM books WHERE id=?";
  private static final String UPDATE = "UPDATE books SET title=?, publisher=?, year=?, edition_number=?, best_seller=? WHERE id=?";
  private static final String UPDATE_BY_ID_DOCUMENT = "UPDATE books SET title=?, publisher=?, year=?, edition_number=?, best_seller=? WHERE document_id=?";
  private static final String FIND_BY_ID_DOCUMENT = "SELECT * FROM books WHERE document_id=?";
  private static final String INSERT = "INSERT INTO books (title, publisher, year, edition_number, best_seller, document_id) VALUES (?,?,?,?,?,?)";

  /**
   * Search database table "books" to find information about book
   * by argument (id) which is referred to actual doc id in table
   * "documents". Return null if the object is not present.
   *
   * @param id - document id (SQL table column)
   * @return - book object
   */
  public Book findByIdDocument(long id) {
    Book book;
    try {
      PreparedStatement ps = db.connect().prepareStatement(FIND_BY_ID_DOCUMENT);
      ps.setLong(1, id);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        book = getVariables(rs);
      } else {
        throw new DocumentNotFoundException();
      }

      rs.close();
      ps.close();
    } catch (SQLException e) {
      throw new OperationFailedException();
    }

    return book;
  }

  /**
   * Search database table "books" by argument (id) which is
   * referred to actual book id in table "books" to find
   * information about book. Return null if object is not
   * present.
   *
   * @param id - book id (column)
   * @return - book object
   */
  @Override
  public Book findById(long id) {
    Book book;
    try {
      PreparedStatement ps = db.connect().prepareStatement(FIND_BY_ID);
      ps.setLong(1, id);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        book = getVariables(rs);
      } else {
        throw new DocumentNotFoundException();
      }

      rs.close();
      ps.close();
    } catch (SQLException e) {
      throw new OperationFailedException();
    }
    return book;
  }

  /**
   * Retrieve all objects of books from database
   * table "books".
   *
   * @return - list of book objects
   */
  @Override
  public List<Book> findAll() {
    ArrayList<Book> books = new ArrayList<>();

    try {
      PreparedStatement ps = db.connect().prepareStatement(FIND_ALL);
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        Book book = findById(rs.getLong("id"));
        books.add(book);
      }
      rs.close();
      ps.close();
    } catch (SQLException e) {
      throw new OperationFailedException();
    }
    return books;
  }

  /**
   * Add new object into the database table "books".
   *
   * @param book - book object
   */
  @Override
  public void insert(Book book) {
    try {
      PreparedStatement ps = db.con.prepareStatement(INSERT);
      setVariables(book, ps);

      ps.executeUpdate();
      ps.close();
    } catch (SQLException e) {
      throw new OperationFailedException();
    }
  }

  /**
   * Update information about book which
   * is passing as argument.
   *
   * @param book - book object
   */
  @Override
  public void update(Book book) {
    try {
      PreparedStatement ps = db.connect().prepareStatement(UPDATE);
      setVariables(book, ps);
      ps.setLong(6, book.getId());

      ps.executeUpdate();
      ps.close();
    } catch (SQLException e) {
      throw new OperationFailedException();
    }
  }

  public void updateByIdDocument(Book book) {
    try {
      PreparedStatement ps = db.connect().prepareStatement(UPDATE_BY_ID_DOCUMENT);
      setVariables(book, ps);
      ps.setLong(6, book.getDocumentId());

      ps.executeUpdate();
      ps.close();
    } catch (SQLException e) {
      throw new OperationFailedException();
    }
  }

  /**
   * Delete all information about book from
   * the database table "books".
   *
   * @param id - book id
   */
  @Override
  public void delete(long id) {
    commonDAO.delete(id, "books", "id");
  }

  public void deleteByDocumentId(long id) {
    commonDAO.delete(id, "books", "document_id");
  }

  private Book getVariables(ResultSet rs) {
    Book book;
    try {
      book = new Book();
      book.setDocumentId(rs.getInt("document_id"));
      book.setTitle(rs.getString("title"));
      book.setPublisher(rs.getString("publisher"));
      book.setYear(rs.getInt("year"));
      book.setEditionNumber(rs.getInt("edition_number"));
      book.setBestseller(rs.getInt("best_seller"));
    } catch (SQLException e) {
      throw new OperationFailedException();
    }
    return book;
  }

  private void setVariables(Book book, PreparedStatement ps) {
    try {
      ps.setString(1, book.getTitle());
      ps.setString(2, book.getPublisher());
      ps.setInt(3, book.getYear());
      ps.setInt(4, book.getEditionNumber());
      ps.setInt(5, book.isBestseller());
    } catch (SQLException e) {
      throw new OperationFailedException();
    }
  }
}
