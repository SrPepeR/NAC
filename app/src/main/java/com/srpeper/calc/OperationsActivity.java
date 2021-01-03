package com.srpeper.calc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.shawnlin.numberpicker.NumberPicker;
import com.srpeper.calc.interfaces.OperationFragment;

public class OperationsActivity extends AppCompatActivity {

    private NumberPicker mode;
    private FrameLayout frame;
    private OperationFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operations);

        String[] modes = getResources().getStringArray(R.array.op_types);

        this.mode = findViewById(R.id.modePicker);
        this.frame = findViewById(R.id.op_frame);

        this.mode.setMinValue(1);
        this.mode.setMaxValue(modes.length);
        this.mode.setDisplayedValues(modes);

        this.mode.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                changeMode(newVal);
            }
        });

        changeMode(1);
    }

    private void changeMode(int mode) {
        switch (mode) {
            case 1 :
                this.fragment = new OhmLawFragment();
                break;
            case 2 :
                this.fragment = new DirectThreeFragment();
                break;
            case 3 :
                this.fragment = new IndirectThreeFragment();
                break;
        }

        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(this.frame.getId(), (Fragment) this.fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void toCalculator (View button) {
        Intent toCalculator = new Intent(this, CalculatorActivity.class);
        startActivity(toCalculator);
    }

    public void toConverter (View button) {
        Intent toConverter = new Intent(this, ConverterActivity.class);
        startActivity(toConverter);
    }

    public void clear (View button) {
        this.fragment.clear();
    }
}