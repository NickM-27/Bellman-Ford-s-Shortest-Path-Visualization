package com.nick.mowen.bellmanfords.data

data class Graph(var vertexCount: Int, var edgeCount: Int, val edges: Array<Edge>) {

    constructor(vertexCount: Int, edgeCount: Int) : this(
        vertexCount,
        edgeCount,
        List(edgeCount) { Edge() }.toTypedArray()
    )

    data class Edge(var source: Int = 0, var destination: Int = 0, var weight: Int = 0, val onObserve: ((Boolean) -> Unit)? = null)
}