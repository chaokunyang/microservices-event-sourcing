package com.timeyang.listener;

import com.timeyang.data.BaseEntity;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeSaveEvent;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

/**
 * @author yangck
 */
@Component
public class BeforeSaveListener extends AbstractMongoEventListener<BaseEntity> {
    @Override
    public void onBeforeSave(BeforeSaveEvent<BaseEntity> event) {
        Date date = Date.from(Instant.now());
        if(event.getSource().getCreatedAt() == null)
            event.getSource().setCreatedAt(date);
        event.getSource().setLastModified(date);
        super.onBeforeSave(event);
    }
}
