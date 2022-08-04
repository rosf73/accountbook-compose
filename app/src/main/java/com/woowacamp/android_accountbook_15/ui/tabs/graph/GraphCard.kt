package com.woowacamp.android_accountbook_15.ui.tabs.graph

import android.R.attr.text
import android.graphics.Rect
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.woowacamp.android_accountbook_15.data.model.Category
import com.woowacamp.android_accountbook_15.ui.components.CategoryView
import com.woowacamp.android_accountbook_15.ui.theme.LightPurple
import com.woowacamp.android_accountbook_15.ui.theme.Purple
import com.woowacamp.android_accountbook_15.ui.theme.PurpleLong
import com.woowacamp.android_accountbook_15.utils.toMoneyString
import kotlin.math.round


@Composable
fun AnimatedGraphCard(
    graphState: Boolean,
    proportions: List<Float>,
    colors: List<Color>,
    modifier: Modifier = Modifier
) {
    var animateFloat = remember { Animatable(0f) }
    LaunchedEffect(animateFloat, graphState) {
        animateFloat = Animatable(0f)
        animateFloat.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 1000, easing = LinearEasing))
    }

    Canvas(modifier) {
        val innerRadius = (size.minDimension - 45f) / 2
        val halfSize = size / 2.0f
        val topLeft = Offset(
            halfSize.width - innerRadius,
            halfSize.height - innerRadius
        )
        val size = Size(innerRadius * 2, innerRadius * 2)
        var startAngle = -90f
        val totalProportion = proportions.sum()
        proportions.forEachIndexed { index, proportion ->
            val sweep = proportion/totalProportion * 360 * animateFloat.value
            drawArc(
                color = colors[index],
                startAngle = startAngle,
                sweepAngle = sweep,
                size = size,
                topLeft = topLeft,
                useCenter = false,
                style = Stroke(45f)
            )
            startAngle += sweep
        }
    }
}

@Composable
fun ExpensesCard(
    expenses: Map<Category, Float>,
    modifier: Modifier = Modifier,
    onClick: (Long) -> Unit
) {
    val totalExpenses = expenses.values.sum()
    LazyColumn(modifier = modifier) {
        item {
            expenses.forEach { (key, value) ->
                Row(
                    modifier = Modifier
                        .padding(0.dp, 8.dp)
                        .clickable {
                            onClick(key.id)
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CategoryView(color = key.color, name = key.name)
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(modifier = Modifier.weight(1f), text = value.toInt().toMoneyString(), fontSize = 14.sp)
                    Text(text = "${round(value/totalExpenses*100).toInt()}%", fontSize = 14.sp, fontWeight = FontWeight(700))
                }
                if (key != expenses.keys.last())
                    Divider(color = LightPurple, thickness = 1.dp)
            }
        }
    }
}

@Composable
fun ChartCard(
    amounts: List<Pair<Int, Long>>,
    modifier: Modifier = Modifier
) {
    val total = amounts.sumOf { it.second }
    val amountsPercentage = amounts.map { Pair(it.first, (round(it.second.toDouble()/total*100)).toInt()) }
    val monthCount = amountsPercentage.size

    Canvas(modifier = modifier
        .height(160.dp)
        .padding(16.dp, 32.dp)
    ) {
        val height = size.height
        val width = size.width
        val space = width/(monthCount*2)

        val textPaint = Paint().asFrameworkPaint().apply { // Canvas 위에 쓰기 위한 글씨
            isAntiAlias = true
            textSize = 12.sp.toPx()
            color = PurpleLong.toInt()
        }

        val prevAmount = amounts[0].second.toMoneyString()
        var prev = amountsPercentage[0]
        var prevX = space
        var prevY = height - height/100 * prev.second
        drawIntoCanvas { // 첫 번째 달의 amount 출력
            val rect = Rect() // text 의 크기
            textPaint.getTextBounds(prevAmount, 0, prevAmount.length, rect)

            it.nativeCanvas.drawText(prevAmount,
                prevX - rect.width().toFloat()/2,
                prevY + 16.dp.toPx() + rect.height().toFloat(),
                textPaint)
        }

        for (i in 1 until monthCount) { // 선과 글씨를 동시에 그리기
            val curr = amountsPercentage[i]
            val currAmount = amounts[i].second.toMoneyString()

            prevY = height - height/100 * prev.second
            val currX = prevX + space*2
            val currY = height - height/100 * curr.second
            drawLine(
                start = Offset(x = prevX, y = prevY),
                end = Offset(x = currX, y = currY),
                color = Purple,
                strokeWidth = 3.0f
            )

            drawIntoCanvas {
                val rect = Rect() // text 의 크기
                textPaint.getTextBounds(currAmount, 0, currAmount.length, rect)
                val textX = currX - rect.width().toFloat()/2
                val textY =
                    if (prev.second < curr.second) currY - 16.dp.toPx()
                    else currY + 16.dp.toPx() + rect.height().toFloat()
                it.nativeCanvas.drawText(currAmount, textX, textY, textPaint)
            }

            prevX += space*2
            prev = curr
        }
    }

    Row( // 마지막 x 좌표 그리기
        modifier = modifier.padding(16.dp, 8.dp)
    ) {
        for (e in amountsPercentage)
            Text(text = "${e.first}", textAlign = TextAlign.Center, fontSize = 14.sp,
                modifier = Modifier.weight(1f))
    }

    Divider(color = Purple, thickness = 1.dp)
}