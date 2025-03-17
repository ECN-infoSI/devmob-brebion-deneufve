package com.example.dressly

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import android.content.Context
import com.example.dressly.ui.theme.DresslyWhite

@Composable
fun VetementScreen(
    navController: NavController,
    id: String,
    name: String,
    category: String,
    tags: List<String>,
    description: String,
    context: Context
) {
    // Récupérer l'ID de la ressource drawable
    val drawableResId = context.resources.getIdentifier(id, "drawable", context.packageName)

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Bouton retour
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(painterResource(id = R.drawable.ic_retour), contentDescription = "Retour", tint = Color.Unspecified)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Affichage de l'image du vêtement
        if (drawableResId != 0) {
            Image(
                painter = painterResource(id = drawableResId),
                contentDescription = null,
                modifier = Modifier
                    .size(150.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant, shape = RoundedCornerShape(8.dp))
            )
        } else {
            Text("Image introuvable : $id", color = Color.Red)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Nom du vêtement
        Text(text = name, style = MaterialTheme.typography.headlineMedium,)

        Spacer(modifier = Modifier.height(8.dp))

        // Tags
        Row {
            tags.forEach { tag ->
                Text(
                    text = tag,
                    modifier = Modifier
                        .background(Color(0xFF8ABDAA), shape = RoundedCornerShape(12.dp))
                        .padding(8.dp),
                    style = MaterialTheme.typography.bodyMedium,
                )
                Spacer(modifier = Modifier.width(4.dp))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Catégorie
        Text(text = "Catégorie", style = MaterialTheme.typography.headlineSmall,)
        Text(text = category, style = MaterialTheme.typography.bodyMedium,)

        Spacer(modifier = Modifier.height(32.dp))

        // Description
        Text(text = "Description", style = MaterialTheme.typography.headlineSmall,)
        Text(text = description, style = MaterialTheme.typography.bodyMedium)

        Spacer(modifier = Modifier.height(32.dp))

        // Boutons d'action
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = { /* Ajouter un tag */ }, colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)) {
                Text("+ Ajouter un tag", color = DresslyWhite,  style = MaterialTheme.typography.bodyLarge)
            }
            Spacer(modifier = Modifier.height(2.dp))
            Button(onClick = { /* Ajouter une photo */ }) {
                Text("+ Ajouter une photo", color = DresslyWhite,  style = MaterialTheme.typography.bodyLarge)
            }
            Spacer(modifier = Modifier.height(2.dp))
            Button(onClick = { /* Supprimer */ }, colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)) {
                Text("Supprimer", color = DresslyWhite, style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}
