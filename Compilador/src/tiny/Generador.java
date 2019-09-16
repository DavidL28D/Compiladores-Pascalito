package tiny;

import ast.*;

public class Generador {
	/* Ilustracion de la disposicion de la memoria en
	 * este ambiente de ejecucion para el lenguaje Tiny
	 *
	 * |t1	|<- mp (Maxima posicion de memoria de la TM
	 * |t1	|<- desplazamientoTmp (tope actual)
	 * |free|
	 * |free|
	 * |...	|
	 * |x	|
	 * |y	|<- gp
	 * 
	 * */
	
	
	
	/* desplazamientoTmp es una variable inicializada en 0
	 * y empleada como el desplazamiento de la siguiente localidad
	 * temporal disponible desde la parte superior o tope de la memoria
	 * (la que apunta el registro MP).
	 * 
	 * - Se decrementa (desplazamientoTmp--) despues de cada almacenamiento y
	 * 
	 * - Se incrementa (desplazamientoTmp++) despues de cada eliminacion/carga en 
	 *   otra variable de un valor de la pila.
	 * 
	 * Pudiendose ver como el apuntador hacia el tope de la pila temporal
	 * y las llamadas a la funcion emitirRM corresponden a una inserccion 
	 * y extraccion de esta pila
	 */
	private static int desplazamientoTmp = 0;
	private static TablaSimbolos tablaSimbolos = null;
	
	public static void setTablaSimbolos(TablaSimbolos tabla){
		tablaSimbolos = tabla;
	}
	
	public static void generarCodigoObjeto(NodoBase raiz){
		System.out.println();
		System.out.println();
		System.out.println("------ CODIGO OBJETO DEL LENGUAJE TINY GENERADO PARA LA TM ------");
		System.out.println();
		System.out.println();
		generarPreludioEstandar();
		generar(raiz);
		/*Genero el codigo de finalizacion de ejecucion del codigo*/   
		UtGen.emitirComentario("Fin de la ejecucion.");
		UtGen.emitirRO("HALT", 0, 0, 0, "");
		System.out.println();
		System.out.println();
		System.out.println("------ FIN DEL CODIGO OBJETO DEL LENGUAJE TINY GENERADO PARA LA TM ------");
	}
	
	//Funcion principal de generacion de codigo
	//prerequisito: Fijar la tabla de simbolos antes de generar el codigo objeto 
	private static void generar(NodoBase nodo){
	if(tablaSimbolos!=null){
		if (nodo instanceof  NodoIf){
			generarIf(nodo);
		}else if (nodo instanceof  NodoRepeat){
			generarRepeat(nodo);
		}else if (nodo instanceof  NodoFor){
			generarFor(nodo);
		}else if (nodo instanceof  NodoAsignacion){
			generarAsignacion(nodo);
		}else if (nodo instanceof  NodoLeer){
			generarLeer(nodo);
		}else if (nodo instanceof  NodoEscribir){
			generarEscribir(nodo);
		}else if (nodo instanceof NodoValor){
			generarValor(nodo);
		}else if (nodo instanceof NodoBool){
			generarBool(nodo);
		}/*else if (nodo instanceof NodoIdentificadorInt){
			generarIdentificadorInt((NodoIdentificador)nodo);
		}else if (nodo instanceof NodoIdentificadorBool){
			generarIdentificadorBool((NodoIdentificador)nodo);
		}*/else if (nodo instanceof NodoOperacion){
			generarOperacion(nodo);
		}else if (nodo instanceof NodoWhile){
            generarWhile(nodo);
        }else if (nodo instanceof NodoIdentificador){
                    generarIdentificador(nodo);
		}else{
			System.out.println("BUG: Tipo de nodo a generar desconocido");
		}
		/*Si el hijo de extrema izquierda tiene hermano a la derecha lo genero tambien*/
		if(nodo.TieneHermano())
			generar(nodo.getHermanoDerecha());
	}else
		System.out.println("���ERROR: por favor fije la tabla de simbolos a usar antes de generar codigo objeto!!!");
}

