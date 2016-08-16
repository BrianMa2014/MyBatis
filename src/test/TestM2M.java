package test;

import domain.Group;
import domain.User;
import domain.UserGroup;
import inter.IGroupOperation;
import inter.IUserGroupOperation;
import inter.IUserOperation;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.Reader;
import java.util.List;

/**
 * Created by Administrator on 2016/8/15.
 */
public class TestM2M {
    private static SqlSessionFactory sqlSessionFactory;
    private static Reader reader;

    static {
        try {
            reader = Resources.getResourceAsReader("mybatis-config.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static SqlSessionFactory getSession() {
        return sqlSessionFactory;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
//         testAddGroup();
//         testAddUser();
//         testAddUserGroup();
        testGetGroupAndUsers();

    }

    public static void testGetGroupAndUsers() {
        UserGroup userGroup = new UserGroup();
        SqlSession session = sqlSessionFactory.openSession();
        try {
            IGroupOperation groupMaper = session.getMapper(IGroupOperation.class);
            Group group = groupMaper.getGroup(1);
            System.out.println("Group => " + group.getGroupName());
            List<User> users = group.getUsers();
            for (User user : users) {
                System.out.println("\t:" + user.getId() + "\t"
                        + user.getUserName());
            }
        } finally {
            session.close();
        }
    }

    public static void testAddUserGroup() {
        UserGroup userGroup = new UserGroup();
        userGroup.setGroupId(3);
        userGroup.setUserId(3);
        SqlSession session = sqlSessionFactory.openSession();
        try {
            IUserGroupOperation userGroupMaper = session
                    .getMapper(IUserGroupOperation.class);
            userGroupMaper.insertUserGroup(userGroup);

            session.commit();
        } finally {
            session.close();
        }

    }

    public static void testAddUser() {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            User user = new User();
            user.setUserName("User-name-1");
            user.setUserAge(22);
            IUserOperation userMaper = session.getMapper(IUserOperation.class);
            userMaper.addUser(user);
            session.commit();
            // System.out.println(user.getGroupId());
        } finally {
            session.close();
        }
    }

    public static void testAddGroup() {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            Group group = new Group();
            group.setGroupName("用户组-1");
            IGroupOperation groupMapper = session.getMapper(IGroupOperation.class);
            groupMapper.insertGroup(group);
            session.commit();
            System.out.println(group.getGroupId());
        } finally {
            session.close();
        }
    }
}
