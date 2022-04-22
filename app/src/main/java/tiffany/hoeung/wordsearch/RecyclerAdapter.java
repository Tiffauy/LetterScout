package tiffany.hoeung.wordsearch;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private ArrayList<Level> levels;
    private LevelSelectFragment frag;
    private OnNoteListener onNoteListener;

    public RecyclerAdapter(ArrayList<Level> levels, LevelSelectFragment frag, OnNoteListener onNoteListener) {
        this.levels = levels;
        this.frag = frag;
        this.onNoteListener = onNoteListener;
    }

    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.level_listlayout, parent, false);

        return new ViewHolder(view, onNoteListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Level level = levels.get(position);

        StringBuilder str = new StringBuilder();
        str.append(level.getLevelId());
        holder.levelNumber.setText(str.toString());

        str = new StringBuilder();
        str.append(level.getWordsComplete() + "/6");
        holder.wordsComplete.setText(str.toString());

        // Set background accordingly
        if(level.getWordsComplete() == 0)
            holder.rl.setBackground(frag.getContext().getDrawable(R.drawable.levelbackground3));
        else if (level.getWordsComplete() == 6)
            holder.rl.setBackground(frag.getContext().getDrawable(R.drawable.levelbackground));
        else
            holder.rl.setBackground(frag.getContext().getDrawable(R.drawable.levelbackground2));


    }

    @Override
    public int getItemCount() {
        return levels.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView levelNumber;
        private final TextView wordsComplete;
        private final RelativeLayout rl;
        OnNoteListener onNoteListener;

        public ViewHolder(View view, OnNoteListener onNoteListener) {
            super(view);
            levelNumber = view.findViewById(R.id.levelnumber_tv);
            wordsComplete = view.findViewById(R.id.wordscomplete_tv);
            rl = view.findViewById(R.id.level_relativeLayout);
            this.onNoteListener = onNoteListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onNoteListener.onNoteClick(getAdapterPosition());
        }
    }

    public interface OnNoteListener {
        void onNoteClick(int position);
    }
}
