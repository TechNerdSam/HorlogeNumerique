# 🚀 Futuristic Clock App / Application Horloge Futuriste

## English Documentation

### Project Title

**Futuristic Clock App: Halo Edition** 🕰️✨

### Tagline

A professional and elegant digital clock application with advanced features and a futuristic design, inspired by Halo. Built with Java 8+.

### Description

The **Futuristic Clock App** is a sophisticated desktop application designed to offer more than just time-telling. It features a stunning, customizable background image, a dynamic digital clock display, and a suite of integrated tools including alarms, a stopwatch, a countdown timer, and a world clock. Its intuitive, collapsible interface allows for seamless navigation between functionalities, while persistent settings ensure a personalized experience every time you launch. Crafted with passion, this application brings a touch of sci-fi elegance to your desktop.

### Features

  * **Dynamic Digital Clock** ⏰: Displays current time (HH:mm:ss) and date (DD MONTH YYYY) in a sleek, customizable format.
  * **Customizable Aesthetics** 🎨:
      * **Background Image**: Set any image from a URL or local file as the application background.
      * **Clock Colors**: Personalize the text color and background color of the main clock display.
  * **Intuitive UI** 🖥️: An undecorated, draggable window with a collapsible tab section for easy access to features.
  * **Alarm System** 🔔:
      * Set multiple alarms with custom times (HH:mm) and messages.
      * Configure alarms for daily repetition or specific dates (YYYY-MM-DD).
      * Choose custom WAV sound files for your alarms, or use the default.
      * Snooze functionality (5 minutes) for a gentle wake-up.
      * Persists all your alarm configurations.
  * **Stopwatch** ⏱️: Accurate stopwatch functionality with start, stop, and reset options, displaying milliseconds.
  * **Countdown Timer** ⏳: Set a timer for hours, minutes, and seconds, with an alarm sound upon completion.
  * **World Clocks** 🌍:
      * Add and manage multiple time zones to see the current time around the globe.
      * Persistence of all configured world time zones.
  * **Personalized Dedication Message** ❤️: A custom animated dedication message at the bottom of the window.
  * **Persistence**: All your preferences, including background image URL, clock colors, tab section state, alarms, alarm sound path, custom dedication text, and world time zones, are automatically saved and loaded.

### Technologies Used

  * **Java 8+**: The core development language.
  * **Swing**: For building the graphical user interface.
  * **AWT**: Fundamental graphics and UI toolkit.
  * **`java.text.SimpleDateFormat`**: For flexible date and time formatting.
  * **`java.time` API**: Modern date and time handling (e.g., `ZoneId`, `ZonedDateTime`, `LocalDate`, `Instant`).
  * **`java.util.Timer`**: For scheduling repetitive tasks like clock updates and alarm checks.
  * **`javax.sound.sampled`**: For robust audio playback (WAV files) for alarms and timers.
  * **`java.util.prefs.Preferences`**: For persisting user settings and data across application sessions.

### Installation & Setup

