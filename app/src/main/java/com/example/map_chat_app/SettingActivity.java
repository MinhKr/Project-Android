package com.example.map_chat_app;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SettingActivity extends AppCompatActivity {

    private CheckBox checkBox;
    private RadioButton unitKm;
    private RadioButton unitMile;
    private RadioButton themeLight;
    private RadioButton themeDark;

    private TextView onReturn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        checkBox = findViewById(R.id.checkBox);
        unitKm = findViewById(R.id.unit_km);
        unitMile = findViewById(R.id.unit_mile);
        themeLight = findViewById(R.id.theme_light);
        themeDark = findViewById(R.id.theme_dark);

        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Toast.makeText(this, "Ẩn danh bạ bật", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Ẩn danh bạ tắt", Toast.LENGTH_SHORT).show();
            }
        });

        RadioGroup unitGroup = findViewById(R.id.unitGroup);
        unitGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.unit_km) {
                Toast.makeText(this, "Đơn vị: Km", Toast.LENGTH_SHORT).show();
            } else if (checkedId == R.id.unit_mile) {
                Toast.makeText(this, "Đơn vị: Dặm", Toast.LENGTH_SHORT).show();
            }
        });

        RadioGroup themeGroup = findViewById(R.id.themeGroup);
        themeGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.theme_light) {
                Toast.makeText(this, "Chủ đề: Sáng", Toast.LENGTH_SHORT).show();
                setTheme(R.style.LightTheme);
            } else if (checkedId == R.id.theme_dark) {
                Toast.makeText(this, "Chủ đề: Tối", Toast.LENGTH_SHORT).show();
                setTheme(R.style.DarkTheme);
            }
        });

        onReturn = findViewById(R.id.onReturn);
        onReturn.setOnClickListener(v -> {
            // Quay lại MainActivity
            finish();
        });
    }
}
