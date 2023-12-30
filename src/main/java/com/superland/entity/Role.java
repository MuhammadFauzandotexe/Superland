package com.superland.entity;


import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name="m_roles")
public class Role implements GrantedAuthority {

    @Id
    @GenericGenerator(strategy = "uuid2", name = "system-uuid")
    @GeneratedValue(generator = "system-uuid")
    @Column(name="role_id")
    private String roleId;

    private String authority;

    public Role(){
        super();
    }

    public Role(String authority){
        this.authority = authority;
    }

    public Role(String roleId, String authority){
        this.roleId = roleId;
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        // TODO Auto-generated method stub
        return this.authority;
    }

    public void setAuthority(String authority){
        this.authority = authority;
    }

    public String getRoleId(){
        return this.roleId;
    }

    public void setRoleId(String roleId){
        this.roleId = roleId;
    }
}
