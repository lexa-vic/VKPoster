package com.kostikov.vkposter.textstyle

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.text.style.LineBackgroundSpan

/**
 * @author Kostikov Aleksey.
 */
class RoundBackgroundSpan(backgroundColor: Int, private val padding: Float, private val radius: Float) : LineBackgroundSpan {

    private val rect = RectF()
    private val paint = Paint()
    private val paintStroke = Paint()
    private val path = Path()

    private var prevWidth = -1f
    private var prevLeft = -1f
    private var prevRight = -1f
    private var prevBottom = -1f
    private var prevTop = -1f


    init {

        paint.color = backgroundColor
        //paintStroke.setStyle(Paint.Style.STROKE);
        //paintStroke.setStrokeWidth(5f);
        paintStroke.color = backgroundColor
    }

    override fun drawBackground(
        c: Canvas,
        p: Paint,
        left: Int,
        right: Int,
        top: Int,
        baseline: Int,
        bottom: Int,
        text: CharSequence,
        start: Int,
        end: Int,
        lnum: Int
    ) {

        val width = p.measureText(text, start, end) + 2f * padding
        var shift = (right - width) / 2f
        if (shift < 0) {
            shift = 0f
        }

        rect.set(shift, top.toFloat(), right - shift, bottom.toFloat())

        if (lnum == 0) {
            c.drawRoundRect(rect, radius, radius, paint)
        } else {
            path.reset()
            val dr = width - prevWidth
            val diff = -Math.signum(dr) * Math.min(2f * radius, Math.abs(dr / 2f)) / 2f

            path.moveTo(
                prevLeft, prevBottom - radius
            )

            path.cubicTo(
                prevLeft, prevBottom - radius,
                prevLeft, rect.top,
                prevLeft + diff, rect.top
            )
            path.lineTo(
                rect.left - diff, rect.top
            )
            path.cubicTo(
                rect.left - diff, rect.top,
                rect.left, rect.top,
                rect.left, rect.top + radius
            )
            path.lineTo(
                rect.left, rect.bottom - radius
            )
            path.cubicTo(
                rect.left, rect.bottom - radius,
                rect.left, rect.bottom,
                rect.left + radius, rect.bottom
            )
            path.lineTo(
                rect.right - radius, rect.bottom
            )
            path.cubicTo(
                rect.right - radius, rect.bottom,
                rect.right, rect.bottom,
                rect.right, rect.bottom - radius
            )
            path.lineTo(
                rect.right, rect.top + radius
            )
            path.cubicTo(
                rect.right, rect.top + radius,
                rect.right, rect.top,
                rect.right + diff, rect.top
            )
            path.lineTo(
                prevRight - diff, rect.top
            )
            path.cubicTo(
                prevRight - diff, rect.top,
                prevRight, rect.top,
                prevRight, prevBottom - radius
            )
            path.cubicTo(
                prevRight, prevBottom - radius,
                prevRight, prevBottom,
                prevRight - radius, prevBottom

            )
            path.lineTo(
                prevLeft + radius, prevBottom
            )
            path.cubicTo(
                prevLeft + radius, prevBottom,
                prevLeft, prevBottom,
                prevLeft, rect.top - radius
            )
            c.drawPath(path, paintStroke)
        }

        prevWidth = width
        prevLeft = rect.left
        prevRight = rect.right
        prevBottom = rect.bottom
        prevTop = rect.top
    }
}