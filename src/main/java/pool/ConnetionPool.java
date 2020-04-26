package pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.IntStream;

public class ConnetionPool {

    private Set<Connection> connections;

    private static ConnetionPool instance = null;
    private final int MAX_CONNECTION = 15;

    private ConnetionPool() {
        connections = new HashSet<Connection>();
        IntStream.range(0, MAX_CONNECTION).forEach((i -> {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                connections.add(DriverManager.getConnection("jdbc:mysql://localhost:3306/coupons" + "?user=root" +
                        "&password=MyNewPassword"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));
    }

    public synchronized Connection getConnection() {
        while (connections.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Iterator<Connection> iterator = connections.iterator();
        Connection connection = iterator.next();
        iterator.remove();
        return connection;
    }

    public synchronized void returnConnection(Connection connection) {
        connections.add(connection);
        notifyAll();
    }

    public synchronized void closeConnections() {
        int counter = 0;

        while (counter < MAX_CONNECTION) {

            while (connections.isEmpty()) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            Iterator<Connection> iterator = connections.iterator();
            while (iterator.hasNext()) {
                try {
                    Connection connection = iterator.next();
                    iterator.remove();
                    connection.close();
                    counter++;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static synchronized ConnetionPool getInstance() {
        if (instance == null) {
            instance = new ConnetionPool();
        }
        return instance;
    }


}
