package android.example.drventryca.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.example.drventryca.R;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioGroup;

public class DiagTeng extends AppCompatActivity {

    RadioGroup radioGroup_teng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diag_teng);
        radioGroup_teng = (RadioGroup) findViewById(R.id.radiogroup_teng);

        //Intent
        Bundle bundle = getIntent().getExtras();

        if(bundle != null){
            String data = bundle.getString("data");
        }

        radioGroup_teng.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId==R.id.radioButton_teng){
                    Intent intent = new Intent(DiagTeng.this, DiagTengPart1.class);
                    String g2 = "tenggorokan"+"padat";
                    intent.putExtra("data", g2);
                    startActivity(intent);
                }
                if (checkedId==R.id.radioButton_teng2){

                }
            }
        });

        Window window = getWindow();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.AUTOFILL_FLAG_INCLUDE_NOT_IMPORTANT_VIEWS);

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);
    }
}