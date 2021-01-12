package MVCModel.Realizations.ActorRealizations.Shop;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import MVCModel.Controllers.ActorControllers.Shop.IProductEditShopViewController;
import MVCModel.Realizations.AbstractView;
import MVCModel.Views.ActorViews.Shop.IProductEditShopView;
import ThriftMerch.Program;
import Utils.SpringUtilities;

public class ProductEditShopView extends AbstractView<IProductEditShopViewController> implements IProductEditShopView {

	private static final long serialVersionUID = 1L;

	/* ****************************** */
	// #region Static public field

	public static final int _MODIFIED_SCREEN_WIDTH = 900, _MODIFIED_SCREEN_HEIGHT = 600;

	// #endregion

	/* ****************************** */
	// #region Private field

	// #endregion

	/* ****************************** */
	// #region Swing Components

	private JLabel appTitle;

	private JPanel accountTitlePanel, titlePanel, footerPanel, utilsTitlePanel;
	private JPanel productPanel, contentPanel;

	private JButton logoutButton, listButton, addProductButton;

	private JPanel utilsPanel;
	private JButton backButton, homeButton, saveButton;

	private JLabel priceLabel, quantityLabel;
	private JTextField priceTextField, quantityTextField;

	// #endregion

	/* ****************************** */
	// #region Construct Layout Process

	public ProductEditShopView(IProductEditShopViewController viewController) throws IOException {
		super(viewController);

		setLayout(new BorderLayout());

		/* *********************** */
		// Add title
		titlePanel = new JPanel();
		titlePanel.setBackground(new Color(30, 30, 30));
		titlePanel.setPreferredSize(new Dimension(_MODIFIED_SCREEN_WIDTH, 50));
		titlePanel.setLayout(new BorderLayout());

		appTitle = new JLabel(Program.comName);
		appTitle.setFont(new Font("Verdana", Font.BOLD, 18));
		appTitle.setForeground(Color.WHITE);
		appTitle.setHorizontalAlignment(SwingConstants.CENTER);

		accountTitlePanel = new JPanel(new GridLayout(1, 2));
		accountTitlePanel.setBackground(new Color(30, 30, 30));

		Image logoutImage = ImageIO.read(new File("Resources/Images/logout.png"));
		Icon logoutIcon = new ImageIcon(getScaledImage(logoutImage, 32, 32));
		logoutButton = new JButton(logoutIcon);
		logoutButton.setBackground(new Color(30, 30, 30));

		logoutButton.addActionListener((ActionEvent e) -> {
			getViewController().switchToMainMenu();
		});

		accountTitlePanel.add(logoutButton);

		utilsTitlePanel = new JPanel(new GridLayout(1, 2));
		utilsTitlePanel.setBackground(new Color(30, 30, 30));

		Image addImage = ImageIO.read(new File("Resources/Images/plus.png"));
		Icon addIcon = new ImageIcon(getScaledImage(addImage, 32, 32));
		addProductButton = new JButton(addIcon);
		addProductButton.setBackground(new Color(30, 30, 30));

		addProductButton.addActionListener((ActionEvent e) -> {
			getViewController().switchToAddProduct();
		});

		Image listImage = ImageIO.read(new File("Resources/Images/list.png"));
		Icon listIcon = new ImageIcon(getScaledImage(listImage, 32, 32));
		listButton = new JButton(listIcon);
		listButton.setBackground(new Color(30, 30, 30));

		listButton.addActionListener((ActionEvent e) -> {
			getViewController().switchToOrders();
		});

		utilsTitlePanel.add(addProductButton);
		utilsTitlePanel.add(listButton);

		titlePanel.add(accountTitlePanel, BorderLayout.LINE_START);
		titlePanel.add(appTitle, BorderLayout.CENTER);
		titlePanel.add(utilsTitlePanel, BorderLayout.LINE_END);

		/* *********************** */
		// Add product panel
		productPanel = new JPanel(new BorderLayout());
		productPanel.setBackground(new Color(99, 99, 99));

		contentPanel = new JPanel(new SpringLayout());
		// contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		contentPanel.setBackground(new Color(99, 99, 99));

		contentPanel.setBorder(BorderFactory.createEmptyBorder(250, 25, 250, 25));

		productPanel.add(contentPanel);

		/* *********************** */
		// Add footer with utilities
		footerPanel = new JPanel(new BorderLayout());
		footerPanel.setBackground(new Color(30, 30, 30));
		footerPanel.setPreferredSize(new Dimension(_MODIFIED_SCREEN_WIDTH, 55));

		utilsPanel = new JPanel(new FlowLayout());
		utilsPanel.setBackground(new Color(30, 30, 30));

		// utilsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		backButton = new JButton("Back");
		backButton.setBackground(new Color(30, 30, 30));
		backButton.setPreferredSize(new Dimension(100, 45));

		backButton.addActionListener((ActionEvent event) -> {
			getViewController().switchToPreviousView();
		});

		homeButton = new JButton("Home");
		homeButton.setBackground(new Color(30, 30, 30));
		homeButton.setPreferredSize(new Dimension(100, 45));

		homeButton.addActionListener((ActionEvent event) -> {
			getViewController().switchToDefault();
		});

		saveButton = new JButton("Save");
		saveButton.setBackground(new Color(30, 30, 30));
		saveButton.setPreferredSize(new Dimension(100, 45));

		saveButton.addActionListener((ActionEvent event) -> {
			getViewController().EditProduct(priceTextField.getText(),quantityTextField.getText(),(isSuccess)->{
				if(isSuccess){
					updateEditProductView();
					JOptionPane.showMessageDialog(null,"Edit success","Success!",JOptionPane.INFORMATION_MESSAGE);
				}
			});
		});

		utilsPanel.add(backButton);
		utilsPanel.add(homeButton);
		utilsPanel.add(saveButton);

		footerPanel.add(utilsPanel);

		/* *********************************** */
		add(titlePanel, BorderLayout.PAGE_START);
		add(productPanel, BorderLayout.CENTER);
		add(footerPanel, BorderLayout.PAGE_END);
	}

