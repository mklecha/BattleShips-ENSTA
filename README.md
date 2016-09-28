# TP 2: Les bases de Java
### BattleShips CLI
##### Problématique

Le but du projet est de réaliser un jeu de Bataille Navale en ligne de commande (CLI).
Le programme devra répondre aux spécifications suivantes :
 - Dessiner une grille à taille variable pour les frappes.
 - Dessiner une grille de même taille pour les navires.
 - Placer des navires sur la grille.

Le jeu se déroulera en 2 phases :
 - La phase de placement. L'utilisateur doit entrer les positions de ses 5 navires.
 - La phase de jeu. L'utilisateur entre les positions de ses frappes.

On compte les types de navires suivants :
 - 1x Destroyer (D) - taille 2
 - 2x Sub-marine (S) - taille 3
 - 1x BattleShip (B) - taille 4
 - 1x Aircraft-cairrier ( C) - taille 5

### Exercice 1 : Affichage du "Board"
##### Notions abordées:
 - Conditions, manipulation console, exceptions...

Nous allons créer une classe "Board", qui représente les 2 grilles sur lesquelles sont placés les navires et les frappes.

 - Créer la classe Board, composée d'un nom, d'un tableau 2D de "Character" pour les navires, et d'un tableau 2D de "Boolean" pour les frappes.
 - Créer un constructeur avec arguments, qui prends en entrée un nom, et la taille de la grille.
 - Créer un constructeur avec argument, qui prends en entrée un nom seulement et initialise la grille avec une taille de 10.
 - Créer une méthode print dans Board, qui efface la console et dessine les 2 grilles côte à côte.
 - Créer une classe TestBoard et sa méthode main(), afin de tester l'affichage des grilles.

NB : il est possible de vider la console avec la commande suivante:
```java
try {Runtime.getRuntime().exec("clear");} catch (IOException e) {}
```

Dans un premier temps, nous ne nous occupons pas des navires. Le "Board" reste donc vide.

Visuellement, le board affiché peut ressembler à ceci :
```sh
Navires : 		 Frappes :
  A B C D E F G H I J       A B C D E F G H I J
1 . . . . . . . . . .     1 . . . . . . . . . .
2 . . . . . . . . C .     2 . . . . . . . . . .
3 . . S . . . . . C .     3 . . . . . . . . . .
4 . . S . . . . . C .     4 . . . . . . . . . .
5 . . S . D D . . C .     5 . . . . . . . . . .
6 . . . . . . . . C .     6 . . . . . . . . . .
7 . . . . . . . . . .     7 . . . . . . . . . .
8 . . S S S . . . . .     8 . . . . . . . . . .
9 . . . . . . . . . .     9 . . . . . . . . . .
10. . . . . B B B B .     10. . . . . . . . . .
```
Question :
 - que se passe-t-il si la taille de grille est trop grande ? ( > taille de la fenêtre) ? Proposer une solution pour y remédier.

Lorsque l'étape est terminée, entrer les commandes suivantes pour valider l'étape.
```sh
git add . -A
git commit -m"step 1"
```

### Exercice 2 : Classes ***Ship.
##### Notions abordées:
 - Classe, héritage, surcharge

Nous allons nous attaquer à la création des navires. Par soucis de pédagogie, nous choisirons de créer une classe par type de navire, chacune d'elles étant une classe fille de "AbstractShip"
La class AbstractShip possède les éléments suivants :
 - Un constructeur avec arguments, qui prend en entrée un nom, un label (le Character qui le représente), une taille et une orientation.
 - les méthodes getLabel() et getLength(), getOrientation() et setOrientation(/*...*/).

Vous devez :
 - Créer la classe AbstractShip
 - Créer les classes Destroyer, Submarine, Battleship, Carrier, qui héritent de AbstractShip.
 - Les classes filles devront posséder un constructeur par défaut (qui placent l'orientation à "null"), et un constructeur avec argument qui prends en paramètre l'Orientation).

Questions :
 - En quoi l'héritage est-il utile dans notre cas ?
 - Il commence à y avoir beaucoup de fichiers source pêle-mêle dans notre projet. Comment remédier à cela?
 - L'orientation est un ensemble fini de 4 valeurs, pouvant être "NORTH", "SOUTH", "EAST", "WEST". Quelle est la meilleure manière de représenter cette information ?
 - Nous allons avoir besoin de placer un navire sur le board. Est-il judicieux de créer une méthode "ship.setPosition(int x, int y)" ?

	```sh
	git add . -A
	git commit -m"step 2"
	```

