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
 *
 * @author new
 */
@Entity
@Table(name = "org")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Org.findAll", query = "SELECT o FROM Org o")
    , @NamedQuery(name = "Org.findById", query = "SELECT o FROM Org o WHERE o.id = :id")
    , @NamedQuery(name = "Org.findByLogin", query = "SELECT o FROM Org o WHERE o.login = :login")
    , @NamedQuery(name = "Org.findByPassword", query = "SELECT o FROM Org o WHERE o.password = :password")
    , @NamedQuery(name = "Org.findByIsProduction", query = "SELECT o FROM Org o WHERE o.isProduction = :isProduction")
    , @NamedQuery(name = "Org.findByBranchName", query = "SELECT o FROM Org o WHERE o.branchName = :branchName")
    , @NamedQuery(name = "Org.findByBranchType", query = "SELECT o FROM Org o WHERE o.branchType = :branchType")})
public class Org implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "login")
    private String login;
    @Basic(optional = false)
    @Column(name = "password")
    private String password;
    @Column(name = "isProduction")
    private Boolean isProduction;
    @Basic(optional = false)
    @Column(name = "branch_name")
    private String branchName;
    @Basic(optional = false)
    @Column(name = "branch_type")
    private String branchType;
    @JoinTable(name = "user_has_org", joinColumns = {
        @JoinColumn(name = "Org_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "User_id", referencedColumnName = "id")})
    @ManyToMany
    private List<User> userList;
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Project projectId;

    public Org() {
    }

    public Org(Long id) {
        this.id = id;
    }

    public Org(Long id, String login, String password, String branchName, String branchType) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.branchName = branchName;
        this.branchType = branchType;
    }

    public Org(String login, String password, String branchName, String branchType, Project projectId) {
        this.login = login;
        this.password = password;
        this.branchName = branchName;
        this.branchType = branchType;
        this.projectId = projectId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getIsProduction() {
        return isProduction;
    }

    public void setIsProduction(Boolean isProduction) {
        this.isProduction = isProduction;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getBranchType() {
        return branchType;
    }

    public void setBranchType(String branchType) {
        this.branchType = branchType;
    }

    @XmlTransient
    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public Project getProjectId() {
        return projectId;
    }

    public void setProjectId(Project projectId) {
        this.projectId = projectId;
    }


    @Override
    public String toString() {
        return "com.model.Org[ id=" + id + " ]";
    }
    
}
