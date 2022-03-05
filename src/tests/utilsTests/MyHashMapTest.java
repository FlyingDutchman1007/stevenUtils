package tests.utilsTests;

import utils.MyHashMap;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @ClassName MyHashMapTest
 * @Description 测试hashmap到底能不能用，顺便康康性能
 * @Author Steven
 * @Date 2022/3/4
 * @Version 1.0
 **/
public class MyHashMapTest {


    Random ram = new Random();

    int min = Integer.MIN_VALUE;
    int max = Integer.MAX_VALUE;

    MyHashMap<Integer, Integer> hp = new MyHashMap<>();
    Map<Integer, Integer> hj = new HashMap<>();

    /**
     * 指定put操作轮数和随机数bound，测试自定义HashMap和Java hashmap跑put操作的时间差异
     * @param rounds 指定put操作的轮数
     * @param bound key, value作为随机int的范围
     */
    public void performanceTest(int rounds, int bound){

        // our hashmap timer begins
        Long startTimeM = System.currentTimeMillis();

        for(int i = 0; i < rounds; i++) {

            int randomNum1 = ram.nextInt(bound) + 1;
            int randomNum2 = ram.nextInt(bound) + 1;

            hp.put(randomNum1, randomNum2);
        }

        // our timer ends
        Long endTimeM = System.currentTimeMillis();
        Long timeM = endTimeM - startTimeM;

        System.out.format("[PUT OPERATION] Total time for MyHashMap: %s ms, with total rounds:%s \n", timeM, rounds);

        // java hashmap timer begins
        Long startTimeJ = System.currentTimeMillis();

        for(int i = 0; i < rounds; i++) {
            int randomNum1 = ram.nextInt(bound) + 1;
            int randomNum2 = ram.nextInt(bound) + 1;

            hj.put(randomNum1, randomNum2);
        }

        // java hash map timer ends
        Long endTimeJ = System.currentTimeMillis();
        Long timeJ = endTimeJ - startTimeJ;

        System.out.format("[PUT OPERATION] Total time for JavaHashMap: %s ms, with total rounds:%s \n", timeJ, rounds);

    }

    /**
     * 传说中加上reverseTable能将containsValue的TC压缩到0(1) 可以在这里测试一下效果
     * @param rounds containsValue操作执行次数
     * @param bounds key value作为随机int的范围
     */
    public void getValueTest(int rounds, int bounds){
        // 等思考清楚implementation再启动，把它想简单了
    }

    public void BasicTest(){

    }


    public static void main(String[] args) {

        MyHashMap<Integer, Integer> hp = new MyHashMap<>();

        hp.put(324, 3353);
        hp.put(324, 54345);

        System.out.println(hp.containsKey(324));
        System.out.println(hp.get(324));
        System.out.println(hp.containsValue(54345));


        System.out.println(hp.get(1));
//        System.out.println();

//        Map<Integer, Integer> m = new HashMap<>();
//        m.put(324,3452);
//        m.put(324, 32432);


        MyHashMapTest test = new MyHashMapTest();
        test.performanceTest(500000000, 20000);


    }
}
