package org.danforthcenter.genome.rootarch.rsagia.app2;

import org.danforthcenter.genome.rootarch.rsagia.dbfunctions.MetadataDBFunctions;
import org.danforthcenter.genome.rootarch.rsagia2.Import;
import org.danforthcenter.genome.rootarch.rsagia2.UserAccess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.List;


public class UploadImages extends JFrame implements ActionListener, PropertyChangeListener {
    private JButton loadButton;
    private JPanel panel1;
    private JButton browseButton;
    private JTextArea printstextArea;
    private JLabel statusLabel;
    private JLabel processingField;
    private JScrollPane jsp;

    private File imageSetDir;
    private boolean backFromBrowse;

    private Import importApp;
    private File baseDir;
    private File defaultImageSetDir;

    public UploadImages(Import importApp, File baseDir) {
        $$$setupUI$$$();
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.getContentPane().add(this.panel1);
        this.setTitle("Upload Imageset Window");

        this.jsp.setViewportView(printstextArea);
        pack();
        this.browseButton.addActionListener(this);
        this.loadButton.addActionListener(this);
        this.backFromBrowse = false;
        this.importApp = importApp;
        this.baseDir = baseDir;
        this.defaultImageSetDir = new File(this.baseDir, "to_sort");
        this.printstextArea.setVisible(false);
        this.statusLabel.setVisible(false);
        this.jsp.setVisible(false);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == browseButton) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Select Imageset Directory");
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fileChooser.showDialog(this, null);
            this.imageSetDir = fileChooser.getSelectedFile();
            if (this.imageSetDir != null) {
                backFromBrowse = true;
            }
        } else if (e.getSource() == loadButton) {
            String s = null;

            try {
                this.processingField.setText("Processing...");
                this.printstextArea.setVisible(true);
                this.statusLabel.setVisible(true);
                jsp.setVisible(true);
                ImportWorker iw = new ImportWorker(importApp, this.imageSetDir, true, printstextArea);
                iw.addPropertyChangeListener(this);
                iw.execute();

            } catch (Exception e1) {
                e1.printStackTrace();
                if (backFromBrowse == false) {
                    JOptionPane.showMessageDialog(null, "Please choose a directory first", "ERROR", JOptionPane.ERROR_MESSAGE);

                } else {
                    JOptionPane.showMessageDialog(null, "The images or directory are not in valid format for loading", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getSource().getClass().equals(ImportWorker.class)
                && evt.getPropertyName().equals("state")
                && evt.getNewValue() == SwingWorker.StateValue.DONE) {
            ImportWorker rw = (ImportWorker) evt.getSource();
            List<String[]> v = rw.getReturnValue();

            boolean checkAdd = false;
            if (v != null) {
                MetadataDBFunctions mdf = new MetadataDBFunctions();

                for (int i = 0; i < v.size(); i++) {
                    String[] datasetFeatures = v.get(i);

                    String organism = datasetFeatures[0];
                    String experiment = datasetFeatures[1];
                    String seed = datasetFeatures[2];
                    String timepoint = datasetFeatures[3];
                    String imageType = datasetFeatures[4];
                    String userName = UserAccess.getCurrentUser();
                    checkAdd = mdf.addNewImageSet(organism, experiment, seed, timepoint, imageType, userName);
                    if (checkAdd == true) {
                        firePropertyChange("getall", null, null);
                    }

                }
            }
            String s = (checkAdd != false) ? "DONE" : "ERROR";
            this.processingField.setText(s);
        }
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout());
        panel1.setPreferredSize(new Dimension(600, 300));
        final JLabel label1 = new JLabel();
        label1.setText("Imageset Directory Path:");
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel1.add(label1, gbc);
        final JPanel spacer1 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(spacer1, gbc);
        final JPanel spacer2 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel1.add(spacer2, gbc);
        loadButton = new JButton();
        loadButton.setMargin(new Insets(3, 18, 3, 18));
        loadButton.setText("Load");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(20, 100, 0, 100);
        panel1.add(loadButton, gbc);
        browseButton = new JButton();
        browseButton.setText("Browse");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 100, 0, 100);
        panel1.add(browseButton, gbc);
        statusLabel = new JLabel();
        statusLabel.setText("Status:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        panel1.add(statusLabel, gbc);
        final JPanel spacer3 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel1.add(spacer3, gbc);
        final JPanel spacer4 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel1.add(spacer4, gbc);
        processingField = new JLabel();
        processingField.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        panel1.add(processingField, gbc);
        jsp = new JScrollPane();
        jsp.setPreferredSize(new Dimension(300, 105));
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 6;
        gbc.fill = GridBagConstraints.BOTH;
        panel1.add(jsp, gbc);
        printstextArea = new JTextArea();
        printstextArea.setColumns(80);
        printstextArea.setPreferredSize(new Dimension(720, 210));
        printstextArea.setRows(10);
        jsp.setViewportView(printstextArea);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }
}
