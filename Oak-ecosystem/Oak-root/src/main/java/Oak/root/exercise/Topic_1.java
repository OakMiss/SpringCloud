package Oak.root.exercise;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Oak on 2018/7/20.
 * Description:
 */
public class Topic_1 {
    public void test(){
        Map map = new HashMap<>();
        map.put("key1","name1");
        map.put("key2","name2");
        map.put("key3","name3");
        map.put("key4","name4");
        map.keySet().forEach( key -> {
            System.out.println(key + ":" + map.get(key));
        });
        map.forEach((k,v) -> {
            System.out.println(k+":"+v);
        });
    }

    public static void main(String[] args) {
        Topic_1 t = new Topic_1();
        t.test();
    }

}
