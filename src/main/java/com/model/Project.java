/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @author new
 */
@Entity
@Table(name = "project")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Project.findAll", query = "SELECT p FROM Project p")
        , @NamedQuery(name = "Project.findById", query = "SELECT p FROM Project p WHERE p.id = :id")
        , @NamedQuery(name = "Project.findByName", query = "SELECT p FROM Project p WHERE p.name = :name")
        , @NamedQuery(name = "Project.findByGitUrl", query = "SELECT p FROM Project p WHERE p.gitUrl = :gitUrl")
        , @NamedQuery(name = "Project.findByGitLogin", query = "SELECT p FROM Project p WHERE p.gitLogin = :gitLogin")
        , @NamedQuery(name = "Project.findByGitPasword", query = "SELECT p FROM Project p WHERE p.gitPasword = :gitPasword")
        , @NamedQuery(name = "Project.findByDescription", query = "SELECT p FROM Project p WHERE p.description = :description")})
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "git_url")
    private String gitUrl;
    @Column(name = "git_login")
    private String gitLogin;
    @Column(name = "git_pasword")
    private String gitPasword;
    @Column(name = "description")
    private String description;
    @Column(name = "folder")
    private String folder;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "projectId", fetch = FetchType.EAGER)
    private List<Org> orgList;

    public Project() {
    }

    public Project(Long id) {
        this.id = id;
    }

    public Project(String name, String gitUrl, String gitLogin, String gitPasword, String folder) {
        this.name = name;
        this.gitUrl = gitUrl;
        this.gitLogin = gitLogin;
        this.gitPasword = gitPasword;
        this.folder = folder;
    }

    public Project(String name, String gitUrl, String gitLogin, String gitPasword, String folder, String description) {
        this.name = name;
        this.gitUrl = gitUrl;
        this.gitLogin = gitLogin;
        this.gitPasword = gitPasword;
        this.description = description;
        this.folder = folder;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGitUrl() {
        return gitUrl;
    }

    public void setGitUrl(String gitUrl) {
        this.gitUrl = gitUrl;
    }

    public String getGitLogin() {
        return gitLogin;
    }

    public void setGitLogin(String gitLogin) {
        this.gitLogin = gitLogin;
    }

    public String getGitPasword() {
        return gitPasword;
    }

    public void setGitPasword(String gitPasword) {
        this.gitPasword = gitPasword;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlTransient
    public List<Org> getOrgList() {
        return orgList;
    }

    public void setOrgList(List<Org> orgList) {
        this.orgList = orgList;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
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
        if (!(object instanceof Project)) {
            return false;
        }
        Project other = (Project) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.model.Project[ id=" + id + " ]";
    }

}
