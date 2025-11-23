package edu.uabc.AEFC.Proyecto;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Mapa extends JPanel implements ActionListener {
    private App app;
    // Controles
    private JButton salir_btn;
    private JButton btnTirar;
    
    // Info
    private JLabel lblSala;
    private JLabel lblAnfitrion;
    private JLabel lblJugadores;
    
    // Paneles visuales
    private PanelTablero panelTablero;
    private Dado dadoVisual; 
    
    // Lógica
    private Random random;
    private int posicionJugador = 1; // El jugador empieza en la casilla 1

    public Mapa(App app) {
        this.app = app;
        this.random = new Random();
        
        setLayout(new BorderLayout());

        // --- IZQUIERDA ---
        JPanel panelIzquierdo = new JPanel(new GridBagLayout());
        panelIzquierdo.setPreferredSize(new Dimension(150, 0));
        panelIzquierdo.setBackground(new Color(230, 230, 230)); 
        panelIzquierdo.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.GRAY));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0; 
        gbc.fill = GridBagConstraints.HORIZONTAL; 

        // Botón Salir
        gbc.gridy = 0; 
        salir_btn = new JButton("Salir de Sala");
        salir_btn.setBackground(Color.BLACK);
        salir_btn.setForeground(Color.RED);
        salir_btn.setFocusable(false);
        salir_btn.addActionListener(this);
        panelIzquierdo.add(salir_btn, gbc);

        // Espacio
        gbc.gridy = 1;
        panelIzquierdo.add(Box.createVerticalStrut(20), gbc);

        // Etiqueta Dado
        gbc.gridy = 2;
        JLabel lblDado = new JLabel("Tu Dado:", SwingConstants.CENTER);
        lblDado.setFont(new Font("Arial", Font.BOLD, 14));
        panelIzquierdo.add(lblDado, gbc);

        // Dado Visual
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE; 
        dadoVisual = new Dado("ImagenesProyecto/Cara 1.png"); 
        panelIzquierdo.add(dadoVisual, gbc);
        
        // Botón Tirar
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        btnTirar = new JButton("Tirar Dado");
        btnTirar.setBackground(Color.BLACK);
        btnTirar.setForeground(Color.BLUE);
        btnTirar.setFocusable(false);
        btnTirar.addActionListener(this);
        panelIzquierdo.add(btnTirar, gbc);
        
        add(panelIzquierdo, BorderLayout.WEST);

        // --- DERECHA ---
        JPanel panelDerecho = new JPanel();
        panelDerecho.setLayout(new BoxLayout(panelDerecho, BoxLayout.Y_AXIS));
        panelDerecho.setPreferredSize(new Dimension(200, 0)); 
        panelDerecho.setBackground(new Color(240, 240, 255)); 
        panelDerecho.setBorder(new EmptyBorder(20, 10, 20, 10)); 

        lblSala = new JLabel("Sala: --");
        lblSala.setFont(new Font("Arial", Font.BOLD, 18));
        lblSala.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelDerecho.add(lblSala);
        
        panelDerecho.add(Box.createRigidArea(new Dimension(0, 20))); 

        lblAnfitrion = new JLabel("Anfitrión: --");
        lblAnfitrion.setFont(new Font("Arial", Font.PLAIN, 14));
        lblAnfitrion.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelDerecho.add(lblAnfitrion);
        
        panelDerecho.add(Box.createRigidArea(new Dimension(0, 10)));

        lblJugadores = new JLabel("Jugadores: 0/0");
        lblJugadores.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelDerecho.add(lblJugadores);

        add(panelDerecho, BorderLayout.EAST); 

        // --- CENTRO (TABLERO) ---
        panelTablero = new PanelTablero("ImagenesProyecto/MapaSerpientes.jpg"); 
        add(panelTablero, BorderLayout.CENTER);
        
        // Agregamos un "escucha" para detectar cuando el panel ya tiene tamaño real
        panelTablero.addComponentListener(new ComponentAdapter() {
        @Override
        public void componentResized(ComponentEvent e) {
            // Cada vez que la ventana cambia de tamaño o se muestra por primera vez,
            // recalculamos la posición visual de la ficha.
            moverFichaACasilla(posicionJugador);
        }
        
        @Override
        public void componentShown(ComponentEvent e) {
            moverFichaACasilla(posicionJugador);
        }
        });
    }

    public void configurarSala(Sala sala) {
        lblSala.setText("Sala #" + sala.getNumeroSala());
        lblAnfitrion.setText("Host: " + sala.getNombreAnfitrion());
        lblJugadores.setText("Jugadores: " + sala.getJugadoresActuales() + "/" + sala.getCapacidad());
    }

    // --- MÉTODO MATEMÁTICO PARA COORDENADAS (ZIG-ZAG) ---
    private Point obtenerCoordenadas(int numeroCasilla) {
        // 1. Validar límites (por seguridad)
        if (numeroCasilla < 1) 
            numeroCasilla = 1;
        if (numeroCasilla > 100) 
            numeroCasilla = 100;

        // 2. Obtener dimensiones actuales del tablero
        // IMPORTANTE: Ajusta estos márgenes si tu imagen tiene bordes blancos
        int margenX = 0; 
        int margenY = 0;
        
        // El ancho útil es el ancho total menos los márgenes
        int anchoUtil = panelTablero.getWidth() - (margenX * 2);
        int altoUtil = panelTablero.getHeight() - (margenY * 2);

        // 3. Calcular tamaño de cada celda (casilla)
        int anchoCelda = anchoUtil / 10;
        int altoCelda = altoUtil / 10;

        // 4. Lógica del Tablero
        // Restamos 1 para trabajar con índices del 0 al 99
        int n = numeroCasilla - 1;
        
        // Calculamos en qué fila visual está (0 es la de abajo, 9 la de arriba)
        int filaVisual = n / 10; 
        int columnaVisual = n % 10;

        // 5. APLICAR ZIG-ZAG
        // Si la fila es impar (1, 3, 5...), la dirección se invierte (Derecha a Izquierda)
        // Fila 0 (1-10): Normal
        // Fila 1 (11-20): Invertida
        if (filaVisual % 2 != 0) {
            columnaVisual = 9 - columnaVisual;
        }

        // 6. Convertir a Coordenadas de Píxeles (X, Y)
        
        // Cálculo de X: (Columna * Ancho) + Margen
        int x = margenX + (columnaVisual * anchoCelda);

        // Cálculo de Y: Java empieza arriba (0), pero el juego empieza abajo.
        // Invertimos la fila: La fila visual 0 debe ser la fila Java 9.
        int filaJava = 9 - filaVisual;
        int y = margenY + (filaJava * altoCelda);

        // 7. Centrar la ficha en la casilla
        // Sumamos la mitad de la celda y restamos la mitad del tamaño de la ficha (40px)
        // Asumiendo que tu ficha mide 40x40 (como pusimos en el código anterior)
        int centroX = x + (anchoCelda / 2) - 20; 
        int centroY = y + (altoCelda / 2) - 20;

        return new Point(centroX, centroY);
    }

    private void moverFichaACasilla(int nuevaCasilla) {
        this.posicionJugador = nuevaCasilla;
        
        // 1. Obtenemos los pixeles donde debe ir la ficha
        Point coordenadas = obtenerCoordenadas(nuevaCasilla);
        
        // 2. Le decimos al tablero que mueva la ficha visualmente
        panelTablero.actualizarPosicionFicha(coordenadas.x, coordenadas.y);
        
        System.out.println("Ficha movida a casilla " + nuevaCasilla + " (X:" + coordenadas.x + ", Y:" + coordenadas.y + ")");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == salir_btn) {
            int confirm = JOptionPane.showConfirmDialog(this, "¿Salir?", "Salir", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) app.setMenuJuego();
        }
        else if (e.getSource() == btnTirar) {
            int dado = random.nextInt(6) + 1;
            dadoVisual.cambiarImagen("ImagenesProyecto/Cara " + dado + ".png");
            
            // Lógica simple de movimiento (Suma lo que salió en el dado)
            int nuevaPos = posicionJugador + dado;
            if(nuevaPos > 100) nuevaPos = 100; // Tope en 100
            
            moverFichaACasilla(nuevaPos);
        }
    }

    // --- CLASES INTERNAS ---

    class PanelTablero extends JPanel {
        private Image imagenFondo;
        private Ficha fichaJugador; // Referencia a la ficha

        public PanelTablero(String rutaImagen) {
            this.imagenFondo = new ImageIcon(rutaImagen).getImage();
            setLayout(null); // IMPORTANTE: Layout nulo para mover libremente
            setBackground(Color.DARK_GRAY);
            
            // Creamos la ficha (Círculo Rojo)
            fichaJugador = new Ficha("ImagenesProyecto/fichaRoja.png");
            
            // La agregamos al panel (pero aun no tiene posición definida)
            add(fichaJugador);
        }

        // Método para mover la ficha visualmente
        public void actualizarPosicionFicha(int x, int y) {
            // setBounds(x, y, ancho, alto)
            // Ajustamos para que x,y sea el centro de la ficha
            fichaJugador.setBounds(x, y, 30, 30); 
            repaint(); // Repintar el tablero para mostrar cambios
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (imagenFondo != null) {
                g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }

    // Clase visual para la ficha del jugador
    class Ficha extends JPanel {
        private Image imagenFicha;
        
        public Ficha(String rutaImagen) {
            // 1. Cargamos la imagen
            this.imagenFicha = new ImageIcon(rutaImagen).getImage();
            
            // 2. Hacemos el panel transparente para que se vea el mapa detrás del personaje
            setOpaque(false); 
            
            // 3. Definimos un tamaño base (puedes ajustarlo)
            setSize(30, 30);  
        }
        
        // (Opcional) Método por si quieres cambiar de personaje en medio del juego
        public void cambiarPersonaje(String nuevaRuta) {
             this.imagenFicha = new ImageIcon(nuevaRuta).getImage();
             repaint();
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            // Dibujamos la imagen del personaje
            if (imagenFicha != null) {
                g.drawImage(imagenFicha, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }

    class Dado extends JPanel {
        private Image imagenDado;
        public Dado(String rutaImagen) {
            this.imagenDado = new ImageIcon(rutaImagen).getImage();
            setBackground(new Color(230, 230, 230)); 
            setPreferredSize(new Dimension(100, 100)); 
        }
        public void cambiarImagen(String nuevaRuta) {
            this.imagenDado = new ImageIcon(nuevaRuta).getImage();
            this.repaint();
        }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (imagenDado != null) {
                g.drawImage(imagenDado, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }
}