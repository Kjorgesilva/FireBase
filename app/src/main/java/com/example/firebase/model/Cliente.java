package com.example.firebase.model;

public class Cliente {

    private  String uid;
    private String nome;
    private String email;
    private String telefone;

    public Cliente() {
    }


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Override
    public String toString() {
        return "Cliente: " +nome +"\n"+
                "E-Mail: " + email + "\n"+
                "Telefone: " + telefone + "\n";
    }
}
