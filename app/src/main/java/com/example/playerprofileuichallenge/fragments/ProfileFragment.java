package com.example.playerprofileuichallenge.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.playerprofileuichallenge.R;

public class ProfileFragment extends Fragment {


    private TextView txtReadMore;
    private TextView txtBio;
    private ImageView imgReadMore;
    private LinearLayout btnReadMore;
    private boolean more;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_profile, container, false);

        btnReadMore=view.findViewById(R.id.btnReadmore);
        txtReadMore=view.findViewById(R.id.txtReadmore);
        txtBio=view.findViewById(R.id.txtBio);
        imgReadMore=view.findViewById(R.id.imgReadmore);

        initBioTextView();

        btnReadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (!more){
                  txtBio.setMaxLines(Integer.MAX_VALUE);
                  txtReadMore.setText(R.string.read_less);
                  imgReadMore.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up);
               }else {
                   txtBio.setLines(4);
                   txtBio.setEllipsize(TextUtils.TruncateAt.END);
                   txtReadMore.setText(R.string.read_more);
                   imgReadMore.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down);
               }

               more=!more;
            }
        });

        return view;
    }

    private void initBioTextView() {

        if (txtBio.getLineCount()>4){
            btnReadMore.setVisibility(View.GONE);
        }else{
            btnReadMore.setVisibility(View.VISIBLE);
        }

    }
}