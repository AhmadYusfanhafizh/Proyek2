package id.ac.poliban.mi.yusfan.proyek2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    EditText editUsername, editPassword, editEmail, editNamaLengkap, editAsalSekolah, editAlamat;
    Button btnSimpan;
    TextView textViewPassword;

    public static final String FILENAME = "login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Halaman Depan");

        editUsername = findViewById(R.id.editUsername);
        textViewPassword = findViewById(R.id.textViewPassword);
        editPassword = findViewById(R.id.editPassword);
        editEmail = findViewById(R.id.editEmail);
        editNamaLengkap = findViewById(R.id.editNamaLengkap);
        editAsalSekolah = findViewById(R.id.editAsalSekolah);
        editAlamat = findViewById(R.id.editAlamat);
        btnSimpan = findViewById(R.id.btnSimpan);

        btnSimpan.setVisibility(View.GONE);
        editUsername.setEnabled(false);
        editPassword.setVisibility(View.GONE);
        textViewPassword.setVisibility(View.GONE);
        editEmail.setEnabled(false);
        editNamaLengkap.setEnabled(false);
        editAsalSekolah.setEnabled(false);
        editAlamat.setEnabled(false);

        bacaFileLogin();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                tampilkanDialogKonfirmasiLogout();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    void tampilkanDialogKonfirmasiLogout() {
        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Apakah anda yakin ingin logout?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    hapusFile();
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton(android.R.string.no, null)
                .show();
    }

    void  hapusFile(){
        File file = new File(getFilesDir(), FILENAME);
        if (file.exists()){
            file.delete();
        }
    }

    void bacaFileLogin(){
        File sdcard = getFilesDir();
        File file = new File(sdcard, FILENAME);
        if(file.exists()){
            StringBuilder text = new StringBuilder();
            try{
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line = br.readLine();
                while (line!=null){
                    text.append(line);
                    line = br.readLine();
                }
                br.close();
            }catch (IOException e){
                System.out.println("Error " + e.getMessage());
            }
            String data = text.toString();
            String[] dataUser = data.split(";");
            bacaDataUser(dataUser[0]);
        }
    }

    private void bacaDataUser(String fileName) {
        File sdcard = getFilesDir();
        File file = new File(sdcard, fileName);
        if (file.exists()){
            StringBuilder text = new StringBuilder();
            try{
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line = br.readLine();
                while ( line != null){
                    text.append(line);
                    line = br.readLine();
                }
                br.close();
            } catch (IOException e){
                System.out.println("Error " + e.getMessage());
            }
            String data = text.toString();
            String[] dataUser = data.split(";");

            editUsername.setText(dataUser[0]);
            editEmail.setText(dataUser[1]);
            editNamaLengkap.setText(dataUser[2]);
            editAsalSekolah.setText(dataUser[3]);
            editAlamat.setText(dataUser[4]);

        } else {
            Toast.makeText(this, "User tidak ditemukan", Toast.LENGTH_SHORT).show();
        }
    }

}
