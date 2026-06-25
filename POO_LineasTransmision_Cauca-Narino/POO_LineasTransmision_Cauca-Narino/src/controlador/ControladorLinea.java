package controlador;

import java.util.ArrayList;
import modelo.Capacidad;
import modelo.InformacionBasica;
import modelo.LineaTransmision;
import modelo.SistemaElectrico;
import modelo.Ubicacion;
import persistencia.Escritura;

public class ControladorLinea {
    
    private SistemaElectrico sistemaElectrico = new SistemaElectrico();
    private Escritura escritura = new Escritura();
    
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
    
    public LineaTransmision buscarLinea(String idBuscar){
        
        //se hace un for para buscar la linea de transmision en base a la id
        for(LineaTransmision linea: obtenerLineaTransmision()){
            if(linea.getInformacionBasica().getID().equals(idBuscar)){
                return linea;
            }
        }
        
        return null;
    }
    
    public void borrarLinea(String idBuscar){
        
        //se borra la linea de transmision del arrayList general del sistema electrico
        sistemaElectrico.eliminarLineaActiva(buscarLinea(idBuscar));
        //se reescribe el csv en base al arrayList anteriormente actualizado
        escritura.actualizarArchivoLineasDeTransmision(obtenerLineaTransmision());
        
    }
    
    public void crearLinea(InformacionBasica informacionBasica, Capacidad capacidad, Ubicacion ubicacion){
        
        LineaTransmision lineaTransmision = new LineaTransmision(informacionBasica, capacidad, ubicacion);
        escritura.guardarNuevaLineaDeTransmision(lineaTransmision);
        sistemaElectrico.agregarLineasActivas(lineaTransmision);
        sistemaElectrico.enlazarNuevaLinea(lineaTransmision);
        
    }
}
