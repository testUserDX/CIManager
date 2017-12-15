/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import java.io.Serializable;
import java.util.ArrayList;
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

public class Org implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "orgLink")
    private String orgLink;
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

    @ManyToMany
    @JoinTable(name = "user_has_org", joinColumns = {
        @JoinColumn(name = "Org_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "User_id", referencedColumnName = "id")})
    private List<User> userList= new ArrayList<>();
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Project projectId;

    public Org() {
    }

    public Org(Long id) {
        this.id = id;
    }

    public Org(Long id, String login, String password, String branchName) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.branchName = branchName;
    }

    public Org(String login, String password, String branchName, String orgLink, Project projectId) {
        this.login = login;
        this.password = password;
        this.branchName = branchName;
        this.projectId = projectId;
        this.orgLink = orgLink;
    }

    public String getOrgLink() {
        return orgLink;
    }

    public void setOrgLink(String orgLink) {
        this.orgLink = orgLink;
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
