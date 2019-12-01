package br.com.diego.banco;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ClienteDAOImplementacao implements ClienteDAO{
	
	//String url = "jdbc:postgresql://172.16.5.130/banco?user=postgres&password=diego";
	String url = "jdbc:postgresql://localhost/Banco?user=postgres&password=postgres";

	public boolean inserir(Cliente cliente) {
		PreparedStatement ps = null;
		int rs;
		
		Connection conexaoBanco = null;
		try {
			

			conexaoBanco = DriverManager.getConnection(url);
			ps = conexaoBanco.prepareStatement("insert into clientes (cpf, email, nome) values (?,?,?)");
			ps.setString(1, cliente.getCpf());
			ps.setString(2, cliente.getEmail());
			ps.setString(3, cliente.getNome());
			
			rs = ps.executeUpdate();

			if (rs > 0) {

				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			if (conexaoBanco != null) {
				try {
					conexaoBanco.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public boolean excluir(Cliente cliente) {
		PreparedStatement ps = null;
		Connection conexaoBanco = null;
		try {


			conexaoBanco = DriverManager.getConnection(url);
			ps = conexaoBanco.prepareStatement("delete from clientes where cpf =?");
			ps.setString(1, cliente.getCpf());

			if (ps.executeUpdate() > 0)
				return true;
			else
				return false;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			if (conexaoBanco != null) {
				try {
					conexaoBanco.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
