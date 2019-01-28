package com.patchpets.Activitys;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.patchpets.R;
import com.patchpets.databinding.ActivityAdsBinding;
import com.patchpets.utils.HeaderBar;
import com.patchpets.utils.LogM;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class AdsActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityAdsBinding binding;
    private HeaderBar headerBar;
    private Spinner spinnerPosition, spinnerTimeSlot;
    TextView tvPosition, tvTimeslot;
    EditText etAdTitle;
    private List<String> alPosition = new ArrayList<>();
    private List<String> alTimeSlot = new ArrayList<>();
    TextView etStartDate, etEndDate;
    private RelativeLayout rlPositionSpinner, rlTimeSlot;
    Button btnSubmit;
    ImageButton ibSpinnerArrow, ibSpinnerArrow1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_ads);
        bindViews();
        setListener();
        setPositionSpinner();
        setTimeslotSpinner();
    }

    private void bindViews() {
        headerBar = binding.headerBar;
        spinnerPosition = binding.spinnerPosition;
        spinnerTimeSlot = binding.spinnerTimeSlot;
        tvPosition = binding.tvPosition;
        rlPositionSpinner = binding.rlPositionspinner;
        rlTimeSlot = binding.rlTimeSlot;
        tvTimeslot = binding.tvTimeSlot;
        etStartDate = binding.etStartDate;
        etEndDate = binding.etEndDate;
        etAdTitle = binding.etAdTitle;
        btnSubmit = binding.btnSubmit;
        ibSpinnerArrow = binding.ibSpinnerArrow;
        ibSpinnerArrow1 = binding.ibSpinnerArrow1;
        headerBar.tvTitle.setText(getResources().getString(R.string.ads));
        headerBar.ibLeft.setImageDrawable(getResources().getDrawable(R.drawable.back));


    }

    private void setListener() {
        headerBar.ibLeft.setOnClickListener(this);
        headerBar.ibRight.setOnClickListener(this);
        rlPositionSpinner.setOnClickListener(this);
        rlTimeSlot.setOnClickListener(this);
        etStartDate.setOnClickListener(this);
        etEndDate.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        ibSpinnerArrow.setOnClickListener(this);
        ibSpinnerArrow1.setOnClickListener(this);
        etAdTitle.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //  Log.e("eventnames",event.getAction())
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    //   spinnerPosition.performClick();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(etAdTitle.getWindowToken(), 0);

                    spinnerPosition.requestFocus();

//spinnerPosition.performClick();
                    // Perform action on Enter key press
                    return true;
                }
                return false;
            }
        });
      /*  etAdTitle.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Toast.makeText(getApplicationContext(),"nexte",Toast.LENGTH_SHORT).show();
                if (event.getAction() == EditorInfo.IME_ACTION_NEXT) {
                    spinnerPosition.performClick();
                    // Perform action on Enter key press

                    return true;
                }
                return false;
            }
        });*/

    }

    private void setPositionSpinner() {
        try {
            String[] positions = getResources().getStringArray(R.array.positions);
            alPosition = new ArrayList<>(Arrays.asList(positions));
            alPosition.add(getResources().getString(R.string.select_position));
            final int alSize = alPosition.size() - 1;

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(AdsActivity.this, R.layout.item_spinner, alPosition) {
                @Override
                public int getCount() {
                    return (alSize);
                }

                @NonNull
                @Override
                public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                    return super.getView(position, convertView, parent);
                }
            };
            dataAdapter.setDropDownViewResource(R.layout.spinner_dropdown_ads);
            spinnerPosition.setAdapter(dataAdapter);
            spinnerPosition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    tvPosition.setText(alPosition.get(i));
                    if (i != alSize) {
                        tvPosition.setTextColor(getResources().getColor(R.color.text_color));
                    } else {
                        tvPosition.setTextColor(getResources().getColor(R.color.text_hint_color));
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
            spinnerPosition.setSelection(alSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void setTimeslotSpinner() {
        try {
            String[] timeslot = getResources().getStringArray(R.array.timeslot);
            alTimeSlot = new ArrayList<>(Arrays.asList(timeslot));
            alTimeSlot.add(getResources().getString(R.string.select_timeslot));
            final int alSize = alTimeSlot.size() - 1;

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(AdsActivity.this, R.layout.item_spinner, alTimeSlot) {
                @Override
                public int getCount() {
                    return (alSize);
                }

                @NonNull
                @Override
                public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                    return super.getView(position, convertView, parent);
                }
            };

            dataAdapter.setDropDownViewResource(R.layout.spinner_dropdown_ads);

            spinnerTimeSlot.setAdapter(dataAdapter);
            spinnerTimeSlot.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    tvTimeslot.setText(alTimeSlot.get(i));
                    if (i != alSize) {
                        tvTimeslot.setTextColor(getResources().getColor(R.color.text_color));
                    } else {
                        tvTimeslot.setTextColor(getResources().getColor(R.color.text_hint_color));
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
            spinnerTimeSlot.setSelection(alSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openDatepicker(final TextView edt) {
        int mYear, mMonth, mDay, maxYear;
        final Calendar cal = Calendar.getInstance();
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);
        maxYear = mYear - 18;
        DatePickerDialog dpd = new DatePickerDialog(AdsActivity.this,/* R.style.DialogTheme,*/
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        Calendar minAdultAge = new GregorianCalendar();
                        minAdultAge.add(Calendar.YEAR, -90);

                        int dayofmonth = monthOfYear + 1;
//                        tvDOB.setText(dayOfMonth + "/" + dayofmonth + "/" + year);
                        edt.setText(dayOfMonth + "/" + dayofmonth + "/" + year);

                        //strAge = getAge(year, dayofmonth, dayOfMonth);

                    }
                }, mYear, mMonth, mDay);
        Calendar c = Calendar.getInstance();
        // Calendar cal = Calendar.getInstance();
        c.add(Calendar.DAY_OF_YEAR, 1);
        //c.set(maxYear, mMonth, mDay);
        dpd.getDatePicker().setMinDate(c.getTimeInMillis());
        dpd.show();
    }

    @Override
    public void onClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.ibLeft:
                    finish();
                    break;
                case R.id.rlPositionspinner:
                    spinnerPosition.performClick();
                    break;
                case R.id.rlTimeSlot:
                    spinnerTimeSlot.performClick();
                    break;
                case R.id.etStartDate:
                    openDatepicker((TextView) view);
                    break;
                case R.id.etEndDate:
                    if (etStartDate.getText().toString().equalsIgnoreCase("")) {

                    } else {
                        openDatepicker((TextView) view);
                    }
                    break;

                case R.id.btnSubmit:
                    Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    finish();
                    break;
                case R.id.ibSpinnerArrow:
                    spinnerPosition.performClick();
                    break;
                case R.id.ibSpinnerArrow1:
                    spinnerTimeSlot.performClick();
                    break;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
