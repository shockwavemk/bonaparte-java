package de.jpaw.bonaparte.util.impl;

import de.jpaw.bonaparte.core.BonaPortable;
import de.jpaw.bonaparte.core.CompactByteArrayComposer;
import de.jpaw.bonaparte.core.CompactByteArrayParser;
import de.jpaw.bonaparte.core.MimeTypes;
import de.jpaw.bonaparte.util.IMarshaller;
import de.jpaw.util.ApplicationException;
import de.jpaw.util.ByteArray;
import de.jpaw.util.ByteBuilder;

/** similar to MarshallerCompactBonaparte, but assumes the receiver has the same object structure.
 * Sub objects within JSON will be serializes as objects.
 *
 * @author mbi
 *
 */
public class RecordMarshallerCompactBonaparteIdentity implements IMarshaller {

    @Override
    public String getContentType() {
        return MimeTypes.MIME_TYPE_COMPACT_BONAPARTE;
    }

    @Override
    public ByteArray marshal(BonaPortable request) {
        CompactByteArrayComposer bac = new CompactByteArrayComposer(false);
        bac.writeRecord(request);
        ByteArray result = new ByteArray(bac.getBuffer(), 0, bac.getLength());
        bac.close();
        return result;
    }

    @Override
    public BonaPortable unmarshal(ByteBuilder buffer) throws ApplicationException {
        return (new CompactByteArrayParser(buffer.getCurrentBuffer(), 0, buffer.length())).readRecord();
    }
}
