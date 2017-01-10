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
public class Ciclo {
   ArrayList<Arco> arestasbasicas= new ArrayList();
   double passoMaximo;
   
   Arco superbasico;
   ArrayList<Arco> trilhadestino = new ArrayList();
   ArrayList<Arco> trilhaorigem;
   public Ciclo(Arco superbasico){
        this.trilhaorigem = new ArrayList();
       this.superbasico=superbasico;
   }
   
   
   //verificar se a matriz rede não esta sendo utilizada
    public void trilhaDestinoOrigem(int[][] MIVB,int[] usinasJusantes){
        Arco arcoInicio1 = null;                // referente a trilha de destino
        Arco arcoInicio2 = null;                // referente a trilha de origem
        int numIntervalo = MIVB[0].length;      // quantidade de períodos
        int numUsina = MIVB.length;             // quantidade de usinas
        
        //arco inicial da trilha destino
        int usinadestino = superbasico.getDestino()[0];
        int intervalodestino = superbasico.getDestino()[1];
    
        if((intervalodestino<numIntervalo)&&(usinadestino<numUsina)){
            // caso o arco de destino seja um arco de defluência
            if(MIVB[usinadestino][intervalodestino]==1){
                arcoInicio1 = new Arco(usinadestino, intervalodestino, usinasJusantes[usinadestino], intervalodestino);
                usinadestino = usinasJusantes[usinadestino];
            
            // caso o arco de destino seja um arco de volume
            } else{ 
                arcoInicio1 = new Arco(usinadestino, intervalodestino, usinadestino, intervalodestino+1);
                intervalodestino++;
            } 
        }
        
        //arco inicial da trilha origem
        int usinaorigem = superbasico.getOrigem()[0];
        int intervaloOrigem = superbasico.getOrigem()[1];
        
        if((intervaloOrigem<numIntervalo)&&(usinaorigem<numUsina)){
            // caso o arco de origem seja um arco de defluência
            if(MIVB[usinaorigem][intervaloOrigem]==1){
                arcoInicio2 = new Arco(usinaorigem, intervaloOrigem, usinasJusantes[usinaorigem], intervaloOrigem);
                usinaorigem = usinasJusantes[usinaorigem];
                
            //caso o arco de origem seja um arco de volume    
            } else{
                arcoInicio2 = new Arco(usinaorigem, intervaloOrigem, usinaorigem, intervaloOrigem+1);
                intervaloOrigem++;
            } 
        }
        
        boolean teste2 = true;      // teste para o while da trilha de destino
        boolean isrepetido = false; //caso o nó seja repetido 
        boolean teste3 = true;      // teste para o while da trilha de origem
        boolean sumidouroTrilhaDestino = false;  // teste para verificar se o nó sumidouro foi encontrado na trilha de destino
        boolean sumidouroTrilhaOrigem = false;   // teste para verificar se o nó sumidouro foi encontrado na trilha de origem
        
        while(true){
            //trilha origem
            while((teste3)&&(intervaloOrigem<=numIntervalo)&&(usinaorigem<=numUsina)){
                if(arcoInicio2!=null){
                    trilhaorigem.add(arcoInicio2);
                    if(MIVB[arcoInicio2.getOrigem()[0]][arcoInicio2.getOrigem()[1]]==1)
                       teste3=false;
                    isrepetido = VerificaRepetido(arcoInicio2,trilhadestino);
                }
                if(isrepetido)        
                    break;
                if((intervaloOrigem<numIntervalo)&&(usinaorigem<numUsina)){
                    if(MIVB[usinaorigem][intervaloOrigem]==1){
                        arcoInicio2 = new Arco(usinaorigem, intervaloOrigem, usinasJusantes[usinaorigem], intervaloOrigem);
                        usinaorigem = usinasJusantes[usinaorigem];
                    }else{
                        arcoInicio2 = new Arco(usinaorigem,intervaloOrigem,usinaorigem,intervaloOrigem+1);
                        intervaloOrigem++;
                    }
                } else{
                    sumidouroTrilhaOrigem = true;
                    if(usinaorigem>= numUsina){
                        usinaorigem++;
                    }else{
                        intervaloOrigem++;
                    }
                }
            }
            
            teste3 = true;
            if(isrepetido){
                break;
            }
            
            //trilha destino
            while((teste2)&&(intervalodestino<=numIntervalo)&&(usinadestino<=numUsina)){
                if(arcoInicio1!=null){
                    trilhadestino.add(arcoInicio1);
                    if(MIVB[arcoInicio1.getOrigem()[0]][arcoInicio1.getOrigem()[1]]==1)
                       teste2=false;
                    isrepetido = VerificaRepetido(arcoInicio1,trilhaorigem);
                }
                if(isrepetido)        
                    break;
                if((intervalodestino<numIntervalo)&&(usinadestino<numUsina)){
                    if(MIVB[usinadestino][intervalodestino]==1){
                        arcoInicio1 = new Arco(usinadestino, intervalodestino, usinasJusantes[usinadestino], intervalodestino);
                        usinadestino = usinasJusantes[usinadestino];
                    }else{
                        arcoInicio1 = new Arco(usinadestino,intervalodestino,usinadestino,intervalodestino+1);
                        intervalodestino++;
                    }
                }else{
                    sumidouroTrilhaDestino = true;
                    if(usinadestino>= numUsina){
                        usinadestino++;
                    }else{
                        intervalodestino++;
                    }
                }
               
            }
          
            if(isrepetido||(sumidouroTrilhaDestino && sumidouroTrilhaOrigem)){
                break;
            }
            teste2=true;
        }
        
        for(int i=0;i<trilhadestino.size();i++){
            arestasbasicas.add(trilhadestino.get(i));
        }
        for(int i=(trilhaorigem.size()-1);i>=0;i--){
            arestasbasicas.add(trilhaorigem.get(i));
        }
   }
   
