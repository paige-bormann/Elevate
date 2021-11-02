package com.example.elevate.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elevate.R;

import java.util.ArrayList;
import java.util.List;

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.WorkoutHolder> {
    private List<Workout> mWorkouts = new ArrayList<>();
    private OnWorkoutListener mOnWorkoutListener;

    public WorkoutAdapter(List<Workout> workouts, OnWorkoutListener onWorkoutListener) {
        mWorkouts = workouts;
        mOnWorkoutListener = onWorkoutListener;
    }

    @NonNull
    @Override
    public WorkoutHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_workout, parent, false);
        return new WorkoutHolder(itemView, mOnWorkoutListener);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutHolder holder, int position) {
        Workout currentWorkout = mWorkouts.get(position);
        holder.mWorkoutNameTextView.setText(currentWorkout.getName());
        holder.mWorkoutGradeTextView.setText("V" + String.valueOf(currentWorkout.getGrade()));
        holder.mWorkoutStyleTextView.setText(currentWorkout.getStyle());
    }

    @Override
    public int getItemCount() {
        return mWorkouts.size();
    }

    public void setWorkouts(List<Workout> workouts) {
        this.mWorkouts = workouts;
        notifyDataSetChanged();
    }

    public class WorkoutHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mWorkoutNameTextView;
        private TextView mWorkoutGradeTextView;
        private TextView mWorkoutStyleTextView;
        OnWorkoutListener mOnWorkoutListener;

        public WorkoutHolder(@NonNull View itemView,  OnWorkoutListener onWorkoutListener) {
            super(itemView);

            mOnWorkoutListener = onWorkoutListener;
            mWorkoutNameTextView = itemView.findViewById(R.id.workout_name_textview);
            mWorkoutGradeTextView = itemView.findViewById(R.id.workout_grade_textview);
            mWorkoutStyleTextView = itemView.findViewById(R.id.workout_style_textview);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mOnWorkoutListener.onWorkoutClick(getAbsoluteAdapterPosition());
        }
    }

    public interface OnWorkoutListener {
        void onWorkoutClick(int position);
    }
}
