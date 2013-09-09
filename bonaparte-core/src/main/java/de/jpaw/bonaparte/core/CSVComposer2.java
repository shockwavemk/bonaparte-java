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
package de.jpaw.bonaparte.core;

import java.io.IOException;

import de.jpaw.bonaparte.pojos.meta.NumericElementaryDataItem;
/**
 * The CSVComposer class.
 *
 * @author Michael Bischoff
 * @version $Revision$
 *
 *          CSV composer with more fields produced through (Number)Format.
 *          See difference to CSVFormat for integral numbers for Locale Locale.forLanguageTag("th-TH-u-nu-thai"),
 *          or negative numbers for new Locale("ar", "EG").
 */

public class CSVComposer2 extends CSVComposer {


    public CSVComposer2(Appendable work, CSVConfiguration cfg) {
        super(work, cfg);
    }

    // byte
    @Override
    public void addField(byte n) throws IOException {
        writeSeparator();
        addRawData(numberFormat.format(n));
    }
    // short
    @Override
    public void addField(short n) throws IOException {
        writeSeparator();
        addRawData(numberFormat.format(n));
    }
    // integer
    @Override
    public void addField(int n) throws IOException {
        writeSeparator();
        addRawData(numberFormat.format(n));
    }

    // int(n)
    @Override
    public void addField(NumericElementaryDataItem di, Integer n) throws IOException {
        writeSeparator();
        addRawData(numberFormat.format(n));
    }

    // long
    @Override
    public void addField(long n) throws IOException {
        writeSeparator();
        addRawData(numberFormat.format(n));
    }
}
