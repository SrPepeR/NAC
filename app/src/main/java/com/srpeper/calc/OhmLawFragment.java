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

public class OhmLawFragment extends Fragment implements OperationFragment {

    private ArrayList<BigDecimal> fields = new ArrayList<>();
    private BigDecimal result = new BigDecimal(0);

    private EditText v, i, r;

    private boolean isCleaning = false;

    public OhmLawFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_ohm_law, container, false);

        this.v = root.findViewById(R.id.ohm_v);
        this.i = root.findViewById(R.id.ohm_i);
        this.r = root.findViewById(R.id.ohm_r);

        v.addTextChangedListener(new TextWatcher() {
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
        i.addTextChangedListener(new TextWatcher() {
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
        r.addTextChangedListener(new TextWatcher() {
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
        if (!isCleaning) {
            if (!this.v.getText().toString().isEmpty() && !this.i.getText().toString().isEmpty() && this.r.getText().toString().isEmpty()) {
                calculateR();
            } else {
                if (!this.v.getText().toString().isEmpty() && this.i.getText().toString().isEmpty() && !this.r.getText().toString().isEmpty()) {
                    calculateI();
                } else {
                    if (this.v.getText().toString().isEmpty() && !this.i.getText().toString().isEmpty() && !this.r.getText().toString().isEmpty()) {
                        calculateV();
                    } else {
                        this.v.setEnabled(true);
                        this.i.setEnabled(true);
                        this.r.setEnabled(true);
                    }
                }
            }
        }
    }

    private void calculateV () {
        BigDecimal v, i, r;

        if (!this.i.getText().toString().equals(".")) {
            i = new BigDecimal(this.i.getText().toString());
        } else {
            i = new BigDecimal(0);
        }

        if (!this.r.getText().toString().equals(".")) {
            r = new BigDecimal(this.r.getText().toString());
        } else {
            r = new BigDecimal(0);
        }

        v = r.multiply(i);

        this.isCleaning = true;
        this.v.setText(v.toString());
        this.isCleaning = false;

        this.v.setEnabled(false);
        this.i.setEnabled(true);
        this.r.setEnabled(true);
    }

    private void calculateI () {
        BigDecimal v, i, r;

        if (!this.v.getText().toString().equals(".")) {
            v = new BigDecimal(this.v.getText().toString());
        } else {
            v = new BigDecimal(0);
        }

        if (!this.r.getText().toString().equals(".")) {
            r = new BigDecimal(this.r.getText().toString());
        } else {
            r = new BigDecimal(0);
        }

        i = v.divide(r, 2, RoundingMode.HALF_UP);

        this.isCleaning = true;
        this.i.setText(i.toString());
        this.isCleaning = false;

        this.v.setEnabled(true);
        this.i.setEnabled(false);
        this.r.setEnabled(true);
    }

    private void calculateR () {
        BigDecimal v, i, r;

        if (!this.v.getText().toString().equals(".")) {
            v = new BigDecimal(this.v.getText().toString());
        } else {
            v = new BigDecimal(0);
        }

        if (!this.i.getText().toString().equals(".")) {
            i = new BigDecimal(this.i.getText().toString());
        } else {
            i = new BigDecimal(0);
        }

        r = v.divide(i, 2, RoundingMode.HALF_UP);

        this.isCleaning = true;
        this.r.setText(r.toString());
        this.isCleaning = false;

        this.v.setEnabled(true);
        this.i.setEnabled(true);
        this.r.setEnabled(false);
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
        this.isCleaning = true;

        this.fields = new ArrayList<>();
        this.result = new BigDecimal(0);

        this.v.setEnabled(true);
        this.v.setText("");
        this.i.setEnabled(true);
        this.i.setText("");
        this.r.setEnabled(true);
        this.r.setText("");

        this.isCleaning = false;
    }
}