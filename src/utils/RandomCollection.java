package utils;

import java.util.*;

/**
 * A LinkedList with a random iterator
 * @param <E> a generic type
 */
public class RandomCollection<E> extends LinkedList<E> {

    private Random ran;

    /**
     * Constructor
     * @param seed the random seed
     */
    public RandomCollection(long seed){
        super();
        ran = new Random(seed);
    }

    /**
     * Constructor
     * @param c a Collection
     */
    public RandomCollection(Collection<? extends E> c) {
        super(c);
        ran = new Random();
    }

    /**
     * @return a random element of the collection
     */
    public E getRandom(){
        int index = ran.nextInt(size());
        return get(index);
    }

    /**
     * @return a Random iterator
     */
    @Override
    public Iterator<E> iterator(){
        return new RandomIterator<>(new LinkedList<>(this));
    }

    /**
     * The random iterator class
     * @param <T> the type of the iterator
     */
    private class RandomIterator<T> implements Iterator<T>{

        private List<T> remains;

        /**
         * Constructor
         * @param toIter the elements to iterate
         */
        public RandomIterator(List<T> toIter){
            remains = toIter;
        }

        /**
         * @return true if there are more elements in the collection
         */
        @Override
        public boolean hasNext() {
            return remains.size() > 0;
        }

        /**
         * @return a random object of the collection
         */
        @Override
        public T next() {
            int index = ran.nextInt(remains.size());
            T ret = remains.get(index);
            remains.remove(index);
            return ret;
        }
    }
}
