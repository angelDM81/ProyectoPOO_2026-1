
package modelo;

import java.util.ArrayList;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class Subestacion {
    
    private String id;
    private String nombre;
    private ArrayList<Double> voltajeNominalDeOperacion;
    private ArrayList<String> operadores;
    private ArrayList<LineaTransmision> lineasRelacionadas;
    private Ubicacion ubicacion;
    private double latitud;
    private double longitud;
    
    public Subestacion(String id, String nombre, ArrayList<Double> voltajeNominalDeOperacion, ArrayList<String>  operadores, Ubicacion ubicacion, double latitud, double longitud){
        this.id=id;
        this.nombre=nombre;
        this.voltajeNominalDeOperacion=voltajeNominalDeOperacion;
        this.operadores=operadores;
        this.ubicacion=ubicacion;
        this.latitud=latitud;
        this.longitud=longitud;
        this.lineasRelacionadas = new ArrayList<>();
    }
    
    public String toCSV(){
        
        //el Stream es como una banda de transportadora que lo hace es que agarra cada voltaje y lo transforma en string mediante el .map, para despues concatenarlos en un solo string mediante el collectors.joining
        //cada operador esta separado por un -
        String voltajeNominalDeOperacion = this.voltajeNominalDeOperacion.stream().map(String::valueOf).collect(Collectors.joining("-"));
        //el metodo String.join lo que hace es agrupar los operadores que estan guardados en el arraylist de operadores en una sola linea String, y a cada operador lo separa un -
        String operadores = String.join(" - ", this.operadores);
        
        //se concatenan toda la informacion de la subestacion en un solo string que va a ser retornando, siendo que cada valor esta separado por un ;
        String linea = this.id+";"+this.nombre+";"+voltajeNominalDeOperacion+";"+operadores+";"+this.ubicacion.getDepartamento()+";"+this.ubicacion.getMunicipio()+";"+this.ubicacion.getSubAreaOperativa()+";"+this.latitud+";"+this.longitud;
        
        return linea;
    }
    
    //getters
    public String getID(){ return id; }
    public String getNombre(){ return nombre; }
    public Ubicacion getUbicacion(){ return ubicacion; }
    public ArrayList<Double> getVoltajeNominalDeOperacion(){ return voltajeNominalDeOperacion; }
    public ArrayList<String> getOperadores(){ return operadores; }
    public double getLatitud(){ return latitud; }
    public double getLongitud(){ return longitud; }
    public String getLineasRelacionadasString(){ 
        
        StringJoiner joiner = new StringJoiner(" / ");
        
        for(LineaTransmision linea: lineasRelacionadas){
            if(linea!=null && linea.getInformacionBasica().getNombre()!=null){
                joiner.add(linea.getInformacionBasica().getNombre());
            }
        }
        
        String linea = joiner.toString();
                
        return linea;
    
    }
    
    public ArrayList<LineaTransmision> getLineasRelacionadas(){ return lineasRelacionadas; }
   
    
    //setters
    public void setID(String id){ this.id=id; }
    public void setNombre(String nombre){ this.nombre=nombre; }
    public void setVoltajeNominal(double voltajeNominal){ this.voltajeNominalDeOperacion.add(voltajeNominal); }
    public void setOperadores(String operador){ this.operadores.add(operador); }
    public void setLatitud(double latitud){ this.latitud=latitud; }
    public void setLongitud(double longitud){ this.longitud=longitud; }
    public void agregarLinea(LineaTransmision linea){ this.lineasRelacionadas.add(linea); }
    
}
