package Backend.Dao;

import Backend.Dao.IDao;
import Backend.User.User;

import java.util.List;

public interface IUserDao extends IDao<User> {
    public List<User> getByType(String type);
    public List<User> getUsersByInfo(String providedUsername, String providedPassword);
    public void update(User user);

}
