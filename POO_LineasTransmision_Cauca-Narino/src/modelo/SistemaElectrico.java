package modelo;

import java.util.*;
import persistencia.Escritura;
import persistencia.Lectura;
import vista.GUI;

public class SistemaElectrico {
    
    private static ArrayList<LineaTransmision> lineasActivas = new ArrayList<>();
    private static ArrayList<Subestacion> subestacionesActivas = new ArrayList<>();
    private static Escritura escritura = new Escritura();
    private static Lectura lectura = new Lectura();
    
    public static void main(String[] args) {
        
        lectura.lecturaLineasDeTransmision();
        lectura.lecturaSubestaciones();
        
        SistemaElectrico sistema = new SistemaElectrico();
        for(LineaTransmision linea : lineasActivas){
            sistema.enlazarNuevaLinea(linea);
        }
        
        java.awt.EventQueue.invokeLater(() -> {
            GUI ventana = new GUI();
            ventana.setLocationRelativeTo(null); 
            ventana.setVisible(true);
        });
 
    }
    
    public void enlazarNuevaLinea(LineaTransmision nuevaLinea){
        
        //se crea una estructura tipo hashmap llamada mapasubestaciones
        //donde el valor de la llave sera el nombre de la subestacion y el valor sera el objeto subestacion 
        java.util.Map<String, Subestacion> mapaSubestaciones = new java.util.HashMap<>();
    
        //este ciclo se encarga de llenar el mapa recorriendo todas las subestaciones registradas       
        for(Subestacion subestacion : subestacionesActivas){
            //el metodo put inserta un nuevo casillero en el mapa, como llave se pasa el nombre, el trim se usa para limpiar espacio y se convierte todo a minisculas, para que quede en un mismo formato
            //y como valor se pasa la subestacion
            mapaSubestaciones.put(subestacion.getNombre().trim().toLowerCase(), subestacion);
        }

        //se extraen las subestaciones y se ponen en un vector
        String[] token = nuevaLinea.getInformacionBasica().getSubestacionesRelacionadas().split("-");
    
        //se hace un ciclo que recorra el token con las dos subestaciones
        for(String tokenNombre : token){
            String nombre = tokenNombre.trim().toLowerCase();
        
            //y si la subestación existe, le asociamos esta nueva línea
            if(mapaSubestaciones.containsKey(nombre)){
                Subestacion subestacion = mapaSubestaciones.get(nombre);
                subestacion.agregarLinea(nuevaLinea);
            }
        }
    }

    public ArrayList<LineaTransmision> getLineasActivas(){ return lineasActivas; }
    public ArrayList<Subestacion> getSubestacionesActivas(){ return subestacionesActivas; }
    public void eliminarLineaActiva(LineaTransmision lineaTransmision){ lineasActivas.remove(lineaTransmision); }
    public void eliminarSubestacionActiva(Subestacion subestacion){ subestacionesActivas.remove(subestacion); }
    public void agregarLineasActivas(LineaTransmision lineaTransmision){ lineasActivas.add(lineaTransmision); }
    public void agregarSubestacionesActivas(Subestacion subestacion){ subestacionesActivas.add(subestacion); }
    
    
}