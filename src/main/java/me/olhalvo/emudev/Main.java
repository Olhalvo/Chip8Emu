package me.olhalvo.emudev;

import me.olhalvo.emudev.chip8.Emulator;

public class Main {
    public static void main(String[] args) {
        Emulator emulator = new Emulator();
        emulator.run("2-ibm-logo.ch8");

    }
}