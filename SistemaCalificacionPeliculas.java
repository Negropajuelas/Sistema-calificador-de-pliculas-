import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SistemaCalificacionPeliculas {

    private static int numPeliculas;
    private static int numUsuarios;
    private static String[] nombresPeliculas;
    private static int[][] calificaciones;
    private static String[] nombresUsuarios;
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Sistema de Calificación de Películas");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Imagen de bienvenida
        JLabel banner = new JLabel(new ImageIcon("bienvenida.png"));
        frame.add(banner, BorderLayout.NORTH);

        // Panel para capturar número de películas y usuarios
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2));

        JLabel peliculasLabel = new JLabel("Número de películas:");
        JLabel usuariosLabel = new JLabel("Número de usuarios:");

        JTextField peliculasField = new JTextField();
        JTextField usuariosField = new JTextField();

        JButton startButton = new JButton("Iniciar");
        inputPanel.add(peliculasLabel);
        inputPanel.add(peliculasField);
        inputPanel.add(usuariosLabel);
        inputPanel.add(usuariosField);
        inputPanel.add(startButton);

        frame.add(inputPanel, BorderLayout.CENTER);

        // Acción al presionar el botón Iniciar
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    numPeliculas = Integer.parseInt(peliculasField.getText());
                    numUsuarios = Integer.parseInt(usuariosField.getText());

                    if (numPeliculas <= 0 || numUsuarios <= 0) {
                        JOptionPane.showMessageDialog(frame, "Por favor ingrese números válidos.");
                        return;
                    }

                    // Inicializamos los arreglos
                    nombresPeliculas = new String[numPeliculas];
                    calificaciones = new int[numUsuarios][numPeliculas];
                    nombresUsuarios = new String[numUsuarios];

                    // Cambiamos a una nueva vista para ingresar las películas
                    inputPanel.setVisible(false);
                    mostrarPanelPeliculas(frame);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Por favor ingrese números válidos.");
                }
            }
        });

        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void mostrarPanelPeliculas(JFrame frame) {
        JPanel peliculasPanel = new JPanel();
        peliculasPanel.setLayout(new GridLayout(numPeliculas + 1, 2));

        JLabel tituloLabel = new JLabel("Ingrese los nombres de las películas:");
        peliculasPanel.add(tituloLabel);

        JTextField[] peliculaFields = new JTextField[numPeliculas];
        for (int i = 0; i < numPeliculas; i++) {
            peliculaFields[i] = new JTextField();
            JLabel iconoPelicula = new JLabel(new ImageIcon("pelicula_icono.png"));
            peliculasPanel.add(iconoPelicula);
            peliculasPanel.add(peliculaFields[i]);
        }

        JButton siguienteButton = new JButton("Siguiente");
        siguienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < numPeliculas; i++) {
                    nombresPeliculas[i] = peliculaFields[i].getText();
                }

                frame.getContentPane().removeAll();
                mostrarPanelUsuarios(frame);
            }
        });
        peliculasPanel.add(siguienteButton);

        frame.getContentPane().add(peliculasPanel);
        frame.revalidate();
        frame.repaint();
    }

    private static void mostrarPanelUsuarios(JFrame frame) {
        JPanel usuariosPanel = new JPanel();
        usuariosPanel.setLayout(new GridLayout(numUsuarios + 1, 2));

        JLabel tituloLabel = new JLabel("Ingrese los nombres de los usuarios:");
        usuariosPanel.add(tituloLabel);

        JTextField[] usuarioFields = new JTextField[numUsuarios];
        for (int i = 0; i < numUsuarios; i++) {
            usuarioFields[i] = new JTextField();
            JLabel iconoUsuario = new JLabel(new ImageIcon("usuario_icono.png"));
            usuariosPanel.add(iconoUsuario);
            usuariosPanel.add(usuarioFields[i]);
        }

        JButton siguienteButton = new JButton("Siguiente");
        siguienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < numUsuarios; i++) {
                    nombresUsuarios[i] = usuarioFields[i].getText();
                }

                frame.getContentPane().removeAll();
                mostrarPanelCalificaciones(frame);
            }
        });
        usuariosPanel.add(siguienteButton);

        frame.getContentPane().add(usuariosPanel);
        frame.revalidate();
        frame.repaint();
    }

    private static void mostrarPanelCalificaciones(JFrame frame) {
        JPanel calificacionesPanel = new JPanel();
        calificacionesPanel.setLayout(new GridLayout(numUsuarios + 2, numPeliculas + 1));

        JLabel instruccionesLabel = new JLabel("Ingrese las calificaciones (1-5):");
        calificacionesPanel.add(instruccionesLabel);

        // Mostramos las películas en la parte superior
        for (int j = 0; j < numPeliculas; j++) {
            calificacionesPanel.add(new JLabel(nombresPeliculas[j]));
        }

        // Creamos campos para ingresar las calificaciones
        JTextField[][] camposCalificacion = new JTextField[numUsuarios][numPeliculas];
        for (int i = 0; i < numUsuarios; i++) {
            calificacionesPanel.add(new JLabel(nombresUsuarios[i]));
            for (int j = 0; j < numPeliculas; j++) {
                camposCalificacion[i][j] = new JTextField(2);
                calificacionesPanel.add(camposCalificacion[i][j]);
            }
        }

        JButton finalizarButton = new JButton("Finalizar");
        finalizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Guardamos las calificaciones
                for (int i = 0; i < numUsuarios; i++) {
                    for (int j = 0; j < numPeliculas; j++) {
                        try {
                            calificaciones[i][j] = Integer.parseInt(camposCalificacion[i][j].getText());
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(frame, "Por favor ingrese calificaciones válidas.");
                            return;
                        }
                    }
                }

                // Mostramos los resultados
                frame.getContentPane().removeAll();
                mostrarResultados(frame);
            }
        });

        calificacionesPanel.add(finalizarButton);

        frame.getContentPane().add(calificacionesPanel);
        frame.revalidate();
        frame.repaint();
    }

    private static void mostrarResultados(JFrame frame) {
        StringBuilder resultados = new StringBuilder("Resultados de las calificaciones:\n");

        for (int i = 0; i < numUsuarios; i++) {
            resultados.append("Usuario: ").append(nombresUsuarios[i]).append("\n");
            for (int j = 0; j < numPeliculas; j++) {
                resultados.append(nombresPeliculas[j]).append(": ").append(calificaciones[i][j]).append(" ");
            }
            resultados.append("\n");
        }

        // Calculamos el promedio por película
        double[] promedioPorPelicula = new double[numPeliculas];
        for (int j = 0; j < numPeliculas; j++) {
            int suma = 0;
            for (int i = 0; i < numUsuarios; i++) {
                suma += calificaciones[i][j];
            }
            promedioPorPelicula[j] = (double) suma / numUsuarios;
        }

        resultados.append("\nPromedio de calificación por película:\n");
        for (int j = 0; j < numPeliculas; j++) {
            resultados.append(nombresPeliculas[j]).append(": ").append(String.format("%.2f", promedioPorPelicula[j])).append("\n");
        }

        JOptionPane.showMessageDialog(frame, resultados.toString());

        // Finalizamos el programa
        System.exit(0);
    }
}
