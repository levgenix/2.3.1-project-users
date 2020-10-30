package ru.itsinfo.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@Entity
@Table(name = "users")
public class User implements UserDetails {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;


   /** @Column @Size @Length
    * @Column is a JPA annotation and the length attribute is used by the schema generation tool to set the associated SQL column length.
    * @Size is a Bean Validation annotation that validates that the associated String has a value whose length is bounded by the minimum and maximum values.
    * @Length is a Hibernate specific annotation and has the same meaning as @Size
    * So both 2. and 3. should validate the String length using Bean Validation. I'd pick 2. because it's generic.
    * https://www.baeldung.com/jpa-size-length-column-differences */
   //TODO https://github.com/eugenp/tutorials/tree/master/persistence-modules/hibernate-mapping

   @Size(min=2, message = "Не меньше 2 знаков") // TODO
   @Column(name = "name", length = 5)
   private String firstName;

   @Column(name = "last_name")
   private String lastName;

   private String email;

   private String password;

   //@Transient //TODO
   @ManyToMany(fetch = FetchType.EAGER)
   private Set<Role> roles;

   public User() {}
   
   public User(String firstName, String lastName, String email, String password, Set<Role> roles) {
      this.firstName = firstName;
      this.lastName = lastName;
      this.email = email;
      this.password = password;
      this.roles = roles;
   }

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
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
   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   @Override
   public Collection<? extends GrantedAuthority> getAuthorities() {
      return roles;
   }

   @Override
   public String getUsername() {
      return email;
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

   public Set<Role> getRoles() {
      return roles;
   }

   public void setRoles(Set<Role> roles) {
      this.roles = roles;
   }

   @Override
   public String toString() {
      return String.format("User [id = %d; firstName = %s; lastName = %s; email = %s; password = %s; roles = (%s)]",
              id, firstName, lastName, email, password, Collections.singletonList(roles));
   }
}
