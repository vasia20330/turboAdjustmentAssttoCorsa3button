package org.example;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseMotionListener;

public class Steering {

    public void  init(){
        steeringMouse();
    }
    private void steeringMouse(){
        int joystickId = 2;
        int axisX = 0x30;
        VJoyEx.VJoyInterface vJoy = VJoyEx.VJoyInterface.INSTANCE;

        try {
            GlobalScreen.registerNativeHook();
            GlobalScreen.addNativeMouseMotionListener(new NativeMouseMotionListener() {
                @Override
                public void nativeMouseMoved(NativeMouseEvent nativeEvent) {

                }
            });
        } catch (NativeHookException e) {
            throw new RuntimeException(e);
        }


        if (!vJoy.AcquireVJD(joystickId)) {
            System.err.println("Не удалось захватить устройство vJoy!");
            return;
        }
        vJoy.ResetVJD(joystickId);
    }
}
