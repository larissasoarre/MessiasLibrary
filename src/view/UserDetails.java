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
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.Border;
import dao.BookSaleDAO;
import dao.UserLoginDAO;
import model.BookSale;
import model.UserLogin;

class UserDetails extends JFrame implements ActionListener {

	// Create JLabels
	private JLabel lblId;
	private JLabel lblUsername;
	private JLabel lblPassType;
	private JLabel lblPermission;

	// Create JButton
	private JButton btnDelete;

	// Create JPanel
	private JPanel pnlMain;
	private JPanel pnlInfo;
	private JPanel pnlButton;

	private UserLogin user;
	private static AdminDashboard adminDashboard;
	private UserLoginDAO dao;

	// Images and logos that are used in the system
	private ImageIcon logo;

	public UserDetails(UserLogin user, AdminDashboard adminDashboard) {
		this.user = user;
		this.adminDashboard = adminDashboard;

		// Window Settings
		this.setTitle("Livraria Messias");
		this.setLocationRelativeTo(null);
		this.setMinimumSize(new Dimension(310, 300));
		this.setLocationRelativeTo(null);
		this.setLayout(new BorderLayout());
		this.getContentPane().setBackground(new Color(0xEAE7EE));
		this.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				Dimension newSize = getSize();
				if (newSize.width < 310 || newSize.height < 300) {
					setSize(Math.max(newSize.width, 310), Math.max(newSize.height, 300));
				}
			}
		});

		// System window logo
		logo = new ImageIcon(LoginPage.class.getResource("/logo.png"));
		this.setIconImage(logo.getImage());

		// Main Panel Settings
		pnlMain = new JPanel();
		pnlMain.setLayout(new BoxLayout(pnlMain, BoxLayout.Y_AXIS));
		pnlMain.setBackground(new Color(0xEAE7EE));
		pnlMain.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		pnlInfo = new JPanel(new GridBagLayout());
		pnlInfo.setBackground(new Color(0xEAE7EE));
		pnlInfo.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets = new Insets(5, 5, 5, 5);
		constraints.anchor = GridBagConstraints.WEST;

		// Populating JLabels with book information
		constraints.gridx = 0;
		constraints.gridy = 0;
		lblId = new JLabel("<html><b>ID:</b> " + user.getId() + "</html>");
		lblId.setFont(new Font("Inter", Font.PLAIN, 16));
		pnlInfo.add(lblId, constraints);

		constraints.gridy = 1;
		lblUsername = new JLabel("<html><b>USUÁRIO:</b> " + user.getUsername() + "</html>");
		lblUsername.setFont(new Font("Inter", Font.PLAIN, 16));
		pnlInfo.add(lblUsername, constraints);

		constraints.gridy = 2;
		lblPassType = new JLabel(
				"<html><b>TIPO DE SENHA:</b> " + getPasswordTypeLabel(user.getTemporaryPassword()) + "</html>");
		lblPassType.setFont(new Font("Inter", Font.PLAIN, 16));
		pnlInfo.add(lblPassType, constraints);

		constraints.gridy = 3;
		lblPermission = new JLabel("<html><b>PERMISSÃO:</b> " + getPermissionLabel(user.getIsAdmin()) + "</html>");
		lblPermission.setFont(new Font("Inter", Font.PLAIN, 16));
		pnlInfo.add(lblPermission, constraints);

		pnlMain.add(pnlInfo);

		// Button Panel
		pnlButton = new JPanel(new FlowLayout());
		pnlButton.setBackground(new Color(0xEAE7EE));

		btnDelete = new JButton("DELETAR");
		btnDelete.setBorderPainted(false);
		btnDelete.setFont(new Font("Inter", Font.BOLD, 13));
		btnDelete.setBackground(new Color(0x5E00D7));
		btnDelete.setForeground(new Color(0xFFFFFF));
		btnDelete.addActionListener(this);
		pnlButton.add(btnDelete);

		pnlMain.add(pnlButton);
		this.add(pnlMain);
	}

	private String getPasswordTypeLabel(int tempPasswordValue) {
		if (tempPasswordValue == 0) {
			return "Permanente";
		} else if (tempPasswordValue == 1) {
			return "Temporária";
		} else {
			return "Desconhecido";
		}
	}

	private String getPermissionLabel(String isAdmin) {
		if ("Y".equals(isAdmin)) {
			return "Administrador";
		} else if ("N".equals(isAdmin)) {
			return "Bibliotecário";
		} else {
			return "Desconhecido"; // Handle other cases as needed
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		UIManager.put("Button.background", new Color(0x5E00D7));
		UIManager.put("Button.foreground", new Color(0xFFFFFF));
		UIManager.put("Button.font", new Font("Inter", Font.BOLD, 13));
		UIManager.put("Button.border", BorderFactory.createEmptyBorder(5, 10, 5, 10));

		if (e.getSource() == btnDelete) {
			// Define the strings for the “yes” and “no” buttons in Portuguese
			UIManager.put("OptionPane.yesButtonText", "SIM");
			UIManager.put("OptionPane.noButtonText", "NÃO");
			UIManager.put("Button.background", new Color(0x5E00D7));
			UIManager.put("Button.foreground", new Color(0xFFFFFF));
			UIManager.put("Button.font", new Font("Inter", Font.BOLD, 13));

			int confirm = JOptionPane.showConfirmDialog(this,
					"Tem certeza que deseja excluir esse usuário? Todas os dados serão perdidos", "Confirmação",
					JOptionPane.YES_NO_OPTION);

			if (confirm == JOptionPane.YES_OPTION) {
				dao = new UserLoginDAO();
				dao.delete(user);

				JOptionPane.showMessageDialog(this, "Usuário excluído com sucesso.");
				adminDashboard.refreshTable();
				dispose();
			}
		}
	}

	public static void main(String[] args) {
		UserLogin user = new UserLogin();
		UserDetails frame = new UserDetails(user, adminDashboard);
		frame.setVisible(true);
	}
}