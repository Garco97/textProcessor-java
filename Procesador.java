package procesador;

import java.awt.Color;
import java.awt.Event;
import java.awt.EventQueue;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Document;
import javax.swing.text.EditorKit;
import javax.swing.text.Highlighter;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

@SuppressWarnings("serial")
public class Procesador extends JFrame {

	/**
	 * Atributos utilizador a la hora de la realizacion del procesador de texto
	 */
	private JPanel contentPane;
	private JTextPane textPane;
	private JToolBar toolBar;
	private JMenuItem mntmAbrir;
	private JMenuBar menuBar;
	private JMenu mnMenu;
	private KeyStroke ctrl_N;
	private KeyStroke ctrl_U;
	private KeyStroke ctrl_K;
	private JButton btnNegrita;
	private JButton btnSubrayado;
	private JButton btnCursiva;
	@SuppressWarnings("rawtypes")
	private JComboBox cbTamanhos;
	@SuppressWarnings("rawtypes")
	private JComboBox cbLetras;
	@SuppressWarnings("rawtypes")
	private JComboBox cbColores;
	private Color[] colors;
	private JButton btnAlinearDer; 
	private JButton btnAlinearIzq;
	private JButton btnAlinearCentro;
	private JButton btnJustificar;
	private SimpleAttributeSet attribs = new SimpleAttributeSet();
	private String tipo_letra;
	private int tamanho_letra;
	private JMenuItem mntmGuardar;
	private boolean underline = false;
	private boolean bold = false;
	private boolean italics = false;
	private   Color colorfondodefault;
	private JTextField txtbuscar;
	private JTextField txtremplazar;
	private JLabel estado;
	private Highlighter hilit;
	private  Highlighter.HighlightPainter painter;
	private JLabel lblEscribeLaPalabra;



