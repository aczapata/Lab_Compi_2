
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
public class Gramatica {

    ArrayList<Produccion> P = new ArrayList();
    ArrayList<Item> NT = new ArrayList();
    ArrayList<String> Siguientes = new ArrayList();

    public void agregarProd(Produccion p) {
        P.add(p);
        int i = 0;
        boolean sw = false;
        if (NT.isEmpty()) {
            NT.add(p.simbolo);
        } else {
            while (sw == false && i < NT.size()) {
                if (NT.get(i).igual(p.simbolo)) {
                    sw = true;
                } else {
                    i++;
                }
            }
            if (sw == false) {
                NT.add(p.simbolo);
            }
        }
    }

    public void mostrarG() {
        for (int i = 0; i < P.size(); i++) {
            System.out.println("" + P.get(i).mostrar());
        }
    }

    public void Primeros() {
        for (int i = 0; i < NT.size(); i++) {
            ArrayList<Produccion> NTi = encontrarNT(NT.get(i));
            String s = "";
            for (int j = 0; j < NTi.size(); j++) {
                s += primerosinEpsilon(NTi.get(j), i, 0);
            }
            NT.get(i).Primero = s;
            System.out.println("" + NT.get(i).mostrar() + ": " + NT.get(i).Primero);
        }
    }

    public void Siguientes() {
        System.out.println("Los siguientes");
        NT.get(0).Siguiente = "$";
        for (int i = 0; i < P.size(); i++) {
            regla1(P.get(i));
            regla2(P.get(i));  
        }
        for (int i = 0; i < NT.size(); i++) {
            NT.get(i).Siguiente=NT.get(i).Siguiente.replaceAll(NT.get(i).mostrar(), "");
            System.out.println(NT.get(i).mostrar()+":"+NT.get(i).Siguiente);
        }
    }

    public void regla1(Produccion p) {
        int i = 0;
        do{
        boolean sw = false;
        while (sw == false && i< p.prod.size()) {
            if (!p.prod.get(i).noTerminal) {
                i++;
            } else {
                sw = true;
            }
        }
        if (sw) {
            Item B = encontrarIT(p.prod.get(i));
            Item beta= p.prod.get(i+1);
            if (beta.noTerminal) {
                B.Siguiente += encontrarIT(beta).Primero;
                
            } else if (!beta.contenido.equals("&")) {
                B.Siguiente += beta.contenido;
                
            }
            i++;
        }
        }while(i<p.prod.size()-1);
       
    }
    
    public void regla2(Produccion p) {
        
        int i = 0;
        do{
        boolean sw = false;
        while (sw == false && i< p.prod.size()) {
            if (!p.prod.get(i).noTerminal) {
                i++;
            } else if (i==p.prod.size()-1){sw = true;}
            else if(p.prod.get(i + 1).Primero.contains("&")){
                   sw=true;           
            }
            else {i++;}
        }
        if (sw) {
            Item B = encontrarIT(p.prod.get(i));
            Item A = encontrarIT(p.simbolo);
            B.Siguiente+=A.mostrar();
            
            i++;
        }
        }while(i<p.prod.size()); 
    }

    public String primerosinEpsilon(Produccion p, int i, int cont) {

        if (p.prod.get(0).noTerminal) {
            String s, s1 = "";
            do {
                if (p.prod.get(cont).noTerminal) {
                    ArrayList<Produccion> NTi = encontrarNT(p.prod.get(cont));

                    s = "";
                    for (int j = 0; j < NTi.size(); j++) {
                        s += primerosinEpsilon(NTi.get(j), i, cont);
                    }
                    if (s1.contains("&")) {
                        s1 = s1.replace("&", s);
                    } else {
                        s1 += s;
                    }
                    cont++;
                } else {
                    s = p.prod.get(cont).contenido;
                    if (s1.contains("&")) {
                        s1 = s1.replace("&", s);
                    } else {
                        s1 += s;
                    }
                }
            } while (s.contains("&") && cont < p.prod.size());
            return s1;
        } else {
            return p.prod.get(0).contenido;
        }
    }

    public void factorizar() {

    }

    public void hallarfactorcomun(ArrayList<Produccion> NT) {

        ArrayList<Produccion> NTo = new ArrayList();
        NTo.add(NT.get(0));

    }

    public Gramatica recAIzq() {
        Gramatica G1 = new Gramatica();
        for (int j = 0; j < NT.size(); j++) {
            Item nt = NT.get(j);
            //Todas las producciones con el Simbolo NT
            ArrayList<Produccion> NTi = encontrarNT(nt);
            //Todas las producciones con el Simbolo NT y CON REC
            ArrayList<Produccion> NTa = new ArrayList<>();
            //Todas las producciones con el Simbolo NT y SIN REC
            ArrayList<Produccion> NTb = new ArrayList<>();
            for (int i = 0; i < NTi.size(); i++) {
                if (NTi.get(i).prod.get(0).igual(nt)) {
                    NTa.add(NTi.get(i));
                } else {
                    NTb.add(NTi.get(i));
                }
            }
            if (!NTa.isEmpty()) {
                Item nt1 = new Item(nt.contenido, true);
                if (NTb.isEmpty()) {
                    ArrayList<Item> cont = new ArrayList();
                    cont.add(nt1);
                    Produccion p = new Produccion(nt, cont);
                    G1.agregarProd(p);
                } else {
                    for (int i = 0; i < NTb.size(); i++) {
                        ArrayList<Item> cont = NTb.get(i).prod;
                        cont.add(nt1);
                        Produccion p = new Produccion(nt, cont);
                        G1.agregarProd(p);
                    }

                }
                for (int i = 0; i < NTa.size(); i++) {
                    ArrayList<Item> cont = NTa.get(i).prod;
                    cont.remove(0);
                    cont.add(nt1);
                    Produccion p = new Produccion(nt1, cont);
                    G1.agregarProd(p);
                }
                G1.agregarProd(new Produccion(nt1, "&"));
            } else {
                for (int i = 0; i < NTb.size(); i++) {
                    G1.agregarProd(NTb.get(i));
                }
            }
        }
        G1.mostrarG();
        return G1;
    }

    public ArrayList<Produccion> encontrarNT(Item item) {
        ArrayList<Produccion> NTi = new ArrayList<>();
        for (int i = 0; i < P.size(); i++) {
            if (P.get(i).simbolo.igual(item)) {
                NTi.add((P.get(i)));
            }
        }
        return NTi;
    }
    
    public Item encontrarIT(Item item) {
        int i=0;
        while(i<NT.size()){
            if (item.igual(NT.get(i))){
                    return NT.get(i);}
            else{i++;}
            
        }
       return null;
    }
    
}
