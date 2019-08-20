package com.example.firebase.conexao;

import android.content.Context;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConexaoFireBase {
    private Context contexto;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference dataBase;

    public ConexaoFireBase(Context contexto) {
        this.contexto = contexto;
    }
    public void startFireBase() {
        FirebaseApp.initializeApp(contexto);
        firebaseDatabase = FirebaseDatabase.getInstance();
        dataBase = firebaseDatabase.getReference();
    }


}
