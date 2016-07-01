package com.halilibrahimaksoy.borsaalarm.model;

import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by haksoy on 4/18/2016.
 */
public class AlarmHolder {
     private    EditText edtAlarmMinValue;
    private   EditText edtAlarmMaxValue;
    private   TextView txtAlarmActiveValue;
    private   TextView txtAlarmCompanyName;

    public EditText getEdtAlarmMinValue() {
        return edtAlarmMinValue;
    }

    public void setEdtAlarmMinValue(EditText edtAlarmMinValue) {
        this.edtAlarmMinValue = edtAlarmMinValue;
    }

    public EditText getEdtAlarmMaxValue() {
        return edtAlarmMaxValue;
    }

    public void setEdtAlarmMaxValue(EditText edtAlarmMaxValue) {
        this.edtAlarmMaxValue = edtAlarmMaxValue;
    }

    public TextView getTxtAlarmActiveValue() {
        return txtAlarmActiveValue;
    }

    public void setTxtAlarmActiveValue(TextView txtAlarmActiveValue) {
        this.txtAlarmActiveValue = txtAlarmActiveValue;
    }

    public TextView getTxtAlarmCompanyName() {
        return txtAlarmCompanyName;
    }

    public void setTxtAlarmCompanyName(TextView txtAlarmCompanyName) {
        this.txtAlarmCompanyName = txtAlarmCompanyName;
    }
}

