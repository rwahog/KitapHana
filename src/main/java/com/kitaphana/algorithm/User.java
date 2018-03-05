import javax.print.Doc;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class User {
    protected String phone_number, name, surname, password, possible_type, type, email;
    protected Address address;
    protected long card_number, maxdays, day = 24*60*60*1000;
    protected int id;
    protected ArrayList<Document> documents;
    protected ArrayList<Long> deadlines;
    protected Statement statement;
    protected Connection connection;
    protected Scanner in;
    protected Library library;
    User(Connection connection, Scanner in) throws SQLException {
        this.connection = connection;
        this.statement = connection.createStatement();
        this.in = in;
        library = new Library(connection, in);
        documents = new ArrayList<Document>();
        deadlines = new ArrayList<Long>();
        address = new Address(connection, in);
        maxdays = 14;
    }
    public void read() throws SQLException {
        System.out.println("Name: ");
        setName(in.next());
        System.out.println("Surname: ");
        setSurname(in.next());
        System.out.println("Possible type: ");
        setPossible_type(in.next());
        System.out.println("Phone number: ");
        setPhone_number(in.next());
        System.out.println("Password: ");
        setPassword(in.next());
        System.out.println("Confirm password: ");
        checkPassword(in.next());
        System.out.println("Email: ");
        setEmail(in.next());
        address = new Address(connection, in);
        address.readAddress();
        setCard_number(getPhone_number());
    }
    public void save() throws SQLException {
        ResultSet resultSet = statement.executeQuery("select * from users where card_number = '"+card_number+"'");
        address.save();
        if(resultSet.next()){
            statement.executeUpdate("update users set name = '"+name+"', surname = '"+surname+"', phone_number ='"+phone_number+"', password = '"+password+"', email = '"+email+"', card_number = '"+card_number+"', id_address = '"+address.getId_address()+"', documents = '"+getDocumentsAsString()+"', deadlines = '"+getDeadlinesAsString()+"', possible_type = '"+possible_type+"' where id = '"+id+"'");
        }
        else {
            int id_address = address.getId_address();
            statement.executeUpdate("insert into users (name, surname, phone_number, card_number, password, email, id_address, documents, deadlines, possible_type) values('" + name + "', '" + surname + "', '" + phone_number + "', '" + card_number + "', '"+password+"', '"+email+"','" + id_address + "', '"+getDocumentsAsString()+"','"+getDeadlinesAsString()+"', '"+possible_type+"')");
            resultSet = statement.executeQuery("select * from users where card_number = '" + card_number + "'");
            if (resultSet.next()) {
                id = resultSet.getInt("id");
            }
        }
    }
    public void login() throws SQLException {
        System.out.println("Phone number: ");
        setPhone_number(in.nextLine());
        ResultSet resultSet = statement.executeQuery("select * from users where phone_number = '"+phone_number+"'");
        if(resultSet.next()){
            System.out.println("Password: ");
            password = in.nextLine();
            if(password.equals(resultSet.getString("password"))){
                setVariablesKnowingCard_number(resultSet.getLong("card_number"));
                System.out.println("Successfully");
            }
            else{
                System.out.println("Wrong password");
                login();
            }
        }
        else{
            System.out.println("There is no user with this phone number");
            login();
        }
    }
    public void remove() throws SQLException {
        while(documents.size()>0){
            returnDocument(documents.get(documents.size()-1));
        }
        address.remove();
        statement.executeUpdate("delete from users where id = '"+id+"'");
    }
    public void setVariablesKnowingNameSurname(String name, String surname) throws SQLException {
        setName(name);
        setSurname(surname);
        ResultSet resultSet = statement.executeQuery("select * from users where name = '"+name+"' and surname = '"+surname+"'");
        if(resultSet.next()){
            setPhone_number(resultSet.getString("phone_number"));
            id = resultSet.getInt("id");
            setCard_number(resultSet.getLong("card_number"));
            setPassword(resultSet.getString("password"));
            setDocumentsFromString(resultSet.getString("documents"));
            setDeadlinesFromString(resultSet.getString("deadlines"));
            setEmail(resultSet.getString("email"));
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
            setPassword(resultSet.getString("password"));
            setDocumentsFromString(resultSet.getString("documents"));
            setDeadlinesFromString(resultSet.getString("deadlines"));
            setEmail(resultSet.getString("email"));
            address.setVariablesKnowingId_address(resultSet.getInt("id_address"));
        }
    }
    public void setVariablesKnowingId(int id) throws SQLException {
        this.id = id;
        ResultSet resultSet = statement.executeQuery("select * from users where id = '"+id+"'");
        if(resultSet.next()){
            setName(resultSet.getString("name"));
            setSurname(resultSet.getString("surname"));
            setPhone_number(resultSet.getString("phone_number"));
            card_number = resultSet.getInt("card_number");
            setDocumentsFromString(resultSet.getString("documents"));
            setDeadlinesFromString(resultSet.getString("deadlines"));
            setEmail(resultSet.getString("email"));
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
        setCard_number(phone_number);
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
    public void setCard_number(long card_number){
        this.card_number = card_number;
    }
    public long getCard_number() {
        return card_number;
    }
    //Id
    public int getId() {
        return id;
    }
    //Password
    public void setPassword(String password){
        this.password = password;
    }
    public String getPassword(){
        return password;
    }
    public boolean checkPassword(String password){
        return this.password.equals(password);
    }
    //Email
    public void setEmail(String email){
        this.email = email;
    }
    public String getEmail(){
        return email;
    }
    //Documents
    public ArrayList<Document> getDocuments() {
        return documents;
    }
    public String getDocumentsAsString(){
        String s = "";
        for(int i = 0; i<documents.size(); i++){
            if(i<documents.size()-1) s = s.concat(String.valueOf(documents.get(i).getId()) + ", ");
            else s = s.concat(String.valueOf(documents.get(i).getId()));
        }
        return s;
    }
    public boolean hasDocument(Document document){
        return documents.contains(document);
    }
    public void addDocument(Document document) throws SQLException {
        documents.add(document);
        Date date1 = new Date();
        if(document instanceof AVMaterial){
            addDeadline(date1.getTime() + 14 * day);
        }
        else if(document instanceof Book && ((Book) document).getBest_sellerAsInt() == 1) {
            addDeadline(date1.getTime() + 14 * day);
        }
        else{
            long kek = date1.getTime() + maxdays * day;
            addDeadline(date1.getTime() + maxdays * day);
        }
    }
    public void removeDocument(Document document) throws SQLException {
        for(int i = 0; i <documents.size(); i++){
            if(documents.get(i).equals(document)){
                removeDeadline(deadlines.get(i));
                documents.remove(document);
                break;
            }
        }
    }
    public void setDocumentsFromString(String s) throws SQLException {
        if(s!=null) {
            for (int i = 0; i < s.length(); i++) {
                int j = i;
                String cur = "";
                while (j < s.length() && s.charAt(j) != ',') {
                    cur = cur.concat(String.valueOf(s.charAt(j)));
                    j++;
                }
                i = j + 1;
                Document document = new Document(connection, in);
                document.setVariablesKnowingId(Integer.valueOf(cur));
                addDocument(document);
            }
        }
    }
    //Type
    public void setPossible_type(String possible_type) {
        this.possible_type = possible_type;
    }
    public String getPossible_type() {
        return possible_type;
    }
    public void setType(String type){
        this.type = type;
    }

    public String getType() {
        return type;
    }
    //Maxdays
    public void setMaxdays(int maxdays){
        this.maxdays = maxdays;
    }
    public long getMaxdays(){
        return maxdays;
    }
    //Deadlines
    public void addDeadline(long deadline){
        deadlines.add(deadline);
    }
    public void removeDeadline(long deadline){
        deadlines.remove(deadline);
    }
    public String getDeadlinesAsString(){
        String s = "";
        for(int i = 0; i<deadlines.size(); i++){
            if(i<deadlines.size()-1) s = s.concat(deadlines.get(i).toString() + ", ");
            else s = s.concat(deadlines.get(i).toString());
        }
        return s;
    }
    public void setDeadlinesFromString(String s) throws SQLException {
        if(s!=null){
            for (int i = 0; i < s.length(); i++) {
                int j = i;
                String cur = "";
                while (j < s.length() && s.charAt(j) != ',') {
                    cur = cur.concat(String.valueOf(s.charAt(j)));
                    j++;
                }
                i = j + 1;
                addDeadline(Long.valueOf(cur));
            }
        }
    }
    public long getDeadlineOfDocument(Document document){
        long deadline = 0;
        for(int i = 0; i <documents.size(); i++){
            if(documents.get(i).equals(document)){
                deadline = deadlines.get(i);
            }
        }
        long left;
        Date date = new Date();
        left = deadline - date.getTime();
        return (long) Math.ceil((double)left / (double)day);
    }
    //Actions

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
    public void checkOutDocumentForCertainPeriod(Document document, long days) throws SQLException {
        long prevMaxdays = maxdays;
        maxdays = Math.min(maxdays, days);
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
        maxdays = prevMaxdays;
    }
    public void returnDocument(Document document) throws SQLException {
        if(hasDocument(document)) {
            removeDocument(document);
            document.increaseAmount();
            document.removeUser(this);
            document.save();
            save();
        }
    }
    public void modify() throws SQLException {
        System.out.println("What do you want to modify?");
        String field = in.nextLine();
        if(field.equals("Name")){
            String name = in.nextLine();
            this.setName(name);
        }
        else if (field.equals("Surname")){
            String surname = in.nextLine();
            this.setSurname(surname);
        }
        else if (field.equals("Phone number")){
            String phone_number = in.nextLine();
            this.setPhone_number(phone_number);
        }
        else if(field.equals("Email")){
            String email = in.nextLine();
            this.setEmail(email);
        }
        else if(field.equals("Type")){
            String type = in.nextLine();
            this.setType(type);
        }
        else if(field.equals("Delete document")){
            Document document = new Document(connection, in);
            String title = in.nextLine();
            document = library.getDocumentByTitle(title);
            this.returnDocument(document);
        }
        this.save();
    }
    public Document getDocumentByTitle(String title) throws SQLException {
        return library.getDocumentByTitle(title);
    }
    public String searchDocumentByPossibleTitle(String title) throws SQLException { // Ищет документ по частично
        return library.searchDocumentByPossibleTitle(title);
    }
    public String searchDocumentByAuthor(Author author) throws SQLException {
        return library.searchDocumentByAuthor(author);
    }
    public String searchDocumentByKeyword(Keyword keyword) throws SQLException {
        return library.searchDocumentByKeyword(keyword);
    }

}
