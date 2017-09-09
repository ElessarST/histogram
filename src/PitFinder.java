import java.util.*;

/**
 * @author Aydar Farrakhov
 * @since 08.09.2017
 */
public class PitFinder implements IPitFinder {


    /**
     * Находит ямы по алгоритму:
     * <ul>
     *     <li>Допустим, мы рассматриваем левый борт. Текущий объем равен 0</li>
     *     <li>Пока следующее значение меньше предыдущего, добавляем к объему, как будто высота ямы равна левому борту</li>
     *     <li>Пока следующее значение больше предыдущего, добавляем к объему, как будто высота ямы равна левому борту</li>
     *     <li>Рассматриваем правый борт:
     *          <ol>
     *             <li>Если левый борт по высоте выше правого, то из текущего объема вычитаем разницу по высоте
     *             умноженную на ширину ямы + правый борт считаем началом новой ямы и добавляем в очередь</li>
     *             <li>Если левый борт по высоте ниже правого, то рассчитываем объем этой ямы, считаем ее рассмотренной,
     *             удаляем ее из очереди
     *             </li>
     *         </ol>
     *     </li>
     *     <li>В итоге находим из рассмотренных самую большую по объему яму</li>
     * </ul>
     * @param histogram гистограмма
     * @return самая глубокая яма
     */
    @Override
    public Pit findMaxPit(int[] histogram) {
        int start = getBordersStartIndex(histogram);
        int end = getBordersEndIndex(histogram);
        if (start > end) {
            return null;
        }

        return Collections.max(findPits(histogram, start, end), Comparator.comparing(Pit::getVolume));
    }


    /**
     * Ищет возможные ямы в гистограмме
     * @param histogram гистограмма
     * @param start     индекс начала
     * @param end       индекс окончания
     * @return
     */
    private List<Pit> findPits(int[] histogram, int start, int end) {
        Deque<Pit> currentPits = new LinkedList<>();
        List<Pit> donePits = new ArrayList<>();
        currentPits.add(new Pit(start, histogram[start]));
        int currentVolume = 0;
        for (int borderEnd = start + 1; borderEnd <= end; borderEnd++) {
            int currentHeight = histogram[borderEnd];
            Direction currentDirection = getDirection(histogram[borderEnd - 1], currentHeight);
            if (Direction.ASC.equals(currentDirection) &&
                    (borderEnd == histogram.length - 1 ||
                    Direction.DESC.equals(getDirection(currentHeight, histogram[borderEnd + 1])))) {
                calculateVolumeForLast(donePits, currentPits, currentVolume, currentHeight, borderEnd);
                currentPits.add(new Pit(borderEnd, currentHeight));
                currentVolume = 0;
            } else {
                currentVolume += Math.max(currentPits.getLast().getStartHeight() - currentHeight, 0);
            }

        }
        return donePits;
    }

    /**
     * Вычисляет объем ямы
     * @param currentPits       рассматриваемые ямы
     * @param donePits          полностью исследованные ямы
     * @param currentVolume     объем текущей ямы
     * @param currentHeight     высота текущей ямы
     * @param endIndex          индекс правого борта ямы
     */
    private void calculateVolumeForLast(List<Pit> donePits, Deque<Pit> currentPits, int currentVolume, int currentHeight, int endIndex) {
        Pit last = currentPits.peekLast();
        if (last.getStartHeight() > currentHeight) {
            currentVolume -= calculateVolume(last.getStartHeight(), currentHeight, endIndex, last.getStartIndex());
        }
        last.setEnd(currentHeight, endIndex);
        last.setVolume(currentVolume);
        if (last.getStartHeight() <= currentHeight) {
            donePits.add(currentPits.pollLast());
            recalculateForParents(currentPits, donePits, currentVolume, currentHeight, endIndex);
        }
    }


    /**
     * Вычисляет значения родительских ям
     * @param currentPits       рассматриваемые ямы
     * @param donePits          полностью исследованные ямы
     * @param currentVolume     объем текущей ямы
     * @param currentHeight     высота текущей ямы
     * @param endIndex          индекс правого борта ямы
     */
    private void recalculateForParents(Deque<Pit> currentPits, List<Pit> donePits, int currentVolume, int currentHeight, int endIndex) {
        Pit last = currentPits.peekLast();
        if (last == null) return;
        if (last.getStartHeight() > currentHeight) {
            last.addVolume(currentVolume + calculateVolume(currentHeight, last.getEndHeight(), endIndex, last.getStartIndex()));
        } else {
            last.addVolume(currentVolume + calculateVolume(last.getStartHeight(), last.getEndHeight(), endIndex, last.getStartIndex()));
            donePits.add(currentPits.pollLast());
            recalculateForParents(currentPits, donePits, last.getVolume(), currentHeight, endIndex);
        }
        last.setEnd(currentHeight, endIndex);
    }

    /**
     * Вычисляет объем прямоугольника
     * @param higherHeight  большая высота
     * @param lowerHeight   меньшая высота   
     * @param endIndex      индекс окончания
     * @param startIndex    индекс начала
     * @return  объем прямоугольника
     */
    private int calculateVolume(int higherHeight, int lowerHeight, int endIndex, int startIndex) {
        return (higherHeight - lowerHeight) * (endIndex - startIndex - 1);
    }

    /**
     * Находит текущее направления изменения значений
     * @param prev предыдущее значение
     * @param cur  текущее значение
     * @return  направление изменения значений
     */
    private Direction getDirection(int prev, int cur) {
        return prev > cur ? Direction.DESC : Direction.ASC;
    }

    /**
     * Находит индекс первого возможного правого борта ямы
     * @param histogram гистограма
     * @return индекс первого возможного правого борта ямы
     */
    private int getBordersEndIndex(int[] histogram) {
        int end = histogram.length - 1;
        int endBorderSize = histogram[end];
        while (end - 1 > 0 && endBorderSize <= histogram[end - 1]) {
            end--;
            endBorderSize = histogram[end];
        }
        return end;
    }

    /**
     * Находит индекс первого возможного левого борта ямы
     * @param histogram гистограма
     * @return индекс первого возможного левого борта ямы
     */
    private int getBordersStartIndex(int[] histogram) {
        int start = 0;
        int startBorderSize = histogram[start];
        while (start + 1 < histogram.length && startBorderSize <= histogram[start + 1]) {
            start++;
            startBorderSize = histogram[start];
        }
        return start;
    }
}
