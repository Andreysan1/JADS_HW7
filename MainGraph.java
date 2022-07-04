package jads.lesson7;

import jads.lesson3.CW.Queue;

import java.io.IOException;
import java.util.Arrays;

public class MainGraph {
    public static void main(String[] args) {
        Graph g = new Graph(16);
        g.addVertex('A');
        g.addVertex('B');
        g.addVertex('C');
        g.addVertex('D');
        g.addVertex('E');
        g.addEdge(0, 1); //AB
        g.addEdge(1, 2); //BC
        g.addEdge(0, 3); //AD
        g.addEdge(3, 4); //DE
//        g.dfs();
        g.depthTraverse();
        System.out.println();
            System.out.println("--------------");
            g.widthTraverse();
            System.out.println();
            System.out.println("--------------");

            Graph.Stack shortWay = g.shortWay('A', '1');
            if (shortWay != null)
                while (!shortWay.isEmpty()) {
                    System.out.println((char) shortWay.pop());
                    System.out.println((shortWay.isEmpty() ? "" : "->"));
                }
            System.out.println();
            Queue q = g.widthTraversePath('A','1');
            System.out.println(Arrays.toString(q.getQueue()));
    }

}


