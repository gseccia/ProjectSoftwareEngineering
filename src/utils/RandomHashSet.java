package utils;

import java.time.Instant;
import java.util.*;

public class RandomHashSet<E> extends HashSet<E> {

    private LinkedList<E> toIter;
    private long seed;

    public RandomHashSet() {
        super();
        this.seed = Instant.now().toEpochMilli();
    }

    public RandomHashSet(long seed){
        super();
        this.seed = seed;
    }

    public RandomHashSet(Collection<? extends E> c) {
        super(c);
    }

    public RandomHashSet(Collection<? extends E> c, long seed) {
        super(c);
        this.seed = seed;
    }

    @Override
    public boolean add(E e) {
        if(toIter == null){
            toIter = new LinkedList<>();
        }
        if(super.add(e)){
            toIter.add(e);
            return true;
        }
        return false;
    }

    @Override
    public Iterator<E> iterator(){
        return new RandomIterator<>(toIter, seed);
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
