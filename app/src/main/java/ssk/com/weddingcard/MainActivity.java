package ssk.com.weddingcard;

import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.vipul.hp_hp.library.Layout_to_Image;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private ImageView mCardImageView;
    private Layout_to_Image layout_to_image;  //Create Object of Layout_to_Image Class
    private RelativeLayout relativeLayout;
    private Bitmap bitmap;
    private TextView mGroomNameTextView;
    private TextView mBrideNameTextView;
    private TextView mInviteeTextView;
    private TextView mGroomParentTextView;
    private TextView mBrideParentTextView;
    private TextView mVenueTextView;
    private TextView mDateTextView;
    private TextView mContactTextView;
    private TextView mFamilyNameTextView;
    private Button mShareButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout();
    }

    private void setContentLayout() {
        setContentView(R.layout.activity_main);

        setToolBar();

        init();

    }

    private void setToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        toolbar.setTitleTextColor(getResources().getColor(R.color.theme_color));
        setSupportActionBar(toolbar);
    }

    private void init() {
        mCardImageView = findViewById(R.id.iv_card);
        //provide layout with its id in Xml
        relativeLayout = findViewById(R.id.rl_bitmap);
        mGroomNameTextView = findViewById(R.id.tv_groom_name);
        mBrideNameTextView = findViewById(R.id.tv_bride_name);
        mInviteeTextView = findViewById(R.id.tv_invitee);
        mGroomParentTextView = findViewById(R.id.tv_groom_parent);
        mBrideParentTextView = findViewById(R.id.tv_bride_parent);
        mVenueTextView = findViewById(R.id.tv_venue);
        mDateTextView = findViewById(R.id.tv_date);
        mContactTextView = findViewById(R.id.tv_contact);
        mFamilyNameTextView = findViewById(R.id.tv_family_name);
        mShareButton = findViewById(R.id.btn_share);

        try {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            setTextAndBackgroundColor(prefs.getInt("text_color", R.color.text_color), prefs.getInt("background_color", R.color.theme_color));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void dataBind() {
        //Extract the data…
        Bundle bundle = getIntent().getExtras();
        String groomName = bundle.getString("groom_name");
        String brideName = bundle.getString("bride_name");
        String invitee = bundle.getString("invitee");
        String groomParent = bundle.getString("groom_parent");
        String brideParent = bundle.getString("bride_parent");
        String venue = bundle.getString("venue");
        String date = bundle.getString("date");
        String contact = bundle.getString("contact");
        String familyName = bundle.getString("family_name");

        mGroomNameTextView.setText(Util.firstLetterToUpperCase(groomName));
        mBrideNameTextView.setText(Util.firstLetterToUpperCase(brideName));
        mInviteeTextView.setText(Util.firstLetterToUpperCase(invitee));
        mGroomParentTextView.setText("S/O " + Util.firstLetterToUpperCase(groomParent));
        mBrideParentTextView.setText("D/O " + Util.firstLetterToUpperCase(brideParent));
        mVenueTextView.setText(Util.firstLetterToUpperCase(venue));
        mDateTextView.setText(Util.getVenueDate(date));
        mContactTextView.setText("Contact: " + contact);
        mFamilyNameTextView.setText(Util.firstLetterToUpperCase(familyName));

        getCard();
        eventListener();
    }

    private void eventListener() {
        mShareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareOnOtherSocialMedia();
            }
        });
    }

    private void getCard() {

        try {
            layout_to_image = new Layout_to_Image(MainActivity.this, relativeLayout);
            bitmap = layout_to_image.convert_layout();
            mCardImageView.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Sharing vCard image for gmail, email, bluetooth and whatsapp only
    public void shareOnOtherSocialMedia() {

        OutputStream output;
        File file = new File(Environment.getExternalStorageDirectory()
                + File.separator + "card.jpg");
        try {
            List<Intent> shareIntentsLists = new ArrayList<Intent>();
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.setType("image/*");
            output = new FileOutputStream(file);

            // Compress into png format image from 0% - 100%
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
            output.flush();
            output.close();

            // Locate the image to Share
            Uri uri = Uri.fromFile(file);

            // Pass the image into an Intnet
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);

            List<ResolveInfo> resInfos = this.getPackageManager().queryIntentActivities(shareIntent, 0);
            if (!resInfos.isEmpty()) {
                for (ResolveInfo resInfo : resInfos) {
                    String packageName = resInfo.activityInfo.packageName;
                        Intent intent = new Intent();
                        intent.setComponent(new ComponentName(packageName, resInfo.activityInfo.name));
                        intent.setAction(Intent.ACTION_SEND);
                        intent.setType("image/*");
                        intent.putExtra(Intent.EXTRA_STREAM, uri);
                        intent.putExtra(Intent.EXTRA_SUBJECT, "Card");
                        intent.setPackage(packageName);
                        shareIntentsLists.add(intent);
                    }

                if (!shareIntentsLists.isEmpty()) {
                    Intent chooserIntent = Intent.createChooser(shareIntentsLists.remove(0), "Choose app to share");
                    chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, shareIntentsLists.toArray(new Parcelable[]{}));
                    startActivity(chooserIntent);
                } else
                    android.util.Log.e("Error", "No Apps can perform your task");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setTextAndBackgroundColor(int textColorCode, int backgroundColorCode) {
        relativeLayout.setBackgroundColor(backgroundColorCode);
        setTextColor(textColorCode);
    }

    private void setTextColor(int textColor) {
        mGroomNameTextView.setTextColor(textColor);
        mBrideNameTextView.setTextColor(textColor);
        mInviteeTextView.setTextColor(textColor);
        mGroomParentTextView.setTextColor(textColor);
        mBrideParentTextView.setTextColor(textColor);
        mVenueTextView.setTextColor(textColor);
        mDateTextView.setTextColor(textColor);
        mContactTextView.setTextColor(textColor);
        mFamilyNameTextView.setTextColor(textColor);

        ((TextView) findViewById(R.id.tv_we_would_delight)).setTextColor(textColor);
        ((TextView) findViewById(R.id.tv_with)).setTextColor(textColor);
        ((TextView) findViewById(R.id.tv_regards)).setTextColor(textColor);

        dataBind();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            startActivityForResult(new Intent(this, SettingActivity.class), 1);
        }
        return super.onOptionsItemSelected(item);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                setContentLayout();
            }
        }
    }
}

