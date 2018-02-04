import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

public class JournalArticle extends Document{
    protected String journal_name, date;
    protected ArrayList<String> editors;

    JournalArticle(Connection connection, Scanner in) throws SQLException {
        super(connection, in);
        editors = new ArrayList<String>();
    }

    @Override
    public void read() throws SQLException {
        super.read();
        GUI.print("Journal name: ");
        setJournal_name(GUI.read());setJournal_name(GUI.read());
        GUI.print("Date: ");
        setDate(GUI.read());
        GUI.print("Editors: ");
        while(in.hasNext()){
            String editor = GUI.read();
            if(editor.equals("end")) break;
            addEditor(editor);
        }
    }

    @Override
    public void save() throws SQLException {
        super.save();
        statement.executeUpdate("update documents set type = 'ja' where id = 'id'");
        ResultSet resultSet = statement.executeQuery("select * from ja where id_document = '"+id+"'");
        if(resultSet.next()){
            statement.executeUpdate("update ja set title = '"+title+"', journal_name = '"+journal_name+"', date = '"+date+"', editors = '"+getEditorsAsString()+"' where id_document = '"+id+"'");
        }
        else{
            statement.executeUpdate("insert into ja (id_document, title, journal_name, date, editors) values('"+id+"', '"+title+"', '"+journal_name+"', '"+date+"', '"+getEditorsAsString()+"')");
        }
    }

    //Journal_name
    public void setJournal_name(String journal_name) {
        this.journal_name = journal_name;
    }
    public String getJournal_name() {
        return journal_name;
    }
    //Date
    public void setDate(String date) {
        this.date = date;
    }
    public String getDate() {
        return date;
    }
    //Editors
    public void setEditors(ArrayList<String> editors) {
        this.editors = editors;
    }
    public ArrayList<String> getEditors() {
        return editors;
    }
    public String getEditorsAsString(){
        String s = "";
        for(int i = 0; i<editors.size(); i++){
            if(i<editors.size()-1) s = s.concat(editors.get(i) + ", ");
            else s = s.concat(editors.get(i));
        }
        return s;
    }
    public void setEditorsFromString(String s){
        for(int i = 0; i<s.length(); i++){
            String cur = "";
            int j = i;
            while(j<s.length() && s.charAt(j) != ','){
                cur = cur.concat(String.valueOf(s.charAt(j)));
                j++;
            }
            i = j+1;
            addEditor(cur);
        }
    }
    public void addEditor(String editor){
        editors.add(editor);
    }
    public void removeEditor(String editor){
        editors.add(editor);
    }
}