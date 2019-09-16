package ast;

public class NodoLeer extends NodoBase {
	private String id;
        private NodoBase expresion;
	public NodoLeer(String identificador) {
		super();
		this.id = identificador;
                this.expresion = null;
	}
        public NodoLeer(String identificador, NodoBase expresion) {
		super();
		this.id = identificador;
                this.expresion = expresion;
	}
        

	public NodoLeer() {
		super();
		id="";
	}

	public String getIdentificador() {
		return id;
	}

	public void setIdentificador(String identificador) {
		this.id = identificador;
	}


        public NodoBase getExpresion() {
            return expresion;
        }

        public void setExpresion(NodoBase expresion) {
            this.expresion = expresion;
        }

        
        

}
