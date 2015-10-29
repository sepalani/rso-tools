/*  This file is part of RsoTool.

    RsoTool is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    RsoTool is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
 */
package Tools;

import java.io.*;

/**
 *
 * @author spln
 */
public class RSO {
    
    // Header Information
    public long unknown0x00, unknown0x04,
                sectionNumber, identifier, nameOffset, nameSize, version,
                unknown0x1C;
    
    // Unknown, could be empty...
    public long unknown0x20, unknown0x24, unknown0x28, unknown0x2C;
    
    // Relocation table
    public long irtOffset, irtSize, ertOffset, ertSize;
    
    // Imports/Exports
    public long importsOffset, importsSize, importsName,
                exportsOffset, exportsSize, exportsName,
                unknown0x58, unknown0x5C;
    
    // Data Blocks
    public long block01Offset, block01Size,
                block02Offset, block02Size,
                block03Offset, block03Size,
                block04Offset, block04Size,
                block05Offset, block05Size,
                block06Offset, block06Size;
    
    // File information
    File getFile = null;
    easyUI eUI = new easyUI();
    
    public RSO(File in) {
        if (in == null) {return;}
        if (in.exists()) {
            getFile = in;
        }
    }
    
    public RSO(String str) {
        if (str == null) {return;}
        File in = new File(str);
        if (in.exists()) {
            getFile = in;
        }
    }
    
    public boolean exists() {
        if (getFile == null) {
            return false;
        } else {
            return getFile.exists();
        }
    }
    
    public void load(long pos) {
        if (!exists()) {return;}
        IO file = null;
        
        try {           
                //--------------------- HEADER ---------------------//                
                file = new IO(getFile, "r");
                file.seek(pos);
                /*0x00 (uint32)*/ unknown0x00      =   file.readUint32();
                /*0x04 (uint32)*/ unknown0x04      =   file.readUint32();
                /*0x08 (uint32)*/ sectionNumber    =   file.readUint32();
                /*0x0C (uint32)*/ identifier       =   file.readUint32(); 
                /*0x10 (uint32)*/ nameOffset       =   file.readUint32();
                /*0x14 (uint32)*/ nameSize         =   file.readUint32();
                /*0x18 (uint32)*/ version          =   file.readUint32();
                /*0x1C (uint32)*/ unknown0x1C      =   file.readUint32();

                //--- Line 0x20 could be empty
                /*0x20 (uint32)*/ unknown0x20      =   file.readUint32();
                /*0x24 (uint32)*/ unknown0x24      =   file.readUint32();
                /*0x28 (uint32)*/ unknown0x28      =   file.readUint32();
                /*0x2C (uint32)*/ unknown0x2C      =   file.readUint32();
    
                //--- Internals/Externals relocation table (irt/ert)
                /*0x30 (uint32)*/ irtOffset        =   file.readUint32();
                /*0x34 (uint32)*/ irtSize          =   file.readUint32();
                /*0x38 (uint32)*/ ertOffset        =   file.readUint32();
                /*0x3C (uint32)*/ ertSize          =   file.readUint32();
                /*0x40 (uint32)*/ exportsOffset    =   file.readUint32();
                /*0x44 (uint32)*/ exportsSize      =   file.readUint32();
                /*0x48 (uint32)*/ exportsName      =   file.readUint32(); // Had some troubles (disordered names)
                /*0x4C (uint32)*/ importsOffset    =   file.readUint32();
                /*0x50 (uint32)*/ importsSize      =   file.readUint32();
                /*0x54 (uint32)*/ importsName      =   file.readUint32();
                /*0x58 (uint32)*/ unknown0x58      =   file.readUint32();
                /*0x5C (uint32)*/ unknown0x5C      =   file.readUint32();

                /*0x60 (uint32)*/ block01Offset    =   file.readUint32();
                /*0x64 (uint32)*/ block01Size      =   file.readUint32();
                /*0x68 (uint32)*/ block02Offset    =   file.readUint32();
                /*0x6C (uint32)*/ block02Size      =   file.readUint32();
                /*0x70 (uint32)*/ block03Offset    =   file.readUint32();
                /*0x74 (uint32)*/ block03Size      =   file.readUint32();
                /*0x78 (uint32)*/ block04Offset    =   file.readUint32();
                /*0x7C (uint32)*/ block04Size      =   file.readUint32();
                /*0x80 (uint32)*/ block05Offset    =   file.readUint32();
                /*0x84 (uint32)*/ block05Size      =   file.readUint32();
                /*0x88 (uint32)*/ block06Offset    =   file.readUint32();
                /*0x8C (uint32)*/ block06Size      =   file.readUint32();                
        } catch (IOException e) {
            System.out.println("--- Failed to load RSO Header! ---");
            e.printStackTrace();
        } finally {
            if (file != null) {
                try { file.close(); }
                catch (IOException e) { e.printStackTrace(); }
            }
        }           
    }
    
