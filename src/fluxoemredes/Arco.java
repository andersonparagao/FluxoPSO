/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fluxoemredes;

import java.util.ArrayList;
/**
 *
 * @author Wellington
 */
public class Arco {
    private int[] origem = new int[2];
    private int[] destino = new int[2];

   
    public Arco(int origemUsina,int origemintervalo,int destinoUsina,int destinoIntervalo){
        origem[0] = origemUsina;
        origem[1] = origemintervalo;
        destino[0] = destinoUsina;
        destino[1] = destinoIntervalo;
    }

    public int[] getOrigem() {
        return origem;
    }

//    public void setOrigem(int origem) {
//        this.origem = origem;
//    }

    public int[] getDestino() {
        return destino;
    }
    
    @Override
    public String toString(){
        String retorno = " Origem = (" +
                        this.origem[0] + ", " +
                        this.origem[1] + "); Destino = (" +
                        this.destino[0] + ", " + 
                        this.destino[1] + ")";
        return  retorno;
    }

//    public void setDestino(int destino) {
//        this.destino = destino;
//    }

//    public ArrayList<Aresta> getSuperbasico() {
//        return superbasico;
//    }
//
//    public void setSuperbasico(ArrayList<Aresta> superbasico) {
//        this.superbasico = superbasico;
//    }
    
    
    
}
