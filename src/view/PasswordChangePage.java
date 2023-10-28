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

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;

import org.mindrot.jbcrypt.BCrypt;

import dao.UserLoginDAO;
import model.UserLogin;

public class PasswordChangePage extends JFrame implements ActionListener {
	// JLabels to indicate the content of each field
	private JLabel lblNewPassword;
	private JLabel lblConfirmPassword;

	// JPasswordFields to allow typing information
	private JPasswordField txtNewPassword;
	private JPasswordField txtConfirmPassword;

	// JButton to allow user interaction
	private JButton btnChangePassword;

	// JPanels to organize visual elements
	private JPanel pnlForm;
	private JPanel pnlButton;
	private JPanel pnlMain;

	// Images and logos that are used in the system
	private ImageIcon logo;

	// Attributes to store user information and manipulate the database
	private static UserLogin user;
	private UserLoginDAO dao;

	public PasswordChangePage(UserLogin user) {
		this.user = user;
		dao = new UserLoginDAO();

		// Window Settings
		setTitle("Livraria Messias");
		setSize(390, 245);
		setResizable(false);
		setLocationRelativeTo(null);
		getContentPane().setBackground(new Color(0xEAE7EE));

		// System window logo
		logo = new ImageIcon(LoginPage.class.getResource("/logo.png"));
		this.setIconImage(logo.getImage());

		// 
		Main Panel with GridBagLayout
		pnlMain = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.CENTER;

		// Form panel
		gbc.gridx = 0;
		gbc.gridy = 0;
		pnlForm = new JPanel(new GridBagLayout());

		// Instantiate JLabels and JTextFields
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets = new Insets(5, 5, 5, 5);
		constraints.anchor = GridBagConstraints.LINE_START;

		Border lineBorder1 = BorderFactory.createLineBorder(new Color(0x111315), 1);
		Border paddingBorder1 = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		Border compoundBorder1 = BorderFactory.createCompoundBorder(lineBorder1, paddingBorder1);

		constraints.gridx = 0;
		constraints.gridy = 0;
		lblNewPassword = new JLabel("NOVA SENHA:");
		lblNewPassword.setFont(new Font("Inter", Font.PLAIN, 13));
		pnlForm.add(lblNewPassword, constraints);

		constraints.gridx = 0;
		constraints.gridy = 1;
		txtNewPassword = new JPasswordField(30);
		txtNewPassword.setFont(new Font("Inter", Font.PLAIN, 13));
		txtNewPassword.setBackground(new Color(0xEDEDF2));
		txtNewPassword.setBorder(compoundBorder1);
		pnlForm.add(txtNewPassword, constraints);

		constraints.gridx = 0;
		constraints.gridy = 2;
		lblConfirmPassword = new JLabel("SENHA:");
		lblConfirmPassword.setFont(new Font("Inter", Font.PLAIN, 13));
		pnlForm.add(lblConfirmPassword, constraints);

		constraints.gridx = 0;
		constraints.gridy = 3;
		txtConfirmPassword = new JPasswordField(30);
		txtConfirmPassword.setFont(new Font("Inter", Font.PLAIN, 13));
		txtConfirmPassword.setBackground(new Color(0xEDEDF2));
		txtConfirmPassword.setBorder(compoundBorder1);
		pnlForm.add(txtConfirmPassword, constraints);

		// Add pnlForm to pnlMain
		pnlMain.add(pnlForm, gbc);

		// Button Panel
		gbc.gridy = 2;
		pnlButton = new JPanel(new FlowLayout());

		btnChangePassword = new JButton("MUDAR SENHA");
		btnChangePassword.setBorderPainted(false);
		btnChangePassword.setPreferredSize(new Dimension(150, 25));
		btnChangePassword.setFont(new Font("Inter", Font.BOLD, 13));
		btnChangePassword.setBackground(new Color(0x5E00D7));
		btnChangePassword.setForeground(new Color(0xFFFFFF));
		btnChangePassword.addActionListener(this);
		pnlButton.add(btnChangePassword);

		// Add pnlButton to pnlMain
		pnlMain.add(pnlButton, gbc);

		// Center pnlMain in the frame
		this.add(pnlMain, BorderLayout.CENTER);
	}

	// Function that checks whether the password passes the acceptance criteria
	private boolean isPasswordValid(String password) {
		// Check if the password has at least 8 characters
		if (password.length() < 8) {
			return false;
		}

		// Check for at least one lowercase letter, one uppercase letter, one number,
		// and one special character
		boolean hasLower = false, hasUpper = false, hasDigit = false, hasSpecial = false;
		for (char c : password.toCharArray()) {
			if (Character.isLowerCase(c)) {
				hasLower = true;
			} else if (Character.isUpperCase(c)) {
				hasUpper = true;
			} else if (Character.isDigit(c)) {
				hasDigit = true;
			} else if (!Character.isLetterOrDigit(c)) {
				hasSpecial = true;
			}
		}

		return hasLower && hasUpper && hasDigit && hasSpecial;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnChangePassword) {
			char[] newPasswordChars = txtNewPassword.getPassword();
			char[] confirmPasswordChars = txtConfirmPassword.getPassword();

			String newPassword = new String(newPasswordChars);
			String confirmPassword = new String(confirmPasswordChars);

			if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Preencha os dois campos de senha.", "Erro",
						JOptionPane.ERROR_MESSAGE);
			} else if (!newPassword.equals(confirmPassword)) {
				JOptionPane.showMessageDialog(this, "As senhas não coincidem.", "Erro", JOptionPane.ERROR_MESSAGE);
			} else if (!isPasswordValid(newPassword)) {
				JOptionPane.showMessageDialog(this,
						"<html><div style='text-align: center; width: 290px;'>A senha deve ter pelo menos 8 caracteres, uma letra minúscula, uma letra maiúscula, um número e um caractere especial.</div></html>",
						"Erro", JOptionPane.ERROR_MESSAGE);
			} else {
				// Update new password
				user.setPassword(newPassword);

				// Sets TEMP_PASSWORD to 0 to indicate that this is no longer a temporary 
				// password
				user.setTemporaryPassword(0);

				// Updates the user's password in the database
				dao.updateUserPassword(user);

				JOptionPane.showMessageDialog(this, "Senha alterada com sucesso!", "Sucesso",
						JOptionPane.INFORMATION_MESSAGE);

				dispose();

				Dashboard dashboard = new Dashboard();
				dashboard.setVisible(true);
				dashboard.setDefaultCloseOperation(EXIT_ON_CLOSE);
			}
		}
	}

	public static void main(String[] args) {
		PasswordChangePage frame = new PasswordChangePage(user);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setVisible(true);

	}
}
