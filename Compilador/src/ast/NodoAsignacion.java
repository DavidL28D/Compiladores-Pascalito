package ast;

public class NodoAsignacion extends NodoBase {
	private String identificador;
	private NodoBase expresion;
        private int size;
	
	public NodoAsignacion(String identificador) {
		super();
		this.identificador = identificador;
		this.expresion = null;
                this.size = 0;
	}
	
	public NodoAsignacion(String identificador, NodoBase expresion, int size) {
		super();
		this.identificador = identificador;
		this.expresion = expresion;
                this.size = size;
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public NodoBase getExpresion() {
		return expresion;
	}

	public void setExpresion(NodoBase expresion) {
		this.expresion = expresion;
	}

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }
	
	
}
