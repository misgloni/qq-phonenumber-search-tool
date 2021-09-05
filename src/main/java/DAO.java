import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DAO {

    //sqlite db file path
    public static final String PATH = "se.db";

    //properties encoding
    public static final String PROPERTIES_ENCODING = "GBK";


    private static String path = null;


    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:" + path);
    }


    public static void initialize() {

        //init with properties file
        Properties properties = new Properties();
        try {
            InputStream inputStream = DAO.class.getClassLoader().getResourceAsStream("config.properties");
            properties.load(new InputStreamReader(inputStream, PROPERTIES_ENCODING));
            path = properties.getProperty("db_path");
            System.out.println(path);
        } catch (IOException e) {
            path = PATH;
        }

    }

    public static List<String> qqToPhone(String qq) throws Exception {
        ArrayList<String> stringArrayList = new ArrayList<>();
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(String.format("select * from qq_phone where qq='%s'", qq));
        while (resultSet.next()) {
//            System.out.println(resultSet.getString("qq") + "xx" + resultSet.getString("phone"));
            stringArrayList.add(resultSet.getString("phone"));
        }
        statement.close();
        connection.close();
        return stringArrayList;
    }

    public static List<String> phoneToQQ(String phone) throws Exception {
        ArrayList<String> stringArrayList = new ArrayList<>();
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(String.format("select * from qq_phone where phone='%s'", phone));
        while (resultSet.next()) {
//            System.out.println(resultSet.getString("qq") + "xx" + resultSet.getString("phone"));
            stringArrayList.add(resultSet.getString("qq"));
        }
        statement.close();
        connection.close();
        return stringArrayList;
    }
}
