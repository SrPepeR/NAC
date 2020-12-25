package com.srpeper.calc.objects;

import com.srpeper.calc.enums.EspA;
import com.srpeper.calc.enums.EspP;
import com.srpeper.calc.enums.Num;
import com.srpeper.calc.enums.Op;

import java.util.ArrayList;
import java.util.Collections;

public class Calculator {

    private ArrayList<StringBuffer> partsControl;

    private StringBuffer operation;
    private double result;

    public Calculator () {
        this.partsControl = new ArrayList<>();
        this.operation = new StringBuffer();
    }

    public StringBuffer getOperation() {
        return operation;
    }

    public double getResult() {
        return this.result;
    }

    public void addZero (Num num) {
        if (checkNum() && this.operation.length() > 0) {
            this.operation.append(num.getSymbol());
            this.partsControl.get(partsControl.size() - 1).append(num.getSymbol());
            changeResult();
        }
    }

    public void addNum (Num num) {
        if (num == Num.PI) {
            if (checkPI()) {
                this.operation.append(num.getSymbol());
                this.partsControl.add(new StringBuffer().append(num.getSymbol()));
                changeResult();
            }
        } else {
            if (checkNum()) {
                this.operation.append(num.getSymbol());
                if (this.partsControl.size() > 0) {
                    if (Num.ONE.equals(this.partsControl.get(partsControl.size() - 1).charAt(0))) {
                        this.partsControl.get(partsControl.size() - 1).append(num.getSymbol());
                    } else {
                        this.partsControl.add(new StringBuffer().append(num.getSymbol()));
                    }
                } else {
                    this.partsControl.add(new StringBuffer().append(num.getSymbol()));
                }
                changeResult();
            }
        }
    }

    public void addOp (Op op) {
        if (checkOp()) {
            this.operation.append(op.getSymbol());
            this.partsControl.add(new StringBuffer().append(op.getSymbol()));
        }
    }

    public void addEspA (EspA espA) {
        if (checkEspA()) {
            this.operation.append(espA.getSymbol());
            this.partsControl.add(new StringBuffer().append(espA.getSymbol()));
        }
    }

    public void addEspP (EspP espP) {
        if (checkEspP()) {
            if (espP == EspP.COMMA) {
                boolean isDecimal = false;

                for (int i = 0; i < partsControl.get(this.partsControl.size() - 1).length(); i++) {
                    if (this.partsControl.get(this.partsControl.size() - 1).charAt(i) == EspP.COMMA.getSymbol()) {
                        isDecimal = true;
                    }
                }

                if (!isDecimal) {
                    this.operation.append(espP.getSymbol());
                    this.partsControl.set(this.partsControl.size() - 1, this.partsControl.get(this.partsControl.size() - 1).append(espP.getSymbol()));
                }
            } else {
                boolean isPrev = false;

                for (int i = 0; i < partsControl.get(this.partsControl.size() - 1).length(); i++) {
                    if (this.partsControl.get(this.partsControl.size() - 1).charAt(i) == EspP.FRACTION.getSymbol() || this.partsControl.get(this.partsControl.size() - 1).charAt(i) == EspP.PERCENTAGE.getSymbol()) {
                        isPrev = true;
                    }
                }

                if (!isPrev) {
                    this.operation.append(espP.getSymbol());
                    this.partsControl.add(new StringBuffer().append(espP.getSymbol()));
                    if (espP == EspP.PERCENTAGE) {
                        changeResult();
                    }
                }
            }
        }
    }

    public void removeOne () {
        if (operation.length() > 0) {
            this.operation.deleteCharAt(operation.length() - 1);
            if (this.partsControl.get(this.partsControl.size() - 1).length() > 1) {
                this.partsControl.set(this.partsControl.size() - 1, this.partsControl.get(this.partsControl.size() - 1).deleteCharAt(this.partsControl.get(this.partsControl.size() - 1).length() - 1));
            } else {
                this.partsControl.remove(this.partsControl.size() - 1);
            }
            changeResult();
        }
    }

