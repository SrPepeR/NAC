package com.srpeper.calc.objects;

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

public class Converter {

    private Type type;
    private StringBuffer valueSTR;
    private Double result;

    private String valueMag;
    private int valueSelectedPos;
    private String resultMag;
    private int resultSelectedPos;

    public Converter () {
        this.type = Type.LENGTH;
        this.valueSTR = new StringBuffer();
        this.result = 0.0;
        this.valueMag = Length.M.getSymbol();
        this.resultMag = Length.KM.getSymbol();
    }

    public void addNum (Num num) {
        this.valueSTR.append(num.getSymbol());
        changeResult();
    }

    public void addComma (EspP comma) {
        if (checkComma()) {
            this.valueSTR.append(comma.getSymbol());
            changeResult();
        }
    }

    private boolean checkComma() {
        boolean isValid = true;

        if (this.valueSTR.length() >= 1) {
            for (int i = 0; i < this.valueSTR.length(); i++) {
                if (this.valueSTR.charAt(i) == EspP.COMMA.getSymbol()) {
                    isValid = false;
                }
            }
        } else {
            isValid = false;
        }

        return isValid;
    }

    public void removeOne () {
        if (this.valueSTR.length() > 0) {
            this.valueSTR.deleteCharAt(this.valueSTR.length() - 1);
            changeResult();
        }
    }

    public void clear () {
        this.valueSTR = new StringBuffer();
        this.valueSTR.append("0");
        changeResult();
    }

    public void changeType (Type type) {
        this.type = type;
    }

    public void changeResult () {
        if (this.valueSTR.length() > 0) {
            switch (this.type) {
                case LENGTH :
                    convertLength();
                    break;
                case AREA :
                    convertArea();
                    break;
                case VOLUME :
                    convertVolume();
                    break;
                case SPEED :
                    convertSpeed();
                    break;
                case TIME :
                    convertTime();
                    break;
                case WEIGHT :
                    convertWeight();
                    break;
                case TEMP :
                    convertTemperature();
                    break;
                case MONEY :
                    convertMoney();
                    break;
                case DATA :
                    convertData();
                    break;
            }
        } else {
            this.result = 0.0;
        }

    }

    private void convertLength () {
        if (this.valueSTR.length() > 0) {
            Length valueLength = getLength(this.valueMag);
            Length resultLength = getLength(this.resultMag);

            this.result = (Double.parseDouble(this.valueSTR.toString()) / valueLength.getValue()) * resultLength.getValue();
        }
    }

    private Length getLength (String magnitudeSTR) {
        Length length = null;

        switch (magnitudeSTR) {
            case "km" :
                length = Length.KM;
                break;
            case "hm" :
                length = Length.HM;
                break;
            case "dam" :
                length = Length.DAM;
                break;
            case "m" :
                length = Length.M;
                break;
            case "dm" :
                length = Length.DM;
                break;
            case "cm" :
                length = Length.CM;
                break;
            case "mm" :
                length = Length.MM;
                break;
        }

        return length;
    }

    private void convertArea () {
        if (this.valueSTR.length() > 0) {
            Area valueArea = getArea(this.valueMag);
            Area resultArea = getArea(this.resultMag);

            this.result = (Double.parseDouble(this.valueSTR.toString()) / valueArea.getValue()) * resultArea.getValue();
        }
    }

    private Area getArea (String magnitudeSTR) {
        Area area = null;

        switch (magnitudeSTR) {
            case "km\u00B2" :
                area = Area.KM;
                break;
            case "hm\u00B2" :
                area = Area.HM;
                break;
            case "dam\u00B2" :
                area = Area.DAM;
                break;
            case "m\u00B2" :
                area = Area.M;
                break;
            case "dm\u00B2" :
                area = Area.DM;
                break;
            case "cm\u00B2" :
                area = Area.CM;
                break;
            case "mm\u00B2" :
                area = Area.MM;
                break;
        }

        return area;
    }

    private void convertVolume () {
        if (this.valueSTR.length() > 0) {
            Volume valueVolume = getVolume(this.valueMag);
            Volume resulVolume = getVolume(this.resultMag);

            this.result = (Double.parseDouble(this.valueSTR.toString()) / valueVolume.getValue()) * resulVolume.getValue();
        }
    }

    private Volume getVolume (String magnitudeSTR) {
        Volume volume = null;

        switch (magnitudeSTR) {
            case "km\u00B3" :
                volume = Volume.KM;
                break;
            case "hm\u00B3" :
                volume = Volume.HM;
                break;
            case "dam\u00B3" :
                volume = Volume.DAM;
                break;
            case "m\u00B3" :
                volume = Volume.M;
                break;
            case "dm\u00B3" :
                volume = Volume.DM;
                break;
            case "cm\u00B3" :
                volume = Volume.CM;
                break;
            case "mm\u00B3" :
                volume = Volume.MM;
                break;
        }

        return volume;
    }

    private void convertSpeed () {
        if (this.valueSTR.length() > 0) {
            Speed valueSpeed = getSpeed(this.valueMag);
            Speed resultSpeed = getSpeed(this.resultMag);

            this.result = (Double.parseDouble(this.valueSTR.toString()) / valueSpeed.getValue()) * resultSpeed.getValue();
        }
    }

    private Speed getSpeed (String magnitudeSTR) {
        Speed speed = null;

        switch (magnitudeSTR) {
            case "km/h" :
                speed = Speed.KM;
                break;
            case "mph" :
                speed = Speed.MPH;
                break;
            case "m/s" :
                speed = Speed.M;
                break;
            case "kn" :
                speed = Speed.KN;
                break;
            case "ft/s" :
                speed = Speed.FT;
                break;
        }

        return speed;
    }

