//package com.kitaphana.Testing;
//
//import com.kitaphana.Database.Database;
//import com.kitaphana.Service.AdminService;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//public class launch {
//    public static void main(String[] args) throws SQLException {
//        clean();
//        TestDelivery4 test = new TestDelivery4();
//        test.test_case10();
//    }
//    public static void clean() throws SQLException {
//        AdminService admin1 = new AdminService();
//        Database db = Database.getInstance();
//        try {
//            db.connect();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        ResultSet rs;
//        db.runSqlUpdate("TRUNCATE TABLE users");
//        db.runSqlUpdate("TRUNCATE TABLE addresses");
//        db.runSqlUpdate("TRUNCATE TABLE books");
//        db.runSqlUpdate("TRUNCATE TABLE documents");
//        for(int i = 0; i<3; i++){
//            rs = db.runSqlQuery("SELECT * from librarians where privilege > '"+0+"'");
//            if(rs.next()!=false) {
//                admin1.deleteLibrarian(rs.getInt("id") + "");
//            }
//        }
//    }
//}
