package utils;

import java.time.Instant;
import java.util.*;

public class RandomCollection<E> extends LinkedList<E> {

    private long seed;

    public RandomCollection() {
        super();
        this.seed = Instant.now().toEpochMilli();
    }

    public RandomCollection(long seed){
        super();
        this.seed = seed;
    }

    public RandomCollection(Collection<? extends E> c) {
        super(c);
    }

    public RandomCollection(Collection<? extends E> c, long seed) {
        super(c);
        this.seed = seed;
    }

    @Override
    public Iterator<E> iterator(){
        return new RandomIterator<>(new LinkedList<>(this), seed);
    }

    private class RandomIterator<T> implements Iterator<T>{

        private List<T> remains;
        private long seed;

        public RandomIterator(List<T> toIter, long seed){
            remains = toIter;
            this.seed = seed;
        }

        @Override
        public boolean hasNext() {
            return remains.size() > 0;
        }

        @Override
        public T next() {
            int index = new Random(seed).nextInt(remains.size());
            T ret = remains.get(index);
            remains.remove(index);
            return ret;
        }
    }
}
