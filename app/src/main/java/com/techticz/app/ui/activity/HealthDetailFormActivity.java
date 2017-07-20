package com.techticz.app.ui.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.techticz.app.R;
import com.techticz.app.base.BaseActivity;
import com.techticz.app.domain.model.pojo.HealthProfile;
import com.techticz.app.utility.AppNavigator;
import com.techticz.app.utility.CommonUtils;

import java.util.Calendar;

public class HealthDetailFormActivity extends BaseActivity {

    private DatePickerDialog.OnDateSetListener dateSelectListner = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            updateDateOfBirth(year, month, day);
        }
    };
    private ViewGroup dobView;
    private ViewGroup genderView;
    private ViewGroup foodPrefView;
    private ViewGroup nonvgFoodPrefView;
    private ViewGroup activityLevelView;
    private ViewGroup heightView;
    private ViewGroup weightView;
    private ViewGroup prescNutriView;
    private HealthProfile profile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_detail_form);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                handleSubmitClick();
            }
        });

        initUI();
    }

    private void handleSubmitClick() {
        captureProfileData();
        if (!isValidated()) {
            return;
        }

        getNavigator().navigateToHealtGoalActivity();

    }

    private void captureProfileData() {
        HealthProfile profile = new HealthProfile();
        profile.setDob(getProfileValue(dobView));
        profile.setGender(getProfileValue(genderView));
        profile.setFoodPref(getProfileValue(foodPrefView));
        profile.setNonVegPref(getProfileValues(nonvgFoodPrefView));
        profile.setActivityLevel(getProfileValue(activityLevelView));
        //  profile.setHeight(Integer.parseInt(getProfileValue(heightView)));
        // profile.setWeight(Integer.parseInt(getProfileValue(weightView)));
        profile.setPrescribedNutri(getProfileValues(prescNutriView));
        this.profile = profile;
    }

    private CharSequence[] getProfileValues(ViewGroup vg) {
        CharSequence[] values = (CharSequence[]) vg.getTag();
        return values;
    }

    private String getProfileValue(ViewGroup vg) {
        CharSequence value = ((EditText) vg.findViewById(R.id.et_value)).getText();

        return value.toString();
    }

    private boolean isValidated() {
        if (profile == null) return false;
        return true;
    }

    private void initUI() {
        LinearLayout ll_container = (LinearLayout) findViewById(R.id.ll_profile_item_container);
        ll_container.removeAllViews();
        dobView = getProfileItemView(R.drawable.ic_refresh, "Date Of Birth");
        genderView = getProfileItemView(R.drawable.ic_wc, "Sex");
        foodPrefView = getProfileItemView(R.drawable.ic_action_restaurant, "Food Preference");
        nonvgFoodPrefView = getProfileItemView(R.drawable.ic_action_restaurant, "Non-veg Preference");
        activityLevelView = getProfileItemView(R.drawable.ic_directions_run, "Activity Level");
        heightView = getProfileItemView(R.drawable.ic_accessibility, "Height(cm)");
        weightView = getProfileItemView(R.drawable.ic_accessibility, "Weight(kg)");
        prescNutriView = getProfileItemView(R.drawable.ic_action_pill, "Prescribed Nutritients");
        ll_container.addView(dobView);
        ll_container.addView(genderView);
        ll_container.addView(foodPrefView);
        ll_container.addView(nonvgFoodPrefView);
        ll_container.addView(activityLevelView);
        ll_container.addView(heightView);
        ll_container.addView(weightView);
        ll_container.addView(prescNutriView);
    }

    private ViewGroup getProfileItemView(int res, String label) {
        ViewGroup vg = (ViewGroup) View.inflate(this, R.layout.profile_info_item_layout, null);

        ((ImageView) vg.findViewById(R.id.iv)).setImageResource(res);
        ((TextView) vg.findViewById(R.id.tv_label)).setText(label);
        if (label.equals("Height(cm)")) {
            ((EditText) vg.findViewById(R.id.et_value)).setFocusable(true);
            ((EditText) vg.findViewById(R.id.et_value)).setHint("Enter Height (cm)");
            ((EditText) vg.findViewById(R.id.et_value)).setInputType(InputType.TYPE_CLASS_NUMBER);
        } else if (label.equals("Weight(kg)")) {
            ((EditText) vg.findViewById(R.id.et_value)).setFocusable(true);
            ((EditText) vg.findViewById(R.id.et_value)).setHint("Enter Weight (kg)");
            ((EditText) vg.findViewById(R.id.et_value)).setInputType(InputType.TYPE_CLASS_NUMBER);
        } else {
            ((EditText) vg.findViewById(R.id.et_value)).setFocusable(false);
        }
        ((EditText) vg.findViewById(R.id.et_value)).setClickable(true);
        ((EditText) vg.findViewById(R.id.et_value)).setTag(label);
        ((EditText) vg.findViewById(R.id.et_value)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleProfileItemBoxClick((String) view.getTag());
            }
        });
        return vg;
    }

    private void handleProfileItemBoxClick(String label) {
        if (label.equals("Date Of Birth")) {
            handleAgeClick();
        } else if (label.equals("Sex")) {
            handleSexClick();
        } else if (label.equals("Food Preference")) {
            handleFoodPreferenceClick();
        } else if (label.equals("Non-veg Preference")) {
            handleNonVegFoodClick();
        } else if (label.equals("Activity Level")) {
            handleActivityLevelClick();
        } else if (label.equals("Height(cm)")) {

        } else if (label.equals("weight(kg)")) {

        } else if (label.equals("Prescribed Nutritients")) {
            handlePrescNutriClick();
        }

    }

    private void handlePrescNutriClick() {
        new MaterialDialog.Builder(this)
                .title(R.string.presc_nutri_dialog_label)
                .items(R.array.presc_nutri_list)
                .itemsCallbackMultiChoice(new Integer[]{-1}, new MaterialDialog.ListCallbackMultiChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                        setProfileItemValues(prescNutriView, text);
                        return true;
                    }
                })
                .positiveText(R.string.choose)
                .show();
    }

    private void handleActivityLevelClick() {
        new MaterialDialog.Builder(this)
                .title(R.string.activity_level_dialog_title)
                .items(R.array.activity_levels)
                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        setProfileItemValue(activityLevelView, text);
                        return true;
                    }
                })
                .positiveText(R.string.choose)
                .show();
    }

    private void handleNonVegFoodClick() {
        new MaterialDialog.Builder(this)
                .title(R.string.non_veg_food_dialog_title)
                .items(R.array.nonveg_food_types)
                .itemsCallbackMultiChoice(new Integer[]{-1}, new MaterialDialog.ListCallbackMultiChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                        setProfileItemValues(nonvgFoodPrefView, text);
                        return true;
                    }
                })
                .positiveText(R.string.choose)
                .show();
    }

    private void setProfileItemValues(ViewGroup vg, CharSequence[] values) {
        vg.setTag(values);
        String result = "";
        int size = values.length;
        for (int i = 0; i < size; i++) {
            result = result + values[i];
            if (i != size - 1) {
                result = result + "\n";
            }
        }
        ((EditText) vg.findViewById(R.id.et_value)).setText(result);
    }

    private void handleFoodPreferenceClick() {
        new MaterialDialog.Builder(this)
                .title(R.string.food_preference_dialog_title)
                .items(R.array.meal_types)
                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        setProfileItemValue(foodPrefView, text);
                        return true;
                    }
                })
                .positiveText(R.string.choose)
                .show();
    }

    private void handleSexClick() {
        new MaterialDialog.Builder(this)
                .title(R.string.gender_dialog_title)
                .items(R.array.gender_list)
                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        setProfileItemValue(genderView, text);
                        return true;
                    }
                })
                .positiveText(R.string.choose)
                .show();
    }

    private void setProfileItemValue(ViewGroup vg, CharSequence text) {
        ((EditText) vg.findViewById(R.id.et_value)).setText(text);
    }

    private void updateDateOfBirth(int year, int month, int day) {
        Calendar dob = Calendar.getInstance();
        dob.set(Calendar.YEAR, year);
        dob.set(Calendar.MONTH, month);
        dob.set(Calendar.DAY_OF_MONTH, day);
        int age = year - Calendar.getInstance().get(Calendar.YEAR);
        String date = CommonUtils.getDisplayDate(dob);
        setProfileItemValue(dobView, date);

    }

    private void handleAgeClick() {
        Calendar today = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this, dateSelectListner, today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    public static Intent getCallingIntent(Context context) {
        Intent i = new Intent(context, HealthDetailFormActivity.class);
        return i;
    }
}
