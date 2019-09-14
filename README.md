<p align="center">
  <img src="unet.png"/>
</p>

# Compiladores e Interpretes
## Compilador de lenguaje pascal reducido "pascalito"

Compilador generador de codigo para maquina virtual tiny, el cual contará con las siguientes especificaciones:

## Lenguaje

* El código puede contener comentarios de una o varias líneas, representado mediante los símbolos **{** *Comentario(s) de una o mas lineas* **}**.
* Solo se permite la declaración de **Variables Simples** y **Vectores**.
* Las variables declaradas serán de dos tipos: **Booleanas** o **Enteras**.
* Las estructuras cíclicas se reducen a instrucciones **For**, **Repeat Until**, **While**.
* Están permitidas las expresiones de asignación (**:=**) y los operadores matemáticos de suma (**+**), resta (**-**), multiplicación (**\***), módulo (**mod**) y división (**/**).
* Las operaciones aritméticas producen como resultado un valor entero.
* Las operaciones relacionales son: menor (**<**), menor o igual (**<=**), mayor (**>**), mayor o igual (**>=**), igual (**=**) y diferente (**<>**).
* Los operadores lógicos son los de conjunción lógica (**AND**), disyunción lógica (**OR**) y Negación Lógica (**NOT**).
* Las operaciones lógicas y relacionales producen como resultado un valor booleano.
* Las estructuras condicionales se reducen a condicionales simples **if then else** simples o anidados.
* El uso de vectores se permite en todas las expresiones, sean aritméticas, de asignación o relacionales, siendo su índice no limitado a variables o valores constantes sino por el contrario aceptando expresiones como vector[i*2+1].
* Todas las variables declaradas tienen ámbito global.
* Se pueden definir funciones, pero las mismas no reciben ni retornan valores debido a que todas las variables son globales.

## Verificaciones Semánticas

* No permitir el uso de variables no declaradas en programas o su uso de forma incorrecta, por ejemplo usar una variable simple como vector o viceversa.
* Permitir solo expresiones lógicas en las sentencia if, definiendo como expresión lógica aquella que puede devolver el valor verdadero o falso.
* Si se hace el llamado a una función esta debe existir.
* ste código debe ser llevado a cabo de forma manual en java como un recorrido especial extra al AST luego de su construcción y no mediante añadidos al código de CUP generado (analizador sintáctico tal cual y como esta hecho en CUP).

## Desarrolladores
* [Adriana Delgado (@adrianaedc)](https://gitlab.com/adrianaedc)
* [David Chacón (@DavidL28D)](https://github.com/DavidL28D)
* [Edgardo Zambrano (@Eddie125)](https://gitlab.com/Eddie125)
* [Yeison Fuentes (@Yeisonryhn)](https://gitlab.com/Yeisonryhn)

*Proyecto desarrollado con propositos educativos para la **Universidad Nacional Experimental del Táchira***