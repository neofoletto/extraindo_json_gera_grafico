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

package persistencia;

import code.EixoX;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author neo
 * @create 08/08/2019
 */
public class PersistenciaGRAFICO {

	private final String PATH = "charts/";
	private String style = "";
	
	/**
	 * 
	 */
	public PersistenciaGRAFICO() {
		// TODO Auto-generated constructor stub
	}

	public PersistenciaGRAFICO(String tipoDoGrafico, String fileName, String title, Map<String, Integer> mapEixos) {
		try {
			grava(tipoDoGrafico, fileName, title, mapEixos);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Reponsável por realizar a persistência do HTML em um arquivo .html.
	 *
	 * @param fileName:   String => recebe o nome do gráfico;
	 * @param title:      String => recebe o título da página;
	 * @param mapEixo 		HashMap => 
	 *
	 * @throws IOException
	 */
	public void grava(String tipoDoGrafico, String fileName, String title, Map<String, Integer> mapEixos)
	    throws IOException {
		FileWriter arquivo = new FileWriter(this.PATH.concat(fileName).concat(".html"));
		PrintWriter persistir = new PrintWriter(arquivo);
		EixoX eixoX;
		List<EixoX> listaEixoX = new ArrayList<>();
		List<String> listaEixoY = new ArrayList<>();

		for (String key : mapEixos.keySet()) {
			// Capturamos o valor a partir da chave
			Integer value = mapEixos.get(key);
			listaEixoY.add(key);
			eixoX = new EixoX();
			eixoX.setNome(key);
			eixoX.setDado(value);
			listaEixoX.add(eixoX);
		}

		persistir.printf("%s\n", cabecalho(fileName, title, listaEixoY, listaEixoX));
		persistir.printf("%s\n", corpo(fileName));
		persistir.printf("%s\n", rodape());
		arquivo.close();
	}

	/**
	 * Método responsável por gerar o cabeçalho padrão do HTML.
	 *
	 * @param type:       String => tipo do gráfico a ser gerado;
	 * @param title:      String => recebe o título da página;
	 * @param listaEixoY: List => lista com os valores do eixo Y;
	 * @param listaEixoX: List => lista com os valores do eixo X.
	 *
	 * @return String.
	 * @throws IOException
	 */
	private String cabecalho(String type, String title, List<String> listaEixoY, List<EixoX> listaEixoX)
	    throws IOException {
		StringBuilder html = new StringBuilder();

		html.append("<!DOCTYPE html>\n");
		html.append("<html>\n");
		html.append("<head>\n");
		html.append("<title>");
		html.append(type);
		html.append("</title>\n");
		html.append(script(type, title, listaEixoY, listaEixoX));
		html.append("</head>\n");
		return html.toString();
	}

	/**
	 * Método responsável por gerar o rodapé padrão do HTML.
	 *
	 * @return StringBuilder.
	 */
	private String rodape() {
		StringBuilder html = new StringBuilder();
		html.append("</html>\n");
		return html.toString();
	}

	/**
	 * Método responsável por gerar o corpo do HTML.
	 *
	 * @param style: String => Estilo CSS.
	 *
	 * @return String.
	 */
	private String corpo(String style) {
		StringBuilder html = new StringBuilder();

		html.append("<body>\n");
		html.append("<div \n");
		html.append("id=\"chart_div\" style=\"");
		html.append(this.style);
		html.append("\">\n");
		html.append("</div>\n");
		html.append("</body>\n");
		return html.toString();
	}

	/**
	 * Método responsável por gerar o <script> do HTML.
	 *
	 * @param type:       String => tipo do gráfico a ser gerado;
	 * @param title:      String => recebe o título da página;
	 * @param listaEixoY: List => lista com os valores do eixo Y;
	 * @param listaEixoX: List => lista com os valores do eixo X.
	 *
	 * @return StringBuilder.
	 * @throws IOException
	 */
	private StringBuilder script(String type, String title, List<String> listaEixoY, List<EixoX> listaEixoX)
	    throws IOException {
		StringBuilder html = new StringBuilder();

		html.append("<script type=\"text/javascript\" src=\"https://www.gstatic.com/charts/loader.js\"></script>\n");
		html.append("<script type=\"text/javascript\">\n");
		html.append("google.charts.load('current', {'packages':['corechart']});\n");
		html.append("google.charts.setOnLoadCallback(drawChart);\n");
		html.append(functionDrawJS(type, title, listaEixoY, listaEixoX));
		html.append("</script>\n");
		return html;
	}

	/**
	 * Método responsável por gerar o JavaScript presente no <script> do HTML.
	 *
	 * @param type:       String => tipo do gráfico a ser gerado;
	 * @param title:      String => recebe o título da página;
	 * @param listaEixoY: List => lista com os valores do eixo Y;
	 * @param listaEixoX: List => lista com os valores do eixo X.
	 *
	 * @return StringBuilder.
	 * @throws IOException
	 */
	private StringBuilder functionDrawJS(String type, String title, List<String> listaEixoY, List<EixoX> listaEixoX)
	    throws IOException {
		StringBuilder html = new StringBuilder();
		String titleArea = "";

		html.append("function drawChart() {\n");
		html.append("var data = google.visualization.arrayToDataTable([\n");

		html.append(dados(type, listaEixoY, listaEixoX));

		html.append("]);\n");

		html.append("var options = {\n");
		html.append("title: '");
		html.append(title);
		html.append("',\n");
		switch (type) {

			case "AreaChart":
				this.style = "width: 100%; height: 500px;";
				html.append("hAxis: {title: '");
				html.append(listaEixoY.get(0));
				html.append("',  titleTextStyle: {color: '#333'}},\n");
				html.append("vAxis: {minValue: 0}\n");
				break;
	
			case "LineChart":
				this.style = "width: 900px; height: 500px";
				html.append("curveType: 'function',");
				html.append("legend: { position: 'bottom' }");
				break;
	
			case "PieChart":
				this.style = "width: 900px; height: 500px;";
				html.append("is3D: true,");
				break;
		}
		html.append("};\n");

		html.append("var chart = new google.visualization.");
		html.append(type);
		html.append("(document.getElementById('chart_div'));\n");
		html.append("chart.draw(data, options);\n");
		html.append("}\n");
		return html;
	}

	/**
	 * Método responsável por organizar e gerar os dados que compõe o JavaScript
	 * para a modelagem do gráfico.
	 *
	 * @param type:       String => tipo do gráfico a ser gerado;
	 * @param listaEixoY: List => lista com os valores do eixo Y;
	 * @param listaEixoX: List => lista com os valores do eixo X.
	 *
	 * @return StringBuilder.
	 * @throws IOException
	 */
	private StringBuilder dados(String type, List<String> listaEixoY, List<EixoX> listaEixoX) throws IOException {
    StringBuilder html = new StringBuilder();

    switch (type) {
      case "AreaChart": case "LineChart":
        for (int i = 0; i < listaEixoY.size(); i++) {
          html.append("[");
          html.append("'");
          html.append(listaEixoY.get(i));
          html.append("',");
          for (int j = 0; j < listaEixoX.size(); j++) {
            if (i == 0) {
              html.append("'");
              html.append(listaEixoX.get(j).getNome());
              html.append("',");
            } else {
            	html.append("[");
            	html.append("'");
              html.append(listaEixoX.get(j).getNome());
              html.append("',");
              html.append(listaEixoX.get(j).getDado().get(0));
              html.append(",");
              html.append("],").deleteCharAt(html.length() - 3);
            }
            
          }
        }
        break;

      case "PieChart":
        for (int i = 0; i < listaEixoY.size(); i++) {
          
          if (i == 0) {
          	html.append("[");
            html.append("'");
            html.append(listaEixoX.get(i).getNome());
            html.append("','");
            html.append(listaEixoX.get(i).getNome());
            html.append("''");
          } else {
          	html.append("[");
          	html.append("'");
            html.append(listaEixoX.get(i - 1).getNome());
            html.append("',");
            html.append(listaEixoX.get(i - 1).getDado().get(0));
            html.append(",");
          }
          html.append("],").deleteCharAt(html.length() - 3);
        }
        break;
    }
    return html.deleteCharAt(html.length() - 1);
  }
}