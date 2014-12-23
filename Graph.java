package test;
import korat.finitization.*;
import korat.finitization.impl.*;
import java.util.*;
public class Graph {
    private Vertex root;
    private int size;
    public static class Vertex {
        Vertex[] outgoingEdges;
    }
    public boolean repOK() {
   // returns true if and if only the graph reachable from "root"
   // is a directed acyclic graph
        if(root == null)
            return size == 0;
        //value == 1: all its children have been visited, perfectly acyclic
        //value == -1: it has occurred once in the path, and we're still in the path
        HashMap<Vertex, Integer> status = new HashMap<Vertex, Integer>();
        return dfs(root, status) && size == status.size();
    }
    private boolean dfs(Vertex current, HashMap<Vertex, Integer> status){
        //For excluding duplicated children
        HashSet<Vertex> checkedChildren = new HashSet<Vertex>();
        if(!status.containsKey(current)){
            status.put(current, -1);
            for(int i = 0; i < current.outgoingEdges.length; i++){
                Vertex child = current.outgoingEdges[i];
                if(!checkedChildren.add(child)) return false;
                if(!dfs(child, status)) return false;
            }
        }
        else{
            if(status.get(current) == -1)
                return false;
        }
        status.put(current, 1);
        return true;
    }
    public static IFinitization finiteGraph(int vNum){
        IFinitization f  = FinitizationFactory.create(Graph.class);
        IObjSet vertex = f.createObjSet(Vertex.class, vNum, false);
        f.set("root", vertex);
        IIntSet arrayLength = f.createIntSet(0, vNum - 1);
        IArraySet arrays = f.createArraySet(Vertex[].class, arrayLength, vertex, vNum);
        f.set("Vertex.outgoingEdges", arrays);
        IIntSet sizes = f.createIntSet(vNum);
        f.set("size", sizes);
        return f;
    }
}