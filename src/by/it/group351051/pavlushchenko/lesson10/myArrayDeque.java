package by.it.group351051.pavlushchenko.lesson10;

import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;

@SuppressWarnings("unchecked")
public class myArrayDeque<E> implements Deque<E> {
    private E[] elements;
    private int head = 0;
    private int tail = 0;
    private int size = 0;
    private int capacity;

    public myArrayDeque() {
        this(16); // начальная емкость
    }

    public myArrayDeque(int initialCapacity) {
        capacity = initialCapacity;
        elements = (E[]) new Object[capacity];
    }

    private void ensureCapacity() {
        if (size == capacity) {
            int newCapacity = capacity * 2;
            E[] newElements = (E[]) new Object[newCapacity];
            for (int i = 0; i < size; i++) {
                newElements[i] = elements[(head + i) % capacity];
            }
            elements = newElements;
            head = 0;
            tail = size;
            capacity = newCapacity;
        }
    }

    @Override
    public void addFirst(E e) {
        if (e == null) throw new NullPointerException();
        ensureCapacity();
        head = (head - 1 + capacity) % capacity;
        elements[head] = e;
        size++;
    }

    @Override
    public void addLast(E e) {
        if (e == null) throw new NullPointerException();
        ensureCapacity();
        elements[tail] = e;
        tail = (tail + 1) % capacity;
        size++;
    }

    @Override
    public E removeFirst() {
        if (isEmpty()) throw new NoSuchElementException();
        E value = elements[head];
        elements[head] = null;
        head = (head + 1) % capacity;
        size--;
        return value;
    }

    @Override
    public E removeLast() {
        if (isEmpty()) throw new NoSuchElementException();
        tail = (tail - 1 + capacity) % capacity;
        E value = elements[tail];
        elements[tail] = null;
        size--;
        return value;
    }

    @Override
    public E getFirst() {
        if (isEmpty()) throw new NoSuchElementException();
        return elements[head];
    }

    @Override
    public E getLast() {
        if (isEmpty()) throw new NoSuchElementException();
        return elements[(tail - 1 + capacity) % capacity];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    // Остальные методы интерфейса Deque можно реализовать по необходимости.
    // Ниже — заглушки для компиляции.

    @Override public boolean offerFirst(E e) { addFirst(e); return true; }
    @Override public boolean offerLast(E e) { addLast(e); return true; }
    @Override public E pollFirst() { return isEmpty() ? null : removeFirst(); }
    @Override public E pollLast() { return isEmpty() ? null : removeLast(); }
    @Override public E peekFirst() { return isEmpty() ? null : getFirst(); }
    @Override public E peekLast() { return isEmpty() ? null : getLast(); }

    @Override public boolean contains(Object o) {
        for (int i = 0; i < size; i++) {
            E e = elements[(head + i) % capacity];
            if (e == null && o == null) return true;
            if (e != null && e.equals(o)) return true;
        }
        return false;
    }

    @Override public boolean removeFirstOccurrence(Object o) {
        for (int i = 0; i < size; i++) {
            int index = (head + i) % capacity;
            E e = elements[index];
            if ((e == null && o == null) || (e != null && e.equals(o))) {
                // Сдвигаем элементы
                for (int j = i; j < size - 1; j++) {
                    int from = (head + j + 1) % capacity;
                    int to = (head + j) % capacity;
                    elements[to] = elements[from];
                }
                tail = (tail - 1 + capacity) % capacity;
                elements[tail] = null;
                size--;
                return true;
            }
        }
        return false;
    }

    @Override public boolean removeLastOccurrence(Object o) {
        for (int i = size - 1; i >= 0; i--) {
            int index = (head + i) % capacity;
            E e = elements[index];
            if ((e == null && o == null) || (e != null && e.equals(o))) {
                for (int j = i; j < size - 1; j++) {
                    int from = (head + j + 1) % capacity;
                    int to = (head + j) % capacity;
                    elements[to] = elements[from];
                }
                tail = (tail - 1 + capacity) % capacity;
                elements[tail] = null;
                size--;
                return true;
            }
        }
        return false;
    }

    @Override public Iterator<E> iterator() {
        return new Iterator<E>() {
            int index = 0;
            public boolean hasNext() { return index < size; }
            public E next() {
                if (!hasNext()) throw new NoSuchElementException();
                E e = elements[(head + index) % capacity];
                index++;
                return e;
            }
        };
    }

    @Override public Iterator<E> descendingIterator() {
        return new Iterator<E>() {
            int index = size - 1;
            public boolean hasNext() { return index >= 0; }
            public E next() {
                if (!hasNext()) throw new NoSuchElementException();
                E e = elements[(head + index) % capacity];
                index--;
                return e;
            }
        };
    }

    // Методы из Queue и Collection, которые не реализованы:
    @Override public boolean add(E e) { addLast(e); return true; }
    @Override public boolean offer(E e) { return offerLast(e); }
    @Override public E remove() { return removeFirst(); }
    @Override public E poll() { return pollFirst(); }
    @Override public E element() { return getFirst(); }
    @Override public E peek() { return peekFirst(); }

    @Override public boolean addAll(java.util.Collection<? extends E> c) { throw new UnsupportedOperationException(); }

    @Override
    public void push(E e) {

    }

    @Override
    public E pop() {
        return null;
    }

    @Override public boolean remove(Object o) { return removeFirstOccurrence(o); }
    @Override public boolean containsAll(java.util.Collection<?> c) { throw new UnsupportedOperationException(); }
    @Override public boolean removeAll(java.util.Collection<?> c) { throw new UnsupportedOperationException(); }
    @Override public boolean retainAll(java.util.Collection<?> c) { throw new UnsupportedOperationException(); }
    @Override public void clear() {
        while (!isEmpty()) removeFirst();
    }
    @Override public Object[] toArray() { throw new UnsupportedOperationException(); }
    @Override public <T> T[] toArray(T[] a) { throw new UnsupportedOperationException(); }
}



