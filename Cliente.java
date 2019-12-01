package br.com.diego.banco;

public class Cliente {
	private String nome;
	private String cpf;
	private String email;
	
  /**
   *  Método construtor
   *  @param nome Nome do objeto cliente à ser instanciado.
   *  @param cpf  Cadastro de Pessoa Física do objeto à ser instanciado.
   */
	public Cliente(String nome, String cpf) {
		this.nome = nome;
		this.cpf = cpf;
	}

  /**
   *  Método construtor
   *  @param nome Nome do objeto cliente à ser instanciado.
   *  @param cpf  Cadastro de Pessoa Física do objeto à ser instanciado.
   *  @param email Email do cliente.
   */
	public Cliente(String nome, String cpf, String email) {
		this.nome = nome;
		this.cpf = cpf;
		this.email = email;
	}

  /**
   *  Retorna o nome do cliente.
   *  @return nome do cliente.
   */
	public String getNome() {
		return nome;
	}
  
  /**
   *  Altera o nome do cliente.
   *  @param nome Novo nome do cliente.
   */
	public void setNome(String nome) {
		this.nome = nome;
	}

  /**
   *  Retorna o CPF do cliente.
   *  @return CPF do cliente.
   */
	public String getCpf() {
		return cpf;
	}

  /**
   *  Altera o CPF do cliente.
   *  @param cpf CPF do cliente.
   */
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

  /**
   *  Retorna o Email do cliente.
   *  @return Email do cliente.
   */
	public String getEmail() {
		return email;
	}

  /**
   *  Altera o Email do cliente.
   *  @param email Email do cliente.
   */
	public void setEmail(String email) {
		this.email = email;
	}

  /**
   *  'Converte' o objeto em uma String.
   *  @return Representação textual do objeto.
   */
	public String toString() {
		StringBuilder strRetorno = new StringBuilder();
		strRetorno.append("-------- ");
		strRetorno.append("\nCliente: ");
		strRetorno.append("\nNome: "+getNome());
		strRetorno.append("\nCPF:"+getCpf());
		strRetorno.append("\nEmail: "+getEmail());
		strRetorno.append("\n-------- ");
		
		return strRetorno.toString(); 
	}

}
