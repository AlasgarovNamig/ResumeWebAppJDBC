/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.dao.impl;

import com.company.dao.inter.AbstractDao;
import com.company.dao.inter.UserSkillDaoInter;
import com.company.entity.Skill;
import com.company.entity.User;
import com.company.entity.UserSkill;
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
public class UserSkillDaoImpl extends AbstractDao implements UserSkillDaoInter {

    public UserSkill getUserSkill(ResultSet rs) throws Exception {
        int userSkillId = rs.getInt("userSkillId");
        int userId = rs.getInt("id");
        int skillId = rs.getInt("skill_id");
        String skillName = rs.getString("skill_name");
        int power = rs.getInt("power");

        return new UserSkill(userSkillId, new User(userId), new Skill(skillId, skillName), power);

    }

    @Override
    public List<UserSkill> getAllUserSkillByUserId(int Id) {
        List<UserSkill> result = new ArrayList<>();
        try {
            Connection c = connect();
            PreparedStatement stmt = c.prepareStatement(" select us.id as userSkillId,"
                    + "u.*,us.skill_id,s.name as Skill_name,"
                    + "us.power from user_skill us"
                    + " left join user u on us.user_id = u.id"
                    + " left join skill s on us.skill_id = s.id"
                    + " where us.user_id = ? ");
            stmt.setInt(1, Id);
            stmt.execute();

            ResultSet rs = stmt.getResultSet();

            while (rs.next()) {
                UserSkill u = getUserSkill(rs);

                result.add(u);

            }
            c.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;

    }

    @Override
    public boolean addUserSkill(UserSkill us) {
//        try (Connection c = connect();) {
//            PreparedStatement stmt = c.prepareStatement("insert   user_skill(user_id,skill_id,power) values(?,?,?) ");
//            stmt.setInt(1, us.getUser().getId());
//            stmt.setInt(2, us.getSkill().getId());
//            stmt.setInt(3, us.getPower());
//            
//            
//            System.out.println(stmt.execute());
//            return stmt.execute();
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            return false;
//        }
//--------------------------------------------------------------------------------------------------------------
        Connection c;
        boolean b = true;

        try {
            c = connect();
            PreparedStatement stmt = c.prepareStatement("insert  into  user_skill(user_id ,skill_id ,power ) values(?,?,?) ");
            stmt.setInt(1, us.getUser().getId());
            stmt.setInt(2, us.getSkill().getId());
            stmt.setInt(3, us.getPower());

         b = stmt.execute();

        } catch (Exception ex) {
            ex.printStackTrace();
            b = false;
        }

        return b;

    }

    @Override
    public boolean updateUserSkill(UserSkill us) {
        try (Connection c = connect();) {
            PreparedStatement stmt = c.prepareStatement("update user_skill set user_id = ?,skill_id = ?,power = ? where id =? ");
            stmt.setInt(1, us.getUser().getId());
            stmt.setInt(2, us.getSkill().getId());
            stmt.setInt(3, us.getPower());
            stmt.setInt(4, us.getId());

            return stmt.execute();

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean removeUserSkill(int id) {
        try (Connection c = connect();) {

            Statement stmt = c.createStatement();
            return stmt.execute("delete from user_skill  where id =" + id);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
