package ru.kata.spring.boot_security.demo.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.*;

@Entity
@Table(name = "users")
public class User implements UserDetails {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "name")
   @NotEmpty(message = "Name should not be empty")
   @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
   private String firstName;

   @Column(name = "last_name")
   @NotEmpty(message = "Last name should not be empty")
   @Size(min = 2, max = 30, message = "Last name should be between 2 and 30 characters")
   private String lastName;

   @Column(name = "email")
   @NotEmpty(message = "Email should not be empty")
   @Email(message = "Email should be valid")
   private String email;

   @Column(name = "username")
   @NotEmpty(message = "Username should not be empty")
   @Size(min = 2, max = 20, message = "Last name should be between 2 and 20 characters")
   private String username;

   @Column(name = "password")
   @NotEmpty(message = "Password should not be empty")
   @Size(min = 4, message = "Password should be bore than 4 characters")
   private String password;

   @Transient
   @NotEmpty(message = "Password Confirm should not be empty")
   private String passwordConfirm;

   @ManyToMany(fetch = FetchType.EAGER)
   private Set<Role> roles = new HashSet<>();
   public User() {}
   public User(String firstName, String lastName, String email, String username, String password) {
      this.firstName = firstName;
      this.lastName = lastName;
      this.email = email;
      this.username = username;
      this.password = password;
   }
   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getFirstName() {
      return firstName;
   }

   public void setFirstName(String firstName) {
      this.firstName = firstName;
   }

   public String getLastName() {
      return lastName;
   }

   public void setLastName(String lastName) {
      this.lastName = lastName;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   @Override
   public String getUsername() {
      return username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   @Override
   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public String getPasswordConfirm() {
      return passwordConfirm;
   }

   public void setPasswordConfirm(String passwordConfirm) {
      this.passwordConfirm = passwordConfirm;
   }

   public Set<Role> getRoles() {
      return roles;
   }

   public void setRoles(Set<Role> roles) {
      this.roles = roles;
   }

   @Override
   public Collection<? extends GrantedAuthority> getAuthorities() {
      return getRoles();
   }

   @Override
   public boolean isAccountNonExpired() {
      return true;
   }

   @Override
   public boolean isAccountNonLocked() {
      return true;
   }

   @Override
   public boolean isCredentialsNonExpired() {
      return true;
   }

   @Override
   public boolean isEnabled() {
      return true;
   }

   @Override
   public String toString() {
      return "User{" +
              "id=" + id +
              ", firstName='" + firstName + '\'' +
              ", lastName='" + lastName + '\'' +
              ", email='" + email + '\'' +
              ", username='" + username + '\'' +
              ", password='" + password + '\'' +
              '}';
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      User user = (User) o;
      return Objects.equals(id, user.id) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(email, user.email) && Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(roles, user.roles);
   }

   @Override
   public int hashCode() {
      return Objects.hash(id, firstName, lastName, email, username, password, roles);
   }
}
