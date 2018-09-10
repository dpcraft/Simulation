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
 * @date 2018-09-03
 * @time 09:43
 */
public class Utils {

    /**
     * 生成不重复的随机整数
     * @param max 随机数最大值
     * @param quantity 随机数的数量
     * @return
     */
    public static ArrayList<Integer> getRandom(int max, int quantity) {
        Random random = new Random();
        Integer[] values = new Integer[quantity];
        HashSet<Integer> hashSet = new HashSet<Integer>();

        // 生成随机数字并存入HashSet
        while(hashSet.size() < values.length){
            hashSet.add(random.nextInt(max) + 1);
        }
        values = hashSet.toArray(values);
        ArrayList<Integer> result = new ArrayList<Integer>(Arrays.asList(values));
//        System.out.println("随机污染节点编号：");
//        for (Integer r:result) {
//            System.out.print(r + ", ");
//
//        }
//        System.out.println();
        return result;
    }

    /**
     * 检查数据污染情况
     * @param nodeNum 节点数
     * @param circleNum 环数
     * @param poisonNodes 污染节点集合
     * @return 污染数据份数
     */
    public static int check(int nodeNum,int circleNum, ArrayList<Integer> poisonNodes){
        Integer[] nodeEachCircle = splitInteger(nodeNum,circleNum);
//        Integer[] nodeEachCircle = splitIntegerRandom(nodeNum,circleNum);
        int result = 0;
        HashMap<Integer, ArrayList<Integer>> poisonNumEachCircle = generateCircle(nodeEachCircle, poisonNodes);
        for(Map.Entry<Integer, ArrayList<Integer>> entry : poisonNumEachCircle.entrySet()) {
            result += check(entry.getKey(), entry.getValue());

        }
//        System.out.println("总共实际污染了 " + result + "份");
        return result;

    }

    /**
     *
     * @param offset 偏移量
     * @param poisonNumOnCircle 污染节点在具体环上的编号
     * @return 该环上污染节点的数目
     */
    public static int check(Integer offset, ArrayList<Integer> poisonNumOnCircle) {
        int nodesNum = poisonNumOnCircle.remove(0);
//        System.out.println("*" + nodesNum);
//        System.out.print("偏移量为 " + offset + " 节点数目为 " + nodesNum + " 的环，污染节点为：");
        HashSet<Integer> poisonNos = new HashSet<Integer>();
        Iterator iterator = poisonNumOnCircle.iterator();
        while(iterator.hasNext()) {
            Integer poisonNo = (Integer)iterator.next();
//            System.out.print(poisonNo + ", ");
            if( poisonNumOnCircle.contains(poisonNo % nodesNum + 1)) {
                poisonNos.add(poisonNo % nodesNum + 1);
            }
            if( poisonNumOnCircle.contains((poisonNo - 2 + nodesNum) % nodesNum + 1)){
                poisonNos.add(poisonNo);
            }


        }
//        System.out.println("\n实际污染数据为：");
//        for (Integer no:poisonNos) {
//            System.out.print("D" + no + ", ");
//        }
//        System.out.println("\n******************");
        return poisonNos.size();

    }

    /**
     * 将被污染的节点编号对应到各个环上
     * @param nodesEachCircle 每个环的节点数
     * @param poisonNodes 污染节点的编号
     * @return 每个环上的污染节点编号
     */
    public static HashMap<Integer,ArrayList<Integer>> generateCircle(
            Integer[] nodesEachCircle, ArrayList<Integer> poisonNodes){
        Iterator iterator = poisonNodes.iterator();
        Integer[] offsets = new Integer[nodesEachCircle.length + 1];
        Integer offset = 0;
        HashMap<Integer, ArrayList<Integer>> result = new HashMap<Integer, ArrayList<Integer>>(nodesEachCircle.length);
        offsets[0] = offset;
        for (int i = 0; i < nodesEachCircle.length; i++) {
            offset = offset + nodesEachCircle[i];
            offsets[i + 1] = offset;
        }
        for (int i = 0; i < nodesEachCircle.length; i++) {
            result.put(offsets[i],new ArrayList<Integer>());
            //arrayList第一个位置放该环上节点的总数目
            result.get(offsets[i]).add(nodesEachCircle[i]);


        }

        while (iterator.hasNext()) {
            Integer tmp = (Integer)iterator.next();
            for (int i = 1; i < offsets.length; i++) {
                if(tmp <= offsets[i]) {
                  result.get(offsets[i - 1]).add(tmp - offsets[i - 1]);
                  break;
                }
            }
        }
        for(Map.Entry<Integer, ArrayList<Integer>> entry : result.entrySet()){
//            System.out.println("偏移量为" + entry.getKey() + "的arrayList：");
            ArrayList<Integer> list = entry.getValue();
//            for (Integer i: list
//                 ) {
//                System.out.print(i + ", ");
//
//            }
//            System.out.println();
        }

        return result;

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
        Integer[] result = new Integer[circleNum];
        for (int i = 0; i < circleNum; i++) {
            result[i] = quotient;
            if(i >= circleNum - remainder) {
                result[i] = result[i] + 1;
            }
        }
        return result;

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
        Integer[] result = new Integer[circleNum];
        Arrays.sort(cut);
        for (int j = 1; j < cut.length; j++) {
            result[j - 1] = cut[j] - cut[j-1] + 1;

        }
        for (int i = 0; i < result.length; i++) {
            System.out.println(result[i]);

        }
        return result;

    }

    public static void fig(Integer[] x, Integer[] y) {
        XYSeries series = new XYSeries("xySeries");
        int xLength = x.length;
        int yLength = y.length;
        if(xLength != yLength){
            throw new IllegalArgumentException("x 和 y 元素个数应该相同");
        }
        for (int i = 0; i < xLength; i++) {
            series.add(x[i], y[i]);
        }

        XYSeriesCollection collection = new XYSeriesCollection();
        collection.addSeries(series);
        JFreeChart chart = ChartFactory.createXYLineChart("污染节点图",
                "污染节点数", "实际污染节点数", collection,
                PlotOrientation.VERTICAL, false, false, false);
        XYPlot plot = (XYPlot)chart.getPlot();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesLinesVisible(0, true); // 设置连线不可见
        plot.setRenderer(renderer);

        ChartFrame frame = new ChartFrame("fuck 图", chart);
        frame.pack(); // fit window to figure size
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }



}
