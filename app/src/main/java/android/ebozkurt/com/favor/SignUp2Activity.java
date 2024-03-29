package android.ebozkurt.com.favor;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.ebozkurt.com.favor.domain.helpers.JSONResponse;
import android.ebozkurt.com.favor.helpers.ActivityHelper;
import android.ebozkurt.com.favor.helpers.AnimationHelper;
import android.ebozkurt.com.favor.helpers.DateValidator;
import android.ebozkurt.com.favor.network.BoonApiInterface;
import android.ebozkurt.com.favor.network.RetrofitBuilder;
import android.ebozkurt.com.favor.views.LoadingDialogFragment;
import android.support.design.widget.TextInputLayout;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SignUp2Activity extends ActivityHelper {

    ImageButton actionBarBack;
    TextView cancelTextView;
    EditText email, birthdate;
    TextInputLayout emailTextInputLayout, birthdateTextInputLayout;
    Button nextButton;
    View actionBarBackground1, actionBarBackground2, actionBarBackground3, actionBarBackground4;
    DatePickerDialog.OnDateSetListener date;

    Animation shake;

    Calendar myCalendar;

    ArrayList<String> takenMailList = new ArrayList<>();

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up2);
        ActivityHelper.initialize(this);
        ActivityHelper.hideKeyboardWhenEdittextNotFocused(getWindow().getDecorView().getRootView(), SignUp2Activity.this);
        ActivityHelper.transparentStatusBar(getWindow(), getResources());

        shake = AnimationUtils.loadAnimation(this, R.anim.button_shake_animation);


        actionBarBackground1 = (View) findViewById(R.id.sign_up_action_bar_background_view1);
        actionBarBackground2 = (View) findViewById(R.id.sign_up_action_bar_background_view2);
        actionBarBackground3 = (View) findViewById(R.id.sign_up_action_bar_background_view3);
        actionBarBackground4 = (View) findViewById(R.id.sign_up_action_bar_background_view4);
        actionBarBackground1.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        actionBarBackground2.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));


        actionBarBack = (ImageButton) findViewById(R.id.sign_up1_action_bar_image_button);
        cancelTextView = (TextView) findViewById(R.id.sign_up1_action_bar_right_text_view);
        cancelTextView.setText(R.string.cancel);
        cancelTextView.setVisibility(View.VISIBLE);
        email = (EditText) findViewById(R.id.activity_sign_up2_email_editText);
        birthdate = (EditText) findViewById(R.id.activity_sign_up2_birthdate_editText);
        emailTextInputLayout = (TextInputLayout) findViewById(R.id.activity_sign_up2_email_text_input_layout);
        birthdateTextInputLayout = (TextInputLayout) findViewById(R.id.activity_sign_up2_birthdate_text_input_layout);
        nextButton = (Button) findViewById(R.id.activity_sign_up2_next_button);
        nextButton.setEnabled(false);

        myCalendar = Calendar.getInstance();
/*
        email.setFilters(new InputFilter[] {
                new InputFilter.AllCaps() {
                    @Override
                    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                        return String.valueOf(source).toLowerCase(Locale.ENGLISH);
                    }
                }
        });
*/


/*
        if (emailTextInputLayout.getError() == null) {
        //email.setTextAppearance(R.style.TextfieldText_Error);
        } else {
            emailTextInputLayout.setErrorTextAppearance(R.style.SignUpTextInputLayoutError);
            //email.setTextAppearance(R.style.SignUpTextInputLayoutError);
        }
*/
        /*
        final CharSequence birthdateHintShort = getString(R.string.birth_date);
        final CharSequence birthdateHintLong = getString(R.string.birth_date_you_must_be_over_18);
        */

        actionBarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.right1, R.anim.right2);

            }
        });

        cancelTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignUp2Activity.this, InitialActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                //slide down
                overridePendingTransition(R.anim.down1, R.anim.down2);
            }
        });


        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && !Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches() && email.length() > 0) {
                    emailTextInputLayout.setError(getString(R.string.invalid_email_address));
                }
            }
        });

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
                    emailTextInputLayout.setError(null);
                    emailTextInputLayout.setErrorEnabled(false);
                }
                if (email.getText().length() == 0) {
                    emailTextInputLayout.setError(null);
                    emailTextInputLayout.setErrorEnabled(false);
                }

                if(emailInUse()) {
                    emailTextInputLayout.setError(getString(R.string.email_already_in_use));
                }

                enableButtonifOK();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        birthdate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                birthdateTextInputLayout.setError(null);
                birthdateTextInputLayout.setErrorEnabled(false);
                enableButtonifOK();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


