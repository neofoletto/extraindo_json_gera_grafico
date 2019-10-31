/**
                    GNU GENERAL PUBLIC LICENSE
                       Version 3, 29 June 2007

 Copyright (C) 2007 Free Software Foundation, Inc. <https://fsf.org/>
 Everyone is permitted to copy and distribute verbatim copies
 of this license document, but changing it is not allowed.
 */
package persistencia;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import main.Pessoa;
import util.DataUtil;

public class PersistenciaHTML implements Gravacao {

	private String setNome(String nome) {
		if (nome.length() != 0) {
			return nome + ".html";
		}
		return "none.html";
	}

	public boolean gravar(List<Pessoa> lista, String nameFile) {
		FileWriter arq = null;
		try {
			arq = new FileWriter(setNome(nameFile));
			PrintWriter gravarArq = new PrintWriter(arq);
			gravarArq.printf("%s\n", gerarHtml(lista));
			gravarArq.printf("%s\n", cabecalho());
			gravarArq.printf("%s\n", rodape());
			arq.close();
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;		
	}

	public StringBuilder gerarHtml(List<Pessoa> lista) throws IOException {
		StringBuilder input = new StringBuilder();
		for (Pessoa pessoa : lista) {
			input.append("<tr><td>");
			input.append("Codigo: ");
			input.append(pessoa.getCodigo());
			input.append("<br />Nome: ");
			input.append(pessoa.getNome());
			input.append("<br />Data Nascimento: ");
			input.append(DataUtil.DataHoraForStringPadrao(pessoa.getDataNascimento()));
			input.append("<br />E-Mail: ");
			input.append(pessoa.getEmail());
			input.append("<br />Telefone: ");
			input.append(pessoa.getTelefone());
			input.append("</td></tr><br />\n");
		}
		return input;
	}

//	public StringBuilder gerarAniversarianteMes(List<Pessoa> lista) throws IOException {
//		StringBuilder input = new StringBuilder();
//		for (Pessoa pessoa : lista) {
//			input.append("<tr><td>");
//			input.append("Codigo: ");
//			input.append(pessoa.getCodigo());
//			input.append("<br />Nome: ");
//			input.append(pessoa.getNome());
//			input.append("</td></tr><br />\n");
//		}
//		return input;
//	}

	public String cabecalho() {
		StringBuilder html = new StringBuilder();
		html.append("<!DOCTYPE html>\n");
		html.append("<html>\n");
		html.append("<head><title>Listas</title>\n");
		html.append("<meta charset=\"UTF-8\">\n");
		html.append("</head>\n");
		html.append("<body>\n");
		html.append("<table border=\"1\">\n");
		html.append("<tr><td>Contatos</td></tr>\n");
		return html.toString();
	}

	public String rodape() {
		StringBuilder html = new StringBuilder();
		html.append("</table>\n");
		html.append("</body>\n");
		html.append("</html>\n");
		return html.toString();
	}

	@Override
	public List<Pessoa> ler(String fileName) {
		// TODO Auto-generated method stub
		return null;
	}

}
