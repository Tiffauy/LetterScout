package tiffany.hoeung.wordsearch;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class LevelSelectFragment extends Fragment implements RecyclerAdapter.OnNoteListener {

    private NavController navController;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerAdapter adapterRV;
    private RecyclerView levelRV;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_level_select, container, false);

        // Navigation
        NavHostFragment navHostFragment = (NavHostFragment) this.getActivity()
                .getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        if (navHostFragment != null)
            navController = navHostFragment.getNavController();

        // Define the Recycler View
        setRecyclerView(view);

        // Set button
        view.findViewById(R.id.backarrow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.levels_to_start);
            }
        });

        // Gradient stuff:
        RelativeLayout layout = view.findViewById(R.id.mainLayout);
        AnimationDrawable animationDrawable = (AnimationDrawable) layout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();

        return view;
    }

    private void setRecyclerView(View view) {
        levelRV = view.findViewById(R.id.levelselect_RV);
        layoutManager = new GridLayoutManager(this.getActivity(), 3);
        levelRV.setLayoutManager(layoutManager);
        adapterRV = new RecyclerAdapter(Level.levels, this, this);
        levelRV.setAdapter(adapterRV);
    }

    @Override
    public void onNoteClick(int position) {
        // Navigation!
        System.out.println(Level.levels.get(position).getLevelId());
    }
}