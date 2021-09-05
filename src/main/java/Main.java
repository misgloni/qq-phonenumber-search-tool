import java.io.*;
import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Class.forName("org.sqlite.JDBC");
        new StartFrame();
    }
}

