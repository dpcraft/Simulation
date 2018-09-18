import static multicircle.Utils.comb;

/**
 * @author dpcraft
 * @date 2018/9/18
 * @time 9:18
 */
public class violence {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        int nodeNum = 30;
        comb(nodeNum,2);
        long endTime = System.currentTimeMillis();
        System.out.println("run time " + (endTime - startTime) + " ms");
        System.out.println("run time " + (endTime - startTime) / 1000 + " s");
    }
}