### Exercice 3 : Placement des navires.
##### Notions abordées:
 - interfaces, exceptions

Il est maintenant temps de placer les navires sur la grille. Il semble nécessaire que la classe Board soit dotée de méthodes pour placer les navires et les frappes.
Nous allons modifier la classe "Board" pour lui faire implémenter l'interface "IBoard".

Rappel: Les interfaces sont des classes 100% abstraites. Elles n'ont que la définition des méthodes, sans leurs implémentations. Elles sont utilisées pour forcer les classes filles à implémenter un comportement générique.
Il existe deux conventions de nommage pour les interfaces : Xxx-able (Drawable, Clickable, Obserable, ...) et I-xxxx (IEngine, IListener, ...). On s'efforce en général d'utiliser l'une des deux conventions.

 - modifier "Board" pour lui faire implémenter l'interface "IBoard".
 - écrire les méthodes "putShip", "hasShip", "putHit" et "getHit" qui placent les navires et les frappes dans leurs tableaux respectifs.
 - modifier la méthode main() de TestBoard, afin de placer quelques navires.
 - modifier la méthode print() de Board, afin d'afficher le label des navires à leurs emplacements respectifs.

Questions :
 - Les indices x et y commencent-ils à 1 ou 0 ?
 - Que ce passe-t-il si la valeur "position + longueur" d'un navire mène hors de la grille ? Comment gérer ce cas ?
 - Que ce passe-t-il si deux navires se chevauchent? Comment gérer ce cas ?

	```sh
	git add . -A
	git commit -m"step 3"
	```
### Exercice 4 : Entrées utilisateurs.
##### Notions abordées:
 - Scanners, gestion d'exceptions.

Nous souhaitons que notre application propose au joueur de placer chacun des 5 navires, par ordre croissant de longueur.
Pour cela, l'utilisateur devra entrer à la suite les positions des navires, au format "A1 n", "B4 s".
> A chaque fois que l'on récupère des données de l'utilisateur (saisie formulaire, etc), il est TRES IMPORTANT de vérifier la cohérence et l'exactitude de ces données. Il faudra donc utiliser un bloc "`try... catch()`" pour s'assurer que les valeurs entrées sont correctes.

Afin de gagner du temps, vous pouvez utiliser la classe **"InputHelper"** fournie, dont la méthode readShipInput récupère les entrées de l'utilisateur et les converti en données exploitables. Notez bien la présence du bloc "try... catch" pour sécuriser les entrées utilisateur.

Exemple d'utilisation de InputHelper :
```java
    do {
        AbstractShip s = ships[i];
        ShipInput res = InputHelper.readShipInput();

        // TODO Convert orientation
        // orientation = res.orientation;
        s.setOrientation(orientation);

        try {
            board.putShip(s, res.x, res.y);
            ++i;
            done = i == 5;
        } catch(BoardException e) {
            System.err.println("Impossible de placer le navire a cette position");
        }
        board.print();

    } while (!done);

```

Il ne reste plus qu'à modifier notre classe TestBoard:
 - Créer un tableau des 5 navires :
	```java
	AbstractShip[] ships = {
		new Destroyer(), new Submarine(), new Submarine(), new BattleShip(), new Carrier()
	};
	```
 - Appeler la méthode `readShipInput` tant que tous les navires ne sont pas correctement placés
 - Paramétrer le Board avec les valeurs retournées
 - Afficher l'état actuel du Board entre chaque saisies

Question :
 - Quelle classe Java permet de lire les entrées clavier ?

```sh
git add . -A
git commit -m"step 4"
```
### Exercice 5 : Etat des navires et des frappes.
##### Notions abordées:
 - "refactors", exceptions

La conception actuelle du jeu nous pose maintenant 2 problèmes :
 - Les "Hits" peuvent en réalité avoir 3 états : "Inconnu", "touché", et "manqué". un **boolean** ne suffis plus. On peut en revanche se servir d'un **Boolean** qui peut être soit vrai, faux ou null. (Un **enum** serait aussi un choix judicieux)
 - Les navires sont placés avec un tableau de **"Character"**. Comment savoir où le navire commence et se termine, et donc s'il est totalement détruit ?

