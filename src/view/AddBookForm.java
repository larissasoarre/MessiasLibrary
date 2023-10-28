package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;

import dao.BookSaleDAO;
import model.BookSale;

public class AddBookForm extends JFrame implements ActionListener {

	// JLabels to indicate what is contained in each field
	private JLabel lblIsbn;
	private JLabel lblTitle;
	private JLabel lblAuthor;
	private JLabel lblGenre;
	private JLabel lblPublisher;
	private JLabel lblEdition;
	private JLabel lblSalePrice;
	private JLabel lblQuantity;

	// JTextFields to allow typing information
	private JTextField txtIsbn;
	private JTextField txtTitle;
	private JTextField txtAuthor;
	private JTextField txtGenre;
	private JTextField txtPublisher;
	private JTextField txtEdition;
	private JTextField txtSalePrice;
	private JTextField txtQuantity;

	// JButton to allow user interaction
	private JButton bntAdd;
	private JButton btnReturn;

	// JPanels to organize visual elements
	private JPanel pnlMain;
	private JPanel pnlForm;
	private JPanel pnlButton;

	// Images and logos that are used in the system
	private ImageIcon logo;

	// Attributes to store user information and manipulate the database
	private BookSale book;
	private BookSaleDAO dao;

	// Importing Dashboard class method
	private Dashboard dashboard;

