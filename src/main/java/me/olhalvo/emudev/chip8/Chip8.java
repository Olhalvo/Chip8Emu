package me.olhalvo.emudev.chip8;

import java.util.Arrays;
import java.util.Stack;

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
    private final Stack<Short> callStack = new Stack<>();
    private final boolean[] keys = new boolean[16];
    private final DisplayFrame renderedDisplay = new DisplayFrame();

    private byte timer = 0;
    private byte sound = 0;
    private short pc = 0;
    private short index = 0;
    private byte sp = 0;
    private int codesize = 0;
    private int currinst;
    private boolean exit = false;


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
        codesize = code.length;
        for(byte b : code){
            System.out.printf("0x%02x ", b);
        }
    }

    private void fetch() {
        //Java not having native uints and also casting everyting to int32 is stupid
        //Honestly I should have done this in C bcuz better language but idc
        //I missed you babe... I'm sorry uni forces me to use kotlin
        //I'm... home...
        if(pc + PROG_ADDR>= memory.length){
            System.out.println("memory exhausted");
            exit=true;
            return;
        }
        currinst = 0;
        currinst = (Short.toUnsignedInt((short)(memory[PROG_ADDR + pc] << 8)));
        pc++;
        currinst = (currinst | Byte.toUnsignedInt(memory[PROG_ADDR + pc]));
        pc++;
        System.out.printf("\n0x%04X", currinst);
    }

    private DecodedInst decode(){
        // Ima do this last cuz it's probably the most annoying part
        int first = (currinst & 0xf000) >> 12;
        System.out.printf("\n0x%01x ", first);
        return new DecodedInst(Instructions.NOOP, (byte)0, (byte)0, (byte)0);
    }

    private boolean execute(DecodedInst decoded){
        int x = 0;
        int y = 0;
        int z = 0;
        switch (decoded.inst()){
            case RET:
                if(callStack.isEmpty()){
                    System.out.println("Invalid return function :(");
                    return true;
                }
                pc = callStack.pop();
                break;
            case NOOP:
                break;
            case CLS:
                Arrays.fill(display, 0L);
                renderedDisplay.render(display);
                break;
            case JMP:
                jump();
                break;
            case CALL:
                callStack.push(pc);
                jump();
                break;
            case SEB:
                x = Byte.toUnsignedInt(regs[decoded.first()]);
                y = Byte.toUnsignedInt(decoded.second()) << 4;
                y |= Byte.toUnsignedInt(decoded.third());
                if(x==y){
                    pc +=2;
                }
                break;
            case SNEB:
                x = Byte.toUnsignedInt(regs[decoded.first()]);
                y = Byte.toUnsignedInt(decoded.second()) << 4;
                y |= Byte.toUnsignedInt(decoded.third());
                if(x!=y){
                    pc +=2;
                }
                break;
            case SER:
                x = Byte.toUnsignedInt(regs[decoded.first()]);
                y = Byte.toUnsignedInt(regs[decoded.second()]);
                if(x==y){
                    pc +=2;
                }
                break;
            case LDRB:
                x = Byte.toUnsignedInt(decoded.second()) << 4;
                x |= Byte.toUnsignedInt(decoded.third());
                regs[decoded.first()] = (byte) x;
                break;
            case ADDRB:
                x = Byte.toUnsignedInt(regs[decoded.first()]);
                y = Byte.toUnsignedInt(decoded.second()) << 4;
                y |= Byte.toUnsignedInt(decoded.third());
                int val = x + y;
                regs[decoded.first()] = (byte) (val%256);
                break;
            case LDRR:
                break;
            case ORRR:
                break;
            case ANDRR:
                break;
            case XORRR:
                break;
            case ADDRR:
                break;
            case SUBRR:
                break;
            case SHR:
                break;
            case SUBNRR:
                break;
            case SHL:
                break;
            case SNER:
                break;
            case LDIADD:
                break;
            case JMPV0:
                break;
            case RND:
                break;
            case DRW:
                break;
            case SKP:
                break;
            case SKNP:
                break;
            case LDRD:
                break;
            case LDRK:
                break;
            case LDDR:
                break;
            case LDSR:
                break;
            case ADDIR:
                break;
            case LDICR:
                break;
            case BCD:
                break;
            case STR:
                break;
            case LDR:
                break;
            default:
                System.out.println("Unexpected instruction (how did you do this !!?)");
                return true;
        }
        return false;
    }

    private void jump(){
        int addr = (Byte.toUnsignedInt(decode().first()) << 8);
        addr |= (Byte.toUnsignedInt(decode().second()) << 4);
        addr |= Byte.toUnsignedInt(decode().third());
        pc = (short)addr;
    }

    public void run() {
        final long startTime = System.currentTimeMillis();
        renderedDisplay.setVisible(true);
        while (!exit) {
            fetch();
            if(exit){
                continue;
            }
            DecodedInst inst = decode();
            exit = execute(inst);

        }
    }
}