	private static void generarIf(NodoBase nodo){
    	NodoIf n = (NodoIf)nodo;
		int localidadSaltoElse,localidadSaltoEnd,localidadActual;
		if(UtGen.debug)	UtGen.emitirComentario("-> if");
		/*Genero el codigo para la parte de prueba del IF*/
		generar(n.getPrueba());
		localidadSaltoElse = UtGen.emitirSalto(1);
		UtGen.emitirComentario("If: el salto hacia el else debe estar aqui");
		/*Genero la parte THEN*/
		generar(n.getParteThen());
		localidadSaltoEnd = UtGen.emitirSalto(1);
		UtGen.emitirComentario("If: el salto hacia el final debe estar aqui");
		localidadActual = UtGen.emitirSalto(0);
		UtGen.cargarRespaldo(localidadSaltoElse);
		UtGen.emitirRM_Abs("JEQ", UtGen.AC, localidadActual, "if: jmp hacia else");
		UtGen.restaurarRespaldo();
		/*Genero la parte ELSE*/
		if(n.getParteElse()!=null){
			generar(n.getParteElse());
    	}
		//igualmente debo generar la sentencia que reserve en
		//localidadSaltoEnd al finalizar la ejecucion de un true
		localidadActual = UtGen.emitirSalto(0);
		UtGen.cargarRespaldo(localidadSaltoEnd);
		UtGen.emitirRM_Abs("LDA", UtGen.PC, localidadActual, "if: jmp hacia el final");
		UtGen.restaurarRespaldo();			
		if(UtGen.debug)	UtGen.emitirComentario("<- if");
	}
	
	private static void generarRepeat(NodoBase nodo){
    	NodoRepeat n = (NodoRepeat)nodo;
		int localidadSaltoInicio;
		if(UtGen.debug)	UtGen.emitirComentario("-> repeat");
			localidadSaltoInicio = UtGen.emitirSalto(0);
			UtGen.emitirComentario("repeat: el salto hacia el final (luego del cuerpo) del repeat debe estar aqui");
			/* Genero el cuerpo del repeat */
			generar(n.getCuerpo());
			/* Genero el codigo de la prueba del repeat */
			generar(n.getPrueba());
			UtGen.emitirRM_Abs("JEQ", UtGen.AC, localidadSaltoInicio, "repeat: jmp hacia el inicio del cuerpo");
		if(UtGen.debug)	UtGen.emitirComentario("<- repeat");
	}
        
        private static void generarFor(NodoBase nodo){

            NodoFor n = (NodoFor)nodo;
            int localidadSaltoInicio;
            if(UtGen.debug)	UtGen.emitirComentario("-> For");
            localidadSaltoInicio = UtGen.emitirSalto(0);
            UtGen.emitirComentario("For: el salto hacia el final (luego del cuerpo) del for debe estar aqui");
            
            // Se genera el indice del for
            generar(n.getIndice());

            // Se genera la condicion del for
            generar(n.getCondicion());

            // Se genera el cuerpo del for
            generar(n.getCuerpo());

            // Se genera la secuencia del for
            generar(n.getCc());
            
            NodoAsignacion a = (NodoAsignacion)n.getIndice();
            NodoValor av = (NodoValor)a.getExpresion();
            int culo = av.getValor();
            
            System.out.println("Hola*******************"+ av.getValor());
            UtGen.emitirRM_Abs("JEQ", UtGen.AC, localidadSaltoInicio, "For: jmp hacia el inicio del cuerpo");
            if(UtGen.debug)	UtGen.emitirComentario("<- For");
	}

	private static void generarWhile(NodoBase nodo){
		NodoWhile n = (NodoWhile)nodo;
		int localidadSaltoInicio;
		if(UtGen.debug) UtGen.emitirComentario("-> while");
			/*Obtengo la linea donde inicia el ciclo */
			/*Salto si la condicion del while no se cumple */
			/* */
		if(UtGen.debug)	UtGen.emitirComentario("<- while");	
	}		
	
