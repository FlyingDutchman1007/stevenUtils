package Interface;

import java.util.Set;

public interface MyMap<K, V> {

    /**
     * insert method
     * @param key
     * @param value
     * @return
     */
    public V put(K key, V value);

    /**
     * get the value from a map
     * @param key
     * @return
     */
    public V get(K key);

    /**
     * judge if the map contains certain key
     * @param key
     * @return
     */
    public boolean containsKey(K key);

    /**
     * get all the keys in key's set
     * @return
     */
    public Set<K> keySet();

    public boolean containsValue(V value);


    interface Entry<K, V>{

        /**
         * getter of KEY
         * @return K
         */
        public K getKey();

        /**
         * getter of Value
         * @return V
         */
        public V getValue();

    }

}
