public class Pair {
    private int first;
    private int second;
    private int id;
    Pair(int first, int second, int id) {
        this.first = first;
        this.second = second;
        this.id = id;
    }
    public int getFirst() {
        return first;
    }
    public int getSecond() {
        return second;
    }
    public void setFirst(int first) {
        this.first = first;
    }
    public void setSecond(int second) {
        this.second = second;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
}
