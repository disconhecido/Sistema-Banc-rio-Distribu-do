package br.com.diego.banco;

public interface ClienteDAO {

  /** 
   *  Insere determinado cliente.
   *  @param cliente Objeto do tipo Cliente à ser inserido.
   *  @return true, caso tenha sucesso; false caso contrário.
   *  @see  Cliente
   */
	public boolean inserir (Cliente cliente);

  /** 
   *  Exclui determinado cliente.
   *  @param cliente Objeto do tipo Cliente à ser excluído.
   *  @return true, caso tenha sucesso; false caso contrário.
   *  @see  Cliente
   */
	public boolean excluir (Cliente cliente);
	
}
