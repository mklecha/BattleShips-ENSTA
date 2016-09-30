# TP 2: Les bases d'android
### BattleShips CLI
##### Problématique

 > Le projet peut se faire à l'aide d'un émulateur Android (intégré à Android Studio), ou bien un appareil Android version 4 minimum. Il est possible sur un appareil physique de renontrer cette erreur : `AndroidRuntimeException: Calling startActivity() from outside of an Activity...`. Sans rentrer dans les détails, cette erreur peut être corrigée en ajoutant cette ligne juste avant l'appel de `startActivity(intent)` : `intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);`

 > Android Studio vous indiquera que l'application ne possède pas d'Activity de point d'entrée. C'est normal, l'activity principale "PlayerNameActivity" est à écrire au cours du premier exercice.

Le but du projet est de réaliser le portage android du jeu de Bataille Navale (CLI).
Le programme devra répondre aux spécifications suivantes :
 - Permettre de saisir le nom du joueur.
 - Dessiner une grille permettant de placer les navires.
 - Dessiner cote à cote la grille des navires et celle pour envoyer des frappes
 - jouer contre une IA.

Le TP 2: "Les bases de Java - BattleShips CLI" doit au moins être avancé jusqu'à l'exercice 6 "Envoyer des frappes" pour suivre ce TP.

### Exercice 1 :  Saisie du nom du joueur.
##### Notions abordées:
 - Android studio, Activity, RelativeLayout, EditText, Listener, AndroidManifest

Notre application à besoin d'un point d'entrée. Nous choisirons de démarrer l'application sur un écran  permettant de saisir le nom de l'utilisateur. Sur android, chaque "écran" est représnté par une Activity.

 > Pour rappel, une Activity doit être considérée comme le "**Controlleur**" dans un modèle **MVC**. Son rôle est de "porter" la vue, d'en "controller" le dynamisme (écouter actions utilisateurs, mise à jour de vue...), et de transmettre les instructions adéquates au modèle.

Android Studio permet de créer facilement des Activity : clic droit => new => activity. Choisissez 'Blank Activity', pour créer une classe Activity et sa vue xml associée les plus simples possibles.

 > Conseils : Commencez par élaborer votre activité par le layout xml, à l'aide de l'Android Layout Designer. Pensez à donner un id pertinnent à chacun des widgets avec lesquels vous prévoyez d'interagir (bouttons, champs textes, etc..) Une fois les éléments crées et correctement agencés, allez dans l'Activity java, puis créez un attribut pour porter chacun des widgets interactifs. La fonction `onCreate()` est le constructeur de l'Activity : c'est ici qu'il faut "binder" vos attributs widgets java avec leurs homologues XML. (fonction `findViewById`).

 > Une convention de code android consiste à donner aux attributs le préfix 'm' : private int mAttribute = 0; Libre à vous d'adopter ou pas cette convention.


Exemple d'activity :
 - XML
```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:fitsSystemWindows="true"
    tools:context="com.example.Activity">

        <EditText
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:hint="@string/enter_name_hint"
            android:id="@+id/example_text_name"
            android:layout_alignParentLeft="true"
            android:layout_alignParentStart="true" />

        <Button
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="@string/button_enter_name"
            android:id="@+id/example_btn_name"
            android:onClick="onClickButton"
            android:layout_below="@+id/example_text_name" />
</RelativeLayout>

```
 - Java
```java
public class ExampleActivity extends AppCompatActivity {
    EditText mNameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);  // bind view
        mNameEditText = (EditText) findViewById(R.id.edit_player_name);  // bind widgets
    }

    public void onClickButton(View v) {
        String name = mNameEditText.getText().toString();
        if (!name.isEmpty()) {
           /* do stuff */
           Toast.makeText(name, Toast.LENGTH_LONG).show();
        }
    }
}
```
Travail à faire
 - Créer un package "anduid.ui" dans le package de votre projet. Ce package contiendra tout ce qui est propre à l'affichage (activitées, ...)
 - Créer une Activity "PlayerNameActivity", dont le "root view" est un "**RelativeLayout**"
 - Ajouter un EditText pour entrer le nom du joueur
 - Ajouter un TextView au dessus, avec la mention "Entrez votre nom"
 - Ajouter un boutton pour valider le champ.
 - Ajouter un "**callback onClick**" dans l'Activity.

	```sh
	git add . -A
	git commit -m"step 1"
	```

 ### Exercice 2 :  Initialisation d'un jeu.
 - classe Application, Singleton, Intent

