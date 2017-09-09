/**
 * @author Aydar Farrakhov
 * @since 09.09.17
 */
public class Pit {

    /**
     * Индекс начала ямы
     */
    private int startIndex;
    /**
     * Высота начала ямы
     */
    private int startHeight;
    /**
     * Индекс окончания ямы
     */
    private int endIndex;
    /**
     * Высота окончания ямы
     */
    private int endHeight;
    /**
     * Объем
     */
    private int volume;

    public Pit(int startIndex, int startHeight) {
        this.startIndex = startIndex;
        this.startHeight = startHeight;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getStartHeight() {
        return startHeight;
    }

    public void setStartHeight(int startHeight) {
        this.startHeight = startHeight;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }

    public int getEndHeight() {
        return endHeight;
    }

    public void setEndHeight(int endHeight) {
        this.endHeight = endHeight;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    @Override
    public String toString() {
        return "Pit{" +
                "startIndex=" + startIndex +
                ", endIndex=" + endIndex +
                ", volume=" + volume +
                '}';
    }

    public void addVolume(int newVolume) {
        this.volume += newVolume;
    }

    public void setEnd(int endHeight, int endIndex) {
        this.endHeight = endHeight;
        this.endIndex = endIndex;
    }
}
