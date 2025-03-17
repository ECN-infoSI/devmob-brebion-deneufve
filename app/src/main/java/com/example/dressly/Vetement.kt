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
            Icon(painterResource(id = R.drawable.ic_retour), contentDescription = "Retour")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Affichage de l'image du vêtement
        if (drawableResId != 0) {
            Image(
                painter = painterResource(id = drawableResId),
                contentDescription = null,
                modifier = Modifier
                    .size(150.dp)
                    .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
            )
        } else {
            Text("Image introuvable : $id", color = Color.Red)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Nom du vêtement
        Text(text = name, fontSize = 22.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(8.dp))

        // Tags
        Row {
            tags.forEach { tag ->
                Text(
                    text = tag,
                    modifier = Modifier
                        .background(Color(0xFFA8E6CF), shape = RoundedCornerShape(12.dp))
                        .padding(8.dp),
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.width(4.dp))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Catégorie
        Text(text = category, fontSize = 16.sp, fontWeight = FontWeight.Light)

        Spacer(modifier = Modifier.height(32.dp))

        // Description
        Text(text = "Description", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Text(text = description, fontSize = 14.sp)

        Spacer(modifier = Modifier.height(32.dp))

        // Boutons d'action
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = { /* Ajouter un tag */ }) {
                Text("+ Ajouter un tag")
            }
            Spacer(modifier = Modifier.height(32.dp))
            Button(onClick = { /* Ajouter une photo */ }) {
                Text("+ Ajouter une photo")
            }
            Spacer(modifier = Modifier.height(32.dp))
            Button(onClick = { /* Supprimer */ }, colors = ButtonDefaults.buttonColors(containerColor = Color.Red)) {
                Text("Supprimer")
            }
        }
    }
}
