/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.dao.impl;

import com.company.dao.inter.AbstractDao;
import com.company.dao.inter.SkillDaoInter;
import com.company.entity.Skill;
import com.company.entity.UserSkill;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Hp
 */
public class SkillDaoImpl extends AbstractDao implements SkillDaoInter {
    
    public Skill getSkill(ResultSet rs) throws Exception {
        int Id = rs.getInt("id");
        String name = rs.getString("name");
        
        return new Skill(Id, name);
    }
    
    @Override
    public Skill getSkillById(int userId) {
        Skill result = null;
        try {
            Connection c = connect();
            Statement stmt = c.createStatement();
            stmt.execute("select * from skill where id = " + userId);
            ResultSet rs = stmt.getResultSet();
            
            while (rs.next()) {
                result = getSkill(rs);
                
            }
            c.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }
    
    @Override
    public List<Skill> getAllSkill() {
        List<Skill> result = new ArrayList<>();
        try {
            Connection c = connect();
            Statement stmt = c.createStatement();
            stmt.execute(" select * from skill ");
            ResultSet rs = stmt.getResultSet();
            
            while (rs.next()) {
                Skill s = getSkill(rs);
                
                result.add(s);
                
            }
            c.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }
    
    @Override
    public boolean updateSkill(Skill s) {
        try (Connection c = connect();) {
            PreparedStatement stmt = c.prepareStatement("update skill set name = ? where id =? ");
            stmt.setString(1, s.getName());
            stmt.setInt(2, s.getId());
            
            return stmt.execute();
            
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean addSkill(Skill u) {

//        try (Connection c = connect();) {
//           
//            PreparedStatement stmt = c.prepareStatement("insert  skill (name) values (?);" );
//            stmt.setString(1, u.getName());
//            
//            return stmt.execute();
//
//           
//
//        } catch (Exception ex) {
//
//            ex.printStackTrace();
//
//            return false;
//
//        }
        Connection c;
        boolean b = true;
        try {
            c = connect();
            
            PreparedStatement stmt = c.prepareStatement(" insert  skill (name) values (?);", + Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, u.getName());
            
            b = stmt.execute();
            
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                u.setId(generatedKeys.getInt(1));
                
            }
            
        } catch (Exception ex) {
            
            ex.printStackTrace();
            
            b = false;
            
        }
        return b;
    }
    
    @Override
    public boolean removeSkill(int id) {
        try (Connection c = connect();) {
            
            Statement stmt = c.createStatement();
            return stmt.execute("delete from skill  where id =" + id);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
}
