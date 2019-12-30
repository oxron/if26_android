package fr.utt.if26t.minimalist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class AddListDialog extends AppCompatDialogFragment {
    private EditText mNameList;
    private AddListDialogListener mListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflator = getActivity().getLayoutInflater();
        View view = inflator.inflate(R.layout.layout_dialog_add_item, null);

        builder.setView(view)
                .setTitle("Ajouter une liste")
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Ajouter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String nameList = mNameList.getText().toString();
                        if (nameList.trim().length() == 0 ) {
                            Toast toastNoName = Toast.makeText(getContext(), "Vous devez donner un nom à la liste", Toast.LENGTH_SHORT);
                            toastNoName.show();
                            return;
                        } else {
                            mListener.applyTexts(nameList);
                        }
                    }
                });

        mNameList = view.findViewById(R.id.editTextNameList);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            mListener = (AddListDialogListener) context;
        } catch (ClassCastException e) {
           throw new ClassCastException((context.toString() + "must implement AddListListener"));
        }
    }

    public interface AddListDialogListener {
        void applyTexts(String nameList);
    }
}
