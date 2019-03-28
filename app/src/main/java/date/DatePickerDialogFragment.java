package date;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by santosh.kathait on 27-11-2017.
 */

public class DatePickerDialogFragment extends DialogFragment implements
        DatePickerDialog.OnDateSetListener {
    private TextView textView;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }


    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, monthOfYear, dayOfMonth);
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

        if(textView != null)
            textView.setText(format.format(calendar.getTime()));
    }

    public void setTextViewToSetDateOnDateSet(TextView textView){
        this.textView = textView;
    }

}

