package com.example.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.model.ElementCategory
import com.example.data.model.PeriodicElement
import com.example.data.repository.PeriodicTableData
import com.example.ui.ChemLabViewModel
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun PeriodicTableScreen(
    viewModel: ChemLabViewModel,
    modifier: Modifier = Modifier
) {
    val searchQuery by viewModel.periodicSearchQuery.collectAsState()
    val selectedCategory by viewModel.selectedElementCategory.collectAsState()
    val selectedElement by viewModel.selectedElement.collectAsState()

    var showDetailDialog by remember { mutableStateOf(false) }

    val categories = ElementCategory.values()

    val filteredElements = PeriodicTableData.elements.filter { elem ->
        val matchesCategory = selectedCategory == null || elem.category == selectedCategory
        val matchesSearch = searchQuery.isBlank() ||
                elem.symbol.contains(searchQuery, ignoreCase = true) ||
                elem.name.contains(searchQuery, ignoreCase = true) ||
                elem.number.toString() == searchQuery.trim()
        matchesCategory && matchesSearch
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("periodic_table_screen")
    ) {
        // SEARCH BAR
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { viewModel.setPeriodicSearchQuery(it) },
            placeholder = { Text("Search by element symbol, name, or atomic number...") },
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = "Search", tint = MaterialTheme.colorScheme.primary)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .testTag("periodic_search_field"),
            shape = RoundedCornerShape(14.dp),
            singleLine = true
        )

        // CATEGORY FILTER CHIPS
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                FilterChip(
                    selected = selectedCategory == null,
                    onClick = { viewModel.setElementCategoryFilter(null) },
                    label = { Text("All Elements") },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                )
            }
            items(categories) { cat ->
                FilterChip(
                    selected = selectedCategory == cat,
                    onClick = { viewModel.setElementCategoryFilter(cat) },
                    label = { Text(cat.label) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = Color(cat.colorHex).copy(alpha = 0.35f)
                    )
                )
            }
        }

        // GRID OF ELEMENTS
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 78.dp),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(filteredElements) { element ->
                ElementGridCard(
                    element = element,
                    onClick = {
                        viewModel.selectElement(element)
                        showDetailDialog = true
                    }
                )
            }
        }
    }

    // ELEMENT DETAIL DIALOG / SHEET
    if (showDetailDialog && selectedElement != null) {
        val elem = selectedElement!!
        Dialog(onDismissRequest = { showDetailDialog = false }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    // Header with symbol & close button
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = Color(elem.category.colorHex).copy(alpha = 0.25f),
                                modifier = Modifier.size(52.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(
                                        text = elem.symbol,
                                        style = MaterialTheme.typography.titleLarge,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = Color(elem.category.colorHex)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = elem.name,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Atomic #${elem.number} • Mass: ${elem.atomicMass} u",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        IconButton(onClick = { showDetailDialog = false }) {
                            Icon(Icons.Default.Close, contentDescription = "Close")
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Electron Shell Visualization Canvas
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        BohrModelCanvas(
                            modifier = Modifier.size(140.dp),
                            shells = elem.shells,
                            color = Color(elem.category.colorHex)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Periodic properties
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("Electron Config:", style = MaterialTheme.typography.labelSmall)
                                Text(elem.electronConfig, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelSmall)
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("Category:", style = MaterialTheme.typography.labelSmall)
                                Text(elem.category.label, color = Color(elem.category.colorHex), fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelSmall)
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("Group / Period / Block:", style = MaterialTheme.typography.labelSmall)
                                Text("G${elem.group}, P${elem.period}, ${elem.block}-block", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelSmall)
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("Melting / Boiling:", style = MaterialTheme.typography.labelSmall)
                                Text("${elem.meltingPointC} / ${elem.boilingPointC}", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelSmall)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text("Common Uses:", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
                    Text(elem.commonUses, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)

                    Spacer(modifier = Modifier.height(6.dp))

                    Text("Common Compounds:", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
                    Text(elem.commonCompounds.joinToString(", "), style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}

@Composable
fun ElementGridCard(
    element: PeriodicElement,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(82.dp)
            .testTag("element_${element.symbol}"),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(element.category.colorHex).copy(alpha = 0.15f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(6.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${element.number}",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = element.block,
                    fontSize = 9.sp,
                    color = Color(element.category.colorHex)
                )
            }

            Text(
                text = element.symbol,
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(element.category.colorHex),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Text(
                text = element.name,
                fontSize = 9.sp,
                maxLines = 1,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
fun BohrModelCanvas(
    modifier: Modifier = Modifier,
    shells: List<Int>,
    color: Color
) {
    Canvas(modifier = modifier) {
        val cx = size.width / 2f
        val cy = size.height / 2f
        val maxRadius = size.width * 0.44f

        // Nucleus
        drawCircle(
            color = color,
            radius = size.width * 0.10f,
            center = Offset(cx, cy)
        )

        // Orbital rings
        val numShells = shells.size.coerceAtLeast(1)
        shells.forEachIndexed { sIndex, count ->
            val r = maxRadius * ((sIndex + 1).toFloat() / numShells.toFloat())

            drawCircle(
                color = Color.White.copy(alpha = 0.25f),
                radius = r,
                center = Offset(cx, cy),
                style = Stroke(width = 1.5f)
            )

            // Electrons on ring
            val cappedCount = count.coerceAtMost(8)
            for (e in 0 until cappedCount) {
                val angle = (e * (360.0 / cappedCount)) * (Math.PI / 180.0)
                val ex = cx + (r * cos(angle)).toFloat()
                val ey = cy + (r * sin(angle)).toFloat()

                drawCircle(
                    color = Color(0xFF38BDF8),
                    radius = 3.5f,
                    center = Offset(ex, ey)
                )
            }
        }
    }
}
