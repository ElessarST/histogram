/**
 * @author Aydar Farrakhov
 * @since 08.09.2017
 */
public interface IPitFinder {

    /**
     * Находит самую большую по объему яму
     * @param histogram гистограмма
     * @return Самая большая по объему яма
     */
    Pit findMaxPit(int[] histogram);

}
