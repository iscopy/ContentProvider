package com.win.contentprovider;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Contacts;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private String[] columns = {  Contacts._ID,             //获得id值
                                    Contacts.DISPLAY_NAME,   //获得姓名
                                    Phone.NUMBER,             //获得电话
                                    Phone.CONTACT_ID};        //电话 id

    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result = (TextView) findViewById(R.id.result);
        result.setText(getQueryData());
    }

    public String getQueryData(){
        //用于保存字符串（String 和 StringBuilder 的区别不知道的网上查）
        StringBuilder stringBuilder = new StringBuilder();
        //获取 ContentResolver 对象
        ContentResolver resolver = getContentResolver();
        //通过 resolver 对象查询将结果放到光标中
        Cursor cursor = resolver.query(Contacts.CONTENT_URI, null, null, null, null);

        while(cursor.moveToNext()){

            //获得 id 值的索引
            int idIndex = cursor.getColumnIndex(columns[0]);
            //根据 id 值的索引，获得 id
            int id = cursor.getInt(idIndex);
            //获得姓名索引
            int displayNameIndex = cursor.getColumnIndex(columns[1]);
            //根据 姓名索引 ，获得姓名
            String desplay = cursor.getString(displayNameIndex);
            Cursor phone = resolver.query(Phone.CONTENT_URI, null, columns[3] + "=" + id, null, null);
            while (phone.moveToNext()){
                //获取电话索引
                int phoneNumberIndex = phone.getColumnIndex(columns[2]);
                //根据电话索引获取电话号码
                String phoneNumber = phone.getString(phoneNumberIndex);
                //将获取的数据添加到 StringBuilder
                stringBuilder.append("id:" + id + " name:" + desplay + " phone:" + phoneNumber + "\n");
            }
        }
        cursor.close();
        return stringBuilder.toString();
    }
}
