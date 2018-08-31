package com.libok.androidcode.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.libok.androidcode.R;
import com.libok.androidcode.fragment.FragmentTest1;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentActivity extends AppCompatActivity {

    @BindView(R.id.fragment_container_add)
    Button mFragmentContainerAdd;
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        ButterKnife.bind(this);
    }

    private void addFragment() {
        FragmentTest1 fragmentTest1 = FragmentTest1.newInstance("One");
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction beginTransaction = mFragmentManager.beginTransaction();
        beginTransaction.setCustomAnimations(R.anim.anim_fragment_enter, R.anim.anim_fragment_exit);
//        beginTransaction.setCustomAnimations(R.animator.animator_fragment_enter, R.animator.animator_fragment_exit);
        beginTransaction.replace(R.id.fragment_container, fragmentTest1, "ONE");
//        beginTransaction.addToBackStack(null);
        beginTransaction.commit();
    }

    @OnClick(R.id.fragment_container_add)
    public void onViewClicked() {
        addFragment();
    }
}
