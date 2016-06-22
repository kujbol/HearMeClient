package com.kprod.hearme;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.SwitchCompat;
import android.widget.Button;
import android.widget.SeekBar;

import com.kprod.hearme.data.api.HearMeService;
import com.kprod.hearme.data.api.model.SearchPreferences;
import com.kprod.hearme.data.api.model.SearchSettings;
import com.kprod.hearme.data.api.model.Settings;
import com.kprod.hearme.ui.SettingsActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowToast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.*;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.ResponseBody;
import okhttp3.internal.http.RealResponseBody;
import okio.BufferedSource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import rx.Observable;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class SettingsTest {

    private SettingsActivity settingsActivity;
    private Settings settings;
    private HearMeService service;

    @Before
    public void SetUp() {
        HearMeApp app = ((HearMeApp) (RuntimeEnvironment.application));
        settings = new Settings(new SearchPreferences(10, 99, stringToLiat("female"), true, stringToLiat("polish")),
                new SearchSettings("male", stringToLiat("english")));
        service = mock(HearMeService.class);
        when(service.getSettings()).thenReturn(Observable.just(settings));

        app.hearMeService = service;
        settingsActivity = Robolectric.setupActivity(SettingsActivity.class);
    }

    @Test
    public void clickingSaveButtonWhenUnableToSaveSettings_shouldDisplayError() {
        when(service.postSettings(Matchers.<Settings>any())).thenReturn(Calls.failure(new IOException()));
        FloatingActionButton button = (FloatingActionButton) settingsActivity.findViewById(R.id.saveSettingsButton);

        button.performClick();

        assertTrue(ShadowToast.getTextOfLatestToast().equals("Unable to save settings"));
    }

    @Test
    public void launchingActivity_shouldLoadSavedSettings() {
        SeekBar ageRangeLowSeekBar = (SeekBar) settingsActivity.findViewById(R.id.age_range_low);
        SeekBar ageRangeTopSeekBar = (SeekBar) settingsActivity.findViewById(R.id.age_range_top);

        AppCompatSpinner profileGenderSpinner = (AppCompatSpinner) settingsActivity.findViewById(R.id.profile_gender);
        SwitchCompat isInSameCountrySwitch = (SwitchCompat) settingsActivity.findViewById(R.id.is_in_same_country);

        AppCompatSpinner profileLanguagesSpinner = (AppCompatSpinner) settingsActivity.findViewById(R.id.profile_languages);
        AppCompatSpinner searchGendersSpinner = (AppCompatSpinner) settingsActivity.findViewById(R.id.search_genders);
        AppCompatSpinner searchLanguagesSpinner = (AppCompatSpinner) settingsActivity.findViewById(R.id.search_languages);

        assertTrue(ageRangeLowSeekBar.getProgress() == settings.search_preferences.age_range_low);
        assertTrue(ageRangeTopSeekBar.getProgress() == settings.search_preferences.age_range_top);
        assertTrue(((String)profileGenderSpinner.getSelectedItem()).equals(settings.search_settings.gender));
        assertTrue(isInSameCountrySwitch.isChecked() == settings.search_preferences.is_in_same_country);
        assertTrue(((String)profileLanguagesSpinner.getSelectedItem()).equals(settings.search_settings.languages.get(0)));
        assertTrue(((String)searchGendersSpinner.getSelectedItem()).equals(settings.search_preferences.genders.get(0)));
        assertTrue(((String)searchLanguagesSpinner.getSelectedItem()).equals(settings.search_preferences.languages.get(0)));
    }

    private List<String> stringToLiat(String string) {
        return Arrays.asList(new String[]{string});
    }

}
