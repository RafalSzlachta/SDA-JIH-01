import com.mysql.cj.jdbc.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.*;

public class Main {

    // aby połączyć sie z baza danych nalezy utworzyć połączenie
    // potrzebny do tego jest connector ktory sie dolącza do POMa jako dependency
    // do każdej bazy danych np MySQL, Oracle itp jest inny connector
    //do java 4 trzeba było odwoływać sie do tego drivera
    // Class.forName("com.mysql.cj.jdbc.Driver"); // zmiana od niedawna: trzeba dodac .cj do sciezki

    private static final String DB_URL = "jdbc:mysql://localhost:3306/sakilaplayground?serverTimezone=Europe/Warsaw";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    public static void returnAllActors(MysqlDataSource ds) {
        try (Connection con = ds.getConnection()) {
            Statement stm = con.createStatement(); // tutaj narazie mówimy że będziemy mieć zapytanie
            ResultSet rs = stm.executeQuery("SELECT * FROM actor LIMIT 3"); // do tego obiektu trafią odpowiedzi na zapytanie
            while (rs.next()) {
                System.out.println(rs.getInt("actor_id"));
                System.out.println(rs.getString(2));
                System.out.println(rs.getString(3));
                System.out.println(rs.getString(4));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void getActorsWithId (MysqlDataSource ds,int id){
        try (Connection con = ds.getConnection()) {

        } catch (SQLException e) {
                e.printStackTrace();
        }
    }


        public static void main (String[]args) throws ClassNotFoundException {

//        try {
//            MysqlDataSource ds = new MysqlDataSource(); // tworzenie połączenia drugim sposobem przez DataSource
//            ds.setUrl(DB_URL);
//            ds.setUser(USER);
//            ds.setPassword(PASSWORD);
//
//            // Connection con = DriverManager.getConnection(DB_URL, USER, PASSWORD); // tworzenie połączenia przez DriverManager
//            Connection con = ds.getConnection(); // połączenie przez data source nie wymaga podawania parametrów do getConnection
//            Statement stm = con.createStatement(); // tutaj narazie mówimy że będziemy mieć zapytanie
//            ResultSet rs = stm.executeQuery("SELECT * FROM actor LIMIT 3"); // do tego obiektu trafią odpowiedzi na zapytanie
//            while (rs.next()){
//                System.out.println(rs.getInt("actor_id"));
//                System.out.println(rs.getString(2));
//                System.out.println(rs.getString(3));
//                System.out.println(rs.getString(4));
//
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }


            // tworzenie zapytania za pomocą metody

            MysqlDataSource ds = new MysqlDataSource(); // tworzenie połączenia drugim sposobem przez DataSource
            ds.setUrl(DB_URL);
            ds.setUser(USER);
            ds.setPassword(PASSWORD);

            returnAllActors(ds);
        }
}