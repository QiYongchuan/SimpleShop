package shop.main;

import java.io.Serializable;
import java.math.BigDecimal;
/**
 * Goods类
 * 这个been类需要先实现Comparable, Cloneable, Serializable这三个接口
 * 分别意思为：实现排序方法，实现clone对象方法，实现文件读写（序列化）
 * 所具有的属性：编号，名称，价格，数量
 *
 *
 */
public class Goods implements Comparable, Cloneable, Serializable {
    /**
     *
     */

    private int id;
    private String name;
    private BigDecimal price;
    private int num;

    /**
     * 重写clone方法
     */
    public Goods clone(){
        Goods g1 = new Goods();
        try {
            g1 = (Goods) super.clone();
        } catch (CloneNotSupportedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return g1;
    }
    /*
     * 初始化商品
     */
    public void initGoodsList() {
        Goods goods1 = new Goods(3, "小米手机", new BigDecimal("999"), 100);
        Goods goods2 = new Goods(2, "海尔冰箱", new BigDecimal("1999"), 100);
        Goods goods3 = new Goods(1, "海信电视", new BigDecimal("4999"), 100);
        //将商品信息添加到商品集合中
        Shop.goodsList.add(goods1);
        Shop.goodsList.add(goods2);
        Shop.goodsList.add(goods3);
        //循环遍历显示商品信息
        for (Goods goods : Shop.goodsList) {
            System.out.println(goods);
        }
        //读取Goodsfile中的商品信息到控制台
        Shop.readGoods2File();
    }

    public Goods() {
        super();
    }

    public Goods(int id, String name, BigDecimal price, int num) {
        super();
        this.id = id;
        this.name = name;
        this.price = price;
        this.num = num;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "[id = " + id + ",name = " + name + ",price = " + price + ",num = " + num + "]";
    }

    // 商品的排序
    @Override
    public int compareTo(Object o) {
        Goods goods = (Goods) o;
        // if(this.num < goods.num){
        // //小于
        // return -1;
        // } else if(this.num == goods.num){
        // //等于
        // return 0;
        // } else{
        // //大于
        // return 1;
        // }
        return this.compareTo(goods.price);
    }
}
