/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tables;

import domain.CFile;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author a_ziv
 */
public class FileTableModel extends AbstractTableModel{
    private List<CFile> list;
    private String[] columnNames = {"Name", "Path"};
    
    public FileTableModel(List<CFile> list){
        this.list = list;
    }
    
    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }    

    @Override
    public int getRowCount() {
        return list.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public String getValueAt(int rowIndex, int columnIndex) {
        if(columnIndex==0){
            return list.get(rowIndex).getName();
        }
        if(columnIndex==1){
            return list.get(rowIndex).getPath();
        }
        return null;   
    }
}
