package android.ebozkurt.com.favor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.ebozkurt.com.favor.domain.User;
import android.ebozkurt.com.favor.domain.helpers.JSONResponse;
import android.ebozkurt.com.favor.helpers.ActivityHelper;
import android.ebozkurt.com.favor.helpers.AnimationHelper;
import android.ebozkurt.com.favor.helpers.CommonOperations;
import android.ebozkurt.com.favor.helpers.PasswordChecker;
import android.ebozkurt.com.favor.helpers.PasswordHintToggler;
import android.ebozkurt.com.favor.helpers.TemporaryHelper;
import android.ebozkurt.com.favor.network.BoonApiInterface;
import android.ebozkurt.com.favor.network.RetrofitBuilder;
import android.ebozkurt.com.favor.views.LoadingDialogFragment;
import android.support.design.widget.TextInputLayout;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class LoginActivity extends ActivityHelper {

    ImageButton backImageButton;
    TextView actionBarCenterText, actionBarRightText;
    Button signInButton;
    TextView termsOfConditions;
    TextInputLayout emailTextInputLayout, passwordTextInputLayout;
    EditText emailEditText, passwordEditText;
    TextView passwordToggleTextView;

    Animation shake;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActivityHelper.initialize(this);
        ActivityHelper.hideKeyboardWhenEdittextNotFocused(getWindow().getDecorView().getRootView(), LoginActivity.this);
        ActivityHelper.transparentStatusBar(getWindow(), getResources());


        backImageButton = (ImageButton) findViewById(R.id.sign_up1_action_bar_image_button);
        actionBarCenterText = (TextView) findViewById(R.id.sign_up1_action_bar_middle_text_view);
        actionBarCenterText.setText(R.string.sign_in);
        actionBarRightText = (TextView) findViewById(R.id.sign_up1_action_bar_right_text_view);
        actionBarRightText.setText(R.string.forgot_password);
        //actionBarRightText.setTypeface(null, Typeface.ITALIC);
        signInButton = (Button) findViewById(R.id.activity_login_sign_in_button);
        signInButton.setEnabled(false);
        //todo remove this
        signInButton.setEnabled(true);
        signInButton.setClipToOutline(true);

        //todo remove this after refresh token implementation & auto login process
        signInButton.setEnabled(true);

        shake = AnimationUtils.loadAnimation(this, R.anim.button_shake_animation);


        termsOfConditions = (TextView) findViewById(R.id.activity_login_terms_and_conditions_textView);
        String formattedTermsOfConditions = String.format(getResources().getString(R.string.by_proceeding_you), getResources().getString(R.string.app_name));
        termsOfConditions.setText(Html.fromHtml(formattedTermsOfConditions));
        termsOfConditions.setMovementMethod(LinkMovementMethod.getInstance());

        emailEditText = (EditText) findViewById(R.id.activity_login_email_editText);
        passwordEditText = (EditText) findViewById(R.id.activity_login_password_editText);
        passwordEditText.setTransformationMethod(null);

        emailTextInputLayout = (TextInputLayout) findViewById(R.id.activity_login_email_text_input_layout);
        passwordTextInputLayout = (TextInputLayout) findViewById(R.id.activity_login_password_text_input_layout);
        passwordToggleTextView = (TextView) findViewById(R.id.activity_login_password_toggle_editText);
        passwordToggleTextView.setVisibility(View.INVISIBLE);
        TemporaryHelper.setLoginInfo(this, emailEditText, passwordEditText);
/*
        emailEditText.setFilters(new InputFilter[] {
                new InputFilter.AllCaps() {
                    @Override
                    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                        return String.valueOf(source).toLowerCase(Locale.ENGLISH);
                    }
                }
        });
*/

        PasswordHintToggler.passwordToggleState(passwordEditText, passwordToggleTextView);


        //for preventing keyboard auto open
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        backImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.right1, R.anim.right2);
            }
        });

        actionBarRightText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, ForgotPassword1Activity.class);
                // i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                //todo continue here
                //slide up
                overridePendingTransition(R.anim.up1, R.anim.up2);

            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final BoonApiInterface apiService = RetrofitBuilder.returnService();
                User user = new User();
                user.setEmail(emailEditText.getText().toString());
                user.setPassword(passwordEditText.getText().toString());

                Call<JSONResponse> call = apiService.login(user);
                final LoadingDialogFragment loadingDialogFragment = ActivityHelper.getLoadingDialog();
                loadingDialogFragment.show(getSupportFragmentManager(), "");
                call.enqueue(new Callback<JSONResponse>() {
                    @Override
                    public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                        loadingDialogFragment.dismiss();
                        if (response.body() instanceof JSONResponse) {
                            if (response.body().isSuccess()) {
                                final String accessToken = "Bearer " + response.body().getPayload().toString();
                                Call<JSONResponse> call1 = apiService.getMyInfo(accessToken);
                                loadingDialogFragment.show(getSupportFragmentManager(), "");
                                call1.enqueue(new Callback<JSONResponse>() {
                                    @Override
                                    public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                                        loadingDialogFragment.dismiss();
                                        if (response.body() instanceof JSONResponse) {
                                            if (response.body().isSuccess()) {
                                                Gson gson = new Gson();
                                                String jsonString = new JSONObject((Map) response.body().getPayload()).toString();
                                                Log.i("dev", "onResponse: " + jsonString);
                                                User user1 = gson.fromJson(jsonString, User.class);
                                                Log.i("dev", "onResponse: " + user1.toString());
                                                //user1 = (User) response.body().getPayload();
                                                //todo change here before release
                                                TemporaryHelper.saveLoginInfo(LoginActivity.this, emailEditText.getText().toString(), passwordEditText.getText().toString());
                                                CommonOperations.saveAccessToken(LoginActivity.this, accessToken);
                                                CommonOperations.saveUserInfo(LoginActivity.this, user1);


                                                Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                                                i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                startActivity(i);
                                                finish();
                                                //todo clear backstack
                                            } else {
                                                AnimationHelper.initializeShakeAnimation(LoginActivity.this, signInButton);
                                                ActivityHelper.DisplayGeneralErrorToast(LoginActivity.this);
                                            }
                                        } else
                                            ActivityHelper.DisplayGeneralErrorToast(LoginActivity.this);
                                    }

                                    @Override
                                    public void onFailure(Call<JSONResponse> call, Throwable t) {
                                        loadingDialogFragment.dismiss();
                                        AnimationHelper.initializeShakeAnimation(LoginActivity.this, signInButton);
                                        ActivityHelper.DisplayGeneralErrorToast(LoginActivity.this);
                                    }
                                });


                            } else {
                                AnimationHelper.initializeShakeAnimation(LoginActivity.this, signInButton);
                                ActivityHelper.DisplayCustomToast(LoginActivity.this, response.body().getError().getMessage(), Toast.LENGTH_LONG);
                            }
                        } else ActivityHelper.DisplayGeneralErrorToast(LoginActivity.this);
                    }


                    @Override
                    public void onFailure(Call<JSONResponse> call, Throwable t) {
                        loadingDialogFragment.dismiss();
                        AnimationHelper.initializeShakeAnimation(LoginActivity.this, signInButton);
                        ActivityHelper.DisplayGeneralErrorToast(LoginActivity.this);

                    }
                });

            }
        });

        emailEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && !Patterns.EMAIL_ADDRESS.matcher(emailEditText.getText().toString()).matches() && emailEditText.length() > 0) {
                    emailTextInputLayout.setError(getString(R.string.invalid_email_address));
                }
            }
        });


        emailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Patterns.EMAIL_ADDRESS.matcher(emailEditText.getText().toString()).matches()) {
                    emailTextInputLayout.setError(null);
                    emailTextInputLayout.setErrorEnabled(false);
                }
                if (emailEditText.getText().length() == 0) {
                    emailTextInputLayout.setError(null);
                    emailTextInputLayout.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() >= 1) {
                    passwordToggleTextView.setVisibility(View.VISIBLE);
                }
                if (PasswordChecker.passwordFitsConditions(passwordEditText.getText().toString(), 6, 1, 1) && Patterns.EMAIL_ADDRESS.matcher(emailEditText.getText().toString()).matches()) {
                    signInButton.setEnabled(true);
                } else signInButton.setEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        passwordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus && passwordEditText.getText().toString().length() > 0) {
                    passwordToggleTextView.setVisibility(View.VISIBLE);
                } else {
                    passwordToggleTextView.setVisibility(View.INVISIBLE);
                }
            }
        });


        passwordToggleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PasswordHintToggler.passwordToggleState(passwordEditText, passwordToggleTextView);
            }
        });
    }
}

