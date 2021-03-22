package org.techtown.study08

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import org.techtown.pager.Fragment1
import org.techtown.pager.Fragment2
import org.techtown.pager.Fragment3


class MainActivity : AppCompatActivity() {

    //enumerated type, 열거형의 줄인
    //클래스 안의 객체들은 대문자로 기술
    //함수도 선언이 가능하다.
    //enum 클래스 안에 fragmentItem들의 객체를 생성하기 위한 선언
    //객체들은 고유한 속성을 가질 수도 있다
    enum class FragmentItem {
        ITEM1, ITEM2, ITEM3
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //툴바 이용할 수 있는 위젯 툴바를 사용할 때는 항상 사용한다고 생각하면 된다.
        setSupportActionBar(toolbar)

        //툴바를 정의
        //ActionBarDrawerToggle(Activity activity, DrawerLayout drawerLayout, Toolbar toolbar,
        // int openDrawerContentDescRes, int closeDrawerContentDescRes)
        //String.xml에 open, close를 만들어놓았다.
        //토글 즉, 바로가기 메뉴를 만들 때 무조건 이대로 치면 된다고 기억 3행 세트로
        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.drawer_open, R.string.drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        //바로가기 메뉴 화면에서 메뉴 선택했을 때서(왼쪽 네비게이션)
        //토글을 열고 아이템을 선택 했을 때
        navigationView.setNavigationItemSelectedListener {
            //그 아이템의 아이디가 OO일 때
            when(it.itemId) {
                //1번 아이템을 선택하면
                R.id.item1 -> {
                    //토스트 메세지를 보인다.
                    showToast("1番選択された")
                    //onFragmentSelected메소드를 실행
                    // FragmentItem.ITEM1가 실행된다.
                    onFragmentSelected(FragmentItem.ITEM1, null)
                }
                //2번 아이템을 선택하면
                R.id.item2 -> {
                    //토스트 메세지를 보인다.
                    showToast("2番選択された")
                    //onFragmentSelected메소드를 실행
                    //FragmentItem.ITEM2가 실행된다.
                    onFragmentSelected(FragmentItem.ITEM2, null)
                }
                //3번 아이템을 선택하면
                R.id.item3 -> {
                    //토스트 메세지를 보인다.
                    showToast("3番選択された")
                    //onFragmentSelected메소드를 실행
                    //FragmentItem.ITEM3가 실행된다.
                    onFragmentSelected(FragmentItem.ITEM3, null)
                }
            }
            //바로 가기 메뉴 닫기
            drawerLayout.closeDrawer(GravityCompat.START)
            //네비게이션 아이템 중에 선택된 값을 반환한다. true?는 왜 사용하지...
            return@setNavigationItemSelectedListener true
        }
        //bottom에 있는 아이템을들 선택 했을 때
        bottom_navigation.setOnNavigationItemSelectedListener {
            //선택한 아이템의 아이디가
            when(it.itemId) {
                //tab1아이템을 선택하면
                R.id.tab1 -> {
                    //토스트 메세지가 나온다.
                    showToast("1番目Tab選択")
                    //onFragmentSelected메소드에서 FragmentItem.ITEM1을 실행
                    onFragmentSelected(FragmentItem.ITEM1, null)

                    return@setOnNavigationItemSelectedListener true
                }
                R.id.tab2 -> {
                    showToast("2番目Tab選択")
                    onFragmentSelected(FragmentItem.ITEM2, null)

                    return@setOnNavigationItemSelectedListener true
                }
                R.id.tab3 -> {
                    showToast("3番目Tab選択")
                    onFragmentSelected(FragmentItem.ITEM3, null)

                    return@setOnNavigationItemSelectedListener true
                }
            }

            return@setOnNavigationItemSelectedListener false
        }

        //첫 번째 탭을 선택된 상태로 만듬 기본 tab1
        bottom_navigation.selectedItemId = R.id.tab1

        //첫 번째 프레그먼트를 선택된 프래그먼트로 보여 줌
        //프래그먼트 추가 또는 삭제와 같은 트랜잭션을 실행하려면 FragmentManager를 사용한 다음
        //beginTransaction를 호출하여 프래그먼트 트랜잭션을 생성하고 add()를 호출하여 프래그먼트를 추가.
        //변경할 준비가 되면 commit()을 호출해야 한다.
        //container는 프래그먼트 레이아웃의 아이디
        supportFragmentManager.beginTransaction().add(R.id.container, Fragment1()).commit()

    }
    //프래그먼트 매니저를 이용해 프래그먼트를 프레임 레이아웃 안에 넣어준다.
    fun onFragmentSelected(item:FragmentItem, bundle: Bundle?) {
        var fragment: Fragment
        when(item) {
            FragmentItem.ITEM1 -> {
                toolbar.title = "1番目画面"
                fragment = Fragment1()
            }
            FragmentItem.ITEM2 -> {
                toolbar.title = "2番目画面"
                fragment = Fragment2()
            }
            FragmentItem.ITEM3 -> {
                toolbar.title = "3番目画面"
                fragment = Fragment3()
            }
        }

        supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
    }

    //BACK키를 눌렀을 때 바로가기 메뉴가 열려있는 상태였다면 닫아주는 기능을 포함한다.
    override fun onBackPressed() {
        //만약 바로가기 메뉴가 열려 있으면
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            //바로 가기 메뉴를 닫는다.
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            //닫혀있으면?
            super.onBackPressed()
        }
    }
    //토스트 메세지를 설정
    fun showToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
    }
}

