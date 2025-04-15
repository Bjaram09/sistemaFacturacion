package Views.Producto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ViewRegistrarProducto extends JFrame {
    private JPanel form;
    private JPanel buttonField;

    private JTextField inputID;
    private JTextField inputDescripcion;
    private JTextField inputMinCantidad;
    private JTextField inputMaxCantidad;
    private JTextField inputPrecioPorUnidad;

    private JLabel labelID;
    private JLabel labelDescripcion;
    private JLabel labelMinCantidad;
    private JLabel labelMaxCantidad;
    private JLabel labelPrecioPorUnidad;

    private JButton btnRegistrar;

    private static final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private static final Color BACKGROUND_COLOR = new Color(236, 240, 241);
    private static final Color INPUT_BACKGROUND = Color.WHITE;

    public ViewRegistrarProducto() {
        super();
        setTitle("Registrar Producto");
        setSize(600, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(BACKGROUND_COLOR);
        setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel titleLabel = new JLabel("Registrar Producto");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);

        form = new JPanel(new GridBagLayout());
        form.setBackground(BACKGROUND_COLOR);
        form.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
        labelID = createStyledLabel("ID:", labelFont);
        labelDescripcion = createStyledLabel("Descripcion:", labelFont);
        labelMinCantidad = createStyledLabel("Cantidad minima:", labelFont);
        labelMaxCantidad = createStyledLabel("Cantidad maxima:", labelFont);
        labelPrecioPorUnidad = createStyledLabel("Precio por unidad:", labelFont);

        Font inputFont = new Font("Segoe UI", Font.PLAIN, 14);
        inputID = createStyledTextField(inputFont);
        inputDescripcion = createStyledTextField(inputFont);
        inputMinCantidad = createStyledTextField(inputFont);
        inputMaxCantidad = createStyledTextField(inputFont);
        inputPrecioPorUnidad = createStyledTextField(inputFont);

        int row = 0;
        addFormRow(gbc, labelID, inputID, row++);
        addFormRow(gbc, labelDescripcion, inputDescripcion, row++);
        addFormRow(gbc, labelMinCantidad, inputMinCantidad, row++);
        addFormRow(gbc, labelMaxCantidad, inputMaxCantidad, row++);
        addFormRow(gbc, labelPrecioPorUnidad, inputPrecioPorUnidad, row++);

        buttonField = new JPanel();
        buttonField.setBackground(BACKGROUND_COLOR);
        buttonField.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        btnRegistrar = new JButton("Registrar");
        btnRegistrar.setBackground(PRIMARY_COLOR);
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnRegistrar.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        btnRegistrar.setFocusPainted(false);
        btnRegistrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonField.add(btnRegistrar);

        add(form, BorderLayout.CENTER);
        add(buttonField, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JLabel createStyledLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(new Color(52, 73, 94));
        return label;
    }

    private JTextField createStyledTextField(Font font) {
        JTextField textField = new JTextField(20);
        textField.setFont(font);
        textField.setBackground(INPUT_BACKGROUND);
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        return textField;
    }

    private void addFormRow(GridBagConstraints gbc, JLabel label, JComponent component, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        form.add(label, gbc);

        gbc.gridx = 1;
        form.add(component, gbc);
    }

    public String getID() {
        return inputID.getText();
    }
    public String getDescripcion() {
        return inputDescripcion.getText();
    }
    public int getMinCantidad() {return Integer.parseInt(inputMinCantidad.getText());}
    public int getMaxCantidad(){return Integer.parseInt(inputMaxCantidad.getText());}
    public double getPrecioPorUnidad() {
        return Double.parseDouble(inputPrecioPorUnidad.getText());
    }
    public JButton getBtnRegistrar() {
        return btnRegistrar;
    }

    public void setID(String id) {
        inputID.setText(id);
    }
    public void setDescripcion(String descripcion) {
        inputDescripcion.setText(descripcion);
    }
    public void setMinCantidad(int minCantidad) {
        inputMinCantidad.setText(String.valueOf(minCantidad));
    }
    public void setMaxCantidad(int maxCantidad) {
        inputMaxCantidad.setText(String.valueOf(maxCantidad));
    }
    public void setPrecioUnitario(double precioUnitario) {
        inputPrecioPorUnidad.setText(String.valueOf(precioUnitario));
    }

    public JTextField getTxtID() {
        return inputID;
    }

    public void addUpdateListener(ActionListener listener) {
        btnRegistrar.addActionListener(listener);
    }
}
