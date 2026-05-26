package se.su.inlupp;

public class OurOwnTestProgram {

    public static void testMethod(Graph<Location> graph) {

        Location node1 = new Location("Arlanda", 400, 400);
        Location node2 = new Location("Skavsta", 150, 40);
        Location node3 = new Location("Bromma", 40, 39);

        graph.add(node1);
        graph.add(node3);
        graph.add(node2);
        graph.connect(node1, node2, "Edge(node1node2)", 4);
        graph.connect(node2, node3, "Edge(node2node3)", 4);

        for (Edge<Location> e : graph.getEdgesFrom(node2)) {
            System.out.println(e.getName());
        }

        System.out.println(graph.getEdgeBetween(node1, node2).getName());
        System.out.println(graph.getEdgeBetween(node1, node3));
        System.out.println(graph.getEdgeBetween(node1, node2));
        System.out.println(graph);
        System.out.println(node1.getAbscissa() + " " + node1.getOrdinate());
        System.out.println(node2.getAbscissa() + " " + node2.getOrdinate());

        // graph.disconnect(node1, node2);
        // System.out.println(graph);

        // graph.remove(node3);
        // System.out.println(graph);
    }
}
