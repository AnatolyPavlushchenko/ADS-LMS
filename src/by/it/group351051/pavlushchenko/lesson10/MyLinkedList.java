package by.it.group351051.pavlushchenko.lesson10;

import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Collection;

    public class MyLinkedList<E> implements Deque<E> {
        private Node<E> first;
        private Node<E> last;
        private int size;

        public MyLinkedList() {
            first = null;
            last = null;
            size = 0;
        }

        private static class Node<E> {
            E item;
            Node<E> next;
            Node<E> prev;

            Node(Node<E> prev, E item, Node<E> next) {
                this.item = item;
                this.prev = prev;
                this.next = next;
            }
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            Node<E> current = first;
            while (current != null) {
                sb.append(current.item);
                if (current.next != null) {
                    sb.append(", ");
                }
                current = current.next;
            }
            sb.append("]");
            return sb.toString();
        }

        @Override
        public boolean add(E element) {
            addLast(element);
            return true;
        }

        public E remove(int index) {
            if (index < 0 || index >= size) {
                throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
            }
            Node<E> current = first;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            return unlink(current);
        }

        public boolean remove(Object element) {
            Node<E> current = first;
            while (current != null) {
                if (element == null ? current.item == null : element.equals(current.item)) {
                    unlink(current);
                    return true;
                }
                current = current.next;
            }
            return false;
        }

        @Override
        public int size() {
            return size;
        }

        @Override
        public void addFirst(E element) {
            Node<E> newNode = new Node<>(null, element, first);
            if (first == null) {
                last = newNode;
            } else {
                first.prev = newNode;
            }
            first = newNode;
            size++;
        }

        @Override
        public void addLast(E element) {
            Node<E> newNode = new Node<>(last, element, null);
            if (last == null) {
                first = newNode;
            } else {
                last.next = newNode;
            }
            last = newNode;
            size++;
        }

        @Override
        public E element() {
            if (first == null) {
                throw new NoSuchElementException();
            }
            return first.item;
        }

        @Override
        public E getFirst() {
            if (first == null) {
                throw new NoSuchElementException();
            }
            return first.item;
        }

        @Override
        public E getLast() {
            if (last == null) {
                throw new NoSuchElementException();
            }
            return last.item;
        }

        @Override
        public E poll() {
            return pollFirst();
        }

        @Override
        public E pollFirst() {
            if (first == null) {
                return null;
            }
            E item = first.item;
            first = first.next;
            if (first == null) {
                last = null;
            } else {
                first.prev = null;
            }
            size--;
            return item;
        }

        @Override
        public E pollLast() {
            if (last == null) {
                return null;
            }
            E item = last.item;
            last = last.prev;
            if (last == null) {
                first = null;
            } else {
                last.next = null;
            }
            size--;
            return item;
        }

        private E unlink(Node<E> node) {
            E item = node.item;
            Node<E> prev = node.prev;
            Node<E> next = node.next;

            if (prev == null) {
                first = next;
            } else {
                prev.next = next;
            }

            if (next == null) {
                last = prev;
            } else {
                next.prev = prev;
            }

            node.prev = null;
            node.next = null;
            size--;
            return item;
        }

        // Заглушки для остальных методов Deque
        @Override public boolean offer(E e) { return add(e); }
        @Override public E remove() { return pollFirst(); }
        @Override public E peek() { return first == null ? null : first.item; }
        @Override public boolean offerFirst(E e) { addFirst(e); return true; }
        @Override public boolean offerLast(E e) { addLast(e); return true; }
        @Override public E removeFirst() { return pollFirst(); }
        @Override public E removeLast() { return pollLast(); }
        @Override public E peekFirst() { return peek(); }
        @Override public E peekLast() { return last == null ? null : last.item; }
        @Override public boolean removeFirstOccurrence(Object o) { return remove(o); }
        @Override public boolean removeLastOccurrence(Object o) { return false; }
        @Override public void push(E e) { addFirst(e); }
        @Override public E pop() { return pollFirst(); }
        @Override public boolean contains(Object o) { return false; }
        @Override public Iterator<E> iterator() { return null; }
        @Override public Iterator<E> descendingIterator() { return null; }
        @Override public boolean isEmpty() { return size == 0; }
        @Override public Object[] toArray() { return new Object[0]; }
        @Override public <T> T[] toArray(T[] a) { return null; }
        @Override public boolean containsAll(Collection<?> c) { return false; }
        @Override public boolean addAll(Collection<? extends E> c) { return false; }
        @Override public boolean removeAll(Collection<?> c) { return false; }
        @Override public boolean retainAll(Collection<?> c) { return false; }
        @Override public void clear() { first = null; last = null; size = 0; }
}
