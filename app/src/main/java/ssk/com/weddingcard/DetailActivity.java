package ssk.com.weddingcard;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.support.v7.widget.Toolbar;
import date.DatePickerDialogFragment;

/**
 * Created by santosh.kathait on 21-11-2017.
 */

public class DetailActivity extends AppCompatActivity {

    private EditText mGroomName;
    private EditText mBrideName;
    private EditText mInviteeName;
    private EditText mGroomParentName;
    private EditText mBrideParentName;
    private EditText mDateEditText;
    private EditText mVenueEditText;
    private EditText mContactEditText;
    private EditText mFamilyNameEditText;

    private Button mSubmitButton;

    private DatePickerDialogFragment mDatePickerDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);

        setToolBar();

        initComponent();

    }

    private void setToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        toolbar.setTitleTextColor(getResources().getColor(R.color.theme_color));
        setSupportActionBar(toolbar);
    }

    private void initComponent() {

        mDatePickerDialogFragment = new DatePickerDialogFragment();
        mGroomName =  findViewById(R.id.et_groom_name);
        mBrideName =  findViewById(R.id.et_bride_name);
        mInviteeName =  findViewById(R.id.et_invitee);
        mGroomParentName =  findViewById(R.id.et_groom_parent);
        mBrideParentName =  findViewById(R.id.et_bride_parent);
        mVenueEditText =  findViewById(R.id.et_venue);
        mDateEditText =  findViewById(R.id.et_date);
        mContactEditText =  findViewById(R.id.et_contact_no);
        mFamilyNameEditText =  findViewById(R.id.et_family_name);
        mSubmitButton =  findViewById(R.id.btn_submit);

        eventListener();

    }

    private void eventListener() {
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(mGroomName.getText().toString()))
                    mGroomName.setError("Enter groom's name");
                else if (TextUtils.isEmpty(mBrideName.getText().toString()))
                    mBrideName.setError("Enter bride's name");
                else if (TextUtils.isEmpty(mInviteeName.getText().toString()))
                    mInviteeName.setError("Enter invitee's name");
                else if (TextUtils.isEmpty(mGroomParentName.getText().toString()))
                    mGroomParentName.setError("Enter groom parent's name");
                else if (TextUtils.isEmpty(mBrideParentName.getText().toString()))
                    mBrideParentName.setError("Enter bride parent's name");
                else if (TextUtils.isEmpty(mVenueEditText.getText().toString()))
                    mVenueEditText.setError("Enter venue");
                else if (TextUtils.isEmpty(mDateEditText.getText().toString()))
                    mDateEditText.setError("Select date");
                else if (TextUtils.isEmpty(mContactEditText.getText().toString()))
                    mContactEditText.setError("Enter contact no.");
                else if (mContactEditText.getText().toString().length() < 10)
                    mContactEditText.setError("Enter valid contact no.");
                else if (TextUtils.isEmpty(mFamilyNameEditText.getText().toString()))
                    mFamilyNameEditText.setError("Enter family name");
                else {
                    Intent in = new Intent(DetailActivity.this, MainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("groom_name", mGroomName.getText().toString());
                    bundle.putString("bride_name", mBrideName.getText().toString());
                    bundle.putString("invitee", mInviteeName.getText().toString());
                    bundle.putString("groom_parent", mGroomParentName.getText().toString());
                    bundle.putString("bride_parent", mBrideParentName.getText().toString());
                    bundle.putString("venue", mVenueEditText.getText().toString());
                    bundle.putString("date", mDateEditText.getText().toString());
                    bundle.putString("contact", mContactEditText.getText().toString());
                    bundle.putString("family_name", mFamilyNameEditText.getText().toString());
                    in.putExtras(bundle);
                    startActivity(in);
                }
            }
        });

        mDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatePickerDialogFragment.setTextViewToSetDateOnDateSet(mDateEditText);
                mDatePickerDialogFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });
    }
}
