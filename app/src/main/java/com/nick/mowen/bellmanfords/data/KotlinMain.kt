package com.nick.mowen.bellmanfords.data

object KotlinMain {

    fun main() {
        val vertexCount = 5
        val edgeCount = 8
        val graph = Graph(
            vertexCount, edgeCount, arrayOf(
                Graph.Edge(0, 1, -1),
                Graph.Edge(0, 2, 4),
                Graph.Edge(1, 2, 3),
                Graph.Edge(1, 3, 2),
                Graph.Edge(1, 4, 2),
                Graph.Edge(3, 2, 5),
                Graph.Edge(3, 1, 1),
                Graph.Edge(4, 3, -3)
            )
        )
        ShortestPath.findShortestPath(graph, 0).let {
            require(it[0].cost == 0)
            require(it[1].cost == -1)
            require(it[2].cost == 2)
            require(it[3].cost == -2)
            require(it[4].cost == 1)
        }
    }
}