package by.it.group351051.pavlushchenko.lesson12;

import java.util.Map;
import java.util.Objects;

public class MyAvlMap  implements Map <Integer, String>{
    private class Node {
        Integer key;
        String value;
        Node left, right;
        int height;

        Node(Integer key, String value) {
            this.key = key;
            this.value = value;
            this.height = 1;
        }
    }

    private Node root;
    private int size;

    public MyAvlMap() {
        root = null;
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public String get(Object key) {
        if (!(key instanceof Integer)) {
            throw new ClassCastException("Key must be an Integer");
        }
        Node node = getNode(root, (Integer) key);
        return node == null ? null : node.value;
    }

    private Node getNode(Node node, Integer key) {
        if (node == null) {
            return null;
        }
        if (key.equals(node.key)) {
            return node;
        }
        return key < node.key ? getNode(node.left, key) : getNode(node.right, key);
    }

    @Override
    public String put(Integer key, String value) {
        String previousValue = get(key);
        root = putNode(root, key, value);
        return previousValue;
    }

    private Node putNode(Node node, Integer key, String value) {
        if (node == null) {
            size++;
            return new Node(key, value);
        }
        if (key.equals(node.key)) {
            node.value = value;
            return node;
        } else if (key < node.key) {
            node.left = putNode(node.left, key, value);
        } else {
            node.right = putNode(node.right, key, value);
        }

        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
        return balance(node);
    }

    @Override
    public String remove(Object key) {
        if (!(key instanceof Integer)) {
            throw new ClassCastException("Key must be an Integer");
        }
        String removedValue = get((Integer) key);
        if (removedValue != null) {
            root = removeNode(root, (Integer) key);
            return removedValue;
        }
        return null;
    }

    private Node removeNode(Node node, Integer key) {
        if (node == null) {
            return null;
        }

        if (key < node.key) {
            node.left = removeNode(node.left, key);
        } else if (key > node.key) {
            node.right = removeNode(node.right, key);
        } else {
            if (node.left == null) {
                size--;
                return node.right;
            } else if (node.right == null) {
                size--;
                return node.left;
            }

            Node minNode = getMin(node.right);
            node.key = minNode.key;
            node.value = minNode.value;
            node.right = removeNode(node.right, minNode.key);
        }

        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
        return balance(node);
    }

    private Node getMin(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    @Override
    public boolean containsKey(Object key) {
        if (!(key instanceof Integer)) {
            return false;
        }
        return getNode(root, (Integer) key) != null;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("{");
        toStringHelper(root, result);
        if (result.length() > 1) {
            result.setLength(result.length() - 2);
        }
        result.append("}");
        return result.toString();
    }

    private void toStringHelper(Node node, StringBuilder result) {
        if (node != null) {
            toStringHelper(node.left, result);
            result.append(node.key).append("=").append(node.value).append(", ");
            toStringHelper(node.right, result);
        }
    }

    private int getHeight(Node node) {
        return node == null ? 0 : node.height;
    }

    private int getBalance(Node node) {
        return node == null ? 0 : getHeight(node.left) - getHeight(node.right);
    }

    private Node balance(Node node) {
        int balanceFactor = getBalance(node);

        if (balanceFactor > 1) {
            if (getBalance(node.left) < 0) {
                node.left = rotateLeft(node.left);
            }
            return rotateRight(node);
        }

        if (balanceFactor < -1) {
            if (getBalance(node.right) > 0) {
                node.right = rotateRight(node.right);
            }
            return rotateLeft(node);
        }

        return node;
    }

    private Node rotateRight(Node node) {
        Node newRoot = node.left;
        node.left = newRoot.right;
        newRoot.right = node;
        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
        newRoot.height = 1 + Math.max(getHeight(newRoot.left), getHeight(newRoot.right));
        return newRoot;
    }

    private Node rotateLeft(Node node) {
        Node newRoot = node.right;
        node.right = newRoot.left;
        newRoot.left = node;
        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
        newRoot.height = 1 + Math.max(getHeight(newRoot.left), getHeight(newRoot.right));
        return newRoot;
    }

    @Override
    public boolean containsValue(Object value) {
        return containsValue(root, value);
    }

    private boolean containsValue(Node node, Object value) {
        if (node == null) {
            return false;
        }
        if (Objects.equals(node.value, value)) {
            return true;
        }
        return containsValue(node.left, value) || containsValue(node.right, value);
    }

    @Override
    public java.util.Set<Map.Entry<Integer, String>> entrySet() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public java.util.Set<Integer> keySet() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public java.util.Collection<String> values() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends String> m) {
        for (Map.Entry<? extends Integer, ? extends String> entry : m.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }


}
