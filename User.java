import javax.print.Doc;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class User implements User_interface {
    protected String phone_number, name, surname;
    protected Address address;
    protected long card_number;
    protected int id;
    protected ArrayList<Document> documents;
    protected Statement statement;
    protected Connection connection;
    protected Scanner in;
    protected Library library;
    User(Connection connection, Scanner in) throws SQLException {
        this.connection = connection;
        this.statement = connection.createStatement();
        this.in = in;
        library = new Library(connection, in);
    }
    public void read() throws SQLException {
        System.out.println("Name: ");
        setName(in.next());
        System.out.println("Surname: ");
        setSurname(in.next());
        System.out.println("Phone number: ");
        setPhone_number(in.next());
        address = new Address(connection, in);
        address.readAddress();
        setCard_number(getPhone_number());
    }
    public void save() throws SQLException {
        ResultSet resultSet = statement.executeQuery("select * from users where card_number = '"+card_number+"'");
        address.save();
        if(resultSet.next()){
            statement.executeUpdate("update users name = '"+name+"', surname = '"+surname+"', phone_number ='"+phone_number+"', card_number = '"+card_number+"', id_address = '"+address.getId_address()+" where id = '"+id+"'");
        }
        else {
            int id_address = address.getId_address();
            statement.executeUpdate("insert into users (name, surname, phone_number, card_number, id_address) values('" + name + "', '" + surname + "', '" + phone_number + "', '" + card_number + "', '" + id_address + "')");
            resultSet = statement.executeQuery("select * from users where card_number = '" + card_number + "'");
            if (resultSet.next()) {
                id = resultSet.getInt("id");
            }
        }
    }
    public void login() throws SQLException {
        System.out.println("Phone number: ");
        setPhone_number(in.next());
        ResultSet resultSet = statement.executeQuery("select * from users where phone_number = '"+phone_number+"'");
        System.out.println("Card number: ");
        card_number = in.nextLong();
        if(resultSet.next()){
            if(card_number == resultSet.getLong("card_number")){
                setVariablesKnowingCard_number(card_number);
            }
            else{
                System.out.println("Wrong card number");
            }
        }
        else{
            System.out.println("There is no user with this phone number");
        }
    }
    public void setVariablesKnowingNameSurname(String name, String surname) throws SQLException {
        setName(name);
        setSurname(surname);
        ResultSet resultSet = statement.executeQuery("select * from users where name = '"+name+"', surname = '"+surname+"'");
        if(resultSet.next()){
            setPhone_number(resultSet.getString("phone_number"));
            id = resultSet.getInt("id");
            setCard_number(resultSet.getString("card_number"));
            setDocumentsFromString(resultSet.getString("documents"));
            Address address = new Address(connection,in);
            address.setVariablesKnowingId_address(resultSet.getInt("id_address"));
        }
    }
    public void setVariablesKnowingCard_number(long card_number) throws SQLException {
        this.card_number = card_number;
        ResultSet resultSet = statement.executeQuery("select * from users where card_number = '"+card_number+"'");
        if(resultSet.next()){
            setName(resultSet.getString("name"));
            setSurname(resultSet.getString("surname"));
            setPhone_number(resultSet.getString("phone_number"));
            id = resultSet.getInt("id");
            setDocumentsFromString(resultSet.getString("documents"));
            Address address = new Address(connection,in);
            address.setVariablesKnowingId_address(resultSet.getInt("id_address"));
        }
    }
    //Address
    public void setAddress(Address address) throws SQLException {
        this.address = address;
    }

    public Address getAddress() {
        return address;
    }

    //Phone_number
    public void setPhone_number(String phone_number) throws SQLException {
        this.phone_number = phone_number;
    }

    public String getPhone_number() {
        return phone_number;
    }

    //Name
    public void setName(String name) throws SQLException {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    //Surname
    public void setSurname(String surname) throws SQLException {
        this.surname = surname;
    }

    public String getSurname() {
        return surname;
    }

    //Card_number
    public void setCard_number(String s) throws SQLException {
        long p = 31, hash = 0, mod = 1000000007;
        for(int i = 0; i<s.length(); i++){
            hash*=p;
            hash%=mod;
            hash+=s.charAt(i);
            hash%=mod;
        }
        this.card_number = hash;
    }

    public long getCard_number() {
        return card_number;
    }
    //Id

    public int getId() {
        return id;
    }
    //Documents
    public ArrayList<Document> getDocuments() {
        return documents;
    }
    public String getDocumentsAsString(){
        String s = "";
        for(int i = 0; i<documents.size(); i++){
            if(i<documents.size()-1) s = s.concat(documents.get(i).getTitle() + ", ");
            else s = s.concat(documents.get(i).getTitle());
        }
        return s;
    }
    public void addDocument(Document document) throws SQLException {
        documents.add(document);
    }
    public void setDocumentsFromString(String s) throws SQLException {
        for(int i = 0; i<s.length(); i++){
            int j = i;
            String cur = "";
            while(j<s.length() && s.charAt(j)!=','){
                cur = cur.concat(String.valueOf(s.charAt(j)));
                j++;
            }
            i = j+1;
            Document document = new Document(connection, in);
            document.setTitle(cur);
            addDocument(document);
        }
    }
    //Actions

    public void checkOutDocument(Document document) throws SQLException {
        if(document.getAmount() > 0){
            addDocument(document);
            document.decreaseAmount();
        }
        else{
            System.out.println("You can't book this document");
        }
    }
    public void returnDocument(Document document){

    }
    public Document getDocumentByTitle(String title) throws SQLException {
        return library.getDocumentByTitle(title);
    }
    public String searchDocumentByPossibleTitle(String title) throws SQLException { // Ищет документ по частично
        return library.searchDocumentByPossibleTitle(title);
    }
    public String searchDocumentByAuthor(String name) throws SQLException {
        return library.searchDocumentByAuthor(name);
    }
    public String searchDocumentByKeyword(String keyword) throws SQLException {
        return library.searchDocumentByKeyword(keyword);
    }

}
