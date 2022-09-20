package io.github.vmzakharov.ecdataframe.ui.function;

import io.github.vmzakharov.ecdataframe.dataframe.DataFrame;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;

public class FloatingBrowseWindow
extends JFrame
{
    public FloatingBrowseWindow(DataFrame dataFrame)
    throws HeadlessException
    {
        super(dataFrame.getName() + "[ rows: " + dataFrame.rowCount() +", columns: " + dataFrame.columnCount() + "]");
        this.setLayout(new BorderLayout());

        TableModel tableModel = new DataFrameTableModel(dataFrame);
        JTable table = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        this.add(scrollPane, BorderLayout.CENTER);

        this.setSize(500, 500);
    }
}
