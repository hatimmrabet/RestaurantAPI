# PJI - Application web pour la gestion d'un restaurant

## Problematique

Un restarant de plats mix (traditionnels et fast-food) veut attirir plus de clients en leurs donnant la possibilité de commander en ligne et de venir chercher leurs commandes, ainsi que la possibilité de profiter de leurs repas sur place.
Pour faciliter la gestion, le gerant a pour idée de creer une application web qui va l'aider à automatiser les taches de gestion du restaurant.

## Technologies utilisees


Pour ce projet, nous avons utilisé les technologies suivantes :

**Pour le backend :**
Ona utilisé le framwork Spring Boot pour la communication avec la base de données MongoDB et le stockage des données.

Le code de la partie backend est disponible sur le [Github](https://github.com/hatimmrabet/RestaurantAPI)

**Pour le frontend :**
On a utilisé le framework Angular 13 pour le frontend. Le code est disponible sur le [Github](https://github.com/hatimmrabet/GestionResto)


## Realisation

### Base de données : MongoDB

Pour la realisation de ce projet on a utiliser une base de données noSQL MongoDB.

**Structure de la base de données**

La base de données contient les collections suivantes :

- **`users`** : contient tous les informations de chaque utilisateur avec son type de compte (Admin, worker ou client)
- **`produits`** : contient tous les produits du restaurant avec leur description, prix, image, categorie et la liste des ingredients.
- **`menus`** : contient tous les menus du restaurant avec leur description, prix, image, et la liste des produits de chaque menu.
- **`commandes`** : contient toutes les commandes du restaurant avec leur description, prix total, et la liste des produits de chaque commande avec sa quantité, et finalement la reference vers l'utilisateur qui a passé la commande.
- **`categories`** : contient toutes les categories de produits du restaurant.
- **`ingredients`** : contient tous les ingredients qu'on peut utiliser dans un produit.

***exemple :***

```json
"users": {
    "id":"objectId",
    "name": "string",
    "firstName":"string",
    "lastName":"string",
    "email":"string",
    "password":"string",
    "phoneNumber":"string",
    "role":"string",
    "address":"string",
    "birthDate":"Date",
    "createdAt":"Date",
    "updatedAt":"Date",
}

"produits": {
    "id":"objectId",
    "categorie":"DBref{categories}",
    "ingredients":"array[DBref{ingredients}]",
    "name": "string",
    "description":"string",
    "price":"number",
    "image":"string",
    "createdAt":"Date",
}

"menus"{
    "id":"objectId",
    "produits":"DBref{produits}",
    "name": "string",
    "description":"string",
    "price":"number",
    "image":"string",
    "createdAt":"Date",
}

"ingredients"{
    "id":"objectId",
    "name": "string",
}

"catetgories"{
    "id":"objectId",
    "name": "string",
}

"commandes"{
    "id":"objectId",
    "numero":"string",
    "etat":"string",
    "type":"string",
    "client":"DBref{users}",
    "price":"number",
    "items":"array[{
        'produit':'DBref{produits}',
        'quantite':'number',
    }]",
    "produits":"DBref{produits}",
    "date":"Date",
}
```

### Back-end : Spring Boot

Pour le back-end on a utiliser le framework Spring Boot.

**Structure du back-end**

L'architecture du notre back-end est constitué par des package qui regroupe chaque les classes selon leur type (controller, service, repository, etc...)
- Controllers
  - AuthController
  - CategoriesController
  - CommandesController
  - IngredientsController
  - MenusController
  - ProduitsController
  - UsersController
- Exceptions
  - IngredientNotFoundAdvice
  - IngredientNotFoundException
  - UserNotFoundAdvice
  - UserNotFoundException
- Models
  - request
    - CommandeRequest
    - LoginRequest
    - UserPutRequest
  - response
    - CommandeDTO
    - JwtResponse
    - ResponseMessage
  - Article
  - ArticleCommande
  - categories
  - Commande
  - CommandeItem
  - EOrderStatus
  - ERole
  - Ingredient
  - Menu
  - MenuCommande
  - Produit
  - ProduitCommande
  - User
  - UserDTO
- repository
  - categoriesRepository
  - ingredientsRepository
  - usersRepository
  - commandesRepository
  - menusRepository
  - produitsRepository
- security
  - jwt
    - AuthEntryPointJwt
    - AuthTokenFilter
    - JwtUtils
  - services
    - UserDetailsServiceImpl
    - UsersDetailsImpl
  - WebSecurityConfig
- WebConfigurations

**Base de données**

Pour la liaison avec la base de données on a modifier le fichier `application.proprities` où on a specifié le nom de la base, le host et le port.

```java
spring.data.mongodb.database=laPetiteSyrienne_db
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
```

**Securité**

Pour securisé les endpoints, ona utilisé JWT pour la gestion des tokens. Seul les utilisateurs qui ont un token valide peuvent acceder aux endpoints privés et securisés.

**Controllers**

Parmi les fonctionnalité avancées de notre back-end, on a utilisé les controllers pour la gestion des utilisateurs, les commandes, les menus, les produits et les categories.

Pour la gestion des produits et des menus on a des contolleurs qui servent a creer un nouveau menu ou produit et ajouté l'image associé à cet article au dossier `ressources/static/images`, ainssi que de creer une nouvelle categorie ou ingredient s'il n'existait pas avant dans notre base de données.

Lors de la suppression d'une categorie, ingredient ou produit, on a supprimé l'image associé à cet article au dossier `ressources/static/images`. 
Et ceci est fait seulement si :
- le produit n'est utilisé dans aucun menu.
- l'ingredient n'est utilisé dans aucun produit.
- la categorie n'est liée à aucun produit.

Sinon, l'utilisateur sera informé qu'il ne peut pas supprimer cette categorie, ingredient ou produit, avec un mesage plus detaillé pour savoir pourquoi et où on a utilisé ce produit, ingredient, categorie.

Lors de la suppression d'un utilisateur, si l'utilisateur n'a aucune commande on le supprime directement de la se de données, sinon on supprime tous ses données (nom, prenom, email, etc...) mais on le garde dans la base de données pour avoir une homogeneite dans notre base de données, et pouvoir savoir combient d'utilisateurs ont passé des commandes.

**Classes DTO**

Pour eviter d'envoyer des données sensibles comme les mots de passes... on a créé des classes DTO qui sont des classes qui sont utilisées pour envoyer que les données necessaires aux front-ends.

### Front-end

Pour le front-end on a utiliser le framework Angular 13.

Plus de details disponible sur : [GestionResto Github](https://github.com/hatimmrabet/GestionResto/tree/master/documentation)