package com.github.atomishere.atomrpg.service.graph;

import java.util.*;

public class Graph<T> {
    private Map<T, GraphNode<T>> nodes = new HashMap<>();
    private NodeValueListener<T> listener;
    private List<GraphNode<T>> evaluatedNodes = new ArrayList<>();

    public Graph(NodeValueListener<T> listener) {
        this.listener = listener;
    }

    public void addDependency(T evalFirstValue, T evalAfterValue) {
        GraphNode<T> firstNode;
        GraphNode<T> afterNode;
        if(nodes.containsKey(evalFirstValue)) {
            firstNode = nodes.get(evalFirstValue);
        } else {
            firstNode = createNode(evalFirstValue);
            nodes.put(evalFirstValue, firstNode);
        }
        if(nodes.containsKey(evalAfterValue)) {
            afterNode = nodes.get(evalAfterValue);
        } else {
            afterNode = createNode(evalAfterValue);
            nodes.put(evalAfterValue, afterNode);
        }
        firstNode.addGoingOutNode(afterNode);
        afterNode.addComingInNode(firstNode);
    }

    public GraphNode<T> createNode(T value) {
        GraphNode<T> node = new GraphNode<>();
        node.value = value;
        return node;
    }

    public void generateDependencies() {
        List<GraphNode<T>> orphanNodes = getOrphanNodes();
        if(orphanNodes == null) {
            throw new RuntimeException("No orphan node found: we got circular dependencies!");
        }

        List<GraphNode<T>> nextNodesToDisplay = new ArrayList<>();
        for(GraphNode<T> node : orphanNodes) {
            listener.evaluating(node.value);
            evaluatedNodes.add(node);
            nextNodesToDisplay.addAll(node.getGoingOutNodes());
        }
        generateDependencies(nextNodesToDisplay);
    }

    private void generateDependencies(List<GraphNode<T>> nodes) {
        List<GraphNode<T>> nextNodesToDisplay = null;
        for(GraphNode<T> node : nodes) {
            if(!isAlreadyEvaluated(node)) {
                List<GraphNode<T>> comingInNodes = node.getGoingOutNodes();
                if(areAlreadyEvaluated(comingInNodes)) {
                    listener.evaluating(node.value);
                    evaluatedNodes.add(node);
                    List<GraphNode<T>> goingOutNodes = node.getGoingOutNodes();
                    if(goingOutNodes != null) {
                        if(nextNodesToDisplay == null) {
                            nextNodesToDisplay = new ArrayList<>();
                        }
                        nextNodesToDisplay.addAll(goingOutNodes);
                    }
                } else {
                    if(nextNodesToDisplay == null) {
                        nextNodesToDisplay = new ArrayList<>();
                    }
                    nextNodesToDisplay.add(node);
                }
            }
        }
        if(nextNodesToDisplay != null) {
            generateDependencies(nextNodesToDisplay);
        }
    }

    private boolean isAlreadyEvaluated(GraphNode<T> node) {
        return evaluatedNodes.contains(node);
    }

    private boolean areAlreadyEvaluated(List<GraphNode<T>> nodes) {
        return evaluatedNodes.containsAll(nodes);
    }

    private List<GraphNode<T>> getOrphanNodes() {
        List<GraphNode<T>> orphanNodes = null;
        Set<T> keys = nodes.keySet();
        for(T key : keys) {
            GraphNode<T> node = nodes.get(key);
            if(node.getComingInNodes() == null) {
                if(orphanNodes == null) {
                    orphanNodes = new ArrayList<>();
                }
                orphanNodes.add(node)
            }
        }
        return orphanNodes;
    }

    public int size() {
        return nodes.size();
    }
}
