package test;
import domain.Post;
import domain.User;
import inter.IPostOperation;
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
public class Test {
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

    public void getUserByID(int userID) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            IUserOperation userOperation = session
                    .getMapper(IUserOperation.class);
            User user = userOperation.selectUserByID(userID);
            if (user != null) {
                System.out.println(user.getId() + ":" + user.getUserName()
                        + ":" + user.getUserAddress());
            }

        } finally {
            session.close();
        }
    }

    public void getUserList(String userName) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            IUserOperation userOperation = session
                    .getMapper(IUserOperation.class);
            List<User> users = userOperation.selectUsersByName(userName);
            for (User user : users) {
                System.out.println("Id:"+user.getId() + ",UserName:" + user.getUserName()
                        + ",UserAddress:" + user.getUserAddress());
                List<Post> posts = user.getPostList();
                for(Post post : posts){
                    System.out.println("PostId:"+post.getId()+
                            ",Post Title:"+post.getTitle()+",PostContent:"+post.getContent());
                }
            }

        } finally {
            session.close();
        }
    }

    /**
     * 增加后要commit
     */
    public void addUser() {
        User user = new User();
        user.setUserAddress("place");
        user.setUserName("test_add");
        user.setUserAge(30);
        SqlSession session = sqlSessionFactory.openSession();
        try {
            IUserOperation userOperation = session
                    .getMapper(IUserOperation.class);
            userOperation.addUser(user);
            session.commit();
            System.out.println("新增用户ID：" + user.getId());
        } finally {
            session.close();
        }
    }

    /**
     * 修改后要commit
     */
    public void updateUser() {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            IUserOperation userOperation = session
                    .getMapper(IUserOperation.class);
            User user = userOperation.selectUserByID(1);
            if (user != null) {
                user.setUserAddress("A new place");
                userOperation.updateUser(user);
                session.commit();
            }
        } finally {
            session.close();
        }
    }

    /**
     * 删除后要commit.
     *
     * @param id
     */
    public void deleteUser(int id) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            IUserOperation userOperation = session
                    .getMapper(IUserOperation.class);
            userOperation.deleteUser(id);
            session.commit();
        } finally {
            session.close();
        }
    }

    public void getPostById(int id) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            IPostOperation postOperation = session
                    .getMapper(IPostOperation.class);
            Post post = postOperation.getPostById(id);
            System.out.println("id:"+post.getId()+",title:"+post.getTitle()+",content:"+post.getContent()+",user:"+post.getUser().getUserName());
        } finally {
            session.close();
        }
    }

    public static void main(String[] args) {
        try {
            Test test = new Test();
            // test.getUserByID(1);
             test.getUserList("summer");
//             test.getPostById(1);
//             test.addUser();
            // test.updateUser();
            // test.deleteUser(6);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
