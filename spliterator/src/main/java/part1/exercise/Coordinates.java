package part1.exercise;

public class Coordinates {
    private final int xCoordinate;
    private final int yCoordinate;

    public Coordinates(int xCoordinate, int yCoordinate) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    public int getxCoordinate() {
        return xCoordinate;
    }

    public int getyCoordinate() {
        return yCoordinate;
    }

    public static long cellAmount(final Coordinates start, final Coordinates end) {
        return (long) (end.xCoordinate - start.xCoordinate + 1)*(end.yCoordinate - start.yCoordinate + 1);

    }
}
