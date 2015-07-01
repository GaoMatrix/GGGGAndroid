package com.gao.gandroid.app.adapter;

import android.content.Context;
import android.text.SpannableString;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gao.gandroid.R;
import com.gao.gandroid.app.util.CouponPriceUtil;
import com.gao.gandroid.model.CouponBO;

/**
 * 券列表的Adapter
 *
 * @version 1.0 创建时间：15/6/28
 */
public class CouponListAdapter extends KBaseAdapter<CouponBO> {

    public CouponListAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.item_list_coupon, viewGroup, false);
            holder = new ViewHolder();
            holder.titleText = (TextView) view.findViewById(R.id.text_item_title);
            holder.infoText = (TextView) view.findViewById(R.id.text_item_info);
            holder.priceText = (TextView) view.findViewById(R.id.text_item_price);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        CouponBO coupon = itemList.get(i);
        holder.titleText.setText(coupon.getName());
        holder.infoText.setText(coupon.getIntroduce());
        SpannableString priceString;
        // 根据不同的券类型展示不同的价格显示方式
        switch (coupon.getModelType()) {
            default:
            case CouponBO.TYPE_CASH:
                priceString = CouponPriceUtil.getCashPrice(context, coupon.getFaceValue(), coupon.getEstimateAmount());
                break;
            case CouponBO.TYPE_DEBIT:
                priceString = CouponPriceUtil.getVoucherPrice(context, coupon.getDebitAmount(), coupon.getMiniAmount());
                break;
            case CouponBO.TYPE_DISCOUNT:
                priceString = CouponPriceUtil.getDiscountPrice(context, coupon.getDiscount(), coupon.getMiniAmount());
                break;
        }
        holder.priceText.setText(priceString);

        return view;
    }

    static class ViewHolder {
        TextView titleText;
        TextView infoText;
        TextView priceText;
    }

}
