package org.example.dao.impl;

import lombok.RequiredArgsConstructor;
import org.example.dao.IUserDao;
import org.example.entity.UserEntity;
import org.example.helper.MongodbHelper;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserDao implements IUserDao {

    private static final String COLLECTION_NAME = "user";

    private final MongodbHelper mongodbHelper;

    @Override
    public void persist(UserEntity user, String messageId) {
        try {
            Date date = new Date();
            user.setCreatedAt(date.getTime());
            user.setUpdatedAt(date.getTime());
            mongodbHelper.saveData(COLLECTION_NAME, user, messageId);
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
}
