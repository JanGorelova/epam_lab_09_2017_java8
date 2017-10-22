package part1.exercise;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.IntConsumer;

public class RectangleSpliterator extends Spliterators.AbstractIntSpliterator {
    private final int[][] array;
    private Coordinates startCoordinates;
    private final Coordinates endCoordinates;

    public RectangleSpliterator(int[][] array, Coordinates startCoordinates, Coordinates endCoordinates) {
        super(Coordinates.cellAmount(startCoordinates, endCoordinates), Spliterator.IMMUTABLE
                | Spliterator.ORDERED
                | Spliterator.SIZED
                | Spliterator.SUBSIZED
                | Spliterator.NONNULL);
        this.array = array;
        this.startCoordinates = startCoordinates;
        this.endCoordinates = endCoordinates;
    }

    public RectangleSpliterator(int[][] array) {
        this(array, new Coordinates(0, 0), new Coordinates(array[0].length, array.length));
    }

    private static long checkArrayAndCalcEstimatedSize(int[][] array) {
        // TODO
        return Coordinates.cellAmount(new Coordinates(0, 0), new Coordinates(array[0].length, array.length));
    }

    @Override
    public OfInt trySplit() {
        // TODO
        int cellAmount = (int) Coordinates.cellAmount(this.startCoordinates, this.endCoordinates);
        if (cellAmount < 2) {
            return null;
        }
        int middle = cellAmount /2;
        Coordinates newEndCoordinates = getNewEndCoordinates(startCoordinates,middle);

        RectangleSpliterator newSpliterator = new RectangleSpliterator(array,startCoordinates,newEndCoordinates);

        startCoordinates = getNewEndCoordinates(startCoordinates,middle);

        return newSpliterator;
    }

    private Coordinates getNewEndCoordinates(Coordinates startCoordinates,int cellAmount) {
        int newXcoordinate;
        int newYcoordinate;

        newYcoordinate = cellAmount/array[0].length;
        newXcoordinate = cellAmount%array[0].length - 1;

        return new Coordinates(newXcoordinate,newYcoordinate);
    }

    @Override
    public long estimateSize() {
        // TODO
        return Coordinates.cellAmount(this.startCoordinates, this.endCoordinates);
    }

    @Override
    public boolean tryAdvance(IntConsumer action) {
        // TODO
        if (coordinatesInBoundsOfArray(startCoordinates) || coordinatesInBoundsOfArray(endCoordinates)) {
            throw new RuntimeException("Invalid coordinates");
        }
        if (checkStartEndCoordinates(startCoordinates, endCoordinates)) {
            int value = array[startCoordinates.getxCoordinate()][startCoordinates.getyCoordinate()];
            action.accept(value);
            if (startCoordinates.getxCoordinate() == array[0].length - 1) {
                startCoordinates = new Coordinates(0, startCoordinates.getyCoordinate() + 1);
            } else {
                startCoordinates = new Coordinates(startCoordinates.getxCoordinate() + 1, startCoordinates.getyCoordinate());
            }
            return true;
        }
        return false;
    }

    private boolean coordinatesInBoundsOfArray(Coordinates coordinates) {
        return (xyCooordinateInBoundOfArray(coordinates.getxCoordinate(), array[0].length) &&
                xyCooordinateInBoundOfArray(coordinates.getyCoordinate(), array.length));
    }

    private boolean xyCooordinateInBoundOfArray(int coordinate, int bound) {
        return (coordinate >= 0 && coordinate < bound);
    }

    private boolean checkStartEndCoordinates(Coordinates startCoordinates, Coordinates endCoordinates) {
        return !((startCoordinates.getxCoordinate() == endCoordinates.getxCoordinate()
                && startCoordinates.getyCoordinate() > endCoordinates.getyCoordinate()) ||
                (startCoordinates.getyCoordinate() == endCoordinates.getyCoordinate()
                        && startCoordinates.getxCoordinate() > endCoordinates.getxCoordinate()));
    }

}


class A {

    protected String val;

    A() {
        setVal();
    }

    public void setVal() {
        val = "A";
    }
}

class B extends A {

    @Override
    public void setVal() {
        val = "B";
    }

    public static void main(String[] args) {
        System.out.println(new B().val);

    }
}