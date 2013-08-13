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
    along with RsoTool.  If not, see <http://www.gnu.org/licenses/>.
 */
package Tools;

import java.awt.*;
import javax.swing.*;
import javax.swing.filechooser.*;

/**
 *
 * @author spln
 */
public class easyUI {
    
    public java.io.File getOpenFilename(String[] desc, String[] filter) {
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.setAcceptAllFileFilterUsed(false);
        fc.setMultiSelectionEnabled(false);
        
        if (desc.length == filter.length) {
            for (int i = 0; i < desc.length; i++) {
                fc.addChoosableFileFilter(addFileFilter(desc[i], filter[i]));
            }   
        }
        fc.setAcceptAllFileFilterUsed(true);
        
        if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            return fc.getSelectedFile();
        } else {
            return null;
        }     
    }
    
    public java.io.File getSaveFilename(String[] desc, String[] filter) {
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.setAcceptAllFileFilterUsed(false);
        fc.setMultiSelectionEnabled(false);
        
        if (desc.length == filter.length) {
            for (int i = 0; i < desc.length; i++) {
                fc.addChoosableFileFilter(addFileFilter(desc[i], filter[i]));
            }   
        }
        fc.setAcceptAllFileFilterUsed(true);
        
        if (fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            return fc.getSelectedFile();
        } else {
            return null;
        }     
    }
    
    public java.io.File getDirectory() {
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fc.setMultiSelectionEnabled(false);
        
        if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            return fc.getSelectedFile();
        } else {
            return null;
        }   
    }
    
    public FileFilter addFileFilter(String desc, String ext) {
        final String d; d = desc;
        final String[] e; e = ext.split("\\|");
        return new FileFilter() {
            @Override
            public String getDescription() {
                return d;
            }
            @Override
            public boolean accept(java.io.File f) {
                if (f.isDirectory()) {
                    return true;
                } else {
                    for (int i = 0; i < e.length; i++) {
                        if (f.getName().toLowerCase().endsWith(e[i]) || e[i].equals("")) {
                            return true;
                        }
                    }
                    return false;
                }
            }
        };
    }
    
    
    //--- Easy MessageBox
    public final static int MBIcon_PLAIN        = JOptionPane.PLAIN_MESSAGE;
    public final static int MBIcon_INFORMATION  = JOptionPane.INFORMATION_MESSAGE;
    public final static int MBIcon_WARNING      = JOptionPane.WARNING_MESSAGE;
    public final static int MBIcon_ERROR        = JOptionPane.ERROR_MESSAGE;
    public final static int MBIcon_QUESTION     = JOptionPane.QUESTION_MESSAGE;
    
    public void messageBox(Component frame, Object message) {
        JOptionPane.showMessageDialog(frame, message);
    }
    
    public void messageBoxExt(Component frame, Object message, String title, int icon) {
        JOptionPane.showMessageDialog(frame, message, title, icon);
    }
    
    public String getString(Component frame, String mess, String def) {
        return JOptionPane.showInputDialog(frame, mess, def);
    }
    
    public String getStringExt(Component frame, String mess, String title, int icon) {
        return JOptionPane.showInputDialog(frame, mess, title, icon);
    }
    
}
