package com.arrk.shishir.arrktest.mainscreen;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arrk.shishir.arrktest.R;

/**
 * This fragment show the details about the selected character of star wars from the list
 */
public class DetailFragment extends Fragment {

    private static int CONVERTER_METERS = 1000;
    private static final String ARG_NAME = "name";
    private static final String ARG_HEIGHT = "height";
    private static final String ARG_MASS = "mass";
    private static final String ARG_DATE_CREATED = "date_credited";
    private static final String ARG_EDITED = "date_credited";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.listitem_character_detail, container, false);

        if (getArguments() != null) {
            ((TextView) view.findViewById(R.id.text_name)).setText(getArguments().getString(ARG_NAME));

            String height = getArguments().getString(ARG_HEIGHT);

            if (!TextUtils.isEmpty(height) && height.matches("[0-9]+")) {
                ((TextView) view.findViewById(R.id.text_height))
                        .setText((Double.parseDouble(height) / CONVERTER_METERS) +" "+ "Meters ");
            } else {
                ((TextView) view.findViewById(R.id.text_height))
                        .setText(getArguments().getString(ARG_HEIGHT));
            }

            ((TextView) view.findViewById(R.id.text_mass)).setText(getArguments().getString(ARG_MASS));
            ((TextView) view.findViewById(R.id.text_created)).setText(getArguments().getString(ARG_DATE_CREATED));
            ((TextView) view.findViewById(R.id.text_edited)).setText(getArguments().getString(ARG_EDITED));
        }

        return view;
    }
}
