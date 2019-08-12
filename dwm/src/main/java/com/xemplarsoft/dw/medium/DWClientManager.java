package com.xemplarsoft.dw.medium;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;

public class DWClientManager {
    private ObjectMapper mapper;
    private Connection c = null;
    private Statement query;
    public DWClientManager(){
        mapper = new ObjectMapper();
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:dat.db");
        } catch ( Exception e ) {
            e.printStackTrace();
            System.exit(0);
        }
        System.out.println("Opened database successfully");
    }

    public ArrayList<String> getAddresses(long UID){
        try {
            query = c.createStatement();
            ResultSet res = query.executeQuery("SELECT `ADDRESSES` FROM USERS WHERE UID=" + UID);
            if(res.next()){
                String dat = res.getString(1);
                ArrayList<String> addresses = mapper.readValue(dat, new TypeReference<ArrayList<String>>(){});
                return addresses;
            }
            res.close();
            query.close();

        } catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public boolean clientExists(long UID){
        try {
            query = c.createStatement();
            ResultSet res = query.executeQuery("SELECT `UID` FROM USERS WHERE UID=" + UID);
            long test = 0L;
            if(res.next()){
                test = res.getLong("UID");
            }
            res.close();
            query.close();

            return UID == test;
        } catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }

    public SecretKey getSyncKey(long UID){
        try {
            query = c.createStatement();
            ResultSet res = query.executeQuery("SELECT `ENCKEY` FROM USERS WHERE UID=" + UID);
            String key = "";
            if(res.next()){
                key = res.getString("ENCKEY");
            }
            res.close();
            query.close();

            byte[] dat = Base64.getDecoder().decode(key);
            return new SecretKeySpec(dat, 0, dat.length, "AES");
        } catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public void saveUser(long UID, SecretKey sync, ArrayList<String> addresses){
        try {
            String key = Base64.getEncoder().encodeToString(sync.getEncoded());
            String addrs = mapper.writeValueAsString(addresses);

            query = c.createStatement();
            String sql = "INSERT INTO USERS (UID,ADDRESSES,EMAIL,ENCKEY) " +
                    "VALUES (" + UID + ", '" + addrs + "', '', '" + key + "');";
            query.executeUpdate(sql);
            query.close();
        } catch (SQLException e){
            if(e.getMessage().contains("USERS")) {
                initalize();
                saveUser(UID, sync, addresses);
            }
        } catch (Exception e){
            e.printStackTrace();
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
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
