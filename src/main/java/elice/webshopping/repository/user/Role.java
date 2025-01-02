package elice.webshopping.repository.user;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public enum Role {
    ROLE_ADMIN, ROLE_USER;

    String myRole;

    Role(String myRole) {
        this.myRole = myRole;
    }

    public String value(){
        return myRole;
    }
}