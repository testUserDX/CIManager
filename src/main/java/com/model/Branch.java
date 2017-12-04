/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author new
 */
@Entity
@Table(name = "branch")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Branch.findAll", query = "SELECT b FROM Branch b")
    , @NamedQuery(name = "Branch.findById", query = "SELECT b FROM Branch b WHERE b.id = :id")
    , @NamedQuery(name = "Branch.findByType", query = "SELECT b FROM Branch b WHERE b.type = :type")
    , @NamedQuery(name = "Branch.findByName", query = "SELECT b FROM Branch b WHERE b.name = :name")})
public class Branch implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "type")
    private short type;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "branchid")
    private Set<Org> orgSet;

    public Branch() {
    }

    public Branch(Long id) {
        this.id = id;
    }

    public Branch(Long id, short type, String name) {
        this.id = id;
        this.type = type;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public short getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlTransient
    public Set<Org> getOrgSet() {
        return orgSet;
    }

    public void setOrgSet(Set<Org> orgSet) {
        this.orgSet = orgSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Branch)) {
            return false;
        }
        Branch other = (Branch) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.mavenproject1.Branch[ id=" + id + " ]";
    }
    
}
