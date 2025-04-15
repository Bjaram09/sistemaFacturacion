package Views.Producto;

import Models.Producto;


import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

public class ViewProducto extends JFrame {
        private DefaultTableModel modelo;
        private JTable tablaProductos;
        private JButton btnRegistrarProducto;
        private ActionListener deleteListener;
        private ActionListener updateListener;

        private static final Color PRIMARY_COLOR = new Color(41, 128, 185); // Blue
        private static final Color SECONDARY_COLOR = new Color(52, 152, 219); // Lighter Blue
        private static final Color ACCENT_COLOR = new Color(231, 76, 60); // Red
        private static final Color BACKGROUND_COLOR = new Color(236, 240, 241); // Light Gray
        private static final Color TABLE_HEADER_COLOR = new Color(52, 73, 94); // Dark Blue
        private static final Color TABLE_ROW_COLOR = Color.WHITE;
        private static final Color TABLE_ALTERNATE_ROW_COLOR = new Color(245, 245, 245);

        public ViewProducto() {
            setTitle("Gestión de Productos");
            setSize(1400, 600);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            setLayout(new BorderLayout());
            getContentPane().setBackground(BACKGROUND_COLOR);

            // Create a header panel
            JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            headerPanel.setBackground(PRIMARY_COLOR);
            headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

            JLabel titleLabel = new JLabel("Lista de Productos");
            titleLabel.setForeground(Color.WHITE);
            titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
            headerPanel.add(titleLabel);
            add(headerPanel, BorderLayout.NORTH);

            String[] columnNames = {"ID", "Descripcion", "Cantidad Min", "Cantidad Max", "Precio Unitario", "Acciones"};
            modelo = new DefaultTableModel(columnNames, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return column == 5;
                }
            };

            tablaProductos = new JTable(modelo);
            tablaProductos.setRowHeight(40);
            tablaProductos.setIntercellSpacing(new Dimension(10, 10));
            tablaProductos.setShowGrid(true);
            tablaProductos.setGridColor(new Color(230, 230, 230));
            tablaProductos.setBackground(TABLE_ROW_COLOR);
            tablaProductos.setSelectionForeground(ACCENT_COLOR);
            tablaProductos.setFont(new Font("Segoe UI", Font.PLAIN, 14));

            // Style the table header
            JTableHeader header = tablaProductos.getTableHeader();
            header.setBackground(TABLE_HEADER_COLOR);
            header.setForeground(Color.WHITE);
            header.setFont(new Font("Segoe UI", Font.BOLD, 14));
            header.setPreferredSize(new Dimension(0, 40));

            // Add padding to all cells except the actions column
            DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    c.setBackground(row % 2 == 0 ? TABLE_ROW_COLOR : TABLE_ALTERNATE_ROW_COLOR);
                    return c;
                }
            };
            renderer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            for (int i = 0; i < tablaProductos.getColumnCount() - 1; i++) {
                tablaProductos.getColumnModel().getColumn(i).setCellRenderer(renderer);
            }

            // Set up the actions column
            TableColumn actionsColumn = tablaProductos.getColumnModel().getColumn(5);
            actionsColumn.setCellRenderer(new Views.Producto.ViewProducto.ActionsRenderer());
            actionsColumn.setCellEditor(new Views.Producto.ViewProducto.ActionsEditor(new JTextField()));

            JScrollPane scrollPane = new JScrollPane(tablaProductos);
            scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            add(scrollPane, BorderLayout.CENTER);

            // Style the register button
            btnRegistrarProducto = new JButton("Registrar Nuevo Producto");
            btnRegistrarProducto.setBackground(PRIMARY_COLOR);
            btnRegistrarProducto.setForeground(Color.WHITE);
            btnRegistrarProducto.setFont(new Font("Segoe UI", Font.BOLD, 14));
            btnRegistrarProducto.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
            btnRegistrarProducto.setFocusPainted(false);
            btnRegistrarProducto.setCursor(new Cursor(Cursor.HAND_CURSOR));

            JPanel panelBotones = new JPanel();
            panelBotones.setBackground(BACKGROUND_COLOR);
            panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
            panelBotones.add(btnRegistrarProducto);
            add(panelBotones, BorderLayout.SOUTH);

            setVisible(true);
        }

        public void listarProductos(Collection<Producto> productos) {
            modelo.setRowCount(0);
            for (Producto producto : productos) {
                Object[] row = {
                        producto.getId(),
                        producto.getDescripcion(),
                        producto.getMin(),
                        producto.getMax(),
                        producto.getPrecioPorUnidad(),
                        producto
                };
                modelo.addRow(row);
            }
        }

        public JButton getBtnRegistrarProducto() {
            return btnRegistrarProducto;
        }

        public void setDeleteListener(ActionListener listener) {
            this.deleteListener = listener;
        }

        public void setUpdateListener(ActionListener listener) {
            this.updateListener = listener;
        }

        public JTable getTablaProductos() {
            return tablaProductos;
        }

        private class ActionsRenderer extends JPanel implements TableCellRenderer {
            private JButton deleteButton;
            private JButton updateButton;

            public ActionsRenderer() {
                setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
                setBackground(TABLE_ROW_COLOR);

                deleteButton = new JButton("Eliminar");
                deleteButton.setBackground(ACCENT_COLOR);
                deleteButton.setForeground(Color.WHITE);
                deleteButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
                deleteButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                deleteButton.setFocusPainted(false);
                deleteButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

                updateButton = new JButton("Actualizar");
                updateButton.setBackground(SECONDARY_COLOR);
                updateButton.setForeground(Color.WHITE);
                updateButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
                updateButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                updateButton.setFocusPainted(false);
                updateButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

                add(deleteButton);
                add(updateButton);
            }

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                setBackground(row % 2 == 0 ? TABLE_ROW_COLOR : TABLE_ALTERNATE_ROW_COLOR);
                return this;
            }
        }

        public class ActionsEditor extends DefaultCellEditor {
            private JPanel panel;
            private JButton deleteButton;
            private JButton updateButton;
            private Producto producto;
            private boolean isPushed;

            public ActionsEditor(JTextField textField) {
                super(textField);
                panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
                panel.setBackground(TABLE_ROW_COLOR);

                deleteButton = new JButton("Eliminar");
                deleteButton.setBackground(ACCENT_COLOR);
                deleteButton.setForeground(Color.WHITE);
                deleteButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
                deleteButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                deleteButton.setFocusPainted(false);
                deleteButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                deleteButton.addActionListener(e -> {
                    if (deleteListener != null && producto != null) {
                        ActionEvent event = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "delete");
                        deleteListener.actionPerformed(event);
                    }
                    fireEditingStopped();
                });

                updateButton = new JButton("Actualizar");
                updateButton.setBackground(SECONDARY_COLOR);
                updateButton.setForeground(Color.WHITE);
                updateButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
                updateButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                updateButton.setFocusPainted(false);
                updateButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                updateButton.addActionListener(e -> {
                    if (updateListener != null && producto != null) {
                        ActionEvent event = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "update");
                        updateListener.actionPerformed(event);
                    }
                    fireEditingStopped();
                });

                panel.add(deleteButton);
                panel.add(updateButton);
            }

            public Producto getProducto() {
                return producto;
            }

            @Override
            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                producto = (Producto) value;
                isPushed = true;
                return panel;
            }

            @Override
            public Object getCellEditorValue() {
                isPushed = false;
                return producto;
            }

            @Override
            public boolean stopCellEditing() {
                isPushed = false;
                return super.stopCellEditing();
            }
        }
}
