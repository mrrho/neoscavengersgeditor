package com.cronje.martin.lso.gui.components;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import com.cronje.martin.lso.gui.LsoEditor;

@SuppressWarnings("serial")
public class LsoEditorMenuBar extends JMenuBar implements ActionListener {

	private static final String FILE_OPEN = "file-open";
	
	private JMenu fileMenu;

	public LsoEditorMenuBar() {
		createMenuBar();
	}

	private void createMenuBar() {
		fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);

		JMenuItem fileOpenItem = new JMenuItem("Open...");
		fileOpenItem.setMnemonic(KeyEvent.VK_O);
		fileOpenItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
				ActionEvent.CTRL_MASK));
		fileOpenItem.setName(LsoEditorMenuBar.FILE_OPEN);
		fileOpenItem.addActionListener(this);
		fileMenu.add(fileOpenItem);

		add(fileMenu);
	}

	public void actionPerformed(ActionEvent e) {
		String name = ((Component)e.getSource()).getName();
		if(name.equals(LsoEditorMenuBar.FILE_OPEN)) {
			JFileChooser fc = new JFileChooser();
			int rv = fc.showOpenDialog(null);
			if(rv == JFileChooser.APPROVE_OPTION) {
				try {
					LsoEditor.getEditor().openFile(fc.getSelectedFile());
				} catch (IOException e1) {
				}
			}
		}
	}

}
