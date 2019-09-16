package ast;

public class NodoAsignacion extends NodoBase {
	private String identificador;
	private NodoBase expresion;
        private NodoBase expresion2;
	
	public NodoAsignacion(String identificador) {
		super();
		this.identificador = identificador;
		this.expresion = null;
                this.expresion2 = null;
	}
        
        public NodoAsignacion(String identificador, NodoBase expresion) {
		super();
		this.identificador = identificador;
		this.expresion = expresion;
                this.expresion2 = null;
	}
	
	public NodoAsignacion(String identificador, NodoBase expresion, NodoBase expresion2) {
		super();
		this.identificador = identificador;
		this.expresion = expresion;
                this.expresion2 = expresion2;
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

        public NodoBase getExpresion2() {
            return expresion2;
        }

        public void setExpresion2(NodoBase expresion2) {
            this.expresion2 = expresion2;
        }


	
	
}
