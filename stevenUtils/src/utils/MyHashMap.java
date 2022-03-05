package utils;

import interfaces.MyMap;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @ClassName utils.MyHashMap
 * @Description Self-used HashMap with reversed table
 * @Author Steven
 * @Date 2022/3/2
 * @Version 1.0
 **/
public class MyHashMap<K, V> implements MyMap<K,V> {


    // private attributes

    private static final int DEFAULT_SIZE = 16;
    private static final float DEFAULT_FACTOR = 0.5f;

    private int capacity;
    private float loadFactor;
    private int entryUseSize; // entry Used size

    private Entry<K, V>[] table = null;
    private Entry<V, K>[] reverseTable = null;


    /**
     *  Intialize function without parameter
     */
    public MyHashMap(){

        this(DEFAULT_SIZE, DEFAULT_FACTOR);

    }

    public MyHashMap(int initCapacity, float loadFactor){

        // 参数合法判断
        if(initCapacity < 0){
            throw new IllegalArgumentException();
        }
        if(loadFactor < 0 || Float.isNaN(loadFactor)){ // 扩充参数大于0且不为NaN
            throw new IllegalArgumentException();
        }

        // attribute赋值
        this.capacity = initCapacity;
        this.loadFactor = loadFactor;

        this.table = new Entry[this.capacity];
        this.reverseTable = new Entry[this.capacity];
        this.entryUseSize = 0;

    }

    /**
     * 把元素放入hashmap，如果bucket没有直接新建；如果bucket已有则替换原来的值
     * @param key 键
     * @param value 值
     * @return 上一个value，如果是新建则为null
     */
    @Override
    public V put(K key, V value) {

        V prev = null;

        // 判断是否需要扩容
        if(entryUseSize >= capacity * loadFactor){
            // 进行扩容
            resize(2 * capacity);
        }
        // 计算hash对应在数组中的index
        int index = hash(key) % capacity;

        if(table[index] == null ){ // 如果size没有越界，直接往entry[]里加东西
            // 直接添加
            table[index] = new Entry(key, value, null);
            entryUseSize++;
        }else{
            // 否则往下遍历
            Entry<K, V> head = table[index];
            Entry<K, V> cur = head;
            while(cur!=null){ // 遍历链表
                if(key == cur.getKey() || key.equals(cur.getKey())){ // 基本数据类型和object都要判断
                    // key对上，这个entry需要update
                    prev = cur.getValue();
                    cur.value = value;
                    // size没变，不用加

                    // update reverse table
                    removeReverseTable(prev, key); // delete the updated node
                    putReverse(key, value);

                    return prev;
                }

                cur = cur.next;
            }
            // 否则说明连表里没有，需要把entry插到bucket里，来个头插法
            table[index] = new Entry<K, V>(key, value, head);
            entryUseSize++; // update size
        }

        return prev;
    }


    private K putReverse(K key, V value){

        K prev = null; // previous key

        // 计算hash对应在数组中的index
        int index = hash(key) % capacity;

        if(reverseTable[index] == null ){ // 如果size没有越界，直接往entry[]里加东西
            // 直接添加
            reverseTable[index] = new Entry(key, value, null);

        }else{
            // 否则往下遍历
            Entry<V, K> head = reverseTable[index];
            Entry<V, K> cur = head;
            while(cur!=null){ // 遍历链表
                if(value == cur.getKey() || value.equals(cur.getKey())){ // 基本数据类型和object都要判断
                    // update that node
                    prev = cur.getValue();
                    cur.value = key;
                    // size没变，不用加
                    return prev;
                }

                cur = cur.next;
            }
            // 否则说明连表里没有，需要把entry插到bucket里，来个头插法
            reverseTable[index] = new Entry<V, K>(value, key, head);

        }

        return prev;
    }

    private K removeReverseTable(V value, K key){

        return null;
    }

