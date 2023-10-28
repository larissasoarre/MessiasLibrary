package view;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.MaskFormatter;
import dao.RentalDAO;
import model.BookSale;
import model.Rental;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RentalForm extends JFrame {

	// JLabels to indicate what each field contains
	private JLabel lblBookISBN;
	private JLabel lblRentalDate;
	private JLabel lblRentalDuration;
	private JLabel lblRentalPrice;
	private JLabel lblClientName;
	private JLabel lblClientEmail;
	private JLabel lblClientPhoneNumber;
	private JLabel lblClientStreet;
	private JLabel lblClientHouseNumber;
	private JLabel lblClientZipcode;
	private JLabel lblClientDistrict;
	private JLabel lblClientCity;
	private JLabel lblClientState;
	private JLabel lblReturnDate;

	// JTextFields to allow typing information
	public JTextField txtBookISBN;
	private JFormattedTextField txtRentalDate;
	public JTextField txtRentalDuration;
	public JTextField txtRentalPrice;
	public JTextField txtClientName;
	private JTextField txtClientEmail;
	private JTextField txtClientPhoneNumber;
	private JTextField txtClientStreet;
	private JTextField txtClientHouseNumber;
	private JTextField txtClientZipcode;
	private JTextField txtClientDistrict;
	private JTextField txtClientCity;
	private JTextField txtClientState;
	public JTextField txtReturnDate;

	// JButton to allow user interaction
	private JButton bntRental;

	// JPanels to organize visual elements
	private JPanel pnlMain;
	private JPanel pnlForm;
	private JPanel pnlButton;
	
	private static RentalDashboard rentalDashboard;

	// Images and logos that are used in the system
	private ImageIcon logo;

	Rental rental = new Rental();
	RentalDAO dao = new RentalDAO();

	public RentalForm(RentalDashboard rentalDashboard) {
		this.rentalDashboard = rentalDashboard;
		
		// Window Settings
		this.setTitle("Livraria Messias");
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setMinimumSize(new Dimension(750, 500));
		this.setLayout(new BorderLayout());
		this.getContentPane().setBackground(new Color(0xEAE7EE));
		this.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				Dimension newSize = getSize();
				if (newSize.width < 750 || newSize.height < 500) {
					setSize(Math.max(newSize.width, 750), Math.max(newSize.height, 500));
				}
			}
		});

		// System window logo
		logo = new ImageIcon(LoginPage.class.getResource("/logo.png"));
		this.setIconImage(logo.getImage());

		// Main Panel with GridBagLayout
		pnlMain = new JPanel(new GridBagLayout());
		pnlMain.setBackground(new Color(0xEAE7EE));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.CENTER;

		// Form panel
		gbc.gridx = 0;
		gbc.gridy = 1;
		pnlForm = new JPanel(new GridBagLayout());
		pnlForm.setBackground(new Color(0xEAE7EE));

		GridBagConstraints constraintLeft = new GridBagConstraints();
		constraintLeft.insets = new Insets(5, 5, 5, 25);
		constraintLeft.anchor = GridBagConstraints.LINE_START;

		Border lineBorder = BorderFactory.createLineBorder(new Color(0x111315), 1);
		Border paddingBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		Border compoundBorder = BorderFactory.createCompoundBorder(lineBorder, paddingBorder);

		constraintLeft.gridx = 0;
		constraintLeft.gridy = 0;
		lblBookISBN = new JLabel("ISBN DO LIVRO:*");
		lblBookISBN.setFont(new Font("Inter", Font.PLAIN, 13));
		pnlForm.add(lblBookISBN, constraintLeft);

		constraintLeft.gridy = 1;
		txtBookISBN = new JTextField(50);
		txtBookISBN.setFont(new Font("Inter", Font.PLAIN, 13));
		txtBookISBN.setBackground(new Color(0xEAE7EE));
		txtBookISBN.setBorder(compoundBorder);
		txtBookISBN.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				updateRentalPrice();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				updateRentalPrice();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				updateRentalPrice();
			}
		});
		txtBookISBN.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				updateRentalPrice();
			}
		});
		pnlForm.add(txtBookISBN, constraintLeft);

		constraintLeft.gridy = 2;
		lblClientName = new JLabel("NOME DO CLIENTE:*");
		lblClientName.setFont(new Font("Inter", Font.PLAIN, 13));
		pnlForm.add(lblClientName, constraintLeft);

		constraintLeft.gridy = 3;
		txtClientName = new JTextField(50);
		txtClientName.setFont(new Font("Inter", Font.PLAIN, 13));
		txtClientName.setBackground(new Color(0xEAE7EE));
		txtClientName.setBorder(compoundBorder);
		pnlForm.add(txtClientName, constraintLeft);

		constraintLeft.gridy = 4;
		lblClientEmail = new JLabel("E-MAIL:*");
		lblClientEmail.setFont(new Font("Inter", Font.PLAIN, 13));
		pnlForm.add(lblClientEmail, constraintLeft);

		constraintLeft.gridy = 5;
		txtClientEmail = new JTextField(50);
		txtClientEmail.setFont(new Font("Inter", Font.PLAIN, 13));
		txtClientEmail.setBackground(new Color(0xEAE7EE));
		txtClientEmail.setBorder(compoundBorder);
		pnlForm.add(txtClientEmail, constraintLeft);

		constraintLeft.gridy = 6;
		lblClientPhoneNumber = new JLabel("TELEFONE PARA CONTATO:*");
		lblClientPhoneNumber.setFont(new Font("Inter", Font.PLAIN, 13));
		pnlForm.add(lblClientPhoneNumber, constraintLeft);

		constraintLeft.gridy = 7;
		txtClientPhoneNumber = new JTextField(50);
		txtClientPhoneNumber.setFont(new Font("Inter", Font.PLAIN, 13));
		txtClientPhoneNumber.setBackground(new Color(0xEAE7EE));
		txtClientPhoneNumber.setBorder(compoundBorder);
		pnlForm.add(txtClientPhoneNumber, constraintLeft);

		constraintLeft.gridy = 8;
		lblClientStreet = new JLabel("LOGRADOURO:*");
		lblClientStreet.setFont(new Font("Inter", Font.PLAIN, 13));
		pnlForm.add(lblClientStreet, constraintLeft);

		constraintLeft.gridy = 9;
		txtClientStreet = new JTextField(50);
		txtClientStreet.setFont(new Font("Inter", Font.PLAIN, 13));
		txtClientStreet.setBackground(new Color(0xEAE7EE));
		txtClientStreet.setBorder(compoundBorder);
		pnlForm.add(txtClientStreet, constraintLeft);

		constraintLeft.gridy = 10;
		lblClientHouseNumber = new JLabel("NÚMERO DA RESIDÊNCIA:");
		lblClientHouseNumber.setFont(new Font("Inter", Font.PLAIN, 13));
		pnlForm.add(lblClientHouseNumber, constraintLeft);

		constraintLeft.gridy = 11;
		txtClientHouseNumber = new JTextField(50);
		txtClientHouseNumber.setFont(new Font("Inter", Font.PLAIN, 13));
		txtClientHouseNumber.setBackground(new Color(0xEAE7EE));
		txtClientHouseNumber.setBorder(compoundBorder);
		pnlForm.add(txtClientHouseNumber, constraintLeft);

		constraintLeft.gridy = 12;
		lblClientZipcode = new JLabel("CEP:*");
		lblClientZipcode.setFont(new Font("Inter", Font.PLAIN, 13));
		pnlForm.add(lblClientZipcode, constraintLeft);

		constraintLeft.gridy = 13;
		txtClientZipcode = new JTextField(50);
		txtClientZipcode.setFont(new Font("Inter", Font.PLAIN, 13));
		txtClientZipcode.setBackground(new Color(0xEAE7EE));
		txtClientZipcode.setBorder(compoundBorder);
		pnlForm.add(txtClientZipcode, constraintLeft);

		GridBagConstraints constraintRight = new GridBagConstraints();
		constraintRight.insets = new Insets(5, 25, 5, 5);
		constraintRight.anchor = GridBagConstraints.LINE_START;

		constraintRight.gridx = 1;
		constraintRight.gridy = 0;
		lblClientDistrict = new JLabel("BAIRRO:*");
		lblClientDistrict.setFont(new Font("Inter", Font.PLAIN, 13));
		pnlForm.add(lblClientDistrict, constraintRight);

		constraintRight.gridy = 1;
		txtClientDistrict = new JTextField(50);
		txtClientDistrict.setFont(new Font("Inter", Font.PLAIN, 13));
		txtClientDistrict.setBackground(new Color(0xEAE7EE));
		txtClientDistrict.setBorder(compoundBorder);
		pnlForm.add(txtClientDistrict, constraintRight);

		constraintRight.gridy = 2;
		lblClientCity = new JLabel("CIDADE:*");
		lblClientCity.setFont(new Font("Inter", Font.PLAIN, 13));
		pnlForm.add(lblClientCity, constraintRight);

		constraintRight.gridy = 3;
		txtClientCity = new JTextField(50);
		txtClientCity.setFont(new Font("Inter", Font.PLAIN, 13));
		txtClientCity.setBackground(new Color(0xEAE7EE));
		txtClientCity.setBorder(compoundBorder);
		pnlForm.add(txtClientCity, constraintRight);

		constraintRight.gridy = 4;
		lblClientState = new JLabel("ESTADO:*");
		lblClientState.setFont(new Font("Inter", Font.PLAIN, 13));
		pnlForm.add(lblClientState, constraintRight);

		constraintRight.gridy = 5;
		txtClientState = new JTextField(50);
		txtClientState.setFont(new Font("Inter", Font.PLAIN, 13));
		txtClientState.setBackground(new Color(0xEAE7EE));
		txtClientState.setBorder(compoundBorder);
		pnlForm.add(txtClientState, constraintRight);

		constraintRight.gridy = 6;
		lblRentalDate = new JLabel("DATA DE INICIO DO EMPRÉSTIMO:*");
		lblRentalDate.setFont(new Font("Inter", Font.PLAIN, 13));
		pnlForm.add(lblRentalDate, constraintRight);

		constraintRight.gridy = 7;
		txtRentalDate = new JFormattedTextField(createMaskFormatter("##/##/####"));
		txtRentalDate.setColumns(50);
		txtRentalDate.setFont(new Font("Inter", Font.PLAIN, 13));
		txtRentalDate.setBackground(new Color(0xEAE7EE));
		txtRentalDate.setBorder(compoundBorder);
		txtRentalDate.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				updateRentalPrice();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				updateRentalPrice();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				updateRentalPrice();
			}
		});
		txtRentalDate.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				updateRentalPrice();
			}
		});
		pnlForm.add(txtRentalDate, constraintRight);

		constraintRight.gridy = 8;
		lblRentalDuration = new JLabel("DURAÇÃO DO EMPRÉSTIMO (em dias):*");
		lblRentalDuration.setFont(new Font("Inter", Font.PLAIN, 13));
		pnlForm.add(lblRentalDuration, constraintRight);

		constraintRight.gridy = 9;
		txtRentalDuration = new JTextField(50);
		txtRentalDuration.setFont(new Font("Inter", Font.PLAIN, 13));
		txtRentalDuration.setBackground(new Color(0xEAE7EE));
		txtRentalDuration.setBorder(compoundBorder);
		txtRentalDuration.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				updateRentalPrice();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				updateRentalPrice();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				updateRentalPrice();
			}
		});
		txtRentalDuration.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				updateRentalPrice();
			}
		});
		pnlForm.add(txtRentalDuration, constraintRight);

		constraintRight.gridy = 10;
		lblRentalPrice = new JLabel("TOTAL DO EMPRÉSTIMO:");
		lblRentalPrice.setFont(new Font("Inter", Font.PLAIN, 13));
		pnlForm.add(lblRentalPrice, constraintRight);

		constraintRight.gridy = 11;
		txtRentalPrice = new JTextField(50);
		txtRentalPrice.setFont(new Font("Inter", Font.PLAIN, 13));
		txtRentalPrice.setBackground(new Color(0xEAE7EE));
		txtRentalPrice.setBorder(compoundBorder);
		txtRentalPrice.setEditable(false);
		txtRentalPrice.setText("0.00");
		pnlForm.add(txtRentalPrice, constraintRight);

		constraintRight.gridy = 12;
		lblReturnDate = new JLabel("DATA DE RETORNO:");
		lblReturnDate.setFont(new Font("Inter", Font.PLAIN, 13));
		pnlForm.add(lblReturnDate, constraintRight);

		constraintRight.gridy = 13;
		txtReturnDate = new JTextField(50);
		txtReturnDate.setFont(new Font("Inter", Font.PLAIN, 13));
		txtReturnDate.setBackground(new Color(0xEAE7EE));
		txtReturnDate.setBorder(compoundBorder);
		txtReturnDate.setEditable(false);
		pnlForm.add(txtReturnDate, constraintRight);

		pnlMain.add(pnlForm, gbc);

		// Button Panel
		GridBagConstraints gbcButton = new GridBagConstraints();
		gbcButton.gridx = 0;
		gbcButton.gridy = 2; // Coloque o botão na terceira linha
		pnlButton = new JPanel(new FlowLayout());
		pnlButton.setBackground(new Color(0xEAE7EE));

		// Instance JButton
		bntRental = new JButton("ADICIONAR");
		constraintRight.insets = new Insets(30, 0, 0, 0);
		bntRental.setBorderPainted(false);
		bntRental.setPreferredSize(new Dimension(130, 25));
		bntRental.setFont(new Font("Inter", Font.BOLD, 13));
		bntRental.setBackground(new Color(0x5E00D7));
		bntRental.setForeground(new Color(0xFFFFFF));
		bntRental.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addRentalToDatabase();
			}
		});
		pnlButton.add(bntRental, BorderLayout.CENTER);

		pnlMain.add(pnlButton, gbcButton);

		this.add(pnlMain, BorderLayout.CENTER);
	}

	// Function that formats the date field
	private MaskFormatter createMaskFormatter(String format) {
		MaskFormatter maskFormatter = null;
		try {
			maskFormatter = new MaskFormatter(format);
			maskFormatter.setPlaceholderCharacter('_');
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return maskFormatter;
	}
	
	// Function that calculates and updates the price and return date of the loan
	private void updateRentalPrice() {
		try {
			long isbn = Long.parseLong(txtBookISBN.getText());
			int duration = Integer.parseInt(txtRentalDuration.getText());

			rental.setBookISBN(isbn);
			rental.setRentalDuration(duration);

			// Gets the sales price from the database based on the entered ISBN
			double salePrice = dao.getBookSalePrice(txtBookISBN.getText());

			// Calculate the rental price
			double rentalPrice = salePrice * duration;

			// Formats and displays the rental price
			DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
			String formattedRentalPrice = decimalFormat.format(rentalPrice).replace(".", ",");
			txtRentalPrice.setText(formattedRentalPrice);

			// Analyzes the rental date from the "LOAN START DATE" field
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date rentalDate = dateFormat.parse(txtRentalDate.getText());

			// Calculates return date based on rental date and duration
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(rentalDate);
			calendar.add(Calendar.DAY_OF_MONTH, duration);
			Date returnDate = calendar.getTime();

			// Formats and displays the return date
			String formattedReturnDate = dateFormat.format(returnDate);
			txtReturnDate.setText(formattedReturnDate);

		} catch (NumberFormatException ex) {
			txtRentalPrice.setText("0.00");
			txtReturnDate.setText("");
		} catch (ParseException e) {
			txtRentalPrice.setText("0.00");
			txtReturnDate.setText("");
		}
	}

	// Function that checks whether the email entered is valid
	private boolean isValidEmail(String email) {
		String emailPattern = "^[A-Za-z0-9+_.-]+@(.+)$"; // Basic regex email pattern
		Pattern pattern = Pattern.compile(emailPattern);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

	// Function that adds loan to the database
	public void addRentalToDatabase() {
		// Define the strings for the "yes" and "no" buttons in Portuguese
		UIManager.put("OptionPane.yesButtonText", "Sim");
		UIManager.put("OptionPane.noButtonText", "Não");
		UIManager.put("Button.background", new Color(0x5E00D7));
		UIManager.put("Button.foreground", new Color(0xFFFFFF));
		UIManager.put("Button.font", new Font("Inter", Font.BOLD, 13));
		UIManager.put("Button.border", BorderFactory.createEmptyBorder(5, 10, 5, 10));

		String rentalDateStr = txtRentalDate.getText();

		// Converts the date string to a Date object
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date rentalDate;
		try {
			rentalDate = dateFormat.parse(rentalDateStr);
		} catch (ParseException e) {
			// Handles parsing errors
			JOptionPane.showMessageDialog(RentalForm.this, "Data de início do empréstimo inválida.");
			return;
		}

		// Checks if the ISBN is valid
		String bookISBN = txtBookISBN.getText();
		if (!dao.isISBNValid(bookISBN)) {
			// Display an error message if the ISBN is not valid
			JOptionPane.showMessageDialog(RentalForm.this, "ISBN Inválido. Livro não encontrado no estoque.");
			return;
		}

		// Gets the sales price from the database based on the entered ISBN
		double salePrice = dao.getBookSalePrice(bookISBN);

		// Creates a bookSale object with the sales price
		BookSale bookSale = new BookSale();
		bookSale.setSalePrice(salePrice);

		rental.setBookSale(bookSale);

		int rentalDuration = 0;

		try {
			rentalDuration = Integer.parseInt(txtRentalDuration.getText());
		} catch (NumberFormatException e) {
			// Handles the case where the rental duration is not a valid integer
			JOptionPane.showMessageDialog(RentalForm.this, "Duração do Empréstimo inválida.");
			return;
		}

		// Validates whether the ISBN contains 13 numeric digits
		if (txtBookISBN.getText().length() != 13 || !txtBookISBN.getText().matches("\\d+")) {
			JOptionPane.showMessageDialog(this, "O ISBN deve conter 13 dígitos numéricos.", "Erro",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Validates that the email field actually contains an email
		String email = txtClientEmail.getText();

		if (!isValidEmail(email)) {
			JOptionPane.showMessageDialog(this, "Por favor, insira um endereço de e-mail válido.", "Erro",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Validates whether the contact number contains 11 numeric digits
		if (txtClientPhoneNumber.getText().length() != 11 || !txtClientPhoneNumber.getText().matches("\\d+")
				|| Long.parseLong(txtClientPhoneNumber.getText()) < 0) {
			JOptionPane.showMessageDialog(this, "O Telefone Para Contato deve conter 11 dígitos numéricos.", "Erro",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Validates whether the residence number contains only numeric digits
		if (!txtClientHouseNumber.getText().matches("\\d+") || Integer.parseInt(txtClientHouseNumber.getText()) < 0) {
			JOptionPane.showMessageDialog(this, "O número da residência deve ser um valor numérico inteiro.", "Erro",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Validates if CEP contains only numeric digits and is not negative
		if (!txtClientHouseNumber.getText().matches("\\d+") || Integer.parseInt(txtClientZipcode.getText()) < 0) {
			JOptionPane.showMessageDialog(this, "O CEP deve ser um valor numérico inteiro.", "Erro",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Validates that the loan duration does not contain numeric digits or is a negative number
		if (!txtRentalDuration.getText().matches("^\\d+$") || Integer.parseInt(txtRentalDuration.getText()) < 0) {
			JOptionPane.showMessageDialog(this,
					"A duração do empréstimo deve ser um valor numérico inteiro não negativo.", "Erro",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Add the information entered
		rental.setBookISBN(Long.parseLong(txtBookISBN.getText()));
		rental.setRentalDate(rentalDate);
		rental.setRentalDuration(Integer.parseInt(txtRentalDuration.getText()));

		// Calculate the rental price and display it in the rental price field
		double rentalPrice = salePrice * rentalDuration;
		DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
		String formattedRentalPrice = decimalFormat.format(rentalPrice).replace(".", ",");
		txtRentalPrice.setText(formattedRentalPrice);

		// Calculates return date
		Date rntlDate = rental.getRentalDate();
		int rntDuration = rental.getRentalDuration();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(rntlDate);
		calendar.add(Calendar.DAY_OF_MONTH, rntDuration);
		Date returnDate = calendar.getTime();

		// Set the return date in the text field
		SimpleDateFormat dtFormat = new SimpleDateFormat("dd/MM/yyyy");
		String returnDateStr = dtFormat.format(returnDate);
		txtReturnDate.setText(returnDateStr);

		rental.setReturnDate(returnDate);
		rental.setRentalPrice(rentalPrice);
		rental.setName(txtClientName.getText());
		rental.setEmail(txtClientEmail.getText());
		rental.setPhoneNumber(Long.parseLong(txtClientPhoneNumber.getText()));
		rental.setStreet(txtClientStreet.getText());
		rental.setHouseNumber(Integer.parseInt(txtClientHouseNumber.getText()));
		rental.setZipcode(Integer.parseInt(txtClientZipcode.getText()));
		rental.setDistrict(txtClientDistrict.getText());
		rental.setCity(txtClientCity.getText());
		rental.setState(txtClientState.getText());

		dao.insertRental(rental);
		
		rentalDashboard.refreshTable();
		
		// Dialog box that confirms adding the loan
		JOptionPane.showMessageDialog(RentalForm.this, "Empréstimo realizado com sucesso.");

		dispose();
	}

	public static void main(String[] args) {
		RentalForm frame = new RentalForm(rentalDashboard);
		frame.setVisible(true);
	}
}
