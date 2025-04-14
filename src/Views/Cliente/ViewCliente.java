package Views.Cliente;

import Models.Cliente;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Collection;

public class ViewCliente extends JFrame {
    private DefaultTableModel modelo;
    private JTable tablaClientes;
    private JButton btnRegistrarCliente;
    private ActionListener deleteListener;

    public ViewCliente() {
        setTitle("Clientes");
        setSize(800, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        String[] columnNames = {"Cédula", "Nombre", "Primer Apellido", "Segundo Apellido", "Género", "Edad", "Acciones"};
        modelo = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 6;
            }
        };

        tablaClientes = new JTable(modelo);
        tablaClientes.setRowHeight(40);
        tablaClientes.setIntercellSpacing(new Dimension(10, 10));
        tablaClientes.setShowGrid(true);
        tablaClientes.setGridColor(Color.LIGHT_GRAY);

        // Add padding to all cells except the delete button column
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        for (int i = 0; i < tablaClientes.getColumnCount() - 1; i++) {
            tablaClientes.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }

        // Set up the delete button column separately
        TableColumn deleteColumn = tablaClientes.getColumnModel().getColumn(6);
        deleteColumn.setCellRenderer(new ButtonRenderer());
        deleteColumn.setCellEditor(new ButtonEditor(new JTextField()));

        JScrollPane scrollPane = new JScrollPane(tablaClientes);
        add(scrollPane, BorderLayout.CENTER);

        btnRegistrarCliente = new JButton("Registrar Cliente");
        JPanel panelBotones = new JPanel();
        panelBotones.add(btnRegistrarCliente);
        add(panelBotones, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void setClientes(Collection<Cliente> clientes) {
        modelo.setRowCount(0);
        for (Cliente cliente : clientes) {
            String genero = "";
            if(cliente.getGenero().equals("M")){
                genero = "Masculino";
            } else if(cliente.getGenero().equals("F")){
                genero = "Femenino";
            } else {
                genero = "Otro";
            }

            Object[] row = {
                cliente.getId(),
                cliente.getNombre(),
                cliente.getPrimerApellido(),
                cliente.getSegundoApellido(),
                genero,
                cliente.getEdad(),
                cliente
            };
            modelo.addRow(row);
        }
    }

    public JButton getBtnRegistrarCliente() {
        return btnRegistrarCliente;
    }

    public void setDeleteListener(ActionListener listener) {
        this.deleteListener = listener;
    }

    public class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
            setText("Eliminar");
            setBackground(Color.RED);
            setForeground(Color.WHITE);
            setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }

    public class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private Cliente cliente;
        private boolean isPushed;

        public ButtonEditor(JTextField textField) {
            super(textField);
            button = new JButton();
            button.setOpaque(true);
            button.setText("Eliminar");
            button.setBackground(Color.RED);
            button.setForeground(Color.WHITE);
            button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            button.addActionListener(e -> {
                if (deleteListener != null && cliente != null) {
                    ActionEvent event = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "delete");
                    deleteListener.actionPerformed(event);
                }
                fireEditingStopped();
            });
        }

        public Cliente getCliente() {
            return cliente;
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            cliente = (Cliente) value;
            isPushed = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            isPushed = false;
            return cliente;
        }

        @Override
        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }
    }
}
