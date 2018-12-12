package utils;

import java.util.*;

public class RandomCollection<E> extends LinkedList<E> {

    private Random ran;

    public RandomCollection() {
        super();
        ran = new Random();
    }

    public RandomCollection(long seed){
        super();
        ran = new Random(seed);
    }

    public RandomCollection(Collection<? extends E> c) {
        super(c);
        ran = new Random();
    }

    public RandomCollection(Collection<? extends E> c, long seed) {
        super(c);
        ran = new Random(seed);
    }

    public E getRandom(){
        int index = ran.nextInt(size());
        return get(index);
    }

    @Override
    public Iterator<E> iterator(){
        return new RandomIterator<>(new LinkedList<>(this));
    }

    private class RandomIterator<T> implements Iterator<T>{

        private List<T> remains;

        public RandomIterator(List<T> toIter){
            remains = toIter;
        }

        @Override
        public boolean hasNext() {
            return remains.size() > 0;
        }

        @Override
        public T next() {
            int index = ran.nextInt(remains.size());
            T ret = remains.get(index);
            remains.remove(index);
            return ret;
        }
    }
}