    public String check() {
        if (!exists()) { return "ERROR: File doesn't exist!"; }
        String ret = "";
        
        // Check if the file seems normal
        long size = getFile.length();
        if (size < nameOffset+nameSize)         { ret += "Name doesn't match! <br />"; }
        if (size < irtOffset+irtSize)           { ret += "Internal Relocation Table doesn't match! <br />"; }
        if (size < ertOffset+ertSize)           { ret += "External Relocation Table doesn't match! <br />"; }
        if (size < importsOffset+importsSize)   { ret += "Imports don't match! <br />"; }
        if (size < exportsOffset+exportsSize)   { ret += "Exports don't match! <br />"; }
        if (size < block01Offset+block01Size)   { ret += "Data block #1 doesn't match! <br />"; }
        if (size < block02Offset+block02Size)   { ret += "Data block #2 doesn't match! <br />"; }
        if (size < block03Offset+block03Size)   { ret += "Data block #3 doesn't match! <br />"; }
        if (size < block04Offset+block04Size)   { ret += "Data block #4 doesn't match! <br />"; }
        if (size < block05Offset+block05Size)   { ret += "Data block #5 doesn't match! <br />"; }
        if (size < block06Offset+block06Size)   { ret += "Data block #6 doesn't match! <br />"; }
        
        return String.format(ret);
    }
    
    public void extractDataBlock(long addr, long size, File out) throws IOException {
        if (!exists()) {return;}
        if (getFile.length() < size + addr) {
            eUI.messageBoxExt(null,"Invalid block size and/or address.","Write error",easyUI.MBIcon_ERROR);
            return;
        }
        FileInputStream fis = new FileInputStream(getFile);
        BufferedInputStream bis = new BufferedInputStream(fis);
        FileOutputStream fos = new FileOutputStream(out);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        bis.skip(addr);
        for (long i=size; i!=0; i--) {
            bos.write(bis.read());
        }
        bos.close();
        bis.close();
    }
    
    public void extractAllDataBlock(File dir) throws IOException {
        String path = dir.getAbsolutePath()+"/";
        
        if (dir.exists() && dir.isDirectory()) {
            File b1, b2, b3, b4, b5, b6;
            b1 = new File(path+"block01.bin"); b2 = new File(path+"block02.bin");
            b3 = new File(path+"block03.bin"); b4 = new File(path+"block04.bin");
            b5 = new File(path+"block05.bin"); b6 = new File(path+"block06.bin");
            if (! (b1.exists() && b2.exists() && b3.exists() &&
                   b4.exists() && b5.exists() && b6.exists())) {
                extractDataBlock(block01Offset, block01Size, b1);
                extractDataBlock(block02Offset, block02Size, b2);
                extractDataBlock(block03Offset, block03Size, b3);
                extractDataBlock(block04Offset, block04Size, b4);
                extractDataBlock(block05Offset, block05Size, b5);
                extractDataBlock(block06Offset, block06Size, b6);
                
            } else {
                eUI.messageBoxExt(null,"File(s) block##.bin already exist(s)!","Failed to write",easyUI.MBIcon_ERROR);
            }
        } else {
            eUI.messageBoxExt(null,"Directory doesn't exist!","Failed to write",easyUI.MBIcon_ERROR);
        }
    }
    
}
