import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;
import java.net.URL;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.Timer; // Timer Swing pour les animations UI

import java.util.prefs.Preferences;
import java.util.StringTokenizer;
import javax.swing.filechooser.FileFilter;

/**
 * Cette classe représente une application d'horloge numérique professionnelle et élégante
 * avec une image de fond centrée (inspirée de Halo), compatible avec Java 6.
 * Elle intègre des fonctionnalités d'alarme, de chronomètre, de minuteur,
 * de personnalisation du thème et d'affichage de multiples fuseaux horaires.
 */
public class FuturisticClockApp extends JFrame {

    // MODIFICATION: Ajout d'une variable pour la position du clic initial pour le déplacement
    private Point initialClick;
    private JButton closeButton; // MODIFICATION: Ajout du bouton de fermeture

    private static class Constants {
        // ... (Le reste de la classe Constants reste inchangé) ...
        static final String APP_TITLE = "Horloge d'Entreprise Futuriste - Édition Halo";
        static final String DEDICATION_TEXT = "Ce logiciel a été conçu avec passion par RTN alias Samyn-Antoy ABASSE";
        static final String CLOCK_FEATURES_TITLE = "Fonctionnalités de l'Horloge";

        // URLs et Chemins
        static final String DEFAULT_BACKGROUND_IMAGE_URL = "https://image.noelshack.com/fichiers/2025/22/5/1748608028-wallpaperflare-com-wallpaper.jpg";
        static final String DEFAULT_ALARM_SOUND_URL = "http://soundbible.com/grab.php?id=2028&type=wav";

        // Clés de Préférences
        static final String PREF_IMAGE_URL = "imageUrl";
        static final String PREF_TEXT_COLOR = "textColor";
        static final String PREF_BG_COLOR = "bgColor";
        static final String PREF_TAB_SECTION_EXPANDED_HEIGHT = "tabSectionExpandedHeight";
        static final String PREF_IS_TAB_SECTION_EXPANDED = "isTabSectionExpanded";
        static final String PREF_ALARMS = "alarms";
        static final String PREF_DEFAULT_RINGTONE_PATH = "defaultRingtonePath";
        static final String PREF_LAST_SOUND_DIRECTORY = "lastSoundDirectory";

        // Dimensions UI et Animation
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

        // Formats de temps
        static final String MAIN_TIME_FORMAT = "HH:mm:ss";
        static final String DATE_FORMAT = "dd MMMM yyyy"; // Changé de GGGG à yyyy pour compatibilité universelle
        static final String ALARM_TIME_FORMAT_REGEX = "\\d{2}:\\d{2}";

        // Couleurs Thème Principal (Sombre pour l'horloge)
        static final Color DEFAULT_TEXT_COLOR = Color.WHITE;
        // Correction: Fond de l'horloge plus opaque
        static final Color DEFAULT_CLOCK_BG_COLOR = new Color(10, 10, 10, 230); // Alpha à 230 (était 200)
        static final Color DEDICATION_TEXT_COLOR = new Color(170, 190, 220, 220);

        // Couleurs Thème "Lifestyle" Chaleureux pour la Section Fonctionnalités (Plus clair)
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

        static final String ALARMS_TAB_TITLE = "Alarmes";
        static final String STOPWATCH_TAB_TITLE = "Chronomètre";
        static final String TIMER_TAB_TITLE = "Minuteur";
        static final String SETTINGS_TAB_TITLE = "Paramètres";
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
    }

    private JLabel timeLabel;
    private BackgroundPanel backgroundPanel;
    private JTabbedPane tabbedPane;
    private JLabel dedicationLabel;
    private JPanel tabSectionCollapsiblePanel;
    private JLabel tabSectionHeader;

    private JPanel alarmsPanel;
    private JList<Alarm> alarmList;
    private DefaultListModel<Alarm> alarmListModel;
    private JTextField alarmTimeField;
    private JTextField alarmMessageField;
    private JCheckBox alarmRepeatDaily;
    private JButton addAlarmButton;
    private JButton removeAlarmButton;

    private JPanel stopwatchPanel;
    private JLabel stopwatchDisplay;
    private JButton startStopwatchButton;
    private JButton stopStopwatchButton;
    private JButton resetStopwatchButton;

    private JPanel timerPanel;
    private JLabel timerDisplay;
    private JTextField timerInputHours;
    private JTextField timerInputMinutes;
    private JTextField timerInputSeconds;
    private JButton startTimerButton;
    private JButton stopTimerButton;
    private JButton resetTimerButton;

    private JPanel settingsPanel;
    private JTextField imageUrlField;
    private JButton changeImageButton;
    private JButton chooseTextColorButton;
    private JButton chooseClockBgColorButton;
    private JTextField localSoundPathField;
    private JButton chooseSoundButton;

    private SimpleDateFormat mainTimeFormatter;
    private SimpleDateFormat dateFormatter;
    private SimpleDateFormat alarmTimeCheckerFormatter;

    private Timer dedicationAnimationTimer; 
    private Timer tabSectionCollapseExpandTimer; 

    private java.util.Timer mainClockUpdateTimer; 
    private java.util.Timer alarmCheckTimer;      
    private java.util.Timer stopwatchTimer;       
    private java.util.Timer countdownTimer;       

    private int dedicationTargetX;
    private int dedicationCurrentX;
    private boolean isTabSectionExpanded;
    private int tabSectionExpandedHeight; 
    private String currentAlarmSoundPath;
    private Clip reusableAlarmSoundClip;
    private long stopwatchStartTime;
    private long stopwatchElapsedTime;
    private boolean stopwatchRunning;
    private long countdownRemainingTime;
    private boolean countdownRunning;
    private Color currentTextColor;
    private Color currentClockBgColor;

    private Preferences prefs;

    public FuturisticClockApp() {
        super(Constants.APP_TITLE);

        initializePreferences();
        loadCorePreferences(); 

        setupFrame();
        initializeFormatters();

        JLayeredPane layeredPane = createLayeredPane();
        initializeBackgroundPanel(layeredPane); 
        // MODIFICATION: Initialiser le bouton de fermeture
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

    private void initializePreferences() {
        prefs = Preferences.userNodeForPackage(FuturisticClockApp.class);
    }

    private void loadCorePreferences() {
        tabSectionExpandedHeight = prefs.getInt(Constants.PREF_TAB_SECTION_EXPANDED_HEIGHT, Constants.DEFAULT_TAB_SECTION_EXPANDED_HEIGHT);
        isTabSectionExpanded = prefs.getBoolean(Constants.PREF_IS_TAB_SECTION_EXPANDED, true);
        currentTextColor = new Color(prefs.getInt(Constants.PREF_TEXT_COLOR, Constants.DEFAULT_TEXT_COLOR.getRGB()));
        currentClockBgColor = new Color(prefs.getInt(Constants.PREF_BG_COLOR, Constants.DEFAULT_CLOCK_BG_COLOR.getRGB()), true); 
        currentAlarmSoundPath = prefs.get(Constants.PREF_DEFAULT_RINGTONE_PATH, Constants.DEFAULT_ALARM_SOUND_URL);
    }

    private void setupFrame() {
        // MODIFICATION: Supprimer les décorations de la fenêtre et rendre le fond transparent
        setUndecorated(true);
        setBackground(new Color(0, 0, 0, 0));

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); 
        setMinimumSize(Constants.MINIMUM_WINDOW_SIZE);
        setSize(Constants.INITIAL_WINDOW_SIZE);
        setLocationRelativeTo(null);
    }

