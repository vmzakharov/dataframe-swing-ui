package io.github.vmzakharov.ecdataframe.ui;

import io.github.vmzakharov.ecdataframe.dataframe.DataFrame;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;

public class FloatingBrowseWindow
extends JFrame
{
    private double rowHeight;

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

        ButtonPanel buttonPanel = new ButtonPanel();

        JButton decreaseFontSizeButton = new JButton("A-");
        decreaseFontSizeButton.addActionListener(e -> changeFontSize(table, 0.9f));

        JButton increaseFontSizeButton = new JButton("A+");
        increaseFontSizeButton.addActionListener(e -> changeFontSize(table, 1.1f));

        buttonPanel.addButton(increaseFontSizeButton);
        buttonPanel.addButton(decreaseFontSizeButton);

        this.add(buttonPanel, BorderLayout.SOUTH);

        this.setSize(500, 500);

        this.rowHeight = table.getRowHeight();
    }

    private void changeFontSize(JTable table, float factor)
    {
        Font currentFont = table.getFont();
        float size = currentFont.getSize2D();
        Font newFont = currentFont.deriveFont(size * factor);

        table.setFont(newFont);

        this.rowHeight *= factor;

        table.setRowHeight((int) this.rowHeight);
    }
}
