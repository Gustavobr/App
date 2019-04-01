/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.Controller;

import br.com.Model.Contato;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.Query;

@ManagedBean(name = "contatoMB")
@ViewScoped
public class ContatoMB {

    Contato contato = new Contato();

    public Contato getContato() {
        return contato;
    }

    public void setContato(Contato contato) {
        this.contato = contato;
    }

    public void addContato() throws IOException, EntityNotFoundException, Exception {
        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("AppPU");
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(contato);
            em.getTransaction().commit();
            em.close();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Contato" + "\n" + contato.getNome() + "\n" + "cadastrado"));
            //em.detach(contato);
        } catch (EntityNotFoundException ex) {
            FacesContext.getCurrentInstance().getExternalContext().dispatch("erro.xhtml");
            throw new RuntimeException("Erro ao persistir objeto contato" + ex.getMessage().concat("ërro"));
        }
    }

    public List<Contato> listar() throws EntityNotFoundException, SQLException, Exception {
        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("AppPU");
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT c FROM Contato AS c");
            List<Contato> lista = query.getResultList();

            return lista;
        } catch (RuntimeException ex) {
            throw new RuntimeException(ex.getMessage() + "Erro ao listar a tabela populada");
        }
    }
    }
