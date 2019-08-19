package com.xemplarsoft.dw.medium;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.*;

public class DBHandler{
    private Connection c = null;
    private Statement query;
    public DBHandler(){
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:dat.db");
        } catch ( Exception e ) {
            e.printStackTrace();
            System.exit(0);
        }
        System.out.println("Opened database successfully");
    }

    public ResultSet executeQuery(String s){
        try {
            query = c.createStatement();
            ResultSet ret = query.executeQuery(s);
            query.close();

            return ret;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public int executeUpdate(String s) throws SQLException{
        query = c.createStatement();
        int ret = query.executeUpdate(s);
        query.close();

        return ret;
    }

    public void putValue(String key, String value){
        ResultSet res = executeQuery("SELECT `IDENT` FROM VARS WHERE IDENT='" + key + "'");
        String test = "";
        try {
            if (res.next()) {
                test = res.getString("IDENT");
            }
            res.close();

            if(key.equals(test)){
                executeUpdate("UPDATE VARS set VALUE='" + value + "' WHERE IDENT='" + key + "'");
            } else {
                executeUpdate("INSERT INTO VARS (IDENT,VALUE) VALUES ('" + key + "','" + value + "')");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getValue(String key){
        ResultSet res = executeQuery("SELECT `VALUE` FROM VARS WHERE IDENT='" + key + "'");
        String test = "";
        try {
            if (res.next()) {
                test = res.getString("VALUE");
            }
            res.close();

            return test;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public void initalize(){
        try {
            query = c.createStatement();
            String sql = "CREATE TABLE USERS " +
                    "(UID BIGINT PRIMARY KEY             NOT NULL," +
                    " ADDRESSES          TEXT            NOT NULL, " +
                    " EMAIL              VARCHAR(64), " +
                    " ENCKEY             VARCHAR(64))";
            query.executeUpdate(sql);
            query.close();

            query = c.createStatement();
            sql = "CREATE TABLE VARS " +
                    "(ID INT PRIMARY KEY             NOT NULL," +
                    " IDENT          VARCHAR(64)     NOT NULL, " +
                    " VALUE          TEXT";
            query.executeUpdate(sql);
            query.close();

            query = c.createStatement();
            sql = "CREATE TABLE ADDRESSES " +
                    "(ID INT PRIMARY KEY           NOT NULL," +
                    " UID            BIGINT        NOT NULL," +
                    " ADDRESS        VARCHAR(64)   NOT NULL," +
                    " BALANCE        VARCHAR(128)  NOT NULL," +
                    " TXS            ";
            query.executeUpdate(sql);
            query.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
