package io.github.vmzakharov.ecdataframe.ui.function;

import io.github.vmzakharov.ecdataframe.dataframe.DataFrame;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

public class DataFrameTableModel
extends AbstractTableModel
{
    private final DataFrame dataFrame;

    public DataFrameTableModel(DataFrame aDataFrame)
    {
        this.dataFrame = aDataFrame;
    }

    @Override
    public int getRowCount()
    {
        return this.dataFrame.rowCount();
    }

    @Override
    public int getColumnCount()
    {
        return this.dataFrame.columnCount();
    }

    @Override
    public String getColumnName(int columnIndex)
    {
        return this.dataFrame.getColumnAt(columnIndex).getName();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        return this.dataFrame.getObject(rowIndex, columnIndex);
    }
}
