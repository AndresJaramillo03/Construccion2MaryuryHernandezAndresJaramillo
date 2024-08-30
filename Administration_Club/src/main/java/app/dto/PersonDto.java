package app.dto;

public class PersonDto {
    private long id;
    private long cedula;
    private String name;
    private long cellPhone;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCedula() {
        return cedula;
    }

    public void setCedula(long cedula) {
        this.cedula = cedula;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCelPhone() {
        return cellPhone;
    }

    public void setCelPhone(long celPhone) {
        this.cellPhone = celPhone;
    }
    
    
    
}