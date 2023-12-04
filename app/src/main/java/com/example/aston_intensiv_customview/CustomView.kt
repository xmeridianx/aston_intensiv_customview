package com.example.aston_intensiv_customview

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import kotlin.math.min
import kotlin.random.Random

class CustomView (context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {

    private lateinit var customTextView: TextView
    private lateinit var customImageView: ImageView
    val imageUrl = "https://loremflickr.com/320/240/dog"

    fun setTargetTextView(textView: TextView) {
        this.customTextView = textView
    }

    fun setTargetImageView(imageView: ImageView){
        this.customImageView = imageView
    }

    private val paint = Paint()
    private val colors = listOf(
        Color.RED,
        Color.rgb(255, 165, 0),
        Color.YELLOW,
        Color.GREEN,
        Color.CYAN,
        Color.BLUE,
        Color.MAGENTA
    )
    private val sweepAngle = 360f / colors.size
    private var startAngle = 0f
    val arrow = ContextCompat.getDrawable(this.context, R.drawable.baseline_keyboard_arrow_down_24)

    init {
        paint.style = Paint.Style.FILL
        paint.color = Color.BLUE

    }

    fun spin() {
        val spinTime = Random.nextLong(1000, 3000)
        val spinCount = Random.nextInt(720, 1500)
        val valueAnimator = ValueAnimator.ofFloat(startAngle, startAngle + spinCount)
        valueAnimator.duration = spinTime
        valueAnimator.addUpdateListener { animation ->
            startAngle = (animation.animatedValue as Float) % 360f
            this.invalidate()
        }
        valueAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator) {

            }

            override fun onAnimationEnd(p0: Animator) {
                for (i in 0 until colors.size) {
                    val endAngle = startAngle + i * sweepAngle + sweepAngle
                    if ( 270f <= endAngle % 360){
                        if (i == 0 || i == 2 || i == 4 || i == 6) {
                            customImageView.visibility = INVISIBLE
                            customTextView.visibility = VISIBLE
                            customTextView.setTextColor(colors[i])
                        } else if (i == 1 || i == 3 || i == 5) {
                            loadPicture()
                            customTextView.visibility = INVISIBLE
                            customImageView.visibility = VISIBLE
                        }
                        break
                    }
                }
            }

            override fun onAnimationCancel(p0: Animator) {

            }

            override fun onAnimationRepeat(p0: Animator) {

            }

        })
        valueAnimator.start()
    }

    private fun loadPicture() {
        Glide.with(this)
            .load(imageUrl)
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(customImageView)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawCircleButton(canvas)
        drawArrow(canvas)
    }

    private fun drawCircleButton(canvas: Canvas) {

        val centerX = width / 2f
        val centerY = height / 2f
        val radius = min(centerX, centerY)

        for (i in 0 until colors.size) {
            paint.color = colors[i]
            canvas.drawArc(
                centerX - radius,
                centerY - radius,
                centerX + radius,
                centerY + radius,
                startAngle + i * sweepAngle,
                sweepAngle,
                true,
                paint
            )
        }
    }

    private fun drawArrow(canvas: Canvas) {
        val centerX = width / 2f
        val centerY = height / 2f
        val radius = min(centerX, centerY)
        val wightArrow = 100
        val heightArrow = 100
        arrow?.setBounds(
            (centerX - wightArrow / 2f).toInt(),
            ((centerY - heightArrow / 2) - radius).toInt(),
            (centerX + wightArrow / 2).toInt(),
            (centerY + heightArrow / 2 - radius).toInt()
        )
        arrow?.draw(canvas)
    }

    fun setSize(newSize: Float) {

        layoutParams.width = (newSize * 200).toInt()
        layoutParams.height = (newSize * 200).toInt()
        requestLayout()
    }
}