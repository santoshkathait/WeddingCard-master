package ssk.com.weddingcard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import interfaces.CardColorListener;

/**
 * Created by santosh.kathait on 29-11-2017.
 */

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_toolbar);

        LinearLayout root = (LinearLayout)findViewById(R.id.toolbar_settings).getParent().getParent().getParent();
        Toolbar bar = (Toolbar) LayoutInflater.from(this).inflate(R.layout.settings, root, false);
        bar.setTitleTextColor(getResources().getColor(R.color.theme_color));
        root.addView(bar, 0); // insert at top
        bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackClicked();
                finish();
            }
        });


        if(savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.content_frame, new ExamplePreferenceFragment())
                    .commit();
        }
    }



    @Override
    public void onBackPressed() {
        callPreviousActivity();
    }

    private void callPreviousActivity() {

        onBackClicked();
        super.onBackPressed();
    }

    private void onBackClicked(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        Intent intent = new Intent();
        intent.putExtra("textColorCode",prefs.getInt("text_color", R.color.text_color) );
        intent.putExtra("bgColorCode", prefs.getInt("background_color", R.color.theme_color));
        setResult(RESULT_OK, intent);
    }


    public static class ExamplePreferenceFragment extends PreferenceFragment {

        @Override public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.main);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = super.onCreateView(inflater, container, savedInstanceState);
            view.setBackgroundColor(getResources().getColor(R.color.theme_color));
            return view;
        }

    }

}
