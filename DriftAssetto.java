package org.example;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseMotionListener;
import com.sun.jna.Library;
import com.sun.jna.Native;

public class DriftAssetto {
    // Определяем интерфейс для работы с vJoy
    public interface VJoyInterface extends Library {
        VJoyEx.VJoyInterface INSTANCE = Native.load("vJoyInterface", VJoyEx.VJoyInterface.class);

        boolean AcquireVJD(int rID);            // Захват устройства

        void RelinquishVJD(int rID);           // Освобождение устройства

        boolean ResetVJD(int rID);             // Сброс состояния устройства

        boolean SetBtn(boolean state, int rID, int buttonID); // Установка состояния кнопки

        boolean SetAxis(int value, int rID, int axisID);      // Установка оси

        boolean GetVJDAxisExist(int rID, int axisID);         // Проверка наличия оси

        boolean GetVJDButtonNumber(int rID);                 // Проверка количества кнопок

        boolean GetVJDStatus(int rID);                       // Проверка состояния устройства
    }

    // Константы осей (пример для осей X, Y)
    private static final int HID_USAGE_X = 0x30;
    private static final int HID_USAGE_Y = 0x31;

    private static final int MAX_AXIS_VALUE = 65535;
    private static final int SMOOTHING_FACTOR = 3; // Степень сглаживания (чем больше, тем более плавное управление)

    private static int prevX = 0;
    private static int prevY = 0;
    VJoyEx.VJoyInterface vJoy = VJoyEx.VJoyInterface.INSTANCE;

    public void init() {

        int joystickId = 1;
        int axisX = 0x30;
        int axisY = 0x31;
        int axisThrottle = 0x32;




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
                    if (nativeEvent.getKeyCode()==NativeKeyEvent.VC_SPACE){
                        vJoy.SetAxis(30000,joystickId,0x35);
                        vJoy.SetAxis(30000,joystickId,0x34);
                    }
                    if (nativeEvent.getKeyCode() == NativeKeyEvent.VC_W) {

                        vJoy.SetAxis(50000, joystickId, 0x32);
                    }
                    if (nativeEvent.getKeyCode() == NativeKeyEvent.VC_S) {

                        vJoy.SetAxis(50000, joystickId, 0x33);


                    }

                    if (nativeEvent.getKeyCode() == NativeKeyEvent.VC_COMMA) {

                        vJoy.SetAxis(0, joystickId, 0x30);


                    }
                    if (nativeEvent.getKeyCode() == NativeKeyEvent.VC_PERIOD) {

                        new Thread(()->{
                            int targetValue=30000;
                            int currentValue=16000;
                            int step=1000;
                            int delay=10;

                            while(currentValue<targetValue){
                                currentValue+=step;
                                if (currentValue>targetValue){
                                    currentValue=targetValue;
                                }
                                vJoy.SetAxis(currentValue,joystickId,0x30);
                                try {
                                    Thread.sleep(delay);
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                            }

                        }).start();


//                        vJoy.SetAxis(0, joystickId, 0x30);


                    }
                    if(nativeEvent.getKeyCode()==NativeKeyEvent.VC_D){
                        vJoy.SetAxis(30000,joystickId,0x35);
                    }
                }

                @Override
                public void nativeKeyReleased(NativeKeyEvent nativeEvent) {
                    if(nativeEvent.getKeyCode()==NativeKeyEvent.VC_D){
                        vJoy.SetAxis(0,joystickId,0x35);
                    }
                    if (nativeEvent.getKeyCode()==NativeKeyEvent.VC_SPACE){
                        vJoy.SetAxis(0,joystickId,0x34);
                        vJoy.SetAxis(0,joystickId,0x35);
                    }
                    if (nativeEvent.getKeyCode() == NativeKeyEvent.VC_W) {
//

                        vJoy.SetAxis(0, joystickId, axisThrottle);


                    }
                    if (nativeEvent.getKeyCode() == NativeKeyEvent.VC_S) {

                        vJoy.SetAxis(0, joystickId, 0x33);


                    }
                    if (nativeEvent.getKeyCode() == NativeKeyEvent.VC_COMMA) {

                        vJoy.SetAxis(16000, joystickId, 0x30);


                    }
                    if (nativeEvent.getKeyCode() == NativeKeyEvent.VC_PERIOD) {

                        vJoy.SetAxis(16000, joystickId, 0x30);


                    }
                }



            });
            GlobalScreen.addNativeMouseMotionListener(new NativeMouseMotionListener() {

                @Override
                public void nativeMouseMoved(NativeMouseEvent nativeEvent) {
                    NativeMouseMotionListener.super.nativeMouseMoved(nativeEvent);
//                    vJoy.SetAxis(nativeEvent.getX()*20,joystickId,0x30);

                }
            });
        } catch (NativeHookException e) {
            throw new RuntimeException(e);
        }







    }
    class Steering{
        int maxSteering=30000;
        int target=30000;
        int step=10000;
        int delay=5;
        int steeringValue=16000;

        void init(){
            try {
                GlobalScreen.registerNativeHook();
                GlobalScreen.addNativeKeyListener(new NativeKeyListener() {
                    @Override
                    public void nativeKeyPressed(NativeKeyEvent nativeEvent) {
                        if (nativeEvent.getKeyCode() == NativeKeyEvent.VC_COMMA) {

                            vJoy.SetAxis(0, 0 , 0x30);


                        }
                        if (nativeEvent.getKeyCode() == NativeKeyEvent.VC_PERIOD) {

                            for (int i=0;i<3;i++){
                                try {
                                    Thread.sleep(delay);
                                    steeringValue=step*i;
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                            vJoy.SetAxis(steeringValue, 1, 0x30);


                        }
                    }

                    @Override
                    public void nativeKeyReleased(NativeKeyEvent nativeEvent) {
                        if (nativeEvent.getKeyCode() == NativeKeyEvent.VC_COMMA) {

                            vJoy.SetAxis(16000, 1, 0x30);


                        }
                        if (nativeEvent.getKeyCode() == NativeKeyEvent.VC_PERIOD) {

                            vJoy.SetAxis(16000, 1, 0x30);


                        }
                    }
                });
            } catch (NativeHookException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