/*
        birthdate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && birthdate.length() > 0) {
                    //check if date is valid
                    String text = birthdate.getText().toString();
                    final String [] divided = text.split("\\.+|\\/+|-+|,+|\\s+");
                    //String[] numbers = text.split("");


                }
            }
        });
*/

        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };


        birthdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(birthdate.getWindowToken(), 0);

                if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches() && email.length() > 0) {
                    emailTextInputLayout.setError(getString(R.string.invalid_email_address));
                }
                if (birthdate.getText().length() < 1)
                    birthdate.setText(" ");
                DatePickerDialog dialog = new DatePickerDialog(SignUp2Activity.this, R.style.MyDatePickerDialogTheme, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                if (birthdate.getText().length() == 10) {
                    String birthdateText = birthdate.getText().toString();
                    String[] divided = birthdateText.split("\\.+|\\/+|-+|,+|\\s+");
                    int day = Integer.parseInt(divided[0]);
                    int month = Integer.parseInt(divided[1]);
                    int year = Integer.parseInt(divided[2]);
                    dialog.updateDate(year, month, day);
                } else dialog.updateDate(1994, 0, 1);
                dialog.getDatePicker().setMaxDate(new Date().getTime());
                dialog.setTitle("");
                dialog.show();
/*
                dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        if (birthdate.getText().length() < 1) {
                            birthdate.setText("");
                        }
                    }
                });

                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        if (birthdate.getText().length() < 1)
                            birthdate.setText("");
                    }
                });
*/
            }
        });


        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get birthdate text and check if valid
                String birthdateText = birthdate.getText().toString();
                String[] divided = birthdateText.split("\\.+|\\/+|-+|,+|\\s+");
                if (divided.length == 3 && DateValidator.isThisDateValid(divided[0] + "/" + divided[1] + "/" + divided[2], "dd/MM/yyyy")) {
                    // DateValidator.isThisDateValid(divided[0]+"/"+divided[1]+"/"+divided[2], "dd/MM/yyyy");

                    int day = Integer.parseInt(divided[0]);
                    int month = Integer.parseInt(divided[1]);
                    int year = Integer.parseInt(divided[2]);

                    Log.i("dev", Arrays.toString(divided));
                   /* int lengthYear = String.valueOf(year).length();
                   if (lengthYear == 2 && year > 20) {
                        year =  + divided[2]);

                    }*/

                    Calendar dob = Calendar.getInstance();
                    Calendar today = Calendar.getInstance();
                    dob.set(year, month, day);
                    final String formattedBirthdate = year + "-" + month + "-" + day;


                    int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

                    if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
                        age--;
                    }

                    Integer ageInt = new Integer(age);

                    if (ageInt < 18) {
                        birthdateTextInputLayout.setError(getString(R.string.you_must_be_over));
                    } else if (ageInt >= 100) {
                        birthdateTextInputLayout.setError(getString(R.string.invalid_age));
                    } else {

                        BoonApiInterface apiService = RetrofitBuilder.returnService();
                        Log.i("dev", "onClick: " + ActivityHelper.getTrimmedString(email));
                        Call<JSONResponse> call = apiService.isEmailRegistered(ActivityHelper.getTrimmedString(email));
                        final LoadingDialogFragment loadingDialogFragment = ActivityHelper.getLoadingDialog();
                        loadingDialogFragment.show(getSupportFragmentManager(), "");
                        call.enqueue(new Callback<JSONResponse>() {
                            @Override
                            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                                loadingDialogFragment.dismiss();

                                if (response.body().isSuccess()) {
                                    boolean isEmailRegistered = response.body().getPayload().equals(Boolean.TRUE);
                                    if (isEmailRegistered) {
                                        AnimationHelper.initializeShakeAnimation(SignUp2Activity.this, nextButton);
                                        emailTextInputLayout.setError(getString(R.string.email_already_in_use));
                                        nextButton.setEnabled(false);
                                        takenMailList.add(ActivityHelper.getTrimmedString(email));
                                    } else {
                                        Intent i = new Intent(SignUp2Activity.this, SignUp3Activity.class);
                                        i.putExtra("name", getIntent().getStringExtra("name"));
                                        i.putExtra("lastname", getIntent().getStringExtra("lastname"));
                                        i.putExtra("email", ActivityHelper.getTrimmedString(email));
                                        i.putExtra("birthdate", formattedBirthdate);
                                        i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                        startActivity(i);
                                    }
                                } else {
                                    AnimationHelper.initializeShakeAnimation(SignUp2Activity.this, nextButton);
                                    ActivityHelper.DisplayCustomToast(SignUp2Activity.this, getResources().getString(R.string.general_error), Toast.LENGTH_LONG);
                                }
                                Log.i("dev", "onResponse: " + response.body().getPayload().toString() + "\t" + Boolean.TRUE.toString());
                            }


                            @Override
                            public void onFailure(Call<JSONResponse> call, Throwable t) {
                                loadingDialogFragment.dismiss();
                                AnimationHelper.initializeShakeAnimation(SignUp2Activity.this, nextButton);
                                ActivityHelper.DisplayCustomToast(SignUp2Activity.this, getResources().getString(R.string.general_error), Toast.LENGTH_LONG);

                            }
                        });
                    }

                } else {
                    Toast.makeText(SignUp2Activity.this, "Not valid date", Toast.LENGTH_SHORT).show();
                    nextButton.setEnabled(false);
                }
            }
        });

    }

    public void enableButtonifOK() {
        if (email.length() > 0 && birthdate.length() > 0 && emailTextInputLayout.getError() == null && Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches() && !emailInUse()) {
            nextButton.setEnabled(true);
        } else nextButton.setEnabled(false);
    }

    private void updateLabel() {

        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        birthdate.setText(sdf.format(myCalendar.getTime()));
    }

    private boolean emailInUse() {
        if (takenMailList.contains(ActivityHelper.getTrimmedString(email))) {
            return true;
        } else return false;
    }

}