    private void changeResult () {
        double result = 0;

        if (this.partsControl.size() > 0) {
            ArrayList<Integer> prioPos = new ArrayList<>();
            ArrayList<StringBuffer> partsControlCopy = new ArrayList<>(this.partsControl);

            if (partsControlCopy.size() > 1) {
                if (!Num.ONE.equals(partsControlCopy.get(partsControlCopy.size() - 1).charAt(0)) && partsControlCopy.get(partsControlCopy.size() - 1).charAt(0) != EspP.PERCENTAGE.getSymbol()) {
                    //THE LAST SYMBOL MUST BE IGNORED
                    partsControlCopy.remove(partsControlCopy.size() - 1);
                }

                //ESPA NEED TO CALCULATE FIRST WITH i+1 POS.
                for (int i = 0; i < partsControlCopy.size(); i++) {
                    if (EspA.COS.equals(partsControlCopy.get(i).charAt(0))) {
                        prioPos.add(i + 1);
                        partsControlCopy.set(i, calculateESPA(partsControlCopy.get(i), partsControlCopy.get(i + 1)));
                    }
                }
                if (prioPos.size() > 0) {
                    //THERE ARE ESPA SYMBOLS, THE ORDER OF THE MARKED POSITIONS IS REVERSED AND REMOVED FROM THE COPY.
                    Collections.reverse(prioPos);
                    for (int pos: prioPos) {
                        partsControlCopy.remove(pos);
                    }
                    //CLEANED UP THE LIST OF MARKED POSITIONS.
                    prioPos = new ArrayList<>();
                }

                //ESPP NEEDS TO BE REVIEWED ON A CASE-BY-CASE BASIS
                //IN THE CASE OF A PERCENTAGE THEY ARE CALCULATED BASED ON i-1 % i.
                for (int i = 0; i < partsControlCopy.size(); i++) {
                    if (partsControlCopy.get(i).charAt(0) == '%') {
                        prioPos.add(i);
                        //PERCENTAGE IS i-1 / 100.
                        if (partsControlCopy.get(i - 1).toString().equals(String.valueOf(Num.PI.getSymbol()))) {
                            partsControlCopy.set(i - 1, new StringBuffer(String.valueOf(Num.PI.getValue() / 100)));
                        } else {
                            partsControlCopy.set(i - 1, new StringBuffer(String.valueOf(Double.parseDouble(partsControlCopy.get(i - 1).toString()) / 100)));
                        }
                    }
                }
                if (prioPos.size() > 0) {
                    //THERE ARE PERCENTAGE SYMBOLS, THE ORDER OF THE MARKED POSITIONS IS REVERSED AND REMOVED FROM THE COPY.
                    Collections.reverse(prioPos);
                    for (int pos: prioPos) {
                        partsControlCopy.remove(pos);
                    }
                    //CLEANED UP THE LIST OF MARKED POSITIONS.
                    prioPos = new ArrayList<>();
                }

                //IN THE CASE OF A FRACTION THEY ARE CALCULATED BASED ON i-1 / i.
                for (int i = 0; i < partsControlCopy.size(); i++) {
                    if (partsControlCopy.get(i).charAt(0) == EspP.FRACTION.getSymbol()) {
                        prioPos.add(i);
                        //FRACTION IS i-1 / i+1.
                        if (partsControlCopy.get(i - 1).toString().equals(String.valueOf(Num.PI.getSymbol()))) {
                            if (partsControlCopy.get(i + 1).toString().equals(String.valueOf(Num.PI.getSymbol()))) {
                                partsControlCopy.set(i - 1, new StringBuffer(String.valueOf(Num.PI.getValue() / Num.PI.getValue())));
                            } else {
                                partsControlCopy.set(i - 1, new StringBuffer(String.valueOf(Num.PI.getValue() / Double.parseDouble(partsControlCopy.get(i + 1).toString()))));
                            }
                        } else {
                            if (partsControlCopy.get(i + 1).toString().equals(String.valueOf(Num.PI.getSymbol()))) {
                                partsControlCopy.set(i - 1, new StringBuffer(String.valueOf(Double.parseDouble(partsControlCopy.get(i - 1).toString()) / Num.PI.getValue())));
                            } else {
                                partsControlCopy.set(i - 1, new StringBuffer(String.valueOf(Double.parseDouble(partsControlCopy.get(i - 1).toString()) / Double.parseDouble(partsControlCopy.get(i + 1).toString()))));
                            }
                        }
                    }
                }
                if (prioPos.size() > 0) {
                    //THERE ARE FRACTIONS SYMBOLS, THE ORDER OF THE MARKED POSITIONS IS REVERSED AND REMOVED FROM THE COPY.
                    Collections.reverse(prioPos);
                    for (int pos: prioPos) {
                        partsControlCopy.remove(pos + 1);
                        partsControlCopy.remove(pos);
                    }
                    //CLEANED UP THE LIST OF MARKED POSITIONS.
                    prioPos = new ArrayList<>();
                }

                //OP NEED TO CALCULATE FIRST HIGH PRIORITY OPERATORS.
                for (int i = 0; i < partsControlCopy.size(); i++) {
                    if (Op.MUL.isHighPriority(partsControlCopy.get(i).charAt(0))) {
                        prioPos.add(i);
                        partsControlCopy.set(i - 1, calculateOp(i, partsControlCopy));
                    }
                }
                if (prioPos.size() > 0) {
                    //THERE ARE ESPA SYMBOLS, THE ORDER OF THE MARKED POSITIONS IS REVERSED AND REMOVED FROM THE COPY.
                    Collections.reverse(prioPos);
                    for (int pos: prioPos) {
                        partsControlCopy.remove(pos + 1);
                        partsControlCopy.remove(pos);
                    }
                }
                //LAST CALCULATE NORMALS OP.
                while (partsControlCopy.size() >= 3) {
                    double opResult = calculateNormal(partsControlCopy.get(0), partsControlCopy.get(1), partsControlCopy.get(2));

                    partsControlCopy.set(0, new StringBuffer(String.valueOf(opResult)));
                    partsControlCopy.remove(2);
                    partsControlCopy.remove(1);
                }

                if (partsControlCopy.get(0).charAt(0) == Num.PI.getSymbol()) {
                    result = Num.PI.getValue();
                } else {
                    result = Double.parseDouble(partsControlCopy.get(0).toString());
                }
            } else {
                if (EspA.COS.equals(partsControlCopy.get(0).charAt(0))) {
                    result = 0;
                } else {
                    if (partsControlCopy.get(0).charAt(0) == Num.PI.getSymbol()) {
                        result = Num.PI.getValue();
                    } else {
                        result = Double.parseDouble(partsControlCopy.get(0).toString());
                    }
                }
            }
        }

        this.result = result;
    }

