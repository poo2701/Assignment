package com.p.assignmentp.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.p.assignmentp.R;
import com.p.assignmentp.databinding.FragmentHomeBinding;
import com.p.assignmentp.ui.category.CategoryFragment;
import com.p.assignmentp.ui.gallery.GalleryFragment;
import com.p.assignmentp.util.SharedPrefrence;
import com.p.assignmentp.view.LoginActivity;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    FragmentHomeBinding mBinding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        mBinding.setLifecycleOwner(this);
        mBinding.setClickHandler(new ClickHandler());
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);


        return mBinding.getRoot();
    }

    public class ClickHandler {
        public void product(View view) {
            try {
                GalleryFragment galleryFragment = GalleryFragment.newInstance(0);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, galleryFragment)
                        .addToBackStack(null)
                        .commit();

//                getActivity().getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.fragmentHost, galleryFragment).
//                        addToBackStack(null)
//                        .commit();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        public void category(View view) {
            try {
                CategoryFragment categoryFragment = CategoryFragment.newInstance();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, categoryFragment)
                        .addToBackStack(null)
                        .commit();


//                        getActivity().getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.fragmentHost,categoryFragment).
//                        addToBackStack(null)
//                        .commit();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        public void logout(View view) {
            SharedPrefrence.setLogin(getContext(), "");
            startActivity(new Intent(getActivity(), LoginActivity.class).addFlags(FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK));

        }

    }
}