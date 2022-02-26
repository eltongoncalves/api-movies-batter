package br.com.letscode.service.mapper;

import br.com.letscode.domain.Authority;
import br.com.letscode.domain.Player;
import br.com.letscode.service.dto.AdminUserDTO;
import br.com.letscode.service.dto.PlayerDTO;
import java.util.*;
import java.util.stream.Collectors;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Service;

/**
 * Mapper for the entity {@link Player} and its DTO called {@link PlayerDTO}.
 *
 * Normal mappers are generated using MapStruct, this one is hand-coded as MapStruct
 * support is still in beta, and requires a manual step with an IDE.
 */
@Service
public class UserMapper {

    public List<PlayerDTO> usersToUserDTOs(List<Player> users) {
        return users.stream().filter(Objects::nonNull).map(this::userToUserDTO).collect(Collectors.toList());
    }

    public PlayerDTO userToUserDTO(Player user) {
        return new PlayerDTO(user);
    }

    public List<AdminUserDTO> usersToAdminUserDTOs(List<Player> users) {
        return users.stream().filter(Objects::nonNull).map(this::userToAdminUserDTO).collect(Collectors.toList());
    }

    public AdminUserDTO userToAdminUserDTO(Player user) {
        return new AdminUserDTO(user);
    }

    public List<Player> userDTOsToUsers(List<AdminUserDTO> userDTOs) {
        return userDTOs.stream().filter(Objects::nonNull).map(this::userDTOToUser).collect(Collectors.toList());
    }

    public Player userDTOToUser(AdminUserDTO userDTO) {
        if (userDTO == null) {
            return null;
        } else {
            Player user = new Player();
            user.setId(userDTO.getId());
            user.setLogin(userDTO.getLogin());
            user.setFirstName(userDTO.getFirstName());
            user.setLastName(userDTO.getLastName());
            user.setEmail(userDTO.getEmail());
            user.setImageUrl(userDTO.getImageUrl());
            user.setActivated(userDTO.isActivated());
            user.setLangKey(userDTO.getLangKey());
            Set<Authority> authorities = this.authoritiesFromStrings(userDTO.getAuthorities());
            user.setAuthorities(authorities);
            return user;
        }
    }

    private Set<Authority> authoritiesFromStrings(Set<String> authoritiesAsString) {
        Set<Authority> authorities = new HashSet<>();

        if (authoritiesAsString != null) {
            authorities =
                authoritiesAsString
                    .stream()
                    .map(string -> {
                        Authority auth = new Authority();
                        auth.setName(string);
                        return auth;
                    })
                    .collect(Collectors.toSet());
        }

        return authorities;
    }

    public Player userFromId(Long id) {
        if (id == null) {
            return null;
        }
        Player user = new Player();
        user.setId(id);
        return user;
    }

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    public PlayerDTO toDtoId(Player user) {
        if (user == null) {
            return null;
        }
        PlayerDTO userDto = new PlayerDTO();
        userDto.setId(user.getId());
        return userDto;
    }

    @Named("idSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    public Set<PlayerDTO> toDtoIdSet(Set<Player> users) {
        if (users == null) {
            return Collections.emptySet();
        }

        Set<PlayerDTO> userSet = new HashSet<>();
        for (Player userEntity : users) {
            userSet.add(this.toDtoId(userEntity));
        }

        return userSet;
    }

    @Named("login")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    public PlayerDTO toDtoLogin(Player user) {
        if (user == null) {
            return null;
        }
        PlayerDTO userDto = new PlayerDTO();
        userDto.setId(user.getId());
        userDto.setLogin(user.getLogin());
        return userDto;
    }

    @Named("loginSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    public Set<PlayerDTO> toDtoLoginSet(Set<Player> users) {
        if (users == null) {
            return Collections.emptySet();
        }

        Set<PlayerDTO> userSet = new HashSet<>();
        for (Player userEntity : users) {
            userSet.add(this.toDtoLogin(userEntity));
        }

        return userSet;
    }
}
