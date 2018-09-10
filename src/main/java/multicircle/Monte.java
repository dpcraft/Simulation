package multicircle;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author dpcraft
 * @date 2018-09-03
 * @time 09:42
 */
public class Monte {

    public static void main(String[] args) {

        long startTime = System.currentTimeMillis();
//      Utils.splitIntegerRando  m(30,15);
        HashMap<Integer,Float[]> resultMap = new HashMap();
        int nodeNum = 30;
        int maxPoisonNo = nodeNum/2;
        for (int circleNum = 1; circleNum <= maxPoisonNo; circleNum++) {
            resultMap.put(circleNum, silmu(nodeNum,circleNum));
        }
        for(Map.Entry<Integer, Float[]> entry:resultMap.entrySet()){
            System.out.println(entry.getKey() + "个环：");
            Float[] result = entry.getValue();
            System.out.print("[");
            for (int i = 0; i < result.length; i++) {
                System.out.print(result[i] + ", ");
            }
            System.out.println("]");
        }
        long endTime = System.currentTimeMillis();
        System.out.println("run time " + (endTime - startTime) + " ms");
        System.out.println("run time " + (endTime - startTime)/1000 + " s");
    }


    public static Float[] silmu(int nodeNum, int circleNum) {
        int maxPoisonNum = nodeNum / 2;
        int experimentTimes = 10000;
        Float[] result = new Float[maxPoisonNum + 1];
        Integer[] x = new Integer[maxPoisonNum + 1];
        for (int poisonNum = 0; poisonNum <= maxPoisonNum; poisonNum++) {
            result[poisonNum] = 0f;
            x[poisonNum] = poisonNum;
            for (int i = 0; i < experimentTimes; i++) {
                ArrayList<Integer> poisonNo = Utils.getRandom(nodeNum, poisonNum);
                if (Utils.check(nodeNum, circleNum, poisonNo) >= 1) {
                    result[poisonNum]++;
                }
            }
            result[poisonNum] = 1f - result[poisonNum] / (float) experimentTimes;
        }

//        System.out.print("[");
//        for (int i = 0; i < result.length; i++) {
//            System.out.print(result[i] + ", ");
//        }
//        System.out.println("]");
//        Utils.fig(x,result);
        return result;

    }

//[1.0, 1.0, 0.9326, 0.8014, 0.62979996, 0.44529998, 0.28890002, 0.15530002, 0.07669997, 0.028900027, 0.010699987, 0.002399981, 2.9999018E-4, 0.0, 0.0, 0.0, ]
    // [1.0, 1.0, 0.9283, 0.8028, 0.6286, 0.44709998, 0.29009998, 0.15920001, 0.07230002, 0.03189999, 0.00849998, 0.0019999743, 5.0002337E-4, 0.0, 0.0, 0.0, ]
}
