package org.danforthcenter.genome.rootarch.rsagia.app2;

import org.danforthcenter.genome.rootarch.rsagia.dbfunctions.UserDBFunctions;
import org.jooq.Record;
import org.jooq.Result;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditUserFrame extends JDialog implements ActionListener {
    private JComboBox accessComboBox;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField labNameField;
    private JButton editUserButton;
    private JPanel panel1;
    private JTextField userNameField;
    private JButton cancelButton;
    private UserDBFunctions udf;
    private String selectedUser;

    public EditUserFrame(String selectedUser) {
        super(null, "Edit User", ModalityType.APPLICATION_MODAL);
        $$$setupUI$$$();
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.getContentPane().add(this.panel1);
        pack();
        this.selectedUser = selectedUser;
        editUserButton.addActionListener(this);
        cancelButton.addActionListener(this);
        this.udf = new UserDBFunctions();

        Result<Record> userRecord = this.udf.findUserFromName(selectedUser);
        Record r = userRecord.get(0);

        userNameField.setText(selectedUser);
        firstNameField.setText((String) r.getValue("first_name"));
        lastNameField.setText((String) r.getValue("last_name"));
        labNameField.setText((String) r.getValue("lab_name"));

        DefaultComboBoxModel levels = new DefaultComboBoxModel();
        String[] accessLevelList = this.udf.getAccessLevels();
        for (String item : accessLevelList) {
            levels.addElement(item);
        }
        accessComboBox.setModel(levels);
        accessComboBox.setSelectedItem(r.getValue("access_level"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == editUserButton) {
            String newUserName = userNameField.getText();
            String newAccessLevel = (String) accessComboBox.getSelectedItem();
            String newFirstName = firstNameField.getText();
            String newLastName = lastNameField.getText();
            String newLabName = labNameField.getText();
            this.udf.updateUser(newUserName, this.selectedUser, newAccessLevel, newFirstName, newLastName, newLabName);
            JOptionPane.showMessageDialog(null, "User is edited successfully.", null, JOptionPane.INFORMATION_MESSAGE);
            firePropertyChange("getall", null, null);
            this.dispose();
        } else if (e.getSource() == cancelButton) {
            this.dispose();
        }
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
        panel1.setPreferredSize(new Dimension(300, 300));
        final JLabel label1 = new JLabel();
        label1.setText("Username:");
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 0, 10, 0);
        panel1.add(label1, gbc);
        final JLabel label2 = new JLabel();
        label2.setText("Access Level:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 0, 10, 10);
        panel1.add(label2, gbc);
        final JLabel label3 = new JLabel();
        label3.setText("First Name:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 0, 10, 0);
        panel1.add(label3, gbc);
        final JLabel label4 = new JLabel();
        label4.setText("Last Name:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 0, 10, 0);
        panel1.add(label4, gbc);
        final JLabel label5 = new JLabel();
        label5.setText("Lab Name:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 0, 10, 0);
        panel1.add(label5, gbc);
        accessComboBox = new JComboBox();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(accessComboBox, gbc);
        firstNameField = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(firstNameField, gbc);
        lastNameField = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(lastNameField, gbc);
        labNameField = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(labNameField, gbc);
        userNameField = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(userNameField, gbc);
        editUserButton = new JButton();
        editUserButton.setText("Save");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 0, 10, 10);
        panel1.add(editUserButton, gbc);
        cancelButton = new JButton();
        cancelButton.setText("Cancel");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(cancelButton, gbc);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }
}