Nous avons le nom du joueur, et nous en aurons besoin pour lui indiquer son tour tout au long du jeu. Il est possible de passer des informations d'une Activity à l'autre via les "**Intent**" et les "**Extra**". Le code ci après permettrait à "**PlayerNameActivity**" de passer à l'Activity "**BoardActivity**", en lui fournissant le nom du joueur :
```java
Intent intent = new Intent(this, BoardActivity.class);
intent.putExtra("WHATEVER_KEY_HERE", name);
startActivity(intent);
```

Cette approche possède toutefois quelques limitations :
 - Si **BoardActivity** a besoin du "name", mais que **PutShipActivity** est entre **PlayerNameActivity** et **BoardActivity**, alors il faut passer *inutilement* le "name" à **PutShipActivity** qui le passera à son tour à **BoardActivity**
 - Si l'on a besoin de passer des objets complexes (un Board, une liste de navires), ca demande du travail supplémentaire (voir `Serializable` / `Parcelable`).

Nous choisirons donc de passer nos variables dans un contexte global à l'application. La classe "Application" est comparable à une "Activity" invisible. Elle possède une fonction "onCreate()" appelée au lancement de l'application, et vous permet de stocker de manière statique toutes les valeurs et comportements transverses à l'application. Par essence, une "Application" est un singleton : Il en existe une et une seule, vous ne pouvez pas en instancier d'autres, vous y avez accès depuis partout.

 NB: Pour indiquer à Android de construire notre application par la classe "**BattleShipApplication**", il est nécéssaire d'en informer le **AndroidManifest** :
 ```xml
<application
    android:name="com.excilys.formation.battleships.android.ui.BattleShipsApplication"
    ...
 ```

 C'est notre application qui sera chargé de créer le jeu, à l'aide de la classe Game ci après
```java
 public class Game {
        /* ***
         * Attributes
         */
        private Player mPlayer1;
        private Player mPlayer2;
        /* ***
         * Methods
         */
        public Game() {
        }

        public Game init(String playerName) {
            mPlayerName = playerName;

            Board b = new Board(playerName);
            mBoard = new BoardController(b);
            mOpponentBoard = new Board("IA");

            mPlayer1 = new Player(playerName, b, mBoard2, createDefaultShips());
            mPlayer2 = new AIPlayer(playerName, mBoard2, b, createDefaultShips());

            // place player ships
            mPlayer1.putShips();
            mPlayer2.putShips();
            mPlayers = new Player[] {mPlayer1, mPlayer2};

            return this;
        }

        private List<AbstractShip> createDefaultShips() {
            return Arrays.asList(new AbstractShip[]{new DrawableDestroyer(), new DrawableSubmarine(), new DrawableSubmarine(), new DrawableBattleship(), new DrawableCarrier()});
        }
    }
```
 > Note : La méthode "init()" de la classe **Game** est un copié collé de celle du TP "BattleShips CLI", épuré de que tout ce qui n'est pas appliquable sur Android (sauvegarde, System.out.println(), Scanners...)

Travail à faire. Dans **BattleShipsApplication** :
 - copier-coller la classe **Game** (faire une "nested class")
 - créer les attributs statiques "**mBoard**", "**mOpponentBoard**" de type respectifs "**BoardController**" "**Board**", ainsi que leurs getters
 - créer l'attribut statique "**mGame**" de type Game, ainsi que son getter.
 - instancier mGame dans le `onCreate()` de l'application
 - Dans **PlayerNameActivity**, appeler `game.init()` en lui fournissant le nom du joueur.

 > NB : Vous avez remarqué la présence d'un "BoardController". Son utilité est décrite plus loin dans le TP. Pour l'instant, vous pouvez considérer qu'il se comporte exactement comme "Board".


```sh
git add . -A
git commit -m"step 2"
```
 ### Exercice 3 : Placement des navires - 1.
 - Intents

