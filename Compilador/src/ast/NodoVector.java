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
public class NodoVector {
    
    private NodoBase id;
    private NodoBase exp;
    
	public NodoVector(NodoBase id, NodoBase exp) {
		super();
		this.id = id;
		this.exp = exp;
	}
	
	
	public NodoVector() {
		super();
		this.id = null;
		this.exp = null;	
	}

	public NodoBase getId() {
		return id;
	}

	public void setId(NodoBase id) {
		this.id = id;
	}

	public NodoBase getExp() {
		return exp;
	}

	public void setExp(NodoBase exp) {
		this.exp = exp;
	}
	   
    
}
