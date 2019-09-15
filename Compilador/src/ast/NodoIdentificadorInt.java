package ast;

public class NodoIdentificadorInt extends NodoIdentificador {
    
    int size;
    
    public NodoIdentificadorInt(String nombre){
        super(nombre);
        this.size = 0;
    }
    
    
    public NodoIdentificadorInt(String nombre,int size){
        super(nombre);
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public void setExpresion(int size) {
        this.size = size;
    }

}