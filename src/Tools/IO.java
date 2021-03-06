/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Tools;

import java.io.*;


/**
 *
 * @author spln
 */
public class IO extends RandomAccessFile {
    
    public String getPath;
    
    public IO (String name, String mode) throws IOException  {       
        super(name, mode);
        getPath = name;
    }
    
    public IO (File file, String mode) throws IOException {    
        super(file, mode);
        getPath = file.getAbsolutePath();
    }
    
    public int readUint8() throws IOException {
        return this.read();
    }
    
    public int readUint16() throws IOException {
        return (this.read() << 8)|this.read();
    }
    
    public int readUint24() throws IOException {
        return (this.read() << 16)|(this.read() << 8)|this.read();
    }
    
    public long readUint32() throws IOException {
        return ((long)this.read() << 24)|(long)this.readUint24();
    }
    
    public String readString() throws IOException {
        int chr; String buff = "";
        while ((chr=this.readUint8())!= 0x00) {
            buff += (char)chr;
        }
        return buff;
    }
    
    public String readString(int lim) throws IOException {
        int i, chr; String buff = "";
        for (i = 0; (chr=this.readUint8()) != 0x00 && i < lim; i++) {
            buff += (char)chr;
        }
        return buff;
    }
        
}

