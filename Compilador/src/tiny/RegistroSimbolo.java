package tiny;

public class RegistroSimbolo {
	private String identificador;
	private int NumLinea;
	private int DireccionMemoria;
        private String tipo;
        private int size;
	
	public RegistroSimbolo(String identificador, int numLinea,
			int direccionMemoria, String tipo, int size) {
		super();
		this.identificador = identificador;
		NumLinea = numLinea;
		DireccionMemoria = direccionMemoria;
                this.tipo = tipo;
                this.size = size;
	}

	public String getIdentificador() {
		return identificador;
	}

	public int getNumLinea() {
		return NumLinea;
	}

	public int getDireccionMemoria() {
		return DireccionMemoria;
	}

	public void setDireccionMemoria(int direccionMemoria) {
		DireccionMemoria = direccionMemoria;
	}

        public String getTipo() {
            return tipo;
        }

        public void setTipo(String tipo) {
            this.tipo = tipo;
        }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
        
}
