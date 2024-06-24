/*
11056020許宸熙
11056009宋繼平
11056019陳宜佳
 */


package imd.ntub.mybottomnav

import imd.ntub.mybottomnav.HomeFragment
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView

class ViewPagerAdapter(activity: AppCompatActivity): FragmentStateAdapter(activity){
    // 回傳總頁數
    override fun getItemCount() = 3

    /* 是每一頁要執行的事，那這裡因為是要裝入 fragment
     * 所以就在裡面回傳一個 fragment 即可
     * 透過傳入的參數(position)，回傳不同的Fragment
     */
    override fun createFragment(position: Int) = when(position){
        0 -> HomeFragment.newInstance()
        1 -> SecondFragment.newInstance()
        2 -> ThirdFragment.newInstance()
        else -> HomeFragment.newInstance()
    }
}


class MainActivity : AppCompatActivity() {
    private lateinit var viewPager2: ViewPager2
    private lateinit var btnNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager2 = findViewById(R.id.viewPager2)
        viewPager2.adapter = ViewPagerAdapter(this)

        btnNav = findViewById(R.id.bottomNavigationView)

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                btnNav.selectedItemId = when (position) {
                    0 -> R.id.btnLeft
                    1 -> R.id.btnMiddle
                    else -> R.id.btnRight
                }
            }
        })

        btnNav.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.btnLeft -> {
                    if (viewPager2.currentItem == 0) {
                        // 如果当前页是第一页，执行重新加载操作
                        val currentFragment = supportFragmentManager.findFragmentByTag("f0")
                        if (currentFragment is HomeFragment) {
                            currentFragment.reloadData() // 自定义方法，用于重新加载数据
                        }
                    } else {
                        viewPager2.currentItem = 0
                    }
                    true
                }
                R.id.btnMiddle -> {
                    viewPager2.currentItem = 1
                    true
                }
                R.id.btnRight -> {
                    viewPager2.currentItem = 2
                    true
                }
                else -> false
            }
        }
    }
    
}
