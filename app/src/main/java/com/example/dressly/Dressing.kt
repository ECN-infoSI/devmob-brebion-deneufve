package com.example.dressly

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.*
import androidx.navigation.compose.*
import com.example.dressly.ui.theme.DresslyTheme
import android.content.Context
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import kotlinx.serialization.*
import androidx.compose.ui.platform.LocalContext
import kotlinx.serialization.json.*

@Composable
fun DresslyApp() {
    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = "dressly"
    ) {
        composable("dressly") { HomeScreen(navController) }
        composable(
            "vetement/{drawableId}/{fileName}/{category}/{tags}/{description}",
            arguments = listOf(
                navArgument("drawableId") { type = NavType.IntType },
                navArgument("fileName") { type = NavType.StringType },
                navArgument("category") { type = NavType.StringType },
                navArgument("tags") { type = NavType.StringType },
                navArgument("description") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val drawableId = backStackEntry.arguments?.getInt("drawableId") ?: return@composable
            val clothingItem = getClothingById(drawableId, context)  // Charge l'objet ClothingItem à partir de l'ID
            val fileName = clothingItem?.name ?: "Inconnu"
            val tags = clothingItem?.tags ?: emptyList()
            val category = clothingItem?.category ?: "Tout"
            val description = clothingItem?.description ?: "Pas de description"
            VetementScreen(navController, drawableId, fileName, category, tags, description)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    var selectedTab by remember { mutableStateOf(0) }
    var selectedCategory by remember { mutableStateOf("Tous") }
    val context = LocalContext.current
    val categories = listOf("Tous", "T-Shirts", "Pantalons", "SweatsGilets")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Dressly") },
                navigationIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_logo),
                        contentDescription = "Logo",
                        modifier = Modifier.size(40.dp).padding(8.dp)
                    )
                }
            )

        },
        bottomBar = {
            BottomNavigationBar(selectedTab, onTabSelected = { selectedTab = it })
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            if (selectedTab == 0) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Dressing", style = MaterialTheme.typography.headlineMedium)
                }
                CategorySelector(categories, selectedCategory) { selectedCategory = it }
                ClothesGrid(getSelectedClothes(selectedCategory, context), selectedCategory, navController, context)
            } else {
                TenueScreen()
            }
        }
    }

}

fun getSelectedClothes(category: String, context : Context): List<Int> {
    val clothesData = loadClothingData(context)

    // Filtrer les vêtements par catégorie
    return clothesData.filter { it.category == category || category == "Tous" }
        .map { it.id }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ClothesGrid(clothes: List<Int>, category: String, navController: NavController, context : Context) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2), // 2 vêtements par ligne
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        items(clothes) { drawableId ->
            val clothingItem = getClothingById(drawableId, context)  // Charge l'objet ClothingItem à partir de l'ID
            val fileName = clothingItem?.name ?: "Inconnu"
            val tags = clothingItem?.tags ?: emptyList()
            val description = clothingItem?.description ?: "Pas de description"
            Image(
                painter = painterResource(id = drawableId),
                contentDescription = description,
                modifier = Modifier
                    .size(150.dp)
                    .padding(8.dp)
                    .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
                    .clickable {
                        navController.navigate("vetement/$drawableId/$fileName/$category/${tags.joinToString(",")}/$description")
                    }
            )
        }
    }
}

@Serializable
data class ClothingItem(
    val id: Int,
    val drawable: String,
    val name: String,
    val category: String,
    val tags: List<String>,
    val description: String
)

fun loadClothingData(context: Context): List<ClothingItem> {
    val jsonString = context.assets.open("info_vetements.json").bufferedReader().use { it.readText() }
    return Json.decodeFromString(jsonString)
}

fun getClothingById(id: Int, context: Context): ClothingItem? {
    val clothes = loadClothingData(context)
    return clothes.find { it.id == id }
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
