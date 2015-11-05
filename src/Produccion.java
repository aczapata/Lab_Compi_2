
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Depto. de Sistemas
 */
public class Produccion {
    Item simbolo;
    ArrayList<Item> prod= new ArrayList<>();
    public Produccion(String s) {
        String[] v= s.split("->");
        simbolo=new Item(v[0]);
        for (int i = 0; i < v[1].length(); i++) {
            prod.add(new Item(""+v[1].charAt(i)));
        }
    }
    public Produccion(Item s, ArrayList<Item> prod) {
        simbolo=s;
        this.prod=prod;
    }
    public Produccion(Item p, String s) {
        simbolo=p;
        for (int i = 0; i < s.length(); i++) {
            prod.add(new Item(""+s.charAt(i)));
        }
    }
    public String mostrarProd(){
        String s="";
        for (int i = 0; i < prod.size(); i++) {
            s+=prod.get(i).mostrar();
        }
       return s;
   }
   public String mostrar(){
       return simbolo.mostrar()+"->"+mostrarProd();
   }
}
