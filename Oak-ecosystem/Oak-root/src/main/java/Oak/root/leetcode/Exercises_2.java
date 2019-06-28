package Oak.root.leetcode;


/**
 * Created by Oak on 2018/7/4.
 * Exercises:有两个单链表，代表两个非负数，每一个节点代表一个数位，数字是反向存储的，即第一个结点表示最低位，最后一个结点表示最高位。求两个数的相加和，并且以链表形式返回
 * 解析：对两个链表都从第一个开始处理，进行相加，结果再除以10求商，作为下一位相加的进位，同时记录余数，作为本位的结果，一直处理，直到所有的结点都处理完
 */
public class Exercises_2 {
    public ListNode addData(ListNode l1, ListNode l2){
        if (l1 == null){
            return l2;
        }
        if (l2 == null){
            return l1;
        }

        ListNode tmpNode1 = l1;
        ListNode tmpNode2 = l2;

        ListNode root = new ListNode(0); // 头结点
        ListNode r = root;
        root.nextNode = l1;

        int carry = 0;
        int sum = 0;
        if (tmpNode1 != null && tmpNode2 != null){
            sum = tmpNode1.val + tmpNode2.val + carry;
            tmpNode1.val += sum % 10;
            carry = sum / 10;

            r.nextNode = tmpNode1;
            r = tmpNode1; // 指向最后一个相加的结点

            tmpNode1 = tmpNode1.nextNode;
            tmpNode2 = tmpNode2.nextNode;
        }


        if (tmpNode1 == null) {
            r.nextNode = tmpNode2;
        } else {
            r.nextNode = tmpNode1;
        }

        // 最后一次相加还有进位
        if (carry == 1) {
            // 开始时r.next是第一个要相加的结点
            while (r.nextNode != null) {
                sum = r.nextNode.val + carry;
                r.nextNode.val = sum % 10;
                carry = sum / 10;
                r = r.nextNode;
            }

            // 都加完了还有进位，就要创建一个新的结点
            if (carry == 1) {
                r.nextNode = new ListNode(1);
            }
        }

        return root.nextNode;
    }

    class ListNode{
        int val;
        ListNode nextNode;
        ListNode(int val){
            this.val=val;
            this.nextNode=null;
        }
    }
}
