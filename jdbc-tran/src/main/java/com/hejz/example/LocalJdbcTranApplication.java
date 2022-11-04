package com.hejz.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2022-10-13 21:30
 * @Description: jdbc事务示例
 */
public class LocalJdbcTranApplication {
    private static final Logger LOG=LoggerFactory.getLogger(LocalJdbcTranApplication.class);
    public static void main(String[] args) throws SQLException {
        Connection connection= getConnection();
        //关闭自动提交事务
        connection.setAutoCommit(false);
        String sq1="UPDATE tb_user SET amount = amount + 100 WHERE username =?";
        PreparedStatement ps1=connection.prepareStatement(sq1);
        String sq2="UPDATE tb_user SET amount = amount - 100 WHERE username =?";
        PreparedStatement ps2=connection.prepareStatement(sq2);
        ps1.setString(1,"SuperMan");
        ps1.execute();
        //加入错误——模拟程序运行出错
//        error();
        ps2.setString(1,"BatMan");
        ps2.execute();
        //提交事务
        connection.commit();
        ps1.close();
        ps2.close();
        connection.close();
    }

    private static void error() throws SQLException {
        throw new SQLException("error");
    }

    private static Connection getConnection() throws SQLException {
        String driver="com.mysql.jdbc.Driver";
        String url="jdbc:mysql://localhost:3306/test?useSSL=false";
        String user="root";
        String password="123456";
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            LOG.error(e.getLocalizedMessage());
        }
        return DriverManager.getConnection(url,user,password);
    }
}
