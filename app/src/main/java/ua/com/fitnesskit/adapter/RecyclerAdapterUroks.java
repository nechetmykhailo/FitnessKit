package ua.com.fitnesskit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ua.com.fitnesskit.R;
import ua.com.fitnesskit.databinding.ItemUrokBinding;
import ua.com.fitnesskit.model.Urok;

public class RecyclerAdapterUroks extends RecyclerView.Adapter<RecyclerAdapterUroks.UrokViewHolder> {

    private Context context;
    private List<Urok> models;

    public RecyclerAdapterUroks(Context context, List<Urok> models) {
        this.context = context;
        this.models = models;
    }

    @NonNull
    @Override
    public UrokViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new RecyclerAdapterUroks.UrokViewHolder(inflater
                .inflate(R.layout.item_urok, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UrokViewHolder holder, int position) {
        holder.binding.tvName.setText(models.get(position).getName());
        holder.binding.tvPlace.setText(models.get(position).getPlace());
        holder.binding.tvTeacher.setText(models.get(position).getTeacher());
        holder.binding.tvDescriptions.setText(models.get(position).getDescription());
        holder.binding.tvStartTime.setText(models.get(position).getStartTime());
        holder.binding.tvEndTime.setText(models.get(position).getEndTime());

        switch (models.get(position).getWeekDay()){
            case 1:
                holder.binding.tvWeekDay.setText("Понедельник");
                break;

            case 2:
                holder.binding.tvWeekDay.setText("Вторник");
                break;

            case 3:
                holder.binding.tvWeekDay.setText("Среда");
                break;

            case 4:
                holder.binding.tvWeekDay.setText("Четверг");
                break;

            case 5:
                holder.binding.tvWeekDay.setText("Пятница");
                break;

            case 6:
                holder.binding.tvWeekDay.setText("Суббота");
                break;

            case 7:
                holder.binding.tvWeekDay.setText("Воскресенье");
                break;
        }

        Picasso.get()
                .load(models.get(position).getTeacherImg())
                .into(holder.binding.ivTeacher);
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class UrokViewHolder extends RecyclerView.ViewHolder {
        private ItemUrokBinding binding;
        public UrokViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}
