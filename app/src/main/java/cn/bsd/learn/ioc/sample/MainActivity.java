package cn.bsd.learn.ioc.sample;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import cn.bsd.learn.ioc.library.ContentView;
import cn.bsd.learn.ioc.library.InjectView;
import cn.bsd.learn.ioc.library.OnClick;

//setContentView(R.layout.activity_main);
@ContentView(R.layout.activity_main)//5行代码
public class MainActivity extends BaseActivity {

    @InjectView(R.id.tv)
    private TextView tv;

    @InjectView(R.id.btn)//findViewById(xxxxid)
    private Button btn;

    private String name;



    @Override
    protected void onResume() {

        super.onResume();
        Toast.makeText(this,btn.getText().toString(),Toast.LENGTH_SHORT).show();
    }

//    @OnClick({R.id.tv,R.id.btn})
//    public void show(View view){
//        Toast.makeText(this,"show(view)",Toast.LENGTH_SHORT).show();
//    }

    @OnClick({R.id.tv,R.id.btn})
    public void show(){
        Toast.makeText(this,"show()",Toast.LENGTH_SHORT).show();
    }

    public void abc(){
        //安卓监听事件，有规律
        //setXXXListener
        //new View.OnxxxListener
        //回调执行方法，onxxx()
        //打包成一个对象，用代理去完成这件事
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //10w行代码
                return false;
            }
        });
    }
}
