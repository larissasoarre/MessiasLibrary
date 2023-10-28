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
import dao.UserLoginDAO;
import model.CustomScrollBar;
import model.UserLogin;

public class AdminDashboard extends JFrame implements ActionListener {

	// Main JLabels
	private JLabel lblExtLogo;
	private JLabel lblAdminDashboard;
	private JLabel lblMainTitle;

	// JButtons
	private JButton btnAddUser;
	private JButton btnSignOut;
	private JButton btnRentalDashboard;
	private JButton btnBookDashboard;

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

	private AdminDashboard adminDashboard;

	// Attributes to store user information and manipulate the database
	private UserLogin user;
	private UserLoginDAO dao;

	public AdminDashboard() {

		adminDashboard = this;
		dao = new UserLoginDAO();

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
		pnlControl.setPreferredSize(new Dimension(250, 200));

		// Instance JLabels
		lblExtLogo = new JLabel();
		extendedLogo = new ImageIcon(AdminDashboard.class.getResource("/extended-logo.png"));
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
		btnBookDashboard.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnBookDashboard.setBorder(BorderFactory.createEmptyBorder(9, 80, 9, 80));
		btnBookDashboard.setPreferredSize(new Dimension(250, 35));
		btnBookDashboard.addActionListener(this);
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

		// Button to return to the home dashboard
		btnRentalDashboard = new JButton("Empréstimos");
		btnRentalDashboard.setBorderPainted(false);
		btnRentalDashboard.setFont(new Font("Inter", Font.BOLD, 15));
		btnRentalDashboard.setBackground(new Color(0xDCD9DF));
		btnRentalDashboard.setForeground(new Color(0x000000));
		btnRentalDashboard.addActionListener(this);
		btnRentalDashboard.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnRentalDashboard.setBorder(BorderFactory.createEmptyBorder(9, 55, 9, 55));
		btnRentalDashboard.setPreferredSize(new Dimension(250, 35));
		btnRentalDashboard.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				btnRentalDashboard.setBackground(new Color(0xCECBD1));
			}

