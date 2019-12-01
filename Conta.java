package br.com.diego.banco;

import java.math.BigDecimal;


public class Conta {

	private Integer idConta;
	private String cpf;
	private BigDecimal saldo;
	private String senha;
	private Integer agencia;

  /**
   *  Construtor da classe.
   *  @param id_conta Identificador da conta.
   *  @param ag Identificador da agência associada.
   *  @param cliente Objeto do tipo Cliente para ser associado à conta.
   *  @param saldo Saldo inicial associado à conta.
   *  @param senha Senha associada à conta.
   */
	public Conta(int id_conta, int ag, Cliente cliente, BigDecimal saldo, String senha) {
		this.agencia= ag;
		this.idConta= id_conta;
		this.cpf = cliente.getCpf();
		this.saldo = saldo;
		this.senha = senha;
	}

  /**
   *  Construtor de classe.
   *  Cria a classe e configura apenas o saldo inicial (0 rs).
   */
	public Conta(){
		this.saldo = new BigDecimal(0);
	}

  /**
   *  Obtem o identificador associado à conta.
   *  @return Identificador da conta.
   */
	public Integer getIdConta() {
		return idConta;
	}

  /**
   *  Altera o identificador da conta.
   *  @param idConta Novo identificador da conta.
   */
	public void setIdConta(Integer idConta) {
		this.idConta = idConta;
	}

  /**
   *  Obtem o CPF associado à conta.
   *  @return CPF da conta.
   */
	public String getCpf() {
		return cpf;
	}

  /**
   *  Altera o CPF da conta.
   *  @param cpf Novo CPF da conta.
   */
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

  /**
   *  Obtem o saldo associado à conta.
   *  @return Saldo da conta.
   */
	public BigDecimal getSaldo() {
		return saldo;
	}

  
  /**
   *  Altera o saldo da conta.
   *  @param saldo Novo saldo da conta.
   */
	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

  /**
   *  Obtem a senha associada à conta.
   *  @return Senha da conta.
   */
	public String getSenha() {
		return senha;
	}

  /**
   *  Altera a senha da conta.
   *  @param senha Nova senha da conta.
   */
	public void setSenha(String senha) {
		this.senha = senha;
	}

  /**
   *  Obtem a agencia associada à conta.
   *  @return Agencia da conta.
   */
	public int getAgencia() {
		return agencia;
	}

  /**
   *  Altera a agencia da conta.
   *  @param agencia Nova agencia da conta.
   */
	public void setAgencia(Integer agencia) {
		this.agencia = agencia;
	}

  /**
   *  'Converte' o objeto em uma String.
   *  @return Representação textual do objeto.
   */
	public String toString() {
		StringBuilder strRetorno = new StringBuilder();
		strRetorno.append("-------- ");
		strRetorno.append("\nConta: ");
		strRetorno.append("\nId: " + getIdConta());
		strRetorno.append("\nCPF:" + getCpf());
		strRetorno.append("\nSaldo: " + getSaldo().doubleValue());
		strRetorno.append("\n-------- ");
		return strRetorno.toString();
	}

}
