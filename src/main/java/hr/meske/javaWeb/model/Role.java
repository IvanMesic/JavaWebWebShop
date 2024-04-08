package hr.meske.javaWeb.model;

public enum Role {
    ADMIN, BUYER;

    public String getAuthority() {
        return "ROLE_" + this.name();
    }
}

