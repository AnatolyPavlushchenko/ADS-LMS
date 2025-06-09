package by.it.group351051.pavlushchenko.lesson11;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

    public class MyLinkedHashSet<E> implements Set<E> {
        private static final int INITIAL_CAPACITY = 16;
        private Node<E>[] table;
        private Node<E> head;
        private Node<E> tail;
        private int size;

        public MyLinkedHashSet() {
            table = new Node[INITIAL_CAPACITY];
            size = 0;
            head = null;
            tail = null;
        }

        private static class Node<E> {
            E value;
            Node<E> next;
            Node<E> prev;

            Node(E value) {
                this.value = value;
                this.next = null;
                this.prev = null;
            }
        }

        @Override
        public int size() {
            return size;
        }

        @Override
        public void clear() {
            table = new Node[INITIAL_CAPACITY];
            head = null;
            tail = null;
            size = 0;
        }

        @Override
        public boolean isEmpty() {
            return size == 0;
        }

        @Override
        public boolean contains(Object o) {
            int index = getIndex(o);
            Node<E> current = table[index];

            while (current != null) {
                if (Objects.equals(current.value, o)) {
                    return true;
                }
                current = current.next;
            }
            return false;
        }

        @Override
        public Iterator<E> iterator() {
            return new Iterator<E>() {
                Node<E> current = head;

                @Override
                public boolean hasNext() {
                    return current != null;
                }

                @Override
                public E next() {
                    E value = current.value;
                    current = current.next;
                    return value;
                }
            };
        }

        @Override
        public Object[] toArray() {
            Object[] result = new Object[size];
            Node<E> current = head;
            int index = 0;
            while (current != null) {
                result[index++] = current.value;
                current = current.next;
            }
            return result;
        }

        @Override
        public <T> T[] toArray(T[] a) {
            if (a.length < size) {
                a = (T[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), size);
            }
            Node<E> current = head;
            int index = 0;
            while (current != null) {
                a[index++] = (T) current.value;
                current = current.next;
            }
            return a;
        }

        @Override
        public boolean add(E value) {
            if (contains(value)) {
                return false;
            }

            int index = getIndex(value);
            Node<E> newNode = new Node<>(value);

            // Добавляем в хэш-таблицу
            if (table[index] == null) {
                table[index] = newNode;
            } else {
                Node<E> current = table[index];
                while (current.next != null) {
                    current = current.next;
                }
                current.next = newNode;
            }
            if (tail == null) {
                head = tail = newNode;
            } else {
                tail.next = newNode;
                newNode.prev = tail;
                tail = newNode;
            }

            size++;
            return true;
        }

        @Override
        public boolean remove(Object o) {
            int index = getIndex(o);
            Node<E> current = table[index];
            Node<E> previous = null;

            while (current != null) {
                if (Objects.equals(current.value, o)) {
                    if (previous == null) {
                        table[index] = current.next;
                    } else {
                        previous.next = current.next;
                    }
                    if (current.prev != null) {
                        current.prev.next = current.next;
                    } else {
                        head = current.next;
                    }

                    if (current.next != null) {
                        current.next.prev = current.prev;
                    } else {
                        tail = current.prev;
                    }

                    size--;
                    return true;
                }
                previous = current;
                current = current.next;
            }
            return false;
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            for (Object o : c) {
                if (!contains(o)) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public boolean addAll(Collection<? extends E> c) {
            boolean modified = false;
            for (E value : c) {
                if (add(value)) {
                    modified = true;
                }
            }
            return modified;
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            boolean modified = false;
            Node<E> current = head;

            while (current != null) {
                if (!c.contains(current.value)) {
                    Node<E> toRemove = current;
                    current = current.next;

                    remove(toRemove.value);
                    modified = true;
                } else {
                    current = current.next;
                }
            }
            return modified;
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            boolean modified = false;
            for (Object o : c) {
                if (remove(o)) {
                    modified = true;
                }
            }
            return modified;
        }

        private int getIndex(Object value) {
            return Math.abs(Objects.hashCode(value)) % table.length;
        }

        @Override
        public String toString() {
            StringBuilder result = new StringBuilder("[");
            Node<E> current = head;
            while (current != null) {
                result.append(current.value).append(", ");
                current = current.next;
            }
            if (result.length() > 1) {
                result.setLength(result.length() - 2);
            }
            result.append("]");
            return result.toString();
        }
}
