package ast;

public class NodoIdentificadorBool extends NodoIdentificador{
    private int size;
    public NodoIdentificadorBool(String nombre){
        super(nombre);
        this.size=0;
    }
    public NodoIdentificadorBool (String nombre, int size){
        super(nombre);
        this.size=size;
    }
    public int getSize(){
        return size;
    }
    public void setSize(int size){
        this.size=size;
    }

}