1.  **Prerequisites**:

      * Ensure you have a Java Development Kit (JDK) 8 or higher installed on your system. You can download it from [Oracle's website](https://www.oracle.com/java/technologies/downloads/).

2.  **Clone the Repository (if applicable)**:

    ```bash
    git clone https://github.com/technerdsam/futuristic-clock-app.git
    cd futuristic-clock-app/HorlogeNumerique/FuturisticClockApp/
    ```

    (Note: If you only have the `.java` and `.class` files, simply navigate to their directory.)

3.  **Compile (if starting from `.java` source files)**:
    If you have the `FuturisticClockApp.java` file and other `.java` sources, compile them using `javac`:

    ```bash
    javac FuturisticClockApp.java FuturisticClockApp$Alarm.java FuturisticClockApp$BackgroundPanel.java FuturisticClockApp$Constants.java FuturisticClockApp$1.java FuturisticClockApp$2.java FuturisticClockApp$3.java FuturisticClockApp$4.java FuturisticClockApp$5.java FuturisticClockApp$6.java FuturisticClockApp$7.java FuturisticClockApp$8.java FuturisticClockApp$9.java FuturisticClockApp$10.java FuturisticClockApp$11.java FuturisticClockApp$12.java FuturisticClockApp$13.java
    ```

    (Ensure all inner and outer classes are compiled. For simplicity, compiling `FuturisticClockApp.java` usually compiles all nested classes automatically if they are correctly structured).

4.  **Run the Application**:
    Navigate to the directory containing the compiled `.class` files (e.g., `HorlogeNumerique/FuturisticClockApp/`) and run:

    ```bash
    java FuturisticClockApp
    ```

### Usage

  * **Main Clock**: The large digital display shows the current time and date. Its colors and background can be customized in the `Settings` tab.
  * **Dragging the Window**: Click and drag anywhere on the background panel to move the undecorated window.
  * **Close Button**: Click the '×' button in the top-right corner to exit the application cleanly.
  * **Collapsible Tab Section** (Features Panel):
      * Click the header (`Fonctionnalités de l'Horloge` / `Clock Features`) to expand or collapse the panel.
      * **Alarms**: Add new alarms by specifying time, message, repetition (daily or specific date), and alarm sound. Select an alarm from the list and click "Supprimer" to remove it. Snooze activated directly from the alarm popup.
      * **Stopwatch**: Use "Démarrer", "Arrêter", and "Réinitialiser" buttons to control the stopwatch.
      * **Timer**: Input hours, minutes, and seconds, then "Démarrer" the countdown. "Arrêter" pauses, and "Réinitialiser" clears.
      * **World Clocks**: Add time zones by their ID (e.g., "Europe/Paris") and view current times globally. Select and "Supprimer" to remove.
      * **Settings**:
          * Change the background image by pasting a URL.
          * Use the "Choisir" buttons to open color choosers for clock text and background.
          * "Parcourir" allows you to select a local WAV file for alarm sounds.
          * Update the custom dedication message and save it.

### Contributing

Contributions are welcome\! If you have suggestions for improvements or new features, feel free to:

1.  Fork the repository.
2.  Create a new branch (`git checkout -b feature/AmazingFeature`).
3.  Commit your changes (`git commit -m 'Add some AmazingFeature'`).
4.  Push to the branch (`git push origin feature/AmazingFeature`).
5.  Open a Pull Request.


### Author

**RTN alias Samyn-Antoy ABASSE**

  * Designed with passion.
 

-----

## Documentation Française

### Titre du Projet

**Application Horloge Futuriste : Édition Halo** 🕰️✨

### Slogan

Une application d'horloge numérique professionnelle et élégante avec des fonctionnalités avancées et un design futuriste, inspirée de Halo. Développée en Java 8+.

### Description

L'**Application Horloge Futuriste** est une application de bureau sophistiquée conçue pour offrir plus que la simple indication de l'heure. Elle dispose d'une image de fond personnalisable, d'un affichage d'horloge numérique dynamique et d'une suite d'outils intégrés comprenant des alarmes, un chronomètre, un minuteur et une horloge mondiale. Son interface intuitive et escamotable permet une navigation fluide entre les fonctionnalités, tandis que les paramètres persistants garantissent une expérience personnalisée à chaque lancement. Conçue avec passion, cette application apporte une touche d'élégance science-fiction à votre bureau.

### Fonctionnalités

  * **Horloge Numérique Dynamique** ⏰ : Affiche l'heure actuelle (HH:mm:ss) et la date (JJ MOIS AAAA) dans un format élégant et personnalisable.
  * **Esthétique Personnalisable** 🎨 :
      * **Image de Fond** : Définissez n'importe quelle image à partir d'une URL ou d'un fichier local comme arrière-plan de l'application.
      * **Couleurs de l'Horloge** : Personnalisez la couleur du texte et la couleur de fond de l'affichage principal de l'horloge.
  * **Interface Utilisateur Intuitive** 🖥️ : Une fenêtre non décorée et déplaçable avec une section d'onglets escamotable pour un accès facile aux fonctionnalités.
  * **Système d'Alarmes** 🔔 :
      * Définissez plusieurs alarmes avec des heures (HH:mm) et des messages personnalisés.
      * Configurez les alarmes pour une répétition quotidienne ou des dates spécifiques (AAAA-MM-JJ).
      * Choisissez des fichiers sonores WAV personnalisés pour vos alarmes, ou utilisez le son par défaut.
      * Fonctionnalité de répétition (snooze) de 5 minutes pour un réveil en douceur.
      * Toutes vos configurations d'alarmes sont persistantes.
  * **Chronomètre** ⏱️ : Fonctionnalité de chronomètre précise avec options de démarrage, d'arrêt et de réinitialisation, affichant les millisecondes.
  * **Minuteur (Compte à Rebours)** ⏳ : Définissez un minuteur pour les heures, les minutes et les secondes, avec un son d'alarme à la fin.
  * **Fuseaux Horaires Mondiaux** 🌍 :
      * Ajoutez et gérez plusieurs fuseaux horaires pour voir l'heure actuelle dans le monde entier.
      * Toutes les fuseaux horaires configurés sont persistantes.
  * **Message de Dédicace Personnalisé** ❤️ : Un message de dédicace animé et personnalisé en bas de la fenêtre.
  * **Persistance** : Toutes vos préférences, y compris l'URL de l'image de fond, les couleurs de l'horloge, l'état de la section d'onglets, les alarmes, le chemin du son de l'alarme, le texte de dédicace personnalisé et les fuseaux horaires mondiaux, sont automatiquement sauvegardées et chargées.

### Technologies Utilisées

  * **Java 8+** : Le langage de développement principal.
  * **Swing** : Pour la construction de l'interface utilisateur graphique.
  * **AWT** : Boîte à outils fondamentale pour les graphiques et l'interface utilisateur.
  * **`java.text.SimpleDateFormat`** : Pour un formatage flexible de la date et de l'heure.
  * **API `java.time`** : Gestion moderne de la date et de l'heure (par exemple, `ZoneId`, `ZonedDateTime`, `LocalDate`, `Instant`).
  * **`java.util.Timer`** : Pour la planification de tâches répétitives comme les mises à jour de l'horloge et les vérifications d'alarmes.
  * **`javax.sound.sampled`** : Pour une lecture audio robuste (fichiers WAV) pour les alarmes et les minuteurs.
  * **`java.util.prefs.Preferences`** : Pour la persistance des paramètres et des données utilisateur entre les sessions de l'application.

### Installation & Configuration

1.  **Prérequis** :

      * Assurez-vous d'avoir un Kit de Développement Java (JDK) 8 ou supérieur installé sur votre système. Vous pouvez le télécharger depuis le [site web d'Oracle](https://www.oracle.com/java/technologies/downloads/).

2.  **Cloner le Dépôt (si applicable)** :

    ```bash
    git clone https://github.com/technerdsam/futuristic-clock-app.git
    cd futuristic-clock-app/HorlogeNumerique/FuturisticClockApp/
    ```

    (Remarque : Si vous n'avez que les fichiers `.java` et `.class`, naviguez simplement vers leur répertoire.)

3.  **Compiler (si vous partez des fichiers source `.java`)** :
    Si vous avez le fichier `FuturisticClockApp.java` et d'autres sources `.java`, compilez-les en utilisant `javac` :

    ```bash
    javac FuturisticClockApp.java FuturisticClockApp$Alarm.java FuturisticClockApp$BackgroundPanel.java FuturisticClockApp$Constants.java FuturisticClockApp$1.java FuturisticClockApp$2.java FuturisticClockApp$3.java FuturisticClockApp$4.java FuturisticClockApp$5.java FuturisticClockApp$6.java FuturisticClockApp$7.java FuturisticClockApp$8.java FuturisticClockApp$9.java FuturisticClockApp$10.java FuturisticClockApp$11.java FuturisticClockApp$12.java FuturisticClockApp$13.java
    ```

    (Assurez-vous que toutes les classes internes et externes sont compilées. Pour simplifier, la compilation de `FuturisticClockApp.java` compile généralement toutes les classes imbriquées automatiquement si elles sont correctement structurées).

4.  **Exécuter l'Application** :
    Naviguez vers le répertoire contenant les fichiers `.class` compilés (par exemple, `HorlogeNumerique/FuturisticClockApp/`) et exécutez :

    ```bash
    java FuturisticClockApp
    ```

### Utilisation

  * **Horloge Principale** : Le grand affichage numérique montre l'heure et la date actuelles. Ses couleurs et son arrière-plan peuvent être personnalisés dans l'onglet `Paramètres`.
  * **Déplacement de la Fenêtre** : Cliquez et faites glisser n'importe où sur le panneau d'arrière-plan pour déplacer la fenêtre non décorée.
  * **Bouton Fermer** : Cliquez sur le bouton '×' dans le coin supérieur droit pour quitter l'application proprement.
  * **Section d'Onglets Escamotable** (Panneau des Fonctionnalités) :
      * Cliquez sur l'en-tête (`Fonctionnalités de l'Horloge` / `Clock Features`) pour développer ou réduire le panneau.
      * **Alarmes** : Ajoutez de nouvelles alarmes en spécifiant l'heure, le message, la répétition (quotidienne ou date spécifique) et le son d'alarme. Sélectionnez une alarme dans la liste et cliquez sur "Supprimer" pour la retirer. La fonction "Snooze" est activée directement depuis la fenêtre contextuelle de l'alarme.
      * **Chronomètre** : Utilisez les boutons "Démarrer", "Arrêter" et "Réinitialiser" pour contrôler le chronomètre.
      * **Minuteur** : Saisissez les heures, minutes et secondes, puis "Démarrer" le compte à rebours. "Arrêter" met en pause, et "Réinitialiser" efface.
      * **Fuseaux Horaires Mondiaux** : Ajoutez des fuseaux horaires par leur ID (par exemple, "Europe/Paris") et affichez les heures actuelles dans le monde entier. Sélectionnez et "Supprimer" pour retirer.
      * **Paramètres** :
          * Changez l'image de fond en collant une URL.
          * Utilisez les boutons "Choisir" pour ouvrir les sélecteurs de couleur pour le texte et l'arrière-plan de l'horloge.
          * "Parcourir" vous permet de sélectionner un fichier WAV local pour les sons d'alarme.
          * Mettez à jour le message de dédicace personnalisé et enregistrez-le.

### Contributions

Les contributions sont les bienvenues \! Si vous avez des suggestions d'améliorations ou de nouvelles fonctionnalités, n'hésitez pas à :

1.  Dupliquer le dépôt (`fork`).
2.  Créer une nouvelle branche (`git checkout -b feature/NouvelleFonctionnaliteGeniale`).
3.  Valider vos modifications (`git commit -m 'Ajout de NouvelleFonctionnaliteGeniale'`).
4.  Pousser vers la branche (`git push origin feature/NouvelleFonctionnaliteGeniale`).
5.  Ouvrir une demande de tirage (`Pull Request`).


### Auteur

**RTN alias Samyn-Antoy ABASSE**

  * Conçu avec passion.

# 🚀 SAMYN-ANTOY
### Architecte Growth & Tech Augmentée par l'IA

> *L'alliance du Code, de la Stratégie et du Design pour propulser votre business.*

---

## ⚡ EXPERTISE À HAUT IMPACT

</div>

### 💻 Dév Nouvelle Génération
* **Développement assisté par IA (Prompt-to-Code)**
* SysAdmin & DevOps
* Infrastructures Robustes & Scalables

### 📈 Croissance & Ads
* **Stratégies d'Acquisition Agressives**
* Marketing Digital 360°
* Growth Hacking

### 🎨 Visuels & Créa
* **Photographie IA & GenAI**
* Publicités à Haute Conversion (Creative Strategy)

---

<div align="center">

## 👇 PASSEZ À LA VITESSE SUPÉRIEURE

### 💼 [Collaborons ensemble : Le Blog Tech Pro de Samyn-Antoy ABASSE : https://monblog-sa-abasse.blogspot.com/

---

## 💎 MON ARSENAL & LIFESTYLE 2025
*Les outils pour performer et durer.*

| Catégorie | Gear / Outil | Pourquoi ? |
| :--- | :--- | :--- |
| **📱 Tech Mobile** | iPhone 16 - Puissance Pure : https://amzn.to/4ivKTuW
| **🧱 Focus Créatif** |  LEGO Star Wars - Deep Work : https://amzn.to/44FrP7N
| **⚡ Santé & Énergie** | Air Fryer - Cuisine Intelligente : https://amzn.to/48AECcZ

---

## 🧢 LA BOUTIQUE OFFICIELLE
*Rejoignez le mouvement. Portez la vision.*

### 🛒 [Accès Exclusif : Mon Store Créateur Fourthwall]: https://samynantoyabasse-shop.fourthwall.com

</div>

