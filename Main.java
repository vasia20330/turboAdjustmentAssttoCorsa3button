package org.example;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import com.github.kwhat.jnativehook.mouse.*;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;



public class Main {
    public static void main(String[] args) {
//        int screenWidth=Toolkit.getDefaultToolkit().getScreenSize().width;
//        int screenHeight=Toolkit.getDefaultToolkit().getScreenSize().height;
//        System.out.println(screenWidth);
//        System.out.println(screenHeight);
//      Assetto911 assetto911=new Assetto911();
//      assetto911.init();

        ACC acc=new ACC();
        acc.init();

    }
}