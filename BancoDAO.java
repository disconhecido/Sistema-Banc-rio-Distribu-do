package br.com.diego.banco;

import java.math.BigDecimal;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;


public interface BancoDAO extends Remote{

  /** 
   *  Obtem acesso à uma conta.
   *
   *  @param id_conta Número de identificação da conta.
   *  @param senha  String que representa a senha associada à conta.
   *  @return true caso tenha conseguido o acesso, false caso contrário 
   */
	public Boolean acessaConta(Integer id_conta, String senha) throws RemoteException;



  /**
   *  Verifica se determinada conta existe.
   *
   *  @param id_conta Número de identificação da conta.
   *  @return true caso exista, false caso contrário.
   */
	public Boolean seContaJaExiste(Integer id_conta) throws RemoteException;


  /**
   *  Retorna o saldo de determinado titular.
   *
   *  @param cpf Número do Cadastro de Pessoa Física do titular em questão.
   *  @return Saldo atual da conta em questão.
   *  @see BigDecimal
   */
	public BigDecimal saldo(String cpf) throws RemoteException;


   /**
   *  Retorna o saldo de determinado conta, dado seu número de identificação.
   *
   *  @param id_conta Número identificador da conta.
   *  @return Saldo atual da conta em questão.
   *  @see BigDecimal
   */
	public BigDecimal saldo(Integer id_conta) throws RemoteException;

  /**
   *  Realiza a inserção de determinado cliente na tabela de contas.  Método de uso interno.
   *
   *  @param conta Objeto que representa a conta em questão.
   *  @param cliente Objeto do tipo Cliente, reponsável por armazenar suas informações
   *  @return true, caso tenha sucesso; false caso contrário.
   *  @see Conta
   *  @see Cliente
   */
	public boolean inserir (Conta conta, Cliente cliente) throws RemoteException;

  /**
   *  Exclui determinada conta. Método de uso interno.
   *
   *  @param conta Objeto que representa a conta em questão.
   *  @return true, caso tenha sucesso; false caso contrário.
   *  @see Conta
   */
	public boolean excluir (Conta conta) throws RemoteException;


  /**
   *  Realiza a operação de saque.
   *
   *  @param id_conta inteiro identificador da conta.
   *  @param ag inteiro identificador da agência.
   *  @param valor Valor que será debitado da conta.
   *  @param senha String de acesso à conta em questão.
   *  @return Informações de status sobre a operação
   *  @see BigDecimal
   */
	public String saque(int id_conta, int ag, BigDecimal valor, String senha) throws RemoteException;

  /**
   *  Realiza o depósito de certa quantia em determinada conta
   *
   *  @param id_conta inteiro identificador da conta.
   *  @param ag inteiro identificador da agência.
   *  @param valor Valor que será debitado da conta.
   *  @return Informações de status sobre a operação
   *  @see BigDecimal
   */
	public String deposito(int id_conta, int ag, BigDecimal valor) throws RemoteException;
  
  /**
   *  Cria e insere, associa um novo cliente e uma nova conta no banco de dados.
   *
   *  @param id_conta inteiro identificador da conta.
   *  @param ag inteiro identificador da agência.
   *  @param saldo Valor que representa o saldo inicial da conta.
   *  @param cpf Cadastro de Pessoa Física do titular.
   *  @param nome Nome do titular.
   *  @param email  Endereço de correio eletronico do titular
   *  @param senha Senha associada.
   *  @return true, caso sucesso; false caso contrário.
   */
	public Boolean inserirClienteConta(int id_conta, int ag, double saldo, String cpf, String nome, String email, String senha)throws RemoteException;

  /**
   *  Função auxiliar para tesde do servidor interno.
   *
   *  @return status do servidor, indicanto sucesso.
   */
	public String teste() throws RemoteException;

  /**
   *  Realiza uma busca do nome de determinado cliente associado à conta especificada.
   *
   *  @param id_conta Identificador associado à conta.
   *  @return Nome do cliente.
   */
	public String getNomeCliente(int id_conta) throws RemoteException;

  /**
   *  Realiza a transferência de valores entre contas.
   *
   *  @param id_conta Identificador da conta de orígem.
   *  @param id_conta1 Identificador da conta de destino.
   *  @param ag Identificador da agência de orígem.
   *  @param ag1 Identificador da agência de destino.
   *  @param valor Quantia que será transferida.
   *  @param senha Senha da conta originária.
   *  @return Informações de status sobre a operação
   */
	public String transferencia(int id_conta, int id_conta1, int ag, int ag1, BigDecimal valor, String senha) throws RemoteException;

  /**
   *  Obtem uma lista com o extrato bancário associado a uma conta
   *
   *  @param id_conta Identificador da conta
   *  @return Lista que compõe o estrato em questão.
   */
	public List<String> extrato(int id_conta) throws RemoteException;


}
