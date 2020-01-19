package com.igfgpo01.gestionpedidosmobile.singleton;

import android.os.Handler;

public class HandlerSingleton {

    private static HandlerSingleton INSTANCE;

    private HandlerSingleton() {}

    public static HandlerSingleton getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new HandlerSingleton();
            INSTANCE.handler = new Handler();
        }
        return INSTANCE;
    }

    private Handler handler;

    public Handler getHandler() {
        return handler;
    }

}
