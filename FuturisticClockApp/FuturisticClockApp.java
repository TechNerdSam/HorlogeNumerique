import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Instant;
import java.util.Date;
import java.util.TimerTask;
import java.net.URL;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.Timer;

import java.util.prefs.Preferences;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.List;
import javax.swing.filechooser.FileFilter;
import java.net.URI; // Import URI
import java.net.URISyntaxException; // Import URISyntaxException

/**
 * <p>
 * This class represents a professional and elegant futuristic digital clock application
 * with a centered background image (inspired by Halo), compatible with Java 8+.
 * It integrates alarm, stopwatch, timer, theme customization, and multiple time zone display functionalities.
 * </p>
 *
 * <p>
 * Cette classe représente une application d'horloge numérique professionnelle et élégante
 * avec une image de fond centrée (inspirée de Halo), compatible avec Java 8+.
 * Elle intègre des fonctionnalités d'alarme, de chronomètre, de minuteur,
 * de personnalisation du thème et d'affichage de multiples fuseaux horaires.
 * </p>
 *
 * @author RTN alias Samyn-Antoy ABASSE
 * @version 1.0
 */
public class FuturisticClockApp extends JFrame {

    /** The point where the mouse was initially clicked for window dragging. */
    private Point initialClick;
    /** The button to close the application. */
    private JButton closeButton;

    // Alarm Panel Components
    /** List model for managing alarm entries. */
    private JList<Alarm> alarmList;
    /** DefaultListModel for storing and managing Alarm objects. */
    private DefaultListModel<Alarm> alarmListModel;
    /** Text field for entering alarm time (HH:mm). */
    private JTextField alarmTimeField;
    /** Text field for entering alarm message. */
    private JTextField alarmMessageField;
    /** Checkbox to set alarm for daily repetition. */
    private JCheckBox alarmRepeatDaily;
    /** Text field for entering a specific alarm date (YYYY-MM-DD). */
    private JTextField alarmDateField;
    /** Checkbox to set alarm for a specific date. */
    private JCheckBox alarmSpecificDate;
    /** Button to add a new alarm. */
    private JButton addAlarmButton;
    /** Button to remove selected alarm. */
    private JButton removeAlarmButton;

    // Main Clock and UI Layout Components
    /** Label displaying the current time and date. */
    private JLabel timeLabel;
    /** Custom JPanel for displaying the background image. */
    private BackgroundPanel backgroundPanel;
    /** Tabbed pane for organizing different features (Alarms, Stopwatch, Timer, Settings, World Clocks). */
    private JTabbedPane tabbedPane;
    /** Label displaying the dedication text. */
    private JLabel dedicationLabel;
    /** Collapsible panel containing the feature tabs. */
    private JPanel tabSectionCollapsiblePanel;
    /** Header label for the collapsible tab section, also acts as a toggle. */
    private JLabel tabSectionHeader;

    /** Panel for managing alarms. */
    private JPanel alarmsPanel;

    // Stopwatch Panel Components
    /** Panel for the stopwatch feature. */
    private JPanel stopwatchPanel;
    /** Label to display the stopwatch time. */
    private JLabel stopwatchDisplay;
    /** Button to start the stopwatch. */
    private JButton startStopwatchButton;
    /** Button to stop the stopwatch. */
    private JButton stopStopwatchButton;
    /** Button to reset the stopwatch. */
    private JButton resetStopwatchButton;

    // Timer Panel Components
    /** Panel for the countdown timer feature. */
    private JPanel timerPanel;
    /** Label to display the countdown timer. */
    private JLabel timerDisplay;
    /** Text field for inputting hours for the timer. */
    private JTextField timerInputHours;
    /** Text field for inputting minutes for the timer. */
    private JTextField timerInputMinutes;
    /** Text field for inputting seconds for the timer. */
    private JTextField timerInputSeconds;
    /** Button to start the countdown timer. */
    private JButton startTimerButton;
    /** Button to stop the countdown timer. */
    private JButton stopTimerButton;
    /** Button to reset the countdown timer. */
    private JButton resetTimerButton;

    // Settings Panel Components
    /** Panel for application settings. */
    private JPanel settingsPanel;
    /** Text field for entering background image URL. */
    private JTextField imageUrlField;
    /** Button to change the background image. */
    private JButton changeImageButton;
    /** Button to choose the clock text color. */
    private JButton chooseTextColorButton;
    /** Button to choose the clock background color. */
    private JButton chooseClockBgColorButton;
    /** Text field displaying the path to the local alarm sound file. */
    private JTextField localSoundPathField;
    /** Button to choose a local alarm sound file. */
    private JButton chooseSoundButton;
    /** Text field for editing the custom dedication message. */
    private JTextField dedicationTextField;
    /** Button to save the custom dedication message. */
    private JButton saveDedicationButton;
    /** Stores the custom dedication text set by the user. */
    private String customDedicationText;

    // World Clocks Panel Components
    /** Panel for displaying world clocks. */
    private JPanel worldClocksPanel;
    /** List model for managing world time zone entries. */
    private DefaultListModel<String> worldClockListModel;
    /** List displaying various world time zones. */
    private JList<String> worldClockList;
    /** Text field for entering a new time zone ID. */
    private JTextField timeZoneIdField;
    /** Button to add a new world time zone. */
    private JButton addWorldClockButton;
    /** Button to remove a selected world time zone. */
    private JButton removeWorldClockButton;
    /** List of world time zone IDs. */
    private List<String> worldTimeZones;

    // Date and Time Formatters
    /** Formatter for the main clock display time. */
    private SimpleDateFormat mainTimeFormatter;
    /** Formatter for the main clock display date. */
    private SimpleDateFormat dateFormatter;
    /** Formatter for checking alarm times. */
    private SimpleDateFormat alarmTimeCheckerFormatter;
    /** Formatter for alarm dates. */
    private DateTimeFormatter alarmDateFormatter;
    /** Formatter for displaying world clock times. */
    private DateTimeFormatter worldClockFormatter;

    // Timers for animations and updates
    /** Timer for the dedication label animation. */
    private Timer dedicationAnimationTimer;
    /** Timer for the tab section collapse/expand animation. */
    private Timer tabSectionCollapseExpandTimer;

    /** Timer for updating the main clock display. */
    private java.util.Timer mainClockUpdateTimer;
    /** Timer for checking active alarms. */
    private java.util.Timer alarmCheckTimer;
    /** Timer for the stopwatch functionality. */
    private java.util.Timer stopwatchTimer;
    /** Timer for the countdown functionality. */
    private java.util.Timer countdownTimer;

    // Animation and State Variables
    /** Target X-coordinate for the dedication label animation. */
    private int dedicationTargetX;
    /** Current X-coordinate of the dedication label during animation. */
    private int dedicationCurrentX;
    /** Flag indicating if the tab section is currently expanded. */
    private boolean isTabSectionExpanded;
    /** The expanded height of the tab section. */
    private int tabSectionExpandedHeight;
    /** Path to the currently selected alarm sound. */
    private String currentAlarmSoundPath;
    /** Reusable Clip object for playing alarm sounds. */
    private Clip reusableAlarmSoundClip;
    /** Start time of the stopwatch in milliseconds. */
    private long stopwatchStartTime;
    /** Elapsed time of the stopwatch in milliseconds. */
    private long stopwatchElapsedTime;
    /** Flag indicating if the stopwatch is running. */
    private boolean stopwatchRunning;
    /** Remaining time for the countdown timer in milliseconds. */
    private long countdownRemainingTime;
    /** Flag indicating if the countdown timer is running. */
    private boolean countdownRunning;
    /** Current color of the clock text. */
    private Color currentTextColor;
    /** Current background color of the clock display. */
    private Color currentClockBgColor;

    /** User preferences storage. */
    private Preferences prefs;

    /**
     * <p>
     * Inner class to hold all application-wide constants.
     * This improves code readability and maintainability by centralizing configuration values.
     * </p>
     *
     * <p>
     * Classe interne pour contenir toutes les constantes de l'application.
     * Cela améliore la lisibilité et la maintenabilité du code en centralisant les valeurs de configuration.
     * </p>
     */
    private static class Constants {
        static final String APP_TITLE = "Horloge d'Entreprise Futuriste - Édition Halo";
        static final String DEDICATION_TEXT_DEFAULT = "Ce logiciel a été conçu avec passion par RTN alias Samyn-Antoy ABASSE";
        static final String CLOCK_FEATURES_TITLE = "Fonctionnalités de l'Horloge";

        static final String DEFAULT_BACKGROUND_IMAGE_URL = "https://image.noelshack.com/fichiers/2025/22/5/1748608028-wallpaperflare-com-wallpaper.jpg";
        static final String DEFAULT_ALARM_SOUND_URL = "http://soundbible.com/grab.php?id=2028&type=wav";

        // Preference Keys
        static final String PREF_IMAGE_URL = "imageUrl";
        static final String PREF_TEXT_COLOR = "textColor";
        static final String PREF_BG_COLOR = "bgColor";
        static final String PREF_TAB_SECTION_EXPANDED_HEIGHT = "tabSectionExpandedHeight";
        static final String PREF_IS_TAB_SECTION_EXPANDED = "isTabSectionExpanded";
        static final String PREF_ALARMS = "alarms";
        static final String PREF_DEFAULT_RINGTONE_PATH = "defaultRingtonePath";
        static final String PREF_LAST_SOUND_DIRECTORY = "lastSoundDirectory";
        static final String PREF_CUSTOM_DEDICATION_TEXT = "customDedicationText";
        static final String PREF_WORLD_TIME_ZONES = "worldTimeZones";

        // UI Dimensions and Sizes
        static final Dimension MINIMUM_WINDOW_SIZE = new Dimension(900, 700);
        static final Dimension INITIAL_WINDOW_SIZE = new Dimension(1200, 850);
        static final int INITIAL_FONT_SIZE = 110;
        static final int TAB_SECTION_COLLAPSED_HEIGHT = 50;
        static final int TAB_SECTION_CONTENT_PADDING = 30;
        static final int DEFAULT_TAB_SECTION_EXPANDED_HEIGHT = 520;
        static final int TAB_SECTION_ANIMATION_STEP = 28;
        static final int DEDICATION_ANIMATION_DELAY = 20;
        static final int DEDICATION_ANIMATION_STEP = 5;
        static final int GLOBAL_MARGIN = 30;
        static final int CLOCK_FRAME_HORIZONTAL_PADDING = 20;
        static final int CLOCK_FRAME_VERTICAL_PADDING = 15;
        static final double CLOCK_HEIGHT_RATIO = 0.30;
        static final int MIN_CLOCK_WIDTH = 300;
        static final int MIN_CLOCK_HEIGHT = 120;
        static final int MIN_TIME_FONT_SIZE = 40;
        static final double TIME_FONT_SIZE_RATIO = 0.45;
        static final int DEDICATION_LABEL_HEIGHT = 32;
        static final int DEDICATION_MARGIN = 35;
        static final int TAB_SECTION_WIDTH = 440;

        // Date and Time Formats
        static final String MAIN_TIME_FORMAT = "HH:mm:ss";
        static final String DATE_FORMAT = "dd MMMMYYYY";
        static final String ALARM_TIME_FORMAT_REGEX = "\\d{2}:\\d{2}";
        static final String ALARM_DATE_FORMAT = "yyyy-MM-dd";
        static final String TIME_ZONE_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss (z)";

        // Colors
        static final Color DEFAULT_TEXT_COLOR = Color.WHITE;
        static final Color DEFAULT_CLOCK_BG_COLOR = new Color(10, 10, 10, 230);
        static final Color DEDICATION_TEXT_COLOR = new Color(170, 190, 220, 220);

        static final Color LIFESTYLE_PANEL_BG = new Color(248, 244, 238, 230);
        static final Color LIFESTYLE_CONTENT_BG = new Color(252, 250, 245, 220);
        static final Color LIFESTYLE_TAB_SELECTED_BG = new Color(230, 126, 34);
        static final Color LIFESTYLE_TAB_UNSELECTED_BG = new Color(225, 215, 200, 200);
        static final Color LIFESTYLE_TAB_TEXT_SELECTED = Color.WHITE;
        static final Color LIFESTYLE_TAB_TEXT_UNSELECTED = new Color(85, 70, 55);
        static final Color LIFESTYLE_TEXT_COLOR = new Color(70, 60, 50);
        static final Color LIFESTYLE_BORDER_COLOR = new Color(205, 195, 180);
        static final Color LIFESTYLE_INPUT_BG = new Color(255, 255, 255, 210);
        static final Color LIFESTYLE_INPUT_TEXT = new Color(60, 60, 60);
        static final Color LIFESTYLE_ALARM_LIST_BG = new Color(242, 240, 235, 190);

        static final Color TABBED_PANE_BG_COLOR = new Color(0, 0, 0, 0);
        static final String DEFAULT_ALARM_TIME = "08:00";
        static final String DEFAULT_ALARM_MESSAGE = "Réveillez-vous !";
        static final Color STOPWATCH_DISPLAY_COLOR = new Color(0, 200, 200);
        static final Color TIMER_DISPLAY_COLOR = new Color(255, 130, 0);

        static final Color BUTTON_ACCENT_GREEN = new Color(40, 170, 70);
        static final Color BUTTON_ACCENT_RED = new Color(190, 50, 50);
        static final Color BUTTON_ACCENT_BLUE = new Color(40, 110, 180);
        static final Color BUTTON_ACCENT_YELLOW = new Color(200, 170, 50);
        static final Color BUTTON_TEXT_COLOR = Color.WHITE;

        // Fonts
        static final Font CLOCK_FONT_BASE = new Font("Monospaced", Font.BOLD, INITIAL_FONT_SIZE);
        static final Font DEDICATION_FONT = new Font("SansSerif", Font.ITALIC, 15);
        static final Font TAB_SECTION_HEADER_FONT = new Font("SansSerif", Font.BOLD, 19);
        static final Font ALARM_LIST_FONT = new Font("SansSerif", Font.PLAIN, 15);
        static final Font STOPWATCH_FONT = new Font("Monospaced", Font.BOLD, 52);
        static final Font TIMER_DISPLAY_FONT = new Font("Monospaced", Font.BOLD, 52);
        static final Font TIMER_INPUT_FONT = new Font("Monospaced", Font.BOLD, 28);
        static final Font BUTTON_FONT = new Font("SansSerif", Font.BOLD, 15);
        static final Font TITLED_BORDER_FONT = new Font("SansSerif", Font.BOLD, 15);
        static final Font TAB_TITLE_FONT = new Font("SansSerif", Font.BOLD, 16);
        static final Font LABEL_FONT = new Font("SansSerif", Font.PLAIN, 15);

