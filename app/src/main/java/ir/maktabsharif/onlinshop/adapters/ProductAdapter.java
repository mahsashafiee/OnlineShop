package ir.maktabsharif.onlinshop.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

import ir.maktabsharif.onlinshop.R;
import ir.maktabsharif.onlinshop.models.Product;
import ir.maktabsharif.onlinshop.network.WooCommerceRequestQueue;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductHolder> {

    private Context mContext;
    private List<Product> mProducts;
    private SetProductOnClickListener mProductOnClickListener;

    public ProductAdapter(Context context, List<Product> products) {
        mContext = context;
        mProducts = products;
        mProductOnClickListener = (SetProductOnClickListener) context;
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.item_product_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
        holder.bind(mProducts.get(position));
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }


    public interface SetProductOnClickListener {
        void onProductClicked(@NonNull long id);
    }

    public class ProductHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ImageLoader mImageLoader;
        private Product mProduct;

        private NetworkImageView mProductImage;
        private TextView mProductName;
        private TextView mProductPrice;


        public ProductHolder(@NonNull View itemView) {
            super(itemView);
            mProductImage = itemView.findViewById(R.id.item_product_image);
            mProductName = itemView.findViewById(R.id.item_product_name);
            mProductPrice = itemView.findViewById(R.id.item_product_price);
            mImageLoader = WooCommerceRequestQueue.getInstance(mContext).getImageLoader();
            itemView.setOnClickListener(this);
        }

        public void bind(Product product) {

            mProduct = product;

            mImageLoader.get(mProduct.getImages().get(0).getURL(),
                    ImageLoader.getImageListener(mProductImage,
                            R.drawable.product_image_placeholder,
                            R.drawable.product_error_placeholder));


            mProductImage.setImageUrl(mProduct.getImages().get(0).getURL(), mImageLoader);
            mProductName.setText(product.getName());

            mProductPrice.setText(mContext.getString(R.string.product_price, mProduct.getPrice()));

        }

        @Override
        public void onClick(View v) {
            mProductOnClickListener.onProductClicked(mProduct.getID());
        }
    }
}
