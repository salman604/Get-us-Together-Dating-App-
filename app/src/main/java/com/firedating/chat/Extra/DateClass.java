package com.firedating.chat.Extra;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.firedating.chat.R;

import java.util.Calendar;

public class DateClass extends DialogFragment {
    @NonNull
    public Dialog onCreateDialog(@Nullable Bundle bundle) {
        Calendar instance = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), R.style.DialogTheme, (DatePickerDialog.OnDateSetListener) getActivity(), instance.get(1), instance.get(2), instance.get(5));
        return datePickerDialog;
    }
}
