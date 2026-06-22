package controlador;

import java.util.ArrayList;
import modelo.LineaTransmision;
import modelo.SistemaElectrico;

public class ControladorLinea {
    
    private SistemaElectrico sistemaElectrico = new SistemaElectrico();
    
    public ArrayList<LineaTransmision> obtenerLineaTransmision(){
        return sistemaElectrico.getLineasActivas();
    }
    
    public int obtenerNumeroDeLineas(){
        return sistemaElectrico.getLineasActivas().size();
    }
    
    //recibe como parametro la tabla de lineas de transmision
    public double calcularCapacidadTotal(javax.swing.JTable tabla){
        
        //se extrae el arraylist de las lineas activas para poder calcular la capacidad en MW de cada una
        ArrayList<LineaTransmision> lineasActivas = obtenerLineaTransmision();
        double capacidadTotal=0;
        
        //se usa getRowCount para saber la cantidad de filas que se estan mostrando en el momento y asi definir el limite del for
        for(int i=0; i<tabla.getRowCount(); i++){
            //se usa el convertRowIndexToModel(i) para acceder al indice que tiene la linea de transmision en el arraylist de lineasActivas, ya que en la tabla pueden tener un indice diferente al que tienen en memoria
            int indiceReal=tabla.convertRowIndexToModel(i);
            capacidadTotal+=lineasActivas.get(indiceReal).getCapacidad().calcularCapacidadMW();
        }
        
        return capacidadTotal;
    }
    //recibe como parametro la tabla de lineas de transmision
    public double calcularLongitudTotal(javax.swing.JTable tabla){
        
        //se extrae el arraylist de las lineas activas para poder extraer la longitud de cada una (km)
        ArrayList<LineaTransmision> lineasActivas = obtenerLineaTransmision();
        double longitudTotal=0;
        
        //se usa getRowCount para saber la cantidad de filas que se estan mostrando en el momento y asi definir el limite del for
        for(int i=0; i<tabla.getRowCount(); i++){
            //se usa el convertRowIndexToModel(i) para acceder al indice que tiene la linea de transmision en el arraylist de lineasActivas, ya que en la tabla pueden tener un indice diferente al que tienen en memoria
            int indiceReal=tabla.convertRowIndexToModel(i);
            longitudTotal+=lineasActivas.get(indiceReal).getCapacidad().getLongitudTotal();
        }
     
        return longitudTotal;
    }
}
