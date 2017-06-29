package com.verto.analytics.util;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Calculate All the path between two vertex recursively.
 * e.g
 * @author abhishekrai
 * @since 24/06/2017
 */
public class CalculateAllPaths {

    private final DiGraph diGraph;

    public CalculateAllPaths(DiGraph diGraph) {
            this.diGraph = diGraph;
    }

    public void addAllPaths(String sourceVertex, String destinationVertex,
                            Map<Character, Integer> endWithCharVsStringLength,
                            Map<Character, Integer> startWithCharVsStringLength,
                            Map<Character, LinkedList<String>> startWithCharVsStringList,
                            Map<Character, LinkedList<String>> endWithCharVsStringList,
                            TreeMap<Integer, Character> endStringLengthVsCharacter) {
        List<List<String>> paths = new ArrayList<>();

        if(!sourceVertex.equals(destinationVertex)) {
            recursive(sourceVertex, destinationVertex, endWithCharVsStringLength, startWithCharVsStringLength,
                    startWithCharVsStringList, endWithCharVsStringList,
                    paths, new LinkedHashSet<>(), endStringLengthVsCharacter);
        }
//        else {
//
//        }
    }

    private void recursive (String sourceVertex, String destinationVertex,
                            Map<Character, Integer> endWithCharVsStringLength,
                            Map<Character, Integer> startWithCharVsStringLength,
                            Map<Character, LinkedList<String>> startWithCharVsStringList,
                            Map<Character, LinkedList<String>> endWithCharVsStringList,
                            List<List<String>> paths,
                            LinkedHashSet<String> path,
                            TreeMap<Integer, Character> endStringLengthVsCharacter) {
        path.add(sourceVertex);

        if (sourceVertex.equals(destinationVertex)) {
            String individualPathString = path.stream().collect(Collectors.joining("-"));

            if(startWithCharVsStringLength.containsKey(individualPathString.charAt(0))) {
                if(startWithCharVsStringLength.get(individualPathString.charAt(0)) < path.size()) {
                    startWithCharVsStringLength.put(individualPathString.charAt(0), path.size());
                    LinkedList<String> newPathStringList = new LinkedList<>();
                    newPathStringList.add(individualPathString);
                    startWithCharVsStringList.put(individualPathString.charAt(0), newPathStringList);
                } else if (startWithCharVsStringLength.get(individualPathString.charAt(0)) == path.size()) {
                    startWithCharVsStringList.get(individualPathString.charAt(0)).add(individualPathString);
                }
//                else {
//                    // no-op
//                }
            } else {
                LinkedList<String> newPathStringList = new LinkedList<>();
                newPathStringList.add(individualPathString);
                startWithCharVsStringLength.put(individualPathString.charAt(0), path.size());
                startWithCharVsStringList.put(individualPathString.charAt(0), newPathStringList);
            }

            if(endWithCharVsStringLength.containsKey(individualPathString.charAt(individualPathString.length() -1))) {
                if(endWithCharVsStringLength.get(individualPathString.charAt(individualPathString.length() -1)) < path.size()) {
                    endWithCharVsStringLength.put(individualPathString.charAt(individualPathString.length() -1), path.size());
                    LinkedList<String> newPathStringList = new LinkedList<>();
                    newPathStringList.add(individualPathString);
                    endWithCharVsStringList.put(individualPathString.charAt(individualPathString.length() -1), newPathStringList);
                    endStringLengthVsCharacter.put(path.size(), individualPathString.charAt(individualPathString.length() - 1));
                } else if (endWithCharVsStringLength.get(individualPathString.charAt(individualPathString.length() -1)) == path.size()) {
                    endWithCharVsStringList.get(individualPathString.charAt(individualPathString.length() -1)).add(individualPathString);
                }
//                else {
//                    // no-op
//                }
            } else {
                LinkedList<String> newPathStringList = new LinkedList<>();
                newPathStringList.add(individualPathString);
                endWithCharVsStringLength.put(individualPathString.charAt(individualPathString.length() -1), path.size());
                endWithCharVsStringList.put(individualPathString.charAt(individualPathString.length() -1), newPathStringList);
                endStringLengthVsCharacter.put(path.size(), individualPathString.charAt(individualPathString.length() -1));
            }
            paths.add(new ArrayList<>(path));
            path.remove(sourceVertex);
            return;
        }

        if(Optional.ofNullable(diGraph.edgesFromVertex(sourceVertex)).isPresent()) {

            diGraph.edgesFromVertex(sourceVertex).forEach((edge) -> {
                if (!path.contains(edge)) {
                    recursive (edge, destinationVertex, endWithCharVsStringLength, startWithCharVsStringLength,
                            startWithCharVsStringList, endWithCharVsStringList, paths, path, endStringLengthVsCharacter);
                }
            });
        }

        path.remove(sourceVertex);
    }
}
