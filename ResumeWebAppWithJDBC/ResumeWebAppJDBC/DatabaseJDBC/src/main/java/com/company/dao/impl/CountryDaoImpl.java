/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.dao.impl;

import com.company.dao.inter.AbstractDao;
import com.company.dao.inter.CountryDaoInter;
import com.company.entity.Country;
import com.company.entity.Skill;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Hp
 */
public class CountryDaoImpl extends AbstractDao implements CountryDaoInter{
    
     public Country getCountry(ResultSet rs) throws Exception {
         int Id = rs.getInt("id");
        String name = rs.getString("name");
      String nationality = rs.getString("nationality");
       return new Country(Id, name, nationality);
       
    }

    @Override
    public List<Country> getAllCountry() {
         List<Country> result = new ArrayList<>();
        try {
            Connection c = connect();
            Statement stmt = c.createStatement();
            stmt.execute(" select * from country ");
            ResultSet rs = stmt.getResultSet();

            while (rs.next()) {
                Country con = getCountry(rs);

                result.add(con);

            }
            c.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;   
    }

    @Override
    public Country getCountryById(int userId) {
           Country result = null;
        try {
            Connection c = connect();
            Statement stmt = c.createStatement();
            stmt.execute("select * from country where id = "+userId);
            ResultSet rs = stmt.getResultSet();

            while (rs.next()) {
                result = getCountry(rs);

              

            }
            c.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean updateCountry(Country u) {
       try (Connection c = connect();) {
            PreparedStatement stmt = c.prepareStatement("update user set name = ?,nationality = ? where id =? ");
            stmt.setString(1, u.getName());
            stmt.setString(2, u.getNationality());
            stmt.setInt(3, u.getId());
          
            
            return stmt.execute();

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean addCountry(Country u) {
    try (Connection c = connect();) {
            PreparedStatement stmt = c.prepareStatement("insert into  country(name,nationality) values(?,?) ");
            stmt.setString(1, u.getName());
            stmt.setString(2, u.getNationality());
           

            return stmt.execute();

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean removeCountry(int id) {
try (Connection c = connect();) {

            Statement stmt = c.createStatement();
            return stmt.execute("delete from country  where id ="+id);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
   
    
}
