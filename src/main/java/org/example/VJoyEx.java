package org.example;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseListener;
import com.github.kwhat.jnativehook.mouse.NativeMouseMotionListener;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

import java.awt.*;
import java.awt.event.KeyEvent;

public class VJoyEx {


    // Определяем интерфейс для работы с vJoy
    public interface VJoyInterface extends Library {
        VJoyInterface INSTANCE = Native.load("vJoyInterface", VJoyInterface.class);

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

    public static void main(String[] args) {
        int joystickId = 1;
        int axisX = 0x30;
        int axisY = 0x31;
        int axisThrottle = 0x32;


        VJoyInterface vJoy = VJoyInterface.INSTANCE;

        if (!vJoy.AcquireVJD(joystickId)) {
            System.err.println("Не удалось захватить устройство vJoy!");
            return;
        }
        vJoy.ResetVJD(joystickId);

        try {
            GlobalScreen.registerNativeHook();

            GlobalScreen.addNativeKeyListener(new NativeKeyListener() {
                int axisThrottleValue=0;
                @Override
                public void nativeKeyPressed(NativeKeyEvent e) {
                    if (e.getKeyCode() == NativeKeyEvent.VC_E) {

                        vJoy.SetBtn(true, joystickId, 1);
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                        vJoy.SetBtn(false, joystickId, 1);
                    }
                    if (e.getKeyCode() == NativeKeyEvent.VC_Q) {
                        axisThrottleValue=20000;
                        vJoy.SetAxis(axisThrottleValue, joystickId, axisThrottle);
                        try {
                            Thread.sleep(0);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }

                    }
                    if (e.getKeyCode() == NativeKeyEvent.VC_W) {
                       axisThrottleValue=50000;
                        vJoy.SetAxis(axisThrottleValue, joystickId, axisThrottle);
                        try {
                            Thread.sleep(0);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }

                    }

                    if (e.getKeyCode() == NativeKeyEvent.VC_S) {

                        vJoy.SetAxis(50000, joystickId, 0x33);
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }

                    }
                    if (e.getKeyCode() == NativeKeyEvent.VC_LEFT) {

                        vJoy.SetAxis(5000, joystickId, 0x30);
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }

                    }
                    if (e.getKeyCode() == NativeKeyEvent.VC_RIGHT) {

                        vJoy.SetAxis(30000, joystickId, 0x30);
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }

                    }
                    if (e.getKeyCode() == NativeKeyEvent.VC_SPACE) {

                        vJoy.SetAxis(25000, joystickId, 0x33);
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }

                    }


                }

                @Override
                public void nativeKeyReleased(NativeKeyEvent e) {

                    if (e.getKeyCode() == NativeKeyEvent.VC_W) {
                        axisThrottleValue=0;
                        vJoy.SetAxis(axisThrottleValue, joystickId, axisThrottle);
                        try {
                            Thread.sleep(0);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }

                    }
                    if (e.getKeyCode() == NativeKeyEvent.VC_S) {

                        vJoy.SetAxis(0, joystickId, 0x33);
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }

                    }
                    if (e.getKeyCode() == NativeKeyEvent.VC_Q) {
                        axisThrottleValue=0;
                        vJoy.SetAxis(axisThrottleValue, joystickId, axisThrottle);
                        try {
                            Thread.sleep(0);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }

                    }
                    if (e.getKeyCode() == NativeKeyEvent.VC_LEFT) {

                        vJoy.SetAxis(16000, joystickId, 0x30);
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }

                    }
                    if (e.getKeyCode() == NativeKeyEvent.VC_RIGHT) {

                        vJoy.SetAxis(16000, joystickId, 0x30);
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }

                    }
                    if (e.getKeyCode() == NativeKeyEvent.VC_SPACE) {

                        vJoy.SetAxis(0, joystickId, 0x33);
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }

                    }
                }

                @Override
                public void nativeKeyTyped(NativeKeyEvent e) {

                }
            });

        } catch (NativeHookException e) {
            throw new RuntimeException(e);
        }
//        vJoy.RelinquishVJD(joystickId);
//        System.out.println("устройство освобожденно.");
    }
}


