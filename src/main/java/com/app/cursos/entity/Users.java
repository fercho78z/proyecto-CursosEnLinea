package com.app.cursos.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Users {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="user_id")
	private Integer id;
	
	@Column(length = 16)
	private String username;
	@Column(length = 100)
	private String password;
	@Column(name = "estado_enabled")
	private boolean enabled;
	
	
	
	@ManyToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	@JoinTable(
			 name="users_roles",joinColumns = @JoinColumn(name="user_id"),
			 inverseJoinColumns = @JoinColumn(name="role_id")
			)
	private Set<Roles> roles = new HashSet<>();
	
 	
	public Set<Roles> getRoles() {
		return roles;
	}
	public void setRoles(Set<Roles> roles) {
		this.roles = roles;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	
	public Users(Integer id, String username, String password, boolean enabled, Set<Roles> roles) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.roles = roles;
	}
	public Users() {
		
	}
	@Override
	public String toString() {
		return "Users [id=" + id + ", username=" + username + ", password=" + password + ", enabled=" + enabled
				+ ", roles=" + roles + "]";
	}

}
