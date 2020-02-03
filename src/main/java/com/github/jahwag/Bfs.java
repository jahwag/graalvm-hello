package com.github.jahwag;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public final class Bfs {

    public Bfs(InputStream is) throws IOException {
        Scanner scanner = new Scanner(is);
        int q = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int qItr = 0; qItr < q; qItr++) {
            String[] nm = scanner.nextLine()
                                 .split(" ");

            int n = Integer.parseInt(nm[0]);

            int m = Integer.parseInt(nm[1]);

            int[][] edges = new int[m][2];

            for (int i = 0; i < m; i++) {
                String[] edgesRowItems = scanner.nextLine()
                                                .split(" ");
                scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

                for (int j = 0; j < 2; j++) {
                    int edgesItem = Integer.parseInt(edgesRowItems[j]);
                    edges[i][j] = edgesItem;
                }
            }

            int s = scanner.nextInt();
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            int[] result = bfs(n, m, edges, s);
            System.out.println(result.length);
        }
        scanner.close();
    }

    static int[] bfs(int n, int m, int[][] edges, int s) {
        UndirectedGraph graph = new UndirectedGraph();

        for (int nodeId = 1; nodeId <= n; nodeId++) {
            graph.add(Node.of(nodeId));
        }
        for (int[] edgePair : edges) {
            int start = edgePair[0];
            int end = edgePair[1];

            graph.addEdge(start, end);
        }

        Node startNode = graph.get(s);
        List<Integer> shortestPaths = graph.shortestPaths(startNode);
        int[] returnValue = new int[graph.nodes()
                                         .size() - 1];
        Arrays.setAll(returnValue, shortestPaths::get);

        return returnValue;
    }

    static class Node {

        private final long id;

        private final Map<Node, Long> edgeToId = new HashMap<>();

        private Node(long id) {
            this.id = id;
        }

        public static Node of(long id) {
            return new Node(id);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return id == node.id;
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }

        public boolean contains(Node node) {
            return edgeToId.containsKey(node);
        }

        public void connect(Node otherNode) {
            if (!this.equals(otherNode) && !contains(otherNode)) {
                edgeToId.put(otherNode, otherNode.id);
                otherNode.connect(this);
            }
        }

        public long id() {
            return id;
        }

        public Collection<Node> neighbors() {
            return edgeToId.keySet();
        }
    }

    static class UndirectedGraph {

        Map<Long, Node> nodes = new HashMap<>();

        public Collection<Node> nodes() {
            return nodes.values();
        }

        public void addEdge(long start, long end) {
            Node startNode = nodes.computeIfAbsent(start, Node::of);
            Node endNode = nodes.computeIfAbsent(end, Node::of);
            startNode.connect(endNode);
        }

        public Node get(long id) {
            return nodes.get(id);
        }

        public void add(Node node) {
            nodes.put(node.id, node);
        }

        public List<Integer> shortestPaths(Node startNode) {
            Map<Node, Integer> depths = new HashMap<>();
            depths.put(startNode, 0);

            Queue<Node> queue = new ArrayDeque<>();
            queue.add(startNode);

            while (!queue.isEmpty()) {
                Node node = queue.remove();
                Integer depth = depths.get(node);

                Set<Node> unvisitedNeighbors = new HashSet<>(node.neighbors());
                unvisitedNeighbors.removeAll(depths.keySet());

                for (Node unvisitedNeighbor : unvisitedNeighbors) {
                    queue.add(unvisitedNeighbor);
                    depths.put(unvisitedNeighbor, depth + 6);
                }
            }

            for (Node node : nodes()) {
                if (!depths.containsKey(node)) {
                    depths.put(node, -1);
                }
            }

            depths.remove(startNode);

            return depths.entrySet()
                         .stream()
                         .sorted(Comparator.comparingLong(t -> t.getKey()
                                                                .id()))
                         .map(Map.Entry::getValue)
                         .collect(Collectors.toList());
        }
    }

}
