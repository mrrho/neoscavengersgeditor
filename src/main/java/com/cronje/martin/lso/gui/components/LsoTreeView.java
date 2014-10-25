package com.cronje.martin.lso.gui.components;

import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;

import com.cronje.martin.amf.types.AMF3InputElement;

@SuppressWarnings("serial")
public class LsoTreeView extends JPanel {
	
	public LsoTreeView(AMF3InputElement data) {
//		DefaultMutableTreeNode root = new DefaultMutableTreeNode(data);
//		JTree tree = new JTree(root);
//		add(tree);
		add(new JTree(new DefaultTreeModel(null)));
	}

}
