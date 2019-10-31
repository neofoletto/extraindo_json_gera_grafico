/**
                    GNU GENERAL PUBLIC LICENSE
                       Version 3, 29 June 2007

 Copyright (C) 2007 Free Software Foundation, Inc. <https://fsf.org/>
 Everyone is permitted to copy and distribute verbatim copies
 of this license document, but changing it is not allowed.
 */
package main;

import java.io.IOException;
import java.util.Scanner;

import persistencia.PersistenciaGRAFICO;
import util.ExtratorURL;
import util.Utils;

/**
 * @author neo
 * 
 */
public class Main {
	public static void main(String[] args) throws IOException {
		Scanner imput = new Scanner(System.in);
		// csv, html, json, xml
		ListaPessoa listaPessoa;

		String fileName = "pop"; // nome do arquivo
//		String extName = "csv"; // extensao do arquivo
//		String extName = "xml"; // extensao do arquivo
		String extName = "json"; // extensao do arquivo

//		System.out.print("Insira nome do arquivo: ");
//		fileName = imput.nextLine();
//		System.out.print("Insira nome do arquivo: ");
//		extName = imput.nextLine();

		listaPessoa = new ListaPessoa(extName);
		listaPessoa.inserirPessoaLista(fileName);

		System.out.println("Opção inicial");
		while (true) {
			System.out.print("Deseja importar os dados através de um link?: ");
			if ('s' == imput.nextLine().toLowerCase().charAt(0)) {
				int cont = 0;
				String textoUrl;
				System.out.print("Informe link: ");
				textoUrl = imput.nextLine();
				while (!Utils.isValidUrlCurvelloSite(textoUrl) && ++cont < 3) 
					textoUrl = imput.nextLine();
		
				if (cont < 3 && Utils.isValidUrlCurvelloSite(textoUrl)) { 
					listaPessoa.somaListasDePessoa(new ExtratorURL().siteCurvello(textoUrl), fileName);
					break;
				}
			} else
				break;
		}
		
		while (true) {
			System.out.println("\n** Menu **");
			System.out.println("1- Incluir");
			System.out.println("2- Alterar");
			System.out.println("3- Excluir");
			System.out.println("4- Consulta por Nome");
			System.out.println("5- Consulta por Mês do aniversariante");
			System.out.println("6- Consulta por domínio de e-mail");
			System.out.println("7- Retorna lista completa");
			System.out.println("8- Gerar gráfico referente aos domínios");
			System.out.println("0- Sair");

			switch (validaImputInteger(imput)) {
			case 0:
				new ListaPessoa("csv").gravarLista(listaPessoa.retornaListaPessoa(), fileName);
				new ListaPessoa("xml").gravarLista(listaPessoa.retornaListaPessoa(), fileName);
				System.out.println("Programa finalizado");
				return;

			case 1: // Incluir
				Pessoa pessoa = new Pessoa();
				System.out.println("** Incluir Pessoa **");
				System.out.print("Informe nome: ");
				pessoa.setNome(imput.nextLine());
				System.out.print("Informe Data de Nascimento (dd/mm/aaaa): ");
				while (!pessoa.setDataNascimento(imput.nextLine()))
					;
				System.out.print("Informe E-mail: ");
				while (!pessoa.setEmail(imput.nextLine()))
					;
				System.out.print("Informe Telefone: ");
				while (!pessoa.setTelefone(imput.nextLine()))
					;

				if (listaPessoa.inserirPessoa(pessoa, fileName))
					System.out.println("Adicionado com sucesso!!");
				else
					System.out.println("Erro!!");
				break;

			case 2: // Alterar
				System.out.println("** Alterar Pessoa **");
				System.out.println(listaPessoa.retornaListaCompleta()); // retorna toda a lista
				System.out.print("Informe código da Pessoa a ser alterada: ");
				int index = validaImputInteger(imput);
				if (listaPessoa.retornaCodigoExisteNaListaPessoa(index)) {
					System.out.println("** Alterar **");
					System.out.println("1- Nome");
					System.out.println("2- E-mail");
					System.out.println("3- Data de Nascimento");
					System.out.println("4- Telefone");
					System.out.println("0- Sair");
					int opc = validaImputInteger(imput);

					if (opc > 0 && opc <= 4) {
						while (!listaPessoa.alterarPessoa(opc, index, imput.nextLine()))
							;
						listaPessoa.gravar(fileName);
					} else {
						System.out.println("Exit Altera módulo.");
						break;
					}
				}

				System.out.println("Código não encontrado.");
				break;

			case 3: // Excluir // finalizar
				imput.nextLine();
				System.out.println("** Excluir por Nome **");
				System.out.print("Informe nome: ");
				if (listaPessoa.removerPessoaLista(imput.nextLine(), fileName))
					System.out.println("Exclusão com sucesso");
				else
					System.out.println("Erro!!");

				break;

			case 4: // Consulta por Nome
				imput.nextLine();
				System.out.println("** Consulta por Nome **");
				System.out.print("Informe nome: ");
				System.out.println(listaPessoa.buscaPorNome(imput.nextLine(), "Não há pessoa com este nome cadastrado!"));
				break;

			case 5: // Consulta por Mês do aniversariante
				System.out.println("** Consulta por Mês do aniversariante **");
				System.out.print("Informe mês: ");
				int mes = validaImputInteger(imput);
				while (mes < 0 || mes > 12)
					mes = validaImputInteger(imput);
				System.out.println(listaPessoa.retornaListaCompletaAniversarioDoMes(mes));

				break;

			case 6: // Consulta por domínio de e-mail
				System.out.println("** Consulta por domínio de e-mail **");
				System.out.println(listaPessoa.retornaListaCompletaDeDominio());
				System.out.print("Informe domínio: ");
				String texto = imput.nextLine();
				while (!new Utils().isValidEmailAddressDomainRegex(texto))
					texto = imput.nextLine();
				System.out.println(listaPessoa.retornaStringListDominios(texto, "Não há domínios iguais a este na lista."));
				break;

			case 7: // Retorna lista completa
				System.out.println(listaPessoa.retornaListaCompleta());
				break;

			case 8: // Gerar gráfico referente aos domínios
				System.out.println("** Gerar gráfico referente aos domínios **");
				new PersistenciaGRAFICO().grava("PieChart", "PieChart", "PieChart", listaPessoa.retornaHashMapDominios());
				System.out.println("Gráfico gerado com sucesso.");
				break;

			default:
				System.out.println("Opção inválida.");
				break;
			}
		}
	}

	private static Integer validaImputInteger(Scanner imput) {
		String texto = imput.nextLine();
		while (!new Utils().isValidNumber(texto))
			texto = imput.nextLine();
		return Integer.parseInt(texto);
	}

}