    public void result () {
        this.operation = new StringBuffer().append(result);
        this.partsControl = new ArrayList<>();
        partsControl.add(new StringBuffer().append(result));
    }

    private StringBuffer calculateESPA (StringBuffer ESPA, StringBuffer NUM) {
        StringBuffer resultSTR = new StringBuffer();
        double result = 0;

        switch (ESPA.charAt(0)) {
            case 's' :
                if (NUM.toString().equals(String.valueOf(Num.PI.getSymbol()))) {
                    result = Math.sin(Num.PI.getValue());
                } else {
                    result = Math.sin(Double.parseDouble(NUM.toString()));
                }
                break;
            case 'c' :
                if (NUM.toString().equals(String.valueOf(Num.PI.getSymbol()))) {
                    result = Math.cos(Num.PI.getValue());
                } else {
                    result = Math.cos(Double.parseDouble(NUM.toString()));
                }
                break;
            case 't' :
                if (NUM.toString().equals(String.valueOf(Num.PI.getSymbol()))) {
                    result = Math.tan(Num.PI.getValue());
                } else {
                    result = Math.tan(Double.parseDouble(NUM.toString()));
                }
                break;
            case 'l' :
                if (NUM.toString().equals(String.valueOf(Num.PI.getSymbol()))) {
                    result = Math.log10(Num.PI.getValue());
                } else {
                    result = Math.log10(Double.parseDouble(NUM.toString()));
                }
                break;
            case 'n' :
                if (NUM.toString().equals(String.valueOf(Num.PI.getSymbol()))) {
                    result = Math.log(Num.PI.getValue());
                } else {
                    result = Math.log(Double.parseDouble(NUM.toString()));
                }
                break;
            case '√' :
                if (NUM.toString().equals(String.valueOf(Num.PI.getSymbol()))) {
                    result = Math.sqrt(Num.PI.getValue());
                } else {
                    result = Math.sqrt(Double.parseDouble(NUM.toString()));
                }
                break;
        }

        resultSTR.append(result);

        return resultSTR;
    }

