package org.example.dao.impl;

import com.mongodb.client.model.Filters;
import lombok.RequiredArgsConstructor;
import org.bson.conversions.Bson;
import org.example.dao.IUserDao;
import org.example.entity.UserEntity;
import org.example.helper.MongodbHelper;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserDao implements IUserDao {

    private static final String COLLECTION_NAME = "user";

    private final MongodbHelper mongodbHelper;

    @Override
    public void persist(UserEntity user) {
        try {
            Date date = new Date();
            user.setCreatedAt(date.getTime());
            user.setUpdatedAt(date.getTime());
            mongodbHelper.saveData(COLLECTION_NAME, user, user.getUserId());
        } catch(Exception ex) {
            //TODO:Make Exception handler
            throw ex;
        }
    }

    @Override
    public List<UserEntity> fetch(Integer pageNumber, Integer pageSize) {
        return mongodbHelper.fetchAllWithoutFilterPaginated(COLLECTION_NAME, pageNumber, pageSize,
                UserEntity.class);
    }

    @Override
    public Optional<UserEntity> fetchByUserName(String userName) {
        try{
            Bson filter = Filters.and(
                    Filters.eq("userName", userName )
            );
            return mongodbHelper.findOptionalByFilter(COLLECTION_NAME, filter, UserEntity.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    @Override
    public void updateById(String id, UserEntity user) {
        try {
            Date date = new Date();
            user.setUpdatedAt(date.getTime());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
