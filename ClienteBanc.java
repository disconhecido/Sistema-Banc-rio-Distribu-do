package br.com.diego.banco;

import static java.lang.System.out;

import java.math.BigDecimal;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.NumberFormat;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class ClienteBanc {

	static Scanner leia = new Scanner(System.in, "latin1");
	private static int nDestaAgencia;
	private static int nContaAtual;
	private static String nomeTitular = null;
	private static Random random;
	private static BancoDAO banco;

	///CONFIGURAÇÃO:
	/// COLOCAR ENDEREÇOS DOS SERVIDORES
	static String ipServer[] = { "192.168.15.10", "0.0.0.0" };//"0.0.0.0" É LOCAL




  /**
   *  Função main responsável por inicializar o balanceamento entre os servidores.
   *  @param args Argumentos passáveis ao programa.
   */
	public static void main(String[] args) {

		int i; //índice do vetor de servidores
		int nTentativas= 0; ///número de tentativas de conexão

		random = new Random();
		do {
			try {

				i = random.nextInt(ipServer.length);//SELECIONA ENTRE 2 SERVIDORES
				out.println(ipServer[i]);

				banco = (BancoDAO) Naming.lookup("//" + ipServer[i] + ":2019/BancoServ");
				out.println(banco.teste());
				break;

			} catch (RemoteException e) {
				if (e.getCause().getMessage().contains("Connection refused: connect"))
					out.println("\n\nServidor fora do ar, tentando outro...\n\n");

			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			nTentativas++;
			if (nTentativas > 9) ///tenta conectar 10 vezes
			{
				out.println("\n\nCOMPORTAMENTO INESPERADO: Nenhum servidor diponível no momento!\n"
						+ "Tente novamente mais tarde.\n\n");
				System.exit(1); /// 
			}

		} while (true);

		// o número desta agência será usado nas movimentações bancárias a partir desta
		// máquina
		out.print("\n\nENTRE COM O NÚMERO DESTA AGENCIA: ");
		nDestaAgencia = leiaInt(999999);

		opInicial();

	}

	/**
	 * Recebe um número inteiro >=0 até o valor máximo
	 * @param maximo: valor máximo que pode ser recebido
	 * @return número recebido
	 */
	static int leiaInt(int maximo) {
		int i;
		do {
			try {
				i = leia.nextInt();
				leia.nextLine();
				if (i >= 0 && i <= maximo)
					return i;
				out.print("NÚMERO INVÁLIDO! Tente novamente: ");
			} catch (Exception e) {
				out.print("DIGITE SOMENTE NÚMEROS: ");
				leia.nextLine();
			}
		} while (true);
	}

  /**
   *  Lê do terminal um double do terminal
   *  @return numero lido.
   */
	static double leiaDouble() {
		double d;
		do {
			try {
				d = leia.nextDouble();
				leia.nextLine();
				if (d < 0) {
					out.print("O VALOR DEVE SER MAIOR OU IGUAL A ZERO: ");
					continue;
				}
				return d;
			} catch (Exception e) {
				out.print("DIGITE SOMENTE NÚMEROS: ");
				leia.nextLine();
			}
		} while (true);
	}

  /**
   *  Lê do terminal uma String do terminal
   *  @return nome lido.
   */
	static String leiaNome() {
		do {
			String temp = leia.nextLine();
			if (!temp.isEmpty())
				return temp;
			out.print("Digite: ");
		} while (true);
	}

  /**
   *  Apresenta as opções disponíveis no menu.
   *  @return opção selecionada.
   */
	static int menuInicial() {
		out.println("\n\n\n\n          SISTEMA BANCÁRIO\n");
		out.println("\nAgência: " + nDestaAgencia);
		out.println("OPÇÕES:");
		out.println("       0 - ENCERRAR PROGRAMA");
		out.println("       1 - Criar NOVA conta");
		out.println("       2 - ACESSAR uma conta");
		out.print("\nEntre com o número da opção: ");
		return leiaInt(2);
	}

  /**
   *  Resume a execução do programa, com execução do menu, saída, criação e acesso de contas.
   */
	static void opInicial() {
		while (true) {
			int i = menuInicial();
			switch (i) {
			case 0:
				out.println("\n\n\n\n     SISTEMA BANCÁRIO ENCERRADO!\n\n\n\n");
				System.exit(0);
			case 1:
				novaConta();
				break;
			case 2:
				acessaConta();
				break;
			}
		}
	}

  /**
   *  Rotina de criação de conta.
   */
	private static void novaConta() {
		do {
			out.println("\n\n\n     CADASTRAMENTO DE NOVA CONTA");
			out.println("\nAgência atual: " + nDestaAgencia);
			out.print("\nEntre com o nº da Agência de destino da conta ou 0 (zero) para sair: ");
			int ag = leiaInt(9999999);
			if (ag == 0)
				return;
			out.print("\nEntre com o nº da Conta ou 0 (zero) para sair: ");
			int n = leiaInt(99999999);
			if (n == 0)
				return;
			try {
				if (banco.seContaJaExiste(n)) {
					out.print("\nCONTA JÁ EXISTE! Tente novamente:");
					continue;
				}
			} catch (RemoteException e1) {
				out.print("\"COMPORTAMENTO INESPERADO:\nFalha no servidor!\"");
				return;
			}
			/// CADASTRO DOS DADOS DO CLIENTE
			out.print("\nEntre com o nome titular da conta ou 0 (zero) para sair: ");
			String titular = leiaNome();
			if (titular.equals("0"))
				return;
			out.print("\nEntre com o CPF do titular da conta ou 0 (zero) para sair: ");
			String cpf = leiaNome();
			if (cpf.equals("0"))
				return;
			out.print("\nEntre com o EMAIL do titular da conta ou 0 (zero) para sair: ");
			String email = leiaNome();
			if (email.equals("0"))
				return;
			out.print("\nEntre com o SENHA da conta ou 0 (zero) para sair: ");
			String senha = leiaNome();
			if (senha.equals("0"))
				return;

			out.print("\nENTRE COM O VALOR INCIAL DE ABERTURA DA CONTA ou 0 (zero) para sair: ");
			double valor = leiaDouble();
			if (valor == 0)
				return;

			try {
				if (banco.inserirClienteConta(n, ag, valor, cpf, titular, email, senha)) {
					out.println("\n\n\n**********CONTA CRIADA COM SUCESSO!**********");
					out.println("\nPressione a tecla ENTER para continuar...\n");
					leia.nextLine();
					return;
				}
			} catch (RemoteException e) {
				out.println("COMPORTAMENTO INESPERADO:\nFalha no servidor!");
				return;
			}
			out.println("COMPORTAMENTO INESPERADO\nCONTA NÃO CRIADA!\nOCORREU UM PROBLEMA AO TENTAR CRIAR NOVA CONTA!");
		} while (true);
	}

  /**
   *  Rotina de manipulação das opções da conta.
   *  @return numero da opção lido.
   */
	static int menuConta() {
		out.println("\n\n\n\n\n*************ACESSANDO A CONTA*************\n             >>MENU DA CONTA<< ");
		out.println("\n\nAgencia atual: " + nDestaAgencia + "  Conta: " + nContaAtual + "\nTitular: " + nomeTitular);
		out.println("\nOPÇÕES:");
		out.println("       0 - SAIR DA CONTA");
		out.println("       1 - Saldo");
		out.println("       2 - Extrato detalhado");
		out.println("       3 - Saque");
		out.println("       4 - Depósito");
		out.println("       5 - Transferência");
		out.print("\nEntre com o número da opção: ");

		return leiaInt(5);
	}

  /**
   *  Rotina de manipulação das opções da conta.
   */
	static void opConta() {
		while (true) {
			int opcao = menuConta();
			switch (opcao) {
			case 0:
				return;
			case 1:
				saldo();
				break;
			case 2:
				extrato();
				break;
			case 3:
				saque();
				break;
			case 4:
				deposito();
				break;
			case 5:
				transferencia();
				break;
			}
		}
	}

  /**
   *  Rotina de transferência de valores
   */
	private static void transferencia() {
		String processamento = null;
		int conta1;
		int ag1;
		out.println("\n\n\n\n     TRANSFERÊNCIA ENTRE CONTAS");
		out.println("\nAgência atual: " + nDestaAgencia);

		out.println("\nDigite a agência da conta de destino ou 0 (zero) para sair");
		ag1 = leiaInt(9999999);
		if (ag1 == 0)
			return;
		do {
			out.println("\nDigite o nº da conta de destino ou 0 (zero) para sair");
			conta1 = leiaInt(99999999);
			if (conta1 == 0)
				return;
			try {
				if (!banco.seContaJaExiste(conta1)) {
					out.print("\nCONTA NÃO EXISTE! Tente novamente:");

				} else {
					break;
				}
			} catch (RemoteException e1) {
				out.print("Comportamento inesperado:\n falha no servidor\"");
				return;
			}
		} while (true);

		out.print("\nENTRE COM O VALOR A TRANSFERIR ou 0 (zero) para sair: ");
		double valor = leiaDouble();
		if (valor == 0)
			return;

		out.print("\nEntre com o SENHA da conta ou 0 (zero) para sair: ");
		String senha = leiaNome();
		if (senha.equals("0"))
			return;
		try {

			processamento = banco.transferencia(nContaAtual, conta1, nDestaAgencia, ag1, new BigDecimal(valor), senha);
		} catch (RemoteException e) {
			out.println(
					"\n\n>>COMPORTAMENTO INESPERADO! - Falha no banco de dados!\nPressione a tecla ENTER para continuar...");
			leia.nextLine();
			return;
		}
		if (processamento.charAt(0) == 'F') {// transferência não pôde ser realizada
			out.println("\n\n\n\n___________________________________\n");
			out.println("\n**********" + processamento + "**********");
			out.println("\nPressione a tecla ENTER para continuar...\n");
			leia.nextLine();
			return;
		}
		NumberFormat nf = NumberFormat.getCurrencyInstance();
		out.println("\n**********" + processamento + "**********\n");
		out.println("\n\nImprimindo comprovante...");
		out.println("\n___________________________________\n");
		out.println("\n\nAgencia atual: " + nDestaAgencia + "  Conta: " + nContaAtual + "\nTitular: " + nomeTitular);
		out.println("\n\n\n\n    TRANSFERÊNCIA: " + nf.format(valor));
		out.println("\n\n\n\n___________________________________\n");
		out.println("\n**********OPERAÇÃO REALIZADA COM SUCESSO!**********");
		out.println("\nPressione a tecla ENTER para continuar...\n");
		leia.nextLine();

	}

  /**
   *  Rotina de depósito
   */
	private static void deposito() {
		String processamento = null;

		out.println("\n\n\n\n     DEPÓSITO EM CONTA");
		out.println("\nAgência atual: " + nDestaAgencia);
		out.print("\nENTRE COM O VALOR A DEPOSITAR ou 0 (zero) para sair: ");
		double valor = leiaDouble();
		if (valor == 0)
			return;

		try {
			processamento = banco.deposito(nContaAtual, nDestaAgencia, new BigDecimal(valor));
		} catch (RemoteException e) {
			out.println(
					"\n\n>>COMPORTAMENTO INESPERADO! - Falha no banco de dados!\nPressione a tecla ENTER para continuar...");
			leia.nextLine();
			return;
		}

		if (processamento.charAt(0) == 'F') {// depósito não pôde ser realizado
			out.println("\n\n\n\n___________________________________\n");
			out.println("\n**********" + processamento + "**********");
			out.println("\nPressione a tecla ENTER para continuar...\n");
			leia.nextLine();
			return;
		}
		NumberFormat nf = NumberFormat.getCurrencyInstance();
		out.println("\n**********" + processamento + "**********\n");
		out.println("\n\nImprimindo comprovante...");
		out.println("\n___________________________________\n");
		out.println("\n\nAgencia atual: " + nDestaAgencia + "  Conta: " + nContaAtual + "\nTitular: " + nomeTitular);
		out.println("\n\n\n\n    DEPÓSITO: " + nf.format(valor));
		out.println("\n\n\n\n___________________________________\n");
		out.println("\n**********OPERAÇÃO REALIZADA COM SUCESSO!**********");
		out.println("\nPressione a tecla ENTER para continuar...\n");
		leia.nextLine();

	}

  /**
   *  Rotina de saque
   */
	private static void saque() {
		String processamento = null;

		out.println("\n\n\n\n     SAQUE EM CONTA");
		out.println("\nAgência atual: " + nDestaAgencia);
		out.print("\nENTRE COM O VALOR A SACAR ou 0 (zero) para sair: ");
		double valor = leiaDouble();
		if (valor == 0)
			return;
		out.print("\nEntre com o SENHA da conta ou 0 (zero) para sair: ");
		String senha = leiaNome();
		if (senha.equals("0"))
			return;

		try {
			processamento = banco.saque(nContaAtual, nDestaAgencia, new BigDecimal(valor), senha);
		} catch (RemoteException e) {
			out.println(
					"\n\n>>COMPORTAMENTO INESPERADO! - Falha no banco de dados!\nPressione a tecla ENTER para continuar...");
			leia.nextLine();
			return;
		}

		if (processamento.charAt(0) == 'F') {// saque não pôde ser realizado
			out.println("\n\n\n\n___________________________________\n");
			out.println("\n**********" + processamento + "**********");
			out.println("\nPressione a tecla ENTER para continuar...\n");
			leia.nextLine();
			return;
		}
		NumberFormat nf = NumberFormat.getCurrencyInstance();
		out.println("\n**********" + processamento + "**********\n");
		out.println("\n\nImprimindo comprovante...");
		out.println("\n___________________________________\n");
		out.println("\n\nAgencia atual: " + nDestaAgencia + "  Conta: " + nContaAtual + "\nTitular: " + nomeTitular);
		out.println("\n\n\n\n    SAQUE: " + nf.format(valor));
		out.println("\n\n\n\n___________________________________\n");
		out.println("\n**********OPERAÇÃO REALIZADA COM SUCESSO!**********");
		out.println("\nPressione a tecla ENTER para continuar...\n");
		leia.nextLine();

	}

  /**
   *  Rotina de extrato bancário.
   */
	private static void extrato() {
		NumberFormat nf = NumberFormat.getCurrencyInstance();
		out.println("\n\n\n\n     EXTRATO DETALHADO");
		List<String> movimento= null;
		try {
			movimento= banco.extrato(nContaAtual);
		} catch (RemoteException e) {
			out.println(
					"\n\n>>COMPORTAMENTO INESPERADO! - Falha no banco de dados!\nPressione a tecla ENTER para continuar...");
			leia.nextLine();
		}
		if (movimento != null) {
			out.println("\n\nImprimindo extrato detalhado...");
			out.println("\n_______________________________________________________\n                   EXTRATO DETALHADO");
			out.println("\nAgencia atual: " + nDestaAgencia + "  Conta: " + nContaAtual + "\nTitular: " + nomeTitular);
			for (int i=0; i< movimento.size(); i++)
				System.out.println(movimento.get(i));
			
			try {
				out.println("\n    Saldo atual: " + nf.format(banco.saldo(nContaAtual).doubleValue()));
			} catch (RemoteException e) {
				out.println(
						"\n\n>>COMPORTAMENTO INESPERADO! - Falha no banco de dados!\nPressione a tecla ENTER para continuar...");
				leia.nextLine();
				return;

			}
			out.println("\n\n_______________________________________________________\n");
			out.println("\n**********OPERAÇÃO REALIZADA COM SUCESSO!**********");
			out.println("\nPressione a tecla ENTER para continuar...\n");
			leia.nextLine();
			
		}else {
			out.println("\nEXTRATO INDISPONÍVEL OU INEXISTENTE");
			out.println("\nPressione a tecla ENTER para continuar...\n");
			leia.nextLine();
		}

	}


  /**
   *  Imprime o saldo da conta atual, cujo número está em nContaAtual
   *  @return numero lido.
   */
	private static void saldo() {
		NumberFormat nf = NumberFormat.getCurrencyInstance();
		out.println("\n\nImprimindo comprovante...");
		out.println("\n___________________________________\n");
		try {
			out.println(
					"\n\nAgencia atual: " + nDestaAgencia + "  Conta: " + nContaAtual + "\nTitular: " + nomeTitular);
			out.println("\n\n\n\n    Saldo atual: " + nf.format(banco.saldo(nContaAtual).doubleValue()));
		} catch (RemoteException e) {
			out.println(
					"\n\n>>COMPORTAMENTO INESPERADO! - Falha no banco de dados!\nPressione a tecla ENTER para continuar...");
			leia.nextLine();
			return;

		}
		out.println("\n\n\n\n___________________________________\n");
		out.println("\n**********OPERAÇÃO REALIZADA COM SUCESSO!**********");
		out.println("\nPressione a tecla ENTER para continuar...\n");
		leia.nextLine();

	}

  /**
   *  Rotina de acesso a uma conta
   */
	private static void acessaConta() {
		while (true) {
			out.println("\n    ACESSAR CONTA");
			out.println("\nAgência atual: " + nDestaAgencia);

			out.print("\nEntre com o nº da Conta ou 0 (zero) para sair: ");
			int id_conta = leiaInt(99999999);
			if (id_conta == 0)
				return;

			out.print("\nEntre com senha dessa conta ou 0 (zero) para sair: ");
			String senha = leia.nextLine();
			if (senha.equals("0"))
				return;

			Boolean acesso = null;
			try {
				acesso = banco.acessaConta(id_conta, senha);
			} catch (RemoteException e) {
				out.println(
						"\nCOMPORTAMENTO INESPERADO! - Falha no banco de dados!\nPressione a tecla ENTER para continuar...");
				leia.nextLine();
				return;
			}
			if (acesso == false) {
				out.println("\nCONTA OU SENHA INVÁLIDA!\nPressione a tecla ENTER para continuar...");
				leia.nextLine();
				continue;
			}

			try {
				nomeTitular = banco.getNomeCliente(id_conta);
			} catch (RemoteException e) {
				out.println(
						"\nCOMPORTAMENTO INESPERADO! - Falha no banco de dados!\nPressione a tecla ENTER para continuar...");
				leia.nextLine();
				return;
			}
			nContaAtual = id_conta;

			opConta();
		}
	}

}
