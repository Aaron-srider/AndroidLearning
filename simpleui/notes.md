# view
窗户：安卓界面
view：玻璃
view是所有ui界面的基类，比如TextView

# 使用xml控制ui布局

* 编写xml文件
    * 选定布局类 （比如FrameLayout帧布局）
    * 编写xml配置文件，配置布局和布局中的组件属性，比如布局的background属性，文本框的text属性
* 选定xml配置文件：setContentView(R.layout.activity_main);

# 使用java代码控制ui布局

与xml区别： 使用java代码代替xml创建布局控件类（FrameLayout）以及组件（TextView）

```java
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
```
就是将`setContentView(R.layout.activity_main);`换成相应的类创建代码即可。通过api设置类的属性。

# 对比
xml不够灵活，但是比较简单，而java正好相反。
xml与java结合使用：
xml中防止一些不太改变的布局代码（可以放管理器），java中放一些复杂逻辑（保存资源）。



# 布局管理器
布局：让组件井然有序
布局管理器：控制组件是如何摆放的。
## 相对布局管理器：
需要一个组件作为参考点（兄弟或父容器）
android:gravity：设置布局管理器中各子组件的摆放方式，android:ignoreGravity：设置哪个组件可以忽略摆放方式，或文字在空间中的位置，
可以理解为空间的重力场。


## LinearLayout
线性布局管理器
组件属性：
* android:layout_weight:组件权重，将垂直或水平剩余间距按各组件权重等比分配给各组件，使屏幕占满，权重比例是剩余空间被分割的比例
  android:layout_gravity：设置LinearLayout布局中空间的位置，如果是水平布局，那么只有上中下有效，如果垂直布局，只有左中右有效。 

## FrameLayout
帧布局管理器
显示重叠效果：先放进去的在最底层，可以实现时钟分针，秒针等，以及拖动效果。
前景图像：始终处于最上层的图像，其他组件无法覆盖


## TableLayout
表格布局管理器
布局属性：
android:collapseColumns="1,2" 隐藏固定的多个列（下标从0开始）
android:shrinkColumns="2" 如果同一行空间遭到挤压，那么指定的列元素将被压缩
android:stretchColumns="1" 如果同一行空间不够，那么指定的列元素将被拉伸

