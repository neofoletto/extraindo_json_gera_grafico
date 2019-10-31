/**
                    GNU GENERAL PUBLIC LICENSE
                       Version 3, 29 June 2007

 Copyright (C) 2007 Free Software Foundation, Inc. <https://fsf.org/>
 Everyone is permitted to copy and distribute verbatim copies
 of this license document, but changing it is not allowed.
 */
package persistencia;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import main.Pessoa;

public class PersistenciaCSV implements Gravacao {

	public PersistenciaCSV() {
	}

	private String setNome(String nome) {
		if (nome.length() != 0) {
			return nome + ".csv";
		}
		return "none.csv";
	}

	public boolean gravar(List<Pessoa> lista, String fileName) {
		FileWriter arq = null;
		try {
			arq = new FileWriter(setNome(fileName));
			PrintWriter gravarArq = new PrintWriter(arq);

			for (Pessoa pessoa : lista) {
				gravarArq.printf("%d;%s;%s;%s;%s\n"
						, pessoa.getCodigo()
						, pessoa.getNome()
						, pessoa.getDataNascimento().getTime()
						, pessoa.getEmail()
				    , pessoa.getTelefone());
			}
			arq.close();
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public List<Pessoa> ler(String fileName) {
		setNome(fileName);
		List<Pessoa> lista = null;
		try {
			Pessoa pessoa;
			FileReader arq = new FileReader(setNome(fileName));
			BufferedReader lerArq = new BufferedReader(arq);
			String linha = lerArq.readLine();
			lista = new ArrayList<Pessoa>();
			while (linha != null || !linha.isEmpty()) {
				String[] leitura = linha.split(";");
				pessoa = new Pessoa();
				pessoa.setCodigo(Integer.parseInt(leitura[0]));
				pessoa.setNome(leitura[1]);
				pessoa.setDataNascimentoDate(new Date(Long.parseLong(leitura[2])));
				pessoa.setEmail(leitura[3]);
				pessoa.setTelefone(leitura[4]);
				lista.add(pessoa);
				linha = lerArq.readLine();
			}
			arq.close();
		} catch (Exception e) {
			System.out.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
		}			
		return lista;
	}
}
