package com.example.shoplocator.ui.settings.preferences;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.preference.DialogPreference;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shoplocator.App;
import com.example.shoplocator.R;
import com.example.shoplocator.data.repsitory.shops.IShopsRepository;
import com.example.shoplocator.data.repsitory.users.IUsersRepository;
import com.example.shoplocator.util.rx.schedulers.RxSchedulersAbs;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Single;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 29.01.17.
 */

public class SendDbPreference extends DialogPreference {

    @Inject IShopsRepository shopsRepository;
    @Inject IUsersRepository usersRepository;
    @Inject RxSchedulersAbs rxSchedulers;

    @BindView(R.id.editTextEmail) EditText editTextEmail;

    public SendDbPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        setDialogLayoutResource(R.layout.from_send_db_by_email);
        setPositiveButtonText(R.string.send);
        setNegativeButtonText(android.R.string.cancel);
        setDialogIcon(R.drawable.vector_upload);
        App.instance().applicationComponent().inject(this);
    }

    @Override
    protected void onBindDialogView(View view) {
        super.onBindDialogView(view);
        ButterKnife.bind(this, view);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (which == DialogInterface.BUTTON_POSITIVE) {
            sendEmail();
        }
        super.onClick(dialog, which);
    }

    private void sendEmail() {
        Single.zip(shopsRepository.getLocalDbStructure(), usersRepository.getLocalDbStructure(),
                (shops, users) -> "{Shops: " + shops +", Users: " + users + "}")
                .compose(rxSchedulers.getIOToMainTransformerSingle())
                .onErrorReturn(throwable -> getContext().getString(R.string.error_when_fetch_db_structure) + throwable)
                .subscribe(this::handleGetLocalDbStructure);
    }

    private void handleGetLocalDbStructure(@NonNull String structureStr) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{editTextEmail.getText().toString()});
        i.putExtra(Intent.EXTRA_SUBJECT, getContext().getString(R.string.shop_database_message_subject));
        i.putExtra(Intent.EXTRA_TEXT   , structureStr);
        try {
            getContext().startActivity(Intent.createChooser(i, getContext().getString(R.string.send_mail_)));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getContext(), R.string.no_email_client_installed, Toast.LENGTH_SHORT).show();
        }
    }

}
