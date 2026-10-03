package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

@Composable
fun FlaskGlassware(
    modifier: Modifier = Modifier,
    fillLevel: Float = 0.5f, // 0.0 to 1.0
    liquidColor: Color = Color(0xFF00E5FF),
    isBubbling: Boolean = true,
    isHeating: Boolean = false,
    precipitateColor: Color? = null
) {
    val infiniteTransition = rememberInfiniteTransition(label = "flask_anim")
    val waveOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 6.28f,
        animationSpec = infiniteRepeatable(
            animation = tween(1800, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "wave"
    )

    val bubbleAnim by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "bubble"
    )

    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        // Flask dimensions
        val neckWidth = w * 0.22f
        val neckTop = h * 0.12f
        val neckBottom = h * 0.40f
        val baseWidth = w * 0.84f
        val baseBottom = h * 0.90f
        val flaskRimWidth = neckWidth * 1.3f

        // Draw Liquid clip path inside Erlenmeyer flask
        val currentFill = fillLevel.coerceIn(0.05f, 0.95f)
        val liquidTopY = baseBottom - (baseBottom - neckBottom) * currentFill

        // Calculate flask width at liquidTopY
        val t = ((liquidTopY - neckBottom) / (baseBottom - neckBottom)).coerceIn(0f, 1f)
        val halfWidthAtY = (neckWidth / 2f) + (1f - t) * ((baseWidth - neckWidth) / 2f)
        val centerX = w / 2f

        val liquidPath = Path().apply {
            moveTo(centerX - halfWidthAtY, liquidTopY)
            // Liquid surface slight wave
            val waveAmplitude = if (isHeating) 6f else 3f
            val segments = 10
            val step = (halfWidthAtY * 2f) / segments
            for (i in 1..segments) {
                val x = centerX - halfWidthAtY + (i * step)
                val y = liquidTopY + sin(waveOffset + i) * waveAmplitude
                lineTo(x, y)
            }
            lineTo(centerX + (baseWidth / 2f) - 6f, baseBottom - 4f)
            quadraticTo(centerX, baseBottom, centerX - (baseWidth / 2f) + 6f, baseBottom - 4f)
            close()
        }

        // Draw liquid
        drawPath(
            path = liquidPath,
            brush = Brush.verticalGradient(
                colors = listOf(
                    liquidColor.copy(alpha = 0.75f),
                    liquidColor.copy(alpha = 0.95f)
                ),
                startY = liquidTopY,
                endY = baseBottom
            )
        )

        // Precipitate layer if any
        if (precipitateColor != null) {
            val pPath = Path().apply {
                moveTo(centerX - (baseWidth / 2f) + 12f, baseBottom - 18f)
                lineTo(centerX + (baseWidth / 2f) - 12f, baseBottom - 18f)
                lineTo(centerX + (baseWidth / 2f) - 6f, baseBottom - 4f)
                quadraticTo(centerX, baseBottom, centerX - (baseWidth / 2f) + 6f, baseBottom - 4f)
                close()
            }
            drawPath(path = pPath, color = precipitateColor.copy(alpha = 0.92f))
        }

        // Draw Bubbles
        if (isBubbling) {
            val bubbleCount = if (isHeating) 9 else 5
            for (i in 0 until bubbleCount) {
                val seed = i * 47
                val xProgress = (sin(seed.toDouble()) * 0.5 + 0.5).toFloat()
                val bubbleX = centerX - halfWidthAtY * 0.6f + xProgress * (halfWidthAtY * 1.2f)
                val bProgress = (bubbleAnim + (i / bubbleCount.toFloat())) % 1f
                val bubbleY = baseBottom - (baseBottom - liquidTopY) * bProgress
                val radius = 3.5f + (i % 3) * 1.5f

                if (bubbleY > liquidTopY && bubbleY < baseBottom) {
                    drawCircle(
                        color = Color.White.copy(alpha = 0.7f),
                        radius = radius,
                        center = Offset(bubbleX, bubbleY)
                    )
                }
            }
        }

        // Draw Flask Glass Outline
        val glassPath = Path().apply {
            // Lip rim
            moveTo(centerX - flaskRimWidth / 2f, neckTop - 4f)
            lineTo(centerX + flaskRimWidth / 2f, neckTop - 4f)
            lineTo(centerX + neckWidth / 2f, neckTop)
            // Right neck
            lineTo(centerX + neckWidth / 2f, neckBottom)
            // Right slope to base
            lineTo(centerX + baseWidth / 2f, baseBottom - 8f)
            // Base rounded corner
            quadraticTo(centerX + baseWidth / 2f, baseBottom, centerX + baseWidth / 2f - 14f, baseBottom)
            // Flat base
            lineTo(centerX - baseWidth / 2f + 14f, baseBottom)
            quadraticTo(centerX - baseWidth / 2f, baseBottom, centerX - baseWidth / 2f, baseBottom - 8f)
            // Left slope to neck
            lineTo(centerX - neckWidth / 2f, neckBottom)
            // Left neck
            lineTo(centerX - neckWidth / 2f, neckTop)
            lineTo(centerX - flaskRimWidth / 2f, neckTop - 4f)
        }

        // Glass highlight
        drawPath(
            path = glassPath,
            color = Color.White.copy(alpha = 0.85f),
            style = Stroke(width = 3.5f, cap = StrokeCap.Round)
        )

        // Glass reflection glare curve
        val glarePath = Path().apply {
            moveTo(centerX - baseWidth * 0.38f, baseBottom - 16f)
            lineTo(centerX - neckWidth * 0.35f, neckBottom + 20f)
        }
        drawPath(
            path = glarePath,
            color = Color.White.copy(alpha = 0.35f),
            style = Stroke(width = 2.5f, cap = StrokeCap.Round)
        )

        // Graduated Volume Markings
        for (m in 1..3) {
            val markY = baseBottom - (baseBottom - neckBottom) * (m * 0.25f)
            drawLine(
                color = Color.White.copy(alpha = 0.6f),
                start = Offset(centerX + 8f, markY),
                end = Offset(centerX + 26f, markY),
                strokeWidth = 2f
            )
        }
    }
}

