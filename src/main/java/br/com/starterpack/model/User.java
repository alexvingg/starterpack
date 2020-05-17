package br.com.starterpack.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

@Document("user")
@Getter
@Setter
public class User extends AbstractModel {

    private String name;

    private String email;

    private String password;

    public static User currentUser() {
        return (User) RequestContextHolder.getRequestAttributes().getAttribute("user", RequestAttributes.SCOPE_REQUEST);
    }

    public static void setCurrentUser(final User user) {
        RequestContextHolder.getRequestAttributes().setAttribute("user", user, RequestAttributes.SCOPE_REQUEST);
    }

    public static void deleteCurrentUser() {
        RequestContextHolder.getRequestAttributes().removeAttribute("user", RequestAttributes.SCOPE_REQUEST);
    }
}