        // UI Text Labels and Button Texts
        static final String ALARMS_TAB_TITLE = "Alarmes";
        static final String STOPWATCH_TAB_TITLE = "Chronomètre";
        static final String TIMER_TAB_TITLE = "Minuteur";
        static final String SETTINGS_TAB_TITLE = "Paramètres";
        static final String WORLD_CLOCKS_TAB_TITLE = "Fuseaux Horaires";
        static final String LABEL_TIME_HHMM = "Heure (HH:mm):";
        static final String LABEL_MESSAGE = "Message:";
        static final String CHECKBOX_REPEAT_DAILY = "Répéter tous les jours";
        static final String BUTTON_ADD_ALARM = "Ajouter";
        static final String BUTTON_REMOVE_ALARM = "Supprimer";
        static final String BUTTON_START = "Démarrer";
        static final String BUTTON_STOP = "Arrêter";
        static final String BUTTON_RESET = "Réinitialiser";
        static final String SECTION_TITLE_BACKGROUND_IMAGE = "Image de Fond (URL)";
        static final String SECTION_TITLE_TEXT_COLOR = "Couleur Texte Horloge";
        static final String SECTION_TITLE_CLOCK_BG_COLOR = "Couleur Fond Horloge";
        static final String SECTION_TITLE_ALARM_SOUND = "Son Alarme (WAV)";
        static final String BUTTON_CHANGE = "Modifier";
        static final String BUTTON_CHOOSE_COLOR = "Choisir";
        static final String BUTTON_CHOOSE_FILE = "Parcourir";
        static final String DIALOG_TITLE_ERROR = "Erreur";
        static final String DIALOG_TITLE_INFO = "Information";
        static final String DIALOG_TITLE_WARNING = "Avertissement";
        static final String ERROR_INVALID_TIME = "Format d'heure invalide. Utilisez HH:mm (ex: 08:00).";
        static final String ERROR_EMPTY_MESSAGE = "Le message de l'alarme ne peut pas être vide.";
        static final String INFO_ALARM_ADDED = "Alarme ajoutée : ";
        static final String INFO_ALARM_REMOVED = "Alarme supprimée.";
        static final String WARNING_SELECT_ALARM_TO_REMOVE = "Veuillez sélectionner une alarme à supprimer.";
        static final String INFO_TIMER_FINISHED = "Minuteur terminé !";
        static final String ERROR_IMAGE_URL_EMPTY = "L'URL de l'image ne peut pas être vide.";
        static final String ERROR_LOAD_IMAGE = "Échec du chargement de l'image depuis l'URL : ";
        static final String ERROR_LOAD_IMAGE_CHECK_URL = "\nVeuillez vérifier l'URL et votre connexion internet. " +
                "Assurez-vous que c'est un lien direct vers un fichier image (ex: .jpg, .png).";
        static final String INFO_BACKGROUND_UPDATED = "Image de fond mise à jour.";
        static final String INFO_ALARM_SOUND_UPDATED = "Son de l'alarme mis à jour.";
        static final String ERROR_UNSUPPORTED_AUDIO = "Format audio non supporté. Veuillez utiliser WAV : ";
        static final String ERROR_LOADING_SOUND = "Erreur lors du chargement du son. Vérifiez le chemin/URL et la connexion : ";
        static final String ERROR_AUDIO_LINE_UNAVAILABLE = "Ligne audio indisponible. Le son ne peut pas être joué.";
        static final String ERROR_UNEXPECTED_SOUND_LOAD = "Une erreur inattendue s'est produite lors du chargement du son.";
        static final String WARNING_LOCAL_SOUND_NOT_FOUND = "Fichier son local non trouvé : ";
        static final String WARNING_LOADING_DEFAULT_SOUND = "\nTentative de chargement du son par défaut.";
        static final String ERROR_POSITIVE_DURATION = "Veuillez entrer une durée positive pour le minuteur.";
        static final String ERROR_VALID_NUMBERS_DURATION = "Veuillez entrer des nombres valides pour la durée du minuteur.";
        static final String FILE_CHOOSER_WAV_DESC = "Fichiers Audio WAV (*.wav)";
        static final String LABEL_ALARM_DATE = "Date (AAAA-MM-JJ):";
        static final String CHECKBOX_SPECIFIC_DATE = "Alarme à date spécifique";
        static final String ERROR_INVALID_DATE_FORMAT = "Format de date invalide. Utilisez AAAA-MM-JJ (ex: 2025-06-23).";
        static final String ALARM_SNOOZE_BUTTON = "Snooze (5 min)";
        static final String LABEL_TIMEZONE_ID = "ID du fuseau horaire (ex: Europe/Paris):";
        static final String BUTTON_ADD_TIMEZONE = "Ajouter Fuseau";
        static final String BUTTON_REMOVE_TIMEZONE = "Supprimer Fuseau";
        static final String ERROR_INVALID_TIMEZONE = "ID de fuseau horaire invalide. Veuillez vérifier la saisie.";
        static final String SECTION_TITLE_DEDICATION_MESSAGE = "Message de Dédicace";
        static final String BUTTON_SAVE_DEDICATION = "Enregistrer Message";
    }

    /**
     * <p>
     * Constructs the FuturisticClockApp application.
     * Initializes UI components, loads user preferences, sets up listeners,
     * and starts core application timers.
     * </p>
     *
     * <p>
     * Construit l'application FuturisticClockApp.
     * Initialise les composants de l'interface utilisateur, charge les préférences de l'utilisateur,
     * configure les écouteurs et démarre les minuteurs principaux de l'application.
     * </p>
     */
    public FuturisticClockApp() {
        super(Constants.APP_TITLE);

        initializePreferences();
        loadCorePreferences();

        setupFrame();
        initializeFormatters();

        JLayeredPane layeredPane = createLayeredPane();
        initializeBackgroundPanel(layeredPane);
        initializeCloseButton(layeredPane);
        initializeTimeLabel(layeredPane);
        initializeFeatureTabs();
        initializeCollapsibleTabSection(layeredPane);
        initializeDedicationLabel(layeredPane);

        loadAllPersistedData();
        loadAlarmSound();

        registerListeners();
        startCoreTimers();

        applyInitialComponentSizes();

        setVisible(true);

        startDedicationAnimation();
        updateTime();
    }

    // --- Initialization Methods ---

    /**
     * <p>
     * Initializes the user preferences object.
     * </p>
     *
     * <p>
     * Initialise l'objet de préférences utilisateur.
     * </p>
     */
    private void initializePreferences() {
        prefs = Preferences.userNodeForPackage(FuturisticClockApp.class);
    }

    /**
     * <p>
     * Loads core application preferences from the user's preference store.
     * This includes tab section state, colors, alarm sound path, custom dedication text, and world time zones.
     * </p>
     *
     * <p>
     * Charge les préférences principales de l'application depuis le magasin de préférences de l'utilisateur.
     * Cela inclut l'état de la section des onglets, les couleurs, le chemin du son de l'alarme,
     * le texte de dédicace personnalisé et les fuseaux horaires mondiaux.
     * </p>
     */
    private void loadCorePreferences() {
        tabSectionExpandedHeight = prefs.getInt(Constants.PREF_TAB_SECTION_EXPANDED_HEIGHT, Constants.DEFAULT_TAB_SECTION_EXPANDED_HEIGHT);
        isTabSectionExpanded = prefs.getBoolean(Constants.PREF_IS_TAB_SECTION_EXPANDED, true);
        currentTextColor = new Color(prefs.getInt(Constants.PREF_TEXT_COLOR, Constants.DEFAULT_TEXT_COLOR.getRGB()));
        currentClockBgColor = new Color(prefs.getInt(Constants.PREF_BG_COLOR, Constants.DEFAULT_CLOCK_BG_COLOR.getRGB()), true);
        currentAlarmSoundPath = prefs.get(Constants.PREF_DEFAULT_RINGTONE_PATH, Constants.DEFAULT_ALARM_SOUND_URL);
        customDedicationText = prefs.get(Constants.PREF_CUSTOM_DEDICATION_TEXT, Constants.DEDICATION_TEXT_DEFAULT);

        worldTimeZones = new ArrayList<>();
        String savedTimeZones = prefs.get(Constants.PREF_WORLD_TIME_ZONES, "");
        if (!savedTimeZones.isEmpty()) {
            StringTokenizer tokenizer = new StringTokenizer(savedTimeZones, ",");
            while (tokenizer.hasMoreTokens()) {
                worldTimeZones.add(tokenizer.nextToken());
            }
        }
    }

    /**
     * <p>
     * Sets up the main JFrame properties, such as undecorated, background,
     * default close operation, minimum size, initial size, and location.
     * </p>
     *
     * <p>
     * Configure les propriétés de la fenêtre principale (JFrame), telles que
     * non décorée, l'arrière-plan, l'opération de fermeture par défaut,
     * la taille minimale, la taille initiale et la position.
     * </p>
     */
    private void setupFrame() {
        setUndecorated(true);
        setBackground(new Color(0, 0, 0, 0));
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setMinimumSize(Constants.MINIMUM_WINDOW_SIZE);
        setSize(Constants.INITIAL_WINDOW_SIZE);
        setLocationRelativeTo(null);
    }

    /**
     * <p>
     * Initializes the date and time formatters used throughout the application.
     * </p>
     *
     * <p>
     * Initialise les formateurs de date et d'heure utilisés dans toute l'application.
     * </p>
     */
    private void initializeFormatters() {
        mainTimeFormatter = new SimpleDateFormat(Constants.MAIN_TIME_FORMAT);
        dateFormatter = new SimpleDateFormat(Constants.DATE_FORMAT, java.util.Locale.FRENCH);
        alarmTimeCheckerFormatter = new SimpleDateFormat("HH:mm");
        alarmTimeCheckerFormatter.setLenient(false);
        alarmDateFormatter = DateTimeFormatter.ofPattern(Constants.ALARM_DATE_FORMAT);
        worldClockFormatter = DateTimeFormatter.ofPattern(Constants.TIME_ZONE_DATE_FORMAT);
    }

    /**
     * <p>
     * Creates and configures the JLayeredPane for handling component layering.
     * </p>
     *
     * <p>
     * Crée et configure le JLayeredPane pour la gestion de la superposition des composants.
     * </p>
     *
     * @return The configured JLayeredPane.
     */
    private JLayeredPane createLayeredPane() {
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setOpaque(false);
        getContentPane().add(layeredPane, BorderLayout.CENTER);
        return layeredPane;
    }

    /**
     * <p>
     * Initializes the background panel with the persisted image URL or a default one.
     * Attempts to load the image and falls back to default if loading fails.
     * </p>
     *
     * <p>
     * Initialise le panneau d'arrière-plan avec l'URL de l'image persistante ou une image par défaut.
     * Tente de charger l'image et revient à la valeur par défaut si le chargement échoue.
     * </p>
     *
     * @param layeredPane The JLayeredPane to which the background panel will be added.
     */
    private void initializeBackgroundPanel(JLayeredPane layeredPane) {
        String persistedImageUrl = prefs.get(Constants.PREF_IMAGE_URL, Constants.DEFAULT_BACKGROUND_IMAGE_URL);
        Image image = loadImage(persistedImageUrl);
        if (image == null && !persistedImageUrl.equals(Constants.DEFAULT_BACKGROUND_IMAGE_URL)) {
            System.err.println("Failed to load persisted image: " + persistedImageUrl + ". Attempting default.");
            image = loadImage(Constants.DEFAULT_BACKGROUND_IMAGE_URL);
            if (image != null) {
                 prefs.put(Constants.PREF_IMAGE_URL, Constants.DEFAULT_BACKGROUND_IMAGE_URL);
                 flushPreferences();
                 persistedImageUrl = Constants.DEFAULT_BACKGROUND_IMAGE_URL;
            }
        }
        backgroundPanel = new BackgroundPanel(image, persistedImageUrl);
        layeredPane.add(backgroundPanel, JLayeredPane.DEFAULT_LAYER);
    }

