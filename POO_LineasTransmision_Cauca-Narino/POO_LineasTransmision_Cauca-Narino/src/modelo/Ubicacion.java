
package modelo;

public class Ubicacion {
    
    private String departamento;
    private String municipio;
    private String subAreaOperativa;
    
    public Ubicacion(String departamento, String municipio, String subAreaOperativa){
        this.departamento=departamento;
        this.municipio=municipio;
        this.subAreaOperativa=subAreaOperativa;
    }
    
    //getters
    public String getDepartamento(){ return departamento; }
    public String getMunicipio(){ return municipio; }
    public String getSubAreaOperativa(){ return subAreaOperativa; }
    
    //setters
    public void setDepartamento(String departamento){ this.departamento=departamento; }
    public void setMunicipio(String municipio){ this.municipio=municipio; }
    public void setSubaAreaOperativa(String subAreaOperativa){ this.subAreaOperativa=subAreaOperativa; }
}
