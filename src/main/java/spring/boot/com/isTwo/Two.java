package spring.boot.com.isTwo;

/**
 * @author: yiqq
 * @date: 2019/4/19
 * @description: 判断一个数是否是基数
 * https://mp.weixin.qq.com/s?__biz=MzIzMzgxOTQ5NA==&mid=2247486909&idx=1&sn=9f6176528e88814fbcfc5cb800872e29&chksm=e8fe91b4df8918a23f3b35455959e618442342087798369e47b8ac375b093797643f5cd13237&scene=0&xtrack=1#rd
 */
public class Two {
    public static void main(String[] args) {
        System.out.println(isOdd(2));
        System.out.println(indexFor(5,15));
    }


    public static boolean isOdd(int i) {
        return (i & 1) == 1;//转为二进制,都是1结果为1,否则为0
    }

    static int indexFor(int h, int length) {
        //转为二进制,都是1结果为1,否则为0
        return h & (length-1);
    }
}
