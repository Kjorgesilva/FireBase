package com.example.firebase;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.firebase.helpe.InterfaceHelpe;
import com.example.firebase.mask.MaskEditUtil;
import com.example.firebase.model.Cliente;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements InterfaceHelpe {

    private EditText edt_texto, edt_telefone, edt_email;
    private Button btn_inserir, btn_excluir, btn_alterar, btn_listar;

//    private ConexaoFireBase conexaoFireBase = new ConexaoFireBase(contexto);

    private Context contexto = this;
    private Cliente cliente;
    private Cliente clienteSelect;
    private ListView listView;
    private ArrayList<Cliente> lista = new ArrayList<>();
    private ArrayAdapter<Cliente> adapterCliente;


    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference dataBase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FindView();
        Onclick();

//      conexaoFireBase.startFireBase();
        startFireBase();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    clienteSelect = (Cliente) adapterView.getItemAtPosition(i);
                    edt_texto.setText(clienteSelect.getNome());
                    edt_email.setText(clienteSelect.getEmail());
                    edt_telefone.setText(clienteSelect.getTelefone());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }


    @Override
    public void FindView() {
        cliente = new Cliente();

        edt_texto = findViewById(R.id.edt_nome);
        edt_telefone = findViewById(R.id.edt_telefone);
        edt_email = findViewById(R.id.edt_email);


        btn_inserir = findViewById(R.id.btn_inserir);
        btn_excluir = findViewById(R.id.btn_excluir);
        btn_alterar = findViewById(R.id.btn_alterar);
        btn_listar = findViewById(R.id.btn_listar);
        listView = findViewById(R.id.listView);

        edt_telefone.addTextChangedListener(MaskEditUtil.mask(edt_telefone, MaskEditUtil.FORMAT_FONE));
    }

    @Override
    public void Onclick() {
        btn_inserir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String nome = edt_texto.getText().toString();
                    String email = edt_telefone.getText().toString();
                    String telefone = edt_email.getText().toString();

                    if (nome.isEmpty() || email.isEmpty() || telefone.isEmpty()) {
                        Toast.makeText(contexto, "Preencha todos os campos", Toast.LENGTH_LONG).show();
                    } else {

                        cliente.setUid(UUID.randomUUID().toString());
                        cliente.setNome(edt_texto.getText().toString());
                        cliente.setTelefone(edt_telefone.getText().toString());
                        cliente.setEmail(edt_email.getText().toString());

                        dataBase.child("Cliente").child(cliente.getUid()).setValue(cliente);
                        clean();
                        Log.e("TAG", "Passou aqui, inserir");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        btn_listar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    eventoBanco();
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });


        btn_excluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Log.e("TAG", "Passou aqui, excluir");

                    Cliente cliente = new Cliente();
                    cliente.setUid(clienteSelect.getUid());
                    dataBase.child("Cliente").child(cliente.getUid()).removeValue();
                    clean();
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });

        btn_alterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Log.e("TAG", "Passou aqui, alterar");

                    Cliente cliente = new Cliente();

                    cliente.setUid(clienteSelect.getUid());
                    cliente.setNome(edt_texto.getText().toString());
                    cliente.setTelefone(edt_telefone.getText().toString());
                    cliente.setEmail(edt_email.getText().toString().trim());

                    dataBase.child("Cliente").child(cliente.getUid()).setValue(cliente);

                    clean();
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });


    }

    public void startFireBase() {
        try {
            //serve para iniciar o FireBase
            FirebaseApp.initializeApp(contexto);
            firebaseDatabase = FirebaseDatabase.getInstance();
            firebaseDatabase.setPersistenceEnabled(true);
            dataBase = firebaseDatabase.getReference();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void eventoBanco() {
        dataBase.child("Cliente").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    //todos os elementeo que eu tenho no Cliente
                    lista.clear();
                    for (DataSnapshot snp : dataSnapshot.getChildren()) {
                        Cliente cli = snp.getValue(Cliente.class);
                        lista.add(cli);

                    }
                    adapterCliente = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, lista);
                    listView.setAdapter(adapterCliente);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    private void clean() {
        edt_email.setText("");
        edt_telefone.setText("");
        edt_texto.setText("");
    }

}
