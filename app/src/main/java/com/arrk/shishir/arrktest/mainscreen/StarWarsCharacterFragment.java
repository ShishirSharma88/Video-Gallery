package com.arrk.shishir.arrktest.mainscreen;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.arrk.shishir.arrktest.R;
import com.arrk.shishir.arrktest.taskdownloder.model.StarWars;

import org.jetbrains.annotations.NotNull;

public class StarWarsCharacterFragment
        extends Fragment implements StarWarsCharacterDownloadContract.View, View.OnClickListener {

    private static final String ARG_NAME = "name";
    private static final String ARG_HEIGHT = "height";
    private static final String ARG_MASS = "mass";
    private static final String ARG_DATE_CREATED = "date_credited";
    private static final String ARG_EDITED = "date_credited";


    private StarWarsCharacterDownloadContract.Presenter mPresenter;
    private RecyclerView charactersList;
    private EndlessScrollListener scrollListener;
    private LinearLayoutManager linearLayoutManager;
    private DetailFragment detailFragment;
    private Button tryAgain;

    public static StarWarsCharacterFragment newInstance() {
        return new StarWarsCharacterFragment();
    }

    @Override
    public void onClick(View v) {
        mPresenter.getData();
    }

    ProgressDialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        dialog = new ProgressDialog(getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.frag_download, container, false);
        charactersList = (RecyclerView) root.findViewById(R.id.img_recycler_view);
        tryAgain = (Button) root.findViewById(R.id.try_again);
        tryAgain.setOnClickListener(this);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        charactersList.setLayoutManager(linearLayoutManager);

        scrollListener = new EndlessScrollListener(mPresenter, linearLayoutManager);
        // Adds the scroll listener to RecyclerView
        charactersList.addOnScrollListener(scrollListener);
        return root;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void setPresenter(@NotNull StarWarsCharacterDownloadContract.Presenter presenter) {
        mPresenter = presenter;
    }

    private CharactersListAdapter mAdapter;

    public void updateUI() {
        startSpin();
        if (mAdapter == null) {
            mAdapter = new CharactersListAdapter(mPresenter.getCharacterData());
            charactersList.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
        stopSpin();
    }

    public void updateUIforTryAgainPositive() {
        charactersList.setVisibility(View.GONE);
        tryAgain.setVisibility(View.VISIBLE);
    }

    public void updateUIforTryAgainNegative() {
        charactersList.setVisibility(View.VISIBLE);
        tryAgain.setVisibility(View.GONE);
    }


    public void startSpin() {
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Loading. Please wait...");
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    public void stopSpin() {
        dialog.dismiss();
    }


    //bind the model data to the GUI
    private class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    private class CharactersListAdapter extends RecyclerView.Adapter<ViewHolder> {

        private StarWars starWars;

        public CharactersListAdapter(StarWars starWars) {
            this.starWars = starWars;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            ViewHolder viewHolder = new ViewHolder(layoutInflater.inflate(R.layout.listitem_text, null));
            viewHolder.name = (TextView) viewHolder.itemView.findViewById(R.id.text_charactername);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.name.setText(starWars.getResults().get(position).getName());
            holder.name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (detailFragment == null) {
                        detailFragment = new DetailFragment();
                    }
                    Bundle bundle = new Bundle();
                    bundle.putString(ARG_NAME, starWars.getResults().get(position).getName());
                    bundle.putString(ARG_HEIGHT, starWars.getResults().get(position).getHeight());
                    bundle.putString(ARG_MASS, starWars.getResults().get(position).getMass());
                    bundle.putString(ARG_DATE_CREATED, starWars.getResults().get(position).getCreated());
                    bundle.putString(ARG_EDITED, starWars.getResults().get(position).getEdited());

                    detailFragment.setArguments(bundle);
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.hide(StarWarsCharacterFragment.this);
                    transaction.add(R.id.contentFrame, detailFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            });

        }

        @Override
        public int getItemCount() {
            return starWars.getResults().size();
        }
    }

}
