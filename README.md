# API REST du Restaurant

L'api est construit en utilisant Spring Boot avec MongoDB.

## Architecture du projet

J'ai utilisé une structure des packages par layer (couche), pour regrouper les classes de chaque type.


### Couche 1 : Contrôleur

- Un controleur "Auth" pour la gestion de l'authentification de l'utilisateur en utilisant JWT.
### Couche 2 : Repository

- Un repository "User" pour la gestion des utilisateurs.

## Securité

Pour la sécurité, j'ai utilisé JWT (Json Web Token) pour générer des tokens d'authentification, pour proteger les routes privées.

