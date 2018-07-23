package Oak.root.leetcode;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Oak on 2018/7/20.
 * Description:给定一个字符串，找字符中的最大非重复子串
 */
public class Exercises_3 {
    /**
     * 思路：从第一位找非重复字符串，定一个长量，按大小放入
     */

    public static void checkStr(String str){
        int index = 0;
        int length = str.length();
        char[] st= str.toCharArray();
        while (true){
            Set set = new HashSet();
            set.add(st[index]);
            for (int i = index+1; i < length; i++) {
                set.add(st[i]);
                if (set.size() != i){

                }
            }
        }
    }
}
