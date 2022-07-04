package jads.lesson7;

import jads.lesson3.CW.Queue;
import java.io.IOException;

public class Graph {
    class Vertex{
        public char label;
        public boolean isVisited;
        public boolean wasVisited;
        public Vertex parent;
        public Vertex(char label) {
            this.label = label;
            this.isVisited = false;
        }
        @Override
        public String toString() {
            return "V (" + label + ')';
        }
    }

    public class Stack {
        private int maxSize;
        private int[] stack;
        private int top;
        public Stack(int size){
            this.maxSize = size;
            this.stack = new int[this.maxSize];
            this.top = -1;
        }
        public void push(int i){
            this.stack[++this.top] = i;
        }
        public int pop(){
            return this.stack[this.top--];
        }
        public int peek(){
            return this.stack[this.top];
        }
        public boolean isEmpty(){
            return (this.top == -1);
        }
        public boolean isFull(){
            return (this.top == this.maxSize-1);
        }
    }

    private final int MAX_VERTICES = 32;
    private Vertex[] vertices;
    private int[][] adjacency;
    private int size;
    private Stack stack;
    public Graph(int i) {
        stack = new Stack(MAX_VERTICES);
        vertices = new Vertex[MAX_VERTICES];
        adjacency = new int[MAX_VERTICES][MAX_VERTICES];
        size = 0;
    }

    private void resetFlags() {
        for (int i = 0; i < size; i++) {
            vertices[i].wasVisited = false;
        }
    }

    public void depthTreverse() throws IOException {
        Stack stack = new Stack(MAX_VERTICES);
        vertices[0].wasVisited = true;
        showVertex(0);
        stack.push(0);
        while (!stack.isEmpty()) {
            int v = getUnvisitedVertex(stack.peek());
            if (v == -1){
                stack.pop();
            } else {
                vertices[v].isVisited = true;
                showVertex(v);
                stack.push(v);
            }
        }
        resetFlags();
    }

    private void showVertex(int i) {

    }

    public void widthTreverse() throws IOException {
        Queue queue = new Queue(MAX_VERTICES);
        vertices[0].wasVisited = true;
        showVertex(0);
        queue.insert(0);
        while (!queue.isEmpty()) {
            int vCurr = queue.remove();
            int vNext;
            if ((vNext = getUnvisitedVertex(vCurr)) != -1) {
                vertices[vNext].isVisited = true;
                showVertex(vNext);
                queue.insert(vNext);
            }
        }
        resetFlags();
    }

    public Queue widthTraversePath(char from, char to) {
        int start = getIndex(from);
        int stop = getIndex(to);

        Queue queue = new Queue(MAX_VERTICES);
        vertices[start].wasVisited = true;
        queue.insert(start);
        boolean done = false;
        while (!queue.isEmpty()) {
            int v1 = queue.remove();
            int v2;
            while ((v2 = getUnvisitedVertex(v1)) != -1) {
                vertices[v2].wasVisited = true;
                if (v2 == stop) {
                    done = true;
                    break;
                }
                queue.insert(v2);
            }
        }
        resetFlags();
        if (done)
            return queue;
        else
            return null;
    }

    private int getUnvisitedVertex(int v1) {
        return 0;
    }

    private int getIndex(char c){
        for (int i = 0; i < vertices.length; i++) {
            if(vertices[i].label == c){
                return i;
            }
        }
        return -1;
    }



    public void addVertex(char label){
        vertices[size++] = new Vertex(label);
    }
    public void addEdge(int start, int end){
        adjacency[start][end] = 1;
        adjacency[end][start] = 1;
    }
    public void displayVertex(int vertex){
        System.out.println(vertices[vertex].label);
    }

    private int getAdjUnvisitedVertex(int ver){
        for (int i = 0; i < size; i++) {
            if(adjacency[ver][i] == 1 && vertices[i].isVisited == false){
                return i;
            }
        }
        return -1;
    }

    public void depthTraverse(){
        Stack stack = new Stack(size);
        vertices[0].isVisited = true;
        displayVertex(0);
        stack.push(0);
        while (!stack.isEmpty()) {
            int v = getAdjUnvisitedVertex(stack.peek());
            if (v == -1){
                stack.pop();
            } else {
                vertices[v].isVisited = true;
                displayVertex(v);
                stack.push(v);
            }
        }
        for (int i = 0; i < size; i++) {
            vertices[i].isVisited = false;
        }
    }


    public void widthTraverse(){
        Queue queue = new Queue(MAX_VERTICES);
        vertices[0].isVisited = true;
        displayVertex(0);
        queue.insert(0);
        int v2;
        while (!queue.isEmpty()) {
            int v1 = queue.remove();
            while ((v2 = getUnvisited(v1)) != -1) {
                vertices[v2].isVisited = true;
                displayVertex(v2);
                queue.insert(v2);
            }
        }
        for (int i = 0; i < size; i++) {
            vertices[i].isVisited = false;
        }
    }

    private int getUnvisited(int v1) {
        return 0;
    }

    Stack shortWay(char from, char to) {
        Stack result = new Stack(MAX_VERTICES);
        Queue queue = new Queue(MAX_VERTICES);

        int start = getIndex(from);
        int stop = getIndex(to);
        if (start == -1 || stop == -1 || start == stop)
            return null;

        vertices[start].wasVisited = true;
        queue.insert(start);
        while (!queue.isEmpty()) {
            int vCur = queue.remove();
            int vNxt;
            while ((vNxt = getUnvisitedVertex(vCur)) != -1) {
                vertices[vNxt].parent = vertices[vCur];
                vertices[vNxt].wasVisited = true;
                if (vNxt == stop) break;
                queue.insert(vNxt);
            }
            if (vNxt == stop) break;
        }
        if (!vertices[stop].wasVisited) return null;

        result.push(vertices[stop].label);
        int current = stop;
        while (vertices [current].parent != null)
            for (int i = 0; i < vertices.length; i++)
                if (vertices [current].parent == vertices[i]) {
                    result.push(vertices[i].label);
                    current = i;
                    break;
                }
        for (int i = 0; i < size; i++) {
            vertices[i].wasVisited = false;
            vertices[i].parent = null;
        }
        return result;
    }
}
