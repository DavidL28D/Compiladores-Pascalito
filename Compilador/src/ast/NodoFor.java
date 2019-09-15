package ast;
// indice, condicion, cuerpo, secuencia
public class NodoFor extends NodoBase {

    private NodoBase indice;
    private NodoBase condicion;
    private NodoBase cuerpo;

    public NodoFor(NodoBase indice, NodoBase condicion, NodoBase cuerpo){
        super();
        this.indice = indice;
        this.condicion  = condicion;
        this.cuerpo = cuerpo;
        //this.secuencia = secuencia;
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
    
    /*
    public NodoBase getSecuencia(){
        return secuencia;
    }
    */
    
    public void setIndice(NodoBase indice){
        this.indice = indice;
    }

    public void setCondicion(NodoBase condicion){
        this.condicion = condicion;
    }

    public void setCuerpo(NodoBase cuerpo){
        this.cuerpo = cuerpo;
    }
    
    /*
    public void setSecuencia(NodoBase secuencia){
        this.secuencia = secuencia;
    }
    */
}
	