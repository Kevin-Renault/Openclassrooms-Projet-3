#!/bin/bash

if [ "$1" = "full" ]; then
    echo "Compilation et installation du projet..."
    ./mvnw clean install -DskipTests
    if [ $? -ne 0 ]; then
        echo "Erreur lors de la compilation"
        exit 1
    fi
    echo ""
fi

echo "Lancement de l'application..."
java -jar target/excercice1-0.0.1-SNAPSHOT.jar
