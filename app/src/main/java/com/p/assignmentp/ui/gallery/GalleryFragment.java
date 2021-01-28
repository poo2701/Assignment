package com.p.assignmentp.ui.gallery;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.p.assignmentp.R;
import com.p.assignmentp.database.DataBase;
import com.p.assignmentp.database.ProductData;
import com.p.assignmentp.databinding.FragmentGalleryBinding;
import com.p.assignmentp.recyclerview.adapter.ProductLIstAdapter;
import com.p.assignmentp.view.MainActivity;
import com.p.assignmentp.viewmodel.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class GalleryFragment extends Fragment implements SearchView.OnQueryTextListener {

    private GalleryViewModel galleryViewModel;
    RecyclerView recyclerView;
    ProductLIstAdapter productLIstAdapter;
    MainActivityViewModel mainActivityViewModel;
    FragmentGalleryBinding mBinding;
    String category;
    int id = 0;


    public static GalleryFragment newInstance(int id) {
        final GalleryFragment fragment = new GalleryFragment();
        final Bundle args = new Bundle();
        args.putInt("id", id);
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_gallery, container, false);
        galleryViewModel = new ViewModelProvider(this).get(GalleryViewModel.class);

        mainActivityViewModel = new ViewModelProvider(getActivity()).get(MainActivityViewModel.class);
        mBinding.setLifecycleOwner(this);
        mBinding.setClickHandler(new ClickHandler());
        recyclerView = mBinding.productListrecyclerView;

        SearchView searchView = mBinding.searchView;
        searchView.setOnQueryTextListener(GalleryFragment.this);
        searchView.setQueryHint("Search ");

        if (mainActivityViewModel.getProductDbLiveData().getValue().size() > 0) {

            if (getArguments() != null) {
                id = getArguments().getInt("id");
            }
            switch (id) {
                case 0:
                    productLIstAdapter = new ProductLIstAdapter(getContext(), mainActivityViewModel.getProductDbLiveData().getValue());
                    break;
                case 1:
                    category ="jewelery";
                    mainActivityViewModel.getProductCategoryWiseFromDb("jewelery");
                    break;
                case 2:
                    category = "electronics";
                    mainActivityViewModel.getProductCategoryWiseFromDb("electronics");
                    break;
                case 3:
                    category ="men clothing";
                    mainActivityViewModel.getProductCategoryWiseFromDb("men clothing");
                    break;
                case 4:
                    category ="women clothing";
                    mainActivityViewModel.getProductCategoryWiseFromDb("women clothing");
                    break;
                default:
                    productLIstAdapter = new ProductLIstAdapter(getContext(), mainActivityViewModel.getProductDbLiveData().getValue());

            }
            recyclerView.setAdapter(productLIstAdapter);
        }

        observeLiveData();

        return mBinding.getRoot();
    }

    private void observeLiveData() {
        mainActivityViewModel.getProductCAtegoryListMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<ProductData>>() {
            @Override
            public void onChanged(List<ProductData> productData) {
                if (id != 0 && productData != null && productData.size() > 0) {
                    productLIstAdapter = new ProductLIstAdapter(getContext(), productData);
                    recyclerView.setAdapter(productLIstAdapter);

                }
            }
        });
    }

    private List<ProductData> filter(List<ProductData> datas, String newText) {
        newText = newText.toLowerCase();

        final List<ProductData> filteredModelList = new ArrayList<>();
        for (ProductData data : datas) {
            final String text = data.getName().toLowerCase();
            if (text.contains(newText)) {
                filteredModelList.add(data);
            }
        }
        return filteredModelList;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (!newText.trim().isEmpty()) {
            if (id == 0) {
                List<ProductData> mlist = filter(mainActivityViewModel.getProductDbLiveData().getValue(), newText);
                productLIstAdapter.setItems(mlist);
            } else {
                List<ProductData> mlist = filter(mainActivityViewModel.getProductCAtegoryListMutableLiveData().getValue(), newText);
                productLIstAdapter.setItems(mlist);
            }
        } else {
            if (id == 0) {
                List<ProductData> mlist = filter(mainActivityViewModel.getProductDbLiveData().getValue(), newText);
                productLIstAdapter.setItems(mlist);
            } else {
                List<ProductData> mlist = filter(mainActivityViewModel.getProductCAtegoryListMutableLiveData().getValue(), newText);
                productLIstAdapter.setItems(mlist);
            }


        }

        productLIstAdapter.notifyDataSetChanged();

        return false;
    }

    public class ClickHandler {
        public void filter(View view) {
            EditText min, max;
            Button filter;
            Dialog dialog = new Dialog(getActivity());
            LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(LAYOUT_INFLATER_SERVICE);
            View v = inflater.inflate(R.layout.filter_dialog, null);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

            min = v.findViewById(R.id.minEdit);
            max = v.findViewById(R.id.maxEdit);
            filter = v.findViewById(R.id.submit);

            filter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handleDbCall(min.getText().toString(), max.getText().toString());
                    dialog.cancel();
                }
            });

            dialog.setContentView(v);
            dialog.setCancelable(true);
            dialog.show();


        }

        private void handleDbCall(String t1, String t2) {
            DataBase.databaseWriteExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    int min = 0, max = 9999;

                    if (!t1.trim().isEmpty()) {
                        min = Integer.parseInt(t1);
                    }
                    if (!t2.trim().isEmpty()) {
                        max = Integer.parseInt(t2);
                    }
                    if (id == 0) {
                        mainActivityViewModel.getProductDbLiveData().postValue(
                                DataBase.getDatabaseInstance(getContext()).getProductDao().getRangeProduct(min, max)
                        );
                        productLIstAdapter.setItems( DataBase.getDatabaseInstance(getContext()).getProductDao().getRangeProduct(min, max));

                        Log.i("TAG5", "run: "+min+max);
                        Log.i("TAG5", "run: "  +DataBase.getDatabaseInstance(getContext()).getProductDao().getRangeProduct(min, max).size());
                    } else {

                        mainActivityViewModel.getProductCAtegoryListMutableLiveData().postValue(DataBase.getDatabaseInstance(getContext()).getProductDao().getRangeProduct(min, max,category));

                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new Handler(Looper.getMainLooper())
                                    .postDelayed(new Runnable() {
                                                     @Override
                                                     public void run() {
                                                         Log.i("TAG5", "run: ");
                                                         productLIstAdapter.notifyDataSetChanged();
                                                     }
                                                 }, 500
                                    );
                        }
                    });
                }
            });
        }
    }
}