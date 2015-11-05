/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Depto. de Sistemas
 */
public class Item{
    String contenido;
    Boolean noTerminal;
    Boolean prima;
    String Primero="";
    String Siguiente="";
    // true NT false T
    public Item(String s) {
        String NT="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        noTerminal = NT.contains(s);
        contenido =s;
        prima=false;
    }
    
    public Item(String s, boolean prima) {
        String NT="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        noTerminal = NT.contains(s);
        contenido =s;
        this.prima=prima;
    }
    
    public String mostrar(){
        if(prima==false){
        return contenido;}
        return contenido+"'";
    }
   
    public boolean igual(Item i){
    return contenido.equals(i.contenido)&& prima==i.prima;
    }
}
