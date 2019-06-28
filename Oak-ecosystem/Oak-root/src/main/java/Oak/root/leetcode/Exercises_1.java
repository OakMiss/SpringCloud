package Oak.root.leetcode;

import java.util.Arrays;

/**
 * Created by Oak on 2018/6/29.
 * Description: 数组中两数之和等于某个数，找出数组中两数的位置
 */
public class Exercises_1 {

    /**
     * 方法一
     * @param numbs 数组（只存在一组满足）
     * @param target 目标数
     * @return
     */
    public static String fun(int[] numbs,int target){
        for (int i = 0; i < numbs.length-1; i++){
            for (int j = i+1; j < numbs.length; j++){
                if (numbs[i]+numbs[j] == target){
                    return "["+(i+1)+","+(j+1)+"]";
                }
            }
        }
        return null;
    }


    /**
     * 定义一个数组节点对象
     */
    private static class Node implements Comparable<Node>{

        int val;  //值
        int index;  //值对应的下标

        public Node() {
        }

        public Node(int val, int index) {
            this.val = val;
            this.index = index;
        }

        /**
         * 比较方法
         * @param o
         * @return
         */
        @Override
        public int compareTo(Node o) {
            if (o == null){
                return -1;
            }
            return this.val - o.val;
        }
    }

    /**
     * 方法二
     * @param numbs
     * @param target
     * @return
     */
    public static int[] fun2(int[] numbs,int target){
        int[] result = {0,0};
        Node[] nodes = new Node[numbs.length];
        for (int i = 0; i < numbs.length; i++) {
            nodes[i] = new Node(numbs[i],i);
        }
        // 对节点类排序
        // Collections.sort(nodes);   //list<Object> 的排序
        Arrays.sort(nodes);
        // 定义左右指针
        int linde = 0,rinde = numbs.length-1;
        while (linde < rinde){
            if (nodes[linde].val + nodes[rinde].val == target){
                if (nodes[linde].index > nodes[rinde].index) {  //这里的下标已经乱了
                    result[0] = nodes[rinde].index;
                    result[1] = nodes[linde].index;
                }else {
                    result[0] = nodes[linde].index;
                    result[1] = nodes[rinde].index;
                }
                break;
            }
            else if (nodes[linde].val + nodes[rinde].val > target){
                rinde --;
            }
            else {
                linde ++;
            }
        }
        return result;
    }



    public static void main(String[] args) {
        int[] a = {0,1,2,3,6};
        int target = 5;
        int[] b = fun2(a,target);
        for (int i = 0; i <b.length ; i++) {
            System.out.println(b[i]);
        }
    }
}