Il est courant de devoir modifier la conception d'un logiciel en cours de développement, et les raisons peuvent être multiples : Changement soudain du cahier des charges, erreur de conception, difficulté technique imprévue... Heureusement, ceci n'est pas toujours dramatique et il est parfois suffisant de quelques petites modifications pour remettre les choses dans l'ordre. C'est ce qu'on appelles dans le jargon un "refactor". Nos IDE sont générlement capables de nous assister dans cette tâche.

Nous aurons besoin d'une classe **"ShipState"** intermédiaire entre le navire et la grille, capable de mémoriser l'état du navire en un point précis. La classe **"ShipState"** possède :
 - un attribut de type **AbstractShip**, référence vers le navire concerné par cet état.
 - un attribut boolean **"struck"**, qui vaut "vrai" si le navire est touché en cet endroit.
 - une méthode **"void addStrike()"**, pour marquer le navire comme "touché"
 - une méthode **"boolean isStruck()"**, qui retourne la valeur de l'attribut "struck"
 - une méthode **"String toString()"**, qui retourne le label du navire associé (en rouge si le navire est touché en cet endroit)
 - un méthode **"boolean isSunk()"**, qui retourne "vrai" si le navire est totalement détruit.
 - une méthode **"AbstratShip getShip()"** qui retourne ne navire concerné par cet état.

Travail demandé :
 - Dans **"AbstractShip"**, créer un attribut entier "strikeCount" ainsi qu'une méthode "addStrike()", permettant de manipuler le nombre de frappes que le navire à reçu au total. Créer la méthode isSunk();
 - Créer la classe **"ShipState"**.
 - Dans **"Board"**, changer le tableau de **boolean "hits"** en un tableau de **Boolean**
 - Dans **"Board"**, changer le tableau de **Character "ships"** en un tableau de **"ShipState"**
 - Dans **"Board"**, changer la méthode **print()** pour afficher '.' si un Hit est null, 'X' en blanc si un hit est faux, et 'X' en rouge si un hit est vrai.
 - Modifier la classe "**TestBoard**" pour instancier un board, placer plusieurs navires, et appeler "**sendHit()**" sur ce board. Vérifier que les navires et les hits changent de couleurs en cas de touche.

>  NB : Vous pouvez utiliser la classe **ColorUtil** qui vous est fournie
```java
System.out.print(ColorUtil.colorize("Hello World with COLOR!!!", ColorUtil.Color.RED));
```

>  NB : Pour les utilisateurs de eclipse, vous pouvez télécharger ce plugin pour que la console eclipse supporte les couleurs : https://marketplace.eclipse.org/content/ansi-escape-console

Question :
 - Si on appelles **addStrike()** plus d'une fois par ShipState, le navire pourra donc être touché plus que le permet sa longueur. Comment gérer cet ***"état illégal"*** ?
```sh
git add . -A
git commit -m"step 5"
```
### Exercice 6 : Envoyer des frappes
##### Notions abordées:
 - enums, interfaces

Avant d'aller plus loin, il va falloir doter notre **"Board"** d'une méthode lui permettant de recevoir les frappes de l'adversaire, et qui permettra à l'adversaire de recevoir les nôtres :

```java
enum Hit {
		MISS(-1, "manqué"),
		STIKE(-2, "touché"),
		DESTROYER(2, "Frégate"),
		SUBMARINE(3, "Sous-marin"),
		BATTLESHIP(4, "Croiseur"),
		CARRIER(5, "Porte-avion")
		;
		private int value;
		private String label;
		Hit(int value, String label) {
			this.value = value;
			this.label = label;
		}

		public static Hit fromInt(int value) {
			for (Hit hit : Hit.values()) {
				if (hit.value == value) {
					return hit;
				}
			}
			throw new NoSuchElementException("no enum for value " + value);
		}

		public String getLabel() {
			return this.label;
		}
	};
/**
 * Sends a hit at the given position
 * @param x
 * @param y
 * @return status for the hit (eg : strike or miss)
 */
Hit sendHit(int x, int y);
```
L'adversaire appelera donc la méthode **sendHit()** sur notre **Board**, tandis que nous appelerons **sendHit()** sur le siens.
L'enum **"Hit"** permet de renvoyer le status d'une frappe lancée. Les valeurs peuvent être **"MISS"** ou **"STRUCK"**, pour une frappe manquée ou réussie, ou bien le nom d'un des 4 navires lorsqu'un navire viens d'être coulé totalement.

