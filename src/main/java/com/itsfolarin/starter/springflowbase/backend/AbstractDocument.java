package com.itsfolarin.starter.springflowbase.backend;

import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterLoadEvent;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import org.springframework.lang.NonNull;

import java.math.BigInteger;
import java.util.Date;

@SuppressWarnings("unchecked")
@EqualsAndHashCode(callSuper = false)
public abstract class AbstractDocument extends AbstractMongoEventListener implements Persistable<BigInteger> {

    @Id
    private BigInteger id;
    @CreatedDate
    private Date dateCreated;

    private boolean persisted;

    protected AbstractDocument() {
    }

    protected AbstractDocument(BigInteger id) {
        this.id = id;
    }

    protected Date getDateCreated() {
        return dateCreated;
    }

    public BigInteger getId() {
        return id;
    }


    public void onPersist() {

    }

    @Override
    public void onAfterSave(@NonNull AfterSaveEvent event) {
        super.onAfterSave(event);
        this.onPersist();
        if (!persisted) persisted = true;
    }

    @Override
    public void onAfterLoad(@NonNull AfterLoadEvent event) {
        super.onAfterLoad(event);

        if (!persisted) persisted = true;
    }

    @Override
    public boolean isNew() {
        return !persisted;
    }
}
