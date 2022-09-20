package io.github.vmzakharov.ecdataframe.ui;

import io.github.vmzakharov.ecdataframe.dataframe.DataFrame;
import io.github.vmzakharov.ecdataframe.dataframe.DfColumn;
import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.*;

public class DataFrameUtil
{
    static public void assertEquals(DataFrame expected, DataFrame actual)
    {

        if (expected.rowCount() != actual.rowCount() || expected.columnCount() != actual.columnCount())
        {
            fail("Dimensions don't match: expected rows " + expected.rowCount() + ", cols " + expected.columnCount()
                    + ", actual rows " + actual.rowCount() + ", cols " + actual.columnCount());
        }

        Assertions.assertEquals(expected.getColumns().collect(DfColumn::getName), actual.getColumns().collect(DfColumn::getName), "Column names");

        Assertions.assertEquals(expected.getColumns().collect(DfColumn::getType), actual.getColumns().collect(DfColumn::getType), "Column types");

        int colCount = expected.columnCount();
        int rowCount = expected.rowCount();

        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++)
        {
            for (int colIndex = 0; colIndex < colCount; colIndex++)
            {
                Assertions.assertEquals(expected.getObject(rowIndex, colIndex), actual.getObject(rowIndex, colIndex),
                        "Different values in row " + rowIndex + ", column " + colIndex);
            }
        }
    }
}