>  NB : L'enum **"Hit"** est particulier : Il possède un constructeur, ce qui nous permet de lui faire porter des valeur. Cela sera pratique lorsque nous voudrons créer l'enum directement à partir de la longueur du navire, grace à la méthode **fromInt()**, ou lorsque nous voudrons avoir le nom ("label") du navire détruit)

Travail demandé :
 - Copier coller le code ci dessus dans **IBoard**, puis l'implémenter dans **Board**.
 - Implémenter la méthode **"sendHit()"** en prenant soin de retourner la bonne valeur si un navire est détruit.
 - Modifier la classe **"TestBoard"** pour envoyer des frappes sur l'unique destroyer de votre board. Vérifier que le destroyer s'affiche en rouge.
 - Vérifier que **destroyer.isSunk()** retourne "vrai", et que le dernier appel à **"sendHit()"** retourne **Hit.DESTROYER**. Afficher "coulé" le cas échéant.

Question :
 - Que se passe t-il lorsque l'on appelles **sendHit()** deux fois sur la même position d'un navire ?
 - que renvoit la méthode **hasShip(x, y)** lorsque le navire en (x, y) a été touché?

```sh
git add . -A
git commit -m"step 6"
```
### Exercice 7 : IA.
##### Notions abordées:
 - Random, Listes, retour par référence

A ce point, nous devrions être capable de :
 - saisir des coordonnées depuis le clavier
 - placer les navires sans erreurs
 - envoyer des frappes sur une grille
 - détécter si un navire est "touché" ou "coulé" suite à une frappe.

Nous avons tous les éléments nécéssaires à la logique de notre jeu, mais il nous manque une chose essentielle : un adversaire.

La classe **"BattleShipsAI"** vous est fournie. Elle propose une Intelligence Artificielle rudimentaire Il vous reste à compléter la méthode `void putShips(AbstractShip ships[])`, qui permet de placer les navires sur `this.board` de manière aléatoire.

> NB : Notez que BattleShipsAI a besoin de deux objets **Board** (un par joueur) pour fonctionner. Votre **"Board"** implémente l'interface **"IBoard"**, ce qui permet à **"BattleShipsAI"** de savoir comment interagir avec votre Board, sans en connaitre les détails d'implémentation. **IBoard** est en quelque sorte le "Manuel d'utiisation" d'un objet **Board** (On parle de "**contrat**").

Pour tester le placement des navires, nous allons faire jouer l'IA contre elle même sur un seul Board. Ecrire une classe **TestGame** et sa fonction main(), qui devra :
 - initialiser un objet "Board" et l'afficher
 - initialiser une liste de navires. Notez qu'un simple tableau pourrait suffire, mais les listes s'avèrerons utiles plus tard dans la partie Bonus.
 - initialiser un objet **"ai"** de type **BattleShipsAI**, qui utilise le même Board pour la grille amie et adverse.
 - créer un compteur qui compte le nombres de navires détruits.
 - tant qu'il reste des navires,
    -  appeler la méthode `ai.sendHit()`
    -  afficher les coordonnées du hit et sont résultat ("touché" ou "manqué", ou "XXX coulé"). La méthode `IBoard.Hit.getLabel()` vous sera utile.
    -  afficher le nouvel état du board

Vous pouvez utiliser la méthode "`sleep(int ms)`" suivante pour temporiser la boucle de jeu :
```java
private static void sleep(int ms) {
	try {
		Thread.sleep(ms);
	} catch (InterruptedException e) {
		e.printStackTrace();
	}
}
```

Travail à faire :
 - Compléter **putShips()** dans **BattleShipsAI** (utiliser un **java.util.Random**)
 - Ecrire une classe **TestGame**

>  NB : Vous remarquerez que **List** utilise une syntaxe avec des chevrons (<>). C'est ce qu'on appelles un **"Generic"** (équivallent des "templates" en c++). C'est un paramètre qui indique que les éléments contenus dans la liste serons du type passé entre chevrons.

Questions :
 - La méthode `sendHit(int[] coords)` est sensée choisir une frappe à des coordonnées aléatoires. Pourquoi lui passe t-on des coordonnées en paramètres ?
 - Quelles différences voyez vous entre une List et un simple tableau?
 - Quelle type de liste avez vous utilisé ? Commentez.

