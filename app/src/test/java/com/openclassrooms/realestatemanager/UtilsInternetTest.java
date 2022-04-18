package com.openclassrooms.realestatemanager;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.shadows.ShadowConnectivityManager;
import org.robolectric.shadows.ShadowNetworkInfo;

@RunWith(RobolectricTestRunner.class)
public class UtilsInternetTest {

    private ConnectivityManager connectivityManager;
    private ShadowConnectivityManager shadowConnectivityManager;

    @Before
    public void setUp() {
        connectivityManager = getConnectivityManager();
        shadowConnectivityManager = Shadows.shadowOf(connectivityManager);

    }

    @Test
    public void testSimple() {


        NetworkInfo networkInfo = ShadowNetworkInfo.newInstance(NetworkInfo.DetailedState.CONNECTED, ConnectivityManager.TYPE_WIFI, 0, true, true);
        // Correct API call: use setActiveNetworkInfo instead of setNetworkInfo
        shadowConnectivityManager.setActiveNetworkInfo(networkInfo);

        NetworkInfo activeInfo = connectivityManager.getActiveNetworkInfo();
        Assert.assertTrue(activeInfo != null && activeInfo.isConnected());

        // Assertion now passes: Correctly returns TYPE_WIFI
        Assert.assertEquals(ConnectivityManager.TYPE_WIFI, activeInfo.getType());


    }

    private ConnectivityManager getConnectivityManager() {
        return (ConnectivityManager) RuntimeEnvironment.application.getSystemService(Context.CONNECTIVITY_SERVICE);
    }
}
