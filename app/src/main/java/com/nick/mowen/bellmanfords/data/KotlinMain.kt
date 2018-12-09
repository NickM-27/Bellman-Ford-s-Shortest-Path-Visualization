package com.nick.mowen.bellmanfords.data

import java.util.*
import kotlin.collections.ArrayList

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

    fun main(args: Array<String>) {

        fun printMenu() {
            print(
                """
                    1: Add Edge
                    2: Run Bellman Fords
                    3: Time Test
                   4+: Quit
                   Choice:
                """.trimIndent()
            )
        }

        val `in` = Scanner(System.`in`)
        printMenu()
        var choice = `in`.nextLine().toIntOrNull() ?: 4
        val list = ArrayList<Graph.Edge>()

        do {
            when (choice) {
                1 -> {
                    print("Enter Start End Weight: ")
                    list.add(Graph.Edge(`in`.nextInt(), `in`.nextInt(), `in`.nextInt()))
                }
                2 -> {
                    print("Enter source vertex: ")
                    println("\nResults: ")
                    ShortestPath.findShortestPath(Graph(list.getVertexCount(), list.size, list.toTypedArray()), `in`.nextInt()).forEach {
                        print("Distance: ${it.cost} Link: ${it.name}")
                    }
                }
                3 -> {
                    print("Enter source vertex: ")
                    val index = `in`.nextInt()
                    val start = System.currentTimeMillis()
                    ShortestPath.findShortestPath(Graph(list.getVertexCount(), list.size, list.toTypedArray()), index)
                    println("Took ${start - System.currentTimeMillis()} milliseconds")
                }
            }

            printMenu()
            choice = `in`.nextInt()
        } while (choice < 4)
    }

    private fun ArrayList<Graph.Edge>.getVertexCount(): Int =
        this.distinctBy { it.source }.size + this.distinctBy { it.destination }.size
}