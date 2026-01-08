package br.com.imobmatch.api.models.user;

public enum UserRole {
    OWNER("owner"),
    BROKER("broker"),
    ADMIN("admin");

    private final String value;

    UserRole(String role) {
        this.value = role;
    }

    public String getValue() {
        return value;
    }
}
