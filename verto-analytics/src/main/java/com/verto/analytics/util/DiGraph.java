package com.verto.analytics.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Class responsible for Directed Graph
 * @author abhishekrai
 * @since 24/06/2017
 */
public class DiGraph {

    private final Logger logger = LoggerFactory.getLogger(DiGraph.class);

    private final Map<String, HashSet<String>> graph = new HashMap<>();

    public Map<String, HashSet<String>> getGraph() {
        return graph;
    }

    public void addVertex(String word) {
        if(!graph.containsKey(word))
            graph.put(word, new HashSet<>());
    }

    public void addEdge(String sourceVertex, String destinationVertex) {
        if(Optional.ofNullable(sourceVertex).isPresent() && Optional.ofNullable(destinationVertex).isPresent()) {
            if(graph.containsKey(sourceVertex) && graph.containsKey(destinationVertex)) {
                graph.get(sourceVertex).add(destinationVertex);
            } else {
                throw new NoSuchElementException("Any of the vertex is not in the graph");
            }
        } else{
            throw new NullPointerException("Both the vertex need to be available");
        }
    }

    /**
     * Overloaded method for system requirements
     * @param sourceVertex Initial Vertex in the graph
     * @param localEndWithChar It contains map for starting character Set matching with ending character of sourceVertex.
     */
    public void addEdge(String sourceVertex, HashSet<String> localEndWithChar) {
        logger.info("Source vertex and edges coming as :" + sourceVertex + "\t" + localEndWithChar);
        if(Optional.ofNullable(localEndWithChar).isPresent()) {
            HashSet<String> stringHashSet = new HashSet<>(localEndWithChar);
            localEndWithChar.forEach((n) -> {
                if (localEndWithChar.contains(sourceVertex)) {
                    stringHashSet.remove(sourceVertex);
                }

            });
            graph.put(sourceVertex, stringHashSet);
        }
    }

    public HashSet<String> edgesFromVertex(String sourceVertex) {
        if(Optional.ofNullable(sourceVertex).isPresent()) {
            return graph.get(sourceVertex);

        } else {
            throw new NullPointerException("Empty source vertex is coming");
        }
    }

}
