package part2.exercise;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;

public class ZipWithIndexDoubleSpliterator extends Spliterators.AbstractSpliterator<IndexedDoublePair> {
    private final OfDouble inner;
    private int currentIndex;


    public ZipWithIndexDoubleSpliterator(OfDouble inner) {
        this(0, inner);
    }

    private ZipWithIndexDoubleSpliterator(int firstIndex, OfDouble inner) {
        super(inner.estimateSize(), inner.characteristics());
        currentIndex = firstIndex;
        this.inner = inner;
    }

    @Override
    public int characteristics() {
        return inner.characteristics() & (SIZED | SUBSIZED | CONCURRENT | IMMUTABLE | ORDERED);
    }

    @Override
    public boolean tryAdvance(Consumer<? super IndexedDoublePair> action) {
        // TODO
        if (currentIndex < inner.estimateSize()) {
            Boolean done = inner.tryAdvance((double value) -> action.accept(new IndexedDoublePair(currentIndex, value)));

            if (done) {
                currentIndex++;
            }
            return true;
        } else return false;
    }

    @Override
    public void forEachRemaining(Consumer<? super IndexedDoublePair> action) {
        // TODO
        inner.forEachRemaining((double value) -> new IndexedDoublePair(currentIndex, value));
        currentIndex = (int) inner.estimateSize();
    }

    @Override
    public Spliterator<IndexedDoublePair> trySplit() {
        OfDouble splited;
        Spliterator<IndexedDoublePair> newSpliterator;
        int length = (int) inner.estimateSize() - currentIndex;

        if (length < 2) {
            return null;
        }

        if (inner.hasCharacteristics(SUBSIZED)) {
            splited = inner.trySplit();
            newSpliterator = new ZipWithIndexDoubleSpliterator(currentIndex, splited);
            currentIndex += splited.estimateSize();
        } else {
            newSpliterator = super.trySplit();
        }
        // TODO
        // if (inner.hasCharacteristics(???)) {
        //   use inner.trySplit
        // } else

        return newSpliterator;
    }

    @Override
    public long estimateSize() {
        return inner.estimateSize();
    }
}
