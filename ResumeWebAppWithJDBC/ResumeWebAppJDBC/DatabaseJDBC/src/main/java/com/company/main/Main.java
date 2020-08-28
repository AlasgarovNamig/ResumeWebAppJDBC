/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.main;

import com.company.dao.inter.UserDaoInter;
import com.company.entity.User;

/**
 *
 * @author Hp
 */
public class Main {

    public static void main(String[] args) throws Exception {
        UserDaoInter udi =Contex.instanceUserDao();
//        User u = new User(0, "Shahin", "Alasgarov", "Mr.Shahin@gmail.com"," +994515459556" , "23456", adress, birthDate, nationality, birthPlace)
//        udi.
       
        
       // System.out.println( udi.findbyEmailAndPasswor("sr.alasgarov@gmail.com", "12345"));
//        SkillDaoInter sklDao = Contex.instanceSkillDao();
//        sklDao.addSkill(new Skill(0, "Qarpiz"));
        
  //       UserDaoInter userDao = Contex.instanceUserDao();
//      
//        User u = new User(0," Tofiq", "Cabbarov", "Cabbarov.93@gmail.com" ,"+994707008011");
//        
//        userDao.addUser(u);
//        List<User> list = userDao.getAll();
//        System.out.println(list);
//       User u = userDao.getById(2);
//        System.out.println(u);
//        System.out.println("by id ile gelen data ");
//       u.setName("SHAHIN1");
//        System.out.println(u);
//        System.out.println("set olunmus data ");
//       userDao.updateUser(u);
//        System.out.println(u);
//        System.out.println("update olunmus data ");
//        System.out.println(userDao.getById(2));
//        System.out.println(" yeniden by id ilde gelen data ");
//
//   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//Date d =  sdf.parse("1999-11-21");
//       long l = d.getTime();
//       java.sql.Date bd = new java.sql.Date(l);
//       
//User u = new User(0, "Nail", "Akbarov", "nail.akbarov@gmail.com", "+995503409966", "islemir", "Baku", bd,new  Country(3), new Country(4));
//     userDao.addUser(u);
//System.out.println("process complated:");
    }

}