    /**
     * <p>
     * Initializes and adds the close button to the layered pane.
     * Configures its appearance and adds a mouse listener for hover effects.
     * </p>
     *
     * <p>
     * Initialise et ajoute le bouton de fermeture au panneau superposé.
     * Configure son apparence et ajoute un écouteur de souris pour les effets de survol.
     * </p>
     *
     * @param layeredPane The JLayeredPane to which the close button will be added.
     */
    private void initializeCloseButton(JLayeredPane layeredPane) {
        closeButton = new JButton("×");
        closeButton.setOpaque(false);
        closeButton.setContentAreaFilled(false);
        closeButton.setBorderPainted(false);
        closeButton.setForeground(new Color(255, 255, 255, 180));
        closeButton.setFont(new Font("Arial", Font.BOLD, 32));
        closeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeButton.setFocusable(false);

        closeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                closeButton.setForeground(Color.RED);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                closeButton.setForeground(new Color(255, 255, 255, 180));
            }
        });
        layeredPane.add(closeButton, JLayeredPane.MODAL_LAYER);
    }

    /**
     * <p>
     * Initializes and adds the main time display label to the layered pane.
     * Sets its initial text color, font, alignment, and background.
     * </p>
     *
     * <p>
     * Initialise et ajoute l'étiquette d'affichage de l'heure principale au panneau superposé.
     * Définit sa couleur de texte initiale, sa police, son alignement et son arrière-plan.
     * </p>
     *
     * @param layeredPane The JLayeredPane to which the time label will be added.
     */
    private void initializeTimeLabel(JLayeredPane layeredPane) {
        timeLabel = new JLabel(" ", SwingConstants.CENTER);
        timeLabel.setForeground(currentTextColor);
        timeLabel.setFont(Constants.CLOCK_FONT_BASE);
        timeLabel.setVerticalAlignment(SwingConstants.CENTER);
        timeLabel.setOpaque(true);
        timeLabel.setBackground(currentClockBgColor);
        layeredPane.add(timeLabel, JLayeredPane.PALETTE_LAYER);
    }

    /**
     * <p>
     * Initializes the JTabbedPane and its various feature panels (Alarms, Stopwatch, Timer, Settings, World Clocks).
     * Applies custom UI manager settings for a consistent look and feel across tabs.
     * </p>
     *
     * <p>
     * Initialise le JTabbedPane et ses divers panneaux de fonctionnalités (Alarmes, Chronomètre, Minuteur, Paramètres, Fuseaux Horaires).
     * Applique des paramètres personnalisés du gestionnaire d'interface utilisateur pour une apparence cohérente entre les onglets.
     * </p>
     */
    private void initializeFeatureTabs() {
        tabbedPane = new JTabbedPane();
        tabbedPane.setOpaque(false);
        tabbedPane.setBackground(Constants.TABBED_PANE_BG_COLOR);
        tabbedPane.setForeground(Constants.LIFESTYLE_TAB_TEXT_UNSELECTED);
        tabbedPane.setFont(Constants.TAB_TITLE_FONT);

        UIManager.put("TabbedPane.contentOpaque", Boolean.TRUE);
        UIManager.put("TabbedPane.tabsOpaque", Boolean.FALSE);
        UIManager.put("TabbedPane.selected", Constants.LIFESTYLE_TAB_SELECTED_BG);
        UIManager.put("TabbedPane.background", Constants.LIFESTYLE_TAB_UNSELECTED_BG);
        UIManager.put("TabbedPane.contentAreaColor", Constants.LIFESTYLE_CONTENT_BG);
        UIManager.put("TabbedPane.tabAreaInsets", new Insets(10, 15, 0, 15));
        UIManager.put("TabbedPane.contentBorderInsets", new Insets(0, 0, 0, 0));
        UIManager.put("TabbedPane.selectedTabPadInsets", new Insets(8, 22, 8, 22));
        UIManager.put("TabbedPane.tabInsets", new Insets(8, 22, 8, 22));
        UIManager.put("TabbedPane.focus", new Color(0,0,0,0));
        UIManager.put("TabbedPane.borderHightlightColor", Constants.LIFESTYLE_BORDER_COLOR);
        UIManager.put("TabbedPane.darkShadow", Constants.LIFESTYLE_BORDER_COLOR.darker());
        UIManager.put("TabbedPane.light", Constants.LIFESTYLE_BORDER_COLOR);
        UIManager.put("TabbedPane.shadow", Constants.LIFESTYLE_BORDER_COLOR);
        UIManager.put("TabbedPane.textIconGap", 12);
        UIManager.put("TabbedPane.tabAreaBackground", new Color(0,0,0,0));

        initAlarmsPanel();
        initStopwatchPanel();
        initTimerPanel();
        initSettingsPanel();
        initWorldClockPanel();

        alarmsPanel.setBackground(Constants.LIFESTYLE_CONTENT_BG);
        stopwatchPanel.setBackground(Constants.LIFESTYLE_CONTENT_BG);
        timerPanel.setBackground(Constants.LIFESTYLE_CONTENT_BG);
        settingsPanel.setBackground(Constants.LIFESTYLE_CONTENT_BG);
        worldClocksPanel.setBackground(Constants.LIFESTYLE_CONTENT_BG);

        tabbedPane.addTab(Constants.ALARMS_TAB_TITLE, alarmsPanel);
        tabbedPane.addTab(Constants.STOPWATCH_TAB_TITLE, stopwatchPanel);
        tabbedPane.addTab(Constants.TIMER_TAB_TITLE, timerPanel);
        tabbedPane.addTab(Constants.WORLD_CLOCKS_TAB_TITLE, worldClocksPanel);
        tabbedPane.addTab(Constants.SETTINGS_TAB_TITLE, settingsPanel);

        tabbedPane.addChangeListener(e -> { // Lambda parameter 'e' is used.
            for (int i = 0; i < tabbedPane.getTabCount(); i++) {
                tabbedPane.setForegroundAt(i, i == tabbedPane.getSelectedIndex() ?
                        Constants.LIFESTYLE_TAB_TEXT_SELECTED : Constants.LIFESTYLE_TAB_TEXT_UNSELECTED);
            }
        });

        // Initial selection highlighting
        if (tabbedPane.getTabCount() > 0) {
            tabbedPane.setForegroundAt(tabbedPane.getSelectedIndex(), Constants.LIFESTYLE_TAB_TEXT_SELECTED);
        }
    }

    /**
     * <p>
     * Calculates the appropriate expanded height for the collapsible tab section based on its preferred size.
     * </p>
     *
     * <p>
     * Calcule la hauteur étendue appropriée pour la section d'onglets escamotable en fonction de sa taille préférée.
     * </p>
     *
     * @return The calculated expanded height.
     */
    private int calculateTabSectionExpandedHeight() {
        tabbedPane.validate();
        int preferredTabHeight = tabbedPane.getPreferredSize().height;
        return preferredTabHeight <= 0 ? Constants.DEFAULT_TAB_SECTION_EXPANDED_HEIGHT :
                Constants.TAB_SECTION_COLLAPSED_HEIGHT + preferredTabHeight + Constants.TAB_SECTION_CONTENT_PADDING;
    }

    /**
     * <p>
     * Initializes the collapsible panel that contains the feature tabs.
     * Sets its appearance and adds it to the layered pane.
     * </p>
     *
     * <p>
     * Initialise le panneau escamotable qui contient les onglets de fonctionnalités.
     * Définit son apparence et l'ajoute au panneau superposé.
     * </p>
     *
     * @param layeredPane The JLayeredPane to which the collapsible tab section will be added.
     */
    private void initializeCollapsibleTabSection(JLayeredPane layeredPane) {
        tabSectionCollapsiblePanel = new JPanel(new BorderLayout());
        tabSectionCollapsiblePanel.setOpaque(true);
        tabSectionCollapsiblePanel.setBackground(Constants.LIFESTYLE_PANEL_BG);
        tabSectionCollapsiblePanel.setBorder(BorderFactory.createLineBorder(Constants.LIFESTYLE_BORDER_COLOR, 1));

        tabSectionHeader = new JLabel();
        tabSectionHeader.setForeground(Constants.LIFESTYLE_TEXT_COLOR);
        tabSectionHeader.setFont(Constants.TAB_SECTION_HEADER_FONT);
        tabSectionHeader.setBorder(new EmptyBorder(18, 20, 18, 20));
        tabSectionHeader.setCursor(new Cursor(Cursor.HAND_CURSOR));
        tabSectionCollapsiblePanel.add(tabSectionHeader, BorderLayout.NORTH);

        JPanel tabContentWrapper = new JPanel(new BorderLayout());
        tabContentWrapper.setOpaque(false);
        tabContentWrapper.add(tabbedPane, BorderLayout.CENTER);
        tabSectionCollapsiblePanel.add(tabContentWrapper, BorderLayout.CENTER);

        if (isTabSectionExpanded) {
            tabSectionExpandedHeight = calculateTabSectionExpandedHeight();
        }

        layeredPane.add(tabSectionCollapsiblePanel, JLayeredPane.PALETTE_LAYER);
        tabbedPane.setVisible(isTabSectionExpanded);
        updateTabSectionHeaderIcon();
    }

    /**
     * <p>
     * Initializes the dedication message label and adds it to the layered pane.
     * Sets its text, color, font, and alignment.
     * </p>
     *
     * <p>
     * Initialise l'étiquette du message de dédicace et l'ajoute au panneau superposé.
     * Définit son texte, sa couleur, sa police et son alignement.
     * </p>
     *
     * @param layeredPane The JLayeredPane to which the dedication label will be added.
     */
    private void initializeDedicationLabel(JLayeredPane layeredPane) {
        dedicationLabel = new JLabel(customDedicationText);
        dedicationLabel.setForeground(Constants.DEDICATION_TEXT_COLOR);
        dedicationLabel.setFont(Constants.DEDICATION_FONT);
        dedicationLabel.setHorizontalAlignment(SwingConstants.LEFT);
        dedicationLabel.setOpaque(false);
        layeredPane.add(dedicationLabel, JLayeredPane.MODAL_LAYER);
    }

    /**
     * <p>
     * Registers various event listeners for window resizing, closing,
     * tab section toggling, and window dragging.
     * </p>
     *
     * <p>
     * Enregistre divers écouteurs d'événements pour le redimensionnement de la fenêtre,
     * la fermeture, le basculement de la section des onglets et le déplacement de la fenêtre.
     * </p>
     */
    private void registerListeners() {
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent evt) { // Lambda parameter 'evt' is used.
                SwingUtilities.invokeLater(FuturisticClockApp.this::adjustComponentSizes);
            }
        });
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) { // Lambda parameter 'e' is used.
                handleWindowClosing();
            }
        });
        tabSectionHeader.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) { // Lambda parameter 'e' is used.
                toggleTabSectionCollapsiblePanel();
            }
        });

        if (closeButton != null) {
            // Fix: Changed () -> to e -> to match ActionListener signature.
            closeButton.addActionListener(e -> handleWindowClosing());
        }

        MouseAdapter windowDragListener = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) { // Lambda parameter 'e' is used.
                initialClick = e.getPoint();
            }
        };
        MouseMotionAdapter windowMotionListener = new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) { // Lambda parameter 'e' is used.
                Point currentLocationOnScreen = e.getLocationOnScreen();
                setLocation(currentLocationOnScreen.x - initialClick.x, currentLocationOnScreen.y - initialClick.y);
            }
        };
        backgroundPanel.addMouseListener(windowDragListener);
        backgroundPanel.addMouseMotionListener(windowMotionListener);
    }

    /**
     * <p>
     * Starts the core timers for the application, including the main clock update timer
     * and the alarm check timer.
     * </p>
     *
     * <p>
     * Démarre les minuteurs principaux de l'application, y compris le minuteur de mise à jour de l'horloge principale
     * et le minuteur de vérification des alarmes.
     * </p>
     */
    private void startCoreTimers() {
        mainClockUpdateTimer = new java.util.Timer("MainClockThread", true);
        mainClockUpdateTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(FuturisticClockApp.this::updateTime);
            }
        }, 0, 1000);
        startAlarmCheckTimer();
    }

    /**
     * <p>
     * Applies the initial component sizes by invoking `adjustComponentSizes` on the Swing event dispatch thread.
     * </p>
     *
     * <p>
     * Applique les tailles initiales des composants en invoquant `adjustComponentSizes` sur le thread
     * de distribution d'événements de Swing.
     * </p>
     */
    private void applyInitialComponentSizes() {
        SwingUtilities.invokeLater(this::adjustComponentSizes);
    }

    /**
     * <p>
     * Adjusts the sizes and positions of various UI components based on the current frame dimensions.
     * This method is called when the window is resized.
     * </p>
     *
     * <p>
     * Ajuste les tailles et les positions des différents composants de l'interface utilisateur
     * en fonction des dimensions actuelles de la fenêtre. Cette méthode est appelée lors du redimensionnement de la fenêtre.
     * </p>
     */
    private void adjustComponentSizes() {
        if (backgroundPanel == null || timeLabel == null || tabSectionCollapsiblePanel == null || dedicationLabel == null || tabbedPane == null) {
            return;
        }
        int frameWidth = getWidth();
        int frameHeight = getHeight();

        backgroundPanel.setBounds(0, 0, frameWidth, frameHeight);

        if (closeButton != null) {
            closeButton.setBounds(frameWidth - 50, 5, 45, 45);
        }

        int currentTabSectionHeight = isTabSectionExpanded ? tabSectionExpandedHeight : Constants.TAB_SECTION_COLLAPSED_HEIGHT;
        int tabSectionPanelX = frameWidth - Constants.TAB_SECTION_WIDTH - Constants.GLOBAL_MARGIN;
        int tabSectionPanelY = Constants.GLOBAL_MARGIN;

        if (tabSectionCollapseExpandTimer == null || !tabSectionCollapseExpandTimer.isRunning()) {
            tabSectionCollapsiblePanel.setBounds(tabSectionPanelX, tabSectionPanelY, Constants.TAB_SECTION_WIDTH, currentTabSectionHeight);
        } else {
            tabSectionCollapsiblePanel.setLocation(tabSectionPanelX, tabSectionPanelY);
            tabSectionCollapsiblePanel.setSize(Constants.TAB_SECTION_WIDTH, tabSectionCollapsiblePanel.getHeight());
        }

        int clockFrameX = 0;
        int clockFrameWidth = Math.max(tabSectionPanelX - Constants.GLOBAL_MARGIN - clockFrameX,
                                       Constants.MIN_CLOCK_WIDTH + Constants.CLOCK_FRAME_HORIZONTAL_PADDING * 2);
        int clockFrameHeight = Math.max((int) (frameHeight * Constants.CLOCK_HEIGHT_RATIO),
                                        Constants.MIN_CLOCK_HEIGHT + Constants.CLOCK_FRAME_VERTICAL_PADDING * 2);

        int clockCenterY = frameHeight / 2;
        int clockFrameY = Math.max(Constants.GLOBAL_MARGIN, clockCenterY - (clockFrameHeight / 2));
        if (clockFrameY + clockFrameHeight + Constants.GLOBAL_MARGIN > frameHeight) {
            clockFrameY = frameHeight - clockFrameHeight - Constants.GLOBAL_MARGIN;
        }

        timeLabel.setBounds(clockFrameX, clockFrameY, clockFrameWidth, clockFrameHeight);

        int effectiveHeightForFont = Math.min(timeLabel.getHeight(), clockFrameHeight - Constants.CLOCK_FRAME_VERTICAL_PADDING * 2);
        int newTimeFontSize = Math.max((int) (effectiveHeightForFont * Constants.TIME_FONT_SIZE_RATIO), Constants.MIN_TIME_FONT_SIZE);
        timeLabel.setFont(Constants.CLOCK_FONT_BASE.deriveFont((float)newTimeFontSize));

        int dedicationWidth = dedicationLabel.getFontMetrics(Constants.DEDICATION_FONT).stringWidth(customDedicationText) + 20;
        int targetYDedication = frameHeight - Constants.DEDICATION_LABEL_HEIGHT - Constants.DEDICATION_MARGIN;

        dedicationTargetX = Constants.GLOBAL_MARGIN;

        if (dedicationAnimationTimer == null || !dedicationAnimationTimer.isRunning()) {
            dedicationLabel.setBounds(dedicationTargetX, targetYDedication, dedicationWidth, Constants.DEDICATION_LABEL_HEIGHT);
            dedicationCurrentX = dedicationTargetX;
        } else {
            dedicationLabel.setBounds(dedicationLabel.getX(), targetYDedication, dedicationWidth, Constants.DEDICATION_LABEL_HEIGHT);
        }

        JLayeredPane lp = (JLayeredPane) getContentPane().getComponent(0);
        lp.revalidate();
        lp.repaint();
    }

    /**
     * <p>
     * Updates the main clock display with the current time and date.
     * Also updates the world clock list to show current times in configured time zones.
     * </p>
     *
     * <p>
     * Met à jour l'affichage de l'horloge principale avec l'heure et la date actuelles.
     * Met également à jour la liste des horloges mondiales pour afficher les heures actuelles dans les fuseaux horaires configurés.
     * </p>
     */
    private void updateTime() {
        Date now = new Date();
        String timeStr = mainTimeFormatter.format(now);
        String dateStr = dateFormatter.format(now);
        timeLabel.setText("<html><center>" + timeStr + "<br><font size='-2'>" + dateStr.toUpperCase() + "</font></center></html>");

        if (worldClockListModel != null) {
            worldClockListModel.clear();
            ZonedDateTime currentUTC = ZonedDateTime.now(ZoneId.of("UTC"));

            for (String zoneId : worldTimeZones) {
                try {
                    ZoneId zone = ZoneId.of(zoneId);
                    ZonedDateTime zonedTime = currentUTC.withZoneSameInstant(zone);
                    String display = String.format("<html><b>%s:</b> %s</html>", zoneId, zonedTime.format(worldClockFormatter));
                    worldClockListModel.addElement(display);
                } catch (Exception e) {
                    worldClockListModel.addElement(String.format("<html><b style='color:red;'>%s: ID Invalide</b></html>", zoneId));
                }
            }
        }
    }

    /**
     * <p>
     * Starts the animation for the dedication label, making it slide into view.
     * </p>
     *
     * <p>
     * Démarre l'animation de l'étiquette de dédicace, la faisant glisser en vue.
     * </p>
     */
    private void startDedicationAnimation() {
        int dedicationWidth = dedicationLabel.getFontMetrics(Constants.DEDICATION_FONT).stringWidth(customDedicationText) + 40;
        dedicationTargetX = Constants.GLOBAL_MARGIN;
        dedicationCurrentX = Constants.GLOBAL_MARGIN - dedicationWidth;

        int currentFrameHeight = getHeight() > 0 ? getHeight() : Constants.INITIAL_WINDOW_SIZE.height;
        int initialY = currentFrameHeight - Constants.DEDICATION_LABEL_HEIGHT - Constants.DEDICATION_MARGIN;

        dedicationLabel.setLocation(dedicationCurrentX, initialY);

        if (dedicationAnimationTimer != null && dedicationAnimationTimer.isRunning()) {
            dedicationAnimationTimer.stop();
        }
        // Fix: Changed () -> to e -> to match ActionListener signature.
        dedicationAnimationTimer = new Timer(Constants.DEDICATION_ANIMATION_DELAY, e -> animateDedicationLabel());
        dedicationAnimationTimer.start();
    }

    /**
     * <p>
     * Animates the dedication label by incrementally moving it towards its target X-coordinate.
     * Stops the animation once the target is reached.
     * </p>
     *
     * <p>
     * Anime l'étiquette de dédicace en la déplaçant progressivement vers sa coordonnée X cible.
     * Arrête l'animation une fois la cible atteinte.
     * </p>
     */
    private void animateDedicationLabel() {
        if (dedicationCurrentX < dedicationTargetX) {
            dedicationCurrentX += Constants.DEDICATION_ANIMATION_STEP;
            if (dedicationCurrentX > dedicationTargetX) {
                dedicationCurrentX = dedicationTargetX;
            }
            dedicationLabel.setLocation(dedicationCurrentX, dedicationLabel.getY());
        } else {
            if (dedicationAnimationTimer != null) {
                dedicationAnimationTimer.stop();
            }
        }
    }

    /**
     * <p>
     * Toggles the collapsible tab section between expanded and collapsed states with animation.
     * Also updates the header icon and saves the new state.
     * </p>
     *
     * <p>
     * Bascule la section d'onglets escamotable entre les états étendu et réduit avec animation.
     * Met également à jour l'icône d'en-tête et enregistre le nouvel état.
     * </p>
     */
    private void toggleTabSectionCollapsiblePanel() {
        if (tabSectionCollapseExpandTimer != null && tabSectionCollapseExpandTimer.isRunning()) {
            return;
        }

        isTabSectionExpanded = !isTabSectionExpanded;
        updateTabSectionHeaderIcon();

        final int startHeight = tabSectionCollapsiblePanel.getHeight();
        final int endHeight = isTabSectionExpanded ? calculateTabSectionExpandedHeight() : Constants.TAB_SECTION_COLLAPSED_HEIGHT;
        tabbedPane.setVisible(isTabSectionExpanded); // Set visibility directly for immediate change if expanding

        tabSectionCollapseExpandTimer = new Timer(10, new ActionListener() {
            private int currentAnimatedHeight = startHeight;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (isTabSectionExpanded) {
                    currentAnimatedHeight += Constants.TAB_SECTION_ANIMATION_STEP;
                    if (currentAnimatedHeight >= endHeight) {
                        currentAnimatedHeight = endHeight;
                        ((Timer) e.getSource()).stop();
                        saveTabSectionState();
                    }
                } else {
                    currentAnimatedHeight -= Constants.TAB_SECTION_ANIMATION_STEP;
                    if (currentAnimatedHeight <= endHeight) {
                        currentAnimatedHeight = endHeight;
                        ((Timer) e.getSource()).stop();
                        tabbedPane.setVisible(false);
                        saveTabSectionState();
                    }
                }
                tabSectionCollapsiblePanel.setSize(tabSectionCollapsiblePanel.getWidth(), currentAnimatedHeight);
                tabSectionCollapsiblePanel.revalidate();
            }
        });
        tabSectionCollapseExpandTimer.start();
    }

    /**
     * <p>
     * Updates the icon in the tab section header to reflect whether the section is expanded or collapsed.
     * </p>
     *
     * <p>
     * Met à jour l'icône dans l'en-tête de la section d'onglets pour indiquer si la section est étendue ou réduite.
     * </p>
     */
    private void updateTabSectionHeaderIcon() {
        String arrow = isTabSectionExpanded ? "\u25B2" : "\u25BC";
        tabSectionHeader.setText("<html><b>" + Constants.CLOCK_FEATURES_TITLE + "</b> <span style='font-size:14px;'>" + arrow + "</span></html>");
    }

    /**
     * <p>
     * Saves the current state of the tab section (expanded height and expanded status) to preferences.
     * </p>
     *
     * <p>
     * Enregistre l'état actuel de la section d'onglets (hauteur étendue et statut étendu) dans les préférences.
     * </p>
     */
    private void saveTabSectionState() {
        prefs.putInt(Constants.PREF_TAB_SECTION_EXPANDED_HEIGHT, (tabSectionExpandedHeight > Constants.TAB_SECTION_COLLAPSED_HEIGHT) ? tabSectionExpandedHeight : Constants.DEFAULT_TAB_SECTION_EXPANDED_HEIGHT);
        prefs.putBoolean(Constants.PREF_IS_TAB_SECTION_EXPANDED, isTabSectionExpanded);
        flushPreferences();
    }

    /**
     * <p>
     * Creates a basic JPanel with common styling for feature panels.
     * </p>
     *
     * <p>
     * Crée un JPanel de base avec un style commun pour les panneaux de fonctionnalités.
     * </p>
     *
     * @return A styled JPanel.
     */
    private JPanel createBasicFeaturePanel() {
        JPanel panel = new JPanel(new BorderLayout(18, 18));
        panel.setOpaque(true);
        panel.setBackground(Constants.LIFESTYLE_CONTENT_BG);
        panel.setBorder(new EmptyBorder(30, 30, 30, 30));
        return panel;
    }

    /**
     * <p>
     * Initializes the alarms panel, including the alarm list, input fields, and action buttons.
     * Sets up listeners for adding and removing alarms.
     * </p>
     *
     * <p>
     * Initialise le panneau des alarmes, y compris la liste des alarmes, les champs de saisie et les boutons d'action.
     * Configure les écouteurs pour l'ajout et la suppression d'alarmes.
     * </p>
     */
    private void initAlarmsPanel() {
        alarmsPanel = createBasicFeaturePanel();

        alarmListModel = new DefaultListModel<>();
        alarmList = new JList<>(alarmListModel);
        alarmList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        alarmList.setBackground(Constants.LIFESTYLE_ALARM_LIST_BG);
        alarmList.setForeground(Constants.LIFESTYLE_TEXT_COLOR);
        alarmList.setFont(Constants.ALARM_LIST_FONT);
        alarmList.setFixedCellHeight(32);

        JScrollPane scrollPane = new JScrollPane(alarmList);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createLineBorder(Constants.LIFESTYLE_BORDER_COLOR));
        alarmsPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 15, 15));
        inputPanel.setOpaque(false);
        inputPanel.setBorder(new EmptyBorder(30, 0, 0, 0));

        alarmTimeField = createStyledInputFieldWithLabel(inputPanel, Constants.LABEL_TIME_HHMM, Constants.DEFAULT_ALARM_TIME);
        alarmMessageField = createStyledInputFieldWithLabel(inputPanel, Constants.LABEL_MESSAGE, Constants.DEFAULT_ALARM_MESSAGE);
        alarmRepeatDaily = createStyledCheckbox(Constants.CHECKBOX_REPEAT_DAILY, true);
        alarmSpecificDate = createStyledCheckbox(Constants.CHECKBOX_SPECIFIC_DATE, false);
        alarmDateField = createStyledInputFieldWithLabel(inputPanel, Constants.LABEL_ALARM_DATE, LocalDate.now().plusDays(1).format(alarmDateFormatter));
        alarmDateField.setEnabled(false);

        alarmSpecificDate.addActionListener(e -> { // Lambda parameter 'e' is used.
            alarmDateField.setEnabled(this.alarmSpecificDate.isSelected());
            if (this.alarmSpecificDate.isSelected()) this.alarmRepeatDaily.setSelected(false);
        });
        alarmRepeatDaily.addActionListener(e -> { // Lambda parameter 'e' is used.
            if (this.alarmRepeatDaily.isSelected()) {
                this.alarmSpecificDate.setSelected(false);
                this.alarmDateField.setEnabled(false);
            }
        });

        // Add checkboxes directly to inputPanel, managing their layout
        JPanel repeatOptionsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        repeatOptionsPanel.setOpaque(false);
        repeatOptionsPanel.add(this.alarmRepeatDaily);
        inputPanel.add(repeatOptionsPanel);

        JPanel specificDateOptionsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        specificDateOptionsPanel.setOpaque(false);
        specificDateOptionsPanel.add(this.alarmSpecificDate);
        inputPanel.add(specificDateOptionsPanel);

        // Fix: Changed () -> to e -> to match ActionListener signature.
        this.addAlarmButton = createStyledButton(Constants.BUTTON_ADD_ALARM, e -> addAlarm(), Constants.BUTTON_ACCENT_GREEN);
        this.removeAlarmButton = createStyledButton(Constants.BUTTON_REMOVE_ALARM, e -> removeAlarm(), Constants.BUTTON_ACCENT_RED);

        inputPanel.add(this.addAlarmButton);
        inputPanel.add(this.removeAlarmButton);

        alarmsPanel.add(inputPanel, BorderLayout.SOUTH);
    }

    /**
     * <p>
     * Initializes the stopwatch panel with display and control buttons.
     * Sets up initial state and button functionalities.
     * </p>
     *
     * <p>
     * Initialise le panneau du chronomètre avec affichage et boutons de contrôle.
     * Configure l'état initial et les fonctionnalités des boutons.
     * </p>
     */
    private void initStopwatchPanel() {
        stopwatchPanel = createBasicFeaturePanel();

        stopwatchDisplay = new JLabel("00:00:00.000", SwingConstants.CENTER);
        stopwatchDisplay.setFont(Constants.STOPWATCH_FONT);
        stopwatchDisplay.setForeground(Constants.STOPWATCH_DISPLAY_COLOR);
        JPanel displayWrapper = new JPanel(new GridBagLayout());
        displayWrapper.setOpaque(false);
        displayWrapper.add(stopwatchDisplay);
        stopwatchPanel.add(displayWrapper, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 25));
        buttonPanel.setOpaque(false);

        // Fix: Changed () -> to e -> to match ActionListener signature.
        startStopwatchButton = createStyledButton(Constants.BUTTON_START, e -> startStopwatch(), Constants.BUTTON_ACCENT_GREEN);
        stopStopwatchButton = createStyledButton(Constants.BUTTON_STOP, e -> stopStopwatch(), Constants.BUTTON_ACCENT_RED);
        resetStopwatchButton = createStyledButton(Constants.BUTTON_RESET, e -> resetStopwatch(), Constants.BUTTON_ACCENT_BLUE);

        buttonPanel.add(startStopwatchButton);
        buttonPanel.add(stopStopwatchButton);
        buttonPanel.add(resetStopwatchButton);
        stopwatchPanel.add(buttonPanel, BorderLayout.SOUTH);

        resetStopwatch();
    }

    /**
     * <p>
     * Initializes the countdown timer panel with display, input fields for time, and control buttons.
     * Sets up initial state and button functionalities.
     * </p>
     *
     * <p>
     * Initialise le panneau du minuteur avec affichage, champs de saisie pour l'heure et boutons de contrôle.
     * Configure l'état initial et les fonctionnalités des boutons.
     * </p>
     */
    private void initTimerPanel() {
        timerPanel = createBasicFeaturePanel();

        timerDisplay = new JLabel("00:00:00", SwingConstants.CENTER);
        timerDisplay.setFont(Constants.TIMER_DISPLAY_FONT);
        timerDisplay.setForeground(Constants.TIMER_DISPLAY_COLOR);
        JPanel displayWrapper = new JPanel(new GridBagLayout());
        displayWrapper.setOpaque(false);
        displayWrapper.add(timerDisplay);
        timerPanel.add(displayWrapper, BorderLayout.CENTER);

        JPanel inputButtonPanel = new JPanel(new BorderLayout(0,25));
        inputButtonPanel.setOpaque(false);

        JPanel timeInputControlsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 15));
        timeInputControlsPanel.setOpaque(false);

        timerInputHours = new JTextField("00", 3);
        timerInputMinutes = new JTextField("00", 3);
        timerInputSeconds = new JTextField("00", 3);

        styleTimerInputFieldLifestyle(timerInputHours);
        styleTimerInputFieldLifestyle(timerInputMinutes);
        styleTimerInputFieldLifestyle(timerInputSeconds);

        timeInputControlsPanel.add(timerInputHours);
        timeInputControlsPanel.add(createDarkLabel(":"));
        timeInputControlsPanel.add(timerInputMinutes);
        timeInputControlsPanel.add(createDarkLabel(":"));
        timeInputControlsPanel.add(timerInputSeconds);
        inputButtonPanel.add(timeInputControlsPanel, BorderLayout.NORTH);

        JPanel buttonControlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 20));
        buttonControlPanel.setOpaque(false);

        // Fix: Changed () -> to e -> to match ActionListener signature.
        startTimerButton = createStyledButton(Constants.BUTTON_START, e -> startCountdown(), Constants.BUTTON_ACCENT_GREEN);
        stopTimerButton = createStyledButton(Constants.BUTTON_STOP, e -> stopCountdown(), Constants.BUTTON_ACCENT_RED);
        resetTimerButton = createStyledButton(Constants.BUTTON_RESET, e -> resetCountdown(), Constants.BUTTON_ACCENT_BLUE);

        buttonControlPanel.add(startTimerButton);
        buttonControlPanel.add(stopTimerButton);
        buttonControlPanel.add(resetTimerButton);
        inputButtonPanel.add(buttonControlPanel, BorderLayout.CENTER);

        timerPanel.add(inputButtonPanel, BorderLayout.SOUTH);
        resetCountdown();
    }

    /**
     * <p>
     * Initializes the settings panel, allowing users to customize background image,
     * clock text color, clock background color, alarm sound, and dedication message.
     * </p>
     *
     * <p>
     * Initialise le panneau des paramètres, permettant aux utilisateurs de personnaliser
     * l'image d'arrière-plan, la couleur du texte de l'horloge, la couleur de fond de l'horloge,
     * le son de l'alarme et le message de dédicace.
     * </p>
     */
    private void initSettingsPanel() {
        settingsPanel = new JPanel();
        settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.Y_AXIS));
        settingsPanel.setOpaque(true);
        settingsPanel.setBackground(Constants.LIFESTYLE_CONTENT_BG);
        settingsPanel.setBorder(new EmptyBorder(30, 30, 30, 30));

        JPanel imagePanel = createSettingSectionPanelLifestyle(Constants.SECTION_TITLE_BACKGROUND_IMAGE);
        imageUrlField = new JTextField(prefs.get(Constants.PREF_IMAGE_URL, Constants.DEFAULT_BACKGROUND_IMAGE_URL));
        styleInputFieldLifestyle(imageUrlField);
        // Fix: Changed () -> to e -> to match ActionListener signature.
        changeImageButton = createStyledButton(Constants.BUTTON_CHANGE, e -> changeBackgroundImage(), Constants.BUTTON_ACCENT_BLUE);
        imagePanel.add(imageUrlField, BorderLayout.CENTER);
        imagePanel.add(changeImageButton, BorderLayout.EAST);
        settingsPanel.add(imagePanel);
        settingsPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel textColorPanel = createSettingSectionPanelLifestyle(Constants.SECTION_TITLE_TEXT_COLOR);
        final JLabel textColorPreview = createColorPreviewLabelLifestyle(currentTextColor);
        chooseTextColorButton = createStyledButton(Constants.BUTTON_CHOOSE_COLOR, e -> { // 'e' is used here
            Color selectedColor = JColorChooser.showDialog(FuturisticClockApp.this, Constants.SECTION_TITLE_TEXT_COLOR, currentTextColor);
            if (selectedColor != null) {
                currentTextColor = selectedColor;
                timeLabel.setForeground(currentTextColor);
                textColorPreview.setBackground(currentTextColor);
                saveColorPreferences();
            }
        }, Constants.BUTTON_ACCENT_YELLOW);
        JPanel previewContainerText = new JPanel(new FlowLayout(FlowLayout.LEFT, 0,0));
        previewContainerText.setOpaque(false);
        previewContainerText.add(textColorPreview);
        textColorPanel.add(previewContainerText, BorderLayout.WEST);
        textColorPanel.add(chooseTextColorButton, BorderLayout.CENTER);
        settingsPanel.add(textColorPanel);
        settingsPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel clockBgColorPanel = createSettingSectionPanelLifestyle(Constants.SECTION_TITLE_CLOCK_BG_COLOR);
        final JLabel clockBgColorPreview = createColorPreviewLabelLifestyle(currentClockBgColor);
        chooseClockBgColorButton = createStyledButton(Constants.BUTTON_CHOOSE_COLOR, e -> { // 'e' is used here
            int oldAlpha = currentClockBgColor.getAlpha();
            Color selectedColorNoAlpha = JColorChooser.showDialog(FuturisticClockApp.this, Constants.SECTION_TITLE_CLOCK_BG_COLOR,
                                                               new Color(currentClockBgColor.getRed(), currentClockBgColor.getGreen(), currentClockBgColor.getBlue()));
            if (selectedColorNoAlpha != null) {
                currentClockBgColor = new Color(selectedColorNoAlpha.getRed(),
                                                selectedColorNoAlpha.getGreen(),
                                                selectedColorNoAlpha.getBlue(),
                                                oldAlpha);
                timeLabel.setBackground(currentClockBgColor);
                clockBgColorPreview.setBackground(currentClockBgColor);
                saveColorPreferences();
            }
        }, Constants.BUTTON_ACCENT_YELLOW);
        JPanel previewContainerBg = new JPanel(new FlowLayout(FlowLayout.LEFT, 0,0));
        previewContainerBg.setOpaque(false);
        previewContainerBg.add(clockBgColorPreview);
        clockBgColorPanel.add(previewContainerBg, BorderLayout.WEST);
        clockBgColorPanel.add(chooseClockBgColorButton, BorderLayout.CENTER);
        settingsPanel.add(clockBgColorPanel);
        settingsPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel soundPanel = createSettingSectionPanelLifestyle(Constants.SECTION_TITLE_ALARM_SOUND);
        localSoundPathField = new JTextField(currentAlarmSoundPath);
        styleInputFieldLifestyle(localSoundPathField);
        localSoundPathField.setEditable(false);
        // Fix: Changed () -> to e -> to match ActionListener signature.
        chooseSoundButton = createStyledButton(Constants.BUTTON_CHOOSE_FILE, e -> chooseLocalAlarmSound(), Constants.BUTTON_ACCENT_BLUE);
        soundPanel.add(localSoundPathField, BorderLayout.CENTER);
        soundPanel.add(chooseSoundButton, BorderLayout.EAST);
        settingsPanel.add(soundPanel);
        settingsPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel dedicationPanel = createSettingSectionPanelLifestyle(Constants.SECTION_TITLE_DEDICATION_MESSAGE);
        dedicationTextField = new JTextField(customDedicationText);
        styleInputFieldLifestyle(dedicationTextField);
        // Fix: Changed () -> to e -> to match ActionListener signature.
        saveDedicationButton = createStyledButton(Constants.BUTTON_SAVE_DEDICATION, e -> saveDedicationText(), Constants.BUTTON_ACCENT_GREEN);
        dedicationPanel.add(dedicationTextField, BorderLayout.CENTER);
        dedicationPanel.add(saveDedicationButton, BorderLayout.EAST);
        settingsPanel.add(dedicationPanel);

        settingsPanel.add(Box.createVerticalGlue());
    }

    /**
     * <p>
     * Initializes the world clocks panel, including the list for displaying time zones,
     * input field for adding new time zones, and control buttons.
     * </p>
     *
     * <p>
     * Initialise le panneau des horloges mondiales, y compris la liste pour afficher les fuseaux horaires,
     * le champ de saisie pour ajouter de nouveaux fuseaux horaires et les boutons de contrôle.
     * </p>
     */
    private void initWorldClockPanel() {
        worldClocksPanel = createBasicFeaturePanel();

        worldClockListModel = new DefaultListModel<>();
        worldClockList = new JList<>(worldClockListModel);
        worldClockList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        worldClockList.setBackground(Constants.LIFESTYLE_ALARM_LIST_BG);
        worldClockList.setForeground(Constants.LIFESTYLE_TEXT_COLOR);
        worldClockList.setFont(Constants.ALARM_LIST_FONT);
        worldClockList.setFixedCellHeight(32);

        JScrollPane scrollPane = new JScrollPane(worldClockList);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createLineBorder(Constants.LIFESTYLE_BORDER_COLOR));
        worldClocksPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 15, 15));
        inputPanel.setOpaque(false);
        inputPanel.setBorder(new EmptyBorder(30, 0, 0, 0));

        timeZoneIdField = createStyledInputFieldWithLabel(inputPanel, Constants.LABEL_TIMEZONE_ID, "Europe/Paris");

        // Fix: Changed () -> to e -> to match ActionListener signature.
        addWorldClockButton = createStyledButton(Constants.BUTTON_ADD_TIMEZONE, e -> addWorldClock(), Constants.BUTTON_ACCENT_GREEN);
        removeWorldClockButton = createStyledButton(Constants.BUTTON_REMOVE_TIMEZONE, e -> removeWorldClock(), Constants.BUTTON_ACCENT_RED);

        inputPanel.add(addWorldClockButton);
        inputPanel.add(removeWorldClockButton);

        worldClocksPanel.add(inputPanel, BorderLayout.SOUTH);

        updateTime();
    }

    /**
     * <p>
     * Adds a new world time zone to the list. Validates the input time zone ID.
     * </p>
     *
     * <p>
     * Ajoute un nouveau fuseau horaire mondial à la liste. Valide l'ID du fuseau horaire saisi.
     * </p>
     */
    private void addWorldClock() {
        String zoneId = timeZoneIdField.getText().trim();
        if (zoneId.isEmpty()) {
            showErrorDialog("Veuillez entrer un ID de fuseau horaire.");
            return;
        }
        try {
            ZoneId.of(zoneId); // Validates if the zoneId is a valid time zone.
            if (!worldTimeZones.contains(zoneId)) {
                worldTimeZones.add(zoneId);
                saveWorldTimeZonesPreference();
                updateTime();
                timeZoneIdField.setText("");
            } else {
                showWarningDialog("Ce fuseau horaire est déjà ajouté.");
            }
        } catch (java.time.zone.ZoneRulesException e) {
            showErrorDialog(Constants.ERROR_INVALID_TIMEZONE);
        }
    }

    /**
     * <p>
     * Removes the selected world time zone from the list.
     * </p>
     *
     * <p>
     * Supprime le fuseau horaire mondial sélectionné de la liste.
     * </p>
     */
    private void removeWorldClock() {
        int selectedIndex = worldClockList.getSelectedIndex();
        if (selectedIndex != -1) {
            worldTimeZones.remove(selectedIndex);
            saveWorldTimeZonesPreference();
            updateTime();
        } else {
            showWarningDialog("Veuillez sélectionner un fuseau horaire à supprimer.");
        }
    }

    /**
     * <p>
     * Saves the current list of world time zones to user preferences.
     * </p>
     *
     * <p>
     * Enregistre la liste actuelle des fuseaux horaires mondiaux dans les préférences de l'utilisateur.
     * </p>
     */
    private void saveWorldTimeZonesPreference() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < worldTimeZones.size(); i++) {
            sb.append(worldTimeZones.get(i));
            if (i < worldTimeZones.size() - 1) {
                sb.append(",");
            }
        }
        prefs.put(Constants.PREF_WORLD_TIME_ZONES, sb.toString());
        flushPreferences();
    }

    /**
     * <p>
     * Adds a new alarm based on user input. Performs input validation
     * for time format, message, and date.
     * </p>
     *
     * <p>
     * Ajoute une nouvelle alarme basée sur la saisie de l'utilisateur. Effectue la validation de la saisie
     * pour le format de l'heure, le message et la date.
     * </p>
     */
    private void addAlarm() {
        String timeStr = alarmTimeField.getText().trim();
        String message = alarmMessageField.getText().trim();
        boolean repeatDaily = alarmRepeatDaily.isSelected();
        boolean specificDate = alarmSpecificDate.isSelected();
        String dateStr = alarmDateField.getText().trim();
        LocalDate alarmDate = null;

        if (!timeStr.matches(Constants.ALARM_TIME_FORMAT_REGEX)) {
            showErrorDialog(Constants.ERROR_INVALID_TIME);
            alarmTimeField.requestFocus();
            return;
        }
        if (message.isEmpty()) {
            showErrorDialog(Constants.ERROR_EMPTY_MESSAGE);
            alarmMessageField.requestFocus();
            return;
        }

        if (specificDate) {
            try {
                alarmDate = LocalDate.parse(dateStr, alarmDateFormatter);
            } catch (Exception e) {
                showErrorDialog(Constants.ERROR_INVALID_DATE_FORMAT);
                alarmDateField.requestFocus();
                return;
            }
            if (alarmDate.isBefore(LocalDate.now())) {
                showWarningDialog("La date de l'alarme spécifique ne peut pas être dans le passé.");
                alarmDateField.requestFocus();
                return;
            }
        } else if (!repeatDaily) {
             showWarningDialog("Veuillez choisir 'Répéter tous les jours' ou 'Alarme à date spécifique'.");
             return;
        }

        try {
            Alarm newAlarm = new Alarm(timeStr, message, repeatDaily, specificDate, alarmDate);
            if (alarmListModel.contains(newAlarm)) {
                showWarningDialog("Cette alarme existe déjà.");
                return;
            }
            alarmListModel.addElement(newAlarm);
            saveAlarmsPreference();
            alarmTimeField.setText(Constants.DEFAULT_ALARM_TIME);
            alarmMessageField.setText(Constants.DEFAULT_ALARM_MESSAGE);
            alarmRepeatDaily.setSelected(true);
            alarmSpecificDate.setSelected(false);
            alarmDateField.setText(LocalDate.now().plusDays(1).format(alarmDateFormatter));
            alarmDateField.setEnabled(false);
            // Using the INFO_ALARM_ADDED constant
            showInfoDialog(Constants.INFO_ALARM_ADDED + newAlarm.getTime());
        } catch (IllegalArgumentException e) {
            showErrorDialog(e.getMessage());
        }
    }

    /**
     * <p>
     * Removes the selected alarm from the alarm list.
     * </p>
     *
     * <p>
     * Supprime l'alarme sélectionnée de la liste des alarmes.
     * </p>
     */
    private void removeAlarm() {
        int selectedIndex = alarmList.getSelectedIndex();
        if (selectedIndex != -1) {
            alarmListModel.remove(selectedIndex);
            saveAlarmsPreference();
            // Using the INFO_ALARM_REMOVED constant
            showInfoDialog(Constants.INFO_ALARM_REMOVED);
        } else {
            showWarningDialog(Constants.WARNING_SELECT_ALARM_TO_REMOVE);
        }
    }

    /**
     * <p>
     * Starts a timer that periodically checks for active alarms.
     * Any previously running alarm check timer is cancelled.
     * </p>
     *
     * <p>
     * Démarre un minuteur qui vérifie périodiquement les alarmes actives.
     * Tout minuteur de vérification d'alarme précédemment en cours est annulé.
     * </p>
     */
    private void startAlarmCheckTimer() {
        if (alarmCheckTimer != null) {
            alarmCheckTimer.cancel();
        }
        alarmCheckTimer = new java.util.Timer("AlarmCheckThread", true);
        alarmCheckTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(FuturisticClockApp.this::checkAlarms);
            }
        }, 0, 1000);
    }

    /**
     * <p>
     * Checks all registered alarms against the current time.
     * Triggers alarms if conditions are met (time matches, not snoozed, not triggered today if daily).
     * Handles specific date alarms by removing them after triggering/expiration.
     * </p>
     *
     * <p>
     * Vérifie toutes les alarmes enregistrées par rapport à l'heure actuelle.
     * Déclenche les alarmes si les conditions sont remplies (l'heure correspond, non reportée,
     * non déclenchée aujourd'hui si quotidienne).
     * Gère les alarmes à date spécifique en les supprimant après le déclenchement/l'expiration.
     * </p>
     */
    private void checkAlarms() {
        if (alarmListModel.isEmpty()) return;

        String currentTime = alarmTimeCheckerFormatter.format(new Date());
        LocalDate currentDate = LocalDate.now();
        LocalTime currentLocalTime = LocalTime.now();
        Instant currentInstant = Instant.now();

        for (int i = alarmListModel.getSize() - 1; i >= 0; i--) {
            Alarm alarm = alarmListModel.getElementAt(i);

            if (alarm.getSnoozedUntil() != null && alarm.getSnoozedUntil().isAfter(currentInstant)) {
                continue;
            } else if (alarm.getSnoozedUntil() != null && alarm.getSnoozedUntil().isBefore(currentInstant)) {
                alarm.setSnoozedUntil(null);
            }

            boolean shouldTrigger = false;

            if (alarm.isRepeatDaily()) {
                if (alarm.getTime().equals(currentTime)) {
                    if (!alarm.isTriggeredToday()) {
                        shouldTrigger = true;
                        alarm.setTriggeredToday(true);
                    }
                } else {
                    if (alarm.isTriggeredToday()) {
                        alarm.setTriggeredToday(false);
                    }
                }
            } else if (alarm.isSpecificDate() && alarm.getAlarmDate() != null) {
                if (alarm.getAlarmDate().isEqual(currentDate) && alarm.getTime().equals(currentTime)) {
                    if (!alarm.isTriggeredToday()) {
                        shouldTrigger = true;
                        alarm.setTriggeredToday(true);
                    }
                } else if (alarm.getAlarmDate().isBefore(currentDate) || (alarm.getAlarmDate().isEqual(currentDate) && LocalTime.parse(alarm.getTime()).isBefore(currentLocalTime))) {
                    SwingUtilities.invokeLater(() -> {
                        alarmListModel.removeElementAt(alarmListModel.indexOf(alarm));
                        saveAlarmsPreference();
                        showInfoDialog("L'alarme pour le " + alarm.getAlarmDate().format(alarmDateFormatter) + " à " + alarm.getTime() + " est expirée et a été supprimée.");
                    });
                    continue;
                } else {
                    if (alarm.isTriggeredToday()) {
                        alarm.setTriggeredToday(false);
                    }
                }
            }

            if (shouldTrigger) {
                int option = JOptionPane.showOptionDialog(this,
                    alarm.getMessage(),
                    Constants.DIALOG_TITLE_INFO,
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    new Object[]{Constants.ALARM_SNOOZE_BUTTON, "Stop"},
                    "Stop");

                if (option == 0) {
                    alarm.setSnoozedUntil(Instant.now().plusSeconds(5 * 60));
                    showInfoDialog("Alarme reportée de 5 minutes.");
                    saveAlarmsPreference();
                } else {
                    if (!alarm.isRepeatDaily()) {
                        alarmListModel.removeElementAt(i);
                    }
                    alarm.setTriggeredToday(false);
                    playAlarmSound(alarm.getMessage());
                    saveAlarmsPreference();
                }
            }
        }
    }

    /**
     * <p>
     * Starts the stopwatch timer. If already running, does nothing.
     * </p>
     *
     * <p>
     * Démarre le minuteur du chronomètre. Ne fait rien s'il est déjà en cours d'exécution.
     * </p>
     */
    private void startStopwatch() {
        if (!stopwatchRunning) {
            stopwatchStartTime = System.currentTimeMillis() - stopwatchElapsedTime;
            if (stopwatchTimer != null) stopwatchTimer.cancel();
            stopwatchTimer = new java.util.Timer("StopwatchThread", true);
            stopwatchTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    SwingUtilities.invokeLater(FuturisticClockApp.this::updateStopwatchDisplay);
                }
            }, 0, 35);
            stopwatchRunning = true;
            updateStopwatchButtonStates();
        }
    }

    /**
     * <p>
     * Stops the stopwatch timer. If not running, does nothing.
     * </p>
     *
     * <p>
     * Arrête le minuteur du chronomètre. Ne fait rien s'il n'est pas en cours d'exécution.
     * </p>
     */
    private void stopStopwatch() {
        if (stopwatchRunning) {
            if (stopwatchTimer != null) stopwatchTimer.cancel();
            stopwatchElapsedTime = System.currentTimeMillis() - stopwatchStartTime;
            stopwatchRunning = false;
            updateStopwatchButtonStates();
        }
    }

    /**
     * <p>
     * Resets the stopwatch to zero and stops it if running.
     * </p>
     *
     * <p>
     * Réinitialise le chronomètre à zéro et l'arrête s'il est en cours d'exécution.
     * </p>
     */
    private void resetStopwatch() {
        if (stopwatchTimer != null) stopwatchTimer.cancel();
        stopwatchElapsedTime = 0;
        stopwatchRunning = false;
        updateStopwatchDisplay();
        updateStopwatchButtonStates();
    }

    /**
     * <p>
     * Updates the stopwatch display label with the current elapsed time.
     * </p>
     *
     * <p>
     * Met à jour l'étiquette d'affichage du chronomètre avec le temps écoulé actuel.
     * </p>
     */
    private void updateStopwatchDisplay() {
        long currentTimeToDisplay = stopwatchElapsedTime;
        if (stopwatchRunning) {
            currentTimeToDisplay = System.currentTimeMillis() - stopwatchStartTime;
        }

        long hours = currentTimeToDisplay / 3600000;
        long minutes = (currentTimeToDisplay % 3600000) / 60000;
        long seconds = (currentTimeToDisplay % 60000) / 1000;
        long milliseconds = currentTimeToDisplay % 1000;
        stopwatchDisplay.setText(String.format("%02d:%02d:%02d.%03d", hours, minutes, seconds, milliseconds));
    }

    /**
     * <p>
     * Updates the enabled/disabled state of the stopwatch control buttons.
     * </p>
     *
     * <p>
     * Met à jour l'état activé/désactivé des boutons de contrôle du chronomètre.
     * </p>
     */
    private void updateStopwatchButtonStates() {
        startStopwatchButton.setEnabled(!stopwatchRunning);
        stopStopwatchButton.setEnabled(stopwatchRunning);
        resetStopwatchButton.setEnabled(stopwatchElapsedTime > 0 || stopwatchRunning);
    }

    /**
     * <p>
     * Starts the countdown timer. Validates user input for hours, minutes, and seconds.
     * </p>
     *
     * <p>
     * Démarre le minuteur. Valide la saisie de l'utilisateur pour les heures, les minutes et les secondes.
     * </p>
     */
    private void startCountdown() {
        if (countdownRunning) return;

        try {
            int hours = Integer.parseInt(timerInputHours.getText());
            int minutes = Integer.parseInt(timerInputMinutes.getText());
            int seconds = Integer.parseInt(timerInputSeconds.getText());

            countdownRemainingTime = (long) (hours * 3600 + minutes * 60 + seconds) * 1000;

            if (countdownRemainingTime <= 0) {
                showErrorDialog(Constants.ERROR_POSITIVE_DURATION);
                return;
            }

            if (countdownTimer != null) countdownTimer.cancel();
            countdownTimer = new java.util.Timer("CountdownThread", true);
            countdownTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    SwingUtilities.invokeLater(FuturisticClockApp.this::updateCountdownDisplay);
                }
            }, 0, 1000);
            countdownRunning = true;
            updateCountdownButtonStates();
        } catch (NumberFormatException e) {
            showErrorDialog(Constants.ERROR_VALID_NUMBERS_DURATION);
        }
    }

    /**
     * <p>
     * Stops the countdown timer. If not running, does nothing.
     * </p>
     *
     * <p>
     * Arrête le minuteur. Ne fait rien s'il n'est pas en cours d'exécution.
     * </p>
     */
    private void stopCountdown() {
        if (countdownRunning) {
            if (countdownTimer != null) countdownTimer.cancel();
            countdownRunning = false;
            updateCountdownButtonStates();
        }
    }

    /**
     * <p>
     * Resets the countdown timer to zero and clears input fields.
     * </p>
     *
     * <p>
     * Réinitialise le minuteur à zéro et efface les champs de saisie.
     * </p>
     */
    private void resetCountdown() {
        if (countdownTimer != null) countdownTimer.cancel();
        countdownRemainingTime = 0;
        countdownRunning = false;
        timerInputHours.setText("00");
        timerInputMinutes.setText("00");
        timerInputSeconds.setText("00");
        updateCountdownDisplay();
        updateCountdownButtonStates();
    }

    /**
     * <p>
     * Updates the countdown timer display label. Triggers alarm when countdown finishes.
     * </p>
     *
     * <p>
     * Met à jour l'étiquette d'affichage du minuteur. Déclenche l'alarme lorsque le compte à rebours se termine.
     * </p>
     */
    private void updateCountdownDisplay() {
        if (countdownRunning) {
            countdownRemainingTime -= 1000;
            if (countdownRemainingTime < 0) countdownRemainingTime = 0;
        }

        long displayTime = countdownRemainingTime;

        if (displayTime <= 0 && countdownRunning) {
            timerDisplay.setText("00:00:00");
            if (countdownTimer != null) countdownTimer.cancel();
            countdownRunning = false;
            playAlarmSound(Constants.INFO_TIMER_FINISHED);
            updateCountdownButtonStates();
            return;
        } else if (displayTime <= 0 && !countdownRunning) {
             timerDisplay.setText("00:00:00");
             updateCountdownButtonStates();
             return;
        }

        long hours = displayTime / 3600000;
        long minutes = (displayTime % 3600000) / 60000;
        long seconds = (displayTime % 60000) / 1000;
        timerDisplay.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
    }

    /**
     * <p>
     * Updates the enabled/disabled state of the countdown timer control buttons and input fields.
     * </p>
     *
     * <p>
     * Met à jour l'état activé/désactivé des boutons de contrôle du minuteur et des champs de saisie.
     * </p>
     */
    private void updateCountdownButtonStates() {
        startTimerButton.setEnabled(!countdownRunning);
        stopTimerButton.setEnabled(countdownRunning);

        boolean hasInputTime = false;
        try {
            int h = Integer.parseInt(timerInputHours.getText());
            int m = Integer.parseInt(timerInputMinutes.getText());
            int s = Integer.parseInt(timerInputSeconds.getText());
            if (h > 0 || m > 0 || s > 0) hasInputTime = true;
        } catch (NumberFormatException e) { /* ignore */ }

        resetTimerButton.setEnabled(countdownRunning || countdownRemainingTime > 0 || hasInputTime);

        timerInputHours.setEnabled(!countdownRunning);
        timerInputMinutes.setEnabled(!countdownRunning);
        timerInputSeconds.setEnabled(!countdownRunning);
    }

    /**
     * <p>
     * Changes the background image of the application based on the URL provided in the input field.
     * Validates the URL and updates preferences upon successful image loading.
     * </p>
     *
     * <p>
     * Change l'image d'arrière-plan de l'application en fonction de l'URL fournie dans le champ de saisie.
     * Valide l'URL et met à jour les préférences après le chargement réussi de l'image.
     * </p>
     */
    private void changeBackgroundImage() {
        String newImageUrl = imageUrlField.getText().trim();
        if (newImageUrl.isEmpty()) {
            showErrorDialog(Constants.ERROR_IMAGE_URL_EMPTY);
            return;
        }
        Image newImage = loadImage(newImageUrl);
        if (newImage != null) {
            backgroundPanel.setImage(newImage, newImageUrl);
            prefs.put(Constants.PREF_IMAGE_URL, newImageUrl);
            flushPreferences();
            showInfoDialog(Constants.INFO_BACKGROUND_UPDATED);
        } else {
            showErrorDialog(Constants.ERROR_LOAD_IMAGE + newImageUrl + Constants.ERROR_LOAD_IMAGE_CHECK_URL);
            imageUrlField.setText(prefs.get(Constants.PREF_IMAGE_URL, Constants.DEFAULT_BACKGROUND_IMAGE_URL));
        }
    }

    /**
     * <p>
     * Opens a file chooser dialog to allow the user to select a local WAV sound file for alarms.
     * Loads the selected sound and updates preferences if successful.
     * </p>
     *
     * <p>
     * Ouvre une boîte de dialogue de sélection de fichier pour permettre à l'utilisateur de sélectionner
     * un fichier son WAV local pour les alarmes. Charge le son sélectionné et met à jour les préférences
     * si l'opération réussit.
     * </p>
     */
    private void chooseLocalAlarmSound() {
        JFileChooser fileChooser = new JFileChooser();
        String lastDir = prefs.get(Constants.PREF_LAST_SOUND_DIRECTORY, System.getProperty("user.home"));
        fileChooser.setCurrentDirectory(new File(lastDir));
        fileChooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.isDirectory() || f.getName().toLowerCase().endsWith(".wav");
            }
            @Override
            public String getDescription() {
                return Constants.FILE_CHOOSER_WAV_DESC;
            }
        });
        fileChooser.setDialogTitle("Choisir un fichier son WAV pour l'alarme");

        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String newSoundPath = selectedFile.getAbsolutePath();
            Clip tempClip = loadSoundClip(newSoundPath);
            if (tempClip != null) {
                if (reusableAlarmSoundClip != null && reusableAlarmSoundClip.isOpen()) {
                    reusableAlarmSoundClip.close(); // Close existing clip to release resources
                }
                reusableAlarmSoundClip = tempClip;
                currentAlarmSoundPath = newSoundPath;
                localSoundPathField.setText(currentAlarmSoundPath);
                prefs.put(Constants.PREF_DEFAULT_RINGTONE_PATH, currentAlarmSoundPath);
                prefs.put(Constants.PREF_LAST_SOUND_DIRECTORY, selectedFile.getParent());
                flushPreferences();
                showInfoDialog(Constants.INFO_ALARM_SOUND_UPDATED);
            } else {
                localSoundPathField.setText(currentAlarmSoundPath); // Revert to old path if new one fails
            }
        }
    }

    /**
     * <p>
     * Loads an audio clip from a given sound path (URL or local file).
     * Handles various exceptions related to audio file loading and availability.
     * Ensures proper resource management by closing the audio stream.
     * </p>
     *
     * <p>
     * Charge un clip audio à partir d'un chemin de son donné (URL ou fichier local).
     * Gère diverses exceptions liées au chargement et à la disponibilité du fichier audio.
     * Assure une bonne gestion des ressources en fermant le flux audio.
     * </p>
     *
     * @param soundPath The path to the sound file (URL or local file path).
     * @return A Clip object if successful, null otherwise.
     */
    private Clip loadSoundClip(String soundPath) {
        AudioInputStream audioStream = null;
        Clip clip = null;
        try {
            URI soundUri;
            if (soundPath.toLowerCase().startsWith("http://") || soundPath.toLowerCase().startsWith("https://")) {
                soundUri = new URI(soundPath);
            } else {
                File soundFile = new File(soundPath);
                if (!soundFile.exists() || !soundFile.isFile()) {
                    System.err.println("Local sound file not found or is not a file: " + soundPath);
                    return null; // Return null early if local file doesn't exist
                }
                soundUri = soundFile.toURI();
            }
            URL soundUrl = soundUri.toURL(); // Convert URI to URL

            audioStream = AudioSystem.getAudioInputStream(soundUrl);

            if (audioStream != null) {
                clip = AudioSystem.getClip();
                clip.open(audioStream);
                System.out.println("Sound clip loaded successfully from: " + soundPath);
            }
        } catch (URISyntaxException e) {
            System.err.println("Invalid URI syntax for " + soundPath + ": " + e.getMessage());
            showErrorDialog("L'URL/le chemin du son n'est pas valide : " + soundPath);
        } catch (UnsupportedAudioFileException e) {
            System.err.println("Unsupported audio file format for " + soundPath + ": " + e.getMessage());
            showErrorDialog(Constants.ERROR_UNSUPPORTED_AUDIO + soundPath);
        } catch (IOException e) {
            System.err.println("I/O error loading sound " + soundPath + ": " + e.getMessage());
            showErrorDialog(Constants.ERROR_LOADING_SOUND + soundPath);
        } catch (LineUnavailableException e) {
            System.err.println("Audio line unavailable for " + soundPath + ": " + e.getMessage());
            showErrorDialog(Constants.ERROR_AUDIO_LINE_UNAVAILABLE);
        } catch (Exception e) { // Catching generic Exception for robustness, good practice to be more specific.
            System.err.println("General error loading sound " + soundPath + ": " + e.getMessage());
            showErrorDialog(Constants.ERROR_UNEXPECTED_SOUND_LOAD + "\n" + e.getMessage());
            e.printStackTrace(); // Print stack trace for debugging unexpected errors
        } finally {
            if (audioStream != null) {
                try {
                    audioStream.close();
                } catch (IOException e) {
                    System.err.println("Error closing audio stream: " + e.getMessage());
                }
            }
        }
        return clip;
    }

    /**
     * <p>
     * Loads the alarm sound based on the `currentAlarmSoundPath` preference.
     * Falls back to the default alarm sound if the preferred one cannot be loaded.
     * </p>
     *
     * <p>
     * Charge le son de l'alarme en fonction de la préférence `currentAlarmSoundPath`.
     * Revient au son d'alarme par défaut si celui préféré ne peut pas être chargé.
     * </p>
     */
    private void loadAlarmSound() {
        if (reusableAlarmSoundClip != null && reusableAlarmSoundClip.isOpen()) {
            reusableAlarmSoundClip.close();
        }
        reusableAlarmSoundClip = loadSoundClip(currentAlarmSoundPath);

        if (reusableAlarmSoundClip == null && !currentAlarmSoundPath.equals(Constants.DEFAULT_ALARM_SOUND_URL)) {
            showWarningDialog(Constants.WARNING_LOCAL_SOUND_NOT_FOUND + currentAlarmSoundPath +
                              Constants.WARNING_LOADING_DEFAULT_SOUND);
            currentAlarmSoundPath = Constants.DEFAULT_ALARM_SOUND_URL;
            prefs.put(Constants.PREF_DEFAULT_RINGTONE_PATH, currentAlarmSoundPath);
            flushPreferences();
            if (localSoundPathField != null) {
                localSoundPathField.setText(currentAlarmSoundPath);
            }
            reusableAlarmSoundClip = loadSoundClip(currentAlarmSoundPath);
        }
    }

    /**
     * <p>
     * Plays the alarm sound. Displays an alert message if provided and not for timer completion.
     * Resets and starts the `reusableAlarmSoundClip`.
     * </p>
     *
     * <p>
     * Joue le son de l'alarme. Affiche un message d'alerte s'il est fourni et ne concerne pas la fin du minuteur.
     * Réinitialise et démarre le `reusableAlarmSoundClip`.
     * </p>
     *
     * @param alertMessage The message to display in the alert dialog, or `Constants.INFO_TIMER_FINISHED` if from the timer.
     */
    private void playAlarmSound(String alertMessage) {
        if (!alertMessage.equals(Constants.INFO_TIMER_FINISHED)) {
             JOptionPane.showMessageDialog(this, alertMessage, Constants.DIALOG_TITLE_INFO, JOptionPane.INFORMATION_MESSAGE);
        }

        if (reusableAlarmSoundClip != null && reusableAlarmSoundClip.isOpen()) {
            if (reusableAlarmSoundClip.isRunning()) {
                reusableAlarmSoundClip.stop();
            }
            reusableAlarmSoundClip.setFramePosition(0); // Rewind to the beginning
            reusableAlarmSoundClip.start();
        } else {
            System.err.println("Alarm sound clip is not available or failed to load. Message: " + alertMessage);
        }
    }

    /**
     * <p>
     * Loads all persisted application data, specifically alarms.
     * </p>
     *
     * <p>
     * Charge toutes les données d'application persistantes, en particulier les alarmes.
     * </p>
     */
    private void loadAllPersistedData() {
        loadAlarmsPreference();
    }

    /**
     * <p>
     * Saves the current text and clock background colors to user preferences.
     * </p>
     *
     * <p>
     * Enregistre les couleurs actuelles du texte et du fond de l'horloge dans les préférences de l'utilisateur.
     * </p>
     */
    private void saveColorPreferences() {
        prefs.putInt(Constants.PREF_TEXT_COLOR, currentTextColor.getRGB());
        prefs.putInt(Constants.PREF_BG_COLOR, currentClockBgColor.getRGB());
        flushPreferences();
    }

    /**
     * <p>
     * Saves the custom dedication text entered by the user. Updates the label and restarts its animation.
     * </p>
     *
     * <p>
     * Enregistre le texte de dédicace personnalisé saisi par l'utilisateur. Met à jour l'étiquette et redémarre son animation.
     * </p>
     */
    private void saveDedicationText() {
        String newDedicationText = dedicationTextField.getText().trim();
        if (!newDedicationText.isEmpty()) {
            customDedicationText = newDedicationText;
            dedicationLabel.setText(customDedicationText);
            prefs.put(Constants.PREF_CUSTOM_DEDICATION_TEXT, customDedicationText);
            flushPreferences();
            showInfoDialog("Message de dédicace mis à jour.");
            adjustComponentSizes(); // Adjust size as text length might change
            startDedicationAnimation(); // Restart animation to reflect new text
        } else {
            showErrorDialog("Le message de dédicace ne peut pas être vide.");
        }
    }

    /**
     * <p>
     * Saves all current alarms from `alarmListModel` to user preferences.
     * Alarm data is serialized into a string format.
     * </p>
     *
     * <p>
     * Enregistre toutes les alarmes actuelles de `alarmListModel` dans les préférences de l'utilisateur.
     * Les données d'alarme sont sérialisées dans un format de chaîne de caractères.
     * </p>
     */
    private void saveAlarmsPreference() {
        StringBuilder alarmData = new StringBuilder();
        for (int i = 0; i < alarmListModel.getSize(); i++) {
            Alarm alarm = alarmListModel.getElementAt(i);
            // Replace delimiters in message to prevent parsing issues
            String safeMessage = alarm.getMessage().replace(";", "&#59;").replace("|", "&#124;");
            alarmData.append(alarm.getTime()).append(";")
                     .append(safeMessage).append(";")
                     .append(alarm.isRepeatDaily()).append(";")
                     .append(alarm.isSpecificDate()).append(";")
                     .append(alarm.getAlarmDate() != null ? alarm.getAlarmDate().format(alarmDateFormatter) : "null").append(";")
                     .append(alarm.getSnoozedUntil() != null ? alarm.getSnoozedUntil().toEpochMilli() : "null");

            if (i < alarmListModel.getSize() - 1) {
                alarmData.append("|");
            }
        }
        prefs.put(Constants.PREF_ALARMS, alarmData.toString());
        flushPreferences();
    }

    /**
     * <p>
     * Loads alarms from user preferences and populates `alarmListModel`.
     * Handles parsing of alarm data strings and recovers from malformed entries.
     * </p>
     *
     * <p>
     * Charge les alarmes depuis les préférences de l'utilisateur et remplit `alarmListModel`.
     * Gère l'analyse des chaînes de données d'alarme et récupère les entrées mal formées.
     * </p>
     */
    private void loadAlarmsPreference() {
        alarmListModel.clear();
        String alarmString = prefs.get(Constants.PREF_ALARMS, "");
        if (!alarmString.isEmpty()) {
            StringTokenizer alarmEntriesTokenizer = new StringTokenizer(alarmString, "|");
            while (alarmEntriesTokenizer.hasMoreTokens()) {
                String alarmEntry = alarmEntriesTokenizer.nextToken();
                String[] parts = alarmEntry.split(";", 6); // Split into 6 parts
                // Ensure sufficient parts for core alarm data (time, message, repeatDaily, specificDate)
                if (parts.length >= 4) {
                    try {
                        String time = parts[0];
                        // Restore original delimiters in message
                        String message = parts[1].replace("&#59;", ";").replace("&#124;", "|");
                        boolean repeatDaily = Boolean.parseBoolean(parts[2]);
                        boolean specificDate = false;
                        LocalDate alarmDate = null;
                        Instant snoozedUntil = null;

                        if (parts.length >= 4) {
                            specificDate = Boolean.parseBoolean(parts[3]);
                        }
                        if (parts.length >= 5 && !parts[4].equals("null")) {
                            alarmDate = LocalDate.parse(parts[4], alarmDateFormatter);
                        }
                        if (parts.length >= 6 && !parts[5].equals("null")) {
                            snoozedUntil = Instant.ofEpochMilli(Long.parseLong(parts[5]));
                        }

                        Alarm loadedAlarm = new Alarm(time, message, repeatDaily, specificDate, alarmDate);
                        loadedAlarm.setSnoozedUntil(snoozedUntil);
                        alarmListModel.addElement(loadedAlarm);

                    } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) { // NumberFormatException removed as it's caught by IllegalArgumentException
                        System.err.println("Error loading a persisted alarm: " + alarmEntry + " - " + e.getMessage());
                    }
                } else {
                     System.err.println("Malformed alarm entry in preferences: " + alarmEntry);
                }
            }
        }
    }

    /**
     * <p>
     * Flushes the preferences to ensure they are saved to the backing store.
     * Handles potential `BackingStoreException`.
     * </p>
     *
     * <p>
     * Vide les préférences pour s'assurer qu'elles sont enregistrées dans le stockage persistant.
     * Gère les `BackingStoreException` potentielles.
     * </p>
     */
    private void flushPreferences() {
        try {
            prefs.flush();
        } catch (java.util.prefs.BackingStoreException e) {
            System.err.println("Error saving preferences to backing store: " + e.getMessage());
        }
    }

    /**
     * <p>
     * Loads an image from a given path, which can be a URL or a local file path.
     * Performs basic validation on the image path and checks image dimensions.
     * </p>
     *
     * <p>
     * Charge une image à partir d'un chemin donné, qui peut être une URL ou un chemin de fichier local.
     * Effectue une validation de base sur le chemin de l'image et vérifie les dimensions de l'image.
     * </p>
     *
     * @param imagePath The URL or local file path of the image.
     * @return The loaded Image object, or null if loading fails or the image is invalid.
     */
    private Image loadImage(String imagePath) {
        if (imagePath == null || imagePath.trim().isEmpty()) {
            System.err.println("Image path is null or empty.");
            return null;
        }
        try {
            URI imageUri;

            if (imagePath.toLowerCase().startsWith("http://") || imagePath.toLowerCase().startsWith("https://")) {
                imageUri = new URI(imagePath);
            } else {
                File imageFile = new File(imagePath);
                if (!imageFile.exists() || !imageFile.isFile()) {
                    System.err.println("Local image file not found or is not a file: " + imagePath);
                    return null;
                }
                imageUri = imageFile.toURI();
            }
            URL imageUrl = imageUri.toURL();

            // ImageIcon is used for easy image loading from URL/path
            Image image = new ImageIcon(imageUrl).getImage();

            // Validate image dimensions to ensure it loaded correctly
            if (image != null && image.getWidth(null) > 0 && image.getHeight(null) > 0) {
                return image;
            } else {
                System.err.println("Failed to decode image or image is invalid: " + imagePath);
                return null;
            }
        } catch (URISyntaxException e) {
            System.err.println("Invalid URI syntax for image path '" + imagePath + "': " + e.getMessage());
            return null;
        } catch (IOException e) {
            System.err.println("I/O error loading image from path '" + imagePath + "': " + e.getMessage());
            return null;
        } catch (Exception e) { // Catching generic Exception for robustness, good practice to be more specific.
            System.err.println("Error loading image from path '" + imagePath + "': " + e.getMessage());
            e.printStackTrace(); // Print stack trace for debugging unexpected errors
            return null;
        }
    }

    /**
     * <p>
     * Applies a consistent "lifestyle" style to a JTextField, including background, foreground,
     * caret color, border, and font.
     * </p>
     *
     * <p>
     * Applique un style "lifestyle" cohérent à un JTextField, y compris l'arrière-plan,
     * le premier plan, la couleur du curseur, la bordure et la police.
     * </p>
     *
     * @param field The JTextField to style.
     */
    private void styleInputFieldLifestyle(JTextField field) {
        field.setBackground(Constants.LIFESTYLE_INPUT_BG);
        field.setForeground(Constants.LIFESTYLE_INPUT_TEXT);
        field.setCaretColor(Constants.LIFESTYLE_TEXT_COLOR.darker());
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Constants.LIFESTYLE_BORDER_COLOR, 1),
            new EmptyBorder(10, 14, 10, 14)
        ));
        field.setFont(Constants.LABEL_FONT.deriveFont(15f));
    }

    /**
     * <p>
     * Applies a specific "lifestyle" style suitable for timer input fields,
     * including font, colors, alignment, and border.
     * </p>
     *
     * <p>
     * Applique un style "lifestyle" spécifique adapté aux champs de saisie du minuteur,
     * y compris la police, les couleurs, l'alignement et la bordure.
     * </p>
     *
     * @param field The JTextField to style as a timer input.
     */
    private void styleTimerInputFieldLifestyle(JTextField field) {
        field.setFont(Constants.TIMER_INPUT_FONT);
        field.setBackground(Constants.LIFESTYLE_INPUT_BG);
        field.setForeground(Constants.LIFESTYLE_INPUT_TEXT);
        field.setHorizontalAlignment(SwingConstants.CENTER);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Constants.LIFESTYLE_BORDER_COLOR, 1),
            new EmptyBorder(6, 10, 6, 10)
        ));
        field.setCaretColor(Constants.LIFESTYLE_TEXT_COLOR.darker());
    }

    /**
     * <p>
     * Creates a styled JButton with a given text, ActionListener, and base background color.
     * Includes mouse hover and pressed effects for better user feedback.
     * </p>
     *
     * <p>
     * Crée un JButton stylisé avec un texte donné, un ActionListener et une couleur d'arrière-plan de base.
     * Inclut des effets de survol et de pression de la souris pour un meilleur retour utilisateur.
     * </p>
     *
     * @param text The text to display on the button.
     * @param listener The ActionListener to be added to the button.
     * @param baseBgColor The base background color for the button.
     * @return A styled JButton.
     */
    private JButton createStyledButton(String text, ActionListener listener, Color baseBgColor) {
        JButton button = new JButton(text);
        button.setBackground(baseBgColor);
        button.setForeground(Constants.BUTTON_TEXT_COLOR);
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(15, 28, 15, 28));
        button.setFont(Constants.BUTTON_FONT);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addActionListener(listener);

        final Color hoverColor = baseBgColor.brighter();
        final Color pressedColor = baseBgColor.darker();

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) { if (button.isEnabled()) button.setBackground(hoverColor); }
            @Override
            public void mouseExited(MouseEvent e) { if (button.isEnabled()) button.setBackground(baseBgColor); }
            @Override
            public void mousePressed(MouseEvent e) { if (button.isEnabled()) button.setBackground(pressedColor); }
            @Override
            public void mouseReleased(MouseEvent e) {
                if (button.isEnabled()) {
                    button.setBackground(button.contains(e.getPoint()) ? hoverColor : baseBgColor);
                }
            }
        });
        return button;
    }

    /**
     * <p>
     * Creates a JTextField with a label, styled according to "lifestyle" theme,
     * and adds them to a parent panel using a grid layout.
     * </p>
     *
     * <p>
     * Crée un JTextField avec une étiquette, stylisé selon le thème "lifestyle",
     * et les ajoute à un panneau parent en utilisant une disposition en grille.
     * </p>
     *
     * @param parentPanel The JPanel to which the label and field will be added.
     * @param labelText The text for the label.
     * @param defaultValue The default text for the JTextField.
     * @return The styled JTextField.
     */
    private JTextField createStyledInputFieldWithLabel(JPanel parentPanel, String labelText, String defaultValue) {
        JLabel label = new JLabel(labelText);
        label.setForeground(Constants.LIFESTYLE_TEXT_COLOR);
        label.setFont(Constants.LABEL_FONT);
        JTextField field = new JTextField(defaultValue);
        styleInputFieldLifestyle(field);
        parentPanel.add(label);
        parentPanel.add(field);
        return field;
    }

    /**
     * <p>
     * Creates a styled JCheckBox with a given text and initial selection state.
     * </p>
     *
     * <p>
     * Crée une JCheckBox stylisée avec un texte donné et un état de sélection initial.
     * </p>
     *
     * @param text The text for the checkbox.
     * @param selected The initial selected state of the checkbox.
     * @return A styled JCheckBox.
     */
    private JCheckBox createStyledCheckbox(String text, boolean selected) {
        JCheckBox checkBox = new JCheckBox(text);
        checkBox.setOpaque(false);
        checkBox.setForeground(Constants.LIFESTYLE_TEXT_COLOR);
        checkBox.setFont(Constants.LABEL_FONT);
        checkBox.setSelected(selected);
        return checkBox;
    }

    /**
     * <p>
     * Creates a JPanel with a titled border, styled according to the "lifestyle" theme.
     * Useful for grouping settings.
     * </p>
     *
     * <p>
     * Crée un JPanel avec une bordure titrée, stylisé selon le thème "lifestyle".
     * Utile pour regrouper les paramètres.
     * </p>
     *
     * @param title The title for the titled border.
     * @return A JPanel with a styled titled border.
     */
    private JPanel createSettingSectionPanelLifestyle(String title) {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setOpaque(false);

        TitledBorder titledBorder = BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Constants.LIFESTYLE_BORDER_COLOR.darker(), 1),
            " " + title + " ",
            TitledBorder.LEFT, TitledBorder.TOP,
            Constants.TITLED_BORDER_FONT, Constants.LIFESTYLE_TEXT_COLOR.darker()
        );
        panel.setBorder(BorderFactory.createCompoundBorder(
            titledBorder,
            new EmptyBorder(10, 10, 10, 10)
        ));
        return panel;
    }

    /**
     * <p>
     * Creates a JLabel to serve as a color preview, with a specific size and border,
     * showing the given color as its background.
     * </p>
     *
     * <p>
     * Crée un JLabel pour servir de prévisualisation de couleur, avec une taille et une bordure spécifiques,
     * affichant la couleur donnée comme arrière-plan.
     * </p>
     *
     * @param color The color to display in the preview.
     * @return A styled JLabel for color preview.
     */
    private JLabel createColorPreviewLabelLifestyle(Color color) {
        JLabel previewLabel = new JLabel();
        previewLabel.setOpaque(true);
        previewLabel.setBackground(color);
        previewLabel.setPreferredSize(new Dimension(60, 30));
        previewLabel.setBorder(BorderFactory.createLineBorder(Constants.LIFESTYLE_BORDER_COLOR.darker(), 1));
        return previewLabel;
    }

    /**
     * <p>
     * Creates a JLabel with dark text and a specific font, suitable for general labels.
     * </p>
     *
     * <p>
     * Crée un JLabel avec du texte foncé et une police spécifique, adapté aux étiquettes générales.
     * </p>
     *
     * @param text The text for the label.
     * @return A styled JLabel.
     */
    private JLabel createDarkLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Constants.LIFESTYLE_TEXT_COLOR);
        label.setFont(Constants.TIMER_INPUT_FONT);
        return label;
    }

    /**
     * <p>
     * Displays an error dialog to the user with the given message.
     * </p>
     *
     * <p>
     * Affiche une boîte de dialogue d'erreur à l'utilisateur avec le message donné.
     * </p>
     *
     * @param message The error message to display.
     */
    private void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(this, message, Constants.DIALOG_TITLE_ERROR, JOptionPane.ERROR_MESSAGE);
    }

    /**
     * <p>
     * Displays an information dialog to the user with the given message.
     * </p>
     *
     * <p>
     * Affiche une boîte de dialogue d'informations à l'utilisateur avec le message donné.
     * </p>
     *
     * @param message The information message to display.
     */
    private void showInfoDialog(String message) {
        JOptionPane.showMessageDialog(this, message, Constants.DIALOG_TITLE_INFO, JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * <p>
     * Displays a warning dialog to the user with the given message.
     * </p>
     *
     * <p>
     * Affiche une boîte de dialogue d'avertissement à l'utilisateur avec le message donné.
     * </p>
     *
     * @param message The warning message to display.
     */
    private void showWarningDialog(String message) {
        JOptionPane.showMessageDialog(this, message, Constants.DIALOG_TITLE_WARNING, JOptionPane.WARNING_MESSAGE);
    }

    /**
     * <p>
     * Handles the window closing event. Flushes preferences, cancels all running timers,
     * closes the alarm sound clip, and exits the application cleanly.
     * </p>
     *
     * <p>
     * Gère l'événement de fermeture de la fenêtre. Vide les préférences, annule tous les minuteurs en cours,
     * ferme le clip sonore de l'alarme et quitte l'application proprement.
     * </p>
     */
    private void handleWindowClosing() {
        flushPreferences();

        // Cancel all active timers to prevent threads from running after dispose
        if (mainClockUpdateTimer != null) mainClockUpdateTimer.cancel();
        if (alarmCheckTimer != null) alarmCheckTimer.cancel();
        if (stopwatchTimer != null) stopwatchTimer.cancel();
        if (countdownTimer != null) countdownTimer.cancel();

        // Stop animation timers if running
        if (dedicationAnimationTimer != null && dedicationAnimationTimer.isRunning()) {
            dedicationAnimationTimer.stop();
        }
        if (tabSectionCollapseExpandTimer != null && tabSectionCollapseExpandTimer.isRunning()) {
            tabSectionCollapseExpandTimer.stop();
        }

        // Close audio resources
        if (reusableAlarmSoundClip != null && reusableAlarmSoundClip.isOpen()) {
            reusableAlarmSoundClip.close();
        }

        dispose(); // Release all AWT resources used by this Frame
        System.exit(0); // Terminate the JVM
    }

    /**
     * <p>
     * A static nested class representing an alarm entry.
     * Includes alarm time, message, repetition settings (daily or specific date),
     * and snooze status.
     * Implements `equals` and `hashCode` for proper comparison in collections.
     * </p>
     *
     * <p>
     * Une classe imbriquée statique représentant une entrée d'alarme.
     * Comprend l'heure de l'alarme, le message, les paramètres de répétition (quotidienne ou date spécifique)
     * et l'état de report.
     * Implémente `equals` et `hashCode` pour une comparaison correcte dans les collections.
     * </p>
     */
    private static class Alarm {
        private final String time;
        private final String message;
        private final boolean repeatDaily;
        private boolean triggeredToday;
        private final boolean specificDate;
        private final LocalDate alarmDate;
        private Instant snoozedUntil;

        /**
         * <p>
         * Constructs an Alarm object with the specified details.
         * Performs basic validation on input parameters.
         * </p>
         *
         * <p>
         * Construit un objet Alarme avec les détails spécifiés.
         * Effectue une validation de base sur les paramètres d'entrée.
         * </p>
         *
         * @param time The alarm time in "HH:mm" format.
         * @param message The message to display when the alarm triggers.
         * @param repeatDaily True if the alarm should repeat daily, false otherwise.
         * @param specificDate True if the alarm is for a specific date, false otherwise.
         * @param alarmDate The specific date for the alarm, if `specificDate` is true. Can be null if `repeatDaily` is true.
         * @throws IllegalArgumentException If time format is invalid, message is empty, or alarm configuration is inconsistent.
         */
        public Alarm(String time, String message, boolean repeatDaily, boolean specificDate, LocalDate alarmDate) {
            if (time == null || !time.matches(Constants.ALARM_TIME_FORMAT_REGEX)) {
                throw new IllegalArgumentException(Constants.ERROR_INVALID_TIME + " (reçu: " + time + ")");
            }
            if (message == null || message.trim().isEmpty()){
                throw new IllegalArgumentException(Constants.ERROR_EMPTY_MESSAGE);
            }
            if (!repeatDaily && !specificDate) {
                 throw new IllegalArgumentException("Une alarme doit être soit quotidienne, soit à date spécifique.");
            }
            if (specificDate && alarmDate == null) {
                 throw new IllegalArgumentException("Une alarme spécifique doit avoir une date.");
            }

            this.time = time;
            this.message = message.trim();
            this.repeatDaily = repeatDaily;
            this.specificDate = specificDate;
            this.alarmDate = alarmDate;
            this.triggeredToday = false;
            this.snoozedUntil = null;
        }

        public String getTime() { return time; }
        public String getMessage() { return message; }
        public boolean isRepeatDaily() { return repeatDaily; }
        public boolean isSpecificDate() { return specificDate; }
        public LocalDate getAlarmDate() { return alarmDate; }
        public boolean isTriggeredToday() { return triggeredToday; }
        public void setTriggeredToday(boolean triggered) { this.triggeredToday = triggered; }
        public Instant getSnoozedUntil() { return snoozedUntil; }
        public void setSnoozedUntil(Instant snoozedUntil) { this.snoozedUntil = snoozedUntil; }

        /**
         * <p>
         * Returns a string representation of the Alarm, including time, message,
         * repetition info (Daily or specific date), and snooze status.
         * </p>
         *
         * <p>
         * Renvoie une représentation textuelle de l'Alarme, incluant l'heure, le message,
         * les informations de répétition (Quotidienne ou date spécifique) et l'état de report.
         * </p>
         *
         * @return A formatted string representing the alarm.
         */
        @Override
        public String toString() {
            String dateInfo = "";
            if (specificDate && alarmDate != null) {
                dateInfo = " (" + alarmDate.format(DateTimeFormatter.ofPattern(Constants.ALARM_DATE_FORMAT)) + ")";
            } else if (repeatDaily) {
                dateInfo = " (Quotidien)";
            }
            return String.format("%s - %s%s%s", time, message, dateInfo, (snoozedUntil != null ? " (Snooze)" : ""));
        }

        /**
         * <p>
         * Compares this Alarm object with another object for equality.
         * Two alarms are considered equal if their time, message, daily repetition,
         * specific date flag, and specific alarm date are identical. Snooze status is ignored for equality.
         * </p>
         *
         * <p>
         * Compare cet objet Alarme avec un autre objet pour l'égalité.
         * Deux alarmes sont considérées égales si leur heure, message, répétition quotidienne,
         * indicateur de date spécifique et date d'alarme sont identiques. L'état de report est ignoré pour l'égalité.
         * </p>
         *
         * @param o The object to compare with.
         * @return True if the objects are equal, false otherwise.
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Alarm alarm = (Alarm) o;
            return repeatDaily == alarm.repeatDaily &&
                   specificDate == alarm.specificDate &&
                   time.equals(alarm.time) &&
                   message.equals(alarm.message) &&
                   java.util.Objects.equals(alarmDate, alarm.alarmDate);
        }

        /**
         * <p>
         * Generates a hash code for this Alarm object based on its time, message,
         * daily repetition, specific date, and alarm date.
         * </p>
         *
         * <p>
         * Génère un code de hachage pour cet objet Alarme basé sur son heure, son message,
         * sa répétition quotidienne, sa date spécifique et sa date d'alarme.
         * </p>
         *
         * @return The hash code.
         */
        @Override
        public int hashCode() {
            int result = time.hashCode();
            result = 31 * result + message.hashCode();
            result = 31 * result + (repeatDaily ? 1 : 0);
            result = 31 * result + (specificDate ? 1 : 0);
            result = 31 * result + (alarmDate != null ? alarmDate.hashCode() : 0);
            return result;
        }
    }

    /**
     * <p>
     * A static nested class that extends JPanel to provide a custom background
     * image rendering. The image is scaled to cover the panel while maintaining aspect ratio,
     * and centered. A fallback background is drawn if the image cannot be loaded.
     * </p>
     *
     * <p>
     * Une classe imbriquée statique qui étend JPanel pour fournir un rendu d'image
     * d'arrière-plan personnalisé. L'image est mise à l'échelle pour couvrir le panneau tout
     * en conservant le rapport d'aspect, et centrée. Un arrière-plan de secours est dessiné
     * si l'image ne peut pas être chargée.
     * </p>
     */
    private static class BackgroundPanel extends JPanel {
        private Image backgroundImage;
        private String currentImageUrl;

        /**
         * <p>
         * Constructs a BackgroundPanel with a specified image and its URL/path.
         * Sets the panel to be non-opaque so the background image is visible.
         * </p>
         *
         * <p>
         * Construit un BackgroundPanel avec une image spécifiée et son URL/chemin.
         * Rend le panneau non opaque afin que l'image d'arrière-plan soit visible.
         * </p>
         *
         * @param image The Image object to display.
         * @param imageUrl The URL or file path of the image.
         */
        public BackgroundPanel(Image image, String imageUrl) {
            this.backgroundImage = image;
            this.currentImageUrl = imageUrl;
            setOpaque(false);
        }

        /**
         * <p>
         * Sets a new background image and its URL/path for the panel, then repaints the component.
         * </p>
         *
         * <p>
         * Définit une nouvelle image d'arrière-plan et son URL/chemin pour le panneau, puis repeint le composant.
         * </p>
         *
         * @param newImage The new Image to display.
         * @param newImageUrl The URL or path of the new image.
         */
        public void setImage(Image newImage, String newImageUrl) {
            this.backgroundImage = newImage;
            this.currentImageUrl = newImageUrl;
            repaint();
        }

        /**
         * <p>
         * Returns the URL or path of the current background image.
         * </p>
         *
         * <p>
         * Renvoie l'URL ou le chemin de l'image d'arrière-plan actuelle.
         * </p>
         *
         * @return The URL or path of the current background image.
         */
        public String getImageUrl() { return currentImageUrl; }

        /**
         * <p>
         * Overrides the `paintComponent` method to draw the background image scaled and centered.
         * If the image is not available or invalid, a fallback background is drawn.
         * Includes rendering hints for better image quality.
         * </p>
         *
         * <p>
         * Remplace la méthode `paintComponent` pour dessiner l'image d'arrière-plan redimensionnée et centrée.
         * Si l'image n'est pas disponible ou invalide, un arrière-plan de secours est dessiné.
         * Inclut des astuces de rendu pour une meilleure qualité d'image.
         * </p>
         *
         * @param g The Graphics context to use for painting.
         */
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                Graphics2D g2d = (Graphics2D) g.create();

                int panelWidth = getWidth();
                int panelHeight = getHeight();
                int imgWidth = backgroundImage.getWidth(this);
                int imgHeight = backgroundImage.getHeight(this);

                if (imgWidth <= 0 || imgHeight <= 0) {
                    g2d.dispose();
                    drawFallbackBackground(g);
                    return;
                }

                double imgRatio = (double) imgWidth / imgHeight;
                double panelRatio = (double) panelWidth / panelHeight;

                int scaledWidth;
                int scaledHeight;

                // Scale image to cover the panel while maintaining aspect ratio
                if (panelRatio > imgRatio) {
                    scaledWidth = panelWidth;
                    scaledHeight = (int) (panelWidth / imgRatio);
                } else {
                    scaledHeight = panelHeight;
                    scaledWidth = (int) (panelHeight * imgRatio);
                }

                int x = (panelWidth - scaledWidth) / 2;
                int y = (panelHeight - scaledHeight) / 2;

                // Apply rendering hints for quality
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2d.drawImage(backgroundImage, x, y, scaledWidth, scaledHeight, this);
                g2d.dispose();
            } else {
                drawFallbackBackground(g);
            }
        }

        /**
         * <p>
         * Draws a fallback dark grey background with a message if the background image is not available.
         * </p>
         *
         * <p>
         * Dessine un arrière-plan gris foncé avec un message si l'image d'arrière-plan n'est pas disponible.
         * </p>
         *
         * @param g The Graphics context to use for painting.
         */
        private void drawFallbackBackground(Graphics g) {
            g.setColor(new Color(30, 30, 30));
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(Color.LIGHT_GRAY);
            g.setFont(new Font("SansSerif", Font.ITALIC, 16));
            String errorMsg = "Image de fond non disponible";
            FontMetrics fm = g.getFontMetrics();
            int msgWidth = fm.stringWidth(errorMsg);
            int msgHeight = fm.getAscent();
            g.drawString(errorMsg, (getWidth() - msgWidth) / 2, (getHeight() - msgHeight) / 2 + msgHeight);
        }
    }

    /**
     * <p>
     * The main entry point for the FuturisticClockApp application.
     * Sets the preferred Look and Feel (Nimbus if available, otherwise system default)
     * and initializes the application on the AWT Event Dispatch Thread.
     * </p>
     *
     * <p>
     * Le point d'entrée principal de l'application FuturisticClockApp.
     * Définit le Look and Feel préféré (Nimbus si disponible, sinon celui du système)
     * et initialise l'application sur le thread de distribution d'événements AWT.
     * </p>
     *
     * @param args Command line arguments (not used in this application).
     */
    public static void main(String[] args) {
        try {
            boolean nimbusFound = false;
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    nimbusFound = true;
                    break;
                }
            }
            if (!nimbusFound) {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            }
        } catch (Exception e) {
            System.err.println("Impossible de définir le Look and Feel : " + e.getMessage());
        }
        SwingUtilities.invokeLater(FuturisticClockApp::new);
    }
}