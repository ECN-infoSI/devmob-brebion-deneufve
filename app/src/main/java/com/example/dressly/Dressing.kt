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

@Composable
fun DresslyApp() {
    val navController = rememberNavController()

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
            val fileName = backStackEntry.arguments?.getString("fileName") ?: ""
            val category = backStackEntry.arguments?.getString("category") ?: ""
            val tags = backStackEntry.arguments?.getString("tags")?.split(",") ?: emptyList()
            val description = backStackEntry.arguments?.getString("description") ?: ""

            VetementScreen(navController, drawableId, fileName, category, tags, description)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    var selectedTab by remember { mutableStateOf(0) }
    var selectedCategory by remember { mutableStateOf("Tous") }

    val categories = listOf("Tous", "T-Shirts", "Pantalons", "SweatsGilets")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Dressing") },
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
                CategorySelector(categories, selectedCategory) { selectedCategory = it }
                ClothesGrid(getSelectedClothes(selectedCategory), selectedCategory, navController)
            } else {
                TenueScreen()
            }
        }
    }
}

fun getSelectedClothes(category: String): List<Int> {
    val clothesMap = mapOf(
        "Tous" to listOf(R.drawable.tee000001, R.drawable.tee000002, R.drawable.pant_000001, R.drawable.pant_000002, R.drawable.sweat000001, R.drawable.sweat000002),
        "T-Shirts" to listOf(R.drawable.tee000001, R.drawable.tee000002),
        "Pantalons" to listOf(R.drawable.pant_000001, R.drawable.pant_000002),
        "SweatsGilets" to listOf(R.drawable.sweat000001, R.drawable.sweat000002)
    )
    return clothesMap[category] ?: emptyList()
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ClothesGrid(clothes: List<Int>, category: String, navController: NavController) {
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        maxItemsInEachRow = 2
    ) {
        clothes.forEach { drawableId ->
            val fileName = "file_$drawableId"
            val tags = listOf("Tag1", "Tag2")
            val description = "Description du vêtement $fileName"

            Image(
                painter = painterResource(id = drawableId),
                contentDescription = null,
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
