package shop.main;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Scanner;

public class User implements Serializable{
    private static final long serialVersionUID = 1L;
    String username;
    String userpwd;
    Goods goods = new Goods();
    /**
     * 验证是否登录
     *
     */
    private boolean isLogin;
    public boolean isLogin() {
        return isLogin;
    }
    public void setLogin(boolean isLogin) {
        this.isLogin = isLogin;
    }

    /*
     * 校验用户名合法性
     */
    private boolean checkUsername(String username) {
        //定义一个为false的bool值
        boolean res = false;
//		//用户名长度不满3位，则重新输入
        if (username.length() < 3) {
            System.out.println("用户名长度不能小于3位");
            return res;
        } else if (Character.isDigit(username.charAt(0))) {//用户名不能以数字开头，则重新输入
            System.out.println("用户名不能以数字开头");
            return res;
        } else {
            //成功，返回true，继续下一步操作
            return res = true;
        }

    }

    /**
     * 展示已购买的商品信息
     *
     */
    public void showMyGoodsList() {
        System.out.println("******您购买的商品列表如下******");
        BigDecimal total = new BigDecimal("0");
        for (Goods myGoods : Shop.myGoodsList) {
            System.out.println(myGoods);
            //将在buy()方法中储存的信息提取出来，获取商品的价格
            BigDecimal price = myGoods.getPrice();
            //获取商品的数量
            int num = myGoods.getNum();
            //计算
            total = total.add(price.multiply(new BigDecimal(num)));
            //total = total.add(myGoods.getPrice().multiply(new BigDecimal(myGoods.getNum())));
        }
        System.out.println("总价格为：" + total);
    }
    /*
     * 1.注册方法
     */
    public void registUser() {
        //校验用户名合法性的标识
        boolean isCheck = true;

        while (true) {
            System.out.println("欢迎注册");
            System.out.println("请输入用户名");
            Shop.sc = new Scanner(System.in);
            String rusername = Shop.sc.next();
            //校验结果的返回值
            isCheck = this.checkUsername(rusername);

            System.out.println(isCheck);

            if (isCheck == true) {
                System.out.println("请输入密码");
                String ruserpwd = Shop.sc.next();
                if(ruserpwd.length()>=6) {
                    boolean isDigit=false;
                    boolean isLetter=false;
                    for(int i=0;i<ruserpwd.length();i++) {
                        if(Character.isDigit(ruserpwd.charAt(i))) {
                            isDigit=true;
                        }else if(Character.isLetter(ruserpwd.charAt(i))) {
                            isLetter=true;
                        }
                    }
                    if(isDigit&&isLetter) {
                        System.out.println("请再次输入密码");
                        String reuserpwd = Shop.sc.next();
                        if (ruserpwd.equals(reuserpwd)) {
                            User user = new User();
                            user.setUsername(rusername);
                            user.setUserpwd(ruserpwd);
                            Shop.userList.add(user);
                            System.out.println("注册成功");
                            //用户注册成功则将用户的注册信息保存到Userfile文件中
                            Shop.saveListToFile();
                            break;
                        }
                        else
                            System.out.println("两次密码不一致，请重新输入———————");

                    }
                    else
                        System.out.println("密码必须是字母和数字的组合");
                }
                else
                    System.out.println("密码长度不能小于6位");
            }
        }
    }
    /*
     * 2.登录方法
     */
    public void login() {
        boolean loginResult = false;
        int maxTime = 0;
        while (true) {
            if (maxTime != 3) {
                maxTime++;
                System.out.println("欢迎登录");
                System.out.println("请输入用户名");
                Shop.sc = new Scanner(System.in);
                String username = Shop.sc.next();
                System.out.println("请输入密码");
                String userpwd = Shop.sc.next();
                for (User user : Shop.userList) {
                    if (username.equals(user.username) && userpwd.equals(user.userpwd)) {
                        System.out.println("登录成功");
                        this.setLogin(true);//用来验证是否登录
                        loginResult = true;
                        break;
                    }
                }
                if (loginResult == true) {
                    break;
                } else {
                    if (maxTime != 3) {
                        System.out.println("密码有误,请重新登录");
                    }
                }

            } else {
                System.out.println(maxTime + "次登录失败，系统退出");
                System.exit(0);
            }

        }
    }
    /*
     * 管理员查看商品列表
     */
    public void showGoodsList() {
        System.out.println("******商品列表如下******");
		Shop.readGoods2File();
		for (Goods goods : Shop.goodsList) {
			System.out.println(goods);
		}

    }
    /*
     * 3.购买商品方法
     */
    public void buy() {
        while (true) {
            System.out.println("请选择您需要购买商品的编号：");
            int id = Shop.sc.nextInt();
            if(id>3) {
                System.out.println("未找到改商品");
            }
            else {
                System.out.println("您将要的购买的商品信息如下：");
                Goods shopGoods = this.findGoodsById(id);
                System.out.println(shopGoods);
                System.out.println("请输入您需要购买商品的数量：");
                int num = Shop.sc.nextInt();

                Goods myGoods = new Goods();
//				myGoods.setId(shopGoods.getId());
//				myGoods.setName(shopGoods.getName());
//				myGoods.setPrice(shopGoods.getPrice());
                //通过clone()方法，就省去了以上备注释的内容
                myGoods = shopGoods.clone();
                myGoods.setNum(num);
                //将已选择的商品添加到我的商品集合中
                Shop.myGoodsList.add(myGoods);
                int newnum=shopGoods.getNum()-num;
                shopGoods.setNum(newnum);
                System.out.println("是否继续Y/N");
                String choice = Shop.sc.next();
                choice = choice.toUpperCase();

                if (choice.equals("N")) {
                    break;
                }
            }
        }
        this.showMyGoodsList();
    }

    /**
     * 查找商品用来删除，修改，购买
     *
     */
    public Goods findGoodsById(int id) {

        Goods returnGoods = null;
        for (Goods goods : Shop.goodsList) {
            if (goods.getId() == id) {
                returnGoods = goods;
                break;
            }
        }
        return returnGoods;
    }

    public User() {
        super();
    }

    public User(String username, String userpwd) {
        super();
        this.username = username;
        this.userpwd = userpwd;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserpwd() {
        return userpwd;
    }

    public void setUserpwd(String userpwd) {
        this.userpwd = userpwd;
    }

}
