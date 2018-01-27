import java.sql.SQLException;
import java.util.ArrayList;

public interface Document_interface {
    //Price
    public void setPrice(int price) throws SQLException;

    public int getPrice();

    //Authors

    public ArrayList<Author> getAuthors();

    public void addAuthor(Author name) throws SQLException;

    public void removeAuthor(Author name) throws SQLException;

    public String getAuthorsAsString();

    //Keywords

    public ArrayList<Keyword> getKeywords();

    public void addKeyword(Keyword keyword) throws SQLException;

    public void removeKeyword(Keyword keyword) throws SQLException;

    public String getKeywordsAsString();

    //Title
    public void setTitle(String title) throws SQLException;

    public String getTitle();

    //Amount
    public void setAmount(int amount) throws SQLException;

    public int getAmount();
}
