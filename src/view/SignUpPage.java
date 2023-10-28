package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
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
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicComboBoxUI;

import dao.UserLoginDAO;
import model.UserLogin;

public class SignUpPage extends JFrame implements ActionListener {

	// JLabels to indicate what each field contains
	private JLabel lblUsername;
	private JLabel lblPassword;
	private JLabel lblAccessType;
	private JLabel lblNote;

	// JTextFields to allow typing information
	private JTextField txtUsername;
	private JPasswordField txtPassword;

	// JButton to allow user interaction
	private JButton bntSignUp;
	private JButton btnReturn;
	private JButton btnGeneratePass;

	// JOptionPane to allow user interaction
	private String[] accessType = { "Administrador", "Bibliotecário" };
	private JComboBox<String> options;

	// JPanels to organize visual elements
	private JPanel pnlForm;
	private JPanel pnlButton;
	private JPanel pnlMain;
	private JPanel pnlPassword;

	// Images and logos that are used in the system
	private ImageIcon logo;

	// Attributes to store user information and manipulate the database
	private UserLogin user;
	private UserLoginDAO dao;
	private GridBagConstraints constraints;

	public SignUpPage() {

		// Window Settings
		this.setTitle("Livraria Messias");
		this.setMinimumSize(new Dimension(420, 350));
		this.setLocationRelativeTo(null);
		this.setLayout(new BorderLayout());
		this.getContentPane().setBackground(new Color(0xEAE7EE));
		this.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				Dimension newSize = getSize();
				if (newSize.width < 400 || newSize.height < 340) {
					setSize(Math.max(newSize.width, 400), Math.max(newSize.height, 340));
				}
			}
		});

		// System window logo
		logo = new ImageIcon(LoginPage.class.getResource("/logo.png"));
		this.setIconImage(logo.getImage());

		// Main Panel with GridBagLayout
		pnlMain = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.CENTER;

		// Form panel
		gbc.gridy = 1;
		pnlForm = new JPanel(new GridBagLayout());

		// Instantiate JLabels and JTextFields
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(5, 5, 5, 5);
		constraints.anchor = GridBagConstraints.LINE_START;

		// Username
		constraints.gridx = 0;
		constraints.gridy = 0;
		lblUsername = new JLabel("NOVO USUÁRIO:*");
		lblUsername.setFont(new Font("Inter", Font.PLAIN, 13));
		pnlForm.add(lblUsername, constraints);

		constraints.gridx = 0;
		constraints.gridy = 1;
		txtUsername = new JTextField(30);
		txtUsername.setFont(new Font("Inter", Font.PLAIN, 13));
		txtUsername.setBackground(new Color(0xEDEDF2));
		Border lineBorder1 = BorderFactory.createLineBorder(new Color(0x111315), 1);
		Border paddingBorder1 = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		Border compoundBorder1 = BorderFactory.createCompoundBorder(lineBorder1, paddingBorder1);
		txtUsername.setBorder(compoundBorder1);
		pnlForm.add(txtUsername, constraints);

		// Password Panel with GridBagLayout
		constraints.gridx = 0;
		constraints.gridy = 2;
		pnlPassword = new JPanel(new GridBagLayout());
		GridBagConstraints consts = new GridBagConstraints();
		consts.insets = new Insets(5, 0, 5, 5);
		consts.anchor = GridBagConstraints.WEST;

		// User password
		consts.gridx = 0;
		consts.gridy = 0;
		lblPassword = new JLabel("SENHA TEMPORÁRIA:*");
		lblPassword.setFont(new Font("Inter", Font.PLAIN, 13));
		pnlPassword.add(lblPassword, consts);

		consts.gridx = 0;
		consts.gridy = 1;
		txtPassword = new JPasswordField(23);
		txtPassword.setFont(new Font("Inter", Font.PLAIN, 13));
		txtPassword.setBackground(new Color(0xEDEDF2));
		Border lineBorder2 = BorderFactory.createLineBorder(new Color(0x111315), 1);
		Border paddingBorder2 = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		Border compoundBorder2 = BorderFactory.createCompoundBorder(lineBorder2, paddingBorder2);
		txtPassword.setEchoChar('\u0000');
		txtPassword.setEditable(false);
		txtPassword.setBorder(compoundBorder2);
		pnlPassword.add(txtPassword, consts);

		// Generate random password button
		consts.gridx = 1;
		consts.gridy = 1;
		btnGeneratePass = new JButton("GERAR");
		btnGeneratePass.setPreferredSize(new Dimension(71, 30));
		btnGeneratePass.setFont(new Font("Inter", Font.BOLD, 13));
		btnGeneratePass.setBackground(new Color(0xEDEDF2));
		btnGeneratePass.setForeground(new Color(0x5E00D7));
		Border lineBorder3 = BorderFactory.createLineBorder(new Color(0x5E00D7), 2);
		Border paddingBorder3 = BorderFactory.createEmptyBorder(5, 5, 10, 5);
		Border compoundBorder3 = BorderFactory.createCompoundBorder(lineBorder3, paddingBorder3);
		btnGeneratePass.setBorder(lineBorder3);
		btnGeneratePass.addActionListener(this);
		pnlPassword.add(btnGeneratePass, consts);

		pnlForm.add(pnlPassword, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 3;
		lblNote = new JLabel("<html><b>Importante:</b> Anote a senha que for gerada pois ela não será<br>mostrada novamente.</html>");
		lblNote.setFont(new Font("Inter", Font.PLAIN, 11));
		pnlForm.add(lblNote, constraints);

		// Dropdown styles
		UIManager.put("ComboBox.background", new Color(0xEDEDF2));
		UIManager.put("ComboBox.foreground", new Color(0x000000));

		// Set the border color to black
		Border blackBorder = BorderFactory.createLineBorder(Color.BLACK, 1);
		UIManager.put("ComboBox.border", blackBorder);

		// Set the color of the arrow (caret) inside the JComboBox
		UIManager.put("ComboBox.buttonBackground", new Color(0xFFFFFF));

		// Remove the arrow border and padding
		UIManager.put("ComboBox.buttonDarkShadow", new Color(0xEDEDF2));
		UIManager.put("ComboBox.buttonHighlight", new Color(0xFFFFFF));
		UIManager.put("ComboBox.buttonShadow", new Color(0xEDEDF2));

		// Set the color of the dropdown menu
		UIManager.put("ComboBox.selectionBackground", new Color(0x5E00D7));
		UIManager.put("ComboBox.selectionForeground", new Color(0xFFFFFF));
		
		// Add the access type
		constraints.gridx = 0;
		constraints.gridy = 4;
		lblAccessType = new JLabel("TIPO DE ACESSO:*");
		lblAccessType.setFont(new Font("Inter", Font.PLAIN, 13));
		pnlForm.add(lblAccessType, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 5;
		options = new JComboBox<>(accessType);
		options.setPreferredSize(new Dimension(342, options.getPreferredSize().height));
		pnlForm.add(options, constraints);
		
		

		// Add pnlForm to pnlMain
		pnlMain.add(pnlForm, gbc);

		// Button Panel
		gbc.gridy = 2;
		pnlButton = new JPanel(new FlowLayout());
		

		Border padding = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		Border compound = BorderFactory.createCompoundBorder(lineBorder3, padding);

		// Instantiate the JButton
		btnReturn = new JButton("VOLTAR");
		btnReturn.setPreferredSize(new Dimension(110, 25));
		btnReturn.setFont(new Font("Inter", Font.BOLD, 13));
		btnReturn.setBackground(new Color(0xEDEDF2));
		btnReturn.setForeground(new Color(0x5E00D7));
		btnReturn.setBorder(compound);
		btnReturn.addActionListener(this);
		pnlButton.add(btnReturn);

		bntSignUp = new JButton("CADASTRAR");
		bntSignUp.setBorderPainted(false);
		bntSignUp.setPreferredSize(new Dimension(130, 25));
		bntSignUp.setFont(new Font("Inter", Font.BOLD, 13));
		bntSignUp.setBackground(new Color(0x5E00D7));
		bntSignUp.setForeground(new Color(0xFFFFFF));
		bntSignUp.addActionListener(this);
		pnlButton.add(bntSignUp);

		// Add pnlButton to pnlMain
		pnlMain.add(pnlButton, gbc);

		// Center pnlMain in the frame
		this.add(pnlMain, BorderLayout.CENTER);

		// Instantiates handling attributes
		user = new UserLogin();
		dao = new UserLoginDAO();
	}

	private void generateAndDisplayRandomPassword() {
		String randomPassword = dao.generateRandomPassword();
		txtPassword.setText(randomPassword);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		UIManager.put("Button.background", new Color(0x5E00D7));
		UIManager.put("Button.foreground", new Color(0xFFFFFF));
		UIManager.put("Button.font", new Font("Inter", Font.BOLD, 13));
		UIManager.put("Button.border", BorderFactory.createEmptyBorder(5, 10, 5, 10));

		if (e.getSource() == bntSignUp) {
			try {
				user.setUsername(txtUsername.getText());
				char[] passwordChars = txtPassword.getPassword();
				String password = new String(passwordChars);
				user.setPassword(password);

				// Check if the username already exists
				if (dao.usernameExists(user.getUsername())) {
					JOptionPane.showMessageDialog(this, "Este nome de usuário já existe", "Erro",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				// Check if all fields have been filled in
				if (user.getUsername().isEmpty() || user.getPassword().isEmpty()) {
					JOptionPane.showMessageDialog(this, "Preencha todos os campos.", "Erro", JOptionPane.ERROR_MESSAGE);
					return;
				}

				// Get the selected option from the dropdown
				String selectedOption = (String) options.getSelectedItem();

				// Create the user with the selected option
				dao.createUserWithTemporaryPassword(user, selectedOption);

				JOptionPane.showMessageDialog(this, "Cadastro realizado com sucesso.", "Sucesso",
						JOptionPane.INFORMATION_MESSAGE);

				dispose();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else {
			if (e.getSource() == btnReturn) {
				dispose();
			} else {
				if (e.getSource() == btnGeneratePass) {
					generateAndDisplayRandomPassword();
				}
			}
		}
	}

	public static void main(String[] args) {
		SignUpPage frame = new SignUpPage();
		frame.setVisible(true);

	}
}