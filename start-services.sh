#!/bin/bash

# Couleurs pour le texte
GREEN='\033[0;32m'
BLUE='\033[0;34m'
RED='\033[0;31m'
NC='\033[0m' # No Color

# Variable pour stocker le mode de démarrage
START_MODE=""

# Fonction pour gérer l'arrêt propre
cleanup() {
    print_info "\nArrêt des services en cours..."
    if [ "$START_MODE" = "local" ]; then
        stop_local
    elif [ "$START_MODE" = "docker" ]; then
        stop_docker
    fi
    print_success "Tous les services ont été arrêtés."
    exit 0
}

# Capture du signal Ctrl+C
trap cleanup SIGINT SIGTERM

# Fonction pour afficher un message en vert
print_success() {
    echo -e "${GREEN}$1${NC}"
}

# Fonction pour afficher un message en bleu
print_info() {
    echo -e "${BLUE}$1${NC}"
}

# Fonction pour arrêter les services en local
stop_local() {
    print_info "Arrêt des processus Maven..."
    pkill -f "spring-boot:run" || true
    cd "$(dirname "$0")"
}

# Fonction pour arrêter les services Docker
stop_docker() {
    print_info "Arrêt des conteneurs Docker..."
    docker-compose down
    cd "$(dirname "$0")"
}

# Fonction pour démarrer les services en local avec Maven
start_local() {
    START_MODE="local"
    print_info "Démarrage des services en local avec Maven..."
    cd "$(dirname "$0")"
    
    # Démarrage du service config
    print_info "Démarrage du Service Config..."
    cd service-config
    mvn spring-boot:run &
    
    # Attendre que le service config soit prêt
    print_info "Attente du démarrage du Service Config..."
    while ! curl -s http://localhost:8080/actuator/health &>/dev/null; do
        sleep 2
        echo -n "."
    done
    echo ""
    print_success "Service Config est prêt!"
    
    # Démarrage du service registry
    print_info "Démarrage du Service Registry..."
    cd ../service-registry
    mvn spring-boot:run &
    
    # Attendre que le service registry soit prêt
    print_info "Attente du démarrage du Service Registry..."
    while ! curl -s http://localhost:8761/actuator/health &>/dev/null; do
        sleep 2
        echo -n "."
    done
    echo ""
    print_success "Service Registry est prêt!"
    
    # Démarrage du service proxy
    print_info "Démarrage du Service Proxy..."
    cd ../service-proxy
    mvn spring-boot:run &
    
    # Attendre que le service proxy soit prêt
    print_info "Attente du démarrage du Service Proxy..."
    while ! curl -s http://localhost:8079/actuator/health &>/dev/null; do
        sleep 2
        echo -n "."
    done
    echo ""
    print_success "Service Proxy est prêt!"
    
    print_success "Tous les services sont en cours de démarrage!"
    print_info "
    URLs des services:
    - Service Config: http://localhost:8080
    - Service Registry: http://localhost:8761
    - Service Proxy: http://localhost:8079"
}

# Fonction pour démarrer les services avec Docker
start_docker() {
    START_MODE="docker"
    print_info "Démarrage des services avec Docker..."
    cd "$(dirname "$0")"
    
    # Vérifier si Docker est en cours d'exécution
    if ! docker info > /dev/null 2>&1; then
        echo -e "${RED}Erreur: Docker n'est pas en cours d'exécution. Veuillez démarrer Docker d'abord.${NC}"
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
        print_info "\nAppuyez sur Ctrl+C pour arrêter tous les services..."
        wait
        ;;
    2)
        start_docker
        print_info "\nAppuyez sur Ctrl+C pour arrêter tous les services..."
        wait
        ;;
    3)
        print_info "Au revoir!"
        exit 0
        ;;
    *)
        echo -e "${RED}Choix invalide${NC}"
        exit 1
        ;;
esac
