public class FcaiPair {
    private int first;
    private Process second;
    FcaiPair(int first, Process second) {
        this.first = first;
        this.second = second;
    }
    public int getFirst() {
        return first;
    }
    public Process getSecond() {
        return second;
    }
    public void setFirst(int first) {
        this.first = first;
    }
    public void setSecond(Process second) {
        this.second = second;
    }
}
