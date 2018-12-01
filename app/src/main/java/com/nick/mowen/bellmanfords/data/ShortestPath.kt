package com.nick.mowen.bellmanfords.data

import kotlinx.coroutines.delay

object ShortestPath {

    suspend fun animateShortestPath(graph: Graph, source: Int, onObserve: (Array<Int>, Int, Boolean) -> Unit) {
        val vertexCount = graph.vertexCount
        val edgeCount = graph.edgeCount
        val distances = List(vertexCount) { Int.MAX_VALUE }.toTypedArray()
        distances[source] = 0

        for (i in 1 until vertexCount) {
            onObserve(distances, i, true)

            for (j in 0 until edgeCount) {
                graph.edges[j].onObserve?.invoke(true)
                val currentSource = graph.edges[j].source
                val currentDest = graph.edges[j].destination
                val currentWeight = graph.edges[j].weight

                if (distances[currentSource] != Int.MAX_VALUE && distances[currentSource] + currentWeight < distances[currentDest])
                    distances[currentDest] = distances[currentSource] + currentWeight

                delay(800)
                graph.edges[j].onObserve?.invoke(false)
            }

            onObserve(distances, i, false)
            delay(5000)
        }
    }

    fun findShortestPath(graph: Graph, source: Int): Array<Int> {
        val vertexCount = graph.vertexCount
        val edgeCount = graph.edgeCount
        val distances = List(vertexCount) { Int.MAX_VALUE }.toTypedArray()
        distances[source] = 0

        for (i in 1 until vertexCount) {
            for (j in 0 until edgeCount) {
                val currentSource = graph.edges[j].source
                val currentDest = graph.edges[j].destination
                val currentWeight = graph.edges[j].weight

                if (distances[currentSource] != Int.MAX_VALUE && distances[currentSource] + currentWeight < distances[currentDest])
                    distances[currentDest] = distances[currentSource] + currentWeight
            }
        }

        return distances
    }
}