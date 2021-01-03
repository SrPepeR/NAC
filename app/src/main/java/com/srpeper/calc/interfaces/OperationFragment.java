package com.srpeper.calc.interfaces;

import androidx.fragment.app.Fragment;

import java.math.BigDecimal;
import java.util.ArrayList;

public interface OperationFragment {
    ArrayList<BigDecimal> fields = new ArrayList<>();
    BigDecimal result = new BigDecimal(0);

    ArrayList<BigDecimal> getFields ();

    BigDecimal getResult ();

    void clear ();
}