	private static void generarAsignacion(NodoBase nodo){
		NodoAsignacion n = (NodoAsignacion)nodo;
		int direccion, valor;
                String tipo;
		if(UtGen.debug)	UtGen.emitirComentario("-> asignacion");		
		/* Genero el codigo para la expresion a la derecha de la asignacion */
                //generar(n.getExpresion());
		/* Ahora almaceno el valor resultante */
                if(n.getExpresion2() != null){
                    generar(n.getExpresion2());
                    NodoValor v = (NodoValor)n.getExpresion2();
                    valor = v.getValor();
                }else{
                    valor = 0;
                }
                generar(n.getExpresion());
                if(n.getExpresion2()!= null){
                     n.setIdentificador(n.getIdentificador()+"[]");
                     direccion = tablaSimbolos.getDireccion(n.getIdentificador()) -(tablaSimbolos.getSize(n.getIdentificador()) - 1) + valor;
                }else{
                    direccion = tablaSimbolos.getDireccion(n.getIdentificador());
                }
                
                tipo = tablaSimbolos.getTipo(n.getIdentificador());
                
                if(tipo.equals("bool")){
                    UtGen.emitirRM("LDC", UtGen.AC1, 0, UtGen.AC, "Carga constante 0");
                    UtGen.emitirRM("LDC", 2, 1, UtGen.AC, "Carga constante 1");
                    UtGen.emitirRM("ST", UtGen.AC, direccion, UtGen.GP, "asignacion: almaceno el valor para el id "+n.getIdentificador());
                    UtGen.emitirRO("SUB", UtGen.AC1, UtGen.AC, UtGen.AC1, "Resta valor - 0");
                    UtGen.emitirRM("JNE", UtGen.AC1, 2, UtGen.PC, " Voy dos instrucciones mas alla del if verdadero (AC1<>0)");
                    UtGen.emitirRM("ST", UtGen.AC, direccion, UtGen.GP, "asignacion: almaceno el valor para el id "+n.getIdentificador());
                    UtGen.emitirRM("LDA", UtGen.PC, 1, UtGen.PC, "Salto incondicional a direccion PC+1");
                    UtGen.emitirRO("SUB", UtGen.AC1, UtGen.AC , 2, "Resta valor - 1");
                    UtGen.emitirRM("JNE", UtGen.AC1, 2, UtGen.PC, " Voy dos instrucciones mas alla del if verdadero (AC<>0)");
                    UtGen.emitirRM("ST", UtGen.AC, direccion, UtGen.GP, "asignacion: almaceno el valor para el id "+n.getIdentificador());
                    UtGen.emitirRM("LDA", UtGen.PC, 1, UtGen.PC, "Salto incondicional a direccion PC+1");
                    UtGen.emitirRO("HALT", 0, 0, 0, "Fin de programa -> Error en asignacion a Booleanos");
                    //generar error

                }else{

                    UtGen.emitirRM("ST", UtGen.AC, direccion, UtGen.GP, "asignacion: almaceno el valor para el id "+n.getIdentificador());
                
				}
				
		if(UtGen.debug)	UtGen.emitirComentario("<- asignacion");
	}
	
	private static void generarLeer(NodoBase nodo){
		NodoLeer n = (NodoLeer)nodo;
		int direccion;
                int valor;
                if(n.getExpresion() != null){
                    NodoValor v = (NodoValor)n.getExpresion();
                    valor = v.getValor();
                }else{
                    valor = 0;
                }
		if(UtGen.debug)	UtGen.emitirComentario("-> leer");
		UtGen.emitirRO("IN", UtGen.AC, 0, 0, "leer: lee un valor entero ");
                
                if(n.getExpresion() != null){
                    n.setIdentificador(n.getIdentificador()+"[]");
                    direccion = tablaSimbolos.getDireccion(n.getIdentificador()) -
                            (tablaSimbolos.getSize(n.getIdentificador()) - 1) + valor;
                }else{
                    direccion = tablaSimbolos.getDireccion(n.getIdentificador());
                }
		UtGen.emitirRM("ST", UtGen.AC, direccion, UtGen.GP, "leer: almaceno el valor entero leido en el id "+n.getIdentificador());
		if(UtGen.debug)	UtGen.emitirComentario("<- leer");
	}
	
	private static void generarEscribir(NodoBase nodo){
		NodoEscribir n = (NodoEscribir)nodo;
		if(UtGen.debug)	UtGen.emitirComentario("-> escribir");
		/* Genero el codigo de la expresion que va a ser escrita en pantalla */
		generar(n.getExpresion());
		/* Ahora genero la salida */
		UtGen.emitirRO("OUT", UtGen.AC, 0, 0, "escribir: genero la salida de la expresion");
		if(UtGen.debug)	UtGen.emitirComentario("<- escribir");
	}
	
	private static void generarValor(NodoBase nodo){
            NodoValor n = (NodoValor)nodo;
            if(UtGen.debug)	UtGen.emitirComentario("-> constante");
            UtGen.emitirRM("LDC", UtGen.AC, n.getValor(), 0, "cargar constante: "+n.getValor().toString());
            if(UtGen.debug)	UtGen.emitirComentario("<- constante");
	}
	 
        private static void generarBool(NodoBase nodo){
    	NodoBool n = (NodoBool)nodo;
    	if(UtGen.debug)	UtGen.emitirComentario("-> constante");
    	UtGen.emitirRM("LDC", UtGen.AC, n.getValor() , 0, "cargar constante: "+n.getValor());
    	if(UtGen.debug)	UtGen.emitirComentario("<- constante");
	}
        
