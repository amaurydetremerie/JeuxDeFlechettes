package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class InfoWarnPanel extends JPanel {
	private JLabel infoWarnLabel;
	private JLabel warnLabel;
	private GridBagConstraints gbc = new GridBagConstraints();

	public InfoWarnPanel(int fontSize) {
		
		infoWarnLabel = new JLabel();
		warnLabel = new JLabel();
		infoWarnLabel.setFont(new Font(infoWarnLabel.getFont().getName(), Font.BOLD, fontSize));
		infoWarnLabel.setOpaque(true);
		warnLabel.setFont(new Font(infoWarnLabel.getFont().getName(), Font.BOLD, fontSize));
		warnLabel.setOpaque(true);

		GridBagLayout gridbag = new GridBagLayout();

		this.setLayout(gridbag);
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1.0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gridbag.setConstraints(infoWarnLabel, gbc);
		
		this.add(infoWarnLabel);
		gbc.gridy = 1;
		gridbag.setConstraints(warnLabel, gbc);
		this.add(warnLabel);
	}
	public InfoWarnPanel() {
		this(24);
	}
	public void afficherMessageInfo(String message) {
		infoWarnLabel.setForeground(Color.decode("#25B233"));
		infoWarnLabel.setText(message);

	}
	public void afficherMessageWarning(String message) {		
		warnLabel.setForeground(Color.decode("#FA3838"));
		warnLabel.setText(message);
	}
}
