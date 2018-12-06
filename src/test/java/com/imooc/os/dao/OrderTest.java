package com.imooc.os.dao;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class OrderTest {
    private String driver="com.mysql.jdbc.Driver";
    private String url="jdbc:mysql:///os?useUnicode=true&characterEncoding=utf8";
    private String username="root";
    private String password="123456";

    @Test
    public void addOrder() {
        Connection connection = null;
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(url,username,password);
            //取消自动提交
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            statement.execute("insert into orders values ('100002','100001',2,2499,now(),null,null," +
                    "'何洁','19995546091','上海','待发货')");
            statement.execute("update products set stck=stock-2 where id='100001'");
            statement.close();
            //事务提交
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
