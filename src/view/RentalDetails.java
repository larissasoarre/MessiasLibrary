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
import java.text.SimpleDateFormat;

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
import dao.RentalDAO;
import model.BookSale;
import model.Rental;

public class RentalDetails extends JFrame implements ActionListener {

	// Create JLabels
	private JLabel lblId;
	private JLabel lblBookISBN;
	private JLabel lblRentalDate;
	private JLabel lblRentalDuration;
	private JLabel lblRentalPrice;
	private JLabel lblClientName;
	private JLabel lblClientEmail;
	private JLabel lblClientPhoneNumber;
	private JLabel lblClientAddress;
	private JLabel lblReturnDate;

	// Create the JPanel
	private JPanel pnlMain;
	private JPanel pnlInfo;
	private JPanel pnlButton;

	// Create the JButton
	private JButton btnReturned;

	// Images and logos that are used in the system
	private ImageIcon logo;

	// Connection to the database
	private RentalDAO dao;

	private Rental rental;
	private static RentalDashboard rentalDashboard;

	public RentalDetails(Rental rental, RentalDashboard rentalDashboard) {
		this.rental = rental;
		this.rentalDashboard = rentalDashboard;

		// Window Settings
		this.setTitle("Livraria Messias");
		this.setMinimumSize(new Dimension(500, 450));
		this.setLocationRelativeTo(null);
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

		// Populate JLabels with loan information
		constraints.gridx = 0;
		constraints.gridy = 0;
		lblId = new JLabel("<html><b>ID:</b> " + rental.getId() + "</html>");
		lblId.setFont(new Font("Inter", Font.PLAIN, 16));
		pnlInfo.add(lblId, constraints);

		constraints.gridy = 1;
		lblBookISBN = new JLabel("<html><b>ISBN:</b> " + rental.getBookISBN() + "</html>");
		lblBookISBN.setFont(new Font("Inter", Font.PLAIN, 16));
		pnlInfo.add(lblBookISBN, constraints);

		constraints.gridy = 2;
		lblClientName = new JLabel("<html><b>CLIENTE:</b> " + rental.getName() + "</html>");
		lblClientName.setFont(new Font("Inter", Font.PLAIN, 16));
		pnlInfo.add(lblClientName, constraints);

		constraints.gridy = 3;
		lblClientEmail = new JLabel("<html><b>E-MAIL:</b> " + rental.getEmail() + "</html>");
		lblClientEmail.setFont(new Font("Inter", Font.PLAIN, 16));
		pnlInfo.add(lblClientEmail, constraints);

		constraints.gridy = 4;
		lblClientPhoneNumber = new JLabel("<html><b>TELEFONE:</b> " + rental.getPhoneNumber() + "</html>");
		lblClientPhoneNumber.setFont(new Font("Inter", Font.PLAIN, 16));
		pnlInfo.add(lblClientPhoneNumber, constraints);

		constraints.gridy = 6;
		lblClientAddress = new JLabel("<html><b>ENDEREÇO:</b> " + rental.getStreet() + ", " + rental.getHouseNumber()
				+ "<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
				+ rental.getDistrict()
				+ "<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
				+ rental.getCity() + " - " + rental.getState()
				+ "<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
				+ rental.getZipcode() + "</html>");
		lblClientAddress.setFont(new Font("Inter", Font.PLAIN, 16));
		pnlInfo.add(lblClientAddress, constraints);

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		constraints.gridy = 7;
		lblRentalDate = new JLabel(
				"<html><b>DATA DE EMPRÉSTIMO:</b> " + dateFormat.format(rental.getRentalDate()) + "</html>");
		lblRentalDate.setFont(new Font("Inter", Font.PLAIN, 16));
		pnlInfo.add(lblRentalDate, constraints);

		constraints.gridy = 8;
		lblReturnDate = new JLabel(
				"<html><b>DATA DE RETORNO:</b> " + dateFormat.format(rental.getReturnDate()) + "</html>");
		lblReturnDate.setFont(new Font("Inter", Font.PLAIN, 16));
		pnlInfo.add(lblReturnDate, constraints);

		constraints.gridy = 9;
		lblRentalDuration = new JLabel("<html><b>DURAÇÃO:</b> " + rental.getRentalDuration() + " dias" + "</html>");
		lblRentalDuration.setFont(new Font("Inter", Font.PLAIN, 16));
		pnlInfo.add(lblRentalDuration, constraints);
		
		constraints.gridy = 10;
		DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
		lblRentalPrice = new JLabel("<html><b>TOTAL: </b>" + "R$" + decimalFormat.format(rental.getRentalPrice()).replace(".", ",") + "</html>");
		lblRentalPrice.setFont(new Font("Inter", Font.PLAIN, 16));
		pnlInfo.add(lblRentalPrice, constraints);

		pnlMain.add(pnlInfo);

		pnlButton = new JPanel(new FlowLayout());
		pnlButton.setBackground(new Color(0xEAE7EE));

		Border lineBorder2 = BorderFactory.createLineBorder(new Color(0x5E00D7), 2);
		Border paddingBorder2 = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		Border compoundBorder2 = BorderFactory.createCompoundBorder(lineBorder2, paddingBorder2);

		btnReturned = new JButton("RETORNAR LIVRO");
		btnReturned.setPreferredSize(new Dimension(140, 25));
		btnReturned.setFont(new Font("Inter", Font.BOLD, 13));
		btnReturned.setBackground(new Color(0xEDEDF2));
		btnReturned.setForeground(new Color(0x5E00D7));
		btnReturned.setBorder(compoundBorder2);
		btnReturned.addActionListener(this);
		pnlButton.add(btnReturned);

		pnlMain.add(pnlButton);
		this.add(pnlMain);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		UIManager.put("Button.background", new Color(0x5E00D7));
		UIManager.put("Button.foreground", new Color(0xFFFFFF));
		UIManager.put("Button.font", new Font("Inter", Font.BOLD, 13));
		UIManager.put("Button.border", BorderFactory.createEmptyBorder(5, 10, 5, 10));

		if (e.getSource() == btnReturned) {
			// Define the strings for the "yes" and "no" buttons in Portuguese
			UIManager.put("OptionPane.yesButtonText", "SIM");
			UIManager.put("OptionPane.noButtonText", "NÃO");
			UIManager.put("Button.background", new Color(0x5E00D7));
			UIManager.put("Button.foreground", new Color(0xFFFFFF));
			UIManager.put("Button.font", new Font("Inter", Font.BOLD, 13));

			int confirm = JOptionPane.showConfirmDialog(this,
					"Tem certeza que deseja marcar esse livro como retornado?", "Confirmação",
					JOptionPane.YES_NO_OPTION);

			if (confirm == JOptionPane.YES_OPTION) {
				dao = new RentalDAO();
				dao.delete(rental);

				JOptionPane.showMessageDialog(this, "Empréstimo retornado com sucesso.");
				rentalDashboard.refreshTable();
				dispose();
			}
		}
	}

	public static void main(String[] args) {
		Rental rental = new Rental();
		RentalDetails frame = new RentalDetails(rental, rentalDashboard);
		frame.setVisible(true);
	}
}
