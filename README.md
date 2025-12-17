# Excercice1 - Projet 3 OpenClassrooms

Application Spring Boot pour la gestion de locations (ChaTop) avec authentification JWT.

## 📋 Prérequis

- **Java** : Version 17 ou supérieure
- **Maven** : Version 3.6+ (wrapper inclus dans le projet)
- **MySQL** : Version 8.0+
- **IDE** : VS Code, IntelliJ IDEA, Eclipse (recommandé : VS Code)
- **Postman** : Pour tester l'API (collection fournie)

## 🔧 Technologies utilisées

- **Spring Boot** : 3.3.5
- **Spring Data JPA** : Gestion de la persistance
- **Spring Security** : 6.3.4 - Sécurisation avec session STATELESS
- **JWT (JSON Web Tokens)** : Authentification (jjwt 0.11.5)
- **MySQL Connector** : Driver de base de données
- **Lombok** : 1.18.32 - Réduction du code boilerplate
- **Hibernate** : 6.5.3.Final - ORM (via JPA)
- **Bean Validation** : Validation des données

## 📁 Structure du projet

```
excercice1/
├── src/
│   ├── main/
│   │   ├── java/com/openclassrooms/projet3/excercice1/
│   │   │   ├── config/              # Configuration Spring
│   │   │   │   ├── JwtAuthenticationFilter.java
│   │   │   │   ├── LoggingFilter.java
│   │   │   │   ├── OpenApiConfig.java
│   │   │   │   ├── SecurityConfig.java
│   │   │   │   ├── ValidationConstants.java
│   │   │   │   └── WebConfig.java
│   │   │   ├── constants/           # Constantes de l'application
│   │   │   │   ├── ApiConstants.java
│   │   │   │   ├── EntityConstants.java
│   │   │   │   ├── FormatConstants.java
│   │   │   │   ├── MessageConstants.java
│   │   │   │   └── SecurityConstants.java
│   │   │   ├── controller/          # Contrôleurs REST
│   │   │   │   ├── AuthController.java
│   │   │   │   ├── MessageController.java
│   │   │   │   ├── RentalController.java
│   │   │   │   └── UserController.java
│   │   │   ├── dto/                 # Data Transfer Objects
│   │   │   │   ├── AuthResponse.java
│   │   │   │   ├── ErrorResponse.java
│   │   │   │   ├── LoginRequest.java
│   │   │   │   ├── MessageDto.java
│   │   │   │   ├── MessageResponse.java
│   │   │   │   ├── RegisterRequest.java
│   │   │   │   ├── RentalDto.java
│   │   │   │   ├── RentalResponse.java
│   │   │   │   └── UserDto.java
│   │   │   ├── entity/              # Entités JPA
│   │   │   │   ├── Message.java
│   │   │   │   ├── Rental.java
│   │   │   │   └── User.java
│   │   │   ├── exception/           # Gestion des erreurs
│   │   │   │   ├── ApiException.java
│   │   │   │   ├── BadRequestException.java
│   │   │   │   ├── FileStorageException.java
│   │   │   │   ├── GlobalExceptionHandler.java
│   │   │   │   ├── ResourceAlreadyExistsException.java
│   │   │   │   └── ResourceNotFoundException.java
│   │   │   ├── mapper/              # Mappers Entity <-> DTO
│   │   │   │   ├── MessageMapper.java
│   │   │   │   ├── RentalMapper.java
│   │   │   │   └── UserMapper.java
│   │   │   ├── repository/          # Repositories JPA
│   │   │   │   ├── MessageRepository.java
│   │   │   │   ├── RentalRepository.java
│   │   │   │   └── UserRepository.java
│   │   │   ├── service/             # Services métier
│   │   │   │   ├── AuthService.java
│   │   │   │   ├── CustomUserDetailsService.java
│   │   │   │   ├── FileStorageService.java
│   │   │   │   ├── JwtService.java
│   │   │   │   ├── MessageService.java
│   │   │   │   ├── RentalService.java
│   │   │   │   └── UserService.java
│   │   │   └── RentalApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── ValidationMessages.properties
│   └── test/
├── Projet3-API-Locations.postman_collection.json
├── pom.xml
└── README.md
```

## ⚙️ Configuration de la base de données

### 1. Créer la base de données MySQL

