package by.it.group351051.pavlushchenko.lesson12;

import java.util.*;

public class MySplayMap  implements NavigableMap<Integer, String>{
    private Node root;
    private int size = 0;
    private static class Node {
        Integer key;
        String value;
        Node parent;
        Node left;
        Node right;

        Node(Integer key, String value) {
            this.key = key;
            this.value = value;
        }
        private String toStringNode() {
            return key + "=" + value;
        }
        private void treeToString(StringBuilder result) {
            if (left != null) left.treeToString(result);
            result.append(this.toStringNode());
            result.append(", ");
            if (right != null) right.treeToString(result);
        }
        public String toString() {
            StringBuilder result = new StringBuilder("{");
            this.treeToString(result);
            result.setLength(result.length() - 2);
            result.append("}");
            return result.toString();
        }
    }
    @Override
    public Entry<Integer, String> lowerEntry(Integer key) { return null; }

    @Override
    public Integer lowerKey(Integer key) {
        Node n = this.lowerNode(root, key);
        if (n != null) {
            this.splayNode(n);
            return n.key;
        }
        return null;
    }

    @Override
    public Entry<Integer, String> floorEntry(Integer key) { return null; }

    @Override
    public Integer floorKey(Integer key) {
        Node n = this.floorNode(root, key);
        if (n != null) {
            this.splayNode(n);
            return n.key;
        }
        return null;
    }

    @Override
    public Entry<Integer, String> ceilingEntry(Integer key) { return null; }

    @Override
    public Integer ceilingKey(Integer key) {
        Node n = this.ceilingNode(root, key);
        if (n != null) {
            this.splayNode(n);
            return n.key;
        }
        return null;
    }

    @Override
    public Entry<Integer, String> higherEntry(Integer key) { return null; }

    @Override
    public Integer higherKey(Integer k) {
        Node n = this.higherNode(root, k);
        if (n != null) {
            this.splayNode(n);
            return n.key;
        }
        return null;
    }

    @Override
    public Entry<Integer, String> firstEntry() { return null; }

    @Override
    public Entry<Integer, String> lastEntry() { return null; }

    @Override
    public Entry<Integer, String> pollFirstEntry() { return null; }

    @Override
    public Entry<Integer, String> pollLastEntry() { return null; }

    @Override
    public NavigableMap<Integer, String> descendingMap() { return null; }

    @Override
    public NavigableSet<Integer> navigableKeySet() { return null; }

    @Override
    public NavigableSet<Integer> descendingKeySet() { return null; }

    @Override
    public NavigableMap<Integer, String> subMap(Integer fromKey, boolean fromInclusive, Integer toKey, boolean toInclusive) { return null; }

    @Override
    public NavigableMap<Integer, String> headMap(Integer toKey, boolean inclusive) { return this.headMap(root, toKey, inclusive, new MySplayMap()); }

    @Override
    public NavigableMap<Integer, String> tailMap(Integer fromKey, boolean inclusive) { return this.tailMap(root, fromKey, inclusive, new MySplayMap()); }

    @Override
    public Comparator<? super Integer> comparator() { return null; }

    @Override
    public SortedMap<Integer, String> subMap(Integer fromKey, Integer toKey) { return null; }

    @Override
    public SortedMap<Integer, String> headMap(Integer toKey) { return this.headMap(toKey, false); }

    @Override
    public SortedMap<Integer, String> tailMap(Integer fromKey) { return this.tailMap(fromKey, true); }

    @Override
    public Integer firstKey() {
        Node n = root;
        while (n.left != null) n = n.left;
        this.splayNode(n);
        return n.key;
    }

    @Override
    public Integer lastKey() {
        Node n = root;
        while (n.right != null) n = n.right;
        this.splayNode(n);
        return n.key;
    }

    @Override
    public int size() { return size; }

    @Override
    public boolean isEmpty() { return size == 0; }

    @Override
    public boolean containsKey(Object k) { return this.findNode((Integer) k) != null; }

    @Override
    public boolean containsValue(Object v) { return this.containsValue(root, v.toString()); }

    private boolean containsValue(Node n, Object v) {
        return n != null && (v.equals(n.value) || this.containsValue(n.left, v) || this.containsValue(n.right, v));
    }

    @Override
    public String get(Object k) {
        Node n = this.findNode((Integer) k);
        if (n != null) {
            this.splayNode(n);
            return n.value;
        }
        return null;
    }

    @Override
    public String put(Integer key, String value) {
        if (root == null) {
            root = new Node(key, value);
            size++;
            return null;
        }
        Node p = null;
        Node n = root;
        while (n != null) {
            p = n;
            int t = key.compareTo(n.key);
            if (t > 0) n = n.right;
            else if (t < 0) n = n.left;
            else {
                String v = n.value;
                n.value = value;
                this.splayNode(n);
                return v;
            }
        }
        Node nn = new Node(key, value);
        nn.parent = p;
        if (key.compareTo(p.key) >= 0) p.right = nn;
        else p.left = nn;
        this.splayNode(nn);
        size++;
        return null;
    }

