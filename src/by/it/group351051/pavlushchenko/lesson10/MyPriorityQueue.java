package by.it.group351051.pavlushchenko.lesson10;


import java.util.Collection;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Queue;

    public class MyPriorityQueue<E extends Comparable<E>> implements Queue<E>, Iterable<E> {
        private E[] heap;
        private int size;
        private static final int DEFAULT_CAPACITY = 10;

        @SuppressWarnings("unchecked")
        public MyPriorityQueue() {
            heap = (E[]) new Comparable[DEFAULT_CAPACITY];
            size = 0;
        }

        private void ensureCapacity() {
            if (size == heap.length) {
                heap = Arrays.copyOf(heap, heap.length * 2);
            }
        }

        private void siftUp(int k) {
            E x = heap[k];
            while (k > 0 && x.compareTo(heap[(k - 1) / 2]) < 0) {
                heap[k] = heap[(k - 1) / 2];
                k = (k - 1) / 2;
            }
            heap[k] = x;
        }

        private void siftDown(int k) {
            E x = heap[k];
            int half = size / 2;
            while (k < half) {
                int child = 2 * k + 1;
                E minChild = heap[child];
                int right = child + 1;
                if (right < size && minChild.compareTo(heap[right]) > 0) {
                    child = right;
                    minChild = heap[right];
                }
                if (x.compareTo(minChild) <= 0) {
                    break;
                }
                heap[k] = minChild;
                k = child;
            }
            heap[k] = x;
        }

        public boolean add(E element) {
            ensureCapacity();
            heap[size] = element;
            siftUp(size);
            size++;
            return true;
        }

        public E remove() {
            if (size == 0) {
                throw new IllegalStateException("Queue is empty");
            }
            E result = heap[0];
            size--;
            heap[0] = heap[size];
            heap[size] = null;
            siftDown(0);
            return result;
        }

        @Override
        public boolean contains(Object element) {
            for (int i = 0; i < size; i++) {
                if (heap[i].equals(element)) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public String toString() {
            return Arrays.toString(Arrays.copyOf(heap, size));
        }

        @Override
        public int size() {
            return size;
        }

        @Override
        public void clear() {
            Arrays.fill(heap, null);
            size = 0;
        }

        @Override
        public boolean isEmpty() {
            return size == 0;
        }

        public boolean offer(E element) {
            return add(element);
        }

        public E poll() {
            return isEmpty() ? null : remove();
        }

        public E peek() {
            return isEmpty() ? null : heap[0];
        }

        public E element() {
            if (isEmpty()) {
                throw new IllegalStateException("Queue is empty");
            }
            return heap[0];
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            for (Object element : c) {
                if (!contains(element)) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public boolean addAll(Collection<? extends E> c) {
            boolean modified = false;
            for (E element : c) {
                modified |= add(element);
            }
            return modified;
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            boolean modified = false;
            E[] temp = (E[]) new Comparable[size];
            int newSize = 0;

            for (int i = 0; i < size; i++) {
                if (!c.contains(heap[i])) {
                    temp[newSize++] = heap[i];
                } else {
                    modified = true;
                }
            }

            heap = temp;
            size = newSize;

            for (int i = (size / 2) - 1; i >= 0; i--) {
                siftDown(i);
            }

            return modified;
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            boolean modified = false;
            E[] temp = (E[]) new Comparable[size];
            int newSize = 0;

            for (int i = 0; i < size; i++) {
                if (c.contains(heap[i])) {
                    temp[newSize++] = heap[i];
                } else {
                    modified = true;
                }
            }

            heap = temp;
            size = newSize;

            for (int i = (size / 2) - 1; i >= 0; i--) {
                siftDown(i);
            }

            return modified;
        }

        @Override
        public Iterator<E> iterator() {
            return new Iterator<E>() {
                private int currentIndex = 0;
                private boolean canRemove = false;

                @Override
                public boolean hasNext() {
                    return currentIndex < size;
                }

                @Override
                public E next() {
                    canRemove = true;
                    return heap[currentIndex++];
                }

                @Override
                public void remove() {
                    if (!canRemove) {
                        throw new IllegalStateException("You must call next() before calling remove()");
                    }
                    canRemove = false;
                    MyPriorityQueue.this.remove(heap[currentIndex - 1]);
                }
            };
        }

        @Override
        public Object[] toArray() {
            return Arrays.copyOf(heap, size);
        }

        @Override
        public <T> T[] toArray(T[] a) {
            if (a.length < size) {
                return (T[]) Arrays.copyOf(heap, size, a.getClass());
            }
            System.arraycopy(heap, 0, a, 0, size);
            if (a.length > size) {
                a[size] = null;
            }
            return a;
        }

        @Override
        public boolean remove(Object o) {
            for (int i = 0; i < size; i++) {
                if (o.equals(heap[i])) {
                    size--;
                    heap[i] = heap[size];
                    heap[size] = null;
                    siftDown(i);
                    return true;
                }
            }
            return false;
        }

    }