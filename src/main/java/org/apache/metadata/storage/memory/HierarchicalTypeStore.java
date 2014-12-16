package org.apache.metadata.storage.memory;


import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.apache.metadata.ITypedReferenceableInstance;
import org.apache.metadata.storage.Id;
import org.apache.metadata.storage.RepositoryException;
import org.apache.metadata.types.AttributeInfo;
import org.apache.metadata.types.HierarchicalType;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class HierarchicalTypeStore {

    final MemRepository repository;
    final HierarchicalType hierarchicalType;
    final ImmutableMap<AttributeInfo, IAttributeStore> attrStores;
    final ImmutableList<HierarchicalTypeStore> superTypeStores;

    /**
     * Map Id to position in storage lists.
     */
    Map<Id, Integer> idPosMap;

    List<Integer> freePositions;

    /**
     * Lock for each Class/Trait.
     */
    ReentrantReadWriteLock lock;

    HierarchicalTypeStore(MemRepository repository, HierarchicalType hierarchicalType) throws RepositoryException {
        this.hierarchicalType = hierarchicalType;
        this.repository = repository;
        ImmutableMap.Builder<AttributeInfo, IAttributeStore> b = new ImmutableBiMap.Builder<AttributeInfo,
                IAttributeStore>();
        ImmutableList<AttributeInfo>l =  hierarchicalType.immediateAttrs;
        for(AttributeInfo i : l) {
            b.put(i, AttributeStores.createStore(i) );
        }
        attrStores = b.build();

        ImmutableList.Builder<HierarchicalTypeStore> b1 = new ImmutableList.Builder<HierarchicalTypeStore>();
        Set<String> allSuperTypeNames = hierarchicalType.getAllSuperTypeNames();
        for(String s : allSuperTypeNames) {
            b1.add(repository.getStore(s));
        }
        superTypeStores = b1.build();
    }

    /**
     * Assign a storage position to an Id.
     * - try to assign from freePositions
     * - ensure storage capacity.
     * - add entry in idPosMap.
     * @param id
     * @return
     * @throws RepositoryException
     */
    int assignPosition(Id id) throws RepositoryException {
        throw new RepositoryException("Not implemented");
    }

    /**
     * - remove from idPosMap
     * - add to freePositions.
     * @throws RepositoryException
     */
    void releaseId() throws RepositoryException {
        throw new RepositoryException("Not implemented");
    }

    /**
     * - store the typeName
     * - store the immediate attributes in the respective IAttributeStore
     * - call store on each SuperType.
     * @param i
     * @throws RepositoryException
     */
    void store(ITypedReferenceableInstance i) throws RepositoryException {

    }

    /**
     * - copy over the immediate attribute values from the respective IAttributeStore
     * - call load on each SuperType.
     * @param i
     * @throws RepositoryException
     */
    void load(ITypedReferenceableInstance i) throws RepositoryException {

    }
}
