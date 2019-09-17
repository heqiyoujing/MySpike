package spring.boot.com.dao;


import org.apache.ibatis.annotations.Param;
import spring.boot.com.entity.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: yiqq
 * @date: 2018/7/20
 * @description:
 */
public interface UserDao {
    ArrayList<User> findInfoList(@Param("name") String name, @Param("age") int age);

    Integer findCount(@Param("name") String name, @Param("age") int age);

    String findName(User user);

    boolean insert(User user);

    List<User> selectall();



}