        @SuppressWarnings("empty-statement")
        private static void generarIdentificador(NodoBase nodo){
            NodoIdentificador n = (NodoIdentificador)nodo;
            String tipo;
            int size;
            
            if(tablaSimbolos.BuscarSimbolo(n.getNombre()) == null){
                tipo = tablaSimbolos.getTipo(n.getNombre()+"[]");
                size = tablaSimbolos.getSize(n.getNombre()+"[]");
            }else{
                tipo = tablaSimbolos.getTipo(n.getNombre());
                size = tablaSimbolos.getSize(n.getNombre());
            }
            
            
            
            if(tipo.equals("int")){
                generarIdentificadorInt(n,size);
            }else if(tipo.equals("bool")){                
                generarIdentificadorBool(n,size);            
            }
        }
        
        private static void generarIdentificadorInt(NodoIdentificador nodo, int size){
            int direccion;
            String nombre = nodo.getNombre();
            if(UtGen.debug)	UtGen.emitirComentario("-> identificador int");
            if(size > 0){
               nombre = nombre + "[]";
                
                direccion = tablaSimbolos.getDireccion(nombre) -(tablaSimbolos.getSize(nombre) - 1) + size;
            }else{
                direccion = tablaSimbolos.getDireccion(nombre);
            }

            UtGen.emitirRM("LD", UtGen.AC, direccion, UtGen.GP, "cargar valor de identificador int: "+nombre+"["+size+"]");
            if(UtGen.debug)	UtGen.emitirComentario("-> identificador int");
        }
        
        private static void generarIdentificadorBool(NodoIdentificador nodo, int size){
            int direccion;
            String nombre = nodo.getNombre();
            if(UtGen.debug)	UtGen.emitirComentario("-> identificador bool");
            if(size > 0){
                nombre = nombre + "[]";
                direccion = tablaSimbolos.getDireccion(nombre) -(tablaSimbolos.getSize(nombre) - 1) + size;
            }else{
                direccion = tablaSimbolos.getDireccion(nombre);
            }
            UtGen.emitirRM("LD", UtGen.AC, direccion, UtGen.GP, "cargar valor de identificador bool: "+nodo.getNombre());
            if(UtGen.debug)	UtGen.emitirComentario("-> identificador bool");
        }
        