	public AddBookForm(Dashboard dashboard) {
		this.dashboard = dashboard;

		// Window Settings
		this.setTitle("Livraria Messias");
		this.setSize(700, 600);
		this.setLocationRelativeTo(null);
		this.setMinimumSize(new Dimension(700, 650));
		this.setLayout(new BorderLayout());
		this.getContentPane().setBackground(new Color(0xEAE7EE));
		this.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				Dimension newSize = getSize();
				if (newSize.width < 700 || newSize.height < 650) {
					setSize(Math.max(newSize.width, 700), Math.max(newSize.height, 650));
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
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets = new Insets(5, 5, 5, 5);
		constraints.anchor = GridBagConstraints.LINE_START;

		Border lineBorder = BorderFactory.createLineBorder(new Color(0x111315), 1);
		Border paddingBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		Border compoundBorder = BorderFactory.createCompoundBorder(lineBorder, paddingBorder);

		constraints.gridx = 0;
		constraints.gridy = 0;
		lblIsbn = new JLabel("CÓDIGO ISBN:*");
		lblIsbn.setFont(new Font("Inter", Font.PLAIN, 13));
		pnlForm.add(lblIsbn, constraints);

		constraints.gridy = 1;
		txtIsbn = new JTextField(50);
		txtIsbn.setFont(new Font("Inter", Font.PLAIN, 13));
		txtIsbn.setBackground(new Color(0xEAE7EE));
		txtIsbn.setBorder(compoundBorder);
		pnlForm.add(txtIsbn, constraints);

		constraints.gridy = 2;
		lblTitle = new JLabel("TÍTULO DO LIVRO:*");
		lblTitle.setFont(new Font("Inter", Font.PLAIN, 13));
		pnlForm.add(lblTitle, constraints);

		constraints.gridy = 3;
		txtTitle = new JTextField(50);
		txtTitle.setFont(new Font("Inter", Font.PLAIN, 13));
		txtTitle.setBackground(new Color(0xEAE7EE));
		txtTitle.setBorder(compoundBorder);
		pnlForm.add(txtTitle, constraints);

		constraints.gridy = 4;
		lblAuthor = new JLabel("AUTOR:*");
		lblAuthor.setFont(new Font("Inter", Font.PLAIN, 13));
		pnlForm.add(lblAuthor, constraints);

		constraints.gridy = 5;
		txtAuthor = new JTextField(50);
		txtAuthor.setFont(new Font("Inter", Font.PLAIN, 13));
		txtAuthor.setBackground(new Color(0xEAE7EE));
		txtAuthor.setBorder(compoundBorder);
		pnlForm.add(txtAuthor, constraints);

		constraints.gridy = 6;
		lblGenre = new JLabel("GÊNERO:*");
		lblGenre.setFont(new Font("Inter", Font.PLAIN, 13));
		pnlForm.add(lblGenre, constraints);

		constraints.gridy = 7;
		txtGenre = new JTextField(50);
		txtGenre.setFont(new Font("Inter", Font.PLAIN, 13));
		txtGenre.setBackground(new Color(0xEAE7EE));
		txtGenre.setBorder(compoundBorder);
		pnlForm.add(txtGenre, constraints);

		constraints.gridy = 8;
		lblPublisher = new JLabel("EDITORA:*");
		lblPublisher.setFont(new Font("Inter", Font.PLAIN, 13));
		pnlForm.add(lblPublisher, constraints);

		constraints.gridy = 9;
		txtPublisher = new JTextField(50);
		txtPublisher.setFont(new Font("Inter", Font.PLAIN, 13));
		txtPublisher.setBackground(new Color(0xEAE7EE));
		txtPublisher.setBorder(compoundBorder);
		pnlForm.add(txtPublisher, constraints);

		constraints.gridy = 10;
		lblEdition = new JLabel("EDIÇÃO:");
		lblEdition.setFont(new Font("Inter", Font.PLAIN, 13));
		pnlForm.add(lblEdition, constraints);

		constraints.gridy = 11;
		txtEdition = new JTextField(50);
		txtEdition.setFont(new Font("Inter", Font.PLAIN, 13));
		txtEdition.setBackground(new Color(0xEAE7EE));
		txtEdition.setBorder(compoundBorder);
		pnlForm.add(txtEdition, constraints);

		constraints.gridy = 12;
		lblSalePrice = new JLabel("PREÇO:*");
		lblSalePrice.setFont(new Font("Inter", Font.PLAIN, 13));
		pnlForm.add(lblSalePrice, constraints);

		constraints.gridy = 13;
		txtSalePrice = new JTextField(50);
		txtSalePrice.setFont(new Font("Inter", Font.PLAIN, 13));
		txtSalePrice.setBackground(new Color(0xEAE7EE));
		txtSalePrice.setBorder(compoundBorder);
		pnlForm.add(txtSalePrice, constraints);

		constraints.gridy = 14;
		lblQuantity = new JLabel("QUANTIDADE:*");
		lblQuantity.setFont(new Font("Inter", Font.PLAIN, 13));
		pnlForm.add(lblQuantity, constraints);

		constraints.gridy = 15;
		txtQuantity = new JTextField(50);
		txtQuantity.setFont(new Font("Inter", Font.PLAIN, 13));
		txtQuantity.setBackground(new Color(0xEAE7EE));
		txtQuantity.setBorder(compoundBorder);
		pnlForm.add(txtQuantity, constraints);

		Border lineBorder2 = BorderFactory.createLineBorder(new Color(0x5E00D7), 2);
		Border paddingBorder2 = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		Border compoundBorder2 = BorderFactory.createCompoundBorder(lineBorder2, paddingBorder2);

		// Add pnlForm to pnlMain
		pnlMain.add(pnlForm, gbc);

		// Button Panel
		gbc.gridy = 2;
		pnlButton = new JPanel(new FlowLayout());
		pnlButton.setBackground(new Color(0xEAE7EE));

		// Instantiating JButton
		btnReturn = new JButton("VOLTAR");
		btnReturn.setPreferredSize(new Dimension(110, 25));
		btnReturn.setFont(new Font("Inter", Font.BOLD, 13));
		btnReturn.setBackground(new Color(0xEDEDF2));
		btnReturn.setForeground(new Color(0x5E00D7));
		btnReturn.setBorder(compoundBorder2);
		btnReturn.addActionListener(this);
		pnlButton.add(btnReturn);

		bntAdd = new JButton("ADICIONAR");
		bntAdd.setBorderPainted(false);
		bntAdd.setPreferredSize(new Dimension(130, 25));
		bntAdd.setFont(new Font("Inter", Font.BOLD, 13));
		bntAdd.setBackground(new Color(0x5E00D7));
		bntAdd.setForeground(new Color(0xFFFFFF));
		bntAdd.addActionListener(this);
		pnlButton.add(bntAdd);

		// Add pnlButton to pnlMain
		pnlMain.add(pnlButton, gbc);

		this.add(pnlMain, BorderLayout.CENTER);

		// Instantiation of handling attributes
		book = new BookSale();
		dao = new BookSaleDAO();
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		// Define the strings for the "yes" and "no" buttons in Portuguese
		UIManager.put("OptionPane.yesButtonText", "Sim");
		UIManager.put("OptionPane.noButtonText", "Não");
		UIManager.put("Button.background", new Color(0x5E00D7));
		UIManager.put("Button.foreground", new Color(0xFFFFFF));
		UIManager.put("Button.font", new Font("Inter", Font.BOLD, 13));
		UIManager.put("Button.border", BorderFactory.createEmptyBorder(5, 10, 5, 10));

		if (e.getSource() == bntAdd) {

			// Validates whether the ISBN contains 13 numeric digits
			if (txtIsbn.getText().length() != 13 || !txtIsbn.getText().matches("\\d+")) {
				JOptionPane.showMessageDialog(this, "O ISBN deve conter 13 dígitos numéricos.", "Erro",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			Integer edition = null;
			if (!txtEdition.getText().isEmpty()) {
				try {
					edition = Integer.parseInt(txtEdition.getText());
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(this, "A edição deve ser um valor numérico inteiro.", "Erro",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
			}

			// Validates whether the sales price field contains monetary value
			Double salePrice = null;
			try {
				salePrice = Double.parseDouble(txtSalePrice.getText());
				if (salePrice <= 0) {
					throw new NumberFormatException();
				}
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this,
						"<html>O preço de venda deve ser um valor numérico válido.<br>Dica: escreva 2.50 ao invés de 2,50</html>",
						"Erro", JOptionPane.ERROR_MESSAGE);
				return;
			}

			// Validates that the quantity field contains only numbers
			Integer quantity = null;
			try {
				quantity = Integer.parseInt(txtQuantity.getText());
				if (quantity <= 0) {
					throw new NumberFormatException(); // Number of books must be positive and greater than zero
				}
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "A quantidade deve ser um valor inteiro válido.", "Erro",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			// Validates whether the number of books to be added is greater than zero
			if (Integer.parseInt(txtQuantity.getText()) < 0) {
				JOptionPane.showMessageDialog(this, "A quantidade de livros deve ser maior que zero.", "Erro",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			// Add the information entered
			book.setIsbn(Long.parseLong(txtIsbn.getText()));
			book.setTitle(txtTitle.getText());
			book.setAuthor(txtAuthor.getText());
			book.setGenre(txtGenre.getText());
			book.setPublisher(txtPublisher.getText());
			if (!txtEdition.getText().isEmpty()) {
				book.setEdition(Integer.parseInt(txtEdition.getText()));
			}
			book.setSalePrice(Double.parseDouble(txtSalePrice.getText()));
			book.setQuantity(Integer.parseInt(txtQuantity.getText()));

			// Validates that all mandatory fields have been filled in
			if (txtIsbn.getText().isEmpty() || txtTitle.getText().isEmpty() || txtAuthor.getText().isEmpty()
					|| txtGenre.getText().isEmpty() || txtPublisher.getText().isEmpty()
					|| txtSalePrice.getText().isEmpty() || txtQuantity.getText().isEmpty()) {
				JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos obrigatórios.", "Erro",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			dao.addBook(book);

			JOptionPane.showMessageDialog(this, "Livro adicionado ao inventório com sucesso!", "Sucesso",
					JOptionPane.INFORMATION_MESSAGE);

			dashboard.refreshTable();

			dispose();
		} else {
			if (e.getSource() == btnReturn) {
				if (!txtIsbn.getText().isEmpty() || !txtEdition.getText().isEmpty() || !txtTitle.getText().isEmpty()
						|| !txtAuthor.getText().isEmpty() || !txtGenre.getText().isEmpty()
						|| !txtPublisher.getText().isEmpty() || !txtSalePrice.getText().isEmpty()
						|| !txtQuantity.getText().isEmpty()) {
					int response = JOptionPane.showConfirmDialog(this,
							"Você tem certeza que deseja voltar? Todos os dados preenchidos serão perdidos.",
							"Confirmação", JOptionPane.YES_NO_OPTION);
					if (response == JOptionPane.YES_OPTION) {
						dispose();
					}
				} else {
					dispose();
				}
			}
		}
	}

	public static void main(String[] args) {
		AddBookForm frame = new AddBookForm(null);
		frame.setVisible(true);
	}
}