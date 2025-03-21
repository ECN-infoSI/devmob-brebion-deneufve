# Dressly - Garde-Robe Virtuelle Intelligente
**[Livrables du projet](livrables/DEVMO_Brebion_Deneufve.pdf)**


## ✨ Présentation de l'Application
**Dressly** est une application de garde-robe virtuelle assistée par une intelligence artificielle avancée. Son objectif est d'aider les utilisateurs à organiser leurs vêtements, à créer des tenues cohérentes et à optimiser leur style vestimentaire.

### 👔 Pourquoi utiliser Dressly ?
- Trouver rapidement une tenue adaptée à son humeur, à la météo ou à un événement.
- Organiser ses vêtements par catégories, tags et couleurs.
- Scanner un vêtement en magasin pour vérifier sa compatibilité avec sa garde-robe.
- Recevoir des suggestions de tenues basées sur ses habits existants.

---
## 🌐 Architecture et Structure du Code
L'application suit une architecture modulaire et utilise **Jetpack Compose** pour l'interface utilisateur.

### 📂 Organisation des fichiers :
```
Dressly/
├── src/
│   ├── main/
│   │   ├── java/com/example/dressly/
│   │   │   ├── MainActivity.kt  # Point d'entrée de l'application
│   │   │   ├── Dressing.kt      # Gestion et affichage de la garde-robe
│   │   │   ├── Vetement.kt      # Affichage des détails d'un vêtement
│   │   │   ├── utils/
│   │   │   │   ├── DataLoader.kt # Lecture et traitement du fichier JSON
│   │   │   │   ├── Navigation.kt # Gestion des routes entre les écrans
│   │   ├── res/
│   │   │   ├── drawable/        # Images et icônes
│   │   │   ├── values/          # Styles et thèmes
│   │   ├── assets/
│   │   │   ├── info_vetements.json  # Base de données des vêtements
```

### 📈 Principales composantes :
#### 1. **MainActivity.kt**
- Initialise l'application et définit le thème global.

#### 2. **Dressing.kt**
- Affiche la liste des vêtements dans une grille dynamique (**LazyVerticalGrid**).
- Permet le tri par catégories et tags.

#### 3. **Vetement.kt**
- Affiche les détails d'un vêtement (image, nom, catégorie, tags, description).
- Possibilité d'ajouter des tags ou supprimer un vêtement.

#### 4. **info_vetements.json**
- Contient la liste des vêtements avec leurs caractéristiques (équivalent d'une base de données locale).
- Format : JSON sérialisable en Kotlin avec **kotlinx.serialization**.

#### 5. **Navigation.kt**
- Gère la navigation entre **HomeScreen** (dressing) et **VetementScreen** (détail d'un vêtement).

---
## ⚡ Instructions d'Utilisation

### 🛂 Installation et Exécution
1. Cloner le projet :
   ```bash
   git clone https://github.com/votre-repo/dressly.git
   ```
2. Ouvrir dans **Android Studio**.
3. Synchroniser les dépendances avec **Gradle Sync**.
4. Exécuter l'application sur un émulateur ou un appareil physique.

### 🛠️ Fonctionnalités Clés
- **Naviguer dans sa garde-robe** : Visualisation intuitive avec des images et des filtres.
- **Voir les détails d'un vêtement** : Description, tags, catégorie et options d'édition.
- **Ajouter et supprimer des vêtements** : Gestion dynamique de sa collection.
- **Suggestions de tenues** : IA intégrée pour recommander des associations vestimentaires.

---
## 🛠️ Configuration Minimale du Téléphone
- **Système d'exploitation** : Android 8.0 (Oreo) ou supérieur.
- **RAM** : Minimum 2 Go.
- **Stockage** : 50 Mo d'espace disponible.
- **Permissions** : Accès à la galerie et à l'appareil photo pour ajouter des images de vêtements.

---
## 🔧 Technologies Utilisées
- **Langage** : Kotlin
- **Framework UI** : Jetpack Compose
- **Gestion des données** : kotlinx.serialization (JSON)
- **Navigation** : Jetpack Navigation
- **Design** : Material 3

---
## 📃 Crédits
Projet réalisé par **Benjamin DENEUFVE** et **Matthieu BREBION** dans le cadre du module **DEVMO**.

