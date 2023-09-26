import java.util.*;

/**
 * Your implementation of various different graph algorithms.
 *
 * @author Kenny Zheng
 * @version 1.0
 * @userid kzheng73 (i.e. gburdell3)
 * @GTID 903529640 (i.e. 900000000)
 * <p>
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 * <p>
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class GraphAlgorithms {

    /**
     * Performs a breadth first search (bfs) on the input graph, starting at
     * the parameterized starting vertex.
     * <p>
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     * <p>
     * You may import/use java.util.Set, java.util.List, java.util.Queue, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     * <p>
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for BFS (storing the adjacency list in a variable is fine).
     * <p>
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the bfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> bfs(Vertex<T> start, Graph<T> graph) {
        if (start == null) {
            throw new IllegalArgumentException("Start is null.");
        } else if (graph == null) {
            throw new IllegalArgumentException("Graph is null.");
        } else if (!(graph.getVertices().contains(start))) {
            throw new IllegalArgumentException("Start doesn't exist in the graph.");
        }

        Set<Vertex<T>> visited = new HashSet<>();
        Queue<Vertex<T>> queue = new LinkedList<>();
        Map<Vertex<T>, List<VertexDistance<T>>> adj = graph.getAdjList();
        List<Vertex<T>> returnList = new LinkedList<>();

        visited.add(start);
        queue.add(start);
        returnList.add(start);

        while (!(queue.isEmpty())
                && visited.size() != graph.getVertices().size()) {
            Vertex<T> removedVertex = queue.remove();

            for (VertexDistance<T> i : adj.get(removedVertex)) {
                Vertex<T> v = i.getVertex();
                if (!(visited.contains(v))) {
                    visited.add(v);
                    returnList.add(v);
                    queue.add(v);
                }
            }
        }

        return returnList;

    }

    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * the parameterized starting vertex.
     * <p>
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     * <p>
     * *NOTE* You MUST implement this method recursively, or else you will lose
     * all points for this method.
     * <p>
     * You may import/use java.util.Set, java.util.List, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     * <p>
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for DFS (storing the adjacency list in a variable is fine).
     * <p>
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> dfs(Vertex<T> start, Graph<T> graph) {
        if (start == null) {
            throw new IllegalArgumentException("Start is null.");
        } else if (graph == null) {
            throw new IllegalArgumentException("Graph is null.");
        } else if (!(graph.getVertices().contains(start))) {
            throw new IllegalArgumentException("Start doesn't exist in the graph.");
        }

        Set<Vertex<T>> visitedSet = new HashSet<>();
        List<Vertex<T>> returnList = new LinkedList<>();
        Map<Vertex<T>, List<VertexDistance<T>>> adjList = graph.getAdjList();

        dfsHelper(start, adjList, visitedSet, returnList);

        return returnList;

    }

    /**
     * @param vertex     the current vertex
     * @param adjList    the adjacency list
     * @param visitedSet the visited set
     * @param returnList the list to return
     * @param <T>        the generic type
     */
    private static <T> void dfsHelper(Vertex<T> vertex, Map<Vertex<T>, List<VertexDistance<T>>> adjList,
                                      Set<Vertex<T>> visitedSet, List<Vertex<T>> returnList) {
        if (!(visitedSet.contains(vertex))) {
            visitedSet.add(vertex);
            returnList.add(vertex);

            for (VertexDistance<T> i : adjList.get(vertex)) {
                Vertex<T> v = i.getVertex();
                if (!(visitedSet.contains(v))) {
                    dfsHelper(v, adjList, visitedSet, returnList);
                }
            }
        }
    }

    /**
     * Finds the single-source shortest distance between the start vertex and
     * all vertices given a weighted graph (you may assume non-negative edge
     * weights).
     * <p>
     * Return a map of the shortest distances such that the key of each entry
     * is a node in the graph and the value for the key is the shortest distance
     * to that node from start, or Integer.MAX_VALUE (representing
     * infinity) if no path exists.
     * <p>
     * You may import/use java.util.PriorityQueue,
     * java.util.Map, and java.util.Set and any class that
     * implements the aforementioned interfaces, as long as your use of it
     * is efficient as possible.
     * <p>
     * You should implement the version of Dijkstra's where you use two
     * termination conditions in conjunction.
     * <p>
     * 1) Check if all of the vertices have been visited.
     * 2) Check if the PQ is empty yet.
     * <p>
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the Dijkstra's on (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from start to every
     * other node in the graph
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
                                                        Graph<T> graph) {
        if (start == null) {
            throw new IllegalArgumentException("Start is null.");
        } else if (graph == null) {
            throw new IllegalArgumentException("Graph is null.");
        } else if (!(graph.getVertices().contains(start))) {
            throw new IllegalArgumentException("Start doesn't exist in the graph.");
        }

        Map<Vertex<T>, List<VertexDistance<T>>> adj = graph.getAdjList();
        Set<Vertex<T>> visited = new HashSet<>();
        PriorityQueue<VertexDistance<T>> pq = new PriorityQueue<>();

        Map<Vertex<T>, Integer> distanceMap = new HashMap<>();
        for (Vertex<T> vertex : graph.getVertices()) {
            if (vertex.equals(start)) {
                distanceMap.put(vertex, 0);
            } else {
                distanceMap.put(vertex, Integer.MAX_VALUE);
            }
        }

        pq.add(new VertexDistance<>(start, 0));

        while (!(pq.isEmpty())
                && graph.getVertices().size() != visited.size()) {
            VertexDistance<T> removed = pq.remove();
            Vertex<T> vertex = removed.getVertex();
            int distance = removed.getDistance();

            if (!(visited.contains(vertex))) {
                visited.add(vertex);
                distanceMap.replace(vertex, distance);

                for (VertexDistance<T> i : adj.get(vertex)) {
                    if (!(visited.contains(i.getVertex()))) {
                        Vertex<T> v = i.getVertex();
                        int distanceToV = distance + i.getDistance();
                        pq.add(new VertexDistance<>(v, distanceToV));
                    }
                }
            }
        }
        return distanceMap;

    }

    /**
     * Runs Kruskal's algorithm on the given graph and returns the Minimal
     * Spanning Tree (MST) in the form of a set of Edges. If the graph is
     * disconnected and therefore no valid MST exists, return null.
     * <p>
     * You may assume that the passed in graph is undirected. In this framework,
     * this means that if (u, v, 3) is in the graph, then the opposite edge
     * (v, u, 3) will also be in the graph, though as a separate Edge object.
     * <p>
     * The returned set of edges should form an undirected graph. This means
     * that every time you add an edge to your return set, you should add the
     * reverse edge to the set as well. This is for testing purposes. This
     * reverse edge does not need to be the one from the graph itself; you can
     * just make a new edge object representing the reverse edge.
     * <p>
     * You may assume that there will only be one valid MST that can be formed.
     * <p>
     * Kruskal's will also require you to use a Disjoint Set which has been
     * provided for you. A Disjoint Set will keep track of which vertices are
     * connected given the edges in your current MST, allowing you to easily
     * figure out whether adding an edge will create a cycle. Refer
     * to the DisjointSet and DisjointSetNode classes that
     * have been provided to you for more information.
     * <p>
     * You should NOT allow self-loops or parallel edges into the MST.
     * <p>
     * By using the Disjoint Set provided, you can avoid adding self-loops and
     * parallel edges into the MST.
     * <p>
     * You may import/use java.util.PriorityQueue,
     * java.util.Set, and any class that implements the aforementioned
     * interfaces.
     * <p>
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param graph the graph we are applying Kruskals to
     * @return the MST of the graph or null if there is no valid MST
     * @throws IllegalArgumentException if any input is null
     */
    public static <T> Set<Edge<T>> kruskals(Graph<T> graph) {
        if (graph == null) {
            throw new IllegalArgumentException("Cannot perform Kruskal's Algorithm with a null reference!");
        } else {
            DisjointSet<Vertex<T>> disjointSet = new DisjointSet<>();
            for (Vertex<T> v : graph.getVertices()) {
                disjointSet.find(v);
            }
            Set<Edge<T>> mst = new HashSet<>();
            Queue<Edge<T>> priorityQueue = new PriorityQueue<>(graph.getEdges());
            while (!priorityQueue.isEmpty() && mst.size() < 2 * (graph.getEdges().size() - 1)) {
                Edge<T> uv = priorityQueue.poll();
                Edge<T> vu = new Edge<>(uv.getV(), uv.getU(), uv.getWeight());
                if (disjointSet.find(uv.getU()) != disjointSet.find(uv.getV())) {
                    mst.add(uv);
                    mst.add(vu);
                    disjointSet.union(uv.getU(), uv.getV());
                }
            }

            if (mst.size() == (graph.getVertices().size() - 1) * 2) {
                return mst;
            } else {
                return null;
            }
        }
    }
}



