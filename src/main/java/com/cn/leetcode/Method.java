package com.cn.leetcode;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Method {
    /*
    1、两数之和
     */
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(target - nums[i])) {
                return new int[]{map.get(target - nums[i]), i};
            }
            map.put(nums[i], i);
        }
        return new int[]{};
    }

    /*
    49. 字母异位词分组
    思路：字母异位词，重新排序后相同
         需要一个数据结构来存储所有的字母异位词。一个合适的选择是哈希表，它可以快速地查找和插入键值对。
         我们可以用排序后的字符串作为键，用对应的异位词列表作为值
     */
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String , List<String>> map = new HashMap<>();
        for (String str : strs) {
            char[] ch = str.toCharArray();
            Arrays.sort(ch);
            String afterSort = new String(ch);
            if (!map.containsKey(afterSort)) {
                map.put(afterSort, new ArrayList<>());
            }
            map.get(afterSort).add(str);
        }
        return new ArrayList<>(map.values());
    }

    /*
    128. 最长连续序列
    思路：哈希表。遍历数组，如果数在哈希表中不存在，说明不是一个连续序列起点。
         如果存在，说明可能是一个连续序列起点。删除这个数，并且检查比他大或小的数是否存在，存在则删除
         当检查到一个数在哈希表中不存在时，说明这个连续序列结束了，我们更新最长连续序列的长度，并继续遍历数组中的下一个数
     */
    public int longestConsecutive(int[] nums) {
        HashSet<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }

        int maxLen = 0;
        for (int num : nums) {
            if (!set.contains(num)) {
                continue;
            }
            set.remove(num);
            int curLen = 1;
            int left = num - 1;
            while (set.contains(left)) {
                set.remove(left);
                left--;
                curLen++;
            }

            int right = num + 1;
            while (set.contains(right)) {
                set.remove(right);
                right++;
                curLen++;
            }
            maxLen = Math.max(maxLen, curLen);
        }
        return maxLen;
    }

}
