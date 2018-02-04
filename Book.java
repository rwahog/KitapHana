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
        GUI.print("Publisher: ");
        setPublisher(GUI.read());
        GUI.print("Year: ");
        setYear(Integer.parseInt(GUI.read()));
        GUI.print("Edition number: ");
        setEdition_number(Integer.parseInt(GUI.read()));
        GUI.print("Best_seller: ");
        setBest_seller(in.nextBoolean());
    }

    @Override
    public void save() throws SQLException {
        super.save();
        statement.executeUpdate("update documents set type = 'book' where id = '"+id+"'");
        ResultSet resultSet = statement.executeQuery("select * from books where id_document = '"+id+"'");
        if(resultSet.next()) {
            statement.executeUpdate("update books set title = '"+title+"', publisher = '" + publisher + "', year='" + year + "', edition_number = '" + edition_number + "', best_seller = '" + getBest_sellerAsInt() + "' where id_document = '" + id + "'");
        }
        else{
            statement.executeUpdate("insert into books (title, publisher, year, edition_number, best_seller, id_document) values('"+title+"', '"+publisher+"', '"+year+"', '"+edition_number+"', '"+getBest_sellerAsInt()+"', '"+id+"')");
        }
    }
    @Override
    public void setVariablesKnowingTitle() throws SQLException {
        super.setVariablesKnowingTitle();
        ResultSet resultSet = statement.executeQuery("select * from books where id = '"+id+"'");
        if(resultSet.next()){
            setYear(resultSet.getInt("year"));
            setBest_sellerFromInt(resultSet.getInt("best_seller"));
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
    public int getBest_sellerAsInt(){
        return best_seller?1:0;
    }
    public void setBest_sellerFromInt(int best_seller){
        this.best_seller = (best_seller==0) ? true:false;
    }
}