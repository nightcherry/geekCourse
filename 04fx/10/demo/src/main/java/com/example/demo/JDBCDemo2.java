package com.example.demo;


import java.sql.*;

public class JDBCDemo2 {
    private static String sqlStatement;

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/demo_test?useSSL=false&useUnicode=true&characterEncoding=utf8";
        Connection con = DriverManager.getConnection(url, "root", "123");
        //为查询的结果集准备接收对象
        ResultSet rs = null;
        //查询
        sqlStatement = "SELECT * FROM DEPT";
        PreparedStatement sta = con.prepareStatement(sqlStatement);
        qry(sta, rs);
        //增加
        sqlStatement = "INSERT INTO DEPT VALUES(?,?,?)";
        sta = con.prepareStatement(sqlStatement);
        sta.setString(1,"50");
        sta.setString(2,"TEST");
        sta.setString(3,"CHINA");
        System.out.println("插入执行结果:"+update(sta));
        //更新
        sqlStatement = "UPDATE DEPT SET loc = ? WHERE DEPTNO = ?";
        sta = con.prepareStatement(sqlStatement);
        sta.setString(1,"SHAOXING");
        sta.setString(2,"50");
        System.out.println("更新执行结果:"+update(sta));
        //删除
        sqlStatement = "DELETE FROM DEPT WHERE DEPTNO = ?";
        sta = con.prepareStatement(sqlStatement);
        sta.setString(1,"50");
        System.out.println("删除执行结果:"+update(sta));
        closeResource(con, sta, rs);
        //批量增加
        sqlStatement = "INSERT INTO USER VALUES(?,?)";
        con.setAutoCommit(false);
        sta = con.prepareStatement(sqlStatement);
        //记录1
        sta.setInt(1, 1);
        sta.setString(2, "Cujo");
        sta.addBatch();
        //记录2
        sta.setInt(1, 2);
        sta.setString(2, "Fred");
        sta.addBatch();
        //记录3
        sta.setInt(1, 3);
        sta.setString(2, "Mark");
        sta.addBatch();
        int [] counts = sta.executeBatch();
        con.commit();
        System.out.println("插入执行结果:"+counts);

    }


    /**
     * 查询
     * @param sta
     * @param rs
     * @throws SQLException
     */
    private static void qry(PreparedStatement sta, ResultSet rs) throws SQLException {
        rs = sta.executeQuery();
        while(rs.next()) {
            System.out.println(rs.getObject("deptno"));
        }
    }
    /**
     * 增删改
     * @param sta
     * @return
     * @throws SQLException
     */
    private static int update(PreparedStatement sta) throws SQLException {
        return sta.executeUpdate();
    }


    public static void closeResource(Connection con,PreparedStatement sta,ResultSet rs) {
        try {
            if(con!=null) {
                con.close();
            }
            if(sta!=null) {
                sta.close();
            }
            if(rs!=null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

}