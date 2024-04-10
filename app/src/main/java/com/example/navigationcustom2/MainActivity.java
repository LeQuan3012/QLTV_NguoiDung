package com.example.navigationcustom2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import com.example.navigationcustom2.Fregment.HomeFragment;
import com.example.navigationcustom2.Fregment.KeSachFregment;
import com.example.navigationcustom2.Fregment.MuonSachFragment;
import com.example.navigationcustom2.Fregment.QuyDinhFragment;
import com.example.navigationcustom2.Fregment.SachFregment;
import com.example.navigationcustom2.Fregment.ThongtinungdungFragment;
import com.example.navigationcustom2.Fregment.ThonngTinCaNhanFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private static final int FRAGMENT_HOME = 0;
    private static final int FRAGMENT_QUYDINH = 1;
    private static final int FRAGMENT_TTCANHAN = 2;
    private static final int FRAGMENT_MATKHAU = 3;
    private static final int FRAGMENT_TTUD = 4;
    private int mCurrent = FRAGMENT_HOME;
    DrawerLayout drawerLayout;
    ViewPager2 viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawerlayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.nav_open, R.string.nav_close );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigationview);
        navigationView.setNavigationItemSelectedListener(this);
        replaceFragment(new HomeFragment());
        setTitle("Z-Library");
        navigationView.getMenu().findItem(R.id.nav_Home).setChecked(true);

    }
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.nav_Home) {
            if (mCurrent != FRAGMENT_HOME) {
                replaceFragment(new HomeFragment());
                setTitle("Z-Library");
                mCurrent = FRAGMENT_HOME;
            }
        }else if(id == R.id.nav_quydinh){
            if(mCurrent!=FRAGMENT_QUYDINH){
                replaceFragment(new QuyDinhFragment());
                setTitle("Quy định");
                mCurrent = FRAGMENT_QUYDINH;
            }
        }else if(id == R.id.nav_thongtincanhan){
            if(mCurrent!=FRAGMENT_TTCANHAN){
                replaceFragment(new ThonngTinCaNhanFragment());
                setTitle("Thông tin cá nhân");
                mCurrent = FRAGMENT_TTCANHAN;
            }
        }else if(id == R.id.nav_change_password){
            if(mCurrent!=FRAGMENT_MATKHAU){
                replaceFragment(new QuyDinhFragment());
                setTitle("Thay đổi mật khẩu");
                mCurrent = FRAGMENT_MATKHAU;
            }
        }else if(id == R.id.nav_ttungdung){
            if(mCurrent!=FRAGMENT_TTUD){
                replaceFragment(new ThongtinungdungFragment());
                setTitle("Thay đổi mật khẩu");
                mCurrent = FRAGMENT_TTUD;
            }
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    private void replaceFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.commit();
    }

    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }

}