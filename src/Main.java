public class Main {

    /**
     * <p>Решает задачу:</p>
     * <p>Дана гистограмма, идет дождь</p>
     * <p>Найти, между какими значениями гистограммы будет самая большая по объему яма</p>
     * @param args
     */
    public static void main(String[] args) {
        PitFinder finder = new PitFinder();
        int[] histogram = HistogramHelper.generateHistogram();
        HistogramHelper.draw(histogram);

        System.out.println(finder.findMaxPit(histogram));

    }
}
