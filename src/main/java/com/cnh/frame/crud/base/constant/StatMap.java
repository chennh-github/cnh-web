package com.cnh.frame.crud.base.constant;

import com.cnh.frame.wraps.ObjectWrap;

import java.util.*;

/**
 * ${Description}
 *
 * @author Administrator
 * @version v1.0.0
 * @since 2016/12/16
 */
public class StatMap<K, V> implements Map<K, V> {

    private Map<K, V> map = new HashMap<K, V>();

    public StatMap() {
        super();
    }

    public StatMap(Map.Entry<K, V> ...entry) {
        super();
        for (Map.Entry<K, V> kvEntry : entry) {
            map.put(kvEntry.getKey(), kvEntry.getValue());
        }
    }

    public List<K> keyList() {
        List<K> list = new ArrayList<K>(map.size());
        for (K k : map.keySet()) {
            list.add(k);
        }
        return list;
    }

    public List<V> valueList() {
        List<V> list = new ArrayList<V>(map.size());
        list.addAll(map.values());
        return list;
    }


    public K getKey(V value) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (ObjectWrap.equals(entry.getValue(), value)) {
                return entry.getKey();
            }
        }
        return null;
    }


    public V getValue(K key) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (ObjectWrap.equals(entry.getKey(), key)) {
                return entry.getValue();
            }
        }
        return null;
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        return getValue((K) key);
    }

    @Override
    public V put(K key, V value) {
        return map.put(key, value);
    }

    @Override
    public V remove(Object key) {
        return map.remove(key);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        map.putAll(m);
    }

    public void clear() {
        map.clear();
    }

    @Override
    public Set<K> keySet() {
        return map.keySet();
    }

    @Override
    public Collection<V> values() {
        return map.values();
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        return map.entrySet();
    }


    public static class Entry<K, V> implements Map.Entry<K, V> {

        private K key;

        private V value;

        public Entry() {
        }

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            return this.value = value;
        }
    }

}
