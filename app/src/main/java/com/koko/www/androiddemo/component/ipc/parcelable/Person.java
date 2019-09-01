package com.koko.www.androiddemo.component.ipc.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Calendar;


public class Person implements Parcelable {
    private String name;
    private int age;
    private Boolean isGay;
    private Hobby hobby;//自定义值
    private ArrayList<Hobby> hobbies;//自定义值得集合
    private Calendar calendar = Calendar.getInstance();//传递的对象的某个属性所属的类是系统自带的同时没有也不可能实现Parcelable接口的类

    public Boolean getGay() {
        return isGay;
    }

    public void setGay(Boolean gay) {
        isGay = gay;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Hobby getHobby() {
        return hobby;
    }

    public void setHobby(Hobby hobby) {
        this.hobby = hobby;
    }

    public ArrayList<Hobby> getHobbies() {
        return hobbies;
    }

    public void setHobbies(ArrayList<Hobby> hobbies) {
        this.hobbies = hobbies;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public Person(String name, int age, Boolean isGay, Hobby hobby, ArrayList<Hobby> hobbies) {
        this.name = name;
        this.age = age;
        this.isGay = isGay;
        this.hobby = hobby;
        this.hobbies = hobbies;
//        this.calendar = calendar;
    }

    @Override
    public int describeContents() {
        return 0;//一般返回零就可以了
    }

    //在这个方法中序列化这个类的变量
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);//对应着 String name;
        parcel.writeInt(age);//对应着 Int age;
        //boolean类型的序列化和反序列化。由于没有直接序列化boolean类型的方式，所以采用如下方式：
        parcel.writeByte((byte) (isGay ? 1 : 0));
        //序列化该School属性
        parcel.writeParcelable(hobby, 0);
        parcel.writeTypedList(hobbies);

        //序列化Calendar对象，Calendar对象中最重要的就是Time和TimeZone两个属性，所以可以将其拆为这两个属性进行序列化
        parcel.writeLong(calendar.getTimeInMillis());
        parcel.writeString(calendar.getTimeZone().getID());

    }


    //反序列化的属性的顺序必须和之前写入的顺序一致
    protected Person(Parcel in) {
        name = in.readString();
        age = in.readInt();
        isGay = in.readByte() != 0;
        hobby = in.readParcelable(Thread.currentThread().getContextClassLoader());

        //这个写法找了好久的，Parcelable不应该多次嵌套会影响性能
        hobbies = new ArrayList<>();
        in.readTypedList(hobbies, Hobby.CREATOR);

        //进行反序列化
        calendar.setTimeInMillis(in.readLong());
        calendar.getTimeZone().setID(in.readString());
    }

    //在实现上面的接口方法后，接下来还需要执行反序列化，定义一个变量，并重新定义其中的部分方法
    public static final Creator<Person> CREATOR = new Creator<Person>() {

        //在这个方法中反序列化上面的序列化内容，最后根据反序列化得到的各个属性，得到之前试图传递的对象
        @Override
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        //一般返回一个数量为size的传递的类的数组就可以了
        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };

}
