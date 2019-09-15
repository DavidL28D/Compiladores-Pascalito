package ast;
// indice, condicion, cuerpo, secuencia
public class NodoFor extends NodoBase {

    private NodoBase indice;
    private NodoBase condicion;
    private NodoBase cuerpo;
    private NodoBase cc;

    public NodoFor(NodoBase indice, NodoBase condicion, NodoBase cc, NodoBase cuerpo){
        super();
        this.indice = indice;
        this.condicion  = condicion;
        this.cuerpo = cuerpo;
        this.cc = cc;
    }

    public NodoBase getIndice(){
        return indice;
    }

    public NodoBase getCondicion(){
        return condicion;
    }

    public NodoBase getCuerpo(){
        return cuerpo;
    }
    
    public NodoBase getCc(){
        return cc;
    }
    
    public void setIndice(NodoBase indice){
        this.indice = indice;
    }

    public void setCondicion(NodoBase condicion){
        this.condicion = condicion;
    }
    
    public void setCc(NodoBase cc){
        this.cc = cc;
    }

    public void setCuerpo(NodoBase cuerpo){
        this.cuerpo = cuerpo;
    }
    
}
	