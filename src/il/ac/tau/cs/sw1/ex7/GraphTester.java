package il.ac.tau.cs.sw1.ex7;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Ignore;
import org.junit.Test;

public class GraphTester {
    static Comparator<Graph.Edge> comp = new Comparator<Graph.Edge>() {
        public int compare(Graph.Edge e1, Graph.Edge e2) {
            if (e1.weight != e2.weight) {
                return e1.weight < e2.weight ? -1 : 1;
            }
            if (e1.node1 != e2.node1) {
                return e1.node1 - e2.node1;
            }
            return e1.node2 - e2.node2;
        }
    };

    static Graph buildGraph(List<Graph.Edge> edges) {
        OptionalInt n = edges.stream().mapToInt(edge -> Math.max(edge.node1, edge.node2)).max();
        return new Graph(n.isPresent() ? n.getAsInt() : -1, edges);
    }

    static List<Graph.Edge> buildList(int[][] array, int... indices) {
        List<Graph.Edge> result = new ArrayList<>();

        if (indices == null) {
            return null;
        }

        for (int i = 0; i < indices.length; i++) {
            int[] entry = array[indices[i]];
            result.add(new Graph.Edge(entry[0], entry[1], entry[2]));
        }
        return result;
    }

    static <T> boolean equals(List<T> list1, List<T> list2, Comparator<T> comparator) {
        if (list1 == null || list2 == null) {
            return list1 == list2;
        }

        list1 = list1.stream().collect(Collectors.toList());
        list2 = list2.stream().collect(Collectors.toList());
        Iterator<T> iter1 = list1.iterator();
        

        boolean found = true;
        while (iter1.hasNext() && found) {
            found = false;
            T item1 = iter1.next();
            Iterator<T> iter2 = list2.iterator();
            while (iter2.hasNext()) {
                T item2 = iter2.next();
                if (comparator.compare(item1, item2) == 0) {
                    list1.remove(item1);
                    list2.remove(item2);
                    iter1 = list1.iterator();
                    found = true;
                    break;
                }
            }
        }

        return list1.isEmpty() && list2.isEmpty();
    }

    public void testGraph(int[][] edges, int[] solution, int[]... wrong){
        Graph graph = buildGraph(buildList(edges, IntStream.range(0, edges.length).toArray()));
        List<Graph.Edge> result = graph.greedyAlgorithm();

        for (int i = 0; i < wrong.length; ++i) {
            List<Graph.Edge> wrongSolution = buildList(edges, wrong[i]);
            assertTrue(String.format("greedyAlgorithm returned %s unexpectedly", wrongSolution), !equals(result, wrongSolution, comp));
        }

        List<Graph.Edge> expected = buildList(edges, solution);
        assertTrue(String.format("greedyAlgorithm expected %s but received %s", expected, result), equals(expected, result, comp));
    }

    @Test
    public void testGraph() {
        int[][] edges = new int[][]{
            {0, 1, 1}, // first
            {1, 2, 4},
            {2, 3, 1}, // second
            {2, 4, 3}, // third
            {3, 4, 3},
            {0, 4, 4}  // Fourth
        };

        int[] solution = new int[]{0,2,3,5};
        int[][] wrongGuesses = new int[][] {
            {0,2,4,5},
            {0,2,3,1},
            {0,2,4,3}
        };

        testGraph(edges, solution, wrongGuesses);
    }

    @Test
    public void testUnconnectedGraph() {
        int[][] edges = new int[][]{
            {0, 1, 0},
            {1, 2, 1},
            {3, 4, 2}
        };

        testGraph(edges, null);

        edges = new int[][] {
            {0, 1, 0},
            {1, 2, 1},
            {0, 4, 2}
        };

        testGraph(edges, null);
    }

    @Test
    public void testFullGraph() {
        int[][] edges = new int[][]{
            {0, 1, 9},
            {0, 2, 8},
            {0, 3, 4},
            {0, 4, 7},
            {0, 5, 3},

            {1, 2, 1},
            {1, 3, 3},
            {1, 4, 2},
            {1, 5, 0},

            {2, 3, 4},
            {2, 4, 1},
            {2, 5, 2},

            {3, 4, 2},
            {3, 5, 0},

            {4, 5, 2}
        };

        testGraph(edges, new int[]{4, 5, 8, 10, 13});
    }

    @Test
    public void testLoopGraph() {
        int[][] edges = new int[][]{
            {0, 0, 0},
            {0, 1, 9},
            {1, 1, 0},
            {1, 2, 3},
            {2, 2, 5}
        };

        testGraph(edges, new int[]{1, 3});
    }

    @Test
    public void testEqualGraph() {
        int[][] edges = new int[][]{
            {2, 2, 0},
            {1, 2, 0},
            {0, 1, 0},
            {1, 1, 0},
            {0, 0, 0},
            {0, 2, 0},
        };

        testGraph(edges, new int[]{2, 5});
    }

    @Test
    public void testTwice() {
        int[][] edges = new int[][]{
            {0, 0, 4},
            {0, 1, 9},
            {1, 3, 8},
            {1, 4, 7},
            {2, 3, 10},
            {2, 4, 0},
            {3, 4, 5}
        };

        Graph g = buildGraph(buildList(edges, 0,1,2,3,4,5,6));
        List<Graph.Edge> first = g.greedyAlgorithm();
        List<Graph.Edge> second = g.greedyAlgorithm();
        assertTrue(String.format("%s and %s do not match", first, second), equals(first, second, comp));
    }

    @Ignore
    @Test
    public void testStatelessGraph() {
        int[][] edges = new int[][]{
            {0, 0, 4},
            {0, 1, 9},
            {1, 3, 8},
            {1, 4, 7},
            {2, 3, 10},
            {2, 4, 0},
            {3, 4, 5}
        };

        Graph g = buildGraph(buildList(edges, 0,1,2,3,4,5,6));

        /* Exhaust the iterator */
        Iterator<Graph.Edge> iter = g.selection();
        while (iter.hasNext()) {
            iter.next();
        }

        boolean f1 = g.feasibility(buildList(edges, 1, 2, 6), buildList(edges, 4).get(0));
        boolean s1 = g.solution(buildList(edges, 0, 3));
        List<Graph.Edge> first = g.greedyAlgorithm();
        boolean f2 = g.feasibility(buildList(edges, 1, 2, 6), buildList(edges, 4).get(0));
        boolean s2 = g.solution(buildList(edges, 0, 3));
        List<Graph.Edge> second = g.greedyAlgorithm();
        boolean f3 = g.feasibility(buildList(edges, 1, 2, 6), buildList(edges, 4).get(0));
        boolean s3 = g.solution(buildList(edges, 0, 3));

        assertTrue(String.format("%s and %s do not match", first, second), equals(first, second, comp));
        assertTrue(f1 == f2 && f2 == f3);
        assertTrue(s1 == s2 && s2 == s3);
    }
}
