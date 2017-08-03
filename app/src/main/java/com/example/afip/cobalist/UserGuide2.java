package com.example.afip.cobalist;

/**
 * Created by afip on 7/18/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.afip.cobalist.model.Cara_aplikasi;
import com.example.afip.cobalist.model.Cara_sensor;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.afip.cobalist.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com.example.afip.cobalist.model.User_Guide.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link com.example.afip.cobalist.model.User_Guide#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserGuide2 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private com.example.afip.cobalist.model.User_Guide.OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment User_Guide.
     */
    // TODO: Rename and change types and number of parameters
    public static com.example.afip.cobalist.model.User_Guide newInstance(String param1, String param2) {
        com.example.afip.cobalist.model.User_Guide fragment = new com.example.afip.cobalist.model.User_Guide();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //return inflater.inflate(R.layout.activity_user_guide,container,false);

        // setContentView(R.layout.activity_user_guide);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.activity_user_guide, container, false);

        LinearLayout aplikasi = (LinearLayout) v.findViewById(R.id.cara_aplikasi);
        LinearLayout sensor = (LinearLayout) v.findViewById(R.id.cara_sensor);

        aplikasi.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Cara_aplikasi.class);
                startActivity(intent);
            }
        });
        sensor.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getActivity(), Cara_sensor.class);
                startActivity(intent2);

            }
        });

        return v;
        // return inflater.inflate(R.layout.fragment_user__guide, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof com.example.afip.cobalist.model.User_Guide.OnFragmentInteractionListener) {
            mListener = (com.example.afip.cobalist.model.User_Guide.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
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
}