```sql
CREATE DATABASE chatop_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER 'votre_utilisateur_db'@'localhost' IDENTIFIED BY 'votre_mot_de_passe';
GRANT SELECT, INSERT, UPDATE, DELETE ON chatop_db.* TO 'votre_utilisateur_db'@'localhost';
FLUSH PRIVILEGES;
```

> ⚠️ **Note** : L'utilisateur de base de données n'a que les privilèges CRUD (pas de ALTER, DROP, etc.)

### 2. Configurer les variables d'environnement

#### DB_PASSWORD (obligatoire)

**Windows (PowerShell)** :
```powershell
[System.Environment]::SetEnvironmentVariable('DB_PASSWORD', 'votre_mot_de_passe', 'User')
```

**Windows (cmd)** :
```cmd
setx DB_PASSWORD "votre_mot_de_passe"
```

**Linux/Mac** :
```bash
export DB_PASSWORD="votre_mot_de_passe"
```

#### JWT_SECRET (optionnel - valeur par défaut fournie)

**Windows (PowerShell)** :
```powershell
[System.Environment]::SetEnvironmentVariable('JWT_SECRET', 'votre_cle_secrete_base64', 'User')
```

> ⚠️ **Important** : Redémarrez votre terminal après avoir défini les variables d'environnement système.

### 3. Configuration (application.properties)

```properties
# Server
server.port=3001

# Base de données
spring.datasource.url=jdbc:mysql://localhost:3306/chatop_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=votre_utilisateur_db
spring.datasource.password=${DB_PASSWORD}
spring.jpa.hibernate.ddl-auto=none
spring.jpa.show-sql=true

# JWT
jwt.secret=${JWT_SECRET:404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970}
jwt.expiration=86400000

# Logging
logging.level.com.openclassrooms.projet3.excercice1.config.LoggingFilter=INFO
```

**Notes** :
- `ddl-auto=none` : Pas de modification automatique du schéma (utilisateur sans privilèges ALTER)
- `jwt.expiration=86400000` : Token valide 24 heures (en millisecondes)
- Une clé secrète JWT par défaut est fournie (à changer en production)

## 🚀 Installation et démarrage

### 1. Cloner ou télécharger le projet

```bash
cd c:\StarsheepStudio\Professionel\OpenClassrooms\Projet-3
```

### 2. Vérifier que MySQL est démarré

```powershell
# Windows
Get-Service MySQL*
```

### 3. Compiler le projet

```powershell
.\mvnw.cmd clean install
```

### 4. Lancer l'application

**Option 1 : Mode normal avec Maven**
```powershell
.\mvnw.cmd spring-boot:run
```

**Option 2 : Mode debug dans VS Code**
- Placez vos points d'arrêt dans le code
- Appuyez sur **F5** ou allez dans **Run > Start Debugging**
- Le debugger s'arrêtera sur vos breakpoints

**Option 3 : Avec JAR exécutable**
```powershell
.\mvnw.cmd package
java -jar target\excercice1-0.0.1-SNAPSHOT.jar
```

### 5. Vérifier le démarrage

L'application démarre sur le port **3001** (configuré pour l'IHM frontend).

```
URL API : http://localhost:3001
URL IHM : http://localhost:3001 (frontend)
```

Si Tomcat démarre correctement :
```
Tomcat started on port 3001 (http)
Started RentalApplication in X.XXX seconds
```

> 📌 **Note** : Le port 3001 est utilisé au lieu de 8080 pour correspondre à la configuration de l'IHM frontend.

## 🔐 Authentification JWT

### Workflow d'authentification

1. **Inscription** : `POST /api/auth/register` → Reçoit un token JWT
2. **Connexion** : `POST /api/auth/login` → Reçoit un token JWT
3. **Requêtes protégées** : Ajouter le header `Authorization: Bearer {token}`
4. **Info utilisateur** : `GET /api/auth/me` → Retourne les données de l'utilisateur connecté

### Endpoints d'authentification

#### POST /api/auth/register
Créer un nouveau compte utilisateur.

**Body (JSON)** :
```json
{
  "name": "John Doe",
  "email": "john.doe@example.com",
  "password": "motdepasse123"
}
```

