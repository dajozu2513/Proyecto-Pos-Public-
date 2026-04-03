package pos.logic;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlID;
import jakarta.xml.bind.annotation.XmlElement;

import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Categoria {
    @XmlID
    String id;
    @XmlElement
    String nombre;

    public Categoria() {
        this("","");
    }

    public Categoria(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Categoria categoria = (Categoria) o;
        return Objects.equals(id, categoria.id);
    }

    @Override
    public int hashCode() {return Objects.hash(id);}

    @Override
    public String toString() {
        return "CAT-"+id+" "+nombre;
    }

}
