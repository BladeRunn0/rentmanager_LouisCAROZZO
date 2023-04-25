# rentmanager_LouisCAROZZO

Ce repo GIT contient mon projet Rentmanager.

Chacune des contraintes imposées suivantes ont pu être implémentées:
1. un client n'ayant pas 18 ans ne peut pas être créé
2. un client ayant une adresse mail déjà prise ne peut pas être créé
3. le nom et le prénom d'un client doivent faire au moins 3 caractères
4. une voiture ne peut pas être réservé 2 fois le même jour
5. une voiture ne peut pas être réservé plus de 7 jours de suite par le même utilisateur
6. une voiture ne peut pas être réservé 30 jours de suite sans pause
7. si un client ou un véhicule est supprimé, alors il faut supprimer les réservations associées
8. une voiture doit avoir un modèle et un constructeur, son nombre de place doitêtre compris entre 2 et 9 
Celles-ci ont été mises dans les servlets sous forme de fonction retournant des booléens afin de faciliter la lecture du code.

Pour le moment, seules les icônes d'édition et de visualtisation des caractéristiques d'un client, d'un véhicule et d'une réservation n'ont pas été implémentées.
La suppression est fonctionnelle pour chacun des éléments.
