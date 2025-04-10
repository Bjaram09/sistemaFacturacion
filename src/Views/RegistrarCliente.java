package Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class RegistrarCliente extends JFrame{
    private JPanel body;
    private JPanel form;
    private JPanel buttonField;
    private JPanel inputFields;
    private JPanel inputs_col_1;
    private JPanel inputs_col_2;

    private JTextField inputID;
    private JTextField inputPrimerApellido;
    private JTextField inputGenero;
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
    private JButton btnCancelar;
    private JPanel titulo;

    public RegistrarCliente() {
        setTitle("Registrar Cliente");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        form = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Padding
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Labels & Inputs
        labelID = new JLabel("ID:");
        inputID = new JTextField(15);
        labelNombre = new JLabel("Nombre:");
        inputNombre = new JTextField(15);
        labelPrimerApellido = new JLabel("Primer Apellido:");
        inputPrimerApellido = new JTextField(15);
        labelSegundoApellido = new JLabel("Segundo Apellido:");
        inputSegundoApellido = new JTextField(15);
        labelCorreo = new JLabel("Correo:");
        inputCorreo = new JTextField(15);

        labelGenero = new JLabel("Género:");
        inputGenero = new JTextField(15);
        labelTelefonoCasa = new JLabel("Teléfono Casa:");
        inputTelefonoCasa = new JTextField(15);
        labelTelefonoCelular = new JLabel("Teléfono Celular:");
        inputTelefonoCelular = new JTextField(15);
        labelDireccion = new JLabel("Dirección:");
        inputDireccion = new JTextField(15);
        labelEdad = new JLabel("Edad:");
        inputEdad = new JTextField(15);

        // Position elements in grid
        gbc.gridx = 0; gbc.gridy = 0; form.add(labelID, gbc);
        gbc.gridx = 1; gbc.gridy = 0; form.add(inputID, gbc);
        gbc.gridx = 0; gbc.gridy = 1; form.add(labelNombre, gbc);
        gbc.gridx = 1; gbc.gridy = 1; form.add(inputNombre, gbc);
        gbc.gridx = 0; gbc.gridy = 2; form.add(labelPrimerApellido, gbc);
        gbc.gridx = 1; gbc.gridy = 2; form.add(inputPrimerApellido, gbc);
        gbc.gridx = 0; gbc.gridy = 3; form.add(labelSegundoApellido, gbc);
        gbc.gridx = 1; gbc.gridy = 3; form.add(inputSegundoApellido, gbc);
        gbc.gridx = 0; gbc.gridy = 4; form.add(labelCorreo, gbc);
        gbc.gridx = 1; gbc.gridy = 4; form.add(inputCorreo, gbc);

        gbc.gridx = 0; gbc.gridy = 5; form.add(labelGenero, gbc);
        gbc.gridx = 1; gbc.gridy = 5; form.add(inputGenero, gbc);
        gbc.gridx = 0; gbc.gridy = 6; form.add(labelTelefonoCasa, gbc);
        gbc.gridx = 1; gbc.gridy = 6; form.add(inputTelefonoCasa, gbc);
        gbc.gridx = 0; gbc.gridy = 7; form.add(labelTelefonoCelular, gbc);
        gbc.gridx = 1; gbc.gridy = 7; form.add(inputTelefonoCelular, gbc);
        gbc.gridx = 0; gbc.gridy = 8; form.add(labelDireccion, gbc);
        gbc.gridx = 1; gbc.gridy = 8; form.add(inputDireccion, gbc);
        gbc.gridx = 0; gbc.gridy = 9; form.add(labelEdad, gbc);
        gbc.gridx = 1; gbc.gridy = 9; form.add(inputEdad, gbc);


        // Buttons
        buttonField = new JPanel();
        btnRegistrar = new JButton("Registrar");
        btnCancelar = new JButton("Cancelar");
        buttonField.add(btnRegistrar);
        buttonField.add(btnCancelar);

        // Add panels to main frame
        add(form, BorderLayout.CENTER);
        add(buttonField, BorderLayout.SOUTH);

        setVisible(true);
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
    public String getGenero(){
        return inputGenero.getText();
    }

    public void limpiarInputs(){
        inputID.setText("");
        inputNombre.setText("");
        inputPrimerApellido.setText("");
        inputSegundoApellido.setText("");
        inputCorreo.setText("");
        inputTelefonoCasa.setText("");
        inputDireccion.setText("");
        inputEdad.setText("");
        inputTelefonoCelular.setText("");
        inputGenero.setText("");
    }

    public void addUpdateListener(ActionListener listener) {
        btnRegistrar.addActionListener(listener);
    }
}
