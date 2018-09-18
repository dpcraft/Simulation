package multicircle;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.util.*;

/**
 * @author dpcraft
 * @date 2018-09-18
 * @time 09:43
 */
public class Utils2 {
    public static void main(String[] args) {
        generateCircle(30,3);
    }

    /**
     * 生成不重复的随机整数
     * @param max 随机数最大值
     * @param quantity 随机数的数量
     * @return
     */
    public static Integer[] getRandom(int max, int quantity) {
        Random random = new Random();
        Integer[] values = new Integer[quantity];
        Set<Integer> randomSet = new LinkedHashSet<Integer>();

        // 生成随机数字并存入HashSet
        while(randomSet.size() < values.length){
            randomSet.add(random.nextInt(max) + 1);
        }
        values = randomSet.toArray(values);
        for (int i = 0; i < values.length; i++) {
            System.out.print(values[i] + ",");
        }
        System.out.println();
        return values;
    }

    public static Map<Integer, Integer[]> generateCircle(int nodeNum, int circleNum){
        Integer[] randomNodeArray = getRandom(nodeNum,nodeNum);
        Integer[] nodesEachCircle = splitIntegerRandom(nodeNum, circleNum);
        Map<Integer,Integer[]> circle = new HashMap<Integer, Integer[]>();
        Integer[] offsets = new Integer[nodesEachCircle.length + 1];
        Integer offset = 0;
        offsets[0] = offset;
        for (int i = 0; i < nodesEachCircle.length; i++) {
            offset = offset + nodesEachCircle[i];
            offsets[i + 1] = offset;
        }
        for (int i = 0; i < offsets.length; i++) {
            System.out.println(offsets[i]);
        }
        for (int i = 0; i < offsets.length - 1; i++) {
            Integer[] tmp = new Integer[nodesEachCircle[i]];
            for (int j = offsets[i], k = 0; j < offsets[i + 1]; j++, k++) {
                tmp[k] = randomNodeArray[j];
            }
            circle.put(nodesEachCircle[i], tmp);
        }
        for (Map.Entry<Integer, Integer[]> entry : circle.entrySet()) {
            Integer[] result = entry.getValue();
            System.out.println(entry.getKey() + " : ");
            for (int i = 0; i < result.length; i++) {
                System.out.print(result[i] + ",");
            }
            System.out.println();
        }
        return circle;

    }



    /**
     * 将节点近似均分到每个环
     * @param nodeNum 节点数
     * @param circleNum 环数
     * @return 分配后每个环上节点数的数组
     */
    public static Integer[] splitInteger(int nodeNum, int circleNum) {
        int quotient = nodeNum / circleNum;
        int remainder = nodeNum % circleNum;
        Integer[] nodesEachCircle = new Integer[circleNum];
        for (int i = 0; i < circleNum; i++) {
            nodesEachCircle[i] = quotient;
            if(i >= circleNum - remainder) {
                nodesEachCircle[i] = nodesEachCircle[i] + 1;
            }

        }


        return nodesEachCircle;

    }

    public static Integer[] splitIntegerRandom(int nodeNum, int circleNum) {
        Integer[] cut = new Integer[circleNum + 1];
        Random random = new Random();
        int top = nodeNum - circleNum;
        HashSet<Integer> hashSet = new HashSet<Integer>();
        hashSet.add(0);
        hashSet.add(top);

        // 生成随机数字并存入HashSet
        while(hashSet.size() < cut.length){
            hashSet.add(random.nextInt(top + 1));
        }
        cut = hashSet.toArray(cut);
        Integer[] nodesEachCircle = new Integer[circleNum];
        Arrays.sort(cut);
        for (int j = 1; j < cut.length; j++) {
            nodesEachCircle[j - 1] = cut[j] - cut[j-1] + 1;

        }
        for (int i = 0; i < nodesEachCircle.length; i++) {
            System.out.println(nodesEachCircle[i]);

        }
        return nodesEachCircle;

    }







}
