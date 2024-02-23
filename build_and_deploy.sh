#!/bin/bash

echo "Relancement des containers avec Docker Compose..."
# Ajuste le chemin si ton docker-compose.yml n'est pas à la racine du projet
docker-compose up -d --build

if [ $? -ne 0 ]; then
    echo "Le redémarrage des containers a échoué."
    exit 1
fi

echo "Toutes les applications ont été reconstruites et les containers relancés avec succès."