    private void initializeFormatters() {
        mainTimeFormatter = new SimpleDateFormat(Constants.MAIN_TIME_FORMAT);
        dateFormatter = new SimpleDateFormat(Constants.DATE_FORMAT, java.util.Locale.FRENCH);
        alarmTimeCheckerFormatter = new SimpleDateFormat("HH:mm"); 
        alarmTimeCheckerFormatter.setLenient(false); 
    }

    private JLayeredPane createLayeredPane() {
        JLayeredPane layeredPane = new JLayeredPane();
        // MODIFICATION: Le contentPane doit aussi être transparent
        layeredPane.setOpaque(false);
        getContentPane().add(layeredPane, BorderLayout.CENTER);
        return layeredPane;
    }

    private void initializeBackgroundPanel(JLayeredPane layeredPane) {
        String persistedImageUrl = prefs.get(Constants.PREF_IMAGE_URL, Constants.DEFAULT_BACKGROUND_IMAGE_URL);
        Image image = loadImage(persistedImageUrl);
        if (image == null && !persistedImageUrl.equals(Constants.DEFAULT_BACKGROUND_IMAGE_URL)) {
            System.err.println("Failed to load persisted image: " + persistedImageUrl + ". Attempting default.");
            image = loadImage(Constants.DEFAULT_BACKGROUND_IMAGE_URL);
            if (image != null) { 
                 prefs.put(Constants.PREF_IMAGE_URL, Constants.DEFAULT_BACKGROUND_IMAGE_URL);
                 flushPreferences();
                 persistedImageUrl = Constants.DEFAULT_BACKGROUND_IMAGE_URL; // Mettre à jour pour le constructeur de BackgroundPanel
            }
        }
        // Utiliser persistedImageUrl mis à jour si l'image par défaut a été chargée en fallback
        backgroundPanel = new BackgroundPanel(image, persistedImageUrl);
        layeredPane.add(backgroundPanel, JLayeredPane.DEFAULT_LAYER);
    }

    // MODIFICATION: Nouvelle méthode pour créer et configurer le bouton de fermeture
    private void initializeCloseButton(JLayeredPane layeredPane) {
        closeButton = new JButton("×"); // Utilisation d'un 'X' plus élégant
        closeButton.setOpaque(false);
        closeButton.setContentAreaFilled(false);
        closeButton.setBorderPainted(false);
        closeButton.setForeground(new Color(255, 255, 255, 180)); // Blanc semi-transparent
        closeButton.setFont(new Font("Arial", Font.BOLD, 32));
        closeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeButton.setFocusable(false);

        // Effet de survol
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

        layeredPane.add(closeButton, JLayeredPane.MODAL_LAYER); // Placer sur une couche élevée
    }

    private void initializeTimeLabel(JLayeredPane layeredPane) {
        timeLabel = new JLabel(" ", SwingConstants.CENTER); 
        timeLabel.setForeground(currentTextColor);
        timeLabel.setFont(Constants.CLOCK_FONT_BASE); 
        timeLabel.setVerticalAlignment(SwingConstants.CENTER);
        timeLabel.setOpaque(true);
        timeLabel.setBackground(currentClockBgColor);
        layeredPane.add(timeLabel, JLayeredPane.PALETTE_LAYER); // PALETTE_LAYER est au-dessus de DEFAULT_LAYER
    }

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

        alarmsPanel.setBackground(Constants.LIFESTYLE_CONTENT_BG);
        stopwatchPanel.setBackground(Constants.LIFESTYLE_CONTENT_BG);
        timerPanel.setBackground(Constants.LIFESTYLE_CONTENT_BG);
        settingsPanel.setBackground(Constants.LIFESTYLE_CONTENT_BG);

        tabbedPane.addTab(Constants.ALARMS_TAB_TITLE, alarmsPanel);
        tabbedPane.addTab(Constants.STOPWATCH_TAB_TITLE, stopwatchPanel);
        tabbedPane.addTab(Constants.TIMER_TAB_TITLE, timerPanel);
        tabbedPane.addTab(Constants.SETTINGS_TAB_TITLE, settingsPanel);

        tabbedPane.addChangeListener(e -> {
            for (int i = 0; i < tabbedPane.getTabCount(); i++) {
                if (i == tabbedPane.getSelectedIndex()) {
                    tabbedPane.setForegroundAt(i, Constants.LIFESTYLE_TAB_TEXT_SELECTED);
                } else {
                    tabbedPane.setForegroundAt(i, Constants.LIFESTYLE_TAB_TEXT_UNSELECTED);
                }
            }
        });
        
