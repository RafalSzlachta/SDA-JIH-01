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
            PreparedStatement ps = con.prepareStatement("SELECT * from actor where actor_id=?"); // ? to tzw placeholder
            ps.setInt(1, id); // tutaj ustawiamy place holder
            ResultSet rs = ps.executeQuery(); // polecenie ktore cos zwraca
            while (rs.next()) {
                System.out.print(rs.getInt("actor_id") + " ");
                System.out.print(rs.getString(2) + " ");
                System.out.print(rs.getString(3) + " ");
                System.out.println(rs.getString(4) + " ");
            }

        } catch (SQLException e) {
                e.printStackTrace();
        }
    }

    public static void deleteTableMovies(DataSource ds)
    {
        try (Connection con = ds.getConnection()) {
            Statement stmt = con.createStatement();
            stmt.execute("Drop table if exists movies"); // za pomoca execute mozemy wykonywac zapytania ktore nic nie zwracaja

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createTableMovies(DataSource ds)
    {
        try (Connection con = ds.getConnection()) {
            Statement stmt = con.createStatement();
            stmt.execute("create table if not exists movies(" +
                    "id int primary key auto_increment," +
                    "title varchar(45)" +
                    ")"
            );

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertIntoMovies(DataSource ds)
    {
        try (Connection con = ds.getConnection()) {
            Statement stmt = con.createStatement();
            String insert1 = "Insert into movies(title) values ('Film1')";
            String insert2 = "Insert into movies(title) values ('Film2')";
            String insert3 = "Insert into movies(title) values ('Film3')";
            stmt.execute(insert1);
            stmt.execute(insert2);
            stmt.execute(insert3);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteFromMoviesFilmWithId(DataSource ds, int id)
    {
        try (Connection con = ds.getConnection()) {
            PreparedStatement stmt = con.prepareStatement("Delete from movies where id=?");
            stmt.setInt(1, id);
            stmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void switchRecord1And3(DataSource ds)
    {
        try (Connection con = ds.getConnection()) {
            CallableStatement stmt = con.prepareCall("{call SwitchFilm1And3()}");
            stmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void switchRecord3And1Parameter(DataSource ds, int id1, int id2)
    {
        try (Connection con = ds.getConnection()) {
            CallableStatement stmt = con.prepareCall("{call SwitchFilm3And1Parameter(?,?)}");
            stmt.setInt(1, id1);
            stmt.setInt(2, id2);
            stmt.execute();

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

            // returnAllActors(ds);
            // getActorsWithId(ds, 3);
//        deleteTableMovies(ds);
//        createTableMovies(ds);
//        insertIntoMovies(ds);
//        deleteFromMoviesFilmWithId(ds, 2);

        switchRecord3And1Parameter(ds, 1, 3);

        }
}