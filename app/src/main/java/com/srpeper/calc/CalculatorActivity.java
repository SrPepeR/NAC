package com.srpeper.calc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.srpeper.calc.enums.EspA;
import com.srpeper.calc.enums.EspP;
import com.srpeper.calc.enums.Num;
import com.srpeper.calc.enums.Op;
import com.srpeper.calc.objects.Calculator;

public class CalculatorActivity extends AppCompatActivity {

    private Calculator calculator;

    private TextView operation, result;
    private ImageButton delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        overridePendingTransition(R.animator.left_in, R.animator.left_out);

        calculator = new Calculator();

        this.operation = findViewById(R.id.txt_op);
        this.result = findViewById(R.id.txt_result);
        this.delete = findViewById(R.id.btn_delete);

        delete.setOnLongClickListener(v -> {
            deleteAll();

            return false;
        });
    }

    public void change (View button) {
        Intent toConverter = new Intent(this, ConverterActivity.class);
        startActivity(toConverter);
    }

    public void addNum (View button) {
        Num num = Num.ZERO;
        switch (button.getId()) {
            case R.id.btn_zero :
                num = Num.ZERO;
                break;
            case R.id.btn_one :
                num = Num.ONE;
                break;
            case R.id.btn_two :
                num = Num.TWO;
                break;
            case R.id.btn_three :
                num = Num.THREE;
                break;
            case R.id.btn_four :
                num = Num.FOUR;
                break;
            case R.id.btn_five :
                num = Num.FIVE;
                break;
            case R.id.btn_six :
                num = Num.SIX;
                break;
            case R.id.btn_seven :
                num = Num.SEVEN;
                break;
            case R.id.btn_eight :
                num = Num.EIGHT;
                break;
            case R.id.btn_nine :
                num = Num.NINE;
                break;
            case R.id.btn_pi :
                num = Num.PI;
                break;
        }

        if (num == Num.ZERO) {
            this.calculator.addZero(num);
        } else {
            this.calculator.addNum(num);
        }

        refreshOperation();
        refreshResult();
    }

    public void addOp (View button) {
        Op op = Op.SUM;

        switch (button.getId()) {
            case R.id.btn_sub :
                op = Op.SUB;
                break;
            case R.id.btn_multiply :
                op = Op.MUL;
                break;
            case R.id.btn_divide :
                op = Op.DIV;
                break;
            case R.id.btn_pot :
                op = Op.POT;
                break;
        }

        calculator.addOp(op);
        refreshOperation();
    }

    public void addEspP (View button) {
        EspP espP = EspP.COMMA;

        switch (button.getId()) {
            case R.id.btn_fraction :
                espP = EspP.FRACTION;
                break;
            case R.id.btn_percentage :
                espP = EspP.PERCENTAGE;
                break;
        }

        this.calculator.addEspP(espP);

        refreshOperation();
        refreshResult();
    }

    public void addEspA (View button) {
        EspA espA = EspA.COS;

        switch (button.getId()) {
            case R.id.btn_sin :
                espA = EspA.SIN;
                break;
            case R.id.btn_tan :
                espA = EspA.TAN;
                break;
            case R.id.btn_log :
                espA = EspA.LOG;
                break;
            case R.id.btn_ln :
                espA = EspA.LN;
                break;
            case R.id.btn_root :
                espA = EspA.ROOT;
                break;
        }

        calculator.addEspA(espA);
        refreshOperation();
    }

    public void delete (View button) {
        this.calculator.removeOne();
        refreshOperation();
        refreshResult();
    }

    public void result (View button) {
        this.calculator.result();
        refreshOperation();
        refreshResult();
    }

    private void deleteAll () {
        this.calculator = new Calculator();
        refreshOperation();
        refreshResult();
    }

    private void refreshOperation () {
        if (!this.calculator.getOperation().toString().isEmpty()) {
            StringBuffer operation = new StringBuffer();
            for (int i = 0; i < this.calculator.getOperation().length(); i++) {
                if (EspA.COS.equals(calculator.getOperation().charAt(i))) {
                    String toAdd = "";
                    switch (calculator.getOperation().charAt(i)) {
                        case 's' :
                            toAdd = EspA.SIN.getName();
                            break;
                        case 'c' :
                            toAdd = EspA.COS.getName();
                            break;
                        case 't' :
                            toAdd = EspA.TAN.getName();
                            break;
                        case 'l' :
                            toAdd = EspA.LOG.getName();
                            break;
                        case 'n' :
                            toAdd = EspA.LN.getName();
                            break;
                        case '√' :
                            toAdd = EspA.ROOT.getName();
                            break;
                    }
                    operation.append(toAdd);
                } else {
                    operation.append(calculator.getOperation().charAt(i));
                }
            }
            this.operation.setText(operation);
        } else {
            this.operation.setText("0");
        }
    }

    private void refreshResult () {
        if (this.calculator.getResult() == 0.0) {
            this.result.setText("0");
        } else {
            this.result.setText(String.valueOf(this.calculator.getResult()));
        }
    }

}