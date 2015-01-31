package de.jpaw.bonaparte.refs;

import de.jpaw.bonaparte.pojos.refs.RefLong;
import de.jpaw.primitivecollections.HashMapPrimitiveLongObject;
import de.jpaw.primitivecollections.HashMapObjectPrimitiveLong;

public abstract class AbstractRefResolver<REF extends RefLong, DTO extends REF> implements RefResolver<REF, DTO> {
    protected HashMapPrimitiveLongObject<DTO> cache;
//    protected HashMapObjectPrimitiveLong<REF> indexCache;

    protected abstract long getUncachedKey(REF refObject);
    protected abstract DTO getUncached(long ref);
    
    @Override
    public long getRef(REF refObject) {
        if (refObject == null)
            return 0;
        long key = refObject.getRef();
        if (key > 0)
            return key;
        // shortcuts not possible, try the local reverse cache
//        key = indexCache.get(refObject);
//        if (key > 0)
//            return key;
        // not in cache either, consult second level (in-memory DB)
        key = getUncachedKey(refObject);
//        if (key > 0)
//            indexCache.put(refObject, key);
        return key;
    }

    @Override
    public DTO getDTO(REF refObject) {
        if (refObject == null)
            return null;
        return getDTO(getRef(refObject));
    }

    @Override
    public DTO getDTO(long ref) {
        if (ref <= 0L)
            return null;
        // first, try to retrieve a value from the cache, in order to be identity-safe
        DTO value = cache.get(ref);
        if (value != null)
            return value;
        // not here, consult second level (in-memory DB)
        value = getUncached(ref);
        if (value != null)
            cache.put(ref, value);
        return value;
    }

    @Override
    public void clear() {
        cache.clear();
    }
 }
