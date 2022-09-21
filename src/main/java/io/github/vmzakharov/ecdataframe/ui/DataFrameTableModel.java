package io.github.vmzakharov.ecdataframe.ui;

import io.github.vmzakharov.ecdataframe.dataframe.DataFrame;

import javax.swing.table.AbstractTableModel;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class DataFrameTableModel
extends AbstractTableModel
{
    private final DataFrame dataFrame;
    private final Class<?>[] columnClass;

    public DataFrameTableModel(DataFrame aDataFrame)
    {
        this.dataFrame = aDataFrame;

        columnClass = new Class[dataFrame.columnCount()];
        this.dataFrame.getColumns().forEachWithIndex((col, colIndex) -> columnClass[colIndex] = switch (col.getType())
                        {
                            case STRING -> String.class;
                            case LONG -> Long.class;
                            case DOUBLE -> Double.class;
                            case DATE -> LocalDate.class;
                            case DATE_TIME -> LocalDateTime.class;
                            default -> Object.class;
                        }
                );
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

    @Override
    public Class<?> getColumnClass(int columnIndex)
    {
        return this.columnClass[columnIndex];
    }
}
