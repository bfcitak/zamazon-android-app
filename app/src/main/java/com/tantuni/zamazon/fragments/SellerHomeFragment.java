package com.tantuni.zamazon.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.tantuni.zamazon.R;
import com.tantuni.zamazon.activities.AddProductActivity;
import com.tantuni.zamazon.controllers.CategoryController;
import com.tantuni.zamazon.models.Category;
import com.tantuni.zamazon.models.SubCategory;
import com.tantuni.zamazon.networks.ProductCallback;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SellerHomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SellerHomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SellerHomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private ProgressBar progressBarSellerHome;
    private FloatingActionButton floatingActionButtonAddProduct;

    private static final int CATEGORY_SIZE = 3;
    private static final int SUBCATEGORY_SIZE = 4;

    private String[] categoryArray = new String[CATEGORY_SIZE];
    private String[] subCategoryArray = new String[SUBCATEGORY_SIZE];

    public SellerHomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SellerHomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SellerHomeFragment newInstance(String param1, String param2) {
        SellerHomeFragment fragment = new SellerHomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_seller_home, container, false);
        progressBarSellerHome = (ProgressBar) rootView.findViewById(R.id.progressBarSellerHome);
        floatingActionButtonAddProduct = (FloatingActionButton) rootView.findViewById(R.id.floatingActionButtonAddProduct);

        floatingActionButtonAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBarSellerHome.setVisibility(View.VISIBLE);
                selectCategory();
            }
        });
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void selectCategory() {
        CategoryController.getAllCategories(getContext(), new ProductCallback<ArrayList<Category>>() {
            @Override
            public void onSuccess(ArrayList<Category> categories, String message) {
                for (Category category : categories) {
                    categoryArray[categories.indexOf(category)] = category.getName();
                }
                progressBarSellerHome.setVisibility(View.GONE);
                showCategoryDialog(categories);
            }

            @Override
            public void onError(Exception exception) {
                Toast.makeText(getContext(), exception.toString(), Toast.LENGTH_SHORT).show();
                progressBarSellerHome.setVisibility(View.GONE);
            }
        });
    }

    public void selectSubCategory(String categoryId, final Category category) {
        CategoryController.getSubCategoriesOfCategory(getContext(), categoryId, new ProductCallback<ArrayList<SubCategory>>() {
            @Override
            public void onSuccess(ArrayList<SubCategory> subCategories, String message) {
                for (SubCategory subCategory : subCategories) {
                    subCategoryArray[subCategories.indexOf(subCategory)] = subCategory.getName();
                }
                showSubCategoryDialog(category, subCategories);
            }

            @Override
            public void onError(Exception exception) {
                Log.d("error", exception.toString());
            }
        });
    }

    public void showCategoryDialog(final ArrayList<Category> categories) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select Category");

        final int checkedItem = 0; //this will checked the item when user open the dialog
        builder.setSingleChoiceItems(categoryArray, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                selectSubCategory(categories.get(which).getId(), categories.get(which));
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void showSubCategoryDialog(final Category category, final ArrayList<SubCategory> subCategories) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select Sub-category");

        int checkedItem = 0; //this will checked the item when user open the dialog
        builder.setSingleChoiceItems(subCategoryArray, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent addProductIntent = new Intent(getActivity(), AddProductActivity.class);
                addProductIntent.putExtra("category", category.toString());
                addProductIntent.putExtra("subCategory", subCategories.get(which).toString());
                startActivity(addProductIntent);
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
