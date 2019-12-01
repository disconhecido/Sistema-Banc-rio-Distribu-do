package br.com.diego.banco;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class BancoDAOImplementacao extends UnicastRemoteObject implements BancoDAO {
	
	//CONFIGURAÇÃO
	///substituir localhost pelo ip do postgresql, colocar o banco, usuário e senha
	String url = "jdbc:postgresql://localhost/Banco?user=postgres&password=postgres";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2651396688488997524L;


	
	public String teste() throws RemoteException {
		
		return "sevidor OK";
	}
	


	
	public Boolean inserirClienteConta(int id_conta, int ag, double saldo, String cpf, String nome, String email, String senha)throws RemoteException {
		Conta novaConta= null;
		Cliente novoCliente= new Cliente(nome,cpf, email);
		ClienteDAOImplementacao c1 = new ClienteDAOImplementacao();
		if (c1.inserir(novoCliente)) {
			novaConta= new Conta(id_conta, ag, novoCliente, new BigDecimal(saldo), senha);
			try {
				BancoDAOImplementacao contaDAO = new BancoDAOImplementacao();
				if (contaDAO.inserir(novaConta, novoCliente)) return true;
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
		return false;
	}

	public String getNomeCliente(int id_conta) throws RemoteException {
		PreparedStatement ps = null;
		ResultSet rs;
		Connection conexaoBanco = null;
		try {

			conexaoBanco = DriverManager.getConnection(url);
			ps = conexaoBanco.prepareStatement("select cl.nome "
					+ "from Clientes cl INNER JOIN Contas ct ON cl.cpf = ct.cpf where ct.id_conta = ?");
			ps.setInt(1, id_conta);
			rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getString(1);
				
			} 
			return "Cliente não encontrado!";
			
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


/*
 * verifica se a conta já existe
 */
	public Boolean seContaJaExiste(Integer id_conta) throws RemoteException {
		PreparedStatement ps = null;
		ResultSet rs;
		Connection conexaoBanco = null;
		try {

			conexaoBanco = DriverManager.getConnection(url);
			ps = conexaoBanco.prepareStatement("select id_conta "
					+ "from Contas where id_conta = ?");
			ps.setInt(1, id_conta);
			rs = ps.executeQuery();

			if (rs.next()) {
				return true;
				
			} 
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


	protected BancoDAOImplementacao() throws RemoteException {
		super();
		
	}

	
	 /*
     * Verifica se pode acessar a conta
     * retorna true se id_conta e senha estiverem corretos
     */
	public Boolean acessaConta(Integer id_conta, String senha)  throws RemoteException{
		PreparedStatement ps = null;
		ResultSet rs;
		Connection conexaoBanco = null;
		try {

			conexaoBanco = DriverManager.getConnection(url);
			ps = conexaoBanco.prepareStatement("select id_conta "
					+ "from Contas where id_conta = ? AND senha = ?");
			ps.setInt(1, id_conta);
			ps.setString(2, senha);
			rs = ps.executeQuery();

			if (rs.next()) {
				return true;				
			} 
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

	/*
	 * retorna o saldo da conta do CPF especificado
	 * 
	 */
	public BigDecimal saldo(String cpf) throws RemoteException {
		PreparedStatement ps = null;
		ResultSet rs;
		Connection conexaoBanco = null;
		try {
			
			conexaoBanco = DriverManager.getConnection(url);
			ps = conexaoBanco.prepareStatement("select saldo from contas where cpf=?");
			ps.setString(1, cpf);
			rs = ps.executeQuery();

			if (rs.next()) {				
				return (rs.getBigDecimal("saldo"));
			} else {
				return null;
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

	public boolean inserir(Conta conta, Cliente cliente) throws RemoteException{
		PreparedStatement ps = null;
		int rs;
		Connection conexaoBanco = null;
		try {


			conexaoBanco = DriverManager.getConnection(url);
			ps = conexaoBanco.prepareStatement("insert into contas (id_conta, senha, cpf, saldo, agencia) values (?,?,?,?,?)");
			ps.setInt(1, conta.getIdConta());
			ps.setString(2, conta.getSenha());
			ps.setString(3, cliente.getCpf());
			ps.setBigDecimal(4, conta.getSaldo());
			ps.setInt(5, conta.getAgencia());
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

	
	/* 
	 * retorna o saldo da conta
	 * @param id : número da conta
	 */
	public BigDecimal saldo(Integer id) throws RemoteException {
		PreparedStatement ps = null;
		ResultSet rs;
		Connection conexaoBanco = null;
		try {
			
			conexaoBanco = DriverManager.getConnection(url);
			ps = conexaoBanco.prepareStatement("select saldo from contas where id_conta=?");
			ps.setInt(1, id);
			rs = ps.executeQuery();

			if (rs.next()) {
				return (rs.getBigDecimal("saldo"));
			} else {
				return null;
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

	public boolean excluir(Conta conta) throws RemoteException{
		PreparedStatement ps = null;
		Connection conexaoBanco = null;
		try {

			conexaoBanco = DriverManager.getConnection(url);
			ps = conexaoBanco.prepareStatement("delete from contas where id_conta =?");
			ps.setInt(1, conta.getIdConta());

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


	
	public List<String> extrato(int id_conta) throws RemoteException {
		PreparedStatement ps = null;
		ResultSet rs;
		Connection conexaoBanco = null;
		List<String>movimentos = new ArrayList<String>();
		NumberFormat nf = NumberFormat.getCurrencyInstance();
		try {
			
			conexaoBanco = DriverManager.getConnection(url);
			ps = conexaoBanco.prepareStatement("select * from movimento where id_conta=?");
			ps.setInt(1, id_conta);
			rs = ps.executeQuery();

			while(rs.next() == true) {
				
				String nova= ("\n"+rs.getString("descr")+"\n"+rs.getDate("data")+" "+"Valor: "+nf.format(rs.getDouble("valor"))
						+" Saldo anterior: "+rs.getDouble("saldo_ant")+"\n");
				movimentos.add(nova);				
			} 
			return movimentos;
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




	/*
	 * Realiza saque ATÔMICO
	 */
	public String saque(int id_conta, int ag, BigDecimal valor, String senha)  throws RemoteException{
		int result=-1;
		String sql;
		if (valor.compareTo(new BigDecimal(0)) == -1) return "ERRO: O valor deve ser positivo!";
		
		Statement s= null;
		Connection conexaoBanco = null;
			try {
				conexaoBanco = DriverManager.getConnection(url);

				conexaoBanco.setAutoCommit(false);
				s = conexaoBanco.createStatement();
				
				//insere o movimento registrando o saldo anterior a operação:
				sql= 	"INSERT INTO Movimento (valor, descr, id_conta, saldo_ant)"
						+ " VALUES ("+valor+", 'SAQUE na Ag.:"+ag+"', "+id_conta
						+", (select saldo from Contas where id_conta = "+id_conta+"));";

				result = s.executeUpdate(sql);
				if (result == 0) {//se nãoo conseguir
					conexaoBanco.rollback();//cancela tudo
					return "Falha na operação";
				}
				
				//Realiza a operação, atualizando o saldo:
				sql =  " UPDATE Contas SET saldo = saldo -"+valor
						+ "WHERE id_conta = "+id_conta+" AND saldo >= "+valor+"AND senha = '"+senha+"';";	
				result = s.executeUpdate(sql);
				
				///(ATOMICIDADE) realiza todas as operações de uma vez, ou cancela tudo:
				if (result == 0) {
					conexaoBanco.rollback();
					return "Falha na operação";
				}
				else {
					conexaoBanco.commit();
					return "Saque realizado com sucesso!";
				}
				
			} catch (SQLException e) {
				e.printStackTrace();		
				throw new RuntimeException(e);
			} finally {
				if (conexaoBanco != null) {
					try {
						conexaoBanco.close();
						s.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
			
			
			
	}
	
	/*
	 * Realiza depósito ATÔMICO
	 */
	public String deposito(int id_conta, int ag, BigDecimal valor) throws RemoteException {
		int result=-1;
		String sql;
		if (valor.compareTo(new BigDecimal(0)) == -1) return "ERRO: O valor deve ser positivo!";
		
		Statement s= null;
		Connection conexaoBanco = null;
			try {
				conexaoBanco = DriverManager.getConnection(url);

				conexaoBanco.setAutoCommit(false);
				s = conexaoBanco.createStatement();
				
				//insere o movimento registrando o saldo anterior a operação:
				sql= 	"INSERT INTO Movimento (valor, descr, id_conta, saldo_ant)"
						+ " VALUES ("+valor+", 'DEPÓSITO na Ag.: "+ag+"', "+id_conta
						+", (select saldo from Contas where id_conta = "+id_conta+"));";

				result = s.executeUpdate(sql);
				if (result == 0) {//se não conseguir
					conexaoBanco.rollback();//cancela tudo
					return "Falha na operação!";
				}
				
				//Realiza a operação, atualizando o saldo:
				sql =  " UPDATE Contas SET saldo = saldo +"+valor
						+ "WHERE id_conta = "+id_conta+";";	
				result = s.executeUpdate(sql);
				
				///(ATOMICIDADE) realiza todas as operações de uma vez, ou cancela tudo:
				if (result == 0) {
					conexaoBanco.rollback();
					return "Falha na operação";
				}
				else {
					conexaoBanco.commit();
					return "Depósito realizado com sucesso!";
				}
				
			} catch (SQLException e) {
				e.printStackTrace();		
				throw new RuntimeException(e);
			} finally {
				if (conexaoBanco != null) {
					try {
						conexaoBanco.close();
						s.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
	}
	
	
	public String transferencia(int id_conta, int id_conta1, int ag, int ag1, BigDecimal valor, String senha)
			throws RemoteException {
		int result=-1;
		String sql;
		if (valor.compareTo(new BigDecimal(0)) == -1) return "ERRO: O valor deve ser positivo!";
		
		Statement s= null;
		Connection conexaoBanco = null;
			try {
				conexaoBanco = DriverManager.getConnection(url);

				conexaoBanco.setAutoCommit(false);
				s = conexaoBanco.createStatement();
				
				//insere o movimento registrando o saldo anterior a operação:
				sql= 	"INSERT INTO Movimento (valor, descr, id_conta, saldo_ant)"
						+ " VALUES ("+valor+", 'Transf. para C.:"+id_conta1+" Ag.:"+ag1+"', "+id_conta
						+", (select saldo from Contas where id_conta = "+id_conta+"));";

				result = s.executeUpdate(sql);
				if (result == 0) {//se não conseguir
					conexaoBanco.rollback();//cancela tudo
					return "Falha na operação!";
				}
				
				sql= 	"INSERT INTO Movimento (valor, descr, id_conta, saldo_ant)"
						+ " VALUES ("+valor+", 'Transf. de C.:"+id_conta+" Ag.: "+ag+"', "+id_conta1
						+", (select saldo from Contas where id_conta = "+id_conta1+"));";

				result = s.executeUpdate(sql);
				if (result == 0) {//se não conseguir
					conexaoBanco.rollback();//cancela tudo
					return "Falha na operação!";
				}
				
				//Realiza a operação, atualizando o saldo:
				sql =  " UPDATE Contas SET saldo = saldo -"+valor
						+ "WHERE id_conta = "+id_conta+"AND saldo >= "+valor+ "AND senha = '"+senha+"';";	
				result = s.executeUpdate(sql);
				
				///(ATOMICIDADE) realiza todas as operações de uma vez, ou cancela tudo:
				if (result == 0) {
					conexaoBanco.rollback();
					return "Falha na operação";
				}
				sql = " UPDATE Contas SET saldo = saldo +"+valor+"WHERE id_conta = "+id_conta1+";";
				result = s.executeUpdate(sql);
				if(result == 0){
					conexaoBanco.rollback();
					return "Falha na operacao";
				}else {
					conexaoBanco.commit();
					return "Transferência realizada com sucesso!";
				}
				
			} catch (SQLException e) {
				e.printStackTrace();		
				throw new RuntimeException(e);
			} finally {
				if (conexaoBanco != null) {
					try {
						conexaoBanco.close();
						s.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
	}
	
	

}
	


