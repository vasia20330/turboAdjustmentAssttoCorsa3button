package org.example;

import com.sun.jna.Library;
import com.sun.jna.Native;

public class ThrottleController {
    private int targetValue=0;
    private int axisThrottleValue=0;
    private final int step=1000;
    private final int delay=10;
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



    public  synchronized  void setAxisThrottleValue(int newValue){
        axisThrottleValue=newValue;
    }
    public  synchronized  int getAxisThrottleValue(){
        return axisThrottleValue;
    }

    public  synchronized  void setTargetValue(int newValue){
        targetValue=newValue;
    }
    public  synchronized  int getTargetValue(){
        return targetValue;
    }

    public void smoothThrottleControl() {
        int joystickId = 1;
        int axisX = 0x30;
        int axisY = 0x31;
        int axisThrottle = 0x32;
        VJoyEx.VJoyInterface vJoy = VJoyEx.VJoyInterface.INSTANCE;
        new Thread(() -> {
            while (axisThrottleValue != targetValue) {
                int currentTargetValue;
                synchronized (this) {
                    currentTargetValue = targetValue;
                }

                if (axisThrottleValue < currentTargetValue) {
                    axisThrottleValue = Math.min(axisThrottleValue + step, currentTargetValue);
                } else if (axisThrottleValue > currentTargetValue) {
                    axisThrottleValue = Math.max(axisThrottleValue - step, currentTargetValue);
                }

                System.out.println("Value: " + axisThrottleValue);
                vJoy.SetAxis(axisThrottleValue, joystickId, axisThrottle);

                try {
                    Thread.sleep(delay);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }
}
