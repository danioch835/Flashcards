import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JTextArea;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ButtonGroup;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

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
	private JTextField textWordToRemove;
	private JTextField textGoodAnswers;
	private JTextField textBadAnswers;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setSize(800, 600);
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
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dictionary.close();
			}
		});
		
		dictionary = new Dictionary();
		translator = new Translator(dictionary);
		word = new Word();
		
		JButton btnNewWord = new JButton("New word");
		btnNewWord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textTestedWord.setText(translator.loadRandomPolishWord());
				textTranslation.setText("");
				textResult.setText("");
			}
		});
		
		JButton btnCheckWord = new JButton("Check word");
		btnCheckWord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(textTranslation.getText().equals(translator.fromPolishToEnglish())) {
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
		
		textTestedWord = new JTextField();
		textTestedWord.setEditable(false);
		textTestedWord.setColumns(10);
		
		textTranslation = new JTextField();
		textTranslation.setColumns(10);
		
		JRadioButton rdbtnEnglishpolish = new JRadioButton("English-Polish");
		buttonGroup.add(rdbtnEnglishpolish);
		
		JRadioButton rdbtnPolishenglish = new JRadioButton("Polish-English");
		rdbtnPolishenglish.setSelected(true);
		buttonGroup.add(rdbtnPolishenglish);
		
		textResult = new JTextField();
		textResult.setEditable(false);
		textResult.setColumns(10);
		
		newWordInPolish = new JTextField();
		newWordInPolish.setColumns(10);
		
		newWordInEnglish = new JTextField();
		newWordInEnglish.setColumns(10);
		
		JButton btnAddWord = new JButton("Add word");
		btnAddWord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				word.setInPolish(newWordInPolish.getText());
				word.setInEnglish(newWordInEnglish.getText());
				dictionary.addWord(word);
				wordsNumber.setText(Integer.toString(dictionary.getNumberOfWords()));
				textAddWordResult.setText("S³owo dodano poprawnie");
			}
		});
		
		textAddWordResult = new JTextField();
		textAddWordResult.setEditable(false);
		textAddWordResult.setColumns(10);
		
		wordsNumber = new JTextField();
		wordsNumber.setEditable(false);
		wordsNumber.setColumns(10);
		wordsNumber.setText(Integer.toString(dictionary.getNumberOfWords()));
		
		JButton btnLoadFile = new JButton("Load File");
		btnLoadFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				int returnVal = fileChooser.showOpenDialog(btnLoadFile);
				
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					int addedWord = dictionary.addWordsFromFile(file);
					textAddWordResult.setText("Dodano " + addedWord + " slow");
					wordsNumber.setText(Integer.toString(dictionary.getNumberOfWords()));
				}
				
			}
		});
		
		JButton btnClearDictionary = new JButton("Clear dictionary");
		btnClearDictionary.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dictionary.clear();
				wordsNumber.setText(Integer.toString(dictionary.getNumberOfWords()));
			}
		});
		
		textWordToRemove = new JTextField();
		textWordToRemove.setColumns(10);
		
		JButton btnRemoveWord = new JButton("Remove word");
		btnRemoveWord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dictionary.deleteWord(textWordToRemove.getText());
				wordsNumber.setText(Integer.toString(dictionary.getNumberOfWords()));
			}
		});
		
		textGoodAnswers = new JTextField();
		textGoodAnswers.setEditable(false);
		textGoodAnswers.setColumns(10);
		
		textBadAnswers = new JTextField();
		textBadAnswers.setEditable(false);
		textBadAnswers.setColumns(10);
		
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(135)
					.addComponent(textTestedWord, GroupLayout.PREFERRED_SIZE, 139, GroupLayout.PREFERRED_SIZE)
					.addGap(134)
					.addComponent(textTranslation, GroupLayout.PREFERRED_SIZE, 139, GroupLayout.PREFERRED_SIZE)
					.addGap(136))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(236)
					.addComponent(textResult, GroupLayout.PREFERRED_SIZE, 193, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(309, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(174)
							.addComponent(textGoodAnswers, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(156)
							.addComponent(textBadAnswers, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(98)
							.addComponent(wordsNumber, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(289)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(btnCheckWord)
								.addComponent(btnNewWord, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED, 177, Short.MAX_VALUE)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(rdbtnPolishenglish)
								.addComponent(rdbtnEnglishpolish))))
					.addGap(94))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(newWordInEnglish, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(newWordInPolish, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addComponent(btnAddWord)
							.addGap(33)
							.addComponent(textAddWordResult, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(151))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(textWordToRemove, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(26)))
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(btnRemoveWord)
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
							.addGroup(groupLayout.createSequentialGroup()
								.addComponent(btnClearDictionary)
								.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addGroup(groupLayout.createSequentialGroup()
								.addComponent(btnLoadFile)
								.addGap(116))))
					.addGap(84))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(27)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(wordsNumber, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(textGoodAnswers, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(textBadAnswers, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(textResult, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
					.addGap(59)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(textTestedWord, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(textTranslation, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(32)
									.addComponent(btnCheckWord)
									.addPreferredGap(ComponentPlacement.RELATED, 8, Short.MAX_VALUE))
								.addGroup(groupLayout.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(rdbtnEnglishpolish)
									.addPreferredGap(ComponentPlacement.UNRELATED)))
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(rdbtnPolishenglish)
								.addComponent(btnNewWord))
							.addPreferredGap(ComponentPlacement.RELATED, 66, Short.MAX_VALUE)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(newWordInPolish, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(newWordInEnglish, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addGap(34))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(btnClearDictionary)
									.addGap(18)
									.addComponent(btnLoadFile)
									.addGap(21))))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(119)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(textWordToRemove, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnRemoveWord))
							.addGap(22)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnAddWord)
								.addComponent(textAddWordResult, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(50))))
		);
		frame.getContentPane().setLayout(groupLayout);
	}
}
