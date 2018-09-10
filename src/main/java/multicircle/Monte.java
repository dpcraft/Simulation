package multicircle;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dpcraft
 * @date 2018-09-03
 * @time 09:42
 */
public class Monte {
    HashMap<Integer, Float[]> resultMap = new HashMap();

    public static void main(String[] args) throws Exception {
        final Monte monte = new Monte();

        long startTime = System.currentTimeMillis();
//      Utils.splitIntegerRando  m(30,15);

        final int nodeNum = 30;
        final Integer[] maxPoisonNo = Utils.splitInteger(nodeNum / 2, 4);
        for (int i = 1; i < maxPoisonNo.length; i++) {
            maxPoisonNo[i] += maxPoisonNo[i - 1];
        }
//        for (int i = 0; i < maxPoisonNo.length; i++) {
//            System.out.println(maxPoisonNo[i]);
//        }
        Thread threadA = new Thread(new myRunnable(1, maxPoisonNo[0], nodeNum, monte));
        Thread threadB = new Thread(new myRunnable(maxPoisonNo[0] + 1, maxPoisonNo[1], nodeNum, monte));
        Thread threadC = new Thread(new myRunnable(maxPoisonNo[1] + 1, maxPoisonNo[2], nodeNum, monte));
        Thread threadD = new Thread(new myRunnable(maxPoisonNo[2] + 1, maxPoisonNo[3], nodeNum, monte));
        threadA.start();
        threadB.start();
        threadC.start();
        threadD.start();
        try {
            threadA.join();
            threadB.join();
            threadC.join();
            threadD.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        File outFile = new File("./out/result.txt");
        if (!outFile.exists()) {
            outFile.createNewFile();
        }
        OutputStream outputStream = new FileOutputStream(outFile);
        for (Map.Entry<Integer, Float[]> entry : monte.resultMap.entrySet()) {
            Float[] result = entry.getValue();
            for (int i = 0; i < result.length; i++) {
                outputStream.write((result[i] + " ").getBytes());
            }
            outputStream.write(" \n".getBytes());
        }
        long endTime = System.currentTimeMillis();
        System.out.println("run time " + (endTime - startTime) + " ms");
        System.out.println("run time " + (endTime - startTime) / 1000 + " s");
    }

    public void write(int circleNum, int nodeNum) {
        resultMap.put(circleNum, silmu(nodeNum, circleNum));
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

//        Utils.fig(x,result);
        return result;

    }


}
class myRunnable implements Runnable{
    Integer startIndex;
    Integer endIndex;
    Integer nodeNum;
    Monte monte;
    public void run(){
        for (int circleNum = startIndex; circleNum <= endIndex; circleNum++) {
            monte.write(circleNum, nodeNum);
        }
    }

    public myRunnable(Integer startIndex, Integer endIndex, Integer nodeNum, Monte monte) {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.nodeNum = nodeNum;
        this.monte = monte;
    }
}
