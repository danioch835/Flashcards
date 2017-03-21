package flashcard;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ButtonGroup;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import javax.swing.JDesktopPane;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Component;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;
import java.awt.Dimension;
import javax.swing.JLayeredPane;
import javax.swing.Box;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.DefaultComboBoxModel;
import java.awt.Dialog.ModalExclusionType;
import javax.swing.JSeparator;

public class MainWindow {

	private JFrame frame;
	private JTextField textTestedWord;
	private JTextField textTranslation;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField textResult;
	private JTextField newWordInPolish;
	private JTextField newWordInEnglish;
	private JTextField textAddWordResult;

	private Translator translator;
	private Dictionary dictionary;
	private Word word;
	private JTextField wordsNumber;
	private JTextField textGoodAnswers;
	private JTextField textBadAnswers;
	private JDesktopPane desktopPane;

	private JInternalFrame internalFrameAddNewWord;
	private JInternalFrame internalFrameRemoveWord;
	private JTextField removeWordResult;
	private JTextField textWordToRemove;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setSize(820, 330);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dictionary.close();
			}
		});

		dictionary = new Dictionary();
		translator = new Translator(dictionary);
		word = new Word();

		desktopPane = new JDesktopPane();
		desktopPane.setBackground(Color.WHITE);

		createMenu();
		createTranslationPane();
		createAddWordInternalFrame();
		createRemoveWordInternalFrame();

		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(desktopPane, GroupLayout.PREFERRED_SIZE, 792, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(22, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(desktopPane, GroupLayout.PREFERRED_SIZE, 287, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(189, Short.MAX_VALUE))
		);
		desktopPane.setLayout(new BorderLayout(0, 0));
		JPanel panelMenu = new JPanel();
		desktopPane.add(panelMenu, BorderLayout.NORTH);
		panelMenu.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panelMenu.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JButton btnMenuNewWord = new JButton("New word");
		panelMenu.add(btnMenuNewWord);
		btnMenuNewWord.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				internalFrameAddNewWord.setVisible(true);
			}
		});

		JButton btnMenuRemoveWord = new JButton("Remove word");
		panelMenu.add(btnMenuRemoveWord);
		btnMenuRemoveWord.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				internalFrameRemoveWord.setVisible(true);
			}
		});

		JButton btnMenuEditWord = new JButton("Edit word");
		btnMenuEditWord.setEnabled(false);
		panelMenu.add(btnMenuEditWord);

		JButton btnMenuChangeDictionary = new JButton("Change dictionary");
		btnMenuChangeDictionary.setEnabled(false);
		panelMenu.add(btnMenuChangeDictionary);

		JLayeredPane layeredPane = new JLayeredPane();
		desktopPane.setLayer(layeredPane, 0);
		desktopPane.add(layeredPane, BorderLayout.CENTER);
		layeredPane.setLayout(null);
		internalFrameRemoveWord = new JInternalFrame("Remove word");
		internalFrameRemoveWord.setBounds(0, 0, 500, 176);
		layeredPane.add(internalFrameRemoveWord);
		internalFrameRemoveWord.setResizable(true);
		internalFrameRemoveWord.setVisible(false);
		internalFrameRemoveWord.getContentPane()
				.setLayout(new BoxLayout(internalFrameRemoveWord.getContentPane(), BoxLayout.Y_AXIS));
		
		JPanel panelRemovingResult = new JPanel();
		internalFrameRemoveWord.getContentPane().add(panelRemovingResult);
		panelRemovingResult.setLayout(new BoxLayout(panelRemovingResult, BoxLayout.X_AXIS));

		removeWordResult = new JTextField();
		removeWordResult.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panelRemovingResult.add(removeWordResult);
		removeWordResult.setAlignmentY(Component.TOP_ALIGNMENT);
		removeWordResult.setHorizontalAlignment(SwingConstants.CENTER);
		removeWordResult.setEditable(false);
		removeWordResult.setColumns(30);

		JPanel panelRemoveWord = new JPanel();
		internalFrameRemoveWord.getContentPane().add(panelRemoveWord);
		panelRemoveWord.setLayout(new BorderLayout(0, 0));

		JLabel lblWordToRemove = new JLabel("Word to remove");
		lblWordToRemove.setHorizontalAlignment(SwingConstants.CENTER);
		panelRemoveWord.add(lblWordToRemove, BorderLayout.NORTH);

		Component rigidArea_9 = Box.createRigidArea(new Dimension(100, 20));
		panelRemoveWord.add(rigidArea_9, BorderLayout.WEST);

		Component rigidArea_10 = Box.createRigidArea(new Dimension(100, 20));
		panelRemoveWord.add(rigidArea_10, BorderLayout.EAST);

		JPanel panelSuggestionWord = new JPanel();
		panelRemoveWord.add(panelSuggestionWord, BorderLayout.SOUTH);
		panelSuggestionWord.setLayout(new BoxLayout(panelSuggestionWord, BoxLayout.Y_AXIS));

		JComboBox comboBox = new JComboBox();
		panelSuggestionWord.add(comboBox);
		comboBox.setVisible(false);
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));

		Component rigidArea_11 = Box.createRigidArea(new Dimension(20, 40));
		panelSuggestionWord.add(rigidArea_11);

		textWordToRemove = new JTextField();
		textWordToRemove.setHorizontalAlignment(SwingConstants.CENTER);
		panelRemoveWord.add(textWordToRemove, BorderLayout.CENTER);
		textWordToRemove.setColumns(10);

		comboBox.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();
				if (keyCode == KeyEvent.VK_ENTER) {
					textWordToRemove.setText(comboBox.getSelectedItem().toString());
					textWordToRemove.requestFocus();
				} else if (keyCode == KeyEvent.VK_ESCAPE) {
					comboBox.hidePopup();
					textWordToRemove.requestFocus();
				}
			}
		});

		JPanel panelClearDictionaryAndClose = new JPanel();
		internalFrameRemoveWord.getContentPane().add(panelClearDictionaryAndClose);
		panelClearDictionaryAndClose.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JButton btnRemoveWord = new JButton("Remove word");
		panelClearDictionaryAndClose.add(btnRemoveWord);
		btnRemoveWord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dictionary.deleteWord(textWordToRemove.getText());
				wordsNumber.setText(Integer.toString(dictionary.getNumberOfWords()));
				removeWordResult.setText("Word " + textWordToRemove.getText() + " removed successfully.");
			}
		});

		JButton btnClearDictionary = new JButton("Clear dictionary");
		panelClearDictionaryAndClose.add(btnClearDictionary);

		JButton btnClose = new JButton("Close");
		panelClearDictionaryAndClose.add(btnClose);
		btnClose.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				internalFrameRemoveWord.setVisible(false);
				textWordToRemove.setText("");
			}
		});
		btnClearDictionary.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dictionary.clear();
				wordsNumber.setText(Integer.toString(dictionary.getNumberOfWords()));
			}
		});
		internalFrameAddNewWord = new JInternalFrame("Add new word");
		internalFrameAddNewWord.setBounds(0, 0, 750, 185);
		layeredPane.add(internalFrameAddNewWord);
		internalFrameAddNewWord.setResizable(true);
		internalFrameAddNewWord.setVisible(false);

		textAddWordResult = new JTextField();
		textAddWordResult.setHorizontalAlignment(SwingConstants.CENTER);
		internalFrameAddNewWord.getContentPane().add(textAddWordResult, BorderLayout.NORTH);
		textAddWordResult.setEditable(false);
		textAddWordResult.setColumns(10);

		JPanel panelAddNewWord = new JPanel();
		internalFrameAddNewWord.getContentPane().add(panelAddNewWord, BorderLayout.CENTER);
		GridBagLayout gbl_panelAddNewWord = new GridBagLayout();
		gbl_panelAddNewWord.columnWidths = new int[] { 50, 79, 50 };
		gbl_panelAddNewWord.rowHeights = new int[] { 23, 0, 0, 0, 0 };
		gbl_panelAddNewWord.columnWeights = new double[] { 0.0, 0.0, 0.0 };
		gbl_panelAddNewWord.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panelAddNewWord.setLayout(gbl_panelAddNewWord);
		
				JButton btnAddWord = new JButton("Add word");
				GridBagConstraints gbc_btnAddWord = new GridBagConstraints();
				gbc_btnAddWord.insets = new Insets(0, 0, 5, 5);
				gbc_btnAddWord.anchor = GridBagConstraints.NORTHWEST;
				gbc_btnAddWord.gridx = 1;
				gbc_btnAddWord.gridy = 2;
				panelAddNewWord.add(btnAddWord, gbc_btnAddWord);
		btnAddWord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!newWordInPolish.getText().isEmpty() && !newWordInEnglish.getText().isEmpty()) {
					word.setInPolish(newWordInPolish.getText());
					word.setInEnglish(newWordInEnglish.getText());
					dictionary.addWord(word);
					wordsNumber.setText(Integer.toString(dictionary.getNumberOfWords()));
					textAddWordResult.setText("Word added correctly.");
				} else {
					textAddWordResult.setText("You must write polish and english word.");
				}

			}
		});

		JPanel panelAddWordInEnglish = new JPanel();
		internalFrameAddNewWord.getContentPane().add(panelAddWordInEnglish, BorderLayout.EAST);
		panelAddWordInEnglish.setLayout(new BoxLayout(panelAddWordInEnglish, BoxLayout.Y_AXIS));

		Component rigidArea_7 = Box.createRigidArea(new Dimension(20, 20));
		panelAddWordInEnglish.add(rigidArea_7);

		JLabel lblWordInEnglish = new JLabel("English");
		lblWordInEnglish.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelAddWordInEnglish.add(lblWordInEnglish);

		Component rigidArea_8 = Box.createRigidArea(new Dimension(20, 20));
		panelAddWordInEnglish.add(rigidArea_8);

		newWordInEnglish = new JTextField();
		panelAddWordInEnglish.add(newWordInEnglish);
		newWordInEnglish.setColumns(25);

		Component rigidArea_6 = Box.createRigidArea(new Dimension(20, 100));
		panelAddWordInEnglish.add(rigidArea_6);

		JPanel panelLoadFileAndClose = new JPanel();
		internalFrameAddNewWord.getContentPane().add(panelLoadFileAndClose, BorderLayout.SOUTH);

		JButton btnLoadFile = new JButton("Load File");
		panelLoadFileAndClose.add(btnLoadFile);
		btnLoadFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				int returnVal = fileChooser.showOpenDialog(btnLoadFile);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					int addedWord = dictionary.addWordsFromFile(file);
					textAddWordResult.setText("Dodano " + addedWord + " slow");
					wordsNumber.setText(Integer.toString(dictionary.getNumberOfWords()));
				}

			}
		});

		JButton btnClose_1 = new JButton("Close");
		panelLoadFileAndClose.add(btnClose_1);
		btnClose_1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				textAddWordResult.setText("");
				newWordInEnglish.setText("");
				newWordInPolish.setText("");
				internalFrameAddNewWord.setVisible(false);
			}

		});

		JPanel panelAddWordInPolish = new JPanel();
		internalFrameAddNewWord.getContentPane().add(panelAddWordInPolish, BorderLayout.WEST);
		panelAddWordInPolish.setLayout(new BoxLayout(panelAddWordInPolish, BoxLayout.Y_AXIS));

		Component rigidArea_5 = Box.createRigidArea(new Dimension(20, 20));
		panelAddWordInPolish.add(rigidArea_5);

		JLabel lblWordInPolish = new JLabel("Polish");
		lblWordInPolish.setHorizontalAlignment(SwingConstants.CENTER);
		lblWordInPolish.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelAddWordInPolish.add(lblWordInPolish);

		Component rigidArea_4 = Box.createRigidArea(new Dimension(20, 20));
		panelAddWordInPolish.add(rigidArea_4);

		newWordInPolish = new JTextField();
		panelAddWordInPolish.add(newWordInPolish);
		newWordInPolish.setColumns(25);

		Component rigidArea_3 = Box.createRigidArea(new Dimension(20, 100));
		panelAddWordInPolish.add(rigidArea_3);
		JPanel panelTranslator = new JPanel();
		panelTranslator.setBounds(0, 0, 791, 249);
		layeredPane.add(panelTranslator);
		panelTranslator.setLayout(new BorderLayout(0, 0));

		JPanel panelTranslationResult = new JPanel();
		panelTranslationResult.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panelTranslator.add(panelTranslationResult, BorderLayout.NORTH);
		panelTranslationResult.setLayout(new BorderLayout(0, 0));

		textResult = new JTextField();
		textResult.setHorizontalAlignment(SwingConstants.CENTER);
		panelTranslationResult.add(textResult, BorderLayout.SOUTH);
		textResult.setEditable(false);
		textResult.setColumns(10);

		JPanel panelLoadNewWord = new JPanel();
		panelLoadNewWord.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panelTranslator.add(panelLoadNewWord, BorderLayout.SOUTH);

		JButton btnNewWord = new JButton("New word");
		panelLoadNewWord.add(btnNewWord);
		btnNewWord.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnNewWord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textTestedWord.setText(translator.loadRandomWord());
				textTranslation.setText("");
				textResult.setText("");
			}
		});

		JPanel panelResults = new JPanel();
		panelResults.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panelTranslator.add(panelResults, BorderLayout.EAST);
		panelResults.setLayout(new BoxLayout(panelResults, BoxLayout.Y_AXIS));

		JLabel lblDictionarySize = new JLabel("Dictionary size:");
		lblDictionarySize.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblDictionarySize.setHorizontalAlignment(SwingConstants.CENTER);
		panelResults.add(lblDictionarySize);

		wordsNumber = new JTextField();
		wordsNumber.setFont(new Font("Tahoma", Font.PLAIN, 14));
		wordsNumber.setBorder(null);
		wordsNumber.setHorizontalAlignment(SwingConstants.CENTER);
		panelResults.add(wordsNumber);
		wordsNumber.setEditable(false);
		wordsNumber.setColumns(10);
		wordsNumber.setText(Integer.toString(dictionary.getNumberOfWords()));
		
		JSeparator separator = new JSeparator();
		separator.setForeground(Color.BLACK);
		panelResults.add(separator);

		JLabel lblResults = new JLabel("Results");
		lblResults.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblResults.setHorizontalAlignment(SwingConstants.CENTER);
		panelResults.add(lblResults);

		textGoodAnswers = new JTextField();
		textGoodAnswers.setText("0");
		textGoodAnswers.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textGoodAnswers.setHorizontalAlignment(SwingConstants.CENTER);
		textGoodAnswers.setBackground(Color.GREEN);
		panelResults.add(textGoodAnswers);
		textGoodAnswers.setEditable(false);
		textGoodAnswers.setColumns(5);

		textBadAnswers = new JTextField();
		textBadAnswers.setText("0");
		textBadAnswers.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textBadAnswers.setHorizontalAlignment(SwingConstants.CENTER);
		textBadAnswers.setBackground(Color.RED);
		panelResults.add(textBadAnswers);
		textBadAnswers.setEditable(false);
		textBadAnswers.setColumns(5);

		JPanel panelCheckTranslation = new JPanel();
		panelCheckTranslation.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panelTranslator.add(panelCheckTranslation, BorderLayout.CENTER);
		panelCheckTranslation.setLayout(new BoxLayout(panelCheckTranslation, BoxLayout.Y_AXIS));

		JLabel lblWordFromDictionary = new JLabel("Polish");
		lblWordFromDictionary.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblWordFromDictionary.setHorizontalAlignment(SwingConstants.CENTER);
		lblWordFromDictionary.setBackground(Color.WHITE);
		panelCheckTranslation.add(lblWordFromDictionary);

		textTestedWord = new JTextField();
		textTestedWord.setHorizontalAlignment(SwingConstants.CENTER);
		panelCheckTranslation.add(textTestedWord);
		textTestedWord.setEditable(false);
		textTestedWord.setColumns(10);

		JLabel lblWordTranslation = new JLabel("English");
		lblWordTranslation.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelCheckTranslation.add(lblWordTranslation);

		textTranslation = new JTextField();
		textTranslation.setHorizontalAlignment(SwingConstants.CENTER);
		panelCheckTranslation.add(textTranslation);
		textTranslation.setColumns(10);

		JButton btnResetResults = new JButton("Reset results");
		btnResetResults.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelResults.add(btnResetResults);
		btnResetResults.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				translator.resetResults();
				textBadAnswers.setText(Integer.toString(translator.getBadAnswers()));
				textGoodAnswers.setText(Integer.toString(translator.getGoodAnswers()));

			}
		});
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setForeground(Color.BLACK);
		panelResults.add(separator_1);

		JLabel lblMode = new JLabel("Mode");
		lblMode.setMaximumSize(new Dimension(35, 14));
		lblMode.setBackground(Color.BLUE);
		lblMode.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblMode.setHorizontalAlignment(SwingConstants.CENTER);
		panelResults.add(lblMode);

		JRadioButton rdbtnEnglishpolish = new JRadioButton("English-Polish");
		rdbtnEnglishpolish.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelResults.add(rdbtnEnglishpolish);
		buttonGroup.add(rdbtnEnglishpolish);
		rdbtnEnglishpolish.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				lblWordFromDictionary.setText("English");
				lblWordTranslation.setText("Polish");
				translator.setEnglishToPolishMode();
			}
		});

		JRadioButton rdbtnPolishenglish = new JRadioButton("Polish-English");
		rdbtnPolishenglish.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelResults.add(rdbtnPolishenglish);
		rdbtnPolishenglish.setSelected(true);
		buttonGroup.add(rdbtnPolishenglish);
		rdbtnPolishenglish.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				lblWordFromDictionary.setText("Polish");
				lblWordTranslation.setText("English");
				translator.setPolishToEnglishMode();
			}
		});

		JButton btnCheckWord = new JButton("Check word");
		btnCheckWord.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelCheckTranslation.add(btnCheckWord);

		btnCheckWord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textTranslation.getText().equals(translator.translate())) {
					textResult.setText("Tlumaczenie poprawne");
					translator.goodAnswer();
					textGoodAnswers.setText(Integer.toString(translator.getGoodAnswers()));
				} else {
					textResult.setText("Tlumaczenie niepoprawne");
					translator.badAnswer();
					textBadAnswers.setText(Integer.toString(translator.getBadAnswers()));
				}
			}
		});
		frame.getContentPane().setLayout(groupLayout);
	}

	private void createMenu() {
	}

	private void createTranslationPane() {
	}

	private void createAddWordInternalFrame() {
	}

	private void createRemoveWordInternalFrame() {

	}
}
