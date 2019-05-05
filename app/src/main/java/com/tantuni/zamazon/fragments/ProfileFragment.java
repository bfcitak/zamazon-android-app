package com.tantuni.zamazon.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tantuni.zamazon.R;
import com.tantuni.zamazon.controllers.UserController;
import com.tantuni.zamazon.controllers.adapters.ProfileMenuAdapter;
import com.tantuni.zamazon.networks.SharedPrefManager;
import com.tantuni.zamazon.models.User;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    UserController userController;
    TextView textViewWelcomeMessage;
    RecyclerView recyclerViewProfileMenu;
    ProfileMenuAdapter profileMenuAdapter;
    ArrayList<String> profileMenuItems = new ArrayList<>(Arrays.asList("Change Password", "Orders", "Saved Addresses", "Saved Credit Cards", "Logout"));

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ProfileFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        recyclerViewProfileMenu = (RecyclerView) view.findViewById(R.id.recyclerViewProfileMenu);
        setupRecycler();
        textViewWelcomeMessage = (TextView) view.findViewById(R.id.textViewWelcomeMessage);

        //getting the current user id
        User user = SharedPrefManager.getInstance(getContext()).getUser();

        textViewWelcomeMessage.setText(getString(R.string.welcome_message, user.getFirstName(), user.getLastName(), user.getRoles().iterator().next()));

        // Inflate the layout for this fragment
        return view;
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void setupRecycler() {
        profileMenuAdapter = new ProfileMenuAdapter(this.getContext(), profileMenuItems);
        recyclerViewProfileMenu.setAdapter(profileMenuAdapter);
        recyclerViewProfileMenu.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerViewProfileMenu.addItemDecoration(new DividerItemDecoration(recyclerViewProfileMenu.getContext(), DividerItemDecoration.VERTICAL));
    }

}
