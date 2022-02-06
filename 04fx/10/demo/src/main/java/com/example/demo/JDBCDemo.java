package com.example.demo;


import java.sql.*;

public class JDBCDemo {
    private static String sqlStatement;

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/demo_test?useSSL=false&useUnicode=true&characterEncoding=utf8";
        Connection con = DriverManager.getConnection(url, "root", "123");
        Statement sta = con.createStatement();
        //为查询的结果集准备接收对象
        ResultSet rs = null;
        //查询
        sqlStatement = "SELECT * FROM DEPT";
        qry(sta,sqlStatement,rs);
        //增加
        sqlStatement = "INSERT INTO DEPT VALUES('50','TEST','CHINA')";
        System.out.println("插入执行结果:"+update(sta,sqlStatement));
        //更新
        sqlStatement = "UPDATE DEPT SET loc='SHAOXING' WHERE DEPTNO = '50'";
        System.out.println("更新执行结果:"+update(sta,sqlStatement));
        //删除
        sqlStatement = "DELETE FROM DEPT WHERE DEPTNO = '50'";
        System.out.println("删除执行结果:"+update(sta,sqlStatement));
        closeResource(con, sta, rs);
    }


    /**
     * 查询
     * @param sta
     * @param sql
     * @param rs
     * @throws SQLException
     */
    private static void qry(Statement sta,String sql,ResultSet rs) throws SQLException {
        rs = sta.executeQuery(sql);
        while(rs.next()) {
            System.out.println(rs.getObject("deptno"));
        }
    }
    /**
     * 增删改
     * @param sta
     * @param sql
     * @return
     * @throws SQLException
     */
    private static int update(Statement sta,String sql) throws SQLException {
        return sta.executeUpdate(sql);
    }


    public static void closeResource(Connection con,Statement sta,ResultSet rs) {
        try {
            if (con != null) {
                con.close();
            }
            if (sta != null) {
                sta.close();
            }
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}