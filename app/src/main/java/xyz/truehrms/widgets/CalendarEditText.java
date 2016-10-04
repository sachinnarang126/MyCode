package xyz.truehrms.widgets;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;

import com.rengwuxian.materialedittext.MaterialAutoCompleteTextView;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

import xyz.truehrms.R;


public class CalendarEditText extends MaterialAutoCompleteTextView implements AdapterView.OnItemClickListener, com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener {

    private final int MAX_CLICK_DURATION = 200;
    public DatePickerDialog dpd;
//    public boolean enableTouch = true;
    private Activity ctx;
    private long startClickTime;
    private boolean isPopup;
    private boolean isEnableTouch = true;

    public CalendarEditText(Context context) {
        super(context);
        ctx = (Activity) context;
        setOnItemClickListener(this);
        Calendar calendar = Calendar.getInstance();
        dpd = DatePickerDialog.newInstance(
                this, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
    }

    public CalendarEditText(Context arg0, AttributeSet arg1) {
        super(arg0, arg1);
        ctx = (Activity) arg0;
        setOnItemClickListener(this);
        Calendar calendar = Calendar.getInstance();
        dpd = DatePickerDialog.newInstance(
                this, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
    }

    public CalendarEditText(Context arg0, AttributeSet arg1, int arg2) {
        super(arg0, arg1, arg2);
        setOnItemClickListener(this);
        ctx = (Activity) arg0;
        Calendar calendar = Calendar.getInstance();
        dpd = DatePickerDialog.newInstance(
                this, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
    }

    @Override
    public boolean enoughToFilter() {
        return true;
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        if (focused) {
//            performFiltering("", 0);
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getWindowToken(), 0);
            setKeyListener(null);
            dismissDropDown();
        } else {
            isPopup = false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (isEnableTouch){
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    startClickTime = Calendar.getInstance().getTimeInMillis();
                    break;
                }
                case MotionEvent.ACTION_UP: {
                    long clickDuration = Calendar.getInstance().getTimeInMillis() - startClickTime;
                    if (clickDuration < MAX_CLICK_DURATION) {
                        if (isPopup) {
                            dismissDropDown();
                            isPopup = false;
                        } else {
                            requestFocus();
                            dpd.show(ctx.getFragmentManager(), "Datepickerdialog");

//                        showDropDown();
                            isPopup = true;
                        }
                    }
                }
            }
        }

        return super.onTouchEvent(event);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        isPopup = false;
    }

    @Override
    public void setCompoundDrawablesWithIntrinsicBounds(Drawable left, Drawable top, Drawable right, Drawable bottom) {
        Drawable dropdownIcon = ContextCompat.getDrawable(getContext(), R.drawable.ic_calendar);
        if (dropdownIcon != null) {
            right = dropdownIcon;
            //right.mutate().setAlpha(66);
        }
        super.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
    }

    @Override
    public void onDateSet(com.wdullaer.materialdatetimepicker.date.DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
        setText(date);
        //ApplyLeaveFragment.chk();
    }

    public void setMinDate(Calendar calendar) {
        if (dpd != null)
            dpd.setMinDate(calendar);
    }

    public void setMaxDate(Calendar calendar) {
        if (dpd != null)
            dpd.setMaxDate(calendar);
    }

    /*public void setDate(Calendar calendar) {
        if (dpd != null)
            dpd.initialize();
    }*/

    public void setEnableTouch(boolean enableTouch) {
        isEnableTouch = enableTouch;
    }
}
