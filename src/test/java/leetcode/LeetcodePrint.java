package leetcode;

import com.cn.SparkPracticeApplication;
import com.cn.leetcode.Method;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@SpringBootTest(classes = SparkPracticeApplication.class)
@RunWith(SpringRunner.class)
@Component
public class LeetcodePrint {

    @Autowired
    private Method method;

    @Test
    public void twoSumPrint() {
        int[] array = new int[]{1, 7, 8, 9};
        int[] ints = method.twoSum(array, 9);
        System.out.println(Arrays.toString(ints));
    }

    @Test
    public void groupAnagramsPrint() {
        String[] strs = new String[]{"eat", "tea", "tan", "ate", "nat", "bat"};
        List<List<String>> lists = method.groupAnagrams(strs);
        System.out.println(lists);
    }

}
