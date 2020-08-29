package DB_H2_Main;

import org.h2.jdbcx.JdbcDataSource;

import javax.sql.DataSource;
import java.sql.*;

public class H2Main {
    private static final String USER = "sa";
    public static final String PASSWORD = "";
    public static final String DB_URL = "jdbc:h2:~/test2";

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

    public static void main(String[] args) {

        JdbcDataSource ds = new JdbcDataSource();
        ds.setUrl(DB_URL);
        ds.setUser(USER);
        ds.setPassword(PASSWORD);

        deleteTableMovies(ds);
        createTableMovies(ds);
        insertIntoMovies(ds);
        deleteFromMoviesFilmWithId(ds, 2);
    }
}
