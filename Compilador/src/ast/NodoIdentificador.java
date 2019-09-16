package ast;

public class NodoIdentificador extends NodoBase {
	private String nombre;
        private int  size;

	public NodoIdentificador(String nombre) {
		super();
		this.nombre = nombre;
                size = 0;
	}
        
        public NodoIdentificador (String nombre, int size){
            super();
            this.nombre = nombre;
            this.size = size;
        }

	public NodoIdentificador() {
		super();
                this.size = 0;
	}

	public String getNombre() {
		return nombre;
	}

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
        

}
