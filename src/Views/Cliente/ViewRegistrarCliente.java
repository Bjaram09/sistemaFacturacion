package Views.Cliente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ViewRegistrarCliente extends JFrame {
    private JPanel form;
    private JPanel buttonField;

    private JTextField inputID;
    private JTextField inputPrimerApellido;
    private JComboBox<String> generoDropdown;
    private JTextField inputCorreo;
    private JTextField inputSegundoApellido;
    private JTextField inputTelefonoCelular;
    private JTextField inputTelefonoCasa;
    private JTextField inputDireccion;
    private JTextField inputNombre;
    private JTextField inputEdad;

    private JLabel labelPrimerApellido;
    private JLabel labelID;
    private JLabel labelGenero;
    private JLabel labelEdad;
    private JLabel labelTelefonoCasa;
    private JLabel labelNombre;
    private JLabel labelSegundoApellido;
    private JLabel labelDireccion;
    private JLabel labelCorreo;
    private JLabel labelTelefonoCelular;

    private JButton btnRegistrar;

    private static final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private static final Color BACKGROUND_COLOR = new Color(236, 240, 241);
    private static final Color INPUT_BACKGROUND = Color.WHITE;

    public ViewRegistrarCliente() {
        super();
        setTitle("Registrar Cliente");
        setSize(600, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(BACKGROUND_COLOR);
        setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel titleLabel = new JLabel("Registrar Cliente");
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
        labelNombre = createStyledLabel("Nombre:", labelFont);
        labelPrimerApellido = createStyledLabel("Primer Apellido:", labelFont);
        labelSegundoApellido = createStyledLabel("Segundo Apellido:", labelFont);
        labelCorreo = createStyledLabel("Correo:", labelFont);
        labelGenero = createStyledLabel("Género:", labelFont);
        labelTelefonoCasa = createStyledLabel("Teléfono Casa:", labelFont);
        labelTelefonoCelular = createStyledLabel("Teléfono Celular:", labelFont);
        labelDireccion = createStyledLabel("Dirección:", labelFont);
        labelEdad = createStyledLabel("Edad:", labelFont);

        Font inputFont = new Font("Segoe UI", Font.PLAIN, 14);
        inputID = createStyledTextField(inputFont);
        inputNombre = createStyledTextField(inputFont);
        inputPrimerApellido = createStyledTextField(inputFont);
        inputSegundoApellido = createStyledTextField(inputFont);
        inputCorreo = createStyledTextField(inputFont);
        inputTelefonoCasa = createStyledTextField(inputFont);
        inputTelefonoCelular = createStyledTextField(inputFont);
        inputDireccion = createStyledTextField(inputFont);
        inputEdad = createStyledTextField(inputFont);

        String[] optionsGenero = {"Masculino", "Femenino", "Otro"};
        generoDropdown = new JComboBox<>(optionsGenero);
        generoDropdown.setFont(inputFont);
        generoDropdown.setBackground(INPUT_BACKGROUND);

        int row = 0;
        addFormRow(gbc, labelID, inputID, row++);
        addFormRow(gbc, labelNombre, inputNombre, row++);
        addFormRow(gbc, labelPrimerApellido, inputPrimerApellido, row++);
        addFormRow(gbc, labelSegundoApellido, inputSegundoApellido, row++);
        addFormRow(gbc, labelCorreo, inputCorreo, row++);
        addFormRow(gbc, labelGenero, generoDropdown, row++);
        addFormRow(gbc, labelTelefonoCasa, inputTelefonoCasa, row++);
        addFormRow(gbc, labelTelefonoCelular, inputTelefonoCelular, row++);
        addFormRow(gbc, labelDireccion, inputDireccion, row++);
        addFormRow(gbc, labelEdad, inputEdad, row++);

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
    public String getNombre() {
        return inputNombre.getText();
    }
    public String getPrimerApellido() {
        return inputPrimerApellido.getText();
    }
    public String getSegundoApellido() {
        return inputSegundoApellido.getText();
    }
    public String getCorreo() {
        return inputCorreo.getText();
    }
    public String getTelefonoCasa() {
        return inputTelefonoCasa.getText();
    }
    public String getDireccion() {
        return inputDireccion.getText();
    }
    public int getEdad() {
        return Integer.parseInt(inputEdad.getText());
    }
    public String getTelefonoCelular() {
        return inputTelefonoCelular.getText();
    }
    public String getGenero() {
        return generoDropdown.getSelectedItem().toString().substring(0, 1);
    }
    public JButton getBtnRegistrar() {
        return btnRegistrar;
    }

    public void setID(String id) {
        inputID.setText(id);
    }
    public void setNombre(String nombre) {
        inputNombre.setText(nombre);
    }
    public void setPrimerApellido(String primerApellido) {
        inputPrimerApellido.setText(primerApellido);
    }
    public void setSegundoApellido(String segundoApellido) {
        inputSegundoApellido.setText(segundoApellido);
    }
    public void setCorreo(String correo) {
        inputCorreo.setText(correo);
    }
    public void setTelefonoCasa(String telefonoCasa) {
        inputTelefonoCasa.setText(telefonoCasa);
    }
    public void setDireccion(String direccion) {
        inputDireccion.setText(direccion);
    }
    public void setEdad(int edad) {
        inputEdad.setText(String.valueOf(edad));
    }
    public void setTelefonoCelular(String telefonoCelular) {
        inputTelefonoCelular.setText(telefonoCelular);
    }
    public void setGenero(String genero) {
        generoDropdown.setSelectedItem(genero);
    }

    public JTextField getTxtID() {
        return inputID;
    }

    public void addUpdateListener(ActionListener listener) {
        btnRegistrar.addActionListener(listener);
    }
}
