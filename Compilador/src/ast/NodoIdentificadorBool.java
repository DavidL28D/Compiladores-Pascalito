package ast;

public class NodoIdentificadorBool extends NodoIdentificador{
    private NodoBase expresion;
    public NodoIdentificadorBool(String nombre){
        super(nombre);
        this.expresion=null;
    }
    public NodoIdentificadorBool (String nombre, NodoBase ex){
        super(nombre);
        this.expresion=ex;
    }
    public NodoBase getExpresion(){
        return expresion;
    }
    public void setExpresion(NodoBase expresion){
        this.expresion=expresion;
    }

}