    @Override
    public String remove(Object key) {
        Node n = this.findNode((Integer) key);
        if (n == null) return null;
        this.splayNode(n);
        if (n.left != null) {
            Node r = n.right;
            root = n.left;
            root.parent = null;
            Node l = root;
            while (l.right != null) l = l.right;
            this.splayNode(l);
            l.right = r;
            if (r != null) r.parent = l;
        } else {
            root = n.right;
            if (root != null) root.parent = null;
        }
        size--;
        return n.value;
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends String> m) {}

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public Set<Integer> keySet() { return null; }

    @Override
    public Collection<String> values() { return null; }

    @Override
    public Set<Entry<Integer, String>> entrySet() { return null; }
    @Override
    public String toString() { return root == null ? "{}" : root.toString(); }
    private void splayNode(Node n) {
        while (n.parent != null) {
            Node p = n.parent;
            Node pp = p.parent;
            if (pp == null) {
                if (n == p.left) this.rotateTreeToRight(p);
                else this.rotateTreeToLeft(p);
            } else {
                if (n == p.left && p == pp.left) {
                    this.rotateTreeToRight(pp);
                    this.rotateTreeToRight(p);
                } else if (n == p.right && p == pp.right) {
                    this.rotateTreeToLeft(pp);
                    this.rotateTreeToLeft(p);
                } else if (n == p.right && p == pp.left) {
                    this.rotateTreeToLeft(p);
                    this.rotateTreeToRight(pp);
                } else {
                    this.rotateTreeToRight(p);
                    this.rotateTreeToLeft(pp);
                }
            }
        }
        root = n;
    }
    private void rotateTreeToLeft(Node n) {
        Node r = n.right;
        n.right = r.left;
        if (r.left != null) r.left.parent = n;
        r.parent = n.parent;
        if (n.parent == null) root = r;
        else if (n == n.parent.left) n.parent.left = r;
        else n.parent.right = r;
        r.left = n;
        n.parent = r;
    }

    private void rotateTreeToRight(Node n) {
        Node l = n.left;
        n.left = l.right;
        if (l.right != null) l.right.parent = n;
        l.parent = n.parent;
        if (n.parent == null) root = l;
        else if (n == n.parent.right) n.parent.right = l;
        else n.parent.left = l;
        l.right = n;
        n.parent = l;
    }
    private Node findNode(Integer k) {
        Node n = root;
        while (n != null) {
            int t = k.compareTo(n.key);
            if (t < 0) n = n.left;
            else if (t > 0) n = n.right;
            else return n;
        }
        return null;
    }

    private MySplayMap headMap(Node n, Integer tk, boolean inclusive, MySplayMap msp) {
        if (n == null) return msp;
        if (n.key.compareTo(tk) < 0 || (n.key.equals(tk) && inclusive)) {
            msp.put(n.key, n.value);
            this.headMap(n.right, tk, inclusive, msp);
        }
        this.headMap(n.left, tk, inclusive, msp);
        return msp;
    }
    private MySplayMap tailMap(Node n, Integer fk, boolean inclusive, MySplayMap msp) {
        if (n == null) return msp;
        if (n.key.compareTo(fk) > 0 || (n.key.equals(fk) && inclusive)) {
            msp.put(n.key, n.value);
            this.tailMap(n.left, fk, inclusive, msp);
        }
        this.tailMap(n.right, fk, inclusive, msp);
        return msp;
    }
    private Node lowerNode(Node n, Integer k) {
        if (n == null) return null;
        else if (n.key.compareTo(k) < 0) {
            Node r = this.lowerNode(n.right, k);
            return r != null ? r : n;
        } else return this.lowerNode(n.left, k);
    }
    private Node floorNode(Node n, Integer k) {
        if (n == null) return null;
        int t = n.key.compareTo(k);
        if (t == 0) return n;
        else if (t < 0) {
            Node r = floorNode(n.right, k);
            return r != null ? r : n;
        }
        else return floorNode(n.left, k);
    }
    private Node ceilingNode(Node n, Integer k) {
        if (n == null) return null;
        int t = n.key.compareTo(k);
        if (t == 0) return n;
        else if (t > 0) {
            Node l = this.ceilingNode(n.left, k);
            return l != null ? l : n;
        } else return this.ceilingNode(n.right, k);
    }
    private Node higherNode(Node n, Integer k) {
        if (n == null) return null;
        else if (n.key.compareTo(k) > 0) {
            Node l = this.higherNode(n.left, k);
            return l != null ? l : n;
        } else return this.higherNode(n.right, k);
    }

}