```sh
git add . -A
git commit -m"step 7"
```
### Exercice 8 : Place au jeu!
##### Notions abordées:
 - Scanners, héritage

Notre jeu est presque prêt. Pour l'instant, la boucle de jeu consiste en une IA qui joue toute seule. Il est temps de lui rajouter un vrai adversaire. La classe **Player** représente un joueur. Elle possède entre autre deux **Board** (le siens, et celui de son adversaire), et une liste de navires. Ses méthodes **putShip** et **sendHit** (à compléter) doivent lire l'entrée clavier pour respectivement placer les navires et les frappes sur le **Board** enemi.
La classe **AIPlayer** étends de **Player**. Elle doit **redéfinir** les méthodes **putShip** et **sendHit** pour utiliser son **AI** plutôt que le clavier.

Travail à faire :
 - Compléter la classe **Player**
 - Compléter la classe **AIPlayer** : redéfinir les méthodes **putShip** et **sendHit**
 - permettre la saisie du nom du joueur 1 au clavier. (utiliser un scanner)
 - modifier la boucle principale : Tant que aucun joueurs n'est hors jeu :
    - afficher le nom du joueur 1 ainsi que son Board
    - saisir les coordonnés de la frappe (`player.sendHit()`)
    - réafficher le board
    - afficher les coordonnées du hit et sont résultat
    - recevoir la frappe de l'adversaire et réafficher le board
    - afficher les coordonnées du hit et sont résultat

Question :
 - En quoi l'héritage est t'il utile dans notre cas ?
 - Comment pourrait-on modifier la classe "Game" pour proposer un mode 2 joueurs (sans IA) ?
```sh
git add . -A
git commit -m"step 8"
```
### Exercice 9 : Bonus 1
Rendre le tirage des navires aléatoire (nombre et types de navires différents pour chaque joueurs)

### Exercice 10 : Bonus 2
##### Notions abordées:
 - Serializable, File, InputStream
On souhaite sauvegarder la partie avant de quitter l'application, de telle sorte à ce que la dernière partie jouée soit automatiquement restaurée au relancement du jeu.

L'idée est de, à chaque tour du jeu, écrire sur le disque dur un fichier qui mémorise l'état actuel du jeu. Au lancement du jeu, on vérifie la présence de ce fichier et on le charge le cas échéant.

> "Oula, c'est bien trop compliqué à mettre en oeuvre..."

A vrai dire, cette fonctionnalité ne devrait prendre que quelques minutes à implémenter! Tout objet Java peut être sauveagardé dans un fichier, sous peu qu'il soit **"Serializable"**. Un objet dit "serializable" est un objet qui implémente l'interface "Serializable", et dont tous les attributs implémentent aussi cette interface.

Exemple de serialization d'un objet :
```java
public class TestSerialize {
    static class Computer implements Serializable {
        private List<Device> devices = new LinkedList<>();

        public Computer(Device... device) {
            this.devices.addAll(Arrays.asList(device));
        }
    }

    static abstract class Device implements Serializable {
        private String name;

        public Device(String name) {
            this.name = name;
        }
    }

    static class Screen extends Device implements Serializable {
        public Screen() {
            super("screen");
        }
    }

    static class Keyboard extends Device implements Serializable {
        public Keyboard() {
            super("keyboard");
        }
    }

    public static void main(String[] args) {
        Computer c = new Computer(new Keyboard(), new Screen());
        File saveFile = new File("saveFile");
        saveComputer(c, saveFile);
        c = loadComputer(saveFile);
    }

    private static Computer loadComputer(File saveFile) {
        Computer computer = null;
        if (saveFile.exists()) {
            try {
                ObjectInputStream ois =
                        new ObjectInputStream(new FileInputStream(saveFile));

                computer = (Computer) ois.readObject();
                ois.close(); // NEVER FORGET TO CLOSE STREAMS, OR IT WILL CAUSE MEMORY LEAKS !
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return computer;
    }

    public static void saveComputer(Computer c, File saveFile) {
        try {
            if (!saveFile.exists()) {
                saveFile.getAbsoluteFile().getParentFile().mkdirs();
            }
            FileOutputStream out = new FileOutputStream(saveFile);
            ObjectOutputStream oout = new ObjectOutputStream(out);
            oout.writeObject(c);
            oout.flush();
            oout.close(); // NEVER FORGET TO CLOSE STREAMS, OR IT WILL CAUSE MEMORY LEAKS !
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

