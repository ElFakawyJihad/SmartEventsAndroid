# SmartEventsAndroid

## Branche Master
La branche Master doit toujours être en état de fonctionner, c'est-à-dire qu'on ne doit pas directement push dessus. 
Il faut tout d'abord utiliser les branches intermédiaires

## Branche Develop
Il s'agit de la branche d'où doit débuter chaque développement.
Cependant, même sur cette branche il ne faut pas faire un push directement.

## Branches Feature-SpecificName
Ce sont les branches futures qui servent vraiment au développement.
Comme leurs noms l'indiquent elles permettent de développer une fonctionnalité spécifique.

## Branches Release-SpecificName
Ces branches prennent le relai sur les branches de Feature, pour comme leurs noms indiquent faire une livraison.
A la fin du développement, ce sont elles et seulement elles qui seront fusionnées avec la branche Develop.
Et, c'est seulement après cette fusion que l'on peut fusionner les branches Develop et Master ensemble.

## Conclusion
Nous allons utiliser les branches pour les futurs développements.
Tout le monde part de la branche Develop pour commencer et chacun crée ses branches Feature et Release pour une fonctionnalité.
(PS: il faudra supprimer ces branches après que vous les ayez fusionnées à la branche Develop)
Si vous connaissez un gestionnaire de branches, nécessitez à le présenter.

