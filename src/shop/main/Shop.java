package shop.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
/**
 * 商城类
 * 所具有的属性：当前登录用户，管理员，商品集合，用户集合，扫描器
 * 所具有的行为：显示菜单的方法，获取用户输入的菜单，判断用户输入的菜单
 *
 *
 */

public class Shop {
    //定义一个全局变量的扫描i去,用于用户输出
    static Scanner sc = new Scanner(System.in);
    /**
     * 定义三个集合存放信息
     *
     */
    static List<Goods> goodsList = new ArrayList<Goods>();
    static List<Goods> myGoodsList = new ArrayList<Goods>();
    static List<User> userList = new ArrayList<User>();
    /**
     * 定义两个txt文本用来存放注册时的账号密码   & 管理员添加的商品和修改后的商品
     *
     *
     */
    static File userFile = new File("D:\\SimpleShopDate\\Userfile.txt");
    static File goodsFile = new File("D:\\SimpleShopDate\\Goodsfile.txt");
    /**
     * 三个全局变量
     *
     */
    User user = new User();
    Admin admin = new Admin();
    Goods goods = new Goods();
    /**
     * 把修改和添加的商品信息储存到文本中
     *
     */
    public static void saveGoods2File(){
        try {
            FileOutputStream fos = new FileOutputStream(goodsFile);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(goodsList);
            oos.close();
            fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    /**
     * 获取文本中商品的信息
     *
     */
    public static void readGoods2File(){
        try {
            FileInputStream fis = new FileInputStream(goodsFile);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Object obj = ois.readObject();
            goodsList = (List) obj;
            ois.close();
            fis.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    /**
     * 把用户注册时的信息存入到文本中
     *
     */
    public static void saveListToFile(){
        try {
            FileOutputStream fos = new FileOutputStream(userFile);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(userList);
            oos.close();
            fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    /**
     * 获取文本中的用户信息
     *
     */
    public void readListFromFile(){
        try {
            FileInputStream fis = new FileInputStream(userFile);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Object obj = ois.readObject();
            userList = (List)obj;
            ois.close();
            fis.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        Shop shop = new Shop();
        shop.run();
    }
    /**
     * 循环显示主菜单，结束菜单
     *
     */
    private void run() {
        this.readListFromFile();
        this.readGoods2File();
//        goods.initGoodsList();
        //设定一个是否退出的条件
        boolean isBreak = true;
        while (isBreak) {
            int choice = this.showMenu();
            //当choice选择为6事，返回的值为false，同时将值赋给了isBreak，并退出菜单
            isBreak = this.chooseMenu(choice);
        }
    }

    /*
     * 菜单展示
     */
    private int showMenu() {
        System.out.println("*****************欢迎进入电子商城**************");
        System.out.println("\t1.注册");
        System.out.println("\t2.登录");
        System.out.println("\t3.查看商城");
        System.out.println("\t4.查看我购买的商品");
        System.out.println("\t5.管理员登录");
        System.out.println("\t6.退出系统");
        System.out.println("******************************************");
        System.out.print("请选择菜单：");
        int choice = sc.nextInt();  //定义了一个整型变量 choice，使用 Scanner 类的 nextInt() 方法从标准输入中读取一个整数，并将其赋值给 choice 变量。该代码可能用于读取用户在控制台中输入的菜单选项，以便程序根据用户的选择进行相应的操作。需要注意的是，如果用户输入的不是整数，该代码可能会抛出 InputMismatchException 异常，需要进行相应的异常处理。
        return choice;
    }

    /*
     * 菜单选择
     */
    private boolean chooseMenu(int choice) {
        boolean result = true;
        switch (choice) {
            case 1:
                System.out.println("你选择的菜单是：注册");
                user.registUser();
                break;
            case 2:
                System.out.println("你选择的菜单是：登录");
                user.login();
                break;
            case 3:
                System.out.println("你选择的菜单是：查看商城");
                user.showGoodsList();
                //判断用户是否登录
                if(user.isLogin() == true){//成功登录，则可以进行购买操作
                    user.buy();
                }else{
                    System.out.println("你还未登录，请先登录，在购买商品");
                }
                break;
            case 4:
                System.out.println("你选择的菜单是：查看我购买的商品");
                user.showMyGoodsList();
                break;
            case 5:
                System.out.println("管理员登录");
                admin.adminLogin();
                break;
            case 6:
                System.out.println("谢谢使用，下次再见！");
                result = false;
                System.exit(0);
            default:
                System.out.println("输入有误，请重新输入———————");
                break;
        }
        return result;
    }


}
