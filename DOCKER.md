# Docker

## Lancer l'application avec Docker

### Prérequis
- Docker installé
- Docker Compose installé

### Lancement

```bash
# Builder et lancer
docker compose up -d

# Voir les logs
docker compose logs -f app

# Arrêter
docker compose down
```

### Accès
- Application : http://localhost:8080/mon-futur-stage/mvc/

### Utilisateurs préconfigurés

| Type | Username | Password | Rôle |
|------|----------|----------|------|
| Étudiant | mail1@ens.univ-artois.fr | password | STUDENT |
| Étudiant | mail2@ens.univ-artois.fr | password | STUDENT |
| Admin | johan | admin123 | ADMIN |
| Admin | daniel | admin123 | ADMIN |
| Admin | admin | admin | ADMIN |
