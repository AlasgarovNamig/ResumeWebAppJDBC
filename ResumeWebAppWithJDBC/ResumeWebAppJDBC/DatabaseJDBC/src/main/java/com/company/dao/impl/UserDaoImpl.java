/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.dao.impl;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.company.entity.Country;
import com.company.entity.Skill;
import com.company.entity.User;
import com.company.entity.UserSkill;
import com.company.dao.inter.AbstractDao;
import com.company.dao.inter.UserDaoInter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Hp
 */
public class UserDaoImpl extends AbstractDao implements UserDaoInter {

    public User getUser(ResultSet rs) throws Exception {
        int id = rs.getInt("id");// database de colum adlari ile eyni olmalidir
        String name = rs.getString("name");
        String surname = rs.getString("surname");
        String email = rs.getString("email");
        String phoneNumber = rs.getString("phone");
        String profileDescription = rs.getString("profile_desc");
        String adress = rs.getString("adress");
        int nationalityId = rs.getInt("nationality_id");
        int birthPlaceId = rs.getInt("birthplace_id");

        String nationalityStr = rs.getString("nationality");
        String birthplaceStr = rs.getString("brithplace");
        Date birthdate = rs.getDate("birthday");

        Country nationality = new Country(nationalityId, null, nationalityStr);
        Country birthplace = new Country(birthPlaceId, birthplaceStr, null);

        return new User(id, name, surname, email, phoneNumber, profileDescription, adress, birthdate, nationality, birthplace);
    }

    public User getUserSimple(ResultSet rs) throws Exception {
        int id = rs.getInt("id");// database de colum adlari ile eyni olmalidir
        String name = rs.getString("name");
        String surname = rs.getString("surname");
        String email = rs.getString("email");
        String phoneNumber = rs.getString("phone");
        String profileDescription = rs.getString("profile_desc");
        String adress = rs.getString("adress");
        int nationalityId = rs.getInt("nationality_id");
        int birthPlaceId = rs.getInt("birthplace_id");

        Date birthdate = rs.getDate("birthday");

        return new User(id, name, surname, email, phoneNumber, profileDescription, adress, birthdate, null, null);
    }

    @Override
    public List<User> getAll() {
        List<User> result = new ArrayList<>();
        try {
            Connection c = connect();
            Statement stmt = c.createStatement();
            stmt.execute(" select "
                    + " u.*, "
                    + " n.nationality , "
                    + " c.name as brithplace "
                    + " from user u "
                    + " left join country n on u.nationality_id = n.id "
                    + " left join country c on u.birthplace_id = c.id ");
            ResultSet rs = stmt.getResultSet();

            while (rs.next()) {
                User u = getUser(rs);

                result.add(u);

            }
            c.close();
        } catch (Exception ex) {
            ex.printStackTrace();;
        }
        return result;
    }

    @Override
    public List<User> SerachUser(String name, String surname, Integer nationalityId) {
        List<User> result = new ArrayList<>();
        try {
            Connection c = connect();

            String sql = " select "
                    + " u.*, "
                    + " n.nationality , "
                    + " c.name as brithplace "
                    + " from user u "
                    + " left join country n on u.nationality_id = n.id "
                    + " left join country c on u.birthplace_id = c.id  where 1=1 ";

            if (name != null && !name.trim().isEmpty()) {
                sql += "and u.name = ?";
            }

            if (surname != null && !surname.trim().isEmpty()) {
                sql += "and u.surname = ?";
            }

            if (nationalityId != null) {
                sql += "and u.nationality_id = ?";
            }

            PreparedStatement stmt = c.prepareStatement(sql);

            int i = 1;
            if (name != null && !name.trim().isEmpty()) {
                stmt.setString(i, name);
                i++;
            }

            if (surname != null && !surname.trim().isEmpty()) {
                stmt.setString(i, surname);
                i++;
            }

            if (nationalityId != null) {
                stmt.setInt(i, nationalityId);

            }
            stmt.execute();

            ResultSet rs = stmt.getResultSet();

            while (rs.next()) {
                User u = getUser(rs);

                result.add(u);

            }
            c.close();
        } catch (Exception ex) {
            ex.printStackTrace();;
        }
        return result;
    }

    @Override
    public boolean updateUser(User u) {
        try (Connection c = connect();) {
            PreparedStatement stmt = c.prepareStatement("update user set "
                    + "name = ?,surname = ?,"
                    + "email = ?,phone = ?,"
                    + "birthday = ?,profile_desc = ?,"
                    + "adress = ?,birthplace_id = ?,nationality_id =? "
                    + "where id =? ");
            stmt.setString(1, u.getName());
            stmt.setString(2, u.getSurname());
            stmt.setString(3, u.getEmail());
            stmt.setString(4, u.getPhone());
            stmt.setDate(5, u.getBirthDate());
            stmt.setString(6, u.getProfileDescription());
            stmt.setString(7, u.getAdress());
            stmt.setInt(8, u.getBirthPlace().getId());
            stmt.setInt(9, u.getNationality().getId());
            stmt.setInt(10, u.getId());

            return stmt.execute();

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean removeUser(int id) {
        try (Connection c = connect();) {

            Statement stmt = c.createStatement();
            return stmt.execute("delete from user  where id =" + id);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public User getById(int userId) {
        User result = null;

        try {
            Connection c = connect();
            Statement stmt = c.createStatement();
            stmt.execute(" select "
                    + "  u.*, "
                    + "  n.nationality , "
                    + "   c.name as brithplace "
                    + "   from user u "
                    + "     left join country n on u.nationality_id = n.id "
                    + "  left join country c on u.birthplace_id = c.id where u.id =" + userId);
            ResultSet rs = stmt.getResultSet();

            while (rs.next()) {
                result = getUser(rs);

            }
            c.close();
        } catch (Exception ex) {
            ex.printStackTrace();;
        }
        return result;
    }
private static BCrypt.Hasher crypt = BCrypt.withDefaults();
        
    @Override
    public boolean addUser(User u) {
        try (Connection c = connect();) {
            PreparedStatement stmt = c.prepareStatement("insert into  user(name,surname,email,phone_number,password,adress,brithday,brithplace_id,nationality_id,) values(?,?,?,?,?,?,?,?,?) ");
            stmt.setString(1, u.getName());
            stmt.setString(2, u.getSurname());
            stmt.setString(3, u.getEmail());
            stmt.setString(4, u.getPhone());
            stmt.setString(5, crypt.hashToString(4, u.getPassword().toCharArray()));
            stmt.setString(6, u.getAdress());
            stmt.setDate(7, u.getBirthDate());
            stmt.setInt(8, u.getBirthPlace().getId());
            stmt.setInt(9, u.getNationality().getId());
            
            return stmt.execute();

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public User findbyEmailAndPasswor(String email, String password) {
        User result = null;
        try (Connection c = connect()) {
            PreparedStatement stmt = c.prepareStatement("select * from user where email =? and password=?");
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result = getUserSimple(rs);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
