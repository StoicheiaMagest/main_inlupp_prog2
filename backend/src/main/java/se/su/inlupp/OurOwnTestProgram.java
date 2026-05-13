package se.su.inlupp;

public class OurOwnTestProgram {

    private static final Graph<String> graph = new ListGraph<>();
    public static void main(String[] args){
        String node1 = "1";
        String node2 = "2";
        String node3 = "3";

        graph.add(node1);
        graph.add(node2);
        graph.add(node3);
        graph.connect(node1, node2, "Edge(node1node2)", 4);
        graph.connect(node2, node3, "Edge(node2node3)", 4);

        for(Edge<String> e: graph.getEdgesFrom(node2)){
            System.out.println(e.getName());
        }

        System.out.println(graph.getEdgeBetween(node1, node2).getName());
        System.out.println(graph.getEdgeBetween(node1, node3));
        System.out.println(graph.getEdgeBetween(node1, node2));        
        System.out.println(graph);

        //graph.disconnect(node1, node2);
        //System.out.println(graph);

        //graph.remove(node3);
        //System.out.println(graph);
    }
}
