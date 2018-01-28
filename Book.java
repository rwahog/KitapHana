import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Book extends Document {
    protected String publisher;
    protected int year, edition_number;
    protected boolean best_seller;
    Book(Connection connection, Scanner in) throws SQLException {
        super(connection, in);
        best_seller = false;
    }

    @Override
    public void read() throws SQLException {
        super.read();
        System.out.println("Publisher: ");
        setPublisher(in.next());
        System.out.println("Year: ");
        setYear(in.nextInt());
        System.out.println("Edition number: ");
        setEdition_number(in.nextInt());
        System.out.println("Best_seller: ");
        setBest_seller(in.nextBoolean());
    }

    @Override
    public void save() throws SQLException {
        super.save();
        statement.executeUpdate("update documents set type = 'book' where id = '"+id+"'");
        statement.executeUpdate("update books set publisher = '"+publisher+"', year='"+year+"', edititon_number = '"+edition_number+"', best_seller = '"+best_seller+"' where id = '"+id+"'");
    }
    @Override
    public void setVariablesKnowingTitle() throws SQLException {
        super.setVariablesKnowingTitle();
        ResultSet resultSet = statement.executeQuery("select * from books where id = '"+id+"'");
        if(resultSet.next()){
            setYear(resultSet.getInt("year"));
            setBest_seller(resultSet.getBoolean("best_seller"));
            setEdition_number(resultSet.getInt("edition_number"));
            setPublisher(resultSet.getString("publisher"));
        }
    }

    //Publisher
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
    public String getPublisher() {
        return publisher;
    }
    //Year
    public void setYear(int year) {
        this.year = year;
    }
    public int getYear() {
        return year;
    }
    //Edition_number
    public void setEdition_number(int edition_number) {
        this.edition_number = edition_number;
    }
    public int getEdition_number() {
        return edition_number;
    }
    //Best_seller
    public void setBest_seller(boolean best_seller) {
        this.best_seller = best_seller;
    }
    public boolean isBest_seller() {
        return best_seller;
    }
}