**Réponse (201 Created)** :
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "user": {
    "id": 1,
    "name": "John Doe",
    "email": "john.doe@example.com",
    "createdAt": "2025-12-11T21:00:00",
    "updatedAt": "2025-12-11T21:00:00"
  }
}
```

#### POST /api/auth/login
Se connecter avec un compte existant.

**Body (JSON)** :
```json
{
  "email": "john.doe@example.com",
  "password": "motdepasse123"
}
```

**Réponse (200 OK)** :
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "user": { ... }
}
```

#### GET /api/auth/me
Récupérer les informations de l'utilisateur connecté.

**Headers** :
```
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

**Réponse (200 OK)** :
```json
{
  "id": 1,
  "name": "John Doe",
  "email": "john.doe@example.com",
  "createdAt": "2025-12-11T21:00:00",
  "updatedAt": "2025-12-11T21:00:00"
}
```

## 📡 API Endpoints

### Routes publiques (sans authentification)
- `POST /api/auth/register` - Inscription
- `POST /api/auth/login` - Connexion

### Routes protégées (authentification requise)

#### Authentication
- `GET /api/auth/me` - Récupérer l'utilisateur connecté

#### Users
- `GET /api/user/{id}` - Détails d'un utilisateur

#### Rentals
- `GET /api/rentals` - Liste de toutes les locations
- `GET /api/rentals/{id}` - Détails d'une location
- `POST /api/rentals` - Créer une location (multipart/form-data)
- `PUT /api/rentals/{id}` - Modifier une location (multipart/form-data)

#### Messages
- `POST /api/messages` - Envoyer un message concernant une location

### Exemple de requête avec token

```bash
curl -X GET http://localhost:3001/api/auth/me \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

## 📮 Collection Postman

Une collection Postman complète est fournie : `Projet3-API-Locations.postman_collection.json`

**Fonctionnalités** :
- Dossier Authentication avec Register et Login
- **Auto-sauvegarde du token JWT** dans les variables de collection et d'environnement
- Tous les endpoints protégés utilisent automatiquement le token sauvegardé
- Scripts de tests intégrés

**Import** :
1. Ouvrir Postman
2. File > Import
3. Sélectionner `Projet3-API-Locations.postman_collection.json`
4. Le token sera automatiquement sauvegardé après register/login

## 🧪 Tests

Lancer les tests unitaires :

```powershell
.\mvnw.cmd test
```

## 📦 Packaging

Créer un fichier JAR exécutable :

```powershell
.\mvnw.cmd clean package
```

Le fichier JAR sera généré dans : `target/excercice1-0.0.1-SNAPSHOT.jar`

## 🏗️ Architecture

L'application suit une architecture en couches :

```
Controller → Service → Repository → Entity
     ↓          ↓          ↓
    DTO ← Mapper ← Entity
```

**Couches** :
- **Controller** : Endpoints REST, validation des requêtes
- **Service** : Logique métier, transactions
- **Repository** : Accès aux données (JPA)
- **Entity** : Modèle de données (tables MySQL)
- **DTO** : Objets de transfert (API ↔ Client)
- **Mapper** : Conversion Entity ↔ DTO

**Sécurité** :
- **JwtAuthenticationFilter** : Validation des tokens avant chaque requête
- **LoggingFilter** : Logs des requêtes/réponses (debug)
- **SecurityConfig** : Configuration Spring Security 6.3.4
- **CustomUserDetailsService** : Chargement des utilisateurs

## 🐛 Résolution des problèmes courants

### Erreur : "Public Key Retrieval is not allowed"

✅ **Solution** : Le paramètre `allowPublicKeyRetrieval=true` est déjà dans l'URL.

### Erreur : "class file has wrong version"

✅ **Solution** : Vérifiez Java 17+ :
```powershell
java -version
```

### Erreur : "Access denied for user"

✅ **Solution** : Vérifiez que :
1. La variable `DB_PASSWORD` est définie
2. L'utilisateur MySQL existe avec les bons droits (SELECT, INSERT, UPDATE, DELETE)
3. Le nom d'utilisateur et mot de passe correspondent à votre configuration
4. `spring.datasource.username` dans `application.properties` correspond à votre utilisateur MySQL

### Erreur : "ALTER command denied"

✅ **Solution** : Normal ! L'utilisateur de base de données n'a que les droits CRUD. Assurez-vous que `spring.jpa.hibernate.ddl-auto=none` dans `application.properties`.

### Port 3001 déjà utilisé

