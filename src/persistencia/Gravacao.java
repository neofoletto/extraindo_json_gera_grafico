/**
                    GNU GENERAL PUBLIC LICENSE
                       Version 3, 29 June 2007

 Copyright (C) 2007 Free Software Foundation, Inc. <https://fsf.org/>
 Everyone is permitted to copy and distribute verbatim copies
 of this license document, but changing it is not allowed.
 */
package persistencia;

import java.util.List;

import main.Pessoa;

public interface Gravacao {

	public boolean gravar(List<Pessoa> lista, String fileName);
	public List<Pessoa> ler(String fileName);
}
