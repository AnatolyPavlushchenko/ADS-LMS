package by.it.group351051.pavlushchenko.lesson13;

    import java.util.*;

    public class GraphA {
        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine(); // Пример: 0 -> 2, 1 -> 3, 2 -> 3, 0 -> 1
            scanner.close();

            // Построение графа
            Map<String, List<String>> graph = new HashMap<>();
            Map<String, Integer> inDegree = new HashMap<>();
            Set<String> allNodes = new HashSet<>();

            String[] edges = input.split(",");
            for (String edge : edges) {
                edge = edge.trim();
                String[] parts = edge.split("->");
                if (parts.length != 2) continue;

                String from = parts[0].trim();
                String to = parts[1].trim();

                // В случае нескольких вершин справа (например: 0 -> 1 2)
                String[] toNodes = to.split("\\s+|,");
                for (String toNode : toNodes) {
                    toNode = toNode.trim();
                    if (toNode.isEmpty()) continue;

                    graph.computeIfAbsent(from, k -> new ArrayList<>()).add(toNode);
                    inDegree.put(toNode, inDegree.getOrDefault(toNode, 0) + 1);
                    allNodes.add(toNode);
                }

                allNodes.add(from);
                graph.putIfAbsent(from, graph.getOrDefault(from, new ArrayList<>()));
                inDegree.putIfAbsent(from, inDegree.getOrDefault(from, 0));
            }

            // Топологическая сортировка (Kahn's algorithm с приоритетной очередью)
            PriorityQueue<String> queue = new PriorityQueue<>();
            for (String node : allNodes) {
                if (inDegree.getOrDefault(node, 0) == 0) {
                    queue.offer(node);
                }
            }

            List<String> result = new ArrayList<>();
            while (!queue.isEmpty()) {
                String current = queue.poll();
                result.add(current);

                for (String neighbor : graph.getOrDefault(current, new ArrayList<>())) {
                    inDegree.put(neighbor, inDegree.get(neighbor) - 1);
                    if (inDegree.get(neighbor) == 0) {
                        queue.offer(neighbor);
                    }
                }
            }

            // Проверка на наличие цикла
            if (result.size() != allNodes.size()) {
                System.out.println("Граф содержит цикл");
            } else {
                System.out.println(String.join(" ", result));
            }
        }
    }

