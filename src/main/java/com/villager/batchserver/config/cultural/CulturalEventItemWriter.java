package com.villager.batchserver.config.cultural;

import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CulturalEventItemWriter<T> extends JpaItemWriter<List<T>> {

    private final JpaItemWriter<T> writer;

    public CulturalEventItemWriter(EntityManagerFactory emf) {
        writer = new JpaItemWriter<>();
        this.writer.setEntityManagerFactory(emf);
    }

    @Override
    public void write(List<? extends List<T>> items) {

        try {
            writer.write(items.stream().flatMap(Collection::stream)
                    .collect(Collectors.toList()));
        }catch (Exception e) {
            System.out.println();
        }

    }

    @Override
    public void afterPropertiesSet(){
        // An EntityManagerFactory is required 방지
    }
}
