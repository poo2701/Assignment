package com.p.assignmentp.ui.category;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.p.assignmentp.R;
import com.p.assignmentp.databinding.FragmentCategoryBinding;
import com.p.assignmentp.ui.gallery.GalleryFragment;


public class CategoryFragment extends Fragment {


    FragmentCategoryBinding mBinding;
    CategoryViewModel mViewModel;

    public CategoryFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static CategoryFragment newInstance() {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_category, container, false);
        mBinding.setLifecycleOwner(this);
        mViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        mBinding.setModel(mViewModel);
        mBinding.setClickHandler(new ClickHandler());


        return mBinding.getRoot();
    }

    public class ClickHandler {

        public void itemClicked(int category) {


            try {
                GalleryFragment galleryFragment = GalleryFragment.newInstance(category);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, galleryFragment)
                        .addToBackStack(null)
                        .commit();
//                getActivity().getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.fragmentHost,galleryFragment).
//                        addToBackStack(null)
//                        .commit();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }


    }
}