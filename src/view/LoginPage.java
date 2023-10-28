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
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;

import org.mindrot.jbcrypt.BCrypt;

import dao.UserLoginDAO;
import model.UserLogin;

public class LoginPage extends JFrame implements ActionListener {
	// JLabels to indicate what each field contains
	private JLabel lblExtLogo;
	private JLabel lblUsername;
	private JLabel lblPassword;

	// JTextFields to allow typing information
	private JTextField txtUsername;
	private JPasswordField txtPassword;

	// JButton to allow user interaction
	private JButton btnLogin;

	// JPanels to organize visual elements
	private JPanel pnlExtLogo;
	private JPanel pnlForm;
	private JPanel pnlButton;
	private JPanel pnlMain;

	// Images and logos that are used in the system
	private ImageIcon logo;
	private ImageIcon extendedLogo;

	// Attributes to store user information and manipulate the database
	private UserLogin user;
	private UserLoginDAO dao;

	public LoginPage() {

		// Window Settings
		this.setTitle("Livraria Messias");
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setMinimumSize(new Dimension(500, 500));
		this.setLayout(new BorderLayout());
		this.getContentPane().setBackground(new Color(0xEAE7EE));
		this.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				Dimension newSize = getSize();
				if (newSize.width < 500 || newSize.height < 500) {
					setSize(Math.max(newSize.width, 500), Math.max(newSize.height, 500));
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

		// Panel with logo
		gbc.gridx = 0;
		gbc.gridy = 0;
		pnlExtLogo = new JPanel(new FlowLayout());

		// Instance JLabels and Image
		lblExtLogo = new JLabel();
		extendedLogo = new ImageIcon(LoginPage.class.getResource("/extended-logo.png"));
		lblExtLogo.setIcon(extendedLogo);
		pnlExtLogo.add(lblExtLogo);

		// Add pnlExtLogo to pnlMain
		pnlMain.add(pnlExtLogo, gbc);

		// Form panel
		gbc.gridy = 1;
		pnlForm = new JPanel(new GridBagLayout());

		// Instantiate JLabels and JTextFields
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets = new Insets(5, 5, 5, 5);
		constraints.anchor = GridBagConstraints.LINE_START;

		// Username
		constraints.gridx = 0;
		constraints.gridy = 0;
		lblUsername = new JLabel("USUÁRIO:");
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

		// User password
		constraints.gridx = 0;
		constraints.gridy = 2;
		lblPassword = new JLabel("SENHA:");
		lblPassword.setFont(new Font("Inter", Font.PLAIN, 13));
		pnlForm.add(lblPassword, constraints);

		constraints.gridx = 0;
		constraints.gridy = 3;
		txtPassword = new JPasswordField(30);
		txtPassword.setFont(new Font("Inter", Font.PLAIN, 13));
		txtPassword.setBackground(new Color(0xEDEDF2));
		Border lineBorder2 = BorderFactory.createLineBorder(new Color(0x111315), 1);
		Border paddingBorder2 = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		Border compoundBorder2 = BorderFactory.createCompoundBorder(lineBorder2, paddingBorder2);
		txtPassword.setBorder(compoundBorder2);
		pnlForm.add(txtPassword, constraints);

		// Add pnlForm to pnlMain
		pnlMain.add(pnlForm, gbc);

		// Button Panel
		gbc.gridy = 2;
		pnlButton = new JPanel(new FlowLayout());

		btnLogin = new JButton("ENTRAR");
		btnLogin.setBorderPainted(false);
		btnLogin.setPreferredSize(new Dimension(110, 25));
		btnLogin.setFont(new Font("Inter", Font.BOLD, 13));
		btnLogin.setBackground(new Color(0x5E00D7));
		btnLogin.setForeground(new Color(0xFFFFFF));
		btnLogin.addActionListener(this);
		pnlButton.add(btnLogin);

		// Add pnlButton to pnlMain
		pnlMain.add(pnlButton, gbc);

		// Center pnlMain in the frame
		this.add(pnlMain, BorderLayout.CENTER);

		// Instance of handling attributes
		user = new UserLogin();
		dao = new UserLoginDAO();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		UIManager.put("Button.background", new Color(0x5E00D7));
		UIManager.put("Button.foreground", new Color(0xFFFFFF));
		UIManager.put("Button.font", new Font("Inter", Font.BOLD, 13));
		UIManager.put("Button.border", BorderFactory.createEmptyBorder(5, 10, 5, 10));

		if (e.getSource() == btnLogin) {
			String username = txtUsername.getText();
			char[] passwordChars = txtPassword.getPassword();
			String password = new String(passwordChars);

			user = dao.getUserbyUsername(username);

			// Check login data
			if (username.isEmpty() || password.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Preencha todos os campos.", "Erro de Login",
						JOptionPane.ERROR_MESSAGE);
			} else {
				if (user != null && BCrypt.checkpw(password, user.getPassword())) {
					if (user.isTemporaryPassword()) {
						// Create a custom JOptionPane
						Object[] options = { "OK" };
						int choice = JOptionPane.showOptionDialog(this,
								"<html><div style='text-align: center; width: 290px;'>Você está acessando o sistema com uma senha temporária e deve alterá-la imediatamente.</div></html>",
								"Alterar Senha", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options,
								options[0]);

						if (choice == 0) { // User clicked OK
							PasswordChangePage passwordChange = new PasswordChangePage(user);
							passwordChange.setVisible(true);
						}
					} else {
						Dashboard dashboard = new Dashboard();
						dashboard.setDefaultCloseOperation(EXIT_ON_CLOSE);
						dashboard.setVisible(true);
					}
					dispose();
				} else {
					JOptionPane.showMessageDialog(this, "Usuário ou senha incorretos.", "Erro de Login",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}

	public static void main(String[] args) {
		LoginPage frame = new LoginPage();
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setVisible(true);

	}
}