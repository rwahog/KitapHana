import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AVMaterial extends Document {
    AVMaterial(Connection connection, Scanner in) throws SQLException {
        super(connection, in);
    }

    @Override
    public void save() throws SQLException {
        super.save();
        statement.executeUpdate("update documents set type = 'av' where id = '"+id+"'");
        ResultSet resultSet = statement.executeQuery("select * from av where id_document = '"+id+"'");
        if(resultSet.next()){
            statement.executeUpdate("update av set title = '"+title+"' where id_document = '"+id+"'");
        }
        else{
            statement.executeUpdate("insert into av (id_document, title) values('"+id+"', '"+title+"')");
        }
    }
}