    private StringBuffer calculateOp (int pos, ArrayList<StringBuffer> partsControlCopy) {
        StringBuffer resultSTR = new StringBuffer();
        double num1, num2;
        double result = 0;

        if (String.valueOf(Num.PI.getSymbol()).equals(partsControlCopy.get(pos - 1).toString())) {
            num1 = Num.PI.getValue();
        } else {
            num1 = Double.parseDouble(partsControlCopy.get(pos - 1).toString());
        }
        if (String.valueOf(Num.PI.getSymbol()).equals(partsControlCopy.get(pos + 1).toString())) {
            num2 = Num.PI.getValue();
        } else {
            num2 = Double.parseDouble(partsControlCopy.get(pos + 1).toString());
        }

        switch (partsControlCopy.get(pos).charAt(0)) {
            case '\u00D7' :
                //MUL
                result = num1 * num2;
                break;
            case '\u00F7' :
                //DIV
                result = num1 / num2;
                break;
            case '\u005E' :
                //POT
                result = Math.pow(num1, num2);
                break;
        }

        resultSTR.append(result);

        return resultSTR;
    }

    private double calculateNormal(StringBuffer num1STR, StringBuffer opSTR, StringBuffer num2STR) {
        double result = 0;
        double num1;
        char op = opSTR.charAt(0);
        double num2;

        if (String.valueOf(Num.PI.getSymbol()).equals(num1STR.toString())) {
            num1 = Num.PI.getValue();
        } else {
            num1 = Double.parseDouble(num1STR.toString());
        }

        if (String.valueOf(Num.PI.getSymbol()).equals(num2STR.toString())) {
            num2 = Num.PI.getValue();
        } else {
            num2 = Double.parseDouble(num2STR.toString());
        }

        switch (op) {
            case '+' :
                result = num1 + num2;
                break;
            case '-' :
                result = num1 - num2;
                break;
        }

        return result;
    }

    private boolean checkNum () {
        boolean isValid = false;
        char prev;

        if (this.operation.length() == 0) {
            prev = '_';
        } else {
            prev = this.operation.charAt(operation.length() - 1);
        }

        if ((Character.valueOf(prev).equals('_') || Num.ONE.equals(prev) || Op.SUM.equals(prev) || EspA.COS.equals(prev) || prev == EspP.COMMA.getSymbol() || prev == EspP.FRACTION.getSymbol()) && !Character.valueOf(prev).equals(Num.PI.getSymbol())) {
            isValid = true;
        }

        return isValid;
    }

    private boolean checkPI () {
        boolean isValid = false;
        char prev;

        if (this.operation.length() == 0) {
            prev = '_';
        } else {
            prev = this.operation.charAt(operation.length() - 1);
        }

        if ((Character.valueOf(prev).equals('_') || Op.SUM.equals(prev) || EspA.COS.equals(prev) || prev == EspP.COMMA.getSymbol() || prev == EspP.FRACTION.getSymbol()) && !Character.valueOf(prev).equals(Num.PI.getSymbol())) {
            isValid = true;
        }

        return isValid;
    }

    private boolean checkOp () {
        boolean isValid = false;
        char prev;

        if (this.operation.length() == 0) {
            prev = '_';
        } else {
            prev = this.operation.charAt(operation.length() - 1);
        }

        if (Num.ONE.equals(prev) || (EspP.FRACTION.equals(prev) && prev != EspP.FRACTION.getSymbol())) {
            isValid = true;
        }

        return isValid;
    }

    private boolean checkEspA () {
        boolean isValid = false;
        char prev;

        if (this.operation.length() == 0) {
            prev = '_';
        } else {
            prev = this.operation.charAt(operation.length() - 1);
        }

        if (Character.valueOf(prev).equals('_') || Op.SUM.equals(prev)) {
            isValid = true;
        }

        return isValid;
    }

    private boolean checkEspP () {
        boolean isValid = false;
        char prev;

        if (this.operation.length() == 0) {
            prev = '_';
        } else {
            prev = this.operation.charAt(operation.length() - 1);
        }

        if (Num.ONE.equals(prev)) {
            isValid = true;
        }

        return isValid;
    }

}
