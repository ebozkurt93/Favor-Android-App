package android.ebozkurt.com.favor.views;

import android.content.Context;
import android.content.DialogInterface;
import android.ebozkurt.com.favor.R;
import android.ebozkurt.com.favor.helpers.ActivityHelper;
import android.ebozkurt.com.favor.helpers.KeyboardHelper;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by erdem on 18.09.2017.
 */

public class LoadingDialogFragment extends DialogFragment {

    public LoadingDialogFragment() {
    }

    public static LoadingDialogFragment newInstance() {
        LoadingDialogFragment frag = new LoadingDialogFragment();
        Bundle args = new Bundle();
        //args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return inflater.inflate(R.layout.loading_dialog_fragment, container);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        // Get field from view
        //mEditText = (EditText) view.findViewById(R.id.txt_your_name);
        // Fetch arguments from bundle and set title
        //String title = getArguments().getString("title", "Enter Name");
        //getDialog().setTitle(title);
        // Show soft keyboard automatically and request focus to field
        //mEditText.requestFocus();
        //getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    private void closeKeyboard(final View caller) {
        caller.post(new Runnable() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) caller.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(caller.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });
    }

}
