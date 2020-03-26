//package android.example.harmanproject;
//
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentManager;
//import androidx.fragment.app.FragmentPagerAdapter;
//
//import java.util.ArrayList;
//
//public class PageAdapter extends FragmentPagerAdapter {
//
//    ArrayList <String> list;
//
//    public PageAdapter (FragmentManager manager, ArrayList <String> list){
//        super(manager);
//        this.list = list;
//    }
//
//    @Override
//    public Fragment getItem(int position) {
//        return PageFragment.newInstance(Activity2.generateValues().get(position));
//    }
//
//    @Override
//    public int getCount() {
//        return Activity2.generateValues().size();
//    }
//}
