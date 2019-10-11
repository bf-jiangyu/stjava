package priv.bingfeng.stjava.common.support;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

public class ExeclSxssf {

    private SXSSFWorkbook workbook;
    private SXSSFSheet sheet;

    public ExeclSxssf(int rowAccessWindowSize) {
        this.workbook = new SXSSFWorkbook(rowAccessWindowSize);
        this.sheet = this.workbook.createSheet();
    }

    public void addRow(String[] datas) {
        Row row = sheet.createRow(sheet.getLastRowNum() + 1);
        for (int i = 0; i < datas.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(new CellReference(cell).formatAsString());
        }
    }

    public void flush() throws IOException {
        sheet.flushRows();
    }

    public void writeToFile(String fileName) throws IOException {
        FileOutputStream out = new FileOutputStream(fileName);
        workbook.write(out);
        out.close();
        workbook.dispose();// 删除临时文件
    }

}
