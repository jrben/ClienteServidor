package ClienteServidorM;
import java.io.*;
import java.net.*;
import java.applet.*;
import java.awt.*;
import java.awt.event.*; 
public class Cliente2A extends Applet implements ActionListener{
   	Socket yo = null;
   	PrintWriter alServidor;
   	DataInputStream delServidor;
   	String tecleado;
   	String host;
// 	Definición de los objetos de la interfaz gráfica  
   	TextField sale = new TextField(20);// Donde se tecleará el string
   	TextField llega = new TextField(4);// Donde se escribirá la longitud del string
   	Button envia = new Button("Conectándose");
   
   	public void init(){
// Deshabilitar el botón hasta que se haga la conexión
   		envia.setEnabled(false);
//      Agregar los objetos, con un FlowLayout, el cual es el default para applets
   		add(envia);
   		add(sale);
   		add(new Label("Longitud del mensaje: "));
   		add(llega);
//      Registrar el escuchador para atender el evento
   		envia.addActionListener(this);
   		try {
//      Obtener la dirección IP del servidor en forma de string
//      getCodeBase() regresa un objeto URL al cual le puedes aplicar el método
//      getHost() que regresa el nombre de dominio del URL
   			host = getCodeBase().getHost();
//      abrir la conexión con el servidor en el puerto 5000
   			yo = new Socket(host,5000);
//      Establecer todos los flujos necesarios:
//      Para enviar al servidor el string
   			alServidor = new PrintWriter(yo.getOutputStream(),true);
//      Para leer lo que me manda el servidor, será un entero
   			delServidor = new DataInputStream(yo.getInputStream());
//      Cambiar la etiqueta del botón para indicar que ya está la conexión
   			envia.setLabel("Envia");
//      y habilitarlo
   			envia.setEnabled(true);
   		}catch (IOException e){
   			sale.setText(e.getMessage());
   			envia.setLabel("No pudo");
   		} 
   	} 

//       Cuando se presione el botón mandar el string que contiene el textfield sale   
   	public void actionPerformed(ActionEvent e){
   		mandaMensaje(sale.getText());
   	}
 	public void stop(){
//       Cuando se pare el applet cerrar la conexión, cuando se vuelve a la página
//       no se abre la conexión, es parte del ejercicio que tienes que hacer
   		try{
   			delServidor.close();
   			alServidor.close();
   			yo.close();
   		}catch (IOException e){
   			sale.setText(e.getMessage());
   			envia.setLabel("Desconectado");
   			envia.setEnabled(false);
   		} 
   	}

//     El método que se invoca cuando hay que mandar el string al servidor,
//     recibe como parámetro el string a mandar   
   	public void mandaMensaje(String mensaje){
   		try{
//     Mandar el string al servidor
   			alServidor.println(mensaje);
//     Esperar a que el servidor conteste con un entero
   			int l = delServidor.readInt();
//     Escribir lo recibido en el segundo textfield
//     se tiene que transformar a string porque el textfield sólo acepta strings
   			llega.setText(String.valueOf(l)); 
//     Si la longitud es cero "desconectarse"
   			if (l==0){
   				envia.setLabel("Desconectado");
//     realmente no se desconecta, si no que inhibe el botón para no poder enviar
   				envia.setEnabled(false);
//     Como al cambiar la etiqueta del botón, cambia su tamaño hay que hacer que el
//     LayoutManager redibuje todo el applet otra vez con los nuevos tamaños
   				doLayout();
   			}
   		}catch (IOException e){
   			sale.setText(e.getMessage());
   			envia.setLabel("Desconectado");
   			envia.setEnabled(false);
   		}
   	}
}