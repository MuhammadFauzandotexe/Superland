package com.superland.entity;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="m_user_credentials")
public class UserCredentials implements UserDetails{

	@Id
	@GenericGenerator(strategy = "uuid2", name = "system-uuid")
	@GeneratedValue(generator = "system-uuid")
    private String userId;
	@Column(unique=true)
    private String username;
    private String password;
	private Boolean isActive;
	private String tokenVerification;
	@OneToOne(fetch = FetchType.EAGER, mappedBy = "credential")
	private UserAccounts userAccounts;

	public String getTokenVerification() {
		return tokenVerification;
	}

	public void setTokenVerification(String tokenVerification) {
		this.tokenVerification = tokenVerification;
	}

    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(
        name="user_role_junction",
        joinColumns = {@JoinColumn(name="user_id")},
        inverseJoinColumns = {@JoinColumn(name="role_id")}
    )
    private Set<Role> authorities;

    public UserCredentials() {
		super();
		authorities = new HashSet<>();
	}

	public UserCredentials(String username, String password, Set<Role> authorities) {
		super();
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.authorities = authorities;
	}
	public UserCredentials(String username, String password, Set<Role> authorities, Boolean isActive, String tokenVerification ) {
		super();
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.authorities = authorities;
		this.isActive = isActive;
		this.tokenVerification = tokenVerification;
	}
    public String getUserId() {
		return this.userId;
	}
	
	public void setId(String userId) {
		this.userId = userId;
	}
	
	public void setAuthorities(Set<Role> authorities) {
		this.authorities = authorities;
	}
	public void setActive(Boolean isActive){
		this.isActive = isActive;
	}

	public boolean getABoolean(){
		return this.isActive;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return this.authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	/* If you want account locking capabilities create variables and ways to set them for the methods below */
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return this.isActive;
	}
}
