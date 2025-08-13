package me.olhalvo.emudev.chip8;

public record DecodedInst(Instructions inst, byte first, byte second, byte third) {
}