Nous souhaitons que lorsque l'utilisateur a entré son nom, il soit transporté sur un écran lui permettant de placer les Navires. Dans le TP 1, le placement des navires était fait via l'appel à la méthode `player.putShip()`, qui utilisait un `Scanner`. Il est clair que nous ne pouvons plus utiliser de scanner ici, nous utiliserons plutôt une Activity dédiée au placement des navires. La **PutShipActity** vous est fournie. Elle dessine une grille, qui au clicl place un navire sur le Board. Il faut que dans **Game*, l'appel à `mPlayer1.putShips()` lance cette Activity.

Travail à faire : Dans **BattleShipsApplication**
 - créer une nested class "**AndroidPlayer**" qui hérite de "**Player**"
 - redéfinir sa méthode `putShips()` afin de lancer la **PutShipsActivity**
 - modifier Game.init() pour que le joueur 1 soit de type `AndroidPlayer`

Question :
 - Lorsque le placement est terminé, l'utilisateur est transporté vers **BoardActivity**, et il n'est pas désirable qu'un appui sur "retour arrière" lui permette de revenir à **PutShipActivity**. Comment est géré ce cas ? Que se passe t'il au niveau de la "Pile d'Activity" ?
```sh
git add . -A
git commit -m"step 3"
```
 ### Exercice 4 : Placement des navires - 2.
 - Héritage, ressources, blocs statiques

**PutShipActivity** devrait dessiner un navire à chaque fois qu'on clique sur une case. Si vous avez essayé, vous avez sans douté été recu par une `IllelagArgumentException : Cannot put a Ship that does not implement DrawableShip`. Nous navires n'on pour l'instant aucune chance d'être dessinés, puisque il faut leurs attribuer un visuel. Chaque navire peut être représenté par 4 images (une par orientation). Les images sont situées dans le dosser `res/drawables`, et sont accessibles depuis java grâce à la ligne suivante :
```java
 Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.my_drawable, null);
```
Cette ligne de code créé un objet `Drawable` à partir du fichier `res/drawable/myDrawable.png` identifié par l'entier `R.drawable.my_drawable`. Charger un **Drawable** nécéssite que le contexte de l'application soit disponible (ie : `this.getContext()` depuis une **Activity** ou un **Fragment**). C'est donc la classe **BoardGridFragment** qui fera le chargement des **Drawable** pour nous, mais cette classe à toutefois besoin des ID vers ceux ci.

Nous allons doter nos classes **XXXShip** du comportement "**ShipDrawable**", afin qu'ils retournent l'ID vers l'image correspondant à leurs orientation.

Travail à faire : Dans le package **android.ui.ships**
 - Créer les classes **DrawableDestroyer**, **DrawableSubmarine**, **DrawableBattleship** et **DrawableCarrier**, qui héritent respectivement de leurs homoloques, et qui implémentent l'interface "DrawableShip"
 - créer dans chacune des classes un tableau associatif `static final Map<Orientation, Integer> DRAWABLES = new HashMap<>();`
 - utiliser un bloc static pour initialiser cette Map avec les ID de ressources appropriés.
 - écrire la méthode getDrawable();

Exemple de bloc statique :
```java
class Example {
    private static int variable;

    static {
        // le code à l'intérieur de ce bloc sera éxecuté une seule fois au chargement de la classe Example.
        // les opérations effectuées sont donc partagées par toutes les instances de Example.
        variable = 12;
    }
}
```
```sh
git add . -A
git commit -m"step 4"
```
 ### Exercice 5 : Contrôle de la vue.
 - Fragments, Décorator

