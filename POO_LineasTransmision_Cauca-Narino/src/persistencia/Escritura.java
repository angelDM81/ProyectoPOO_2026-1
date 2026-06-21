
package persistencia;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import modelo.LineaTransmision;
import modelo.Subestacion;

public class Escritura {
    
    public void guardarNuevaLineaDeTransmision(LineaTransmision lineaTransmision){
        
        File out = new File("data\\dataLineas.csv");
        
        //si el FileWriter recibe como segundo parametro un true, escribe al final del archivo
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(out, true))){
            
            //el bufferedwriter llama a la funcion toCSV que devuelve una linea completa con todos los valores que se necesitan para guardar una linea de transmision
            bw.write(lineaTransmision.toCSV());
            bw.newLine();
                         
            System.out.println("Linea de transmision guardaba exitosamente");
            
        }
        catch(Exception e){
            System.out.println("Error en la escritura del archivo csv: "+e.getMessage()); //getMessage() es un metodo que extrae un mensaje corto explicando la causa directa del error
            e.printStackTrace(); //muestra una lista de archivos y lineas de codigo organizados de manera inversa, que me ayudan a identificar en donde se rompio el programa, mas exactamente en la linea 1
        }
    }
    
    public void guardarNuevaSubestacion(Subestacion subestacion){
        
        File out = new File("data\\dataSubestaciones.csv");
        
        //si el FileWriter recibe como segundo parametro un true, escribe al final del archivo
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(out, true))){
            
            //el bufferedwriter llama a la funcion toCSV que devuelve una linea completa con todos los valores que se necesitan para guardar una subestacion
            bw.write(subestacion.toCSV());
            bw.newLine();
            
            System.out.println("Subestacion guardada exitosamente");
            
        }
        catch(Exception e){
            System.out.println("Error en la escritura del archivo csv: "+e.getMessage()); //getMessage() es un metodo que extrae un mensaje corto explicando la causa directa del error
            e.printStackTrace(); //muestra una lista de archivos y lineas de codigo organizados de manera inversa, que me ayudan a identificar en donde se rompio el programa, mas exactamente en la linea 1
        }
    }
    
    public void actualizarArchivoLineasDeTransmision(ArrayList<LineaTransmision> listaDeLineas){
        
        File out = new File("data\\dataLineas.csv");
        
        //si el FileWriter recibe como segundo parametro un false, sobreescribe todo el archivo
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(out, false))){
            
            //se actualiza el archivo csv en base a los registros que se tienen en el arraylist de lineasActivas de la clase sistema electrico
            //el for lo que hace es que reescribe todo el csv, borrando registros anteriores, para escribir los nuevos actualizados
            for(LineaTransmision lineaDetransmision : listaDeLineas){
            
                //el bufferedwriter llama a la funcion toCSV que devuelve una linea completa con todos los valores que se necesitan para guardar una linea de transmision
                bw.write(lineaDetransmision.toCSV());
                bw.newLine();   
            }
            
            System.out.println("Archivo csv de las lineas de transmision actualizado exitosamente");
        }
        catch(Exception e){
            System.out.println("Error en la escritura del archivo csv: "+e.getMessage()); //getMessage() es un metodo que extrae un mensaje corto explicando la causa directa del error
            e.printStackTrace(); //muestra una lista de archivos y lineas de codigo organizados de manera inversa, que me ayudan a identificar en donde se rompio el programa, mas exactamente en la linea 1
        }
    }
    
    public void actualizarArchivoDeSubestaciones(ArrayList<Subestacion> listaDeSubestaciones){
        
        File out = new File("data\\dataSubestaciones.csv");
        
        //si el FileWriter recibe como segundo parametro un false, sobreescribe todo el archivo
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(out, false))){
            
            //se actualiza el archivo csv en base a los registros que se tienen en el arraylist de subestacionesActivas de la clase sistema electrico
            //el for lo que hace es que reescribe todo el csv, borrando registros anteriores, para escribir los nuevos actualizados
            for(Subestacion subestacion: listaDeSubestaciones){
            
                //el bufferedwriter llama a la funcion toCSV que devuelve una linea completa con todos los valores que se necesitan para guardar una subestacion
                bw.write(subestacion.toCSV());
                bw.newLine();
            
            }
            
            System.out.println("Archivo csv de las subestaciones actualizado exitosamente");
        }
        catch(Exception e){
            System.out.println("Error en la escritura del archivo csv: "+e.getMessage()); //getMessage() es un metodo que extrae un mensaje corto explicando la causa directa del error
            e.printStackTrace(); //muestra una lista de archivos y lineas de codigo organizados de manera inversa, que me ayudan a identificar en donde se rompio el programa, mas exactamente en la linea 1
        }
    }
    
    
}
