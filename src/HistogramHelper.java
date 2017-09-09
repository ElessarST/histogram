import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * @author Aydar Farrakhov
 * @since 08.09.2017
 */
public class HistogramHelper {

    private static final int MAX_VALUE = 20;
    private static final int SIZE = 20;
    private static final char HIST_CHAR = '*';
    private static final char EMPTY_CHAR = '-';
    private static final String COLUMN_SEPARATOR = "\t";

    public static int[] generateHistogram() {
        return new Random().ints(SIZE, 1, MAX_VALUE).toArray();
    }

    public static int[] generateFixedHistogram() {
        return new int[] {17, 17, 13, 7, 18, 6, 11, 13, 3, 15, 14, 11, 5, 7, 17, 9, 4, 9, 14, 5, 19, 18, 4, 1, 12, 2, 5, 19, 13, 7, 6, 5, 19, 16, 12, 1, 11, 17, 2, 10, 1, 9, 2, 4, 10, 19, 4, 12, 3, 18};
    }


    public static void draw(int[] histogram) {
        System.out.println(Arrays.toString(histogram));
        IntStream.range(0, SIZE).forEach(i -> System.out.print(i + COLUMN_SEPARATOR));
        for (int i = MAX_VALUE; i > 0; i--) {
            System.out.println("");
            for (int hisVal : histogram) {
                if (hisVal >= i) {
                    System.out.print(HIST_CHAR);
                } else {
                    System.out.print(EMPTY_CHAR);
                }
                System.out.print(COLUMN_SEPARATOR);
            }
        }
        System.out.println();
    }
}
