package de.jpaw.bonaparte.api.codecs.impl;

import de.jpaw.bonaparte.api.codecs.IMessageEncoder;
import de.jpaw.bonaparte.core.BonaPortable;
import de.jpaw.bonaparte.core.CompactByteArrayComposer;
import de.jpaw.bonaparte.pojos.meta.ObjectReference;

public class CompactRecordEncoder<O extends BonaPortable> implements IMessageEncoder<O, byte []> {
    private final CompactByteArrayComposer bac = new CompactByteArrayComposer();

    @Override
    public byte[] encode(O obj, ObjectReference di) {
        bac.reset();
        bac.writeRecord(obj);
        return bac.getBytes();
    }
}
