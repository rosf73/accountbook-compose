package com.woowacamp.android_accountbook_15.ui.tabs.graph

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.woowacamp.android_accountbook_15.data.model.Category
import com.woowacamp.android_accountbook_15.ui.components.CategoryView
import com.woowacamp.android_accountbook_15.ui.theme.LightPurple
import com.woowacamp.android_accountbook_15.utils.toMoneyString
import kotlin.math.round

@Composable
fun AnimatedGraphCard(
    proportions: List<Float>,
    colors: List<Color>,
    modifier: Modifier = Modifier
) {
    val animateFloat = remember { Animatable(0f) }
    LaunchedEffect(animateFloat) {
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
    modifier: Modifier = Modifier
) {
    val totalExpenses = expenses.values.sum()
    LazyColumn(modifier = modifier) {
        item {
            expenses.forEach { (key, value) ->
                Row(
                    modifier = Modifier.padding(0.dp, 8.dp),
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