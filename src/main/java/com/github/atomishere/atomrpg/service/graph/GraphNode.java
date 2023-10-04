package com.github.atomishere.atomrpg.service.graph;

import java.util.ArrayList;
import java.util.List;

final class GraphNode<T> {
    T value;
    private List<GraphNode<T>> comingInNodes;
    private List<GraphNode<T>> goingOutNodes;

    void addComingInNode(GraphNode<T> node) {
        if(comingInNodes == null) {
            comingInNodes = new ArrayList<>();
        }
        comingInNodes.add(node);
    }

    void addGoingOutNode(GraphNode<T> node) {
        if(goingOutNodes == null) {
            goingOutNodes = new ArrayList<>();
        }
        goingOutNodes.add(node);
    }

    List<GraphNode<T>> getComingInNodes() {
        return comingInNodes;
    }

    List<GraphNode<T>> getGoingOutNodes() {
        return goingOutNodes;
    }
}
