package ClienteServidorM;

import java.io.*;
import java.net.*; 
public class ServidorM2{
   	public static void main(String[] args){
   		ServerSocket yo = null;
   		Socket cliente = null;
   		boolean escuchando = true;
 		try{
   			yo = new ServerSocket(5000);
   			System.out.println("Socket escuchando en puerto 5000");
 			while(escuchando){
   				cliente = yo.accept();
   				System.out.println("Un cliente conectado desde: " + 
									cliente.getInetAddress().getHostName()+ 
									"("+cliente.getPort()+")");
   				new Atiende(cliente).start();
   			}
	 		yo.close();
   		}catch (IOException e){
   			System.err.println(e.getMessage());
   			System.exit(1);
   		}
   	}
}
class Atiende extends Thread{
   	private BufferedReader entrada;
   	private DataOutputStream salida;
   	private String llego;
   	private Socket cliente = null;
   	private String nombreyDirIP;
 	public Atiende(Socket cliente){
   		this.cliente = cliente;
   		nombreyDirIP = this.cliente.getInetAddress().toString();
   	}
 	public void run(){
   		try{
   			Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
  			 entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
   			salida = new DataOutputStream(cliente.getOutputStream());
 			do{
   				llego = entrada.readLine();
   				System.out.println("("+nombreyDirIP+") Mensaje recibido: " + llego);
   				salida.writeInt(llego.length());
   			}while(llego.length()!=0);
 			entrada.close();
   			cliente.close();
   		}catch(IOException e){
   			System.out.println(e.getMessage());
   			System.exit(1);
   		}
   			System.out.println("Ya se desconecto"+"("+nombreyDirIP+")");
	}
}