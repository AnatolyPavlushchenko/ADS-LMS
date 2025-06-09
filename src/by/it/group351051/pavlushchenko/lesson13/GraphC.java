package by.it.group351051.pavlushchenko.lesson13;


import java.util.*;

public class GraphC {
    private static void dfs(String vertex, Map<String, List<String>> graph, Set<String> visited, ArrayList<String> finishStack) {
        visited.add(vertex);
        for (String v : graph.get(vertex))
            if (!visited.contains(v))
                dfs(v, graph, visited, finishStack);
        finishStack.add(vertex);
    }
    private static void reversedDfs(String vertex, Map<String, List<String>> reversedGraph, Set<String> visited, List<String> component) {
        visited.add(vertex);
        component.add(vertex);
        for (String v : reversedGraph.get(vertex))
            if (!visited.contains(v))
                reversedDfs(v, reversedGraph, visited, component);
    }
    private static List<List<String>> findComponents(Map<String, List<String>> graph, Map<String, List<String>> reversedGraph, Set<String> allVertex) {
        for (List<String> neighbors : graph.values()) Collections.sort(neighbors);
        Set<String> visited = new HashSet<>();
        ArrayList<String> finishStack = new ArrayList<>();
        List<String> sortedVertexes = new ArrayList<>(allVertex);
        Collections.sort(sortedVertexes);
        for (String vertex : sortedVertexes)
            if (!visited.contains(vertex))
                dfs(vertex, graph, visited, finishStack);

        visited.clear();
        List<List<String>> components = new ArrayList<>();
        while (!finishStack.isEmpty()) {
            String vertex = finishStack.remove(finishStack.size() - 1);
            if (!visited.contains(vertex)) {
                List<String> component = new ArrayList<>();
                reversedDfs(vertex, reversedGraph, visited, component);
                Collections.sort(component);
                components.add(component);
            }
        }
        return components;
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        Map<String, List<String>> graph = new HashMap<>();
        Map<String, List<String>> reversedGraph = new HashMap<>();
        Set<String> allVertex = new HashSet<>();

        for (String edge : input.split(", ")) {
            String[] t = edge.split("->");
            String vertexFrom = t[0];
            String vertexTo = t[1];
            graph.computeIfAbsent(vertexFrom, k -> new ArrayList<>()).add(vertexTo);
            reversedGraph.computeIfAbsent(vertexTo, k -> new ArrayList<>()).add(vertexFrom);
            allVertex.add(vertexFrom);
            allVertex.add(vertexTo);
            graph.putIfAbsent(vertexTo, new ArrayList<>());
            reversedGraph.putIfAbsent(vertexFrom, new ArrayList<>());
        }
        for (List<String> component : findComponents(graph, reversedGraph, allVertex))
            System.out.println(String.join("", component));
    }
}
