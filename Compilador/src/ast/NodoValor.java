package ast;

public class NodoValor extends NodoBase {
	private Integer valor;

	public NodoValor(Integer valor) {
		super();
		this.valor = valor;
	}

	public NodoValor() {
		super();
	}
	
	public Integer getValor() {
            if(valor != null)
		return valor;
            else{
                return 0;
            }
	}

}
