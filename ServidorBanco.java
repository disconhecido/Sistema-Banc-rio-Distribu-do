package br.com.diego.banco;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;



public class ServidorBanco {
	
	///ENDEREÇO DO SERVIDOR
	static String ipServer = "localhost"; // basta deixar localhost mesmo


	public static void main(String[] args) {
		BancoDAO banco;

		try {
			// Cria o Registry
			LocateRegistry.createRegistry(2019);
			banco = new BancoDAOImplementacao();
			
			Naming.rebind("//"+ipServer+":2019/BancoServ", banco);
			System.out.println("Servidor do banco em execução com Java RMI");
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
