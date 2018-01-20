package android.ebozkurt.com.favor.views;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

/**
 * Created by erdem on 20.01.2018.
 */

public class AlertDialogFragment extends DialogFragment {

    public AlertDialogFragment() {
    }

    public static AlertDialogFragment newInstance(String title, String message, String posButtonText, String negButtonText) {

        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("message", message);
        args.putString("posButtonText", posButtonText);
        args.putString("negButtonText", negButtonText);

        AlertDialogFragment fragment = new AlertDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String title = getArguments().getString("title");
        String message = getArguments().getString("message");
        String posButtonText = getArguments().getString("posButtonText");
        String negButtonText = getArguments().getString("negButtonText");

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setPositiveButton(posButtonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // on success
                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, null);
            }
        });
        alertDialogBuilder.setNegativeButton(negButtonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }

        });

        return alertDialogBuilder.create();
    }

}
