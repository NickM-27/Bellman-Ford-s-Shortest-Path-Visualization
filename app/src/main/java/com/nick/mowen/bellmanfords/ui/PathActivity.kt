package com.nick.mowen.bellmanfords.ui

import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.nick.mowen.bellmanfords.R
import com.nick.mowen.bellmanfords.data.Graph
import com.nick.mowen.bellmanfords.data.ShortestPath
import com.nick.mowen.bellmanfords.databinding.ActivityPathBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PathActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPathBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_path)
        AlertDialog.Builder(this)
            .setTitle(R.string.title_algorithm_simulator)
            .setMessage(R.string.message_algorithm_simulator)
            .setPositiveButton(getString(R.string.action_start).toUpperCase()) { _, _ -> animateShortestPath() }
            .show()
    }

    private fun animateShortestPath() = GlobalScope.launch(Dispatchers.Main) {

        fun View.setColorFilter(selected: Boolean) {
            background?.let {
                if (selected)
                    it.setColorFilter(
                        ContextCompat.getColor(
                            this@PathActivity,
                            if (selected) R.color.colorAccent else android.R.color.white
                        ), PorterDuff.Mode.SRC_IN
                    )
                else
                    it.colorFilter = null
            }
        }

        var costs = arrayOf(0)
        ShortestPath.animateShortestPath(Graph(5, 5, arrayOf(
            Graph.Edge(0, 1, 10000) { binding.edge1.setColorFilter(it) },
            Graph.Edge(0, 2, -5000) { binding.edge2.setColorFilter(it) },
            Graph.Edge(1, 3, -20000) { binding.edge3.setColorFilter(it) },
            Graph.Edge(2, 3, 8000) { binding.edge4.setColorFilter(it) },
            Graph.Edge(3, 4, -12000) { binding.edge5.setColorFilter(it) }
        )), 0) { values, position, enabled ->
            costs = values

            if (!enabled)
                Toast.makeText(
                    this@PathActivity,
                    """
                Cost for start: $${costs[0] * -1}
                Cost for plan 1: $${costs[1] * -1}
                Cost for plan 2: $${costs[2] * -1}
                Cost for plan 3: $${costs[3] * -1}
                Cost for end: $${costs[4] * -1}
            """.trimIndent(),
                    Toast.LENGTH_LONG
                ).show()

            when (position) {
                0 -> binding.start
                1 -> binding.plan1
                2 -> binding.plan2
                3 -> binding.plan3
                4 -> binding.end
                else -> throw IllegalStateException("Not a valid view index")
            }.setColorFilter(enabled)
        }
        binding.cost = costs[4] * -1
    }

    fun explain(@Suppress("UNUSED_PARAMETER") view: View?) {
        AlertDialog.Builder(this)
            .setTitle(R.string.title_algorithm_explanation)
            .setMessage(R.string.message_algorithm_explanation)
            .show()
    }
}