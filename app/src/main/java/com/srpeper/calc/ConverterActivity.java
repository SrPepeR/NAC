package com.srpeper.calc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.srpeper.calc.enums.Area;
import com.srpeper.calc.enums.Data;
import com.srpeper.calc.enums.EspP;
import com.srpeper.calc.enums.Length;
import com.srpeper.calc.enums.Money;
import com.srpeper.calc.enums.Num;
import com.srpeper.calc.enums.Speed;
import com.srpeper.calc.enums.Temperature;
import com.srpeper.calc.enums.Time;
import com.srpeper.calc.enums.Type;
import com.srpeper.calc.enums.Volume;
import com.srpeper.calc.enums.Weight;
import com.srpeper.calc.objects.Converter;

import java.util.ArrayList;

public class ConverterActivity extends AppCompatActivity {

    private Converter converter;

    private TextView value, result, type;
    private ImageButton delete;
    private Spinner valueSPN, resultSPN;

    private String[] typeArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converter);
        overridePendingTransition(R.animator.right_in, R.animator.right_out);

        this.converter = new Converter();

        this.typeArray = getResources().getStringArray(R.array.length);

        this.value = findViewById(R.id.txt_value);
        this.result = findViewById(R.id.txt_result_conv);
        this.type = findViewById(R.id.txt_type);
        this.delete = findViewById(R.id.btn_delete_conv);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, this.typeArray);

        this.valueSPN = findViewById(R.id.spn_unity_value);
        this.valueSPN.setSelection(0);
        this.valueSPN.setAdapter(arrayAdapter);
        this.resultSPN = findViewById(R.id.spn_unity_result);
        this.resultSPN.setSelection(1);
        this.resultSPN.setAdapter(arrayAdapter);

        this.delete.setOnLongClickListener(v -> {
            deleteAll();

            return false;
        });

        this.valueSPN.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                changeValueMag(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        this.resultSPN.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                changeResultMag(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void change (View button) {
        Intent toCalculator = new Intent(this, CalculatorActivity.class);
        startActivity(toCalculator);
    }

    private void changeValueMag (int position) {
        this.converter.setValueMag(this.typeArray[position]);
        this.converter.setValueSelectedPos(position);
        refresh();
    }

    private void changeResultMag (int position) {
        this.converter.setResultMag(this.typeArray[position]);
        this.converter.setResultSelectedPos(position);
        refresh();
    }

    public void delete (View button) {
        this.converter.removeOne();
        refresh();
    }

    private void deleteAll () {
        this.converter.clear();
        refresh();
    }

    public void changeType (View button) {
        ArrayAdapter<String> adapter = null;
        String type = null;

        switch (button.getId()) {
            case R.id.conv_btn_length :
                this.converter.changeType(Type.LENGTH);
                this.converter.setValueMag(Length.M.getSymbol());
                this.converter.setResultMag(Length.KM.getSymbol());
                this.typeArray = getResources().getStringArray(R.array.length);
                type = getString(R.string.con_len);
                break;
            case R.id.conv_btn_area :
                this.converter.changeType(Type.AREA);
                this.converter.setValueMag(Area.M.getSymbol());
                this.converter.setResultMag(Area.KM.getSymbol());
                this.typeArray = getResources().getStringArray(R.array.area);
                type = getString(R.string.con_area);
                break;
            case R.id.conv_btn_volume :
                this.converter.changeType(Type.VOLUME);
                this.converter.setValueMag(Volume.M.getSymbol());
                this.converter.setResultMag(Volume.KM.getSymbol());
                this.typeArray = getResources().getStringArray(R.array.volume);
                type = getString(R.string.con_volume);
                break;
            case R.id.conv_btn_speed :
                this.converter.changeType(Type.SPEED);
                this.converter.setValueMag(Speed.M.getSymbol());
                this.converter.setResultMag(Speed.KM.getSymbol());
                this.typeArray = getResources().getStringArray(R.array.speed);
                type = getString(R.string.con_speed);
                break;
            case R.id.conv_btn_time :
                this.converter.changeType(Type.TIME);
                this.converter.setValueMag(Time.S.getSymbol());
                this.converter.setResultMag(Time.M.getSymbol());
                this.typeArray = getResources().getStringArray(R.array.time);
                type = getString(R.string.con_time);
                break;
            case R.id.conv_btn_weight :
                this.converter.changeType(Type.WEIGHT);
                this.converter.setValueMag(Weight.GR.getSymbol());
                this.converter.setResultMag(Weight.KG.getSymbol());
                this.typeArray = getResources().getStringArray(R.array.weight);
                type = getString(R.string.con_weight);
                break;
            case R.id.conv_btn_temp :
                this.converter.changeType(Type.TEMP);
                this.converter.setValueMag(Temperature.CELSIUS.getSymbol());
                this.converter.setResultMag(Temperature.KELVIN.getSymbol());
                this.typeArray = getResources().getStringArray(R.array.temperature);
                type = getString(R.string.con_temp);
                break;
            case R.id.conv_btn_money :
                this.converter.changeType(Type.MONEY);
                this.converter.setValueMag(Money.EURO.getSymbol());
                this.converter.setResultMag(Money.DOLLAR.getSymbol());
                this.typeArray = getResources().getStringArray(R.array.money);
                type = getString(R.string.con_money);
                break;
            case R.id.conv_btn_data :
                this.converter.changeType(Type.DATA);
                this.converter.setValueMag(Data.GB.getSymbol());
                this.converter.setResultMag(Data.TB.getSymbol());
                this.typeArray = getResources().getStringArray(R.array.data);
                type = getString(R.string.con_data);
                break;
        }

        adapter = new ArrayAdapter<>(this, R.layout.spinner_item, this.typeArray);

        this.valueSPN.setAdapter(adapter);
        this.valueSPN.setSelection(0);
        this.resultSPN.setAdapter(adapter);
        this.resultSPN.setSelection(1);

        this.type.setText(type);
        refresh();
    }

    public void addComma (View button) {
        converter.addComma(EspP.COMMA);
        refresh();
    }

    public void addNum (View button) {
        switch (button.getId()) {
            case R.id.conv_btn_zero :
                converter.addNum(Num.ZERO);
                break;
            case R.id.conv_btn_one :
                converter.addNum(Num.ONE);
                break;
            case R.id.conv_btn_two :
                converter.addNum(Num.TWO);
                break;
            case R.id.conv_btn_three :
                converter.addNum(Num.THREE);
                break;
            case R.id.conv_btn_four :
                converter.addNum(Num.FOUR);
                break;
            case R.id.conv_btn_five :
                converter.addNum(Num.FIVE);
                break;
            case R.id.conv_btn_six :
                converter.addNum(Num.SIX);
                break;
            case R.id.conv_btn_seven :
                converter.addNum(Num.SEVEN);
                break;
            case R.id.conv_btn_eight :
                converter.addNum(Num.EIGHT);
                break;
            case R.id.conv_btn_nine :
                converter.addNum(Num.NINE);
                break;
        }

        refresh();
    }

    private void refresh () {
        this.converter.changeResult();

        this.value.setText(this.converter.getValue());
        this.result.setText(this.converter.getResult());
    }

}