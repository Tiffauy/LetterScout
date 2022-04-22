package tiffany.hoeung.wordsearch;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class StartFragment extends Fragment {
    private NavController navController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_start, container, false);

        NavHostFragment navHostFragment = (NavHostFragment) this.getActivity()
                .getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        if (navHostFragment != null)
            navController = navHostFragment.getNavController();

        view.findViewById(R.id.button_startactivity).setOnClickListener(view1 -> {
           navController.navigate(R.id.start_to_game);
        });

        view.findViewById(R.id.button_levelactivity).setOnClickListener(view1 -> {
            navController.navigate(R.id.start_to_levels);
        });

        RelativeLayout layout = view.findViewById(R.id.mainLayout);

        AnimationDrawable animationDrawable = (AnimationDrawable) layout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();

        return view;
    }
}