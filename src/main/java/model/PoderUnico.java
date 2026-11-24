package model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "poderunico")
public class PoderUnico implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nome_poder", length = 100, nullable = false)
    private String nomePoder;

    @ManyToOne
    @JoinColumn(name = "id_protagonista", nullable = false)
    private Protagonista protagonista;

    public PoderUnico() {
    }

    public PoderUnico(String nomePoder, Protagonista protagonista) {
        this.nomePoder = nomePoder;
        this.protagonista = protagonista;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomePoder() {
        return nomePoder;
    }

    public void setNomePoder(String nomePoder) {
        this.nomePoder = nomePoder;
    }

    public Protagonista getProtagonista() {
        return protagonista;
    }

    public void setProtagonista(Protagonista protagonista) {
        this.protagonista = protagonista;
    }
}
