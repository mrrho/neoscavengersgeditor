package com.cronje.martin.lso.gui;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.cronje.martin.amf.types.AMF3Object;
import com.cronje.martin.lso.LsoUtility;
import com.cronje.martin.lso.gui.components.LsoEditorMenuBar;
import com.cronje.martin.lso.gui.components.LsoTreeView;

@SuppressWarnings("serial")
public class LsoEditor extends JFrame implements Runnable {

	private static LsoEditor editor = null;
	private LsoTreeView treeView = null;
	private JPanel rootPanel = null;

	public LsoEditor() {
		editor = this;

		setTitle("Blah");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		LsoEditorMenuBar menuBar = new LsoEditorMenuBar();
		setJMenuBar(menuBar);

		rootPanel = new JPanel();
		rootPanel.setLayout(new BoxLayout(rootPanel, BoxLayout.PAGE_AXIS));
//		treeScroller = new JScrollPane();
//		rootPanel.add(treeScroller);
		setContentPane(rootPanel);

		pack();
		setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
	}

	public static void main(String[] args) throws ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			UnsupportedLookAndFeelException {
		LsoEditor editor = new LsoEditor();
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		SwingUtilities.invokeLater(editor);
	}

	public void run() {
		setVisible(true);
	}

	public static LsoEditor getEditor() {
		return LsoEditor.editor;
	}

	public void openFile(File selectedFile) throws IOException {
		DataInputStream dis = new DataInputStream(new BufferedInputStream(
				new FileInputStream(selectedFile)));
		AMF3Object root = LsoUtility.deserialize(dis);
		treeView = new LsoTreeView(root);
		rootPanel.add(treeView);
//		treeScroller.getViewport().removeAll();
//		treeScroller.getViewport().add(treeView);
	}

}
