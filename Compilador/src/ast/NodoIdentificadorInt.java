package ast;

public class NodoIdentificadorInt extends NodoIdentificador {
    
    private NodoBase expresion;
    
    public NodoIdentificadorInt(String nombre){
        super(nombre);
        this.expresion = null;
    }
    
    
    public NodoIdentificadorInt(String nombre,NodoBase ex){
        super(nombre);
        this.expresion = ex;
    }

    public NodoBase getExpresion() {
        return expresion;
    }

    public void setExpresion(NodoBase expresion) {
        this.expresion = expresion;
    }

}