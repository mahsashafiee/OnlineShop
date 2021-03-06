package ir.maktabsharif.onlinshop.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.ImageLoader;

import java.util.List;

import ir.maktabsharif.onlinshop.R;
import ir.maktabsharif.onlinshop.models.Category;
import ir.maktabsharif.onlinshop.network.WooCommerceRequestQueue;
import ir.maktabsharif.onlinshop.utils.SquareNetworkImage;

import static ir.maktabsharif.onlinshop.utils.Utils.setPaddingInDp;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ColorHolder> {

    private Context mContext;
    private List<Category> mCategories;
    private boolean isMain;

    private final int[] colors = new int[]{
            R.color.red_A400,
            R.color.pink_A400,
            R.color.purple_A400,
            R.color.amber_A100,
            R.color.deep_purple_A400,
            R.color.indigo_A400,
            R.color.blue_A400,
            R.color.light_blue_A400,
            R.color.cyan_A400,
            R.color.teal_A400,
            R.color.green_A400,
            R.color.amber_A400,
            R.color.deep_purple_A900,
            R.color.orange_A400,
            R.color.deep_orange_A400,
            R.color.brown_400,
            R.color.pink_A300,
            R.color.blue_gray_400,

    };

    public CategoryAdapter(Context context, List<Category> categories) {
        mContext = context;
        mCategories = categories;
    }

    public CategoryAdapter(Context context, List<Category> categories, boolean isMain) {
        mContext = context;
        mCategories = categories;
        this.isMain = isMain;
    }

    @NonNull
    @Override
    public ColorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category_list, parent, false);
        return new ColorHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ColorHolder holder, int position) {
        int color = colors[holder.getAdapterPosition()];
        int parsedColor = ContextCompat.getColor(mContext, color);
        holder.bind(mCategories.get(position), parsedColor);
    }

    @Override
    public int getItemCount() {
        return mCategories.size();
    }

    public class ColorHolder extends RecyclerView.ViewHolder {

        private final SquareNetworkImage mNetworkImage;
        private TextView mTextView;
        private final ImageLoader imageLoader;
        private Category mCategory;

        public ColorHolder(@NonNull View itemView) {
            super(itemView);
            mNetworkImage = itemView.findViewById(R.id.category_item_icon);
            mTextView = itemView.findViewById(R.id.category_item_name);
            imageLoader = WooCommerceRequestQueue.getInstance(mContext).getImageLoader();
        }

        public void bind(Category category, int parsedColor) {

            mCategory = category;

            imageLoader.get(mCategory.getImage().getURL(),
                    ImageLoader.getImageListener(mNetworkImage,
                            R.drawable.ic_checkbox_blank,
                            R.drawable.product_error_placeholder));


            mNetworkImage.setImageUrl(mCategory.getImage().getURL(), imageLoader);
            mNetworkImage.setColorFilter(Color.WHITE);
            mNetworkImage.setBackgroundColor(parsedColor);
            mTextView.setText(mCategory.getName());

            if (isMain) {
                setPaddingInDp(mContext, mNetworkImage, 32);
                mTextView.setVisibility(View.GONE);
            } else
                mTextView.setVisibility(View.VISIBLE);
        }
    }
}