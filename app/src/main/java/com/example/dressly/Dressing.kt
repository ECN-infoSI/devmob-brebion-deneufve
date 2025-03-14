package com.example.dressly

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dressly.ui.theme.DresslyTheme

@Composable
fun DresslyApp() {
    var selectedTab by remember { mutableStateOf(0) }
    var selectedCategory by remember { mutableStateOf("Tous") }

    val categories = listOf("Tous","T-Shirts", "Pantalons", "SweatsGilets")

    Scaffold(
        bottomBar = {
            BottomNavigationBar(selectedTab, onTabSelected = { selectedTab = it })
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            if (selectedTab == 0) {
                CategorySelector(categories, selectedCategory) { selectedCategory = it }
                ClothesGrid(getSelectedClothes(selectedCategory))
            } else {
                TenueScreen()
            }
        }
    }
}

// Fonction pour récupérer les vêtements en fonction de la catégorie sélectionnée
fun getSelectedClothes(category: String): List<Int> {
    val clothesMap = mapOf(
        "Tous" to listOf(R.drawable.tee000001, R.drawable.tee000002,R.drawable.pant_000001, R.drawable.pant_000002, R.drawable.sweat000001, R.drawable.sweat000002),
        "T-Shirts" to listOf(R.drawable.tee000001, R.drawable.tee000002),
        "Pantalons" to listOf(R.drawable.pant_000001, R.drawable.pant_000002),
        "SweatsGilets" to listOf(R.drawable.sweat000001, R.drawable.sweat000002)
    )
    return clothesMap[category] ?: emptyList()
}

// Affichage des vêtements côte à côte
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ClothesGrid(clothes: List<Int>) {
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        maxItemsInEachRow = 2
    ) {
        clothes.forEach { drawableId ->
            Image(
                painter = painterResource(id = drawableId),
                contentDescription = null,
                modifier = Modifier
                    .size(150.dp)
                    .padding(8.dp)
            )
        }
    }
}


@Composable
fun CategorySelector(categories: List<String>, selectedCategory: String, onCategorySelected: (String) -> Unit) {
    Row(modifier = Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
        categories.forEach { category ->
            Text(
                text = category,
                modifier = Modifier.clickable { onCategorySelected(category) },
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
fun ClothesList(clothes: List<Int>) {
    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        items(clothes) { imageRes ->
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth().height(150.dp).padding(8.dp)
            )
        }
    }
}

@Composable
fun BottomNavigationBar(selectedTab: Int, onTabSelected: (Int) -> Unit) {
    NavigationBar {
        NavigationBarItem(
            selected = selectedTab == 0,
            onClick = { onTabSelected(0) },
            icon = { Icon(painterResource(id = R.drawable.dressing), contentDescription = "Dressing") },
            label = { Text("Dressing") },
            modifier = Modifier.size(24.dp)
        )
        NavigationBarItem(
            selected = selectedTab == 1,
            onClick = { onTabSelected(1) },
            icon = { Icon(painterResource(id = R.drawable.tenue), contentDescription = "Tenue") },
            label = { Text("Tenue") },
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
fun TenueScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Tenue", style = MaterialTheme.typography.headlineMedium)
    }
}

@Preview(showBackground = true)
@Composable
fun DresslyAppPreview() {
    DresslyTheme {
        DresslyApp()
    }
}