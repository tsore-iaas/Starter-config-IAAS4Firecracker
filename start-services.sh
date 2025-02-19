#!/bin/bash

# Couleurs pour le texte
GREEN='\033[0;32m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Fonction pour afficher un message en vert
print_success() {
    echo -e "${GREEN}$1${NC}"
}

# Fonction pour afficher un message en bleu
print_info() {
    echo -e "${BLUE}$1${NC}"
}

# Fonction pour démarrer les services en local avec Maven
start_local() {
    print_info "Démarrage des services en local avec Maven..."
    
    # Démarrage du service config
    print_info "Démarrage du Service Config..."
    cd service-config
    mvn spring-boot:run &
    sleep 30  # Attendre que le service config soit prêt
    
    # Démarrage du service registry
    print_info "Démarrage du Service Registry..."
    cd ../service-registry
    mvn spring-boot:run &
    sleep 20  # Attendre que le service registry soit prêt
    
    # Démarrage du service proxy
    print_info "Démarrage du Service Proxy..."
    cd ../service-proxy
    mvn spring-boot:run &
    
    print_success "Tous les services sont en cours de démarrage!"
    print_info "
    URLs des services:
    - Service Config: http://localhost:8080
    - Service Registry: http://localhost:8761
    - Service Proxy: http://localhost:8079"
}

# Fonction pour démarrer les services avec Docker
start_docker() {
    print_info "Démarrage des services avec Docker..."
    
    # Vérifier si Docker est en cours d'exécution
    if ! docker info > /dev/null 2>&1; then
        echo "Erreur: Docker n'est pas en cours d'exécution. Veuillez démarrer Docker d'abord."
        exit 1
    fi
    
    # Construire et démarrer les conteneurs
    docker-compose up --build -d
    
    print_success "Les conteneurs Docker sont en cours de démarrage!"
    print_info "
    URLs des services:
    - Service Config: http://localhost:8080
    - Service Registry: http://localhost:8081
    - Service Proxy: http://localhost:8082
    - RabbitMQ Management: http://localhost:15672"
}

# Menu principal
clear
echo "==================================="
print_info "Script de démarrage des services"
echo "==================================="
echo ""
echo "Choisissez l'environnement de démarrage:"
echo "1) Local (Maven)"
echo "2) Docker"
echo "3) Quitter"
echo ""
read -p "Votre choix (1-3): " choice

case $choice in
    1)
        start_local
        ;;
    2)
        start_docker
        ;;
    3)
        print_info "Au revoir!"
        exit 0
        ;;
    *)
        echo "Choix invalide"
        exit 1
        ;;
esac