✅ **Solution** :
```powershell
# Trouver et arrêter le processus
Get-Process -Id (Get-NetTCPConnection -LocalPort 3001).OwningProcess | Stop-Process -Force
```

> 💡 **Changer le port** : Modifier `server.port` dans `application.properties` si le port 3001 est occupé.

### Token JWT expiré (401 Unauthorized)

✅ **Solution** : Re-connectez-vous avec `/api/auth/login` pour obtenir un nouveau token (durée : 24h).

### Erreur : "Content-Type multipart/form-data is not supported"

✅ **Solution** : Pour `POST /api/rentals`, utilisez `multipart/form-data` avec des champs séparés (`name`, `surface`, `price`, `picture`, `description`).

> ℹ️ **Note** : Le `owner_id` est automatiquement récupéré depuis le token JWT de l'utilisateur connecté.

## 🔍 Debugging

### LoggingFilter

Le `LoggingFilter` affiche automatiquement dans les logs :
- 📥 Méthode et URI de la requête
- 📨 Body de la requête (JSON formaté)
- 📨 Paramètres de la requête (multipart)
- 📤 Status code de la réponse
- 📋 Body de la réponse (JSON formaté)

**Exemple de log** :
```
📥 Requête: POST /api/auth/login - Content-Type: application/json
📨 Request Body:
{
  "email" : "john@example.com",
  "password" : "password123"
}
📤 Réponse: POST /api/auth/login - Status: 200
📋 Response Body:
{
  "token" : "eyJhbGci...",
  "type" : "Bearer"
}
```

### Mode Debug VS Code

1. Placez des breakpoints en cliquant dans la marge gauche
2. Appuyez sur **F5**
3. L'application démarre et s'arrête sur vos points d'arrêt
4. Utilisez les contrôles de debug (F10 = step over, F11 = step into)

## 📝 Entités et modèles

### User
```java
- id: Long
- name: String (max 255)
- email: String (unique, max 255)
- password: String (encodé BCrypt)
- createdAt: LocalDateTime
- updatedAt: LocalDateTime
```

### Rental
```java
- id: Long
- name: String (max 255)
- surface: Integer (positif)
- price: Integer (positif)
- picture: String (URL, max 500)
- description: String (max 2000)
- ownerId: Long (référence User)
- createdAt: LocalDateTime
- updatedAt: LocalDateTime
```

### Message
```java
- id: Long
- rentalId: Long (référence Rental)
- userId: Long (référence User)
- message: String (max 2000)
- createdAt: LocalDateTime
- updatedAt: LocalDateTime
```

## ✅ Validation des données

**Constantes de validation** (`ValidationConstants.java`) :
- Noms, emails : max 255 caractères
- Descriptions, messages : max 2000 caractères
- Pictures : max 500 caractères
- Passwords : min 8 caractères

**Messages personnalisés** : `ValidationMessages.properties`

Les erreurs de validation retournent un status **400 Bad Request** avec détails.

## 🔐 Sécurité

### Configuration
- **Session** : STATELESS (pas de session HTTP, pas de cookies)
- **CSRF** : Désactivé (API REST avec JWT)
- **Password** : Encodage BCrypt
- **Token JWT** : HS256, expiration 24h

### Routes publiques
- `/api/auth/**` (register, login)
- `/swagger-ui/**`, `/v3/api-docs/**`

### Routes protégées
- Toutes les autres routes nécessitent `Authorization: Bearer {token}`

### Filtres de sécurité (ordre d'exécution)
1. **LoggingFilter** : Logs des requêtes/réponses
2. **JwtAuthenticationFilter** : Validation du token JWT
3. **UsernamePasswordAuthenticationFilter** : Filtre Spring Security par défaut

## 🚧 Limitations connues

- **Upload de fichiers** : ✅ Implémenté avec FileStorageService (UUID unique, validation, stockage dans `uploads/pictures/`)
- **Refresh token** : Pas de mécanisme de rafraîchissement automatique du token (expiration fixe 24h)
- **Roles/Authorities** : Pas de gestion des rôles utilisateur (tous les utilisateurs ont les mêmes droits)
- **Pagination** : Les listes (`GET /api/rentals`, etc.) ne sont pas paginées

## 📄 Licence

Projet d'exercice OpenClassrooms - Projet 3

## 👤 Auteur

Kevin Renault

---

**Version** : 0.0.1-SNAPSHOT  
**Dernière mise à jour** : Décembre 2025
