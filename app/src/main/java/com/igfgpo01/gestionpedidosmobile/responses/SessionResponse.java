package com.igfgpo01.gestionpedidosmobile.responses;

public class SessionResponse {

    private String login;
    private String logout;

    public SessionResponse(String login, String logout) {
        this.login = login;
        this.logout = logout;
    }

    public String getLogin() {
        return login;
    }

    public String getLogout() {
        return logout;
    }
}