	// #endregion

	/* ****************************** */
	// #region Helpers
	@Override
	public void updateEditProductView() {
		contentPanel.removeAll();

		String[] labels = { "Price: ", "Quantity: " };
		int numPairs = labels.length;

		/* *********************************** */
		priceLabel = new JLabel("Price: ", JLabel.TRAILING);

		priceLabel.setFont(new Font("Verdana", Font.BOLD, 16));
		priceLabel.setForeground(Color.WHITE);
		priceLabel.setHorizontalAlignment(SwingConstants.RIGHT);

		priceTextField = new JTextField(256);
		priceLabel.setLabelFor(priceTextField);
		priceTextField.setFont(new Font("Verdana", Font.PLAIN, 16));

		contentPanel.add(priceLabel);
		contentPanel.add(priceTextField);

		/* *********************************** */
		quantityLabel = new JLabel("Quantity: ", JLabel.TRAILING);

		quantityLabel.setFont(new Font("Verdana", Font.BOLD, 16));
		quantityLabel.setForeground(Color.WHITE);
		quantityLabel.setHorizontalAlignment(SwingConstants.RIGHT);

		quantityTextField = new JTextField(256);
		quantityLabel.setLabelFor(quantityTextField);
		quantityTextField.setFont(new Font("Verdana", Font.PLAIN, 16));

		contentPanel.add(quantityLabel);
		contentPanel.add(quantityTextField);

		/* *********************************** */
		SpringUtilities.makeCompactGrid(contentPanel, numPairs, 2, // rows, cols
				0, 0, // initX, initY
				5, 5); // xPad, yPad

		contentPanel.getParent().validate();
		contentPanel.getParent().repaint();
	}

	private Image getScaledImage(Image srcImg, int w, int h) {
		BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = resizedImg.createGraphics();

		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.drawImage(srcImg, 0, 0, w, h, null);
		g2.dispose();

		return resizedImg;
	}

	// #endregion

}
