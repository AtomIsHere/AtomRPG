package com.github.atomishere.atomrpg.service.graph;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * It represents the node of the graph. It holds a user value that is passed back to the user when a node gets the
 * chance to be evaluated.
 *
 * @author nicolae caralicea
 */
final class GraphNode<T> {

    T value;
    private List<GraphNode<T>> comingInNodes;
    private List<GraphNode<T>> goingOutNodes;

    /**
     * Adds an incoming node to the current node
     *
     * @param node The incoming node
     */
    void addComingInNode(@NotNull GraphNode<T> node) {
        if (comingInNodes == null) {
            comingInNodes = new ArrayList<>();
        }
        comingInNodes.add(node);
    }

    /**
     * Adds an outgoing node from the current node
     *
     * @param node The outgoing node
     */
    void addGoingOutNode(@NotNull GraphNode<T> node) {
        if (goingOutNodes == null) {
            goingOutNodes = new ArrayList<>();
        }
        goingOutNodes.add(node);
    }

    /**
     * Provides all the coming in nodes
     *
     * @return The coming in nodes
     */
    @NotNull
    List<GraphNode<T>> getComingInNodes() {
        return comingInNodes;
    }

    /**
     * Provides all the going out nodes
     *
     * @return The going out nodes
     */
    @NotNull
    List<GraphNode<T>> getGoingOutNodes() {
        return goingOutNodes;
    }
}
