package de.jpaw.bonaparte.refsp;

import java.io.IOException;
import java.util.Map;

import de.jpaw.bonaparte.core.BonaCustom;
import de.jpaw.bonaparte.core.CompactByteArrayComposer;
import de.jpaw.bonaparte.pojos.api.AbstractRef;
import de.jpaw.bonaparte.pojos.api.VoidRef;
import de.jpaw.bonaparte.pojos.meta.ClassDefinition;
import de.jpaw.bonaparte.pojos.meta.FieldDefinition;
import de.jpaw.bonaparte.pojos.meta.ObjectReference;
import de.jpaw.util.ApplicationException;
import de.jpaw.util.ByteBuilder;

/** A composer of the compact format family, using classIds instead of names and replacing references to other classes by the key. */
public class ReferencingComposer extends CompactByteArrayComposer {
    private static final AbstractRef DOES_NOT_MATCH_ANY = new VoidRef();
    private final Map<ClassDefinition,RefResolver<AbstractRef, ?, ?>> resolvers;
    private AbstractRef excludedObject = DOES_NOT_MATCH_ANY;       // an object not to replace, usually the outer one, in case the resolver map is created as a static object

    public ReferencingComposer(ByteBuilder out, Map<ClassDefinition,RefResolver<AbstractRef, ?, ?>> resolvers) {
        super(out, true);
        this.resolvers = resolvers;
    }

    public void excludeObject(AbstractRef obj) {
        excludedObject = obj == null ? DOES_NOT_MATCH_ANY : obj;
    }

    protected RefResolver<AbstractRef, ?, ?> getReferencedResolver(ObjectReference di) {
        return di.getLowerBound() == null ? null : resolvers.get(di.getLowerBound());
    }

    @Override
    public void startMap(FieldDefinition di, int currentMembers) {
        out.writeByte(MAP_BEGIN);
        try {
            // write the map count or an indicator if the object is external (because size could change independently then)
            // important: the provided map must be identical for composing and parsing
            if (di instanceof ObjectReference && getReferencedResolver((ObjectReference)di) != null) {
                intOut(COLLECTION_COUNT_REF);
            } else {
                intOut(currentMembers);
            }
        } catch (IOException e) {
            // IOException from ByteArray operation???
            throw new RuntimeException(e);
        }
    }

    @Override
    public void startArray(FieldDefinition di, int currentMembers, int sizeOfElement) {
        out.writeByte(ARRAY_BEGIN);
        try {
            // write the array count or an indicator if the object is external (because size could change independently then)
            // important: the provided map must be identical for composing and parsing
            if (di instanceof ObjectReference && getReferencedResolver((ObjectReference)di) != null) {
                intOut(COLLECTION_COUNT_REF);
            } else {
                intOut(currentMembers);
            }
        } catch (IOException e) {
            // IOException from ByteArray operation???
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addField(ObjectReference di, BonaCustom obj) {
        final RefResolver<AbstractRef, ?, ?> r = getReferencedResolver(di);
        if (r == null || obj == null || obj == excludedObject) {
            super.addField(di, obj);
        } else {
            // this is an object to replace by its reference
            AbstractRef refobj = (AbstractRef)obj;
            // see if the reference is contained already
            long ref = refobj.ret$RefP();
            if (ref <= 0) {
                // contained references we have to resolve first
                try {
                    ref = r.getRef(refobj);
                } catch (ApplicationException e) {
                    throw new RuntimeException(e);
                }
            }
            try {
                longOut(ref);
            } catch (IOException e) {
                // IOException from ByteArray operation???
                throw new RuntimeException(e);
            }
        }
    }
}
