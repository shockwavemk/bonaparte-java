package de.jpaw.bonaparte.refsc;

import java.util.List;

import de.jpaw.bonaparte.core.BonaPortableRef;
import de.jpaw.bonaparte.pojos.api.DataWithTracking;
import de.jpaw.bonaparte.pojos.api.SearchFilter;
import de.jpaw.bonaparte.pojos.api.SortColumn;
import de.jpaw.bonaparte.pojos.api.TrackingBase;
import de.jpaw.bonaparte.refs.BaseRefResolver;
import de.jpaw.util.ApplicationException;

/** API to noSQL backends (mini EntityManager) for types inheriting from their key superclass. */
public interface RefResolver<REF extends BonaPortableRef, KEY extends REF, DTO extends KEY, TRACKING extends TrackingBase> extends BaseRefResolver<REF, DTO, TRACKING> {
    /**
     * Returns the key for the provided unique index. Null-safe, returns 0 for a null parameter. Throws an exception if the reference does not exist.
     */
    KEY getRef(REF refObject) throws ApplicationException;

    /**
     * Returns a frozen copy of the tracking columns (to avoid tampering with them) for a given primary key.
     */
    TRACKING getTracking(KEY ref) throws ApplicationException;

    /**
     * Removes the record referenced by the key. Does nothing if key is null. Throws an exception if the key does not exist.
     */
    void remove(KEY key) throws ApplicationException;

    /** Returns a number of records for a query.
     * Throws UnsupportedOperationException in case the persistence provider does not support searches.
     */
    List<DataWithTracking<DTO,TRACKING>> query(int limit, int offset, SearchFilter filter, List<SortColumn> sortColumns) throws ApplicationException;

}
