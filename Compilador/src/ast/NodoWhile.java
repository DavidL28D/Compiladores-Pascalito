package ast;

public class NodoWhile extends NodoBase {

    private NodoBase condicion;
    private NodoBase cuerpo;

    public NodoWhile(NodoBase condicion, NodoBase cuerpo){
        super();
        this.condicion=condicion;
        this.cuerpo=cuerpo;
    }

    public NodoWhile(){
        super();
        this.condicion=null;
        this.cuerpo=null;
    }

    public NodoBase getCondicion(){
        return condicion;
    }

    public NodoBase getCuerpo(){
        return cuerpo;
    }

    public void setCondicion(NodoBase condicion){
        this.condicion = condicion;
    }
    
    public void setCuerpo(NodoBase cuerpo){
        this.cuerpo=cuerpo;
    }
     
}