        if (tabbedPane.getTabCount() > 0) {
            for (int i = 0; i < tabbedPane.getTabCount(); i++) {
                 if (i == tabbedPane.getSelectedIndex()) {
                    tabbedPane.setForegroundAt(i, Constants.LIFESTYLE_TAB_TEXT_SELECTED);
                } else {
                    tabbedPane.setForegroundAt(i, Constants.LIFESTYLE_TAB_TEXT_UNSELECTED);
                }
            }
        }
    }

    private int calculateTabSectionExpandedHeight() {
        tabbedPane.validate(); 
        int preferredTabHeight = tabbedPane.getPreferredSize().height;
        if (preferredTabHeight <= 0) { 
            return Constants.DEFAULT_TAB_SECTION_EXPANDED_HEIGHT;
        }
        return Constants.TAB_SECTION_COLLAPSED_HEIGHT + preferredTabHeight + Constants.TAB_SECTION_CONTENT_PADDING;
    }


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

    private void initializeDedicationLabel(JLayeredPane layeredPane) {
        dedicationLabel = new JLabel(Constants.DEDICATION_TEXT);
        dedicationLabel.setForeground(Constants.DEDICATION_TEXT_COLOR);
        dedicationLabel.setFont(Constants.DEDICATION_FONT);
        dedicationLabel.setHorizontalAlignment(SwingConstants.LEFT);
        dedicationLabel.setOpaque(false);
        layeredPane.add(dedicationLabel, JLayeredPane.MODAL_LAYER); 
    }

    private void registerListeners() {
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent evt) {
                SwingUtilities.invokeLater(FuturisticClockApp.this::adjustComponentSizes);
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                handleWindowClosing();
            }
        });

        tabSectionHeader.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                toggleTabSectionCollapsiblePanel();
            }
        });
        
        // MODIFICATION: Ajouter un listener pour fermer la fenêtre via le nouveau bouton
        if (closeButton != null) {
            closeButton.addActionListener(e -> handleWindowClosing());
        }

        // MODIFICATION: Ajouter des listeners au panneau de fond pour permettre le déplacement de la fenêtre
        MouseAdapter windowDragListener = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                initialClick = e.getPoint(); // Capturer le point de clic initial dans le composant
            }
        };

        MouseMotionAdapter windowMotionListener = new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                Point currentLocationOnScreen = e.getLocationOnScreen();
                // Calcule et définit la nouvelle position du coin supérieur gauche de la fenêtre
                setLocation(currentLocationOnScreen.x - initialClick.x, currentLocationOnScreen.y - initialClick.y);
            }
        };

        backgroundPanel.addMouseListener(windowDragListener);
        backgroundPanel.addMouseMotionListener(windowMotionListener);
    }

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

    private void applyInitialComponentSizes() {
        SwingUtilities.invokeLater(this::adjustComponentSizes);
    }

    private void adjustComponentSizes() {
        if (backgroundPanel == null || timeLabel == null || tabSectionCollapsiblePanel == null || dedicationLabel == null || tabbedPane == null) {
            return;
        }
        int frameWidth = getWidth();
        int frameHeight = getHeight();

        backgroundPanel.setBounds(0, 0, frameWidth, frameHeight);

        // MODIFICATION: Positionner le bouton de fermeture en haut à droite
        if (closeButton != null) {
            closeButton.setBounds(frameWidth - 50, 5, 45, 45);
        }

        if (isTabSectionExpanded) {
            tabSectionExpandedHeight = calculateTabSectionExpandedHeight();
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
        
        // --- Horloge principale ---
        int clockFrameX = 0; 
        
        int clockFrameWidth = tabSectionPanelX - Constants.GLOBAL_MARGIN - clockFrameX; 
        clockFrameWidth = Math.max(clockFrameWidth, Constants.MIN_CLOCK_WIDTH + Constants.CLOCK_FRAME_HORIZONTAL_PADDING*2);

        int clockFrameHeight = (int) (frameHeight * Constants.CLOCK_HEIGHT_RATIO); 
        clockFrameHeight = Math.max(clockFrameHeight, Constants.MIN_CLOCK_HEIGHT + Constants.CLOCK_FRAME_VERTICAL_PADDING*2);

        int clockCenterY = frameHeight / 2;
        int clockFrameY = clockCenterY - (clockFrameHeight / 2); 
        clockFrameY = Math.max(Constants.GLOBAL_MARGIN, clockFrameY); 
        if (clockFrameY + clockFrameHeight + Constants.GLOBAL_MARGIN > frameHeight) { 
            clockFrameY = frameHeight - clockFrameHeight - Constants.GLOBAL_MARGIN;
        }

        timeLabel.setBounds(clockFrameX, clockFrameY, clockFrameWidth, clockFrameHeight);

        int effectiveHeightForFont = Math.min(timeLabel.getHeight(), clockFrameHeight - Constants.CLOCK_FRAME_VERTICAL_PADDING * 2);
        int newTimeFontSize = (int) (effectiveHeightForFont * Constants.TIME_FONT_SIZE_RATIO);
        newTimeFontSize = Math.max(newTimeFontSize, Constants.MIN_TIME_FONT_SIZE);
        timeLabel.setFont(Constants.CLOCK_FONT_BASE.deriveFont((float)newTimeFontSize));


        // --- Dédicace ---
        int dedicationWidth = dedicationLabel.getFontMetrics(Constants.DEDICATION_FONT).stringWidth(Constants.DEDICATION_TEXT) + 20; 
        int targetYDedication = frameHeight - Constants.DEDICATION_LABEL_HEIGHT - Constants.DEDICATION_MARGIN;
        
        dedicationTargetX = Constants.GLOBAL_MARGIN; 

        if (dedicationAnimationTimer == null || !dedicationAnimationTimer.isRunning()) {
            dedicationLabel.setBounds(dedicationTargetX, targetYDedication, dedicationWidth, Constants.DEDICATION_LABEL_HEIGHT);
            dedicationCurrentX = dedicationTargetX;
        } else {
            dedicationLabel.setBounds(dedicationLabel.getX(), targetYDedication, dedicationWidth, Constants.DEDICATION_LABEL_HEIGHT);
        }
        
        if (getContentPane().getComponentCount() > 0 && getContentPane().getComponent(0) instanceof JLayeredPane) {
            JLayeredPane lp = (JLayeredPane) getContentPane().getComponent(0);
            lp.revalidate();
            lp.repaint();
        } else {
            revalidate(); 
            repaint();
        }
    }

    // ... Le reste de votre code (updateTime, animations, gestion des alarmes, etc.) reste inchangé ...
    // ... Collez ici la suite de votre classe, de "private void updateTime()" jusqu'à la fin.
    // ... La classe `BackgroundPanel` et la méthode `main` n'ont pas besoin d'être modifiées.
    private void updateTime() {
        Date now = new Date();
        String timeStr = mainTimeFormatter.format(now);
        String dateStr = dateFormatter.format(now);
        timeLabel.setText("<html><center>" + timeStr + "<br><font size='-2'>" + dateStr.toUpperCase() + "</font></center></html>");
    }

    private void startDedicationAnimation() {
        int dedicationWidth = dedicationLabel.getFontMetrics(Constants.DEDICATION_FONT).stringWidth(Constants.DEDICATION_TEXT) + 40; 
        dedicationTargetX = Constants.GLOBAL_MARGIN; 
        dedicationCurrentX = Constants.GLOBAL_MARGIN - dedicationWidth; 
        
        // Assurer que initialY est calculé par rapport à la hauteur actuelle de la fenêtre si disponible
        int currentFrameHeight = getHeight() > 0 ? getHeight() : Constants.INITIAL_WINDOW_SIZE.height;
        int initialY = currentFrameHeight - Constants.DEDICATION_LABEL_HEIGHT - Constants.DEDICATION_MARGIN;
      
        dedicationLabel.setLocation(dedicationCurrentX, initialY);

        if (dedicationAnimationTimer != null && dedicationAnimationTimer.isRunning()) {
            dedicationAnimationTimer.stop();
        }
        dedicationAnimationTimer = new Timer(Constants.DEDICATION_ANIMATION_DELAY, e -> animateDedicationLabel());
        dedicationAnimationTimer.start();
    }

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

    private void toggleTabSectionCollapsiblePanel() {
        if (tabSectionCollapseExpandTimer != null && tabSectionCollapseExpandTimer.isRunning()) {
            return; 
        }

        isTabSectionExpanded = !isTabSectionExpanded;
        updateTabSectionHeaderIcon(); 

        final int startHeight = tabSectionCollapsiblePanel.getHeight();
        final int endHeight;

        if (isTabSectionExpanded) {
            tabSectionExpandedHeight = calculateTabSectionExpandedHeight(); 
            endHeight = tabSectionExpandedHeight;
            tabbedPane.setVisible(true); 
        } else {
            endHeight = Constants.TAB_SECTION_COLLAPSED_HEIGHT;
        }

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

    private void updateTabSectionHeaderIcon() {
        String arrow = isTabSectionExpanded ? "\u25B2" : "\u25BC"; // Flèches haut/bas
        tabSectionHeader.setText("<html><b>" + Constants.CLOCK_FEATURES_TITLE + "</b> <span style='font-size:14px;'>" + arrow + "</span></html>");
    }

    private void saveTabSectionState() {
        prefs.putInt(Constants.PREF_TAB_SECTION_EXPANDED_HEIGHT, (tabSectionExpandedHeight > Constants.TAB_SECTION_COLLAPSED_HEIGHT) ? tabSectionExpandedHeight : Constants.DEFAULT_TAB_SECTION_EXPANDED_HEIGHT);
        prefs.putBoolean(Constants.PREF_IS_TAB_SECTION_EXPANDED, isTabSectionExpanded);
        flushPreferences();
    }

    private void initAlarmsPanel() {
        alarmsPanel = new JPanel(new BorderLayout(18,18)); 
        alarmsPanel.setOpaque(true); 
        alarmsPanel.setBackground(Constants.LIFESTYLE_CONTENT_BG); 
        alarmsPanel.setBorder(new EmptyBorder(30, 30, 30, 30)); 

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

        JLabel timeTextLabel = new JLabel(Constants.LABEL_TIME_HHMM);
        timeTextLabel.setForeground(Constants.LIFESTYLE_TEXT_COLOR);
        timeTextLabel.setFont(Constants.LABEL_FONT);
        alarmTimeField = new JTextField(Constants.DEFAULT_ALARM_TIME, 5); 
        styleInputFieldLifestyle(alarmTimeField);

        JLabel messageTextLabel = new JLabel(Constants.LABEL_MESSAGE);
        messageTextLabel.setForeground(Constants.LIFESTYLE_TEXT_COLOR);
        messageTextLabel.setFont(Constants.LABEL_FONT);
        alarmMessageField = new JTextField(Constants.DEFAULT_ALARM_MESSAGE, 15); 
        styleInputFieldLifestyle(alarmMessageField);

        alarmRepeatDaily = new JCheckBox(Constants.CHECKBOX_REPEAT_DAILY);
        alarmRepeatDaily.setOpaque(false);
        alarmRepeatDaily.setForeground(Constants.LIFESTYLE_TEXT_COLOR);
        alarmRepeatDaily.setFont(Constants.LABEL_FONT);

        addAlarmButton = new JButton(Constants.BUTTON_ADD_ALARM);
        addAlarmButton.addActionListener(e -> addAlarm());
        styleFeatureButtonLifestyle(addAlarmButton, Constants.BUTTON_ACCENT_GREEN);

        removeAlarmButton = new JButton(Constants.BUTTON_REMOVE_ALARM);
        removeAlarmButton.addActionListener(e -> removeAlarm());
        styleFeatureButtonLifestyle(removeAlarmButton, Constants.BUTTON_ACCENT_RED);

        inputPanel.add(timeTextLabel);
        inputPanel.add(alarmTimeField);
        inputPanel.add(messageTextLabel);
        inputPanel.add(alarmMessageField);
        inputPanel.add(alarmRepeatDaily);
        inputPanel.add(new JLabel()); 
        inputPanel.add(addAlarmButton);
        inputPanel.add(removeAlarmButton);

        alarmsPanel.add(inputPanel, BorderLayout.SOUTH);
    }

    private void initStopwatchPanel() {
        stopwatchPanel = new JPanel(new BorderLayout(18,18));
        stopwatchPanel.setOpaque(true);
        stopwatchPanel.setBackground(Constants.LIFESTYLE_CONTENT_BG);
        stopwatchPanel.setBorder(new EmptyBorder(30, 30, 30, 30));

        stopwatchDisplay = new JLabel("00:00:00.000", SwingConstants.CENTER);
        stopwatchDisplay.setFont(Constants.STOPWATCH_FONT);
        stopwatchDisplay.setForeground(Constants.STOPWATCH_DISPLAY_COLOR);
        JPanel displayWrapper = new JPanel(new GridBagLayout()); 
        displayWrapper.setOpaque(false);
        displayWrapper.add(stopwatchDisplay); 
        stopwatchPanel.add(displayWrapper, BorderLayout.CENTER);


        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 25)); 
        buttonPanel.setOpaque(false);

        startStopwatchButton = new JButton(Constants.BUTTON_START);
        startStopwatchButton.addActionListener(e -> startStopwatch());
        styleFeatureButtonLifestyle(startStopwatchButton, Constants.BUTTON_ACCENT_GREEN);

        stopStopwatchButton = new JButton(Constants.BUTTON_STOP);
        stopStopwatchButton.addActionListener(e -> stopStopwatch());
        styleFeatureButtonLifestyle(stopStopwatchButton, Constants.BUTTON_ACCENT_RED);

        resetStopwatchButton = new JButton(Constants.BUTTON_RESET);
        resetStopwatchButton.addActionListener(e -> resetStopwatch());
        styleFeatureButtonLifestyle(resetStopwatchButton, Constants.BUTTON_ACCENT_BLUE);

        buttonPanel.add(startStopwatchButton);
        buttonPanel.add(stopStopwatchButton);
        buttonPanel.add(resetStopwatchButton);
        stopwatchPanel.add(buttonPanel, BorderLayout.SOUTH);

        resetStopwatch(); 
    }

    private void initTimerPanel() {
        timerPanel = new JPanel(new BorderLayout(18,18));
        timerPanel.setOpaque(true);
        timerPanel.setBackground(Constants.LIFESTYLE_CONTENT_BG);
        timerPanel.setBorder(new EmptyBorder(30, 30, 30, 30));

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

        startTimerButton = new JButton(Constants.BUTTON_START);
        startTimerButton.addActionListener(e -> startCountdown());
        styleFeatureButtonLifestyle(startTimerButton, Constants.BUTTON_ACCENT_GREEN);

        stopTimerButton = new JButton(Constants.BUTTON_STOP);
        stopTimerButton.addActionListener(e -> stopCountdown());
        styleFeatureButtonLifestyle(stopTimerButton, Constants.BUTTON_ACCENT_RED);

        resetTimerButton = new JButton(Constants.BUTTON_RESET);
        resetTimerButton.addActionListener(e -> resetCountdown());
        styleFeatureButtonLifestyle(resetTimerButton, Constants.BUTTON_ACCENT_BLUE);

        buttonControlPanel.add(startTimerButton);
        buttonControlPanel.add(stopTimerButton);
        buttonControlPanel.add(resetTimerButton);
        inputButtonPanel.add(buttonControlPanel, BorderLayout.CENTER); 

        timerPanel.add(inputButtonPanel, BorderLayout.SOUTH);
        resetCountdown(); 
    }

    private void initSettingsPanel() {
        settingsPanel = new JPanel();
        settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.Y_AXIS)); 
        settingsPanel.setOpaque(true);
        settingsPanel.setBackground(Constants.LIFESTYLE_CONTENT_BG);
        settingsPanel.setBorder(new EmptyBorder(30, 30, 30, 30));

        JPanel imagePanel = createSettingSectionPanelLifestyle(Constants.SECTION_TITLE_BACKGROUND_IMAGE);
        imageUrlField = new JTextField(prefs.get(Constants.PREF_IMAGE_URL, Constants.DEFAULT_BACKGROUND_IMAGE_URL));
        styleInputFieldLifestyle(imageUrlField);
        changeImageButton = new JButton(Constants.BUTTON_CHANGE);
        changeImageButton.addActionListener(e -> changeBackgroundImage());
        styleFeatureButtonLifestyle(changeImageButton, Constants.BUTTON_ACCENT_BLUE);
        imagePanel.add(imageUrlField, BorderLayout.CENTER);
        imagePanel.add(changeImageButton, BorderLayout.EAST);
        settingsPanel.add(imagePanel);
        settingsPanel.add(Box.createRigidArea(new Dimension(0, 20))); 

        JPanel textColorPanel = createSettingSectionPanelLifestyle(Constants.SECTION_TITLE_TEXT_COLOR);
        final JLabel textColorPreview = createColorPreviewLabelLifestyle(currentTextColor);
        chooseTextColorButton = new JButton(Constants.BUTTON_CHOOSE_COLOR);
        chooseTextColorButton.addActionListener(e -> {
            Color selectedColor = JColorChooser.showDialog(FuturisticClockApp.this, Constants.SECTION_TITLE_TEXT_COLOR, currentTextColor);
            if (selectedColor != null) {
                currentTextColor = selectedColor;
                timeLabel.setForeground(currentTextColor);
                textColorPreview.setBackground(currentTextColor); 
                saveColorPreferences();
            }
        });
        styleFeatureButtonLifestyle(chooseTextColorButton, Constants.BUTTON_ACCENT_YELLOW);
        JPanel previewContainerText = new JPanel(new FlowLayout(FlowLayout.LEFT, 0,0));
        previewContainerText.setOpaque(false);
        previewContainerText.add(textColorPreview);
        textColorPanel.add(previewContainerText, BorderLayout.WEST); 
        textColorPanel.add(chooseTextColorButton, BorderLayout.CENTER); 
        settingsPanel.add(textColorPanel);
        settingsPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel clockBgColorPanel = createSettingSectionPanelLifestyle(Constants.SECTION_TITLE_CLOCK_BG_COLOR);
        final JLabel clockBgColorPreview = createColorPreviewLabelLifestyle(currentClockBgColor);
        chooseClockBgColorButton = new JButton(Constants.BUTTON_CHOOSE_COLOR);
        chooseClockBgColorButton.addActionListener(e -> {
            // Conserver l'alpha actuel car JColorChooser standard ne le gère pas toujours bien
            int oldAlpha = currentClockBgColor.getAlpha();
            Color selectedColorNoAlpha = JColorChooser.showDialog(FuturisticClockApp.this, Constants.SECTION_TITLE_CLOCK_BG_COLOR, 
                                                               new Color(currentClockBgColor.getRed(), currentClockBgColor.getGreen(), currentClockBgColor.getBlue()));
            
            if (selectedColorNoAlpha != null) {
                currentClockBgColor = new Color(selectedColorNoAlpha.getRed(), 
                                                selectedColorNoAlpha.getGreen(), 
                                                selectedColorNoAlpha.getBlue(), 
                                                oldAlpha); // Appliquer l'ancien alpha à la nouvelle couleur RGB
                timeLabel.setBackground(currentClockBgColor);
                clockBgColorPreview.setBackground(currentClockBgColor);
                saveColorPreferences();
            }
        });
        styleFeatureButtonLifestyle(chooseClockBgColorButton, Constants.BUTTON_ACCENT_YELLOW);
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
        chooseSoundButton = new JButton(Constants.BUTTON_CHOOSE_FILE);
        chooseSoundButton.addActionListener(e -> chooseLocalAlarmSound());
        styleFeatureButtonLifestyle(chooseSoundButton, Constants.BUTTON_ACCENT_BLUE);
        soundPanel.add(localSoundPathField, BorderLayout.CENTER);
        soundPanel.add(chooseSoundButton, BorderLayout.EAST);
        settingsPanel.add(soundPanel);

        settingsPanel.add(Box.createVerticalGlue()); 
    }

    private void addAlarm() {
        String timeStr = alarmTimeField.getText().trim();
        String message = alarmMessageField.getText().trim();
        boolean repeatDaily = alarmRepeatDaily.isSelected();

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

        try {
            Alarm newAlarm = new Alarm(timeStr, message, repeatDaily);
            if (alarmListModel.contains(newAlarm)) {
                showWarningDialog("Cette alarme existe déjà.");
                return;
            }
            alarmListModel.addElement(newAlarm);
            saveAlarmsPreference();
            alarmTimeField.setText(Constants.DEFAULT_ALARM_TIME); 
            alarmMessageField.setText(Constants.DEFAULT_ALARM_MESSAGE);
            alarmRepeatDaily.setSelected(false);
        } catch (IllegalArgumentException e) { 
            showErrorDialog(e.getMessage());
        }
    }

    private void removeAlarm() {
        int selectedIndex = alarmList.getSelectedIndex();
        if (selectedIndex != -1) {
            alarmListModel.remove(selectedIndex);
            saveAlarmsPreference();
        } else {
            showWarningDialog(Constants.WARNING_SELECT_ALARM_TO_REMOVE);
        }
    }

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
        }, 0, 1000); // Vérifie chaque seconde
    }

    private void checkAlarms() {
        if (alarmListModel.isEmpty()) return; 

        String currentTime = alarmTimeCheckerFormatter.format(new Date()); 

        for (int i = alarmListModel.getSize() - 1; i >= 0; i--) { 
            Alarm alarm = alarmListModel.getElementAt(i);
            if (alarm.getTime().equals(currentTime)) {
                if (!alarm.isTriggeredToday()) {
                    alarm.setTriggeredToday(true);
                    playAlarmSound(alarm.getMessage()); 

                    if (!alarm.isRepeatDaily()) {
                        alarmListModel.removeElementAt(i); 
                        saveAlarmsPreference(); 
                    }
                }
            } else {
                // Réinitialise triggeredToday si l'heure actuelle est différente de l'heure de l'alarme
                // Cela permet à une alarme quotidienne de se redéclencher le lendemain.
                if (alarm.isTriggeredToday()) {
                    alarm.setTriggeredToday(false);
                }
            }
        }
    }

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
            }, 0, 35); // Rafraîchissement plus fréquent pour les millisecondes
            stopwatchRunning = true;
            updateStopwatchButtonStates();
        }
    }

    private void stopStopwatch() {
        if (stopwatchRunning) {
            if (stopwatchTimer != null) stopwatchTimer.cancel();
            stopwatchElapsedTime = System.currentTimeMillis() - stopwatchStartTime; // Enregistre le temps écoulé
            stopwatchRunning = false;
            updateStopwatchButtonStates();
        }
    }

    private void resetStopwatch() {
        if (stopwatchTimer != null) stopwatchTimer.cancel();
        stopwatchElapsedTime = 0;
        stopwatchRunning = false;
        updateStopwatchDisplay(); // Met à jour l'affichage à 00:00:00.000
        updateStopwatchButtonStates();
    }

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

    private void updateStopwatchButtonStates() {
        startStopwatchButton.setEnabled(!stopwatchRunning);
        stopStopwatchButton.setEnabled(stopwatchRunning);
        resetStopwatchButton.setEnabled(stopwatchElapsedTime > 0 || stopwatchRunning); 
    }

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

    private void stopCountdown() {
        if (countdownRunning) {
            if (countdownTimer != null) countdownTimer.cancel();
            // Le temps restant (countdownRemainingTime) est conservé pour une éventuelle reprise.
            countdownRunning = false;
            updateCountdownButtonStates();
        }
    }

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

    private void updateCountdownDisplay() {
        if (countdownRunning) {
            countdownRemainingTime -= 1000; 
            if (countdownRemainingTime < 0) countdownRemainingTime = 0; 
        }
        
        long displayTime = countdownRemainingTime; 

        if (displayTime <= 0 && countdownRunning) { // Si le temps est écoulé PENDANT que le minuteur tournait
            timerDisplay.setText("00:00:00"); 
            if (countdownTimer != null) countdownTimer.cancel();
            countdownRunning = false; // Arrêter le minuteur
            playAlarmSound(Constants.INFO_TIMER_FINISHED);
            updateCountdownButtonStates(); 
            return; 
        } else if (displayTime <= 0 && !countdownRunning) { // Si le temps est à 0 mais le minuteur n'est pas en cours (ex: après reset)
             timerDisplay.setText("00:00:00");
             updateCountdownButtonStates();
             return;
        }


        long hours = displayTime / 3600000;
        long minutes = (displayTime % 3600000) / 60000;
        long seconds = (displayTime % 60000) / 1000;
        timerDisplay.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
    }

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
        
        // Activer Reset si le minuteur tourne OU si du temps est déjà écoulé/entré
        resetTimerButton.setEnabled(countdownRunning || countdownRemainingTime > 0 || hasInputTime);

        timerInputHours.setEnabled(!countdownRunning);
        timerInputMinutes.setEnabled(!countdownRunning);
        timerInputSeconds.setEnabled(!countdownRunning);
    }

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
                    reusableAlarmSoundClip.close(); 
                }
                reusableAlarmSoundClip = tempClip; 
                currentAlarmSoundPath = newSoundPath;
                localSoundPathField.setText(currentAlarmSoundPath);
                prefs.put(Constants.PREF_DEFAULT_RINGTONE_PATH, currentAlarmSoundPath);
                prefs.put(Constants.PREF_LAST_SOUND_DIRECTORY, selectedFile.getParent()); 
                flushPreferences();
                showInfoDialog(Constants.INFO_ALARM_SOUND_UPDATED);
            } else {
                // Si le chargement échoue, réinitialiser le champ au chemin actuel (qui pourrait être le son par défaut)
                localSoundPathField.setText(currentAlarmSoundPath); 
            }
        }
    }

    private Clip loadSoundClip(String soundPath) {
        AudioInputStream audioStream = null;
        Clip clip = null;
        try {
            if (soundPath.toLowerCase().startsWith("http://") || soundPath.toLowerCase().startsWith("https://")) {
                URL soundUrl = new URL(soundPath);
                audioStream = AudioSystem.getAudioInputStream(soundUrl);
            } else {
                File soundFile = new File(soundPath);
                if (!soundFile.exists() || !soundFile.isFile()) {
                    System.err.println("Local sound file not found or is not a file: " + soundPath);
                    return null; 
                }
                audioStream = AudioSystem.getAudioInputStream(soundFile);
            }

            if (audioStream != null) {
                clip = AudioSystem.getClip();
                clip.open(audioStream);
                System.out.println("Sound clip loaded successfully from: " + soundPath);
            }
        } catch (UnsupportedAudioFileException e) {
            System.err.println("Unsupported audio file format for " + soundPath + ": " + e.getMessage());
            showErrorDialog(Constants.ERROR_UNSUPPORTED_AUDIO + soundPath);
        } catch (IOException e) {
            System.err.println("I/O error loading sound " + soundPath + ": " + e.getMessage());
            showErrorDialog(Constants.ERROR_LOADING_SOUND + soundPath);
        } catch (LineUnavailableException e) {
            System.err.println("Audio line unavailable for " + soundPath + ": " + e.getMessage());
            showErrorDialog(Constants.ERROR_AUDIO_LINE_UNAVAILABLE);
        } catch (Exception e) { 
            System.err.println("General error loading sound " + soundPath + ": " + e.getMessage());
            showErrorDialog(Constants.ERROR_UNEXPECTED_SOUND_LOAD + "\n" + e.getMessage());
            e.printStackTrace(); 
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


    private void loadAlarmSound() {
        if (reusableAlarmSoundClip != null && reusableAlarmSoundClip.isOpen()) {
            reusableAlarmSoundClip.close(); 
        }
        reusableAlarmSoundClip = loadSoundClip(currentAlarmSoundPath); 

        // Si le son personnalisé échoue à charger, et que ce n'est pas déjà le son par défaut, tenter de charger le son par défaut.
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
        // Si même le son par défaut ne charge pas, reusableAlarmSoundClip restera null.
    }


    private void playAlarmSound(String alertMessage) {
        // Afficher le message d'abord
        JOptionPane.showMessageDialog(this, alertMessage, Constants.DIALOG_TITLE_INFO, JOptionPane.INFORMATION_MESSAGE);

        // Ensuite, tenter de jouer le son
        if (reusableAlarmSoundClip != null && reusableAlarmSoundClip.isOpen()) {
            if (reusableAlarmSoundClip.isRunning()) {
                reusableAlarmSoundClip.stop(); 
            }
            reusableAlarmSoundClip.setFramePosition(0); 
            reusableAlarmSoundClip.start();
        } else {
            System.err.println("Alarm sound clip is not available or failed to load. Message: " + alertMessage);
            // Pas de message d'erreur supplémentaire à l'utilisateur ici, car le message d'alerte a déjà été montré.
            // Les erreurs de chargement de son sont gérées dans loadSoundClip et loadAlarmSound.
            // Si le son par défaut n'a pas pu être chargé, une alerte a déjà été montrée.
        }
    }


    private void loadAllPersistedData() {
        loadAlarmsPreference();
        // Autres données à charger si nécessaire
    }

    private void saveColorPreferences() {
        prefs.putInt(Constants.PREF_TEXT_COLOR, currentTextColor.getRGB());
        prefs.putInt(Constants.PREF_BG_COLOR, currentClockBgColor.getRGB()); 
        flushPreferences();
    }

    private void saveAlarmsPreference() {
        StringBuilder alarmData = new StringBuilder();
        for (int i = 0; i < alarmListModel.getSize(); i++) {
            Alarm alarm = alarmListModel.getElementAt(i);
            // Échapper les délimiteurs potentiels dans le message
            String safeMessage = alarm.getMessage().replace(";", "&#59;").replace("|", "&#124;");
            alarmData.append(alarm.getTime()).append(";")
                     .append(safeMessage).append(";")
                     .append(alarm.isRepeatDaily());
            if (i < alarmListModel.getSize() - 1) {
                alarmData.append("|"); // Séparateur d'entrées d'alarme
            }
        }
        prefs.put(Constants.PREF_ALARMS, alarmData.toString());
        flushPreferences();
    }

    private void loadAlarmsPreference() {
        alarmListModel.clear(); // S'assurer que la liste est vide avant de charger
        String alarmString = prefs.get(Constants.PREF_ALARMS, "");
        if (!alarmString.isEmpty()) {
            StringTokenizer alarmEntriesTokenizer = new StringTokenizer(alarmString, "|");
            while (alarmEntriesTokenizer.hasMoreTokens()) {
                String alarmEntry = alarmEntriesTokenizer.nextToken();
                String[] parts = alarmEntry.split(";", 3); // Limiter à 3 parties pour éviter les problèmes avec les ; dans le message non échappé
                if (parts.length == 3) {
                    try {
                        String time = parts[0];
                        // Déséchapper les caractères
                        String message = parts[1].replace("&#59;", ";").replace("&#124;", "|");
                        boolean repeatDaily = Boolean.parseBoolean(parts[2]);
                        alarmListModel.addElement(new Alarm(time, message, repeatDaily));
                    } catch (IllegalArgumentException e) { 
                        System.err.println("Error loading a persisted alarm (invalid format/data): " + alarmEntry + " - " + e.getMessage());
                        // Optionnel: informer l'utilisateur d'une erreur de chargement d'alarme spécifique
                    } catch (ArrayIndexOutOfBoundsException e) { // Au cas où split ne renverrait pas assez de parties
                         System.err.println("Error parsing a persisted alarm (missing parts): " + alarmEntry + " - " + e.getMessage());
                    }
                } else {
                     System.err.println("Malformed alarm entry in preferences (expected 3 parts, got " + parts.length + "): " + alarmEntry);
                }
            }
        }
    }

    private void flushPreferences() {
        try {
            prefs.flush();
        } catch (java.util.prefs.BackingStoreException e) {
            System.err.println("Error saving preferences to backing store: " + e.getMessage());
            // Optionnel: informer l'utilisateur d'une erreur de sauvegarde des préférences
        }
    }

    private Image loadImage(String imagePath) {
        if (imagePath == null || imagePath.trim().isEmpty()) {
            System.err.println("Image path is null or empty.");
            return null;
        }
        try {
            Image image;
            if (imagePath.toLowerCase().startsWith("http://") || imagePath.toLowerCase().startsWith("https://")) {
                image = new ImageIcon(new URL(imagePath)).getImage();
            } else {
                File imageFile = new File(imagePath);
                if (imageFile.exists() && imageFile.isFile()) {
                    image = new ImageIcon(imageFile.getAbsolutePath()).getImage();
                } else {
                    System.err.println("Local image file not found or is not a file: " + imagePath);
                    return null;
                }
            }
            // Vérifier si l'image a été correctement chargée (largeur/hauteur > 0)
            if (image != null && image.getWidth(null) > 0 && image.getHeight(null) > 0) {
                return image;
            } else {
                System.err.println("Failed to decode image or image is invalid: " + imagePath);
                return null;
            }
        } catch (Exception e) { // Attraper MalformedURLException aussi
            System.err.println("Error loading image from path '" + imagePath + "': " + e.getMessage());
            return null;
        }
    }

    // Méthodes de style pour les composants UI "Lifestyle"
    private void styleInputFieldLifestyle(JTextField field) {
        field.setBackground(Constants.LIFESTYLE_INPUT_BG);
        field.setForeground(Constants.LIFESTYLE_INPUT_TEXT);
        field.setCaretColor(Constants.LIFESTYLE_TEXT_COLOR.darker()); 
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Constants.LIFESTYLE_BORDER_COLOR, 1), 
            new EmptyBorder(10, 14, 10, 14) // padding intérieur
        ));
        field.setFont(Constants.LABEL_FONT.deriveFont(15f)); 
    }

    private void styleTimerInputFieldLifestyle(JTextField field) {
        field.setFont(Constants.TIMER_INPUT_FONT);
        field.setBackground(Constants.LIFESTYLE_INPUT_BG);
        field.setForeground(Constants.LIFESTYLE_INPUT_TEXT);
        field.setHorizontalAlignment(SwingConstants.CENTER);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Constants.LIFESTYLE_BORDER_COLOR, 1),
            new EmptyBorder(6, 10, 6, 10) // padding intérieur
        ));
        field.setCaretColor(Constants.LIFESTYLE_TEXT_COLOR.darker());
    }

    private void styleFeatureButtonLifestyle(final JButton button, final Color baseBgColor) {
        button.setBackground(baseBgColor);
        button.setForeground(Constants.BUTTON_TEXT_COLOR);
        button.setFocusPainted(false); 
        button.setBorder(new EmptyBorder(15, 28, 15, 28)); 
        button.setFont(Constants.BUTTON_FONT);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        final Color hoverColor = baseBgColor.brighter(); 
        final Color pressedColor = baseBgColor.darker();  

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (button.isEnabled()) button.setBackground(hoverColor);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                 if (button.isEnabled()) button.setBackground(baseBgColor);
            }
            @Override
            public void mousePressed(MouseEvent e) {
                if (button.isEnabled()) button.setBackground(pressedColor);
            }
            @Override
            public void mouseReleased(MouseEvent e) { 
                if (button.isEnabled()) {
                    // Si la souris est toujours sur le bouton après le relâchement
                    if (button.contains(e.getPoint())) { 
                        button.setBackground(hoverColor);
                    } else { // Si la souris a quitté le bouton avant le relâchement
                        button.setBackground(baseBgColor);
                    }
                }
            }
        });
    }

    private JPanel createSettingSectionPanelLifestyle(String title) {
        JPanel panel = new JPanel(new BorderLayout(15, 15)); 
        panel.setOpaque(false); 

        TitledBorder titledBorder = BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Constants.LIFESTYLE_BORDER_COLOR.darker(), 1), // Ligne de bordure
            " " + title + " ", // Titre avec léger espacement
            TitledBorder.LEFT, TitledBorder.TOP,
            Constants.TITLED_BORDER_FONT, Constants.LIFESTYLE_TEXT_COLOR.darker() // Police et couleur du titre
        );
        // Ajoute un padding intérieur au panneau après la bordure titrée
        panel.setBorder(BorderFactory.createCompoundBorder(
            titledBorder,
            new EmptyBorder(10, 10, 10, 10) // Padding intérieur
        ));
        return panel;
    }

    private JLabel createColorPreviewLabelLifestyle(Color color) {
        JLabel previewLabel = new JLabel(); // Pas besoin de texte
        previewLabel.setOpaque(true);
        previewLabel.setBackground(color);
        previewLabel.setPreferredSize(new Dimension(60, 30)); // Taille fixe pour l'aperçu
        previewLabel.setBorder(BorderFactory.createLineBorder(Constants.LIFESTYLE_BORDER_COLOR.darker(), 1)); // Bordure pour visibilité
        return previewLabel;
    }

    private JLabel createDarkLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Constants.LIFESTYLE_TEXT_COLOR); // Utiliser la couleur de texte principale du thème Lifestyle
        label.setFont(Constants.TIMER_INPUT_FONT); // Cohérence avec les champs de saisie du minuteur
        return label;
    }

    // Méthodes utilitaires pour les dialogues
    private void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(this, message, Constants.DIALOG_TITLE_ERROR, JOptionPane.ERROR_MESSAGE);
    }

    private void showInfoDialog(String message) { 
        JOptionPane.showMessageDialog(this, message, Constants.DIALOG_TITLE_INFO, JOptionPane.INFORMATION_MESSAGE);
    }

    private void showWarningDialog(String message) {
        JOptionPane.showMessageDialog(this, message, Constants.DIALOG_TITLE_WARNING, JOptionPane.WARNING_MESSAGE);
    }

    // Gestion de la fermeture de la fenêtre
    private void handleWindowClosing() {
        flushPreferences(); // Sauvegarder les préférences avant de quitter

        // Arrêter tous les timers pour libérer les ressources
        if (mainClockUpdateTimer != null) mainClockUpdateTimer.cancel();
        if (alarmCheckTimer != null) alarmCheckTimer.cancel();
        if (stopwatchTimer != null) stopwatchTimer.cancel();
        if (countdownTimer != null) countdownTimer.cancel();

        if (dedicationAnimationTimer != null && dedicationAnimationTimer.isRunning()) {
            dedicationAnimationTimer.stop();
        }
        if (tabSectionCollapseExpandTimer != null && tabSectionCollapseExpandTimer.isRunning()) {
            tabSectionCollapseExpandTimer.stop();
        }

        // Libérer les ressources audio
        if (reusableAlarmSoundClip != null && reusableAlarmSoundClip.isOpen()) {
            reusableAlarmSoundClip.close();
        }
        
        dispose(); // Libérer les ressources de la fenêtre
        System.exit(0); // Terminer l'application
    }

    // Classe interne pour représenter une Alarme
    private static class Alarm { 
        private final String time; // Format HH:mm
        private final String message;
        private final boolean repeatDaily;
        private boolean triggeredToday; // Pour s'assurer qu'une alarme sonne une seule fois par jour si non répétée, ou une fois par cycle pour les répétées

        public Alarm(String time, String message, boolean repeatDaily) {
            // Validation basique des entrées
            if (time == null || !time.matches(Constants.ALARM_TIME_FORMAT_REGEX)) {
                throw new IllegalArgumentException(Constants.ERROR_INVALID_TIME + " (reçu: " + time + ")");
            }
            if (message == null || message.trim().isEmpty()){
                throw new IllegalArgumentException(Constants.ERROR_EMPTY_MESSAGE);
            }
            this.time = time;
            this.message = message.trim(); 
            this.repeatDaily = repeatDaily;
            this.triggeredToday = false; 
        }

        public String getTime() { return time; }
        public String getMessage() { return message; }
        public boolean isRepeatDaily() { return repeatDaily; }
        public boolean isTriggeredToday() { return triggeredToday; }
        public void setTriggeredToday(boolean triggered) { this.triggeredToday = triggered; }

        @Override
        public String toString() {
            return String.format("%s - %s%s", time, message, (repeatDaily ? " (Quotidien)" : ""));
        }

        // equals et hashCode pour la gestion dans DefaultListModel (contains, remove)
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Alarm alarm = (Alarm) o;
            // Une alarme est considérée comme égale si l'heure, le message et le statut de répétition sont identiques.
            // triggeredToday n'est pas inclus car c'est un état transitoire.
            return repeatDaily == alarm.repeatDaily &&
                   time.equals(alarm.time) &&
                   message.equals(alarm.message);
        }

        @Override
        public int hashCode() {
            int result = time.hashCode();
            result = 31 * result + message.hashCode();
            result = 31 * result + (repeatDaily ? 1 : 0);
            return result;
        }
    }

    // Classe interne pour le panneau d'arrière-plan avec image
    private static class BackgroundPanel extends JPanel { 
        private Image backgroundImage;
        private String currentImageUrl; // Garder une trace de l'URL pour la sauvegarde éventuelle

        public BackgroundPanel(Image image, String imageUrl) {
            this.backgroundImage = image;
            this.currentImageUrl = imageUrl;
            setOpaque(false); // Important pour que le dessin personnalisé soit visible
        }

        public void setImage(Image newImage, String newImageUrl) {
            this.backgroundImage = newImage;
            this.currentImageUrl = newImageUrl;
            repaint(); // Redessiner le panneau avec la nouvelle image
        }

        public String getImageUrl() { return currentImageUrl; }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g); // Nécessaire pour nettoyer la zone de dessin
            if (backgroundImage != null) {
                Graphics2D g2d = (Graphics2D) g.create(); // Utiliser une copie pour ne pas affecter d'autres dessins

                int panelWidth = getWidth();
                int panelHeight = getHeight();
                int imgWidth = backgroundImage.getWidth(this); // Obtenir les dimensions de l'image
                int imgHeight = backgroundImage.getHeight(this);

                if (imgWidth <= 0 || imgHeight <= 0) { // Image invalide ou non chargée
                    g2d.dispose();
                    drawFallbackBackground(g); // Dessiner un fond par défaut
                    return;
                }

                // Logique pour le centrage et le redimensionnement "cover"
                double imgRatio = (double) imgWidth / imgHeight;
                double panelRatio = (double) panelWidth / panelHeight;

                int scaledWidth;
                int scaledHeight;

                if (panelRatio > imgRatio) { // Le panneau est plus large que l'image (relativement)
                    scaledWidth = panelWidth; // L'image prend toute la largeur
                    scaledHeight = (int) (panelWidth / imgRatio); // Calculer la hauteur proportionnellement
                } else { // Le panneau est plus haut ou de même ratio que l'image
                    scaledHeight = panelHeight; // L'image prend toute la hauteur
                    scaledWidth = (int) (panelHeight * imgRatio); // Calculer la largeur proportionnellement
                }

                // Calculer la position pour centrer l'image
                int x = (panelWidth - scaledWidth) / 2;
                int y = (panelHeight - scaledHeight) / 2;

                // Activer des hints de rendu pour une meilleure qualité d'image
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                g2d.drawImage(backgroundImage, x, y, scaledWidth, scaledHeight, this);
                g2d.dispose(); // Libérer les ressources graphiques
            } else {
                drawFallbackBackground(g); // Si aucune image n'est définie
            }
        }
        
        // Dessine un fond simple si l'image ne peut pas être chargée
        private void drawFallbackBackground(Graphics g) {
            g.setColor(new Color(30, 30, 30)); // Un fond sombre
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(Color.LIGHT_GRAY);
            g.setFont(new Font("SansSerif", Font.ITALIC, 16));
            String errorMsg = "Image de fond non disponible";
            FontMetrics fm = g.getFontMetrics();
            int msgWidth = fm.stringWidth(errorMsg);
            int msgHeight = fm.getAscent(); // Utiliser Ascent pour la position Y de base
            g.drawString(errorMsg, (getWidth() - msgWidth) / 2, (getHeight() - msgHeight) / 2 + msgHeight);
        }
    }

    // Point d'entrée de l'application
    public static void main(String[] args) {
        // Essayer de définir le Look and Feel Nimbus pour une apparence plus moderne
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
                // Si Nimbus n'est pas disponible, utiliser le Look and Feel du système
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            }
        } catch (Exception e) { 
            // En cas d'erreur, l'application utilisera le L&F par défaut de Java
            System.err.println("Impossible de définir le Look and Feel : " + e.getMessage());
        }

        // Lancer l'application dans l'Event Dispatch Thread (EDT) de Swing
        SwingUtilities.invokeLater(FuturisticClockApp::new);
    }
}