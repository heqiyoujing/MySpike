package spring.boot.com.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import spring.boot.com.entity.User;

/**
 * @author: yiqq
 * @date: 2018/8/8
 * @description:
 */

public interface UserRepositoty extends JpaRepository<User,Long> {

    User findByName(@Param("name") String name);
}
