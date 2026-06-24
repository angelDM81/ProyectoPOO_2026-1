package controlador;

import java.util.ArrayList;
import modelo.Subestacion;
import modelo.SistemaElectrico;
import persistencia.Escritura;

public class ControladorSubestacion {
    
        private SistemaElectrico sistemaElectrico = new SistemaElectrico();
        private Escritura escritura = new Escritura();
    
    public ArrayList<Subestacion> obtenerSubestaciones(){
        return sistemaElectrico.getSubestacionesActivas();
    }
    
    public int obtenerNumeroDeSubestaciones(){
        return sistemaElectrico.getSubestacionesActivas().size();
    }

    
    //metodo creado para hallar la subestacion en base a la id, retorna la subestacion
    public Subestacion buscarSubE(String idSubE){
        
        for(Subestacion subE: obtenerSubestaciones()){
           if(subE.getID().equals(idSubE))
           {
               return subE;
           }
        }
        return null;
        
    }
    
    public void eliminarSubestacion(String idSubE){
        //con esto eliminamos del arraylist central(sistema electrico) la subesatcion con la id q se envie a este metodo
        sistemaElectrico.eliminarSubestacionActiva(buscarSubE(idSubE));
        //con el arraylist central actualizamos el csv, es decir tras haber sido borrado la subestacion, con el metodo retornamos el arraylist centrar que actualiza la lista
        escritura.actualizarArchivoDeSubestaciones(obtenerSubestaciones());
        
    }
}
