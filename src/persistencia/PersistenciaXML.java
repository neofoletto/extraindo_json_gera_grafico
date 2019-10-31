/**
                    GNU GENERAL PUBLIC LICENSE
                       Version 3, 29 June 2007

 Copyright (C) 2007 Free Software Foundation, Inc. <https://fsf.org/>
 Everyone is permitted to copy and distribute verbatim copies
 of this license document, but changing it is not allowed.
 */
package persistencia;

import util.DataUtil;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

import main.Pessoa;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class PersistenciaXML implements Gravacao {

	private Element config = new Element("Agenda");
	private Pessoa pessoa;

	private String setNome(String nome) {
		if (nome.length() != 0) {
			return nome + ".xml";
		}
		return "none.xml";
	}

	public boolean gravar(List<Pessoa> lista, String fileName) {
		Document documento = new Document(config);
		Element titulo = new Element("titulo");
		titulo.setText("Agenda");
		Element data = new Element("data");
		data.setText(DataUtil.DataHoraForStringPadrao(new Date()));
		config.addContent(titulo);
		config.addContent(data);

		for (int x = 0; x < lista.size(); x++) {
			Element pessoa = new Element("leAgende");
			pessoa.setAttribute("codigo", String.valueOf(lista.get(x).getCodigo()));
			Element nome = new Element("nome");
			nome.setText(lista.get(x).getNome());
			pessoa.addContent(nome);
			Element dataNascimento = new Element("dataNascimento");
			dataNascimento.setText(String.valueOf(lista.get(x).getDataNascimento().getTime()));
			pessoa.addContent(dataNascimento);
			Element email = new Element("email");
			email.setText(lista.get(x).getEmail());
			pessoa.addContent(email);
			Element telefone = new Element("telefone");
			telefone.setText(lista.get(x).getTelefone());
			pessoa.addContent(telefone);
			config.addContent(pessoa);
		}
		XMLOutputter xout = new XMLOutputter();
		try {
			BufferedWriter arquivo = new BufferedWriter(
			    new OutputStreamWriter(new FileOutputStream(setNome(fileName)), "UTF-8"));
			xout.output(documento, arquivo);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	@SuppressWarnings("rawtypes")
	public List<Pessoa> ler(String fileName) {
		List<Pessoa> lista = new ArrayList<Pessoa>();
		Document doc = null;
		SAXBuilder builder = new SAXBuilder();
		try {
			doc = builder.build(setNome(fileName));
		} catch (Exception e) {
			e.printStackTrace();
		}
		config = doc.getRootElement();
		List ls = config.getChildren("leAgende");

		for (Iterator iter = ls.iterator(); iter.hasNext();) {
			Element element = (Element) iter.next();
			pessoa = new Pessoa();
			pessoa.setCodigo(Integer.parseInt(element.getAttributeValue("codigo")));
			pessoa.setNome(element.getChildText("nome"));
			pessoa.setDataNascimentoDate(new Date(Long.parseLong(element.getChildText("dataNascimento"))));
			pessoa.setEmail(element.getChildText("email"));
			pessoa.setTelefone(element.getChildText("telefone"));
			lista.add(pessoa);
		}
		return lista;
	}
}
