package me.olhalvo.emudev.chip8;

public enum Instructions {
    CLS, //00E0
    RET, //00EE
    NOOP, // Anything that pmo also the sys addr thing cuz fuck ts
    JMP, // 1nnn
    CALL, // 2nnn
    SEB, //3xkk
    SNEB, //4xkk
    SER, //5xy0
    LDRB, //6xkk
    ADDRB, //7xkk
    LDRR, //8xy0
    ORRR, //8xy1
    ANDRR, //8xy2
    XORRR, //8xy3
    ADDRR, //8xy4
    SUBRR, //8xy5
    SHR, //8xy6, I have no Idea how to implement this and every datasheet says smth diff, guess ill kms?
    SUBNRR,//8xy7
    SHL, //8xyE, Same thing ig(also why tf does it jump from 7 to E? does chip8 hate continuity
    SNER, //9xy0
    LDIADD, //Annn
    JMPV0, //Bnnn
    RND, //Cxkk, Weird ahh instruction but sur
    DRW, //Dxyn, UGHHHHHHHHHHHHHHHHHHHHHHHH
    SKP, //Ex9E, UGHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH
    SKNP, //ExA1, Wtv bro im js kms or smth
    //Weird fucking instruction signatures that prove that chip8 hates consitency
    LDRD, //FX07
    LDRK, //FX0A, ffs
    LDDR, //Fx15
    LDSR, //Fx18 I probably wont play sounds but ye
    ADDIR, //Fx1E
    LDICR, //Fx29 this is just kinda annoying but ye
    BCD, //Fx33 fakjfkjasfkjhafkjhafkjhakfjhakj4
    STR, //Fx55 KMS
    LDR, //Fx65 KMS
}