Nous l'avons vu, le dessin de la grille est géré par l'intermédiaire du Fragment **BoardGridFragment**. C'est lui qui est affiché lorsque la grille se dessine. Lorsque dans PutShipActivity, on place un navire sur le Board, on souhaiterais qu'il soit aussitôt dessiné par **BoardGridFragment**. C'est ici qu'entre en jeu le **BoardController**.

 > Vous avez remarqué que le **Board** du joueur 1 est un "**BoardController**", alors que celui de l'IA est un simple **Board**. Aussi, **BoardController** de type "**IBoard**" se manipule exactement comme un **Board**, et se construit dans **Game** à partir du **Board** réel. C'est ce qu'on appelles un **Design Pattern Decorator**. Voyez **BoardController** comme une "enveloppe" autour de **Board**, qui permet les mêmes fonctionnalités mais en le "décorant" de l'aspect "mise à jour de la vue". (Rappelez vous : un "Controller" dans le pattern MVC sert à mettre "controler la vue"

 Le **BoardController** créé deux **BoardGridFragment** pour respectivement les navires et les frappes d'un **Board**. Interagir avec le BoardController plutôt qu'avec le **Board** lui même doit permettre de mettre à jour les **BoardGridFragment**.

 **BoardGridFragment** possède une méthode `putDrawable(int drawable_id, int x, int y)` qui permet de placer un élément graphique sur sa grille.

 Travail à faire dans **BoardController** :
  - complétez les méthodes `putShip()`, `hasShip()`, `setHit()`, `getHit()`, `sendHit()` et `getSize()`. Ces méthodes doivent rediriger l'appel vers `mBoard`, et lorsque c'est nécéssaire, les "décorer" avec `putDrawable()` sur le fragment approprié.
  - Testez que les navires s'affichent bien aux emplacements voulus. Si `RuntimeException : must implement BoardGridFragmentListener`, tout sera corrigé à l'étape suivante.

 > Utiliser res/drawable/hit.png et res/drawable/miss.png pour les frappes réussies et manquées.

 ```sh
git add . -A
git commit -m"step 5"
```
### Exercice 6 : jeu.
 - Listeners


La phase de jeu se déroule dans le **BoardActivity**. **BoardActivity** mets en place un **Layout** de type **ViewPager**, qui lui permet de disposer des fragments côte à côte en passant de l'un à l'autre par un "balayage". Ici, notre viewPager permet d'afficher les deux fragments crées par le `BoardController` (voir `BoardController.getFragments()`). La mécanique de jeu est principalement implémentée dans les fonctions `doPlayerTurn()` et `doOpponentTurn()`, la première appelant la deuxième. Ces fonctions permettent aussi de :
 - balayer automatiquement le **ViewPager** pour afficher la grille adéquate en fonction du tour.
 - afficher des messages sur la partie inférieure de l'écran.

Ces fonctions de **BoardActivity** sont déja développées (ouf ;) ), mais il reste à **BoardActivity** de "Capter" les clics sur l'écran. Nous allons pour cela utiliser un "Listener". Nous savons que c'est **BoardGridFragment** qui capte les clics sur l'écran, mais il lui faut un moyen de transmettre cette information à **BoardActivity**. Si vous regardez dans **BoardGridFragment**, vous verrez une interface interne **BoardGridFragmentListener** qui définit la méthode `onTileClick(int id, int x, int y)`. **BoardGridFragment** considère que son appelant est de type **BoardGridFragmentListener**, c'est à dire que l'activité qui l'appelle doit fournir une implémentation de **BoardGridFragmentListener**. Ainsi, **BoardGridFragment** peut appeller la méthode `onTileClick(int id, int x, int y)` de son appelant, sans pour autant connaitre qui il est.

Travail à faire dans **BoardActivty**
 - implémenter l'interface **BoardGridFragment.BoardGridFragmentListener**
 - si `id == BoardController.HITS_FRAGMENT`, appeller la méthode `doPlayerTurn(int x, int y)`
 - Défier l'IA de bataille navale......
 -
  ```sh
git add . -A
git commit -m"step 6"
```
 ### Bonus : AsyncTask
 - AsyncTask, Threads

Notre application présente un défaut : Dès lors qu'on lance une frappe en cliquant sur la grille de hits, le ViewPager passe à la grille des navires. Il serait préférable de marquer un temps de pause avant cette transition.

Dans `doPlayerTurn()`, Vous pouvez utiliser la méthode `sleep(Default.TURN_DELAY);` juste avant l'appel à ` mViewPager.setCurrentItem(BoardController.SHIPS_FRAGMENT)` pour marquer une pause d'une seconde.

Question :
 - Sur quel Thread est effectué cette pause ? Quel problème cela pose t'il ?
 - Comment résoudre le problème ? S'inspirer de la méthode "doOpponentTurn()"

 ```sh
git add . -A
git commit -m"bonus 1"
```
