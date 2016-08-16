package inter;

import domain.UserGroup;

import java.util.Map;

/**
 * Created by Administrator on 2016/8/15.
 */
public interface IUserGroupOperation {
    public void insertUserGroup(UserGroup userGroup);
    public Map getUsersByGroupId(int id);
    public Map getGroupsByUserId(int id);
}
