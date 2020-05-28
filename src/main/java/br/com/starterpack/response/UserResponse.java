package br.com.starterpack.response;

import br.com.starterpack.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;

@Data
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class UserResponse {

    private String id;

    private String name;

    private String username;

    private String email;

    private String image;

    private List<String> roles;

    public static UserResponse toJson(User user) {
        return new UserResponse(user.getId(), user.getName()
                ,user.getUsername(), user.getEmail(), user.getImage() ,user.getRoles());
    }

    public static List<UserResponse> toJson(List<User> users) {
        return users.stream().map(user -> UserResponse.toJson(user)).collect(Collectors.toList());
    }
}
