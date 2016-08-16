package inter;

import domain.Group;

/**
 * Created by Administrator on 2016/8/15.
 */
public interface IGroupOperation {
    public void insertGroup(Group group);
    public Group getGroup(int id);
}
