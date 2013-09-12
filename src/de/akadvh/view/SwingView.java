package de.akadvh.view;

import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.Vector;

import javax.swing.JTree;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import de.akadvh.Akadvh;
import de.akadvh.AkadvhCredentials;
import de.akadvh.Modul;
import de.akadvh.Module;
import de.akadvh.Note;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.JProgressBar;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;


/**
 * 
 * @author Daniel Falkner
 * 
 * This file is part of Akadvh SwingView.
 * 
 * Akadvh SwingView is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Akadvh SwingView is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Akadvh SwingView. If not, see <http://www.gnu.org/licenses/>.
 *
 */
public class SwingView {

	private JFrame frmAkadVhc;
	private JTree tree = new JTree();
	private Akadvh vh;
	private Module module;
	private JTable table;
	private JProgressBar progressBar;
	private JMenu mnAktion;
	private String username;
	private String password;
	
	/**
	 * Launch the application.
	 */
	public SwingView(String username, String password) {

		
		if (username != null && password != null) {
			
			this.username = username;
			this.password = password;
		
			try {
				vh = new Akadvh(new AkadvhCredentials(this.username, this.password));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
//					UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
				    UIManager.setLookAndFeel( "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel" );
					initialize();				    
															
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}

	
	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings("serial")
	private void initialize() {
		
		
		frmAkadVhc = new JFrame();
		frmAkadVhc.setTitle("AKAD VH (C) 2013 Daniel Falkner");
		frmAkadVhc.setBounds(100, 100, 608, 414);
		frmAkadVhc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAkadVhc.setVisible(true);
		
		JLabel lblNewLabel = new JLabel("");
		frmAkadVhc.getContentPane().add(lblNewLabel, BorderLayout.NORTH);
		
		final JButton btnHoleNote = new JButton("Hole Note");
		btnHoleNote.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		
				btnHoleNote.setEnabled(false);
				mnAktion.setEnabled(false);
				
			    // Get paths of all selected nodes
			    final TreePath[] paths = tree.getSelectionPaths();
			    
			    if (paths == null) {
			    	
			    	JOptionPane.showMessageDialog(frmAkadVhc,
			    		    "Kein Modul ausgewählt.",
			    		    "Fehler",
			    		    JOptionPane.ERROR_MESSAGE);
			    	
					btnHoleNote.setEnabled(true);
			    	mnAktion.setEnabled(true);
			    	
			    } else {
			    			    	   
			    			try {
								
								
								class ReaderThread  implements Runnable {
							
									@Override
									public void run() {
										// TODO Auto-generated method stub
										
										progressBar.setValue(0);
										progressBar.setVisible(true);
										
							    		for (int i = 0; i < paths.length; i++) {

							    			String modulName = paths[i].getLastPathComponent().toString();
   			
							    			if (modulName.equals("Module") || modulName.equals("Meine Module")) continue;
											
											Modul modul = null;
											try {
												modul = module.getModulByName(modulName);
												modul = vh.holeNoten(modul);
											} catch (Exception e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
											
									    	for (Note n: modul.getNoten()) {
									    		Vector<String> row = new Vector<String>();
										    	row.add(modul.getName());
										    	row.add(n.getNote());
										    	row.add(n.getBezeichnung());
										    	row.add(n.getDatum());
										    	((DefaultTableModel)table.getModel()).addRow(row);
									    	}
									    	
											progressBar.setValue(100 / paths.length * (i + 1));

									    	
							    		}
							    		
										btnHoleNote.setEnabled(true);
										mnAktion.setEnabled(true);
										progressBar.setVisible(false);

									}
									
								}
								
								new Thread(new ReaderThread()).start();
								
							  	
							} catch (Exception e2) {
								JOptionPane.showMessageDialog(frmAkadVhc,
						    		    e2.getMessage(),
						    		    "Fehler",
						    		    JOptionPane.ERROR_MESSAGE);
							}
							  
			    }
			    
			 
			}
		});
		frmAkadVhc.getContentPane().add(btnHoleNote, BorderLayout.SOUTH);
		
		JScrollPane scrollPane = new JScrollPane();
		frmAkadVhc.getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Modul", "Note", "Bezeichnung", "Datum"
			}
		));
		scrollPane.setViewportView(table);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		frmAkadVhc.getContentPane().add(scrollPane_1, BorderLayout.WEST);
		tree.setSelectionRows(new int[] {1});
		tree.setSelectionRow(1);
		
		tree.setModel(new DefaultTreeModel(
			new DefaultMutableTreeNode("Meine Module") {
				{
				}
			}
		));
		scrollPane_1.setViewportView(tree);
		
		JMenuBar menuBar = new JMenuBar();
		frmAkadVhc.setJMenuBar(menuBar);
		
		mnAktion = new JMenu("Aktion");
		menuBar.add(mnAktion);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Module aktualisieren");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					if (vh != null) {
						
						module = vh.holeModule();
				
						DefaultMutableTreeNode root = new DefaultMutableTreeNode("Module");
						for (Modul m: module) {
							root.add(new DefaultMutableTreeNode(m));

						}
						
						tree.setModel(new DefaultTreeModel(root));
						
					} else {
						
						JOptionPane.showMessageDialog(frmAkadVhc,
				    		    "Keine AKAD VH Nutzerkennung vorhanden.",
				    		    "Fehler",
				    		    JOptionPane.ERROR_MESSAGE);
					}
					

					
					
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(frmAkadVhc,
			    		    e1.getMessage(),
			    		    "Fehler",
			    		    JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		mnAktion.add(mntmNewMenuItem);

		JMenuItem mntmBenutzerdaten = new JMenuItem("Benutzerdaten");
		mntmBenutzerdaten.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JPanel userPanel = new JPanel();
				userPanel.setLayout(new GridLayout(2,2));

				JLabel usernameLbl = new JLabel("Nutzerkennung:");
				JLabel passwordLbl = new JLabel("Passwort:");

				JTextField username = new JTextField();
				username.setText(SwingView.this.username);
				JPasswordField password = new JPasswordField();
				password.setText(SwingView.this.password);
				
				userPanel.add(usernameLbl);
				userPanel.add(username);
				userPanel.add(passwordLbl);
				userPanel.add(password);

				int ret = JOptionPane.showConfirmDialog(null, userPanel, "AKAD VH Nutzerkennung:"
				                      ,JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		        
				//ret 0 = OK; 2 = Cancel
				if (ret == 0) {
				
					try {
						SwingView.this.username = username.getText();
						SwingView.this.password = String.valueOf(password.getPassword());
						
						vh = new Akadvh(new AkadvhCredentials(username.getText(), String.valueOf(password.getPassword())));
						
					} catch (UnsupportedEncodingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (MalformedURLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
				
	
			}
		});
		mnAktion.add(mntmBenutzerdaten);
		
		JMenuItem mntmCSV = new JMenuItem("CSV Export");
		mntmCSV.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				JFileChooser chooser = new JFileChooser();
				
		        int ret = chooser.showSaveDialog(null);
		        
		        if(ret == JFileChooser.APPROVE_OPTION)
		        {
		             
		            try {
						exportTableDataToCSV(table, chooser.getSelectedFile().getAbsoluteFile());
						JOptionPane.showMessageDialog(frmAkadVhc,
					    		    "CSV-Datei " + chooser.getSelectedFile().getName() + " wurde erstellt",					    		    
					    		    "Information",
					    		    JOptionPane.INFORMATION_MESSAGE);
						
					} catch (IOException e1) {
		        		JOptionPane.showMessageDialog(frmAkadVhc,
		    	    		    e1.getMessage(),
		    	    		    "Fehler",
		    	    		    JOptionPane.ERROR_MESSAGE);
		            }
					
		        }
				
			}
		});
		mnAktion.add(mntmCSV);
		
		JMenuItem mntmVersion = new JMenuItem("Version");
		mntmVersion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frmAkadVhc,
		    		    "Version: " + Akadvh.getVersion() + "\n" + "(C) Daniel Falkner",
		    		    "Version",
		    		    JOptionPane.INFORMATION_MESSAGE);
			}
		});
		mnAktion.add(mntmVersion);
		
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		menuBar.add(separator);
		
		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		progressBar.setVisible(false);
		menuBar.add(progressBar);
		
		
	}

	public void exportTableDataToCSV(JTable table, File file) throws IOException {
        
                file.createNewFile();

                BufferedWriter bw = new BufferedWriter(new FileWriter(file));

                TableModel model = table.getModel();
                for (int h = 0 ; h < model.getColumnCount();h++){
                  bw.write(model.getColumnName(h).toString());
                  if (h+1 != model.getColumnCount())
                    bw.write(";");
                }
                bw.newLine();
                
                for (int clmCnt = model.getColumnCount(), rowCnt = model
                                .getRowCount(), i = 0; i < rowCnt; i++) {
                        for (int j = 0; j < clmCnt; j++) {
                                if (model.getValueAt(i, j) != null){
                                  String value = model.getValueAt(i, j).toString();
                                  bw.write("\"" + value + "\"");
                                }
                                if(j+1 != clmCnt)
                                  bw.write(";");
                        }
                        bw.newLine();
                }

                bw.flush();
                bw.close();

        
}
	
}
