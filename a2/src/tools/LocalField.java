package tools;

public class LocalField extends Field {
    String fname;
    
    LocalField(String type, String name, String fname){
        super(type, name);
        this.fname = fname;
    }                      
}
