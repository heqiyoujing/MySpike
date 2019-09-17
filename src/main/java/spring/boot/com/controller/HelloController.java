package spring.boot.com.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import spring.boot.com.SpringbootApplication;
import spring.boot.com.dao.UserDao;
import spring.boot.com.dao.UserDaoIml;
import spring.boot.com.entity.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: yiqq
 * @date: 2019/3/29
 * @description:
 */
@RestController
public class HelloController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    UserDao userDao = new UserDaoIml(SpringbootApplication.sqlSessionFactory);

    /**
     * 返回对象
     */
    @RequestMapping(value = "/goods", method = RequestMethod.GET)
    @ResponseBody
    public ArrayList<User> GetGoodList(@RequestParam("name") String name, @RequestParam("age") int age) throws Exception{

        ArrayList<User> user = userDao.findInfoList(name, age);
        return user ;
    }

    /**
     * 返回对象，可不传参数，使用默认参数
     */
    @RequestMapping(value = "/goodsCount", method = RequestMethod.GET)
    @ResponseBody
    public ArrayList<User> GetGoodCount(@RequestParam(value = "name",defaultValue = "易强强") String name
            , @RequestParam(value = "age", defaultValue = "27") int age) throws Exception{

        ArrayList<User> user = userDao.findInfoList(name, age);
        return user ;
    }

    /**
     * 查询所有User
     * @return
     */
    @RequestMapping(value = "/selectall", method = RequestMethod.GET)
    @ResponseBody
    public List<User> SelectAll() {
        return userDao.selectall();
    }
}
