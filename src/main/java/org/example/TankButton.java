package org.example;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

import java.awt.*;
import java.awt.event.KeyEvent;

public class TankButton {

    public static void init(){
        try {
            GlobalScreen.registerNativeHook();
            Robot robot=new Robot();
            GlobalScreen.addNativeKeyListener(new NativeKeyListener() {
                @Override
                public void nativeKeyPressed(NativeKeyEvent e) {
                    if (e.getKeyCode()==NativeKeyEvent.VC_E){

                        robot.keyPress(KeyEvent.VK_5);
                    }
                    if (e.getKeyCode()==NativeKeyEvent.VC_Q){

                        robot.keyPress(KeyEvent.VK_6);
                    }
                    if (e.getKeyCode()==NativeKeyEvent.VC_SPACE){

                        robot.keyPress(KeyEvent.VK_4);
                    }
                    System.out.println("Нажата клавиша: " + NativeKeyEvent.getKeyText(e.getKeyCode()));

                }

                @Override
                public void nativeKeyReleased(NativeKeyEvent e) {
//                   System.out.println("Клавиша отпущена: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
                }

                @Override
                public void nativeKeyTyped(NativeKeyEvent e) {

                }
            });

        } catch (NativeHookException e) {
            throw new RuntimeException(e);
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }
}