			public void mouseExited(MouseEvent e) {
				btnRentalDashboard.setBackground(new Color(0xDCD9DF));
			}
		});

		pnlControl.add(btnRentalDashboard);

		// Separator
		pnlControl.add(Box.createRigidArea(new Dimension(10, 10)));

		JPanel pnlRental = new JPanel();
		pnlRental.setLayout(new BoxLayout(pnlRental, BoxLayout.X_AXIS));
		pnlRental.setBackground(new Color(0x5E00D7));
		pnlRental.setAlignmentX(Component.CENTER_ALIGNMENT);
		pnlRental.setBorder(BorderFactory.createEmptyBorder(9, 46, 9, 46));
		pnlRental.setPreferredSize(new Dimension(250, 35));

		lblAdminDashboard = new JLabel("Administração");
		lblAdminDashboard.setFont(new Font("Inter", Font.BOLD, 16));
		lblAdminDashboard.setAlignmentX(Component.LEFT_ALIGNMENT);
		lblAdminDashboard.setForeground(new Color(0xFFFFFF));
		pnlRental.add(lblAdminDashboard);

		pnlControl.add(pnlRental);

		// Separator
		pnlControl.add(Box.createRigidArea(new Dimension(10, 10)));

		pnlControlBtns = new JPanel();
		pnlControlBtns.setLayout(new BoxLayout(pnlControlBtns, BoxLayout.Y_AXIS));
		pnlControlBtns.setBackground(new Color(0xDCD9DF));

		Border lineBorder2 = BorderFactory.createLineBorder(new Color(0x5E00D7), 2);
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

		lblMainTitle = new JLabel("Livros");
		lblMainTitle.setFont(new Font("Inter", Font.BOLD, 40));
		pnlTop.add(lblMainTitle, BorderLayout.WEST);

		Box box = Box.createHorizontalBox();
		box.add(Box.createHorizontalGlue());

		btnAddUser = new JButton("CRIAR NOVO USUÁRIO");
		btnAddUser.setBorderPainted(false);
		btnAddUser.setFont(new Font("Inter", Font.BOLD, 13));
		btnAddUser.setBackground(new Color(0x5E00D7));
		btnAddUser.setForeground(new Color(0xFFFFFF));
		btnAddUser.addActionListener(this);
		box.add(btnAddUser);
		pnlTop.add(box, BorderLayout.EAST);

		pnlMain.add(pnlTop, BorderLayout.NORTH);

		// Panel with list of books
		pnlBookInfo = new JPanel(new GridLayout(1, 1));
		pnlBookInfo.setBackground(new Color(0xEAE7EE));
		pnlBookInfo.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0));

		// Create the JTable model
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("ID");
		model.addColumn("Usuário");
		model.addColumn("Tipo de Senha");
		model.addColumn("Permissão");

		// Populate the model with data from the ArrayList
		dao = new UserLoginDAO();

		List<UserLogin> users = dao.selectAll();
		for (UserLogin user : users) {
			Object[] rowData = new Object[9];
			rowData[0] = user.getId();
			rowData[1] = user.getUsername();
			rowData[2] = user.getTemporaryPassword();
			rowData[3] = user.getIsAdmin();

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

		// Custom each cell in the table
		// Custom cell renderer
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

				// Adjust Permission column values
				if (column == 3 && value != null) {
					if (value.equals("Y")) {
						setText("Administrador");
					} else if (value.equals("N")) {
						setText("Bibliotecário");
					}
				}

				// Adjusting Password Type column values
				if (column == 2 && value != null) {
					int tempPasswordValue = Integer.parseInt(value.toString());
					if (tempPasswordValue == 0) {
						setText("Permanente");
					} else if (tempPasswordValue == 1) {
						setText("Temporária");
					}
				}

				return component;
			}
		};

		table.setDefaultRenderer(Object.class, cellRenderer);

		// Adjusts the width of each column
		table.getColumnModel().getColumn(0).setPreferredWidth(50); // ID
		table.getColumnModel().getColumn(1).setPreferredWidth(180); // User
		table.getColumnModel().getColumn(2).setPreferredWidth(180); // Password Type
		table.getColumnModel().getColumn(3).setPreferredWidth(180); // Access Type

		// Create a mouse listener for table rows
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) { // Double-click event
					int selectedRow = table.getSelectedRow();
					if (selectedRow != -1) {
						// Retrieve the book information from the selected row
						int userId = (int) table.getValueAt(selectedRow, 0);
						user = dao.selectById(userId);

						// Open a new window to display the book information
						UserDetails bookDetailsWindow = new UserDetails(user, adminDashboard);
						bookDetailsWindow.setVisible(true);
					}
				}
			}
		});

		// Customize the title of each column in the table
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

		// Gets JScrollPane's vertical scrollbar
		JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();

		// Apply custom style
		verticalScrollBar.setUI(new CustomScrollBar());
		scrollPane.setBackground(new Color(0xEAE7EE));
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		scrollPane.setViewportBorder(null);
		scrollPane.getViewport().setBorder(null);

		// Adds the table panel
		pnlBookInfo = new JPanel(new BorderLayout());
		pnlBookInfo.setBackground(new Color(0xEAE7EE));
		pnlBookInfo.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0));
		pnlBookInfo.add(scrollPane, BorderLayout.CENTER);

		pnlMain.add(pnlBookInfo, BorderLayout.CENTER);

		this.add(pnlMain, BorderLayout.CENTER);
	}

	public void refreshTable() {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0); // Clears all model lines

		List<UserLogin> users = dao.selectAll();
		for (UserLogin user : users) {
			Object[] rowData = new Object[4];
			rowData[0] = user.getId();
			rowData[1] = user.getUsername();
			rowData[2] = user.getTemporaryPassword();
			rowData[3] = user.getIsAdmin();

			model.addRow(rowData);
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

		if (e.getSource() == btnAddUser) {
			SignUpPage signUp = new SignUpPage();
			signUp.setVisible(true);
			refreshTable(); // Update table after adding new user
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
				if (e.getSource() == btnRentalDashboard) {
					RentalDashboard rentalDashboard = new RentalDashboard();
					rentalDashboard.setVisible(true);
					this.dispose();
				} else {
					if (e.getSource() == btnBookDashboard) {
						Dashboard dashboard = new Dashboard();
						dashboard.setVisible(true);
						this.dispose();
					}
				}
			}
		}
	}

	public static void main(String[] args) {
		AdminDashboard dashboard = new AdminDashboard();
		dashboard.setVisible(true);
		dashboard.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

}