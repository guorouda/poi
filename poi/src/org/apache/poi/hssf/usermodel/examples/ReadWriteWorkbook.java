/* ====================================================================
   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to You under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
==================================================================== */

package org.apache.poi.hssf.usermodel.examples;

import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.examples.ListFiles;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFCell;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.TreeMap;

/**
 * This example demonstrates opening a workbook, modifying it and writing
 * the results back out.
 *
 * @author Glen Stampoultzis (glens at apache.org)
 */
public class ReadWriteWorkbook {
    public static void main(String[] args) throws IOException {
        FileInputStream fileIn = null;
        FileOutputStream fileOut = null;
        HSSFWorkbook wb = null;

        
		String path = "e:\\1\\1";
		List<String> list = ListFiles.listFiles(path);
		TreeMap<String, String> treemap = ListFiles.getPicPath(list);
		
        try
        {
            fileIn = new FileInputStream("e:\\1\\15.xls");
            POIFSFileSystem fs = new POIFSFileSystem(fileIn);
            wb = new HSSFWorkbook(fs);
            DataFormatter formatter = new DataFormatter();
            HSSFSheet sheet = wb.getSheetAt(0);
            int k = 0;
            for(Row row:sheet){
            	k++;
            	if(k<2){
            		continue;
            	}
            	int i = 0;
            	String s = "";
            	for(Cell cell:row){
            		i++;
            		if(i == 2){
            			System.out.println("        " + formatter.formatCellValue(cell));
            			s = treemap.get(formatter.formatCellValue(cell));
            			break;
            		}
            	}
            	Cell cell = row.getCell(27);
            	if(cell == null){
            		cell = row.createCell(27);
            	}
            	cell.setCellValue(s);
            }
            
            HSSFRow row = sheet.getRow(2);
            if (row == null)
                row = sheet.createRow(2);
            HSSFCell cell = row.getCell(3);
            if (cell == null)
                cell = row.createCell(3);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            cell.setCellValue("a test");

            // Write the output to a file
            fileOut = new FileOutputStream("e:\\1\\workbookout.xls");
            wb.write(fileOut);
        } finally {
            if (fileOut != null)
                fileOut.close();
            if (fileIn != null)
                fileIn.close();
            if(wb != null){
            	wb.close();
            }
        }
    }
}