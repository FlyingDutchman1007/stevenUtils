package tests.utilsTests;

import utils.MyHashMap;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @ClassName MyHashMapTest
 * @Description 测试hashmap到底能不能用，顺便康康性能
 * @Author Steven
 * @Date 2022/3/4
 * @Version 1.0
 **/
public class MyHashMapTest {


    public static void main(String[] args) {

        Random ram = new Random();

        int min = Integer.MIN_VALUE;
        int max = Integer.MAX_VALUE;


        MyHashMap<Integer, Integer> hp = new MyHashMap<>();
        hp.put(1,4);
        hp.put(32423,4365);
        for(int i = 0; i < 100000; i++){
            int randomNum1 = ram.nextInt(1000) + 1;
            int randomNum2 = ram.nextInt(1000) + 1;

            hp.put(randomNum1, randomNum2);
            System.out.format("Inserting the entry: key = %s, value = %s \n", randomNum1, randomNum2);
            System.out.println(hp.containsKey(randomNum1));
        }

        System.out.println(hp.get(1));
//        System.out.println();
    }
}
