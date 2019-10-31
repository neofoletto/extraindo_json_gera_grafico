/**
                    GNU GENERAL PUBLIC LICENSE
                       Version 3, 29 June 2007

 Copyright (C) 2007 Free Software Foundation, Inc. <https://fsf.org/>
 Everyone is permitted to copy and distribute verbatim copies
 of this license document, but changing it is not allowed.
 */
package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import persistencia.Gravacao;
import persistencia.Persistencia;
import persistencia.PersistenciaCSV;
import persistencia.PersistenciaHTML;
import persistencia.PersistenciaJSON;
import persistencia.PersistenciaXML;
import util.DataUtil;

public class ListaPessoa {

	private Persistencia pers = null;
	private List<Pessoa> listaPessoa;

	public ListaPessoa(String extensaoArquivo) {
//		this.listaPessoa = new ArrayList<Pessoa>();
		switch (extensaoArquivo.toLowerCase()) {
		case "csv":
			pers = new Persistencia(new PersistenciaCSV());
			break;
		case "xml":
			pers = new Persistencia((Gravacao) new PersistenciaXML());
			break;
		case "html":
			pers = new Persistencia((Gravacao) new PersistenciaHTML());
			break;
		case "json":
			pers = new Persistencia((Gravacao) new PersistenciaJSON());
			break;
		}
	}

	public void gravar(String arquivoNome) throws IOException {
		pers.gravar(this.listaPessoa, arquivoNome);
	}
	
	public void gravarLista(List<Pessoa> listaPessoa, String arquivoNome) throws IOException {
		pers.gravar(listaPessoa, arquivoNome);
	}
	
	public boolean inserirPessoa(Pessoa pessoa, String arquivoNome) throws IOException {
		if (!pessoa.equals(null)) {
			pessoa.setCodigo(returnCodigoListaPessoa());
			this.listaPessoa.add(pessoa);
			pers.gravar(this.listaPessoa, arquivoNome);
			return true;
		}
		return false;
	}
	
	public boolean somaListasDePessoa(List<Pessoa> listaPessoa, String arquivoNome) throws IOException {
		if (!listaPessoa.isEmpty()) {
			if (this.listaPessoa != null)
				this.listaPessoa.addAll(listaPessoa);
			else
				this.listaPessoa = listaPessoa;
			pers.gravar(this.listaPessoa, arquivoNome);
			return true;
		}
		return false;
	}

	public void inserirPessoaLista(String fileName) {
		this.listaPessoa = pers.ler(fileName);
		pers.gravar(this.listaPessoa, fileName);
	}

	public boolean removerPessoaLista(String nome, String arquivoNome) throws IOException {
		int index = retornaIndexDaPessoaNaLista(nome);
		if (index != -1)
			this.listaPessoa.remove(index);
		else 
			return false;
		pers.gravar(listaPessoa, arquivoNome);
		return true;
	}

	public List<Pessoa> retornaListaPessoa() {
		return listaPessoa;
	}

	public Integer retornaTamanhoDaListaPessoa() {
		return this.listaPessoa.size();
	}
	
	public Pessoa retornaPessoa(int index) {
		for (Pessoa pessoa : this.listaPessoa) {
			if (pessoa.getCodigo() == index)
				return listaPessoa.get(index);
		}
		return null;
	}

	public String retornaListaCompleta() {
		StringBuilder builder = new StringBuilder();
		ListIterator<Pessoa> pessoa = this.listaPessoa.listIterator();
		while (pessoa.hasNext()) {
			Object element = pessoa.next();
			builder.append(element).append("\n");
		}
		return builder.toString();
	}
	
	public String retornaListaCompletaDeDominio() {
		StringBuilder builder = new StringBuilder("\n");
		List<String> texto = new ArrayList<String>();
		boolean aux = true;
		for (Pessoa pessoa : this.listaPessoa) 
			texto.add(pessoa.getEmail().split("@")[1]);
		for (int i = 0; i < texto.size(); i++) {
			for (int j = 0; j < i; j++) {
				if (texto.get(i) == texto.get(j) && i != j) {
					aux = false;
					break;
				}
			}
			if (aux) {
				builder.append(texto.get(i)).append("\n");	
				aux = true;
			}
		}
		
		return builder.toString();
	}
	
	public String retornaListaCompletaAniversarioDoMes(int mes) {
		StringBuilder builder = new StringBuilder("\n");
		for (Pessoa pessoa : this.listaPessoa) 
			if (DataUtil.MesEmInteiro(pessoa.getDataNascimento()) == mes)
				builder.append(pessoa).append("\n");
		
		return (!builder.toString().isEmpty()) ? builder.toString() : "Não há aniversariantes neste mês.";
	}

	private int returnCodigoListaPessoa() {
		if (this.listaPessoa != null)
			return this.listaPessoa.get(listaPessoa.size() - 1).getCodigo() + 1;
		return 0;
	}

	public Integer retornaIndexDaPessoaNaLista(String nome) {
		for (int i = 0; i < this.listaPessoa.size(); i++) 
			if (this.listaPessoa.get(i).getNome().equals(nome))
				return i;
		return -1;
	}
	
	public boolean retornaCodigoExisteNaListaPessoa(int index) {
		for (int i = 0; i < this.listaPessoa.size(); i++) 
			if (this.listaPessoa.get(i).getCodigo() == index)
				return true;
		return false;
	}

	public String retornaStringListDominios(String dominio, String erroReturn) {
		dominio = dominio.replace("@", "");
		StringBuilder builder = new StringBuilder();		
		for (Pessoa pessoa : this.listaPessoa)
			if (pessoa.getEmail().split("@")[1].equals(dominio))
				builder.append(pessoa).append("\n");
		return (!builder.toString().isEmpty()) ? builder.toString() : erroReturn;
	}
	
	public Map<String, Integer> retornaHashMapDominios() {
		Map<String, Integer> dominio = new HashMap<String, Integer>();
		for (Pessoa pessoa : this.listaPessoa) {
			if (dominio.containsKey(pessoa.getEmail().split("@")[1])) { // Palavras esta no mapa
				int contador = dominio.get(pessoa.getEmail().split("@")[1]); // Obtem contagem atual
				dominio.put(pessoa.getEmail().split("@")[1], contador + 1); // Incrementa a contagem
			} else
				dominio.put(pessoa.getEmail().split("@")[1], 1); // Adiciona uma nova palavra com contagem de 1 ao mapa
		}
		return dominio;
	}
	
	public String buscaPorNome(String nome, String erroReturn) {
		for (Pessoa pessoa : this.listaPessoa)
			if (pessoa.getNome().equals(nome))
				return pessoa.toString();
		return erroReturn;
	}
	
	public boolean alterarPessoa(int opcao, int index, String texto) {
		for (int i = 0; i < this.listaPessoa.size(); i++) {
			if (this.listaPessoa.get(i).getCodigo() == index)
				switch (opcao) {
				case 1:
					this.listaPessoa.get(i).setNome(texto);
					return true;
					
				case 2:
					this.listaPessoa.get(i).setEmail(texto);
					return true;
					
				case 3:
					this.listaPessoa.get(i).setDataNascimento(texto);
					return true;
					
				case 4:
					this.listaPessoa.get(i).setTelefone(texto);
					return true;

				default:
					return false;
				}
		}
		return false;
	}
	
}
