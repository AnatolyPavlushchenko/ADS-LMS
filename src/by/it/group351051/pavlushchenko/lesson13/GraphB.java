package by.it.group351051.pavlushchenko.lesson13;

import java.util.*;

public class GraphB {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine(); // Пример: 1 -> 2, 1 -> 3, 2 -> 3
        scanner.close();

        // Построение графа
        Map<String, List<String>> graph = new HashMap<>();
        Set<String> allNodes = new HashSet<>();

        String[] edges = input.split(",");
        for (String edge : edges) {
            edge = edge.trim();
            String[] parts = edge.split("->");
            if (parts.length != 2) continue;

            String from = parts[0].trim();
            String to = parts[1].trim();

            String[] toNodes = to.split("\\s+|,");
            for (String toNode : toNodes) {
                toNode = toNode.trim();
                if (toNode.isEmpty()) continue;

                graph.computeIfAbsent(from, k -> new ArrayList<>()).add(toNode);
                allNodes.add(toNode);
            }

            allNodes.add(from);
            graph.putIfAbsent(from, graph.getOrDefault(from, new ArrayList<>()));
        }

        // Обход в глубину для обнаружения цикла
        Set<String> visited = new HashSet<>();
        Set<String> recursionStack = new HashSet<>();

        boolean hasCycle = false;
        for (String node : allNodes) {
            if (!visited.contains(node)) {
                if (dfsHasCycle(node, graph, visited, recursionStack)) {
                    hasCycle = true;
                    break;
                }
            }
        }

        System.out.println(hasCycle ? "yes" : "no");
    }

    private static boolean dfsHasCycle(String node, Map<String, List<String>> graph,
                                       Set<String> visited, Set<String> recursionStack) {
        visited.add(node);
        recursionStack.add(node);

        for (String neighbor : graph.getOrDefault(node, new ArrayList<>())) {
            if (!visited.contains(neighbor)) {
                if (dfsHasCycle(neighbor, graph, visited, recursionStack)) {
                    return true;
                }
            } else if (recursionStack.contains(neighbor)) {
                return true;
            }
        }

        recursionStack.remove(node);
        return false;
    }
}
