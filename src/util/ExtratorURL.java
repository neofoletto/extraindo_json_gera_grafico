/*
 *                     GNU GENERAL PUBLIC LICENSE
 *                        Version 3, 29 June 2007
 *
 *  Copyright (C) 2007 Free Software Foundation, Inc. <https://fsf.org/>
 *  Everyone is permitted to copy and distribute verbatim copies
 *  of this license document, but changing it is not allowed.
 *
 *                             Preamble
 *
 *   The GNU General Public License is a free, copyleft license for
 * software and other kinds of works.
 */

/**
 * @author neo, JotaSouza
 * @date 12/09/2019
 */
package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import main.Pessoa;

public class ExtratorURL {

	public ExtratorURL() {
	}

	public ExtratorURL(String url) {
		switch (url.split(".")[1]) {
		case "curvello":
			siteCurvello(url);
			break;

		default:
			return;
		}
	}

	public List<Pessoa> siteCurvello(String siteUrl) {
		String textoUrl = "";
		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		try {
			java.net.URL url = new URL(siteUrl);
			BufferedReader html = new BufferedReader(new InputStreamReader(url.openStream()));
			String aux_texto;
			while ((aux_texto = html.readLine()) != null) 
				textoUrl += aux_texto + System.lineSeparator();
			textoUrl = textoUrl.replace("\\/", "/");
			textoUrl = textoUrl.replace("\"fone\"", "\"telefone\"");
			Type listType = new TypeToken<ArrayList<Pessoa>>() {}.getType();
			List<Pessoa> lista = new ArrayList<Pessoa>();
			lista = gson.fromJson(textoUrl, listType);
			
			return lista;
		} catch (MalformedURLException excecao) {
			excecao.printStackTrace();
		} catch (IOException excecao) {
			excecao.printStackTrace();
		} catch (Exception excecao) {
			excecao.printStackTrace();
		}
		return null;
	}

}