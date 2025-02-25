package org.example;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseMotionListener;

public class Assetto911 {

    public void init(){
        int joystickId = 1;
        int axisX = 0x30;
        int axisY = 0x31;
        int axisThrottle = 0x32;


        VJoyEx.VJoyInterface vJoy = VJoyEx.VJoyInterface.INSTANCE;

        if (!vJoy.AcquireVJD(joystickId)) {
            System.err.println("Не удалось захватить устройство vJoy!");
            return;
        }
        vJoy.ResetVJD(joystickId);

        try {
            GlobalScreen.registerNativeHook();
            GlobalScreen.addNativeKeyListener(new NativeKeyListener() {
                @Override
                public void nativeKeyPressed(NativeKeyEvent nativeEvent) {
                    if (nativeEvent.getKeyCode() == NativeKeyEvent.VC_W) {


                        vJoy.SetAxis(50000,joystickId,0x32);
                    }
                    if (nativeEvent.getKeyCode() == NativeKeyEvent.VC_UP) {

                        vJoy.SetAxis(16000,joystickId,axisX);

                    }
                    if (nativeEvent.getKeyCode() == NativeKeyEvent.VC_Q) {

                        vJoy.SetAxis(10000, joystickId, axisThrottle);


                    }
                    if (nativeEvent.getKeyCode() == NativeKeyEvent.VC_SHIFT) {

                        vJoy.SetAxis(0, joystickId, axisThrottle);


                    }
                    if (nativeEvent.getKeyCode() == NativeKeyEvent.VC_D) {


                        vJoy.SetAxis(25000,joystickId,0x32);
                    }
                    if (nativeEvent.getKeyCode() == NativeKeyEvent.VC_S) {

                        vJoy.SetAxis(50000, joystickId, 0x33);


                    }
                    if (nativeEvent.getKeyCode() == NativeKeyEvent.VC_SPACE) {

                        vJoy.SetAxis(17000, joystickId, 0x33);


                    }
                    if (nativeEvent.getKeyCode()==NativeKeyEvent.VC_A){
                        vJoy.SetBtn(true,joystickId,1);

                    }
                    if (nativeEvent.getKeyCode()==NativeKeyEvent.VC_Z){
                        vJoy.SetBtn(true,joystickId,2);

                    }
                }

                @Override
                public void nativeKeyReleased(NativeKeyEvent nativeEvent) {
                    if (nativeEvent.getKeyCode() == NativeKeyEvent.VC_SHIFT) {

                        vJoy.SetAxis(10000, joystickId, axisThrottle);


                    }
                    if (nativeEvent.getKeyCode() == NativeKeyEvent.VC_Q) {

                        vJoy.SetAxis(0, joystickId, axisThrottle);


                    }
                    if (nativeEvent.getKeyCode() == NativeKeyEvent.VC_W) {


                        vJoy.SetAxis(10000,joystickId,0x32);
                    }
                    if (nativeEvent.getKeyCode() == NativeKeyEvent.VC_S) {

                        vJoy.SetAxis(0, joystickId, 0x33);


                    }
                    if (nativeEvent.getKeyCode() == NativeKeyEvent.VC_SPACE) {

                        vJoy.SetAxis(0, joystickId, 0x33);


                    }
                    if (nativeEvent.getKeyCode()==NativeKeyEvent.VC_A){
                        vJoy.SetBtn(false,joystickId,1);

                    }
                    if (nativeEvent.getKeyCode()==NativeKeyEvent.VC_Z){
                        vJoy.SetBtn(false,joystickId,2);

                    }
                    if (nativeEvent.getKeyCode() == NativeKeyEvent.VC_D) {


                        vJoy.SetAxis(0,joystickId,0x32);
                    }
                }
            });
            GlobalScreen.addNativeMouseMotionListener(new NativeMouseMotionListener() {
                @Override
                public void nativeMouseMoved(NativeMouseEvent nativeEvent) {
                    vJoy.SetAxis(nativeEvent.getX()*20,joystickId,axisX);
                }


            });
        } catch (NativeHookException e) {
            throw new RuntimeException(e);
        }
    }
}
