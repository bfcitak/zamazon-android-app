package com.tantuni.zamazon.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.tantuni.zamazon.R;
import com.tantuni.zamazon.controllers.ProductController;
import com.tantuni.zamazon.controllers.adapters.ProductAdapter;
import com.tantuni.zamazon.models.Product;
import com.tantuni.zamazon.networks.ProductCallback;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    ProgressBar progressBarHome;
    RecyclerView recyclerViewProducts;
    ProductAdapter productAdapter;
    ProductController productController;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        progressBarHome = (ProgressBar) rootView.findViewById(R.id.progressBarHome);
        recyclerViewProducts = (RecyclerView) rootView.findViewById(R.id.recyclerViewProducts);


        ArrayList<Product> products = new ArrayList<>();
        productController.getAllProducts(getContext(), new ProductCallback<ArrayList<Product>>() {
            @Override
            public void onSuccess(ArrayList<Product> products) {
                progressBarHome.setVisibility(View.GONE);
                setupRecycler();
            }
            @Override
            public void onError(Exception exception) {
                progressBarHome.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "There is an error listing the products!", Toast.LENGTH_LONG).show();
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

    public void setupRecycler() {
        if (getActivity() != null) {
            productAdapter = new ProductAdapter(getContext(), ProductController.products);
            recyclerViewProducts.setAdapter(productAdapter);
            recyclerViewProducts.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        }
    }
}
