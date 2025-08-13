package me.olhalvo.emudev.chip8;

import java.io.*;
import java.util.ArrayList;

public class Emulator {
    Chip8 chip8 = new Chip8();
    public void run(String path){
        byte[] prog = readBinaryFile(path);
        chip8.load(prog);
        chip8.run();
        System.out.println("awaiting for window close");
        while(true){

        }
    }




    private byte[] readBinaryFile(String path){
        File file = new File(path);
        ArrayList<Byte> data = new ArrayList<>();
        try{
            DataInputStream dis = new DataInputStream(new FileInputStream(file));
            while(true){
                data.add(dis.readByte());
            }
        }
        catch (EOFException e){
            byte[] arr = new byte[data.size()];
            for(int i = 0; i < data.size(); i++){
                arr[i] = data.get(i);
            }
            return arr;
        }
        catch (IOException e) {
            System.err.println("Could not open or create file at: " + path);
            return null;
        }
    }

}