    private void convertTime () {
        if (this.valueSTR.length() > 0) {
            Time valueTime = getTime(this.valueMag);
            Time resultTime = getTime(this.resultMag);

            this.result = (Double.parseDouble(this.valueSTR.toString()) / valueTime.getValue()) * resultTime.getValue();
        }
    }

    private Time getTime (String magnitudeSTR) {
        Time time = null;

        switch (magnitudeSTR) {
            case "day" :
                time = Time.D;
                break;
            case "hour" :
                time = Time.H;
                break;
            case "minute" :
                time = Time.M;
                break;
            case "second" :
                time = Time.S;
                break;
            case "millisecond" :
                time = Time.MS;
                break;
            case "día" :
                time = Time.D;
                break;
            case "hora" :
                time = Time.H;
                break;
            case "minuto" :
                time = Time.M;
                break;
            case "segundo" :
                time = Time.S;
                break;
            case "milisegundo" :
                time = Time.MS;
                break;
        }

        return time;
    }

    private void convertWeight () {
        if (this.valueSTR.length() > 0) {
            Weight valueWeight = getWeight(this.valueMag);
            Weight resultWeight = getWeight(this.resultMag);

            this.result = (Double.parseDouble(this.valueSTR.toString()) / valueWeight.getValue()) * resultWeight.getValue();
        }
    }

    private Weight getWeight (String magnitudeSTR) {
        Weight weight = null;

        switch (magnitudeSTR) {
            case "ton" :
                weight = Weight.TON;
                break;
            case "kilogram" :
                weight = Weight.KG;
                break;
            case "gram" :
                weight = Weight.GR;
                break;
            case "pound" :
                weight = Weight.POUND;
                break;
            case "ounce" :
                weight = Weight.OZ;
                break;
            case "tonelada" :
                weight = Weight.TON;
                break;
            case "kilogramo" :
                weight = Weight.KG;
                break;
            case "gramo" :
                weight = Weight.GR;
                break;
            case "libra" :
                weight = Weight.POUND;
                break;
            case "onza" :
                weight = Weight.OZ;
                break;
        }

        return weight;
    }

    private void convertTemperature () {
        if (this.valueSTR.length() > 0) {
            Temperature valueTemperature = getTemperature(this.valueMag);
            Temperature resultTemperature = getTemperature(this.resultMag);

            this.result = (Double.parseDouble(this.valueSTR.toString()) / valueTemperature.getValue()) * resultTemperature.getValue();
        }
    }

    private Temperature getTemperature (String magnitudeSTR) {
        Temperature temperature = null;

        switch (magnitudeSTR) {
            case "celsius" :
                temperature = Temperature.CELSIUS;
                break;
            case "kelvin" :
                temperature = Temperature.KELVIN;
                break;
            case "fahrenheit" :
                temperature = Temperature.FAHRENHEIT;
                break;
        }

        return temperature;
    }

    private void convertMoney () {
        if (this.valueSTR.length() > 0) {
            Money valueMoney = getMoney(this.valueMag);
            Money resultMoney = getMoney(this.resultMag);

            this.result = (Double.parseDouble(this.valueSTR.toString()) / valueMoney.getValue()) * resultMoney.getValue();
        }
    }

    private Money getMoney (String magnitudeSTR) {
        Money money = null;

        switch (magnitudeSTR) {
            case "euro" :
                money = Money.EURO;
                break;
            case "dollar" :
                money = Money.DOLLAR;
                break;
            case "pound" :
                money = Money.POUND;
                break;
            case "ruble" :
                money = Money.RUBLE;
                break;
            case "dólar" :
                money = Money.DOLLAR;
                break;
            case "libra" :
                money = Money.POUND;
                break;
            case "rublo" :
                money = Money.RUBLE;
                break;
            case "yen" :
                money = Money.YEN;
                break;
            case "bitcoin" :
                money = Money.BITCOIN;
                break;
        }

        return money;
    }

    private void convertData () {
        if (this.valueSTR.length() > 0) {
            Data valueData = getData(this.valueMag);
            Data resultData = getData(this.resultMag);

            this.result = (Double.parseDouble(this.valueSTR.toString()) / valueData.getValue()) * resultData.getValue();
        }
    }

    private Data getData (String magnitudeSTR) {
        Data data = null;

        switch (magnitudeSTR) {
            case "petabyte" :
                data = Data.PB;
                break;
            case "terabyte" :
                data = Data.TB;
                break;
            case "gigabyte" :
                data = Data.GB;
                break;
            case "megabyte" :
                data = Data.MB;
                break;
            case "kilobyte" :
                data = Data.KB;
                break;
            case "byte" :
                data = Data.B;
                break;
        }

        return data;
    }


    public void setValueMag(String valueMag) {
        this.valueMag = valueMag;
    }

    public void setResultMag(String resultMag) {
        this.resultMag = resultMag;
    }

    public String getValue() {
        return this.valueSTR.toString();
    }

    public String getResult() {
        return String.valueOf(this.result);
    }

    public void setValueSelectedPos(int valueSelectedPos) {
        this.valueSelectedPos = valueSelectedPos;
    }

    public void setResultSelectedPos(int resultSelectedPos) {
        this.resultSelectedPos = resultSelectedPos;
    }

    public String getType() {
        return type.getSymbol();
    }
}
