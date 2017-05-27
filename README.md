# ClickFilter
参考butterknife原理制作的屏蔽同一控件频繁点击事件的注解处理器

# 使用
## 1. 在app的build.gradle中添加依赖
```
compile 'com.sheaye:click-filter:1.0.0'
```

## 2.1 在Activity中使用
1. 在你的基类Activity中执行ClickFilter.bind(this);例如：
```
public class BasicActivity extends AppCompatActivity {
    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        // 也可以在Application中设置全局的intervalSeconds,屏蔽该时间内同一控件接收到的后续点击事件
        ClickFilter.setIntervalSeconds(2);
        // 在setContentView之后执行这一句
        ClickFilter.bind(this);
    }
}

```
2. 在子类Activity中使用@BindClick，例如：
```
public class MainActivity extends BasicActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @BindClick({R.id.textView1, R.id.textView2, R.id.textView3, R.id.textView3})
    public void onClick(View view) {
        Toast.makeText(this, ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
    }

}
```

## 2.2 在Fragment中使用：

1. 在基类Fragment中bind，例如：
```
public class BasicFragment extends Fragment {

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ClickFilter.bind(this, view);
    }
}
```
2. 在子类Fragment中使用@BindClick, 例如：
```
public class MainFragment extends BasicFragment {
    ...

    @BindClick(R.id.hello_blank_text)
    public void onClick(View view){
        Toast.makeText(getContext(), "hello_blank_text", Toast.LENGTH_SHORT).show();
    }
}
```

## 在Adapter中使用，这里以ListView中设置ViewHolder为例：
```
static class ViewHolder{
        Button mButton1;
        Button mButton2;

        ViewHolder(View itemView) {
            ClickFilter.bind(this,itemView);
            mButton1 = (Button) itemView.findViewById(R.id.item_button1);
            mButton2 = (Button) itemView.findViewById(R.id.item_button2);
        }

        @BindClick({R.id.item_button1, R.id.item_button2})
        public void onClick(View view){
            Toast.makeText(view.getContext(), ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
        }
    }
```
更详细的使用，请参照sample



