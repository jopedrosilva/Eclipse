package model;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.swing.AbstractAction;

@Entity
@NamedQueries(value = { @NamedQuery(name = "Usuario.findByEmailSenha",
query = "SELECT c FROM Usuario c "
                   + "WHERE c.email = :email AND c.senha = :senha")})
@Table(name = "User")
public class User {

	@Transient
    public static final String FIND_BY_EMAIL_SENHA = "Usuario.findByEmailSenha";       

    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    private Integer id;
     

    @Column
    private String nome;

    @Column(unique = true)
    private String email;

    @Column
    private String senha;

    @Column(name = "data_cadastro")
    @Temporal(TemporalType.DATE)
    private Date dataCadastro;

    public Integer getId() {
              return id;
    }

    public void setId(Integer id) {
              this.id = id;
    }
     
    public String getNome() {
              return nome;
    }

    public void setNome(String nome) {
              this.nome = nome.trim();
    }
     
    public String getEmail() {
              return email;
    }

    public void setEmail(String email) {
              this.email = email.trim().toLowerCase();
    }

    public String getSenha() {
              return senha;
    }

    public void setSenha(String senha) {
              this.senha = senha.trim();
    }

    public Date getDataCadastro() {
              return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
              this.dataCadastro = dataCadastro;
    }

    @Override
    public int hashCode() {
              final int prime = 31;
              int result = 1;
              result = prime * result + ((id == null) ? 0 : id.hashCode());
              return result;
    }

    @Override
    public boolean equals(Object obj) {
              if (this == obj)
                       return true;
              if (obj == null)
                       return false;
              if (getClass() != obj.getClass())
                       return false;
               
              return (obj instanceof AbstractAction) ? (this.getId() == null ? this == obj : this.getId().equals(((AbstractAction)obj).getValue("id"))):false;
    }
     
}