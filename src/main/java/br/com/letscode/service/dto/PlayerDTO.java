package br.com.letscode.service.dto;

import br.com.letscode.domain.Player;

/**
 * A DTO representing a user, with only the public attributes.
 */
public class PlayerDTO {

    private Long id;

    private String login;

    public PlayerDTO() {
        // Empty constructor needed for Jackson.
    }

    public PlayerDTO(Player user) {
        this.id = user.getId();
        // Customize it here if you need, or not, firstName/lastName/etc
        this.login = user.getLogin();
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

    // prettier-ignore
    @Override
    public String toString() {
        return "UserDTO{" +
            "id='" + id + '\'' +
            ", login='" + login + '\'' +
            "}";
    }
}
