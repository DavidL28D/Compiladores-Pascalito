package ast;

public class NodoOperacion extends NodoBase {
		
	private NodoBase opIzquierdo;
	private NodoBase opDerecho;
	private tipoOp operacion;
	
	public NodoOperacion(NodoBase opIzquierdo, tipoOp tipoOperacion, NodoBase opDerecho) {
		super();
		this.opIzquierdo = opIzquierdo;
		this.opDerecho = opDerecho;
		this.operacion = tipoOperacion;
	}

	public NodoOperacion(tipoOp tipoOperacion, NodoBase opDerecho) {
		super();
		this.opDerecho = opDerecho;
		this.operacion = tipoOperacion;
	}

	public NodoOperacion(tipoOp tipoOperacion) {
		super();
		this.opIzquierdo = null;
		this.opDerecho = null;
		this.operacion = tipoOperacion;
	}

    public NodoOperacion(tipoOp tipoOp, NodoBool exbD) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


	public NodoBase getOpIzquierdo() {
		return opIzquierdo;
	}

	public void setOpIzquierdo(NodoBase opIzquierdo) {
		this.opIzquierdo = opIzquierdo;
	}

	public NodoBase getOpDerecho() {
		return opDerecho;
	}

	public void setOpDerecho(NodoBase opDerecho) {
		this.opDerecho = opDerecho;
	}

	public tipoOp getOperacion() {
		return operacion;
	}

	
}
