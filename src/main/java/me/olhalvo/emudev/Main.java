package me.olhalvo.emudev;

import me.olhalvo.emudev.chip8.Chip8;

public class Main {
    public static void main(String[] args) {
        Chip8 machine  = new Chip8();
        machine.load(new byte[]{(byte)0xFF, 0x09, (byte)0xF4, (byte)0xF2, (byte) 0xFF, (byte) 0xFF});
        machine.run();
    }
}