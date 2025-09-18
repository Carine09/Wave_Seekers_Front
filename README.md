# Wave Seekers 

Application Mobile dédiée à la recherche de spots de surf.

Type : collectif — 4 personnes   
Durée : 2 semaines.  Sprint 1 Front. Sprint 2 Back   
Période : 9eme mois de formation   

## Stack  

Android Studio, Kotlin
VSCode, Golang, Gin gonic
- Langages / Frameworks : Kotlin (sans framework), Golang (Gin Gonic). Pas d'ORM
- Base de données : SQLite  
- Outils / Services : VSCode - Android Studio — Postman  

## Fonctionnalités et compétences

| Fonctionnalité                              | Compétence acquise                                                                      |
| ------------------------------------------- | ----------------------------------------------------------------------------------------|
| **Architecture Jetpack Compose**           | Création d'interfaces déclaratives avec des `@Composable` réutilisables                  |
| **Gestion d'état réactive**                | Utilisation de `remember`, `mutableStateOf` et `LaunchedEffect` pour la réactivité       |
| **Navigation entre écrans**                | Transition entre Activities avec `Intent` et passage de données via `putExtra`           |
| **Intégration API REST avec OkHttp**       | Appels HTTP sécurisés (HTTPS) avec parsing JSON via Gson                                 |
| **Gestion asynchrone avec Coroutines**     | Exécution d'appels réseau sur `Dispatchers.IO` avec `withContext`                        |
| **Architecture MVC côté mobile**           | Séparation entre modèles de données (`ApiSpot`, `ApiUser`) et logique UI                 |
| **Affichage conditionnel dynamique**       | Rendu conditionnel avec `when` (loading, erreur, succès)                                 |
| **Transformation de données**              | Mapping entre modèles API et modèles UI (`toUiSpot()`)                                   |
| **Gestion des certificats SSL**            | Configuration de client HTTP pour environnement de développement                         |
| **State management complexe**               | Coordination de multiple sources de données (spots + countries) dans un seul composant  |

Lien vers le [Backend](https://github.com/Carjardying/Wave_Seekers_Back)

Lien vers la [Demo via Canva en lecture seule ](https://www.canva.com/design/DAGzUIHmsnc/l-NeM6JfFkRLTJcq4LAdPA/edit)
