package com.nick.mowen.bellmanfords.ui

import android.app.ActivityOptions
import android.content.Intent
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.view.ViewAnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.animation.doOnEnd
import androidx.core.view.isInvisible
import androidx.databinding.DataBindingUtil
import androidx.transition.TransitionManager
import com.nick.mowen.bellmanfords.R
import com.nick.mowen.bellmanfords.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        animateIntro()
    }

    private fun animateIntro() {

        suspend fun animatePath() {
            delay(3000)
            (binding.image.drawable as AnimatedVectorDrawable).start()
            delay(2000)
            binding.content.run {
                ViewAnimationUtils.createCircularReveal(this, width / 2, height / 2, Math.hypot(width.toDouble(), height.toDouble()).toFloat(), 0f).apply {
                    duration = 600
                    doOnEnd {
                        isInvisible = true
                        startActivity(Intent(context, PathActivity::class.java), ActivityOptions.makeSceneTransitionAnimation(this@MainActivity).toBundle())
                    }
                    start()
                }
            }
        }

        GlobalScope.launch(Dispatchers.Main) {
            val set = ConstraintSet().also { it.clone(this@MainActivity, R.layout.main_keyframe) }
            TransitionManager.beginDelayedTransition(binding.content)
            set.applyTo(binding.content)
            animatePath()
        }
    }
}