	/**
	 * Metodo main que lanza el procesador
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Procesador frame = new Procesador();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Constructor del procesador
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Procesador() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 737, 435);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		textPane = new JTextPane();


		toolBar = new JToolBar();

		tamanho_letra = 10;
		tipo_letra = "Sans Serif";

		StyleConstants.setFontSize(attribs, tamanho_letra);
		StyleConstants.setFontFamily(attribs, tipo_letra);
		StyleConstants.setUnderline(attribs, false);
		StyleConstants.setBold(attribs, false);
		StyleConstants.setItalic(attribs, false);
		btnNegrita = new JButton("");
		btnNegrita.setIcon(new ImageIcon(Procesador.class.getResource("/procesador/n.png")));
		toolBar.add(btnNegrita);
		btnNegrita.addActionListener(new negrita());
		btnNegrita.setSize(20, 20);

		btnCursiva = new JButton("");
		btnCursiva.setIcon(new ImageIcon(Procesador.class.getResource("/procesador/k.png")));
		toolBar.add(btnCursiva);
		btnCursiva.addActionListener(new cursiva());
		btnCursiva.setSize(20, 20);


		btnSubrayado = new JButton("");
		btnSubrayado.setIcon(new ImageIcon(Procesador.class.getResource("/procesador/S.png")));
		toolBar.add(btnSubrayado);
		btnSubrayado.addActionListener(new subrayado());
		btnSubrayado.setSize(20, 20);


		Integer[] tamanhos = {10,12,14,16,18,20,22,24};
		cbTamanhos = new JComboBox(tamanhos);
		cbTamanhos.addActionListener(new tamanhoLetra());

		GraphicsEnvironment g = GraphicsEnvironment.getLocalGraphicsEnvironment();
		String[] letras = g.getAvailableFontFamilyNames();
		cbLetras = new JComboBox(letras);
		cbLetras.addActionListener(new tipoLetra());


		String[] colores = {"Negro","Azul","Cyan","Gris oscuro","Gris","Verde","Rojo"};
		colors = new Color[7];
		colors[0] = Color.black;
		colors[1] = Color.blue;
		colors[2] = Color.cyan;
		colors[3] = Color.darkGray;
		colors[4] = Color.gray;
		colors[5] = Color.green;
		colors[6] = Color.red;
		cbColores = new JComboBox(colores);
		cbColores.addActionListener(new color());

		btnAlinearDer = new JButton("");
		btnAlinearDer.setIcon(new ImageIcon(Procesador.class.getResource("/procesador/text-align-right_icon-icons.com_48214 (1).png")));
		btnAlinearDer.addActionListener(new alinearDerecha());

		btnAlinearIzq = new JButton("");
		btnAlinearIzq.setIcon(new ImageIcon(Procesador.class.getResource("/procesador/text-align-left_icon-icons.com_48215 (1).png")));
		btnAlinearIzq.addActionListener(new alinearIzquierda());

		btnAlinearCentro = new JButton("");
		btnAlinearCentro.setIcon(new ImageIcon(Procesador.class.getResource("/procesador/text-align-center_icon-icons.com_48217 (2).png")));
		btnAlinearCentro.addActionListener(new alinearCentro());

		btnJustificar = new JButton("Justificar");
		btnJustificar.addActionListener(new justificar());
		toolBar.add(btnAlinearIzq);
		toolBar.add(btnAlinearCentro);
		toolBar.add(btnAlinearDer);
		toolBar.add(btnJustificar);


		toolBar.add(cbLetras);
		toolBar.add(cbTamanhos);
		toolBar.add(cbColores);

		txtbuscar = new JTextField();

		hilit = new DefaultHighlighter();
		painter = new DefaultHighlighter.DefaultHighlightPainter(Color.CYAN);



		colorfondodefault = txtbuscar.getBackground();

		txtbuscar.addKeyListener(new KeyListener(){


			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ESCAPE){
					hilit.removeAllHighlights();
					txtbuscar.setText("");
					txtbuscar.setBackground(colorfondodefault);
				}
			}


			@Override
			public void keyReleased(KeyEvent e) {
				buscarTexto();
			}


			@Override
			public void keyTyped(KeyEvent e) {
			}
		});



		txtremplazar = new JTextField();
		txtremplazar.setColumns(10);
		txtremplazar.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ESCAPE){
					txtremplazar.setText("");
				}
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					remplazarTexto();
				}

			}

			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {

			}
		});


		estado = new JLabel("Escribe la palabra a buscar.");
		ActionMap acciones = textPane.getActionMap();
		lblEscribeLaPalabra = new JLabel("Escribe la palabra por la que remplazar");
		Action accionCopiar = acciones.get(DefaultEditorKit.copyAction);
		Action accionPegar = acciones.get(DefaultEditorKit.pasteAction);
		Action accionCortar = acciones.get(DefaultEditorKit.cutAction);
		accionCopiar.putValue(Action.NAME, "Copiar");
		accionCopiar.putValue(Action.ACCELERATOR_KEY,KeyStroke.getAWTKeyStroke('C', Event.CTRL_MASK)); 

		accionCortar.putValue(Action.NAME, "Cortar");
		accionCortar.putValue(Action.ACCELERATOR_KEY,KeyStroke.getAWTKeyStroke('X', Event.CTRL_MASK)); 

		accionPegar.putValue(Action.NAME, "Pegar");
		accionCopiar.putValue(Action.ACCELERATOR_KEY, KeyStroke.getAWTKeyStroke('V', Event.CTRL_MASK)); 

		menuBar = new JMenuBar();

		mnMenu = new JMenu("Archivo");
		menuBar.add(mnMenu);

		mntmAbrir = new JMenuItem("Abrir Archivo");
		mntmAbrir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
		mntmAbrir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abrirArchivo();


			}
		});

		mnMenu.add(mntmAbrir);

		mntmGuardar = new JMenuItem("Guardar como");
		mntmGuardar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		mntmGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				guardarArchivo();

			}
		});
		mnMenu.add(mntmGuardar);

		JMenu mnEditar = new JMenu("Editar");
		menuBar.add(mnEditar);


		JMenuItem menuItem = mnEditar.add(accionCortar);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));
		JMenuItem menuItem_1 = mnEditar.add(accionCopiar);
		menuItem_1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
		JMenuItem menuItem_2 = mnEditar.add(accionPegar);
		menuItem_2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK));

		//Creacion del layout reescalable 
		textPane.setContentType("text/plain");
		textPane.setCharacterAttributes(attribs, true);
		textPane.setHighlighter(hilit);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
				gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(menuBar, GroupLayout.DEFAULT_SIZE, 727, Short.MAX_VALUE)
				.addComponent(toolBar, GroupLayout.DEFAULT_SIZE, 727, Short.MAX_VALUE)
				.addGroup(gl_contentPane.createSequentialGroup()
						.addContainerGap()
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(txtremplazar, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
								.addComponent(txtbuscar, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(estado, GroupLayout.PREFERRED_SIZE, 569, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblEscribeLaPalabra, GroupLayout.PREFERRED_SIZE, 569, GroupLayout.PREFERRED_SIZE))
						.addGap(17))
				.addGroup(gl_contentPane.createSequentialGroup()
						.addComponent(textPane, GroupLayout.DEFAULT_SIZE, 724, Short.MAX_VALUE)
						.addGap(3))
				);
		gl_contentPane.setVerticalGroup(
				gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
						.addComponent(menuBar, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
						.addComponent(toolBar, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
						.addComponent(textPane, GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(txtbuscar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(estado, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE))
						.addGap(1)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(txtremplazar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblEscribeLaPalabra, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE))
						.addContainerGap())
				);
		contentPane.setLayout(gl_contentPane);





		

		ctrl_K = KeyStroke.getKeyStroke(KeyEvent.VK_K,Event.CTRL_MASK);
		ActionMap mapaAccion = contentPane.getActionMap();
		InputMap map = contentPane.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		map.put(ctrl_K, ctrl_K.toString());
		mapaAccion.put(ctrl_K.toString(), new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(!italics){
					StyleConstants.setItalic(attribs, true);
					textPane.setCharacterAttributes(attribs, true);
					italics = true;
				}else if(italics){
					StyleConstants.setItalic(attribs,false);
					textPane.setCharacterAttributes(attribs, true);
					italics = false;
				}
			}
		});

		ctrl_N = KeyStroke.getKeyStroke(KeyEvent.VK_N,Event.CTRL_MASK);
		map.put(ctrl_N, ctrl_N.toString());
		mapaAccion.put(ctrl_N.toString(), new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(!bold){
					StyleConstants.setBold(attribs,true);
					textPane.setCharacterAttributes(attribs, true);
					bold = true;

				}else if(bold){
					StyleConstants.setBold(attribs,false);
					textPane.setCharacterAttributes(attribs, true);
					bold = false;
				}
			}
		});
		ctrl_U = KeyStroke.getKeyStroke(KeyEvent.VK_U,Event.CTRL_MASK);
		map.put(ctrl_U, ctrl_U.toString());
		mapaAccion.put(ctrl_U.toString(), new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				if(!underline){
					StyleConstants.setUnderline(attribs,true);
					textPane.setCharacterAttributes(attribs, true);
					underline = true;
				}else{
					StyleConstants.setUnderline(attribs,false);
					textPane.setCharacterAttributes(attribs, true);
					underline = false;
				}
			}
		});

	}


	/**
	 * Métodos y clases privados usados durante el desarrollo del procesador de texto
	 */
	private void abrirArchivo() {

		JFileChooser fcAbrir = new JFileChooser();
		fcAbrir.setDialogTitle("Abrir archivo");
		fcAbrir.addChoosableFileFilter(new FileNameExtensionFilter("Archivos .txt", "txt","TXT"));
		int valorDevuelto = fcAbrir.showOpenDialog(null);
		//Recoger el nombre del fichero seleccionado por el usuario
		if (valorDevuelto == JFileChooser.APPROVE_OPTION) {
			EditorKit kit = textPane.getEditorKit();
			Document doc = kit.createDefaultDocument();
			try {
				kit.read(new FileInputStream(new File(fcAbrir.getSelectedFile().getAbsolutePath())), doc, 0);
			} catch (IOException | BadLocationException e) {
				e.printStackTrace();
			}
			textPane.setDocument(doc);
		}
	}
	//Metodo para guardar archivos
	private void guardarArchivo() {

		JFileChooser fcGuardar = new JFileChooser();
		fcGuardar.addChoosableFileFilter(new FileNameExtensionFilter("Archivos .txt", "txt","TXT"));
		fcGuardar.setDialogTitle("Guardar archivo");
		fcGuardar.setApproveButtonText("Guardar");
		int valorDevuelto = fcGuardar.showOpenDialog(null);
		if (valorDevuelto == JFileChooser.APPROVE_OPTION) {
			File file = fcGuardar.getSelectedFile();
			String ruta = file.getAbsolutePath();
			try {
				PrintWriter pw = new PrintWriter(new File(ruta));
				pw.write(textPane.getText());
				pw.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	private class tamanhoLetra implements ActionListener{
		public void actionPerformed(ActionEvent e){
			tamanho_letra = Integer.parseInt(cbTamanhos.getSelectedItem().toString());
			StyleConstants.setFontSize(attribs, tamanho_letra);
			textPane.setCharacterAttributes(attribs, true);

		}
	}
	private class tipoLetra implements ActionListener{
		public void actionPerformed(ActionEvent e){
			tipo_letra = (String) cbLetras.getSelectedItem();
			StyleConstants.setFontFamily(attribs, tipo_letra);
			textPane.setCharacterAttributes(attribs, true);

		}
	}

	private class negrita implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(!bold){
				StyleConstants.setBold(attribs,true);
				textPane.setCharacterAttributes(attribs, true);
				bold = true;

			}else if(bold){
				StyleConstants.setBold(attribs,false);
				textPane.setCharacterAttributes(attribs, true);
				bold = false;
			}
		}
	}
	private class cursiva implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(!italics){
				StyleConstants.setItalic(attribs, true);
				textPane.setCharacterAttributes(attribs, true);
				italics = true;
			}else if(italics){
				StyleConstants.setItalic(attribs,false);
				textPane.setCharacterAttributes(attribs, true);
				italics = false;
			}

		}
	}
	private class subrayado implements ActionListener{
		public void actionPerformed(ActionEvent e){

			if(!underline){
				StyleConstants.setUnderline(attribs, true);
				textPane.setCharacterAttributes(attribs, true);
				underline = true;
			}else{
				StyleConstants.setUnderline(attribs, false);
				textPane.setCharacterAttributes(attribs, true);
				underline = false;
			}

		}
	}
	private class color implements ActionListener{
		public void actionPerformed(ActionEvent e){
			Color color = null;
			switch (cbColores.getSelectedIndex()) {
			case 0:
				color = colors[0];

				break;
			case 1:
				color = colors[1];

				break;
			case 2:
				color = colors[2];

				break;
			case 3:
				color = colors[3];

				break;
			case 4:
				color = colors[4];

				break;
			case 5:
				color = colors[5];

				break;
			case 6:
				color = colors[6];

				break;
			}
			StyleConstants.setForeground(attribs, color);
			textPane.setCharacterAttributes(attribs, true);


		}
	}
	private class alinearDerecha implements ActionListener{
		public void actionPerformed(ActionEvent e){
			StyleConstants.setAlignment(attribs , StyleConstants.ALIGN_RIGHT);
			textPane.setParagraphAttributes(attribs, true); 
		}
	}
	private class alinearIzquierda implements ActionListener{
		public void actionPerformed(ActionEvent e){
			StyleConstants.setAlignment(attribs , StyleConstants.ALIGN_LEFT);
			textPane.setParagraphAttributes(attribs, true); 
		}
	}
	private class alinearCentro implements ActionListener{
		public void actionPerformed(ActionEvent e){
			StyleConstants.setAlignment(attribs , StyleConstants.ALIGN_CENTER);
			textPane.setParagraphAttributes(attribs, true); 
		}
	}
	private class justificar implements ActionListener{
		public void actionPerformed(ActionEvent e){
			StyleConstants.setAlignment(attribs , StyleConstants.ALIGN_JUSTIFIED);
			textPane.setParagraphAttributes(attribs, true); 
		}
	}
	private void buscarTexto() {

		hilit.removeAllHighlights();
		String s = txtbuscar.getText();

		if (s.length() > 0) {
			String contenido = textPane.getText();
			int index = contenido.indexOf(s, 0);
			if (index >= 0) {
				try {
					int end = index + s.length();
					hilit.addHighlight(index, end, painter);
					textPane.setCaretPosition(end);
					txtbuscar.setBackground(colorfondodefault);
					estado.setText("'" + s + "' fue encontrado. Presione ESC para finalizar la busqueda.");
				} catch (BadLocationException e) {
					e.printStackTrace();
				}
			} else {
				txtbuscar.setBackground(Color.ORANGE);
				estado.setText("'" + s + "' no se ha encontrado. Presione ESC para finalizar la busqueda.");
			}
		}else{
			estado.setText("Digite la palabra a buscar.");
		}
	}

	private void remplazarTexto(){
		String busca = txtbuscar.getText();
		String remplazo = txtremplazar.getText();
		String contenido = textPane.getText();
		int index = contenido.indexOf(busca, 0);
		if(index >= 0){
			textPane.setText(contenido.replaceFirst(busca, remplazo));
			lblEscribeLaPalabra.setText("'Se ha sustituido '" +busca  + "' por '"+remplazo+"'. Presione ESC para finalizar la busqueda.");


		}
	}


}