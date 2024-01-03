package com.marwaeltayeb.das.adapter;

import static android.content.ContentValues.TAG;
import static androidx.core.content.ContextCompat.startActivity;
import static com.marwaeltayeb.das.utils.Constant.AMOUNT;
import static com.marwaeltayeb.das.utils.Constant.CARTID;
import static com.marwaeltayeb.das.utils.Constant.IS_CHECK_PIN;
import static com.marwaeltayeb.das.utils.Constant.LOCALHOST;
import static com.marwaeltayeb.das.utils.Constant.PRODUCTCOLOR;
import static com.marwaeltayeb.das.utils.Constant.PRODUCTID;
import static com.marwaeltayeb.das.utils.Constant.PRODUCTSIZE;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.marwaeltayeb.das.R;
import com.marwaeltayeb.das.databinding.CartListItemBinding;
import com.marwaeltayeb.das.model.Favorite;
import com.marwaeltayeb.das.model.Product;
import com.marwaeltayeb.das.storage.LoginUtils;
import com.marwaeltayeb.das.utils.RequestCallback;
import com.marwaeltayeb.das.view.ShippingAddressActivity;
import com.marwaeltayeb.das.viewmodel.AddFavoriteViewModel;
import com.marwaeltayeb.das.viewmodel.FromCartViewModel;
import com.marwaeltayeb.das.viewmodel.RemoveFavoriteViewModel;

import java.text.DecimalFormat;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private final Context mContext;

    private final List<Product> productsInCart;

    private Product currentProduct;

    private final AddFavoriteViewModel addFavoriteViewModel;
    private final RemoveFavoriteViewModel removeFavoriteViewModel;
    private final FromCartViewModel fromCartViewModel;

    private final CartAdapter.CartAdapterOnClickHandler clickHandler;

    /**
     * The interface that receives onClick messages.
     */
    public interface CartAdapterOnClickHandler {
        void onClick(Product product);
    }

    public CartAdapter(Context mContext, List<Product> productInCart, CartAdapter.CartAdapterOnClickHandler clickHandler, FragmentActivity activity) {
        this.mContext = mContext;
        this.productsInCart = productInCart;
        this.clickHandler = clickHandler;
        addFavoriteViewModel = ViewModelProviders.of(activity).get(AddFavoriteViewModel.class);
        removeFavoriteViewModel = ViewModelProviders.of(activity).get(RemoveFavoriteViewModel.class);
        fromCartViewModel = ViewModelProviders.of(activity).get(FromCartViewModel.class);
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        CartListItemBinding cartListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.cart_list_item, parent, false);
        return new CartViewHolder(cartListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        currentProduct = productsInCart.get(position);
        holder.binding.txtProductName.setText(currentProduct.getProductName());

        DecimalFormat formatter = new DecimalFormat("#,###,###");
        String formattedPrice = formatter.format(currentProduct.getProductPrice());
        holder.binding.txtProductPrice.setText(formattedPrice + " VNĐ");
        holder.binding.colorCart.setBackgroundColor(Color.parseColor(currentProduct.getCartColor()));
        holder.binding.sizeCart.setText(currentProduct.getCartSize());

        // Load the Product image into ImageView
        String imageUrl = LOCALHOST + currentProduct.getProductImage().replaceAll("\\\\", "/");
        Glide.with(mContext)
                .load(imageUrl)
                .into(holder.binding.imgProductImage);

    }

    @Override
    public int getItemCount() {
        if (productsInCart == null) {
            return 0;
        }
        return productsInCart.size();
    }

    class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // Create view instances
        private final CartListItemBinding binding;

        private CartViewHolder(CartListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            // Register a callback to be invoked when this view is clicked.
            itemView.setOnClickListener(this);
            binding.imgFavourite.setOnClickListener(this);
            binding.imgCart.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getBindingAdapterPosition();
            // Get position of the product
            currentProduct = productsInCart.get(position);

            switch (v.getId()) {
                case R.id.card_view:
                    // Send product through click
                    clickHandler.onClick(currentProduct);
//                    Log.d(TAG,""+currentProduct.getProductColor());
                    break;
                case R.id.imgFavourite:
                    toggleFavourite(currentProduct);
                    break;
                case R.id.imgCart:
                    deleteProductsInCart();
                    break;
                default: // Should not get here
            }
        }

        private void toggleFavourite(Product currentProduct) {
            Log.d(TAG,""+currentProduct.getCartID());
            Intent shippingIntent = new Intent(mContext, ShippingAddressActivity.class);
            shippingIntent.putExtra(IS_CHECK_PIN,"false");
            shippingIntent.putExtra(CARTID, currentProduct.getCartID());
            shippingIntent.putExtra(AMOUNT, currentProduct.getProductPrice());
            shippingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(shippingIntent);
        }

        private void deleteProductsInCart() {
            deleteFromCart(() -> {
                currentProduct.setIsInCart(false);
                notifyDataSetChanged();
            });
            productsInCart.remove(getBindingAdapterPosition());

            notifyItemRemoved(getBindingAdapterPosition());
            notifyItemRangeChanged(getBindingAdapterPosition(), productsInCart.size());
            showSnackBar("Removed From Cart");
//            showSnackBar("Xóa nhé");
        }

        private void showSnackBar(String text) {
            Snackbar.make(itemView, text, Snackbar.LENGTH_SHORT).show();
        }
        private void insertFavoriteProduct(RequestCallback callback) {
            Favorite favorite = new Favorite(LoginUtils.getInstance(mContext).getUserInfo().getId(), currentProduct.getProductId());
            addFavoriteViewModel.addFavorite(favorite, callback);
        }

        private void deleteFavoriteProduct(RequestCallback callback) {
            removeFavoriteViewModel.removeFavorite(LoginUtils.getInstance(mContext).getUserInfo().getId(), currentProduct.getProductId(), callback);
        }

        private void deleteFromCart(RequestCallback callback) {
            Log.d(TAG,""+LoginUtils.getInstance(mContext).getUserInfo().getId());
            Log.d(TAG,""+currentProduct.getCartID());
            fromCartViewModel.removeFromCart(LoginUtils.getInstance(mContext).getUserInfo().getId(), currentProduct.getCartID(), callback);
        }
    }
}
