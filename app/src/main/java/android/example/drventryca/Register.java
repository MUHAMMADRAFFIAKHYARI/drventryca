package android.example.drventryca;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // Variable yang akan merefers Firebase Realtime Database.
    DatabaseReference root; // untuk mengambil rootnya
    FirebaseAuth auth;
    FirebaseUser user; // untuk mengambil user

    private TextView toHome, goLogin;

    // Variable fields EditText dan Button
    private Button btSubmitDB;
    EditText etNamaDepan, etNamaBelakang, etBeratBadan, etTinggiBadan, etUsia, etUserName, etPassword;
    Spinner goldar;
    RadioGroup gender;
    RadioButton jenisKelamin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // inisialisasi
        etNamaDepan = findViewById(R.id.edt_email);
        etNamaBelakang = findViewById(R.id.edt_password);
        etBeratBadan = findViewById(R.id.et_massaBadan);
        etTinggiBadan = findViewById(R.id.et_tinggiBadan);
        etUsia = findViewById(R.id.et_usia);
        btSubmitDB = findViewById(R.id.bt_login);
        toHome = findViewById(R.id.toHome);
        etUserName = findViewById(R.id.edt_userName);
        etPassword = findViewById(R.id.password_);

        // Ranah Firebase
        auth = FirebaseAuth.getInstance();
        root = FirebaseDatabase.getInstance().getReference();

        btSubmitDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String namaDepan, namaBelakang, username, password;
                final int massaBadan, tinggiBadan, usia;

                namaDepan = etNamaDepan.getText().toString().trim();
                namaBelakang = etNamaBelakang.getText().toString().trim();
                username = etUserName.getText().toString().trim();
                password = etPassword.getText().toString().trim();

                massaBadan = Integer.parseInt(etBeratBadan.getText().toString().trim());
                tinggiBadan = Integer.parseInt(etTinggiBadan.getText().toString().trim());
                usia = Integer.parseInt(etUsia.getText().toString().trim());

                auth.createUserWithEmailAndPassword(username, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    user = auth.getCurrentUser(); // dia mengambil user yang terbaru;
                                    Data data = new Data(namaDepan, namaBelakang, String.valueOf(massaBadan), String.valueOf(tinggiBadan), String.valueOf(usia));
                                    root.child("User:").child(user.getUid()).setValue(data)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    Toast.makeText(getApplicationContext(), "Data Berhasil Ditambah ke Realtime Database", Toast.LENGTH_LONG).show();
                                                }
                                            });
                                }
                            }
                        });

            }
        });

        Window window = getWindow();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.AUTOFILL_FLAG_INCLUDE_NOT_IMPORTANT_VIEWS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);

        Spinner spinner = findViewById(R.id.spin_goldar);

        if (spinner != null) {
            spinner.setOnItemSelectedListener(this);
        }

        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(this, R.array.goldar, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        if (spinner != null) {
            spinner.setAdapter(adapter);
        }


    }

    private boolean isEmpty(String s) {
        return TextUtils.isEmpty(s);

    }


//    private void submitData(Data data) {
//        database.child("Data User : ").push().setValue(data).addOnSuccessListener(this, new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void aVoid) {
//                etNamaDepan.setText("");
//                etNamaBelakang.setText("");
//                etBeratBadan.setText("");
//                etTinggiBadan.setText("");
//                etUsia.setText("");
//                Snackbar.make(findViewById(R.id.bt_submit), "Data Berhasil Ditambahkan", Snackbar.LENGTH_LONG).show();
//                startActivity(Register.getActIntent(Register.this));
//            }
//        });
//    }

    public static Intent getActIntent(Activity activity) {
        return new Intent(activity, Landing.class);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long id) {
        String spinnerLabel = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void toLogin(View view){
        startActivity(new Intent(this, Login.class));
    }

    public void skipHome(View view) {
        startActivity(new Intent(this,Landing.class));
    }
}