    /**
     * 从HashMap中取元素
     * @param key
     * @return 需要get的元素，不存在则为null
     */
    @Override
    public V get(K key) {

        // 计算hash 找到Entry[]中对应的index
        int index = hash(key) % capacity;

        // 如果不存在
        if(table[index] == null){
            return null;
        }

        // variables
        Entry<K, V> head = table[index];
        Entry<K, V> cur = head;

        // begin traverse the bucket list
        while(cur != null){

            if(key == cur.getKey() || key.equals(cur.getKey())){
                return cur.getValue();
            }

            cur = cur.next;
        }

        // 否则就是彻底无了，返回null
        return null;

    }

    /**
     * 查看某个key是否再hashmap中
     * @param key
     * @return 在的话true, 不在的话false
     */
    @Override
    public boolean containsKey(K key) {

        // calculate hash and get index
        int index = hash(key) % capacity;

        // corner case: 如果index在的地方不存在元素
        if(table[index] == null){
            return false;
        }

        // variables for traverse
        Entry<K, V> head = table[index];
        Entry<K, V> cur = head;

        // traverse the bucket
        while(cur != null){
            if(key == cur.getKey() || key.equals(cur.getKey())){
                return true;
            }
            cur = cur.next;
        }

        return false;
    }

    /**
     * 返回所有key所在的集合
     * @return 返回所有key所在的集合
     */
    @Override
    public Set<K> keySet() {

        if(this.table == null){
            return null;
        }

        // variables
        Set<K> set = new HashSet<>();

        for(Entry<K, V> e : this.table){
            // traverse begin
            while(e != null){
                set.add(e.getKey());
                e = e.next;
            }
        }

        return set;
    }

    /**
     * check the hashmap contains certain value
     * @param value 值
     * @return true if value exists, otherwise false
     */
    @Override
    public boolean containsValue(V value){

        // calculate hash and get index
        int index = hash(value) % capacity;

        if(reverseTable[index] == null){
            return false;
        }

        // variables
        Entry<V, K> e = reverseTable[index];

        while(e != null){
            if(value == e.getKey() || value.equals(e.getKey())){
                return true;
            }
            e = e.next;
        }

        return false;
    }

    public Set<V> valueSet(){
        return null;
    }

    /**
     * 用作计算一个key的hash值，key为null的情况会有特殊返回值
     * @param key 键
     * @return key的hash值，如果key为null则返回0
     */
    private int hash(Object key){
        int hashCode;
        if(key == null){
            return 0; // null condition
        }else{
            return key.hashCode(); // normal return
        }
    }

    /**
     * 当table太拥挤的时候，要重新hash一下
     * @param table
     */
    private void rehash(Entry<K, V>[] table){

        List<Entry<K, V>> entryList = new ArrayList<>();

        // 把所有东西拷到entryList里做中转
        for(Entry<K, V> e : table){
            if(e != null){
                // 非空代表有东西，要移出来
                while(e != null){
                    entryList.add(e);
                    e = e.next;
                }
            }
        }

        // 覆盖原来的数组
        if(table.length > 0){
            this.table = table;
        }

        // rehash 重新散列
        for(Entry<K, V> e : entryList){
            this.put(e.getKey(), e.getValue());
        }
    }

    /**
     * 给table扩容，维护hashmap的capacity和size
     * @param size 新table的容量
     */
    private void resize(int size){

        // variables
        Entry<K, V>[] resizedTable = new Entry[size];

        // update capacity and size
        this.capacity = size;
        this.entryUseSize = 0;

        // 重新散列
        rehash(resizedTable);
    }

    /**
     * 内部类Entry的实现
     * @param <K> key
     * @param <V> value
     */
    class Entry<K, V> implements MyMap.Entry<K, V>{

        private K key;

        private V value;

        private Entry<K, V> next;

        /**
         * 无参数Entry构造函数
         */
        public Entry(){

        }

        /**
         * 有参构造
         * @param key key
         * @param value value
         * @param next 指向下一个node
         */
        public Entry(K key, V value, Entry<K,V> next){
            super();
            this.key = key;
            this.value = value;
            this.next = next;
        }

        @Override
        public K getKey() {
            return this.key;
        }

        @Override
        public V getValue() {
            return this.value;
        }
    }

}