@Composable
fun BunsenBurnerCanvas(
    modifier: Modifier = Modifier,
    isIgnited: Boolean = true,
    flameType: String = "Roaring Blue" // "Roaring Blue", "Safety Yellow", "Lilac", "Golden Yellow", "Emerald Green", "Brick Red"
) {
    val infiniteTransition = rememberInfiniteTransition(label = "flame_anim")
    val flameFlicker by infiniteTransition.animateFloat(
        initialValue = 0.9f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(220, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "flicker"
    )

    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val cx = w / 2f

        // Burner Base
        drawRoundRect(
            color = Color(0xFF334155),
            topLeft = Offset(cx - w * 0.35f, h * 0.82f),
            size = Size(w * 0.70f, h * 0.15f),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(6f, 6f)
        )

        // Burner Barrel (Tube)
        drawRect(
            brush = Brush.horizontalGradient(
                colors = listOf(Color(0xFF64748B), Color(0xFF94A3B8), Color(0xFF475569)),
                startX = cx - w * 0.08f,
                endX = cx + w * 0.08f
            ),
            topLeft = Offset(cx - w * 0.08f, h * 0.45f),
            size = Size(w * 0.16f, h * 0.38f)
        )

        // Air hole collar
        drawCircle(
            color = Color(0xFF1E293B),
            radius = w * 0.035f,
            center = Offset(cx, h * 0.72f)
        )

        // Flame if ignited
        if (isIgnited) {
            val flameTop = (h * 0.08f) * flameFlicker
            val flameBottom = h * 0.45f

            val (outerFlameColor, innerFlameColor) = when (flameType) {
                "Safety Yellow" -> Color(0xFFFBBF24) to Color(0xFFF59E0B)
                "Lilac" -> Color(0xFFA855F7) to Color(0xFFC084FC)
                "Golden Yellow" -> Color(0xFFF59E0B) to Color(0xFFFDE047)
                "Emerald Green" -> Color(0xFF10B981) to Color(0xFF34D399)
                "Brick Red" -> Color(0xFFEF4444) to Color(0xFFF87171)
                else -> Color(0xFF0284C7) to Color(0xFF38BDF8) // Roaring blue
            }

            // Outer flame
            val outerFlamePath = Path().apply {
                moveTo(cx - w * 0.14f, flameBottom)
                cubicTo(
                    cx - w * 0.18f, flameBottom - (flameBottom - flameTop) * 0.4f,
                    cx - w * 0.08f, flameTop,
                    cx, flameTop
                )
                cubicTo(
                    cx + w * 0.08f, flameTop,
                    cx + w * 0.18f, flameBottom - (flameBottom - flameTop) * 0.4f,
                    cx + w * 0.14f, flameBottom
                )
                close()
            }

            drawPath(
                path = outerFlamePath,
                brush = Brush.verticalGradient(
                    colors = listOf(outerFlameColor.copy(alpha = 0.85f), outerFlameColor.copy(alpha = 0.5f)),
                    startY = flameTop,
                    endY = flameBottom
                )
            )

            // Inner hot core
            val innerTop = flameTop + (flameBottom - flameTop) * 0.45f
            val innerFlamePath = Path().apply {
                moveTo(cx - w * 0.06f, flameBottom)
                cubicTo(
                    cx - w * 0.08f, flameBottom - 25f,
                    cx - w * 0.03f, innerTop,
                    cx, innerTop
                )
                cubicTo(
                    cx + w * 0.03f, innerTop,
                    cx + w * 0.08f, flameBottom - 25f,
                    cx + w * 0.06f, flameBottom
                )
                close()
            }

            drawPath(
                path = innerFlamePath,
                brush = Brush.verticalGradient(
                    colors = listOf(Color.White, innerFlameColor),
                    startY = innerTop,
                    endY = flameBottom
                )
            )
        }
    }
}

