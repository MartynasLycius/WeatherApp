package org.weather.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import org.apache.commons.lang3.StringUtils;
import org.weather.app.config.Constants;

@Entity
@Table(name = "app_user")
public class User implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
  @SequenceGenerator(name = "sequenceGenerator")
  private Long id;

  @NotNull
  @Pattern(regexp = Constants.LOGIN_REGEX)
  @Size(min = 1, max = 50)
  @Column(length = 50, unique = true, nullable = false)
  private String login;

  @Size(max = 20)
  @Column(name = "role", length = 20)
  private String role;

  @JsonIgnore
  @NotNull
  @Size(min = 4, max = 60)
  @Column(name = "password_hash", length = 60, nullable = false)
  private String password;

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
  private List<FavouriteLocation> favouriteLocations;

  public User() {}

  public User(String login, String role, String password) {
    this.login = login;
    this.role = role;
    this.password = password;
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

  // Lowercase the login before saving it in database
  public void setLogin(String login) {
    this.login = StringUtils.lowerCase(login, Locale.ENGLISH);
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public List<FavouriteLocation> getFavouriteLocations() {
    return (favouriteLocations.isEmpty()) ? List.of() : favouriteLocations;
  }

  public void setFavouriteLocations(List<FavouriteLocation> favouriteLocations) {
    this.favouriteLocations = favouriteLocations;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof User)) {
      return false;
    }
    return id != null && id.equals(((User) o).id);
  }

  @Override
  public int hashCode() {
    return 31;
  }

  @Override
  public String toString() {
    return "User{" + "username='" + login + '\'' + ", role='" + role + '\'' + "}";
  }
}
