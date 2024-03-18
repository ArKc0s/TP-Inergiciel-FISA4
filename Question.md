# TP-Inergiciel-FISA4 Réponse aux questions

## Question N°1:
### Cette architecture est sympathique et répond au problème, mais n’aurait-il pas été aussi possible dansl’hypothèse où je ne connaîtrais pas les bénéfices du bus Kafka de faire autrement ?<br>Proposer une architecture qui répond au même besoin sans Kafka ou éventuellement un autre bus intergiciel.

Oui, on aurait pu réaliser cette architecture sans l'utilisation de Kafka ou d'un autre bus intergiciel. Voici la démarche :

Au lieu d'utiliser Kafka, on peut concevoir une architecture basée sur une file d'attente locale ou un système de fichiers partagé pour le transport des messages entre les différents modules. Par exemple, le producteur pourrait déposer les fichiers JSON dans un dossier partagé, et le consommateur pourrait surveiller ce dossier et traiter les fichiers dès qu'ils y sont déposés.

Cependant, cette approche pourrait présenter des défis en termes d'évolutivité, de tolérance aux pannes et de gestion des flux de données. Avec Kafka on peut répartir des charges, avoir de la persistance des messages, et une architecture distribuée qui facilite la mise en œuvre de systèmes solides et évolutifs.

## Question N°2:
### Donnez votre avis sur les deux architectures celle proposé dans le TP et celle que vous proposerez, qu’elle est l’architecture la plus simple à mettre en place et à maintenir, pourquoi ?

Les deux architectures ont leurs avantages et leurs inconvénients. La solution proposée dans le TP avec Kafka offre une évolutivité élevée, une tolérance aux pannes, et une gestion efficace des flux de données. Mais, elle peut être complexe à mettre en place et nécessite une connaissance approfondie de Kafka.

Une architecture sans Kafka pourrait être plus simple à mettre en œuvre, mais elle pourrait être moins évolutive et moins robuste. Elle pourrait également être plus difficile à maintenir et à faire évoluer à mesure que les besoins de l'entreprise changent.

Si on cherhce une solution hautement évolutive et tolérante aux pannes, la solution avec Kafka pourrait être la meilleure option.

## Question N°3:
### Cette architecture comme développé actuellement, ne propose pas de sécurité dans les échanges des messages sous Kafka, je vous demanderai donc de me présenter synthétiquement les différentes possibilités de sécurisation des échanges dans un bus Kafka dans un premier temps.<br>Dans un second temps, vous choisirez un procédé possible parmi les différentes possibilités et exposerez clairement le principe de mise en place de celle-ci sur votre projet

Pour sécuriser les échanges dans un bus Kafka, on retrouve plusieurs options de sécurité :

SSL/TLS : Utilisation de SSL/TLS pour chiffrer les communications entre les producteurs, les consommateurs et les brokers Kafka.

Authentification : Configuration de l'authentification pour s'assurer que seuls les utilisateurs autorisés peuvent accéder au bus Kafka.

Autorisation : Définition de politiques d'autorisation pour contrôler les actions spécifiques que chaque utilisateur peut effectuer sur les topics Kafka.

SASL (Simple Authentication and Security Layer) : Mise en place de SASL pour une authentification sécurisée dans un environnement distribué.

Kerberos : Utilisation de l'authentification Kerberos pour renforcer la sécurité des échanges.

Pour votre projet, vous pourriez opter pour une combinaison de SSL/TLS pour le chiffrement et l'authentification.

Configuration SSL/TLS pour Kafka :

On génére des certificats SSL/TLS pour les brokers Kafka, les producteurs et les consommateurs. Pour cela, on peut utiliser des outils comme OpenSSL pour générer ces certificats.

On configure les brokers Kafka pour utiliser les certificats SSL/TLS. Cela nécessitera des modifications dans les fichiers de configuration des brokers Kafka pour spécifier les emplacements des certificats.

On configure les producteurs et les consommateurs pour utiliser les certificats SSL/TLS lors de la communication avec les brokers Kafka. Cela implique également la spécification des emplacements des certificats dans les configurations des producteurs et des consommateurs.

Configuration de l'authentification pour Kafka :

On configure les brokers Kafka pour utiliser un mécanisme d'authentification approprié, comme SASL/SSL.

On définie les utilisateurs et les mots de passe pour l'authentification SASL.

On configure les producteurs et les consommateurs pour utiliser le même mécanisme d'authentification que les brokers Kafka. Cela implique également la spécification des informations d'identification dans les configurations des producteurs et des consommateurs.