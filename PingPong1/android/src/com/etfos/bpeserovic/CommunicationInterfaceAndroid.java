package com.etfos.bpeserovic;

import android.app.Activity;
import android.content.Context;
import com.etfos.bpeserovic.*;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * Created by Bobo on 3.6.2017..
 */

public class CommunicationInterfaceAndroid implements CommunicationInterface {

    private Activity context;

    public CommunicationInterfaceAndroid(Activity context)
    {
        this.context = context;
    }

    @Override
    public void getUserStatus() {
        boolean isServer;
    }
}
