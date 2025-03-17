package com.example.dressly

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.*
import androidx.navigation.compose.*
import com.example.dressly.ui.theme.DresslyTheme
import android.content.Context
import android.util.Log
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import kotlinx.serialization.*
import androidx.compose.ui.platform.LocalContext
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.*
import java.io.FileNotFoundException
import java.io.IOException

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
            "vetement/{id}/{fileName}/{category}/{tags}/{description}",
            arguments = listOf(
                navArgument("id") { type = NavType.StringType },
                navArgument("fileName") { type = NavType.StringType },
                navArgument("category") { type = NavType.StringType },
                navArgument("tags") { type = NavType.StringType },
                navArgument("description") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: return@composable
            val clothingItem = getClothingById(id, context)  // Charge l'objet ClothingItem à partir de l'ID
            val fileName = clothingItem?.name ?: "Inconnu"
            val tags = clothingItem?.tags ?: emptyList()
            val category = clothingItem?.category ?: "Tout"
            val description = clothingItem?.description ?: "Pas de description"
            VetementScreen(navController, id, fileName, category, tags, description, context)
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

fun getSelectedClothes(category: String, context: Context): List<String> {
    val clothesData = loadClothingData(context)

    // Filtrer les vêtements par catégorie et récupérer `drawable` (String)
    return clothesData.filter { it.category == category || category == "Tous" }
        .map { it.drawable }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ClothesGrid(clothes: List<String>, category: String, navController: NavController, context: Context) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2), // 2 vêtements par ligne
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        items(clothes) { id ->
            // Récupérer l'ID de la ressource à partir du nom du fichier
            val drawableResId = context.resources.getIdentifier(id, "drawable", context.packageName)

            // Charger l'objet ClothingItem en cherchant par `drawable`
            val clothingItem = getClothingById(id, context)

            val fileName = clothingItem?.name ?: "Inconnu"
            val tags = clothingItem?.tags ?: emptyList()
            val description = clothingItem?.description ?: "Pas de description"

            Image(
                painter = painterResource(id = drawableResId),
                contentDescription = description,
                modifier = Modifier
                    .size(150.dp)
                    .padding(8.dp)
                    .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
                    .clickable {
                        navController.navigate("vetement/$id/$fileName/$category/${tags.joinToString(",")}/$description")
                    }
            )
        }
    }
}

@Serializable
data class ClothingItem(
    @SerialName("id") val drawable: String, // Changed the name to drawable
    @SerialName("name") val name: String,
    @SerialName("category") val category: String,
    @SerialName("tags") val tags: List<String>,
    @SerialName("description") val description: String
)

fun loadClothingData(context: Context): List<ClothingItem> {
    Log.d("ClothingLoader", "Attempting to load clothing data...")
    return try {
        val inputStream = context.assets.open("info_vetements.json")
        val jsonString = inputStream.bufferedReader().use { it.readText() }
        Log.d("ClothingLoader", "Successfully read file content.")
        val clothingList: List<ClothingItem> = Json.decodeFromString(ListSerializer(ClothingItem.serializer()), jsonString)
        Log.d("ClothingLoader", "Successfully parsed file content: $clothingList")
        clothingList
    } catch (e: FileNotFoundException) {
        Log.e("ClothingLoader", "Error: File 'info_vetements.json' not found in assets folder.", e)
        emptyList()
    } catch (e: SerializationException) {
        Log.e("ClothingLoader", "Error: Invalid JSON format in 'info_vetements.json'.", e)
        emptyList()
    } catch (e: IOException) {
        Log.e("ClothingLoader", "Error: An error occurred while reading 'info_vetements.json'.", e)
        emptyList()
    } catch (e: Exception) {
        Log.e("ClothingLoader", "Error: An unexpected error occurred.", e)
        emptyList()
    }
}

fun getClothingById(id: String, context: Context): ClothingItem? {
    val clothes = loadClothingData(context)
    return clothes.find { it.drawable == id }
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
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Titre en haut à gauche
        Text(
            text = "Faire une Tenue",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(32.dp)) // Espace après le titre

        // Centrer le contenu restant
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.Build, // Icône "construction"
                contentDescription = "Work in progress",
                modifier = Modifier.size(80.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "En travaux...",
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Cette fonctionnalité est en cours de développement.",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DresslyAppPreview() {
    DresslyTheme {
        DresslyApp()
    }
}
