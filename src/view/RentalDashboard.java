package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import dao.RentalDAO;
import model.CustomScrollBar;
import model.Rental;

public class RentalDashboard extends JFrame implements ActionListener {

	// Main JLabels
	private JLabel lblExtLogo;
	private JLabel lblRentals;
	private JLabel lblMainTitle;

	// JButtons
	private JButton btnAddRental;
	private JButton btnSignOut;
	private JButton btnBookDashboard;
	private JButton btnAdminDashboard;

	// JPanels to organize visual elements
	private JPanel pnlControl;
	private JPanel pnlMain;
	private JPanel pnlTop;
	private JPanel pnlBookInfo;
	private JPanel pnlControlBtns;

	// JTable for displaying data
	private JTable table;

	// Images and logos that are used in the system
	private ImageIcon logo;
	private ImageIcon extendedLogo;

	private RentalDashboard rentalDashboard;
	private Dashboard dashboard;

	// Attributes to store user information and manipulate the database
	private Rental rental;
	private RentalDAO dao;

	public RentalDashboard() {

		rentalDashboard = this;

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

		// Side control panel
		pnlControl = new JPanel();
		pnlControl.setLayout(new BoxLayout(pnlControl, BoxLayout.Y_AXIS));
		pnlControl.setBackground(new Color(0xDCD9DF));
		pnlControl.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));

		// Instance JLabels
		lblExtLogo = new JLabel();
		extendedLogo = new ImageIcon(Dashboard.class.getResource("/extended-logo.png"));
		lblExtLogo.setIcon(extendedLogo);
		lblExtLogo.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblExtLogo.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
		pnlControl.add(lblExtLogo);

		// Separator
		pnlControl.add(Box.createRigidArea(new Dimension(30, 30)));

		// Button to return to the home dashboard
		btnBookDashboard = new JButton("Livros");
		btnBookDashboard.setBorderPainted(false);
		btnBookDashboard.setFont(new Font("Inter", Font.BOLD, 15));
		btnBookDashboard.setBackground(new Color(0xDCD9DF));
		btnBookDashboard.setForeground(new Color(0x000000));
		btnBookDashboard.addActionListener(this);
		btnBookDashboard.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnBookDashboard.setBorder(BorderFactory.createEmptyBorder(9, 75, 9, 75));
		btnBookDashboard.setPreferredSize(new Dimension(250, 35));
		btnBookDashboard.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				btnBookDashboard.setBackground(new Color(0xCECBD1));
			}

			public void mouseExited(MouseEvent e) {
				btnBookDashboard.setBackground(new Color(0xDCD9DF));
			}
		});

		pnlControl.add(btnBookDashboard);

		// Separator
		pnlControl.add(Box.createRigidArea(new Dimension(10, 10)));

		JPanel pnlRental = new JPanel();
		pnlRental.setLayout(new BoxLayout(pnlRental, BoxLayout.X_AXIS));
		pnlRental.setBackground(new Color(0x5E00D7));
		pnlRental.setAlignmentX(Component.CENTER_ALIGNMENT);
		pnlRental.setBorder(BorderFactory.createEmptyBorder(9, 46, 9, 46));
		pnlRental.setPreferredSize(new Dimension(250, 35));

		lblRentals = new JLabel("Empréstimos");
		lblRentals.setFont(new Font("Inter", Font.BOLD, 16));
		lblRentals.setAlignmentX(Component.LEFT_ALIGNMENT);
		lblRentals.setForeground(new Color(0xFFFFFF));
		pnlRental.add(lblRentals);

		pnlControl.add(pnlRental);
		
		// Separator
		pnlControl.add(Box.createRigidArea(new Dimension(10, 10)));

		// Button to return to the administration dashboard
		btnAdminDashboard = new JButton("Administração");
		btnAdminDashboard.setBorderPainted(false);
		btnAdminDashboard.setFont(new Font("Inter", Font.BOLD, 15));
		btnAdminDashboard.setBackground(new Color(0xDCD9DF));
		btnAdminDashboard.setForeground(new Color(0x000000));
		btnAdminDashboard.addActionListener(this);
		btnAdminDashboard.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnAdminDashboard.setBorder(BorderFactory.createEmptyBorder(9, 45, 9, 45));
		btnAdminDashboard.setPreferredSize(new Dimension(252, 35));
		btnAdminDashboard.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				btnAdminDashboard.setBackground(new Color(0xCECBD1));
			}

			public void mouseExited(MouseEvent e) {
				btnAdminDashboard.setBackground(new Color(0xDCD9DF));
			}
		});

		pnlControl.add(btnAdminDashboard);

		pnlControlBtns = new JPanel();
		pnlControlBtns.setLayout(new BoxLayout(pnlControlBtns, BoxLayout.Y_AXIS));
		pnlControlBtns.setBackground(new Color(0xDCD9DF));

		Border lineBorder2 = BorderFactory.createLineBorder(new Color(0x5E00D7), 2);

		pnlControlBtns.add(Box.createRigidArea(new Dimension(15, 15)));

		Border paddingSignOut = BorderFactory.createEmptyBorder(5, 15, 5, 15);
		Border compoundBorderSignOut = BorderFactory.createCompoundBorder(lineBorder2, paddingSignOut);

		btnSignOut = new JButton("ENCERRAR SESSÃO");
		btnSignOut.setPreferredSize(new Dimension(110, 25));
		btnSignOut.setFont(new Font("Inter", Font.BOLD, 13));
		btnSignOut.setBackground(new Color(0xDCD9DF));
		btnSignOut.setForeground(new Color(0x5E00D7));
		btnSignOut.setBorder(compoundBorderSignOut);
		btnSignOut.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnSignOut.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		btnSignOut.addActionListener(this);
		pnlControlBtns.add(btnSignOut);

		pnlControl.setLayout(new BoxLayout(pnlControl, BoxLayout.Y_AXIS));
		pnlControl.add(Box.createVerticalGlue());
		pnlControl.add(pnlControlBtns);

		this.add(pnlControl, BorderLayout.WEST);

		// Main Dashboard
		pnlMain = new JPanel(new BorderLayout());
		pnlMain.setBackground(new Color(0xEAE7EE));
		pnlMain.setBorder(BorderFactory.createEmptyBorder(20, 30, 10, 30));

		// Title panel and add book button
		pnlTop = new JPanel(new BorderLayout());
		pnlTop.setBackground(new Color(0xEAE7EE));

		lblMainTitle = new JLabel("Empréstimos");
		lblMainTitle.setFont(new Font("Inter", Font.BOLD, 40));
		pnlTop.add(lblMainTitle, BorderLayout.WEST);

		Box box = Box.createHorizontalBox();
		box.add(Box.createHorizontalGlue());

		btnAddRental = new JButton("NOVO EMPRÉSTIMO");
		btnAddRental.setBorderPainted(false);
		btnAddRental.setFont(new Font("Inter", Font.BOLD, 13));
		btnAddRental.setBackground(new Color(0x5E00D7));
		btnAddRental.setForeground(new Color(0xFFFFFF));
		btnAddRental.addActionListener(this);
		box.add(btnAddRental);
		pnlTop.add(box, BorderLayout.EAST);

		pnlMain.add(pnlTop, BorderLayout.NORTH);

		// Panel with list of loans
		pnlBookInfo = new JPanel(new GridLayout(1, 1));
		pnlBookInfo.setBackground(new Color(0xEAE7EE));
		pnlBookInfo.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0));

		// Create the JTable model
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("Nº Pedido");
		model.addColumn("ISBN");
		model.addColumn("Cliente");
		model.addColumn("Duração");
		model.addColumn("Preço");
		model.addColumn("Dt. Retorno");

		// Populate the model with data from the ArrayList
		dao = new RentalDAO();

		List<Rental> rentals = dao.selectAll();
		rentals.sort(Comparator.comparing(Rental::getReturnDate));
		for (Rental rental : rentals) {
			Object[] rowData = new Object[6];
			rowData[0] = rental.getId();
			rowData[1] = rental.getBookISBN();
			rowData[2] = rental.getName();
			rowData[3] = rental.getRentalDuration() + " dias";
			DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
			rowData[4] = decimalFormat.format(rental.getRentalPrice()).replace(".", ",");
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			rowData[5] = dateFormat.format(rental.getReturnDate());
			model.addRow(rowData);
		}

		// Create a new JTable with the model
		table = new JTable(model);

		table.setBackground(new Color(0xEAE7EE));
		table.setFont(new Font("Inter", Font.PLAIN, 16));
		table.setBorder(null);
		table.setRowHeight(50);
		table.getTableHeader().setReorderingAllowed(false);
		table.setGridColor(new Color(0, 0, 0, 0));
		table.setDefaultEditor(Object.class, null);

		// Custom each table cell
		TableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
						column);

				((JComponent) component).setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(0x8B8B8B)));
				setHorizontalAlignment(SwingConstants.CENTER);
				setVerticalAlignment(SwingConstants.CENTER);

				// Checks if the table row is selected
				if (isSelected) {
					component.setBackground(new Color(0xd8d1e1));
				} else {
					component.setBackground(table.getBackground());
				}

				return component;
			}
		};

		table.setDefaultRenderer(Object.class, cellRenderer);

		// Adjust the width of each column
		table.getColumnModel().getColumn(0).setPreferredWidth(100); // ID
		table.getColumnModel().getColumn(1).setPreferredWidth(180); // ISBN
		table.getColumnModel().getColumn(2).setPreferredWidth(250); // Client
		table.getColumnModel().getColumn(3).setPreferredWidth(80); // Duration
		table.getColumnModel().getColumn(4).setPreferredWidth(120); // Price
		table.getColumnModel().getColumn(5).setPreferredWidth(150); // Return Date

		// Create a mouse listener for table rows
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) { // Double-click event
					int selectedRow = table.getSelectedRow();
					if (selectedRow != -1) {
						// Retrieve the book information from the selected row
						int rentalId = (int) table.getValueAt(selectedRow, 0);
						rental = dao.selectById(rentalId);

						// Open a new window to display the book information
						RentalDetails rentalDetails = new RentalDetails(rental, rentalDashboard);
						rentalDetails.setVisible(true);
					}
				}
			}
		});

		// Custom the title of each table column
		JTableHeader header = table.getTableHeader();
		header.setDefaultRenderer(new TableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				JLabel label = new JLabel(value.toString());
				label.setFont(new Font("Inter", Font.BOLD, 13));
				label.setForeground(new Color(0x8B8B8B));
				label.setBackground(new Color(0xEAE7EE));
				label.setOpaque(true);
				label.setHorizontalAlignment(SwingConstants.CENTER);
				label.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
				return label;
			}
		});

		// Add the table in the scroll pane
		JScrollPane scrollPane = new JScrollPane(table);

		// Get vertical scrollbar from JScrollPane
		JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();

		// Apply custom styling
		verticalScrollBar.setUI(new CustomScrollBar());
		scrollPane.setBackground(new Color(0xEAE7EE));
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		scrollPane.setViewportBorder(null);
		scrollPane.getViewport().setBorder(null);

		// Add the table panel
		pnlBookInfo = new JPanel(new BorderLayout());
		pnlBookInfo.setBackground(new Color(0xEAE7EE));
		pnlBookInfo.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0));
		pnlBookInfo.add(scrollPane, BorderLayout.CENTER);

		pnlMain.add(pnlBookInfo, BorderLayout.CENTER);

		this.add(pnlMain, BorderLayout.CENTER);
	}

	// Function that updates the loan table
	public void refreshTable() {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0); // Clears all model lines

		List<Rental> rentals = dao.selectAll();
		rentals.sort(Comparator.comparing(Rental::getReturnDate));
		for (Rental rental : rentals) {
			Object[] rowData = new Object[9];
			rowData[0] = rental.getId();
			rowData[1] = rental.getBookISBN();
			rowData[2] = rental.getName();
			rowData[3] = rental.getRentalDuration() + " dias";
			DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
			rowData[4] = decimalFormat.format(rental.getRentalPrice()).replace(".", ",");
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			rowData[5] = dateFormat.format(rental.getReturnDate());

			model.addRow(rowData); // Add the new lines to the model
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Define the strings for the "yes" and "no" buttons in Portuguese
		UIManager.put("OptionPane.yesButtonText", "SIM");
		UIManager.put("OptionPane.noButtonText", "NÃO");
		UIManager.put("Button.background", new Color(0x5E00D7));
		UIManager.put("Button.foreground", new Color(0xFFFFFF));
		UIManager.put("Button.font", new Font("Inter", Font.BOLD, 13));
		UIManager.put("Button.border", BorderFactory.createEmptyBorder(5, 10, 5, 10));

		if (e.getSource() == btnBookDashboard) {
			Dashboard bookDashboard = new Dashboard();
			bookDashboard.setVisible(true);
			dispose();
		} else {
			if (e.getSource() == btnAddRental) {
				RentalForm rentalForm = new RentalForm(rentalDashboard);
				rentalForm.setVisible(true);
				refreshTable(); // Update the table after adding the loan
			} else {
				if (e.getSource() == btnSignOut) {
					int response = JOptionPane.showConfirmDialog(this, "Você tem certeza que deseja sair da sua conta?",
							"Sair", JOptionPane.YES_NO_OPTION);
					if (response == JOptionPane.YES_OPTION) {
						dispose();
						LoginPage login = new LoginPage();
						login.setDefaultCloseOperation(EXIT_ON_CLOSE);
						login.setVisible(true);
					}
				} else {
					if (e.getSource() == btnAdminDashboard) {
						CreateUser createUser = new CreateUser(rentalDashboard, dashboard);
						createUser.setVisible(true);
					}
				}
			}
		}
	}

	public static void main(String[] args) {
		RentalDashboard dashboard = new RentalDashboard();
		dashboard.setVisible(true);
		dashboard.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

}