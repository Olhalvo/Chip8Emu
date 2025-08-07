package me.olhalvo.emudev.chip8;

import java.util.Arrays;

public class Chip8 {
    private final byte[] ZERO = {(byte) 0xF0, (byte) 0x90, (byte) 0x90, (byte) 0x90, (byte) 0xF0};
    private final byte[] ONE = {(byte) 0x20, (byte) 0x60, (byte) 0x20, (byte) 0x20, (byte) 0x70};
    private final byte[] TWO = {(byte) 0xF0, (byte) 0x10, (byte) 0xF0, (byte) 0x80, (byte) 0xF0};
    private final byte[] THREE = {(byte) 0xF0, (byte) 0x10, (byte) 0xF0, (byte) 0x10, (byte) 0xF0};
    private final byte[] FOUR = {(byte) 0x90, (byte) 0x90, (byte) 0xF0, (byte) 0x10, (byte) 0x10};
    private final byte[] FIVE = {(byte) 0xF0, (byte) 0x80, (byte) 0xF0, (byte) 0x10, (byte) 0xF0};
    private final byte[] SIX = {(byte) 0xF0, (byte) 0x80, (byte) 0xF0, (byte) 0x90, (byte) 0xF0};
    private final byte[] SEVEN = {(byte) 0xF0, (byte) 0x10, (byte) 0x20, (byte) 0x40, (byte) 0x40};
    private final byte[] EIGHT = {(byte) 0xF0, (byte) 0x90, (byte) 0xF0, (byte) 0x90, (byte) 0xF0};
    private final byte[] NINE = {(byte) 0xF0, (byte) 0x90, (byte) 0xF0, (byte) 0x10, (byte) 0xF0};
    private final byte[] A = {(byte) 0xF0, (byte) 0x90, (byte) 0xF0, (byte) 0x90, (byte) 0x90};
    private final byte[] B = {(byte) 0xE0, (byte) 0x90, (byte) 0xE0, (byte) 0x90, (byte) 0xE0};
    private final byte[] C = {(byte) 0xF0, (byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0xF0};
    private final byte[] D = {(byte) 0xE0, (byte) 0x90, (byte) 0x90, (byte) 0x90, (byte) 0xE0};
    private final byte[] F = {(byte) 0xF0, (byte) 0x80, (byte) 0xF0, (byte) 0x80, (byte) 0x80};
    private final byte[] E = {(byte) 0xF0, (byte) 0x80, (byte) 0xF0, (byte) 0x80, (byte) 0xF0};

    private final int CHAR_ADDR = 0x50;
    private final int PROG_ADDR = 0x200;

    private final byte[] memory = new byte[4096];
    private final byte[] regs = new byte[16];
    private final short[] stack = new short[32];
    private final byte timer = 0;
    private final byte sound = 0;

    private short pc = 0;
    private short index = 0;
    private byte sp = 0;
    private int currinst;


    public final long[] display = new long[32];


    public Chip8() {
        int mempos = CHAR_ADDR;
        System.arraycopy(ZERO, 0, memory, mempos, ZERO.length);
        mempos += ZERO.length;
        System.arraycopy(ONE, 0, memory, mempos, ZERO.length);
        mempos += ZERO.length;
        System.arraycopy(TWO, 0, memory, mempos, ZERO.length);
        mempos += ZERO.length;
        System.arraycopy(THREE, 0, memory, mempos, ZERO.length);
        mempos += ZERO.length;
        System.arraycopy(FOUR, 0, memory, mempos, ZERO.length);
        mempos += ZERO.length;
        System.arraycopy(FIVE, 0, memory, mempos, ZERO.length);
        mempos += ZERO.length;
        System.arraycopy(SIX, 0, memory, mempos, ZERO.length);
        mempos += ZERO.length;
        System.arraycopy(SEVEN, 0, memory, mempos, ZERO.length);
        mempos += ZERO.length;
        System.arraycopy(EIGHT, 0, memory, mempos, ZERO.length);
        mempos += ZERO.length;
        System.arraycopy(NINE, 0, memory, mempos, ZERO.length);
        mempos += ZERO.length;
        System.arraycopy(A, 0, memory, mempos, ZERO.length);
        mempos += ZERO.length;
        System.arraycopy(B, 0, memory, mempos, ZERO.length);
        mempos += ZERO.length;
        System.arraycopy(C, 0, memory, mempos, ZERO.length);
        mempos += ZERO.length;
        System.arraycopy(D, 0, memory, mempos, ZERO.length);
        mempos += ZERO.length;
        System.arraycopy(E, 0, memory, mempos, ZERO.length);
        mempos += ZERO.length;
        System.arraycopy(F, 0, memory, mempos, ZERO.length);

    }

    public void load(byte[] code){
        System.arraycopy(code, 0, memory, PROG_ADDR, code.length);
        System.out.println(Arrays.toString(code));
    }

    private void fetch() {
        //Java not having native uints and also casting everyting to int32 is stupid
        //Honestly I should have done this in C bcuz better language but idc
        //I missed you babe... I'm sorry uni forces me to use kotlin
        //I'm... home...
        currinst = 0;
        currinst = (Short.toUnsignedInt((short)(memory[PROG_ADDR + (pc++)] << 8)));
        System.out.printf("0x%02X ", currinst);
        currinst = (currinst | Byte.toUnsignedInt(memory[PROG_ADDR + (pc++)]));
        System.out.printf("0x%02X \n", currinst);
    }

    private void decode(){

    }

    private void execute(){

    }

    public void run(){
      for(int i = 0; i < 32; i++) {
          fetch();
          decode();
          execute();
      }
    }
}
