# garage-website

## Présentation

Application frontend pour le projet Garage Corner. Application en Spring 5 et utilisant thymeleaf comme template engine.

## Pour changer la configuration avec l'API

* Aller dans le répertoire : `src/main/resources/`
* Modifier ensuite le fichier `application.properties` pour la faire fonctionner avec votr propre configuration.

## Achitecture

* `frontend` : gestion de la configuration de l'pplication
* `frontend.api` : interaction avec l'API Garage Corner.
* `frontend.controller` : gestion des routes
* `frontend.model` : models

## Ajouter des endpoints

Pour l'ajout d'une nouvelle resources, par exemple la gestion séparé des commentaires, il suffit d'étendre l'interface API et de décrire le comportement de nos différents endpoints respectatn l'architecture REST. Le service ApiService se charge de la couche HTTP.

