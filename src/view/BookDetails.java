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
import model.BookSale;

class BookDetails extends JFrame implements ActionListener {

	// Create JLabels
	private JLabel lblId;
	private JLabel lblIsbn;
	private JLabel lblTitle;
	private JLabel lblAuthor;
	private JLabel lblGenre;
	private JLabel lblPublisher;
	private JLabel lblEdition;
	private JLabel lblSalePrice;
	private JLabel lblQuantity;

	// Create JButton
	private JButton btnUpdateBook;
	private JButton btnDelete;

	// Create JPanel
	private JPanel pnlMain;
	private JPanel pnlInfo;
	private JPanel pnlButton;

	private BookSale book;
	private static Dashboard dashboard;
	private BookSaleDAO dao;

	// Images and logos that are used in the system
	private ImageIcon logo;

	public BookDetails(BookSale book, Dashboard dashboard) {
		this.book = book;
		this.dashboard = dashboard;

		// Window Settings
		this.setTitle("Livraria Messias");
		this.setLocationRelativeTo(null);
		this.setMinimumSize(new Dimension(500, 450));
		this.setLocationRelativeTo(null);
		this.setLayout(new BorderLayout());
		this.getContentPane().setBackground(new Color(0xEAE7EE));
		this.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				Dimension newSize = getSize();
				if (newSize.width < 500 || newSize.height < 450) {
					setSize(Math.max(newSize.width, 500), Math.max(newSize.height, 450));
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
		

		// Populate JLabels with book information
		constraints.gridx = 0;
		constraints.gridy = 0;
		lblId = new JLabel("<html><b>ID:</b> " + book.getId() + "</html>");
		lblId.setFont(new Font("Inter", Font.PLAIN, 16));
		pnlInfo.add(lblId, constraints);

		constraints.gridy = 1;
		lblIsbn = new JLabel("<html><b>ISBN:</b> " + book.getIsbn() + "</html>");
		lblIsbn.setFont(new Font("Inter", Font.PLAIN, 16));
		pnlInfo.add(lblIsbn, constraints);

		constraints.gridy = 2;
		lblTitle = new JLabel("<html><b>TÍTULO:</b> " + book.getTitle() + "</html>");
		lblTitle.setFont(new Font("Inter", Font.PLAIN, 16));
		pnlInfo.add(lblTitle, constraints);

		constraints.gridy = 3;
		lblAuthor = new JLabel("<html><b>AUTOR(A):</b> " + book.getAuthor() + "</html>");
		lblAuthor.setFont(new Font("Inter", Font.PLAIN, 16));
		pnlInfo.add(lblAuthor, constraints);

		constraints.gridy = 4;
		lblGenre = new JLabel("<html><b>GÊNERO:</b> " + book.getGenre() + "</html>");
		lblGenre.setFont(new Font("Inter", Font.PLAIN, 16));
		pnlInfo.add(lblGenre, constraints);

		constraints.gridy = 5;
		lblPublisher = new JLabel("<html><b>EDITORA:</b> " + book.getPublisher() + "</html>");
		lblPublisher.setFont(new Font("Inter", Font.PLAIN, 16));
		pnlInfo.add(lblPublisher, constraints);

		constraints.gridy = 6;
		lblEdition = new JLabel("<html><b>EDIÇÃO:</b> " + book.getEdition() + "</html>");
		lblEdition.setFont(new Font("Inter", Font.PLAIN, 16));
		pnlInfo.add(lblEdition, constraints);
		
		DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
		constraints.gridy = 7;
		lblSalePrice = new JLabel("<html><b>PREÇO:</b> " + decimalFormat.format(book.getSalePrice()).replace(".", ",") + "</html>");
		lblSalePrice.setFont(new Font("Inter", Font.PLAIN, 16));
		pnlInfo.add(lblSalePrice, constraints);

		constraints.gridy = 8;
		lblQuantity = new JLabel("<html><b>QUANTIDADE:</b> " + book.getQuantity() + "</html>");
		lblQuantity.setFont(new Font("Inter", Font.PLAIN, 16));
		pnlInfo.add(lblQuantity, constraints);
		
		pnlMain.add(pnlInfo);

		// Button Panel
		pnlButton = new JPanel(new FlowLayout());
		pnlButton.setBackground(new Color(0xEAE7EE));
		
		Border lineBorder2 = BorderFactory.createLineBorder(new Color(0x5E00D7), 2);
		Border paddingBorder2 = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		Border compoundBorder2 = BorderFactory.createCompoundBorder(lineBorder2, paddingBorder2);

		btnDelete = new JButton("DELETAR");
		btnDelete.setPreferredSize(new Dimension(110, 25));
		btnDelete.setFont(new Font("Inter", Font.BOLD, 13));
		btnDelete.setBackground(new Color(0xEDEDF2));
		btnDelete.setForeground(new Color(0x5E00D7));
		btnDelete.setBorder(compoundBorder2);
		btnDelete.addActionListener(this);
		pnlButton.add(btnDelete);
		
		btnUpdateBook = new JButton("ATUALIZAR");
		btnUpdateBook.setBorderPainted(false);
		btnUpdateBook.setFont(new Font("Inter", Font.BOLD, 13));
		btnUpdateBook.setBackground(new Color(0x5E00D7));
		btnUpdateBook.setForeground(new Color(0xFFFFFF));
		btnUpdateBook.addActionListener(this);
		pnlButton.add(btnUpdateBook);
		
		pnlMain.add(pnlButton);
		this.add(pnlMain);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		UIManager.put("Button.background", new Color(0x5E00D7));
        UIManager.put("Button.foreground", new Color(0xFFFFFF));
        UIManager.put("Button.font", new Font("Inter", Font.BOLD, 13));
        UIManager.put("Button.border", BorderFactory.createEmptyBorder(5, 10, 5, 10));
        
		if (e.getSource() == btnUpdateBook) {
			if (book != null) {				
				BookSale updatedBook = new BookSale();
				updatedBook.setId(book.getId());
				updatedBook.setIsbn(book.getIsbn());
				updatedBook.setTitle(book.getTitle());
				updatedBook.setAuthor(book.getAuthor());
				updatedBook.setGenre(book.getGenre());
				updatedBook.setPublisher(book.getPublisher());
				updatedBook.setEdition(book.getEdition());
				updatedBook.setSalePrice(book.getSalePrice());
				updatedBook.setQuantity(book.getQuantity());

				UpdateBook updateBookData = new UpdateBook(updatedBook, dashboard);
				updateBookData.setVisible(true);
				dispose();
			}
		} else {
			if (e.getSource() == btnDelete) {
				// Define the strings for the "yes" and "no" buttons in Portuguese
				UIManager.put("OptionPane.yesButtonText", "SIM");
				UIManager.put("OptionPane.noButtonText", "NÃO");
				UIManager.put("Button.background", new Color(0x5E00D7));
	            UIManager.put("Button.foreground", new Color(0xFFFFFF));
	            UIManager.put("Button.font", new Font("Inter", Font.BOLD, 13));
				
				int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir esse livro? Todas os dados serão excluídos", "Confirmação", JOptionPane.YES_NO_OPTION);
				
				if (confirm == JOptionPane.YES_OPTION) {
					dao = new BookSaleDAO();
					dao.delete(book);
					
					JOptionPane.showMessageDialog(this, "Livro excluído com sucesso.");
					dashboard.refreshTable();
					dispose(); 
				}
			}
		}
	}

	public static void main(String[] args) {
		BookSale book = new BookSale();
		BookDetails frame = new BookDetails(book, dashboard);
		frame.setVisible(true);
	}
}