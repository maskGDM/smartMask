package com.example.smartmask;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import java.util.ArrayList;


public class ModalAirQualityTable {

    private Context ctx;
    private TableLayout table;

    private int headerBackGroundColor = 500038;//black
    private int rowsBackGroundColor = 500061;//white

    private int headersForeGroundColor = 500061;//white
    private int rowsForeGroundColor = 500038;//black

    private int fontSize = 14;

    private String[] headers;
    private ArrayList<String[]> rows;

    public ModalAirQualityTable(Context ctx, TableLayout table) {
        this.ctx = ctx;
        this.table = table;
        headers = new String[0];
        rows = new ArrayList<>();
    }

    public String[] getHeaders() {
        return headers;
    }

    public void setHeaders(String[] headers) {
        this.headers = headers;

    }

    public ArrayList<String[]> getRows() {
        return this.rows;
    }

    public void setRows(ArrayList<String[]> rows) {
        this.rows = rows;

    }

    public void makeTable() {
        //limpia la tabla
        table.removeAllViews();

        createRow(this.headers, true);
        createTable(this.rows);
    }

    /* Config */
    public void setHeaderBackGroundColor(int headerBackGroundColor) {
        this.headerBackGroundColor = headerBackGroundColor;
    }

    public void setRowsBackGroundColor(int rowsBackGroundColor) {
        this.rowsBackGroundColor = rowsBackGroundColor;
    }

    public void setHeadersForeGroundColor(int headersForeGroundColor) {
        this.headersForeGroundColor = headersForeGroundColor;
    }

    public void setRowsForeGroundColor(int rowsForeGroundColor) {
        this.rowsForeGroundColor = rowsForeGroundColor;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    private TableRow newRow() {
        TableRow tmpRow = new TableRow(this.ctx);
        return tmpRow;
    }

    private TextView newCell() {
        TextView tmpCell = new TextView(this.ctx);
        tmpCell.setGravity(Gravity.CENTER);
        tmpCell.setTextSize(fontSize);
        tmpCell.setPadding(15,5,15,5);
        return tmpCell;
    }

    private void createTable(ArrayList<String[]> data) {
        for (int ind = 0; ind < data.size(); ind++) {
            createRow(data.get(ind), false);
        }
    }

    private void createRow(String[] value, boolean isHead) {
        //creo una nueva fila
        TableRow tmpRow = newRow();
        //ciclo creando las columnas
        for (int ind = 0; ind < value.length; ind++) {
            //agrega la columnas a la fila
            createCell(tmpRow, isHead, value[ind]);
        }
        //se establece el color a la fila
        if(isHead){
            tmpRow.setBackground(ContextCompat.getDrawable(ctx, headerBackGroundColor));
        }else{
            tmpRow.setBackground(ContextCompat.getDrawable(ctx, rowsBackGroundColor));
        }
        //se ubica la fila de cabecera en la tabla
        table.addView(tmpRow);
    }

    private void createCell(TableRow row, boolean isHead, String value) {
        if (value == null) {
            value = "";
        }
        TextView tmpCell = newCell();
        tmpCell.setText(value.trim());
        if(isHead){
            tmpCell.setTextColor(ContextCompat.getColor(ctx, headersForeGroundColor));
            tmpCell.setTypeface(tmpCell.getTypeface(), Typeface.BOLD);
        }else{
            tmpCell.setTextColor(ContextCompat.getColor(ctx, rowsForeGroundColor));
        }
        row.addView(tmpCell, newTableRowParams());
    }

    private TableRow.LayoutParams newTableRowParams() {
        TableRow.LayoutParams param = new TableRow.LayoutParams();
        param.setMargins(1, 1, 1, 1);
        param.weight = 1;
        return param;
    }
}