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
        Bson clientFilter = addEqualCriteria("client_id",clientId);
        addEqualCriteria("memberId",clientId);
        addEqualCriteria("startDate",clientId);
        addEqualCriteria("clientId",clientId);
        Bson createdDateFilter = addGTEAndLTECriteria("createdDate",new Date(),new Date());
        Bson lastModifiedDate =  addGTEAndLTECriteria("lastModifiedDate",new Date(),new Date());
        addEqualCriteria("member.firstName",clientId);
        addEqualCriteria("member.lastName",clientId);
        addEqualCriteria("member.dob",clientId);
        List<PrimaryModel> primaryModelList = primaryModelQuery.find().toList();
        return new SimpleMongoDbFactory(new MongoClient(mongo.getHost(), mongo.getPort()),
                mongo.getDatabase());
    }


    public List<MedicalEligibility> getQueryResults(MedicalEligibility medicalEligibility){
        MongoCollection<MedicalEligibility> medicalEligibilityCollection = new MongoClient().getDatabase("").getCollection("", MedicalEligibility.class);
        if(medicalEligibility != null){
            List<Bson> bsons= new ArrayList<>();
            if(medicalEligibility.getClientId() != null){
            bsons.add(addEqualCriteria("clientId",medicalEligibility.getClientId()));
            }
            if(medicalEligibility.getMemberId() != null){
                bsons.add(addEqualCriteria("memberId",medicalEligibility.getMemberId()));
            }
            if(medicalEligibility.getMember().getFirstName() != null){
                bsons.add(addEqualCriteria("member.firstName",medicalEligibility.getMember().getFirstName()));
            }
            if(medicalEligibility.getMember().getLastName() != null){
                bsons.add(addEqualCriteria("member.firstName",medicalEligibility.getMember().getLastName()));
            }
            if(medicalEligibility.getMember().getLastName() != null){
                bsons.add(addEqualCriteria("member.dob",medicalEligibility.getMember().getDob()));
            }
            return medicalEligibilityCollection.find(Filters.and(bsons)).into(new ArrayList<>());
        }
        return null;
    }

    public List<MedicalEligibility> getMedicalEligilibilityByCreatedDateAndClientId(String clientId,Date startDate,Date endDate){
        MongoCollection<MedicalEligibility> medicalEligibilityCollection = new MongoClient().getDatabase("").getCollection("", MedicalEligibility.class);
        List<Bson> bsons = new ArrayList<>();
        if(clientId != null){
            bsons.add(addEqualCriteria("clientId",clientId));
        }
        bsons.add(addGTEAndLTECriteria("createdDate",startDate,endDate));
        return medicalEligibilityCollection.find(Filters.and(bsons)).into(new ArrayList<>());
    }

    public List<MedicalEligibility> getMedicalEligilibilityByLastModifiedDateAndClientId(String clientId,Date startDate,Date endDate){
        MongoCollection<MedicalEligibility> medicalEligibilityCollection = new MongoClient().getDatabase("").getCollection("", MedicalEligibility.class);
        List<Bson> bsons = new ArrayList<>();
        if(clientId != null){
            bsons.add(addEqualCriteria("clientId",clientId));
        }
        bsons.add(addGTEAndLTECriteria("lastModifiedDate",startDate,endDate));
        return medicalEligibilityCollection.find(Filters.and(bsons)).into(new ArrayList<>());
    }


    private Bson addEqualCriteria(String fieldName,String fieldValue) {
        if(StringUtils.isEmpty(fieldValue)){
            return Filters.eq(fieldName,fieldValue);
        }
        return null;
    }

    private Bson addGTEAndLTECriteria(String fieldName,Date lessDate,Date greaterDate) {
        Bson greaterThanFilter = null;
        if(lessDate != null){
            greaterThanFilter = Filters.gte(fieldName,lessDate);
        }
        Bson lesserThanFilter = null;
        if(greaterDate != null){
            greaterThanFilter = Filters.gte(fieldName,lessDate);
        }
        Bson andBsonFilter = Filters.and(greaterThanFilter,lesserThanFilter);
        return andBsonFilter;
    }

    @Bean
    public MongoDbFactory secondaryFactory(final MongoProperties mongo) throws Exception {
        return new SimpleMongoDbFactory(new MongoClient(mongo.getHost(), mongo.getPort()),
                mongo.getDatabase());
    }

}
