package spring.boot.com.isTwo;

/**
 * @author: yqq
 * @date: 2019/4/22
 * @description: 哈哈哈，排序的牛逼算法
 * https://blog.csdn.net/P5dEyT322JACS/article/details/83965388
 */
public class ArraySort implements Runnable {
    private int number;

    public ArraySort(int number) {
        this.number = number;
    }

    public static void main(String[] args) {
        int[] numbers = new int[]{102, 338, 62, 9132, 580, 666};
        for (int number : numbers) {
            new Thread(new ArraySort(number)).start();
        }
    }

    @Override
    public void run() {
        try {
            Thread.sleep(this.number);
            System.out.println(this.number);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