@Composable
fun BuretteGlassware(
    modifier: Modifier = Modifier,
    liquidVolumeMl: Float = 35f, // 0 to 50 mL
    isDripping: Boolean = false,
    liquidColor: Color = Color(0x3338BDF8)
) {
    val infiniteTransition = rememberInfiniteTransition(label = "drip_anim")
    val dropProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(700, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "drop"
    )

    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val cx = w / 2f

        val tubeWidth = w * 0.18f
        val tubeTop = h * 0.05f
        val tubeBottom = h * 0.72f
        val tapY = h * 0.76f
        val nozzleBottom = h * 0.88f

        // Burette barrel
        drawRect(
            color = Color.White.copy(alpha = 0.15f),
            topLeft = Offset(cx - tubeWidth / 2f, tubeTop),
            size = Size(tubeWidth, tubeBottom - tubeTop)
        )

        // Liquid inside burette
        val fillPercent = ((50f - liquidVolumeMl) / 50f).coerceIn(0f, 1f)
        val liquidTop = tubeTop + (tubeBottom - tubeTop) * (1f - fillPercent)
        drawRect(
            color = liquidColor,
            topLeft = Offset(cx - tubeWidth / 2f + 2f, liquidTop),
            size = Size(tubeWidth - 4f, tubeBottom - liquidTop)
        )

        // Graduated markings
        for (i in 0..10) {
            val y = tubeTop + (tubeBottom - tubeTop) * (i / 10f)
            val lineLength = if (i % 5 == 0) tubeWidth * 0.6f else tubeWidth * 0.35f
            drawLine(
                color = Color.White.copy(alpha = 0.7f),
                start = Offset(cx + tubeWidth / 2f - lineLength, y),
                end = Offset(cx + tubeWidth / 2f, y),
                strokeWidth = 1.5f
            )
        }

        // Glass tube outline
        drawRect(
            color = Color.White.copy(alpha = 0.85f),
            topLeft = Offset(cx - tubeWidth / 2f, tubeTop),
            size = Size(tubeWidth, tubeBottom - tubeTop),
            style = Stroke(width = 2.5f)
        )

        // Tap Stopcock
        drawRoundRect(
            color = Color(0xFFEF4444),
            topLeft = Offset(cx - tubeWidth * 0.9f, tapY - 4f),
            size = Size(tubeWidth * 1.8f, 10f),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(3f, 3f)
        )

        // Delivery Nozzle tip
        val tipPath = Path().apply {
            moveTo(cx - tubeWidth * 0.25f, tubeBottom)
            lineTo(cx - tubeWidth * 0.1f, nozzleBottom)
            lineTo(cx + tubeWidth * 0.1f, nozzleBottom)
            lineTo(cx + tubeWidth * 0.25f, tubeBottom)
        }
        drawPath(path = tipPath, color = Color.White.copy(alpha = 0.9f), style = Stroke(width = 2f))

        // Falling Drop
        if (isDripping) {
            val dropY = nozzleBottom + (h - nozzleBottom) * dropProgress
            drawCircle(
                color = liquidColor.copy(alpha = 0.95f),
                radius = 3.5f,
                center = Offset(cx, dropY)
            )
        }
    }
}

@Composable
fun MoleculeCanvas(
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "mol_anim")
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(12000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rot"
    )

    Canvas(modifier = modifier) {
        val cx = size.width / 2f
        val cy = size.height / 2f
        val radius = size.width * 0.32f

        // Central atom
        drawCircle(
            color = Color(0xFF38BDF8),
            radius = size.width * 0.12f,
            center = Offset(cx, cy)
        )

        // Surrounding bonded atoms
        for (i in 0 until 3) {
            val atomAngle = Math.toRadians((angle + i * 120.0))
            val ax = cx + (radius * cos(atomAngle)).toFloat()
            val ay = cy + (radius * sin(atomAngle)).toFloat()

            // Bond line
            drawLine(
                color = Color.White.copy(alpha = 0.5f),
                start = Offset(cx, cy),
                end = Offset(ax, ay),
                strokeWidth = 3f
            )

            // Outer atom
            val outerColor = when (i) {
                0 -> Color(0xFF10B981)
                1 -> Color(0xFFF59E0B)
                else -> Color(0xFFA855F7)
            }
            drawCircle(
                color = outerColor,
                radius = size.width * 0.08f,
                center = Offset(ax, ay)
            )
        }
    }
}
