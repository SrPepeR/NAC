package com.srpeper.calc;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.srpeper.calc.interfaces.OperationFragment;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class IndirectThreeFragment extends Fragment implements OperationFragment {

    private ArrayList<BigDecimal> fields = new ArrayList<>();
    private BigDecimal result = new BigDecimal(0);

    private EditText a, b, c;
    private TextView x;

    public IndirectThreeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_three, container, false);

        this.a = root.findViewById(R.id.ohm_a);
        this.b = root.findViewById(R.id.ohm_b);
        this.c = root.findViewById(R.id.ohm_c);
        this.x = root.findViewById(R.id.ohm_x);

        a.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                changed();
            }
        });
        b.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                changed();
            }
        });
        c.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                changed();
            }
        });

        return root;
    }

    private void changed () {
        if (!this.a.getText().toString().isEmpty() && !this.b.getText().toString().isEmpty() && !this.c.getText().toString().isEmpty()) {
            this.result = new BigDecimal(0);

            BigDecimal a, b, c;
            if (!this.a.getText().toString().equals(".")) {
                a = new BigDecimal(this.a.getText().toString());
            } else {
                a = new BigDecimal(0);
            }
            if (!this.b.getText().toString().equals(".")) {
                b = new BigDecimal(this.b.getText().toString());
            } else {
                b = new BigDecimal(0);
            }
            if (!this.c.getText().toString().equals(".")) {
                c = new BigDecimal(this.c.getText().toString());
            } else {
                c = new BigDecimal(0);
            }

            this.result = a.multiply(b).divide(c, 2, RoundingMode.HALF_UP);
        } else {
            this.result = new BigDecimal(0);
        }

        this.x.setText(this.result.toString());
    }

    @Override
    public ArrayList<BigDecimal> getFields() {
        return this.fields;
    }

    @Override
    public BigDecimal getResult() {
        return this.result;
    }

    @Override
    public void clear() {
        this.fields = new ArrayList<>();
        this.result = new BigDecimal(0);

        this.a.setText("");
        this.b.setText("");
        this.c.setText("");
        this.x.setText(this.result.toString());
    }
}