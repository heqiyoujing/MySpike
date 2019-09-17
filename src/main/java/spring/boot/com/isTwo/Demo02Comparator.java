package spring.boot.com.isTwo;

import spring.boot.com.entity.User;

import java.util.*;

/**
 * @author: yiqq
 * @date: 2019/4/23
 * @description:
 */
public class Demo02Comparator {
    //定义一个方法,方法的返回值类型使用函数式接口Comparator
//    public static Comparator<String> getComparator(){
//        //继续优化Lambda表达式
//        return (o1, o2)->o2.length()-o1.length();
//    }

    public static void main(String[] args) {
//        //创建一个字符串数组
//        String[] arr = {"aaa","b","cccccc","dddddddddddd"};
//        //输出排序前的数组
//        System.out.println(Arrays.toString(arr));//[aaa, b, cccccc, dddddddddddd]
//        //调用Arrays中的sort方法,对字符串数组进行排序
//        Arrays.sort(arr,getComparator());
//        //输出排序后的数组
//        System.out.println(Arrays.toString(arr));//[dddddddddddd, cccccc, aaa, b]
       /* String[] arr = {"aaa","bbb","ccc","ddd","fff","ggg"};
        Arrays.sort(arr, (String s1, String s2) -> (s1.compareTo(s2)));//Lambdas排序集合
        System.out.println(Arrays.toString(arr));*/
        List<User> list = new ArrayList<>();
        User user = new User();
        user.setName("yiqq");
        user.setEmail("111111");
        list.add(user);
        User user1 = new User();
        user1.setName("yqq");
        user1.setEmail("222222");
        list.add(user1);
        get(list);

        for (int i=0;i<list.size();i++) {
            System.out.println(list.get(i));
        }
        System.out.println("--------------");
    }

    public static void get(List<User> list){
        for(int i=0;i<list.size();i++) {
            User user = list.get(i);
            int a = new Random().nextInt(10);
            user.setAge(a);
        }

    }
}
