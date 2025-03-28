# auth-service

Le Auth-Service est un service dédié à l'authentification des utilisateurs. Il prend en charge les opérations suivantes :

- Login : Authentification d'un utilisateur existant via un email et un mot de passe.
- Signup : Création d'un nouvel utilisateur avec un email et un mot de passe.
Le service utilise des tokens JWT pour sécuriser les communications entre le client et le serveur.

### Fonctionnalités
- Authentification basée sur JWT (JSON Web Token).
- Support pour l'enregistrement de nouveaux utilisateurs (signup).
- Protection des mots de passe avec un chiffrement sécurisé (BCrypt).
- Validation des informations de connexion pour garantir une authentification sécurisée.

### Prérequis
Avant de pouvoir exécuter ce service, assurez-vous d'avoir les éléments suivants :

- JDK 17+ : Le projet utilise Java 17 ou une version supérieure.
- Maven : Outil de gestion des dépendances.
- PostgreSQL : Base de données PostgreSQL pour stocker les utilisateurs.
- Spring Boot : Le service est construit avec Spring Boot.

### Installation
- Cloner le repository 

Clonez le repository dans votre répertoire local :

`` git clone https://github.com/tsore-iaas/auth-service.git ``

- Configuration de la base de données

Base de données : Configurez votre base de données PostgreSQL, créez une base de données auth_tsore (ou toute autre nom souhaité) et assurez-vous que les informations d'identification sont correctes.

Configurer le fichier application.properties : Modifiez le fichier src/main/resources/application.properties pour y inclure vos informations de connexion à la base de données ainsi que les configurations JWT.

### Dépendances nécessaires
Si vous utilisez Maven, les dépendances nécessaires sont dans le fichier pom.xml. Assurez-vous que vous avez les bonnes versions des dépendances comme 
- spring-boot-starter-web, 
- spring-boot-starter-security, 
- spring-boot-starter-data-jpa, 
- spring-boot-starter-validation,
- jjwt pour la gestion des tokens JWT.

### Démarrage du service
Pour démarrer le service, utilisez la commande suivante :

`` mvn spring-boot:run ``

Cela démarrera le service sur le port configuré (par défaut 9010).

### Endpoints de l'API
#### 1. POST /auth/login
<i> Permet à un utilisateur de se connecter en utilisant son adresse e-mail et son mot de passe. </i>

##### Requête
```JSON
{
"email": "utilisateur@example.com",
"password": "motDePasseSécurisé"
}
```

##### Réponse
<i> Code 200 : Succès de l'authentification. Retourne un token JWT. </i>

```JSON
{
"email": "utilisateur@example.com",
"token": "jwt_token_generé"
}
```
<i>Code 401 : Identifiants invalides ou utilisateur non trouvé.</i>

```JSON
{
"message": "Utilisateur non trouvé ou mot de passe incorrect"
}
```

#### 2. POST /auth/signup
<i>Permet à un nouvel utilisateur de s'enregistrer.</i>

##### Requête

```JSON
{
"email": "nouvelutilisateur@example.com",
"password": "motDePasseSécurisé"
}
```

##### Réponse
<i>Code 201 : Utilisateur créé avec succès. Retourne un token JWT.</i>

```JSON
{
"email": "nouvelutilisateur@example.com",
"token": "jwt_token_generé"
}
```

<i>Code 409 : L'utilisateur avec cet email existe déjà.</i>

```JSON
{
"message": "Utilisateur avec cet email existe déjà"
}
```

### Sécurisation des mots de passe
Le service utilise BCrypt pour sécuriser les mots de passe des utilisateurs. Les mots de passe ne sont jamais stockés en clair dans la base de données, mais sont hachés avant d'être enregistrés.

###### Exemple de création de mot de passe sécurisé
Lorsque l'utilisateur s'inscrit, son mot de passe est automatiquement haché avec BCrypt avant d'être enregistré dans la base de données.

###### Exemple de génération de token JWT
Lorsque l'utilisateur se connecte, un token JWT est généré pour lui. Ce token est utilisé pour sécuriser les communications avec le service.

``String token = jwtUtil.generateToken(user.getEmail());``

#### Sécurisation via JWT
Le service utilise JSON Web Tokens (JWT) pour garantir la sécurité des communications. Le token contient l'adresse e-mail de l'utilisateur et est signé avec une clé secrète. Ce token est ensuite renvoyé dans la réponse et doit être inclus dans l'en-tête Authorization des requêtes suivantes pour accéder aux ressources sécurisées.

``Authorization: Bearer <token>``

#### Gestion des erreurs
Les erreurs sont renvoyées sous forme de JSON, avec un code HTTP approprié.

###### Exemples de réponses d'erreur :

- 400 Bad Request : Erreur de validation (ex. format de mot de passe incorrect).
- 401 Unauthorized : Erreurs d'authentification (email ou mot de passe incorrect).
- 409 Conflict : Conflit lors de la création (ex. email déjà utilisé).

### Tests
Le projet est compatible avec les tests unitaires et d'intégration en utilisant JUnit et Mockito. Vous pouvez ajouter vos propres tests dans le répertoire src/test/java.

### Contribution
Si vous souhaitez contribuer à ce projet, veuillez suivre les étapes ci-dessous :

- Fork le repository.
- Créez une nouvelle branche (git checkout -b feature/ma-feature).
- Faites vos modifications.
- Testez vos modifications.
- Soumettez une Pull Request avec une description détaillée des modifications apportées.

---

**Auteur**: BISSOG SAMUEL

**Licence**: MIT