   public boolean isRepetido(ArrayList<Arco> basicos, Arco arco){
        for (Arco basico : basicos) {
            if(basico.getOrigem()[0]==arco.getOrigem()[0]&&basico.getOrigem()[1]==arco.getOrigem()[1]
               &&basico.getDestino()[0]==arco.getDestino()[0]&&basico.getDestino()[1]==arco.getDestino()[1]){
                return true;
            }
        }
        
        return false;
    }
   
   
   public boolean VerificaRepetido(Arco arcoinserido,ArrayList<Arco> trilha){
       boolean isrepetido=false;
       int indice=0;
       for(int i=0;i<trilha.size();i++){
           if((trilha.get(i).getDestino()[0]== arcoinserido.getDestino()[0])&&(trilha.get(i).getDestino()[1]==arcoinserido.getDestino()[1])){
               indice=i;
               isrepetido=true;
               break;
           }
       } 
       
       if(isrepetido){
           int j=trilha.size()-1;
           while(trilha.size()!=(indice+1)){
               trilha.remove(j);
               j--;
           }
       }
       
       return isrepetido;
    }
   
    public ArrayList<Arco> getArestasbasicas() {
        return arestasbasicas;
    }

    public void setArestasbasicas(ArrayList<Arco> arestasbasicas) {
        this.arestasbasicas = arestasbasicas;
    }

    public Arco getSuperbasico() {
        return superbasico;
    }

    public void setSuperbasico(Arco superbasico) {
        this.superbasico = superbasico;
    }

    public ArrayList<Arco> getTrilhadestino() {
        return trilhadestino;
    }

//    public void setTrilhadestino(ArrayList<Integer[]> trilhadestino) {
//        this.trilhadestino = trilhadestino;
//    }

    public ArrayList<Arco> getTrilhaorigem() {
        return trilhaorigem;
    }

//    public void setTrilhaorigem(ArrayList<Integer[]> trilhaorigem) {
//        this.trilhaorigem = trilhaorigem;
//    }
    public double getPassoMaximo() {
        return passoMaximo;
    }

    public void setPassoMaximo(double passoMaximo) {
        this.passoMaximo = passoMaximo;
    }
    
  
}
