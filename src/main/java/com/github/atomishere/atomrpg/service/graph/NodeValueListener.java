package com.github.atomishere.atomrpg.service.graph;

import org.jetbrains.annotations.NotNull;

/**
 * The main mechanism used for notifying the outside of the fact that a node just got its evaluation
 *
 * @author nicolae caralicea
 */
public interface NodeValueListener<T> {

    /**
     * The callback method used to notify the fact that a node that has assigned the nodeValue value just got its
     * evaluation
     *
     * @param nodeValue The user set value of the node that just got the evaluation
     */
    void evaluating(@NotNull T nodeValue);
}
