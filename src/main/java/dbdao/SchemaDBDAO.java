package dbdao;


import dao.SchemaDAO;
import pool.ConnetionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class SchemaDBDAO implements SchemaDAO {

    private ConnetionPool connetionPool;

    public SchemaDBDAO() {
        connetionPool = ConnetionPool.getInstance();
    }


    @Override
    public void rebuild() {
        ConnetionPool pool = connetionPool.getInstance();
        Connection connection = pool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement("drop table coupons.COMPANIES;\n" +
                "DROP TABLE IF EXISTS coupons.CUSTOMERS"
        )) {
            preparedStatement.execute();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
