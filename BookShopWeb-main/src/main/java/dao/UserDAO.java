package dao;

import beans.User;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.customizer.Define;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;
import java.util.Optional;

@RegisterBeanMapper(User.class)
public interface UserDAO extends DAO<User> {
    @Override
    @SqlUpdate("INSERT INTO users (username, password, fullname, email, phoneNumber, gender, address, role) " +
            "VALUES (:username, :password, :fullname, :email, :phoneNumber, :gender, :address, :role)")
    @GetGeneratedKeys("id")
    long insert(@BindBean User user);

    @Override
    @SqlUpdate("UPDATE users SET username = :username, password = :password, fullname = :fullname, " +
            "email = :email, phoneNumber = :phoneNumber, gender = :gender, address = :address, role = :role " +
            "WHERE id = :id")
    void update(@BindBean User user);

    @Override
    @SqlUpdate("DELETE FROM users WHERE id = :id")
    void delete(@Bind("id") long id);

    @Override
    @SqlQuery("SELECT * FROM users WHERE id = :id")
    Optional<User> getById(@Bind("id") long id);

    @Override
    @SqlQuery("SELECT * FROM users")
    List<User> getAll();

    @Override
    @SqlQuery("SELECT * FROM users LIMIT :limit OFFSET :offset")
    List<User> getPart(@Bind("limit") int limit, @Bind("offset") int offset);

    @Override
    @SqlQuery("SELECT * FROM users ORDER BY <orderBy> <orderDir> OFFSET :offset ROWS FETCH NEXT :limit ROWS ONLY")
    List<User> getOrderedPart(@Bind("limit") int limit, @Bind("offset") int offset,
                              @Define("orderBy") String orderBy, @Define("orderDir") String orderDir);

    @SqlQuery("SELECT * FROM users WHERE username = :username")
    Optional<User> getByUsername(@Bind("username") String username);

    @SqlUpdate("UPDATE users SET password = :newPassword  WHERE id = :userId")
    void changePassword(@Bind("userId") long userId, @Bind("newPassword") String newPassword);

    @SqlQuery("SELECT * FROM users WHERE email = :email")
    Optional<User> getByEmail(@Bind("email") String email);

    @SqlQuery("SELECT * FROM users WHERE phoneNumber = :phoneNumber")
    Optional<User> getByPhoneNumber(@Bind("phoneNumber") String phoneNumber);

    @SqlQuery("SELECT COUNT(id) FROM users")
    int count();
}