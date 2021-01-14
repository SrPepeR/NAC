package com.srpeper.calc.objects;

import com.srpeper.calc.enums.EspA;
import com.srpeper.calc.enums.EspP;
import com.srpeper.calc.enums.Num;
import com.srpeper.calc.enums.Op;

import java.math.BigDecimal;
import java.util.ArrayList;

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
            char prev = this.partsControl.get(this.partsControl.size() - 1).charAt(this.partsControl.get(this.partsControl.size() - 1).length() - 1);
            if (!Num.ONE.equals(prev) && prev != EspP.COMMA.getSymbol()) {
                this.partsControl.add(new StringBuffer().append(num.getSymbol()));
            } else {
                this.partsControl.get(partsControl.size() - 1).append(num.getSymbol());
            }
            changeResult(this.partsControl);
        }
    }

    public void addNum (Num num) {
        if (num == Num.PI) {
            if (checkPI()) {
                this.operation.append(num.getSymbol());
                this.partsControl.add(new StringBuffer().append(num.getSymbol()));
                changeResult(this.partsControl);
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
                changeResult(this.partsControl);
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
                        changeResult(this.partsControl);
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
            if (this.operation.length() > 0) {
                char prev = this.operation.charAt(this.operation.length() - 1);
                if (Num.ONE.equals(prev) || prev == EspP.PERCENTAGE.getSymbol()) {
                    changeResult(this.partsControl);
                }
            } else {
                this.result = 0;
            }
        }
    }

    private void changeResult(ArrayList<StringBuffer> parts) {
        ArrayList<StringBuffer> partsCopy = new ArrayList<StringBuffer> (parts);
        boolean flag = false;

        if (partsCopy.size() > 1) {
            int i = 0;
            while (i < partsCopy.size() && !flag) {
                if (EspA.SIN.equals(partsCopy.get(i).charAt(0))) {
                    partsCopy.set(i, calculateESPA(partsCopy.get(i), partsCopy.get(i + 1)));
                    partsCopy.remove(i + 1);
                    flag = true;
                } else {
                    i++;
                }
            }

            if (flag) {
                changeResult(partsCopy);
            } else {
                i = 0;
                while (i < partsCopy.size() && !flag) {
                    if (EspP.FRACTION.equals(partsCopy.get(i).charAt(0))) {
                        if (partsCopy.get(i).charAt(0) == EspP.PERCENTAGE.getSymbol()) {
                            partsCopy.set(i - 1, calculatePercentage(i, partsCopy));
                            partsCopy.remove(i);
                        } else {
                            partsCopy.set(i - 1, calculateFraction(i, partsCopy));
                            partsCopy.remove(i + 1);
                            partsCopy.remove(i);
                        }

                        flag = true;
                    } else {
                        i++;
                    }
                }

                if (flag) {
                    changeResult(partsCopy);
                } else {
                    i = 0;
                    while (i < partsCopy.size() && !flag) {
                        if (Op.POT.isHighPriority(partsCopy.get(i).charAt(0))) {
                            partsCopy.set(i - 1, calculateOp(i, partsCopy));
                            partsCopy.remove(i + 1);
                            partsCopy.remove(i);
                            flag = true;
                        } else {
                            i++;
                        }
                    }

                    if (flag) {
                        changeResult(partsCopy);
                    } else {
                        boolean numPrev = true;
                        i = 0;
                        while (i < partsCopy.size()) {
                            if (!numPrev) {
                                partsCopy.add(i, new StringBuffer(Op.SUM.getSymbol()));
                            }
                            if (isNum(partsCopy.get(i))) {
                                numPrev = true;
                            } else {
                                numPrev = false;
                            }
                            i++;
                        }
                        while (partsCopy.size() >= 3) {
                            double opResult = calculateNormal(partsCopy.get(0), partsCopy.get(1), partsCopy.get(2));
                            partsCopy.set(0, new StringBuffer(String.valueOf(opResult)));
                            partsCopy.remove(2);
                            partsCopy.remove(1);
                        }

                        this.result = Double.parseDouble(partsCopy.get(0).toString());
                    }
                }
            }
        } else {
            if (partsCopy.get(0).charAt(0) == Num.PI.getSymbol()) {
                this.result = Num.PI.getValue();
            } else {
                this.result = Double.parseDouble(partsCopy.get(0).toString());
            }
        }
    }

    private StringBuffer calculatePercentage (int i, ArrayList<StringBuffer> parts) {
        StringBuffer str = new StringBuffer();

        if (parts.get(i - 1).toString().charAt(0) == Num.PI.getSymbol()) {
            str.append(String.valueOf(Num.PI.getValue() / 100));
        } else {
            str.append(String.valueOf(Double.parseDouble(parts.get(i - 1).toString()) / 100));
        }

        return  str;
    }

    private StringBuffer calculateFraction (int i, ArrayList<StringBuffer> parts) {
        StringBuffer str = new StringBuffer();

        if (parts.get(i - 1).toString().charAt(0) == Num.PI.getSymbol()) {
            if (parts.get(i + 1).toString().charAt(0) == Num.PI.getSymbol()) {
                str.append(String.valueOf(1));
            } else {
                str.append(String.valueOf(Num.PI.getValue() / Double.parseDouble(parts.get(i + 1).toString())));
            }

        } else {
            if (parts.get(i + 1).toString().charAt(0) == Num.PI.getSymbol()) {
                str.append(String.valueOf(Double.parseDouble(parts.get(i - 1).toString()) / Num.PI.getValue()));
            } else {
                str.append(String.valueOf(Double.parseDouble(parts.get(i - 1).toString()) / Double.parseDouble(parts.get(i + 1).toString())));
            }
        }

        return  str;
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
        BigDecimal result = new BigDecimal(0);
        BigDecimal num1;
        char op = opSTR.charAt(0);
        BigDecimal num2;

        if (String.valueOf(Num.PI.getSymbol()).equals(num1STR.toString())) {
            num1 = BigDecimal.valueOf(Num.PI.getValue());
        } else {
            num1 = BigDecimal.valueOf(Double.parseDouble(num1STR.toString()));
        }

        if (String.valueOf(Num.PI.getSymbol()).equals(num2STR.toString())) {
            num2 = BigDecimal.valueOf(Num.PI.getValue());
        } else {
            num2 = BigDecimal.valueOf(Double.parseDouble(num2STR.toString()));
        }

        switch (op) {
            case '+' :
                result = num1.add(num2);
                break;
            case '-' :
                result = num1.subtract(num2);
                break;
        }

        return result.doubleValue();
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

    private boolean isNum (StringBuffer str) {
        boolean isNum = true;

        try {
            Double.parseDouble(str.toString());
        } catch (NumberFormatException ex) {
            if (str.charAt(0) != Num.PI.getSymbol() && !Op.SUM.equals(str.charAt(0))) {
                isNum = false;
            }
        }

        return isNum;
    }

    public void setOperation(String[] operation) {
        for (String part : operation) {
            part = part.replace(',', EspP.COMMA.getSymbol());
            if (isNum(new StringBuffer().append(part)) && !Op.SUM.equals(part.charAt(0))) {
                for (int i = 0; i < part.length(); i++) {
                    addNewNum(part.charAt(i));
                }
            } else if (part.charAt(part.length() - 1) == EspP.PERCENTAGE.getSymbol()) {
                    for (int i = 0; i < part.length() - 1; i++) {
                        if (isNum(new StringBuffer().append(part.charAt(i))) || part.charAt(0) == ',') {
                            addNewNum(part.charAt(i));
                        }
                    }
            } else if (part.equals("pi") || part.equals("phi") || part.equals(String.valueOf(Num.PI.getSymbol()))) {
                addNum(Num.PI);
            } else if (part.equals("menos") || part.equals("substract") || part.equals(String.valueOf(Op.SUB.getSymbol()))) {
                addOp(Op.SUB);
            } else if (part.equals("más") || part.equals("more") || part.equals("sum") || part.equals(String.valueOf(Op.SUM.getSymbol()))) {
                addOp(Op.SUM);
            } else if (part.equals("por") || part.equals("multiplicado") || part.equals("multiply") || part.equals("multiplied") || part.equals("x") || part.equals("*") || part.equals(String.valueOf(Op.MUL.getSymbol()))) {
                addOp(Op.MUL);
            } else if (part.equals("entre") || part.equals("dividido") || part.equals("division") || part.equals("divided") || part.equals(String.valueOf(Op.DIV.getSymbol()))) {
                addOp(Op.DIV);
            } else if (part.equals("elevado") || part.equals("elevated") || part.equals(String.valueOf(Op.POT.getSymbol()))) {
                addOp(Op.POT);
            } else if (part.equals("log") || part.equals("logaritmo") || part.equals("logarithm") || part.equals(String.valueOf(EspA.LOG.getSymbol()))) {
                addEspA(EspA.LOG);
            } else if (part.equals("ln") || part.equals(String.valueOf(EspA.LN.getSymbol()))) {
                addEspA(EspA.LN);
            } else if (part.equals("raíz") || part.equals(String.valueOf(EspA.ROOT.getSymbol()))) {
                addEspA(EspA.ROOT);
            } else if (part.equals("seno") || part.equals("sin") || part.equals(String.valueOf(EspA.SIN.getSymbol()))) {
                addEspA(EspA.SIN);
            } else if (part.equals("coseno") || part.equals("cos") || part.equals(String.valueOf(EspA.COS.getSymbol()))) {
                addEspA(EspA.COS);
            } else if (part.equals("tangente") || part.equals("tan") || part.equals(String.valueOf(EspA.TAN.getSymbol()))) {
                addEspA(EspA.TAN);
            } else if (part.equals("partido") || part.equals("split") || part.equals("fracción") || part.equals(String.valueOf(EspP.FRACTION.getSymbol()))) {
                addEspP(EspP.FRACTION);
            }
        }
    }

    private void addNewNum(char num) {
        switch (num) {
            case '0' :
                addNum(Num.ZERO);
                break;
            case '1' :
                addNum(Num.ONE);
                break;
            case '2' :
                addNum(Num.TWO);
                break;
            case '3' :
                addNum(Num.THREE);
                break;
            case '4' :
                addNum(Num.FOUR);
                break;
            case '5' :
                addNum(Num.FIVE);
                break;
            case '6' :
                addNum(Num.SIX);
                break;
            case '7' :
                addNum(Num.SEVEN);
                break;
            case '8' :
                addNum(Num.EIGHT);
                break;
            case '9' :
                addNum(Num.NINE);
                break;
            case '.' :
                addEspP(EspP.COMMA);
                break;
        }
    }
}
