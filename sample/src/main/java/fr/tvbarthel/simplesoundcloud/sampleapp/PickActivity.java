package fr.tvbarthel.simplesoundcloud.sampleapp;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import fr.tvbarthel.simplesoundcloud.sampleapp.ui.ExtraHintEditText;

/**
 * Simple activity used to pick an artist in order to load it into the sample activity.
 */
public class PickActivity extends ActionBarActivity implements
    TextView.OnEditorActionListener, View.OnClickListener {

    private ExtraHintEditText mArtistName;
    private Animation mWiggle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick);

        Toolbar toolbar = ((Toolbar) findViewById(R.id.activity_pick_toolbar));
        setSupportActionBar(toolbar);

        mArtistName = ((ExtraHintEditText) findViewById(R.id.activity_pick_artist_edit));
        View extraHintView = findViewById(R.id.activity_pick_extra_hint);
        mArtistName.setExtraHintView(extraHintView);
        mArtistName.setOnEditorActionListener(this);

        mWiggle = AnimationUtils.loadAnimation(this, R.anim.wiggle);

        findViewById(R.id.activity_pick_search_btn).setOnClickListener(this);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        boolean handle = false;
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            if (checkField(mArtistName)) {
                handle = true;
                ArtistActivity.startActivity(this, mArtistName.getText().toString());
            }
        }
        return handle;
    }

    /**
     * Check if the edit text is valid or not.
     *
     * @param editText field to check.
     * @return true if the edit text isn't empty
     */
    private boolean checkField(EditText editText) {
        boolean valid = true;
        if (TextUtils.isEmpty(editText.getText())) {
            editText.startAnimation(mWiggle);
            editText.requestFocus();
            valid = false;
        }
        return valid;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_pick_search_btn:
                if (checkField(mArtistName)) {
                    ArtistActivity.startActivity(this, mArtistName.getText().toString());
                }
                break;
            default:
                throw new IllegalStateException("Click not handled on " + v);
        }
    }
}