package cn.zrkcoder.cloud;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * @author zrk on 2026/4/12
 */
public class Test {

    public static void main(String[] args) {
        Set<String> set = new HashSet<>();
        set.add("apple");
        set.add("banana");
        set.add("apple");   // 重复，add 返回 false，集合不变
        set.add(null);      // ✅ 允许一个 null

        System.out.println(set.size());

    }
}
