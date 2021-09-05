import java.io.*;
import java.sql.*;
import java.util.Scanner;

public class Test {

    public static boolean OK = false;
    public static long count = 0;

    //??正在赶来，请速撤离~
    public static void getMy() throws IOException {
        newThread();
        File file = new File("F:\\下载\\微博五亿2019.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        String s = "";
        while (s != null) {
            count++;
            s = reader.readLine();
            if (count == 1) {
                continue;
            }

            String[] sArr = s.split("\\s", 2);
            if (sArr != null&&sArr.length==2) {
                if(sArr[1].equals("6104190990")){
                    System.out.println(s);
                }
            }

        }
    }

    public static void weibo_check() throws IOException {
        newThread();
        File file = new File("F:\\下载\\微博五亿2019.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        String s = "";
        while (s != null) {
            count++;
            s = reader.readLine();
            if (count == 1) {
                continue;
            }

            String[] sArr = s.split("\\s", 2);
            if (sArr != null&&sArr.length==2) {
                try{
                    Long.parseLong(sArr[0]);
                    Long.parseLong(sArr[1]);
                }catch (NumberFormatException e){
                    System.out.println(s);
                    continue;
                }
                if(sArr[0].length()!=11||sArr[1].length()>10){
                    System.out.println(s);
                }
            }else{
                System.out.println("nohas:"+s);
            }

        }
    }

    public static void newThread(){
        new Thread() {
            @Override
            public void run() {
                Scanner scanner = new Scanner(System.in);
                for (; ; ) {
                    String str = scanner.nextLine();
                    if (str.equals("check")) {
                        System.out.println("count is " + Test.count);
                    }
                }
            }
        }.start();
    }

    public static void check() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:se.db");
        Statement stat = conn.createStatement();
        ResultSet resultSet = stat.executeQuery("select * from qq_phone limit 10 offset 0;");
        while (resultSet.next()) {
            System.out.println(resultSet.getString(3));
        }
    }

    public static void select() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:se.db");
        Statement stat = conn.createStatement();
        ResultSet resultSet = stat.executeQuery("select * from qq_phone where qq='3567334871'");
        while (resultSet.next()) {
            System.out.println(resultSet.getString("qq") + "xx" + resultSet.getString("phone"));
        }
    }

    public static void addsuoyin() throws SQLException {
        new Thread(() -> {
            for (; ; ) {
                if (!OK) {
                    try {
                        Thread.sleep(60000);
                        System.out.println(++count);
                        ;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    return;
                }
            }
        }).start();
        Connection conn = DriverManager.getConnection("jdbc:sqlite:se.db");
        Statement stat = conn.createStatement();
        stat.executeUpdate("CREATE INDEX index_qq ON qq_phone(qq);");
        OK = true;
        System.out.println("OK");
        stat.close();
        conn.close();
    }

    public static void to() throws ClassNotFoundException, SQLException, IOException {
        new Thread() {
            @Override
            public void run() {
                Scanner scanner = new Scanner(System.in);
                for (; ; ) {
                    String str = scanner.nextLine();
                    if (str.equals("check")) {
                        System.out.println("count is " + Test.count);
                    }
                }
            }
        }.start();

        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection("jdbc:sqlite:se.db");
        Statement stat = conn.createStatement();
        stat.executeUpdate("create table qq_phone(id integer primary key AUTOINCREMENT, qq varchar(10), phone char(11));");
        stat.close();

        Statement statx = conn.createStatement();
        statx.executeUpdate("CREATE INDEX index_qq ON qq_phone(qq);");
        statx.close();
        statx = conn.createStatement();
        statx.executeUpdate("CREATE INDEX index_phone ON qq_phone(phone);");
        statx.close();

        Statement stat2 = conn.createStatement();
        stat2.execute("BEGIN;");
        stat2.close();

        add(conn);

        conn.close();
    }

    public static void add(Connection conn) throws IOException, SQLException {
        File file = new File("F:\\下载\\8e.txt");
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (true) {
            count++;
            String str[];
            String read;

            //排除错误
            if (count == 148659434 || count == 232182655 || count == 707395943 || count == 707395944) {
                continue;
            }

            if (count % 400000 == 0) {
                Statement stat3 = conn.createStatement();
                stat3.execute("COMMIT;");
                stat3.close();
                Statement stat4 = conn.createStatement();
                stat4.execute("BEGIN;");
                stat4.close();

            }
            //初始化
            read = reader.readLine();
            if (read == null) {
                return;
            }
            str = read.split("----");

            if (str.length == 2) {
                toNew(str[0], str[1], conn);
            } else if (str.length == 3) {
                if (str[0].equals(str[1])) {
                    toNew(str[0], str[2], conn);
                } else {
                    toNew(str[0], str[2], conn);
                    toNew(str[1], str[2], conn);
                }
            } else {
                System.out.println("错误:" + read + " count:" + count);
            }

        }
    }

    public static void toNew(String qq, String phone, Connection conn) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("insert into qq_phone values(NULL,?,?)");
        statement.setString(1, qq);
        statement.setString(2, phone);
        statement.executeUpdate();
        statement.close();
    }
}
