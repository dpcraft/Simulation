import multicircle.Utils;
import multicircle.Utils2;

import java.util.ArrayList;


/**
 * @author dpcraft
 * @date 2018/9/18
 * @time 9:18
 */
public class violence {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        int nodeNum = 30;
        int circleNum = 30;

        comb(nodeNum,3,3);
        long endTime = System.currentTimeMillis();
        System.out.println("run time " + (endTime - startTime) + " ms");
        System.out.println("run time " + (endTime - startTime) / 1000 + " s");
    }

    /**
     * 穷举组合数
     * @param nodeNum
     */
    public static void comb(int nodeNum, int circleNum,int selectNum) {
        int nbits = 1 << nodeNum;
        boolean flag ;
        int count = 0;
        int c = 0;

        for (int i = 0; i < nbits; ++i) {
            int t;
            flag = false;
            ArrayList<Integer> arrayList = new ArrayList<Integer>(selectNum);
            if(count1(i) == selectNum) {
                for (int j = 0; j < nodeNum; j++) {
                    t = 1 << j;
                    if ((t & i) != 0) { // 与运算，同为1时才会是1
                        arrayList.add(j+1);
                        System.out.print((j+1) + " ");
                        flag = true;
                    }
                }
            }
            if(flag) {
                System.out.println();
                if (Utils.check(nodeNum, circleNum, arrayList) >= 1) {

                    c++;
                }
                count++;
            }
            System.out.println(c);
        }
        System.out.println(c);
        System.out.println(c/count);
        System.out.println("总数：" + count);
    }
    public static int count1(int num)
    {
        int sum = 0;
        while(num != 0)
        {
            num &= num-1;
            sum++;
        }
        return sum;
    }
}
