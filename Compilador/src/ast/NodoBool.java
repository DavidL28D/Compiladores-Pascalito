/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ast;

/**
 *
 * @author thestrength
 */
public class NodoBool extends NodoBase{
        private int valor;
        
	public NodoBool(int valor) {
		super();
		this.valor = valor;
	}

	public NodoBool() {
		super();
	}
	
	public int getValor() {
		return valor;
	}

}
