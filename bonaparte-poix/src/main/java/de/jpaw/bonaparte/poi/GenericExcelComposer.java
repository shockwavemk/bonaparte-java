/*
 * Copyright 2012 Michael Bischoff
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.jpaw.bonaparte.poi;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Implements the output of Bonaparte objects into Excel formats.
 *
 * @author Michael Bischoff
 * @version $Revision$
 */

public class GenericExcelComposer extends BaseExcelComposer implements ExcelWriter {
    // variables set by constructor
    private final ExcelFormat fmt;  // xls or xlsx

    public GenericExcelComposer(ExcelFormat fmt) {
        super(fmt == ExcelFormat.XLSX_STREAMING ? new SXSSFWorkbook() : fmt == ExcelFormat.XLSX ? new XSSFWorkbook() : new HSSFWorkbook());
        this.fmt = fmt;
    }

    @Override
    public void writeToFile(String filename) throws IOException {
        try (FileOutputStream out = new FileOutputStream(filename)) {
            write(out);
        } finally {
            if (fmt == ExcelFormat.XLSX_STREAMING)
                ((SXSSFWorkbook)xls).dispose();
        }
    }
}
