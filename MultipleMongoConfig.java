package com.marcosbarbero.wd.multiplemongo.config;

import com.marcosbarbero.wd.multiplemongo.repository.primary.PrimaryModel;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import dev.morphia.query.Criteria;
import dev.morphia.query.CriteriaContainerImpl;
import dev.morphia.query.Query;
import org.bson.BSON;
import org.bson.BsonDocument;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import static com.mongodb.client.model.Filters.eq;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Marcos Barbero
 */
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(MultipleMongoProperties.class)
public class MultipleMongoConfig {

    private final MultipleMongoProperties mongoProperties;

    @Primary
    @Bean(name = PrimaryMongoConfig.MONGO_TEMPLATE)
    public MongoTemplate primaryMongoTemplate() throws Exception {
        return new MongoTemplate(primaryFactory(this.mongoProperties.getPrimary()));
    }

    @Bean(name = SecondaryMongoConfig.MONGO_TEMPLATE)
    public MongoTemplate secondaryMongoTemplate() throws Exception {
        return new MongoTemplate(secondaryFactory(this.mongoProperties.getSecondary()));
    }

    @Bean
    @Primary
    public MongoDbFactory primaryFactory(final MongoProperties mongo) throws Exception {
        MongoCollection<PrimaryModel> mongoCollections = new MongoClient().getDatabase("").getCollection("", PrimaryModel.class);
        Morphia morphia = new Morphia();
        morphia.mapPackage("com.baeldung.morphia");
        Datastore datastore = morphia.createDatastore(new MongoClient(), "library");
        datastore.ensureIndexes();
        String clientId = "";
        String memberId= "";
        String startDate= "";
        List<PrimaryModel> primaryModels = new ArrayList<>();
        datastore.createQuery(PrimaryModel.class).field("clientId").equal("").field("memberId").equal("").field("start_date").equal("").field("created_date").lessThanOrEq("").field("last_modified_date").equal("");
        Query<PrimaryModel> primaryModelQuery = datastore.createQuery(PrimaryModel.class);
        addEqualCriteria("client_id",clientId, primaryModelQuery);
        addEqualCriteria("member_id",clientId, primaryModelQuery);
        addEqualCriteria("start_date",clientId, primaryModelQuery);
        addEqualCriteria("client_id",clientId, primaryModelQuery);
        addGTEAndLTECriteria("created_date",new Date(),new Date(),primaryModelQuery);
        addGTEAndLTECriteria("last_modified_date",new Date(),new Date(),primaryModelQuery);
        addEqualCriteria("member.firstName",clientId, primaryModelQuery);
        addEqualCriteria("member.lastName",clientId, primaryModelQuery);
        addEqualCriteria("member.dob",clientId, primaryModelQuery);
        List<PrimaryModel> primaryModelList = primaryModelQuery.find().toList();
        return new SimpleMongoDbFactory(new MongoClient(mongo.getHost(), mongo.getPort()),
                mongo.getDatabase());
    }

    private void addEqualCriteria(String fieldName,String fieldValue, Query<PrimaryModel> primaryModelQuery) {
        if(StringUtils.isEmpty(fieldValue)){
            primaryModelQuery.field(fieldName).equal(fieldValue);
        }
    }

    private void addGTEAndLTECriteria(String fieldName,Date lessDate,Date greaterDate, Query<PrimaryModel> primaryModelQuery) {
        if(lessDate != null){
            primaryModelQuery.field(fieldName).greaterThanOrEq(lessDate);
        }
        if(greaterDate != null){
            primaryModelQuery.field(fieldName).lessThanOrEq(greaterDate);
        }
    }

    @Bean
    public MongoDbFactory secondaryFactory(final MongoProperties mongo) throws Exception {
        return new SimpleMongoDbFactory(new MongoClient(mongo.getHost(), mongo.getPort()),
                mongo.getDatabase());
    }

}