	private static void generarOperacion(NodoBase nodo){
		NodoOperacion n = (NodoOperacion) nodo;
		if(UtGen.debug)	UtGen.emitirComentario("-> Operacion: " + n.getOperacion());
		
		generar(n.getOpIzquierdo());
		/* Almaceno en la pseudo pila de valor temporales el valor de la operacion izquierda */
		UtGen.emitirRM("ST", UtGen.AC, desplazamientoTmp--, UtGen.MP, "op: push en la pila tmp el resultado expresion izquierda");
		/* Genero la expresion derecha de la operacion */
		generar(n.getOpDerecho());
		/* Ahora cargo/saco de la pila el valor izquierdo */
		UtGen.emitirRM("LD", UtGen.AC1, ++desplazamientoTmp, UtGen.MP, "op: pop o cargo de la pila el valor izquierdo en AC1");
		switch(n.getOperacion()){
                        case    mod: UtGen.emitirRO("DIV", 2, UtGen.AC1, UtGen.AC, "op: /");
                                        UtGen.emitirRO("MUL", 2, 2, UtGen.AC, "op: *");
                                        UtGen.emitirRO("SUB", UtGen.AC, UtGen.AC1, 2, "op: %");
                                break;
                        case    mayorigual: UtGen.emitirRO("SUB", UtGen.AC, UtGen.AC1, UtGen.AC, "op: >=");
                                            UtGen.emitirRM("JGE", UtGen.AC, 2, UtGen.PC, "Voy dos instrucciones mas alla del if verdadero.");
                                            UtGen.emitirRM("LDC", UtGen.AC, 0, UtGen.AC, "En caso de ser falso, se asigna 0");
                                            UtGen.emitirRM("LDA", UtGen.PC, 1, UtGen.PC, "Salto incondicional a direccion PC+1");
                                            UtGen.emitirRM("LDC", UtGen.AC, 1, UtGen.AC, "En caso de ser verdadero, se asigna 1");
                                            
                                break;
			case	mas:	UtGen.emitirRO("ADD", UtGen.AC, UtGen.AC1, UtGen.AC, "op: +");		
							break;
			case	menos:	UtGen.emitirRO("SUB", UtGen.AC, UtGen.AC1, UtGen.AC, "op: -");
							break;
			case	por:	UtGen.emitirRO("MUL", UtGen.AC, UtGen.AC1, UtGen.AC, "op: *");
							break;
			case	entre:	UtGen.emitirRO("DIV", UtGen.AC, UtGen.AC1, UtGen.AC, "op: /");
							break;		
			case	menor:	UtGen.emitirRO("SUB", UtGen.AC, UtGen.AC1, UtGen.AC, "op: <");
							UtGen.emitirRM("JLT", UtGen.AC, 2, UtGen.PC, "voy dos instrucciones mas alla if verdadero (AC<0)");
							UtGen.emitirRM("LDC", UtGen.AC, 0, UtGen.AC, "caso de falso (AC=0)");
							UtGen.emitirRM("LDA", UtGen.PC, 1, UtGen.PC, "Salto incodicional a direccion: PC+1 (es falso evito colocarlo verdadero)");
							UtGen.emitirRM("LDC", UtGen.AC, 1, UtGen.AC, "caso de verdadero (AC=1)");
							break;
			case	igual:	UtGen.emitirRO("SUB", UtGen.AC, UtGen.AC1, UtGen.AC, "op: ==");
							UtGen.emitirRM("JEQ", UtGen.AC, 2, UtGen.PC, "voy dos instrucciones mas alla if verdadero (AC==0)");
							UtGen.emitirRM("LDC", UtGen.AC, 0, UtGen.AC, "caso de falso (AC=0)");
							UtGen.emitirRM("LDA", UtGen.PC, 1, UtGen.PC, "Salto incodicional a direccion: PC+1 (es falso evito colocarlo verdadero)");
							UtGen.emitirRM("LDC", UtGen.AC, 1, UtGen.AC, "caso de verdadero (AC=1)");
							break;
                        case    mayor:  UtGen.emitirRO("SUB",UtGen.AC, UtGen.AC1, UtGen.AC, "op: >");
                                                        UtGen.emitirRM("JGT",UtGen.AC, 2, UtGen.PC, "voy dos instrucciones mas alla if verdadero (AC>=0)");
                                                        UtGen.emitirRM("LDC", UtGen.AC, 0, UtGen.AC, "caso de falso (AC < 0)");
                                                        UtGen.emitirRM("LDA", UtGen.PC, 1, UtGen.PC, "Salto incondicional a la direccion: PC+1 (es false evoto colocarlo verdadero))");
                                                        UtGen.emitirRM("LDC", UtGen.AC, 1, UtGen.AC, "caso de verdadero (AC >= 0)");
                                                        break;
			case 	diferente: 
							UtGen.emitirRO("SUB", UtGen.AC, UtGen.AC1, UtGen.AC, "op: <>");
							UtGen.emitirRM("JNE", UtGen.AC, 2, UtGen.PC, "voy dos instrucciones mas alla if verdadero (AC<>0)");
							UtGen.emitirRM("LDC", UtGen.AC, 0, UtGen.AC, "caso de falso (AC=0)");
							UtGen.emitirRM("LDA", UtGen.PC, 1, UtGen.PC, "Salto incodicional a direccion: PC+1 (es falso evito colocarlo verdadero)");
							UtGen.emitirRM("LDC", UtGen.AC, 1, UtGen.AC, "caso de verdadero (AC=1)");
							break;
			case 	menorigual:
							UtGen.emitirRO("SUB", UtGen.AC, UtGen.AC1, UtGen.AC, "op:<=");
							UtGen.emitirRM("JLE", UtGen.AC, 2, UtGen.PC, "voy dos instrucciones mas alla if verdadero (AC<=0)" );
							UtGen.emitirRM("LDC", UtGen.AC, 0, UtGen.AC, "caso de falso (AC=0)");
							UtGen.emitirRM("LDA", UtGen.PC, 1, UtGen.PC, "Salto incodicional a direccion: PC+1 (es falso evito colocarlo verdadero)");
							UtGen.emitirRM("LDC", UtGen.AC, 1, UtGen.AC, "caso de verdadero (AC=1)");
							break;


			default:
							UtGen.emitirComentario("BUG: tipo de operacion desconocida");
		}
		if(UtGen.debug)	UtGen.emitirComentario("<- Operacion: " + n.getOperacion());
	}
	
	//TODO: enviar preludio a archivo de salida, obtener antes su nombre
	private static void generarPreludioEstandar(){
		UtGen.emitirComentario("Compilacion TINY para el codigo objeto TM");
		UtGen.emitirComentario("Archivo: "+ "NOMBRE_ARREGLAR");
		/*Genero inicializaciones del preludio estandar*/
		/*Todos los registros en tiny comienzan en cero*/
		UtGen.emitirComentario("Preludio estandar:");
		UtGen.emitirRM("LD", UtGen.MP, 0, UtGen.AC, "cargar la maxima direccion desde la localidad 0");
		UtGen.emitirRM("ST", UtGen.AC, 0, UtGen.AC, "limpio el registro de la localidad 0");
	}

}
