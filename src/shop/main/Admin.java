package shop.main;
import java.math.BigDecimal;
/**
 * Admin类
 * 所具有的属性：用户名和密码
 * 所具有的行为：添加，修改，删除
 *
 *
 */
public class Admin extends User {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /*
     * 管理员登录
     */
    public void adminLogin() {

        System.out.println("欢迎管理员登录");
        System.out.println("请输入管理员账号");
        String admin = Shop.sc.next();

        boolean ok=false;
        if(admin.equals("admin")|| admin.equals("qyc123") || admin.equals("lsn123") || admin.equals("dy123")) {
            ok=true;
        }
        else
            System.out.println("账号输入有误，请重新输入");
        while (true) {
            if(ok==true) {

                System.out.println("请输入管理员密码");
                String password = Shop.sc.next();
                if (admin.equals("admin") && password.equals("admin") || admin.equals("qyc123")|| (admin.equals("lsn123") || admin.equals("dy123") )&& password.equals("admin")) {
                    System.out.println("管理员登录成功");
                    while (true) {
                        int choice = this.showAdminMenu();

                        if (choice == 5) {
                            break;
                        }

                        this.choiceAdminMenu(choice);
                    }
                    break;
                } else {
                    System.out.println("登录失败");
                }

            }
        }
    }
    /*
     * 展示管理员菜单
     */
    public int showAdminMenu() {
        System.out.println("*****************欢迎进入管理员菜单**************");
        System.out.println("\t1.添加商品");
        System.out.println("\t2.修改商品");
        System.out.println("\t3.删除商品");
        System.out.println("\t4.查看商品列表");
        System.out.println("\t5.退出");
        System.out.println("******************************************");
        System.out.print("请选择菜单：");
        int choice = Shop.sc.nextInt();
        return choice;
    }
    /*
     * 判断是否继续的方法
     */
    public String isGo_on() {
        System.out.println("您是否继续：Y/N");
        String Go_on = Shop.sc.next();
        return Go_on.toUpperCase();//把y、n变成大写字母Y、N
    }
    /*
     * 菜单选择方法
     */
    public boolean choiceAdminMenu(int choice) {
        boolean result = true;
        String Go_on = "Y";
        switch (choice) {
            case 1:
                System.out.println("你选择的是添加商品");
                //当go_on为Y时，则继续执行添加商品操作
                while (Go_on.equals("Y")) {
                    this.addGoods();
                    Go_on = this.isGo_on();
                }
                break;
            case 2:
                System.out.println("你选择的是修改商品");
               String isContinue = "y";
                while (isContinue.equals("y") || isContinue.equals("Y")){
                    this.updateGoods();
                    System.out.println("是否继续修改？Y/N");
                    isContinue = Shop.sc.next();
                }

                break;
            case 3:
                System.out.println("你选择的是删除商品");
                String isContinue2 = "y";
                while (isContinue2.equals("y") || isContinue2.equals("Y")){
                    this.deleteGoods();
                    System.out.println("是否继续修改？Y/N");
                    isContinue2 = Shop.sc.next();
                }

                break;
            case 4:
                System.out.println("你选择的是查看商品列表");
                super.showGoodsList();
                break;
            case 5:
                System.out.println("退出");
                result = false;
                break;
            default:
                System.out.println("输入有误");
                break;
        }
        return false;
    }
    /*
     * 添加商品方法
     */
    public void addGoods() {
        // 原本的功能，没有设置id互斥，也就是相同id依然可以添加进去

        System.out.println("****开始添加商品****");
        System.out.println("请输入商品编号：");
        int id =-1;
        while (true){
            id = Shop.sc.nextInt();
            boolean flag = true;
            for (
                    Goods good:Shop.goodsList
            ){
                if(good.getId() ==id){
                    flag =false;
                    break;
                }
            }
            if (flag ==false){
                System.out.println("已有相同id的商品，请重新输入不同id");
                continue;
            }
            break;
        }
//        int id = Shop.sc.nextInt();
        System.out.println("请输入商品名称：");
        String name = Shop.sc.next();
        System.out.println("请输入商品价格：");
        String price = Shop.sc.next();
        System.out.println("请输入商品数量：");
        int num = Shop.sc.nextInt();

        Goods goods = new Goods();
        goods.setId(id);
        goods.setName(name);
        goods.setPrice(new BigDecimal(price));
        goods.setNum(num);
        Shop.goodsList.add(goods);
        System.out.println("商品添加成功");
        Shop.saveGoods2File();
    }

    /*
     * 修改商品方法
     */
    public void updateGoods() {
        System.out.println("****开始修改商品信息****");
        System.out.println("请输入要修改的商品编号：");
        int id = Shop.sc.nextInt();
        Goods goods = super.findGoodsById(id);

        if (goods == null) {
            System.out.println("你输入的商品不存在，请重新输入吧");
        } else {

            System.out.println("商品信息如下：");
            System.out.println("商品编号\t商品名称\t商品价格\t商品数量\t");
            System.out.println(goods.getId() + "\t" + goods.getName() + "\t" + goods.getPrice() + "\t" + goods.getNum() + "\t");
        }
        System.out.println("请输入修改后的商品名称：");
        String name = Shop.sc.next();
        goods.setName(name);
        System.out.println("请输入修改后的商品价格：");
        double price = Shop.sc.nextDouble();
        goods.setPrice(new BigDecimal(price));
        System.out.println("请输入修改后的商品数量：");
        int num = Shop.sc.nextInt();
        goods.setNum(num);

        System.out.println("******商品修改成功！******");
        Shop.saveGoods2File();
    }

    /*
     * 删除商品方法
     */
    public void deleteGoods() {
        System.out.println("****开始删除商品信息****");
        System.out.println("请输入要删除的商品编号：");
        int id = Shop.sc.nextInt();
        Goods goods = super.findGoodsById(id);
        if (goods == null){
            System.out.println("不可以虚空删除");
        } else {
            Shop.goodsList.remove(goods);
            System.out.println("商品删除成功");
            Shop.saveGoods2File();
